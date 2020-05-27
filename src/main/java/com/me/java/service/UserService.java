package com.me.java.service;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.me.java.base.contains.Contains;
import com.me.java.base.redis.JedisManager;
import com.me.java.inter.UserInterface;
import com.me.java.model.User;

@Service
public class UserService {

	@Autowired
	private UserInterface userInterface;
	
	@Transactional
	public User findUserById(Integer id){
		User entity = null;
		String redisKey = Contains.REDIS_CACHE_PREFIX + "UserService_findUserById";
		try {
			if(JedisManager.exists(redisKey)){
				String data_str =JedisManager.getValue(redisKey);
				entity = JSON.parseObject(data_str, User.class);
			}else{
				entity = userInterface.selectUserByID(id);
				String list_json = StringEscapeUtils.unescapeHtml(com.alibaba.fastjson.JSONObject.toJSONString(entity,SerializerFeature.WriteMapNullValue));
				JedisManager.setValue(redisKey, list_json,Contains.REDIS_CACHE_SECONDS);
			}
		} catch (Exception e) {
			entity = new User();
			e.printStackTrace();
		}
		return entity;
	}
}
