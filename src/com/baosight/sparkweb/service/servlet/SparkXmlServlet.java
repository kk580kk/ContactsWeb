package com.baosight.sparkweb.service.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baosight.sparkweb.service.SparkServiceXmlFactory;
import com.baosight.sparkweb.service.SparkXmlServiceInterface;

public class SparkXmlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		response.setCharacterEncoding("UTF-8");
		if(type != null)
		{
			if("org".equals(type.toLowerCase()))
			{
				String parent = request.getParameter("parent");
				HashMap<String,String> params = new HashMap<String,String>();
				params.put("parent", parent);
				SparkXmlServiceInterface service = SparkServiceXmlFactory.getService();
				String outString = service.orgTree(params);
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(outString);
				
			}else if("navi".equals(type.toLowerCase()))
			{
				//return menu.xml
				HashMap<String,String> params = new HashMap<String,String>();
				SparkXmlServiceInterface service = SparkServiceXmlFactory.getService();
				String outString = service.menu(params);
				response.getWriter().write(outString);
			}
		}
		
	}
}
