package com.me.java.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.me.java.inter.UserInterface;
import com.me.java.model.Article;
import com.me.java.util.PageInfo;


@RequestMapping("/home")
@Controller
public class HomeController {
	@Autowired
	private UserInterface userInterface;

	@RequestMapping("/index")
	public String index() {
		return "home/index";
	}
	
	@RequestMapping("/pagelist")
	public String pageList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		int currentPage = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
		int pageSize = 3;
		if (currentPage<=0){
			currentPage =1;
		}
		int currentResult = (currentPage-1) * pageSize;
		
		System.out.println(request.getRequestURI());
		System.out.println(request.getQueryString());
		
		PageInfo page = new PageInfo();
		page.setShowCount(pageSize);
		page.setCurrentResult(currentResult);
		List<Article> articles=userInterface.selectArticleListPage(page,1);
		
		System.out.println(page);
		
		int totalCount = page.getTotalResult();
		
		int lastPage=0;
		if (totalCount % pageSize==0){
			lastPage = totalCount % pageSize;
		}
		else{
			lastPage =1+ totalCount / pageSize;
		}
		
		if (currentPage>=lastPage){
			currentPage =lastPage;
		}
		
		String pageStr = "";

		pageStr=String.format("<a href=\"%s\">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"%s\">下一页</a>",
						request.getRequestURI()+"?page="+(currentPage-1),request.getRequestURI()+"?page="+(currentPage+1) );

		model.put("articles", articles);
		model.put("pageStr", pageStr);
		
		return "home/pageList";
	}
}
