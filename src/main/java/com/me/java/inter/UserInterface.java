package com.me.java.inter;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.me.java.model.Article;
import com.me.java.model.User;
import com.me.java.util.PageInfo;



public interface UserInterface {

	public User selectUserByID(int id);
	public List<User> selectUsers(String userName);	
	public void addUser(User user);
	public void updateUser(User user);
	public void deleteUser(int id);
	
	public List<Article> getUserArticles(int id);
	
	public List<Article> selectArticleListPage(@Param("page") PageInfo page,@Param("userid") int userid);
}
