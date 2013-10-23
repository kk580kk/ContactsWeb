package com.baosight.sparkweb.service.servlet;

import com.baosight.iplat4j.core.spring.SpringApplicationContext;
import com.baosight.sparkweb.service.SparkXmlServiceFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class SparkXmlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        response.setCharacterEncoding("UTF-8");
        Map params = request.getParameterMap();
        if (type != null) {
            SparkXmlServiceFilter ins = (SparkXmlServiceFilter) SpringApplicationContext.getBean("xmlservicefilter");
            String outputString = ins.doFilter(type, params);
            if (outputString != null) {
                response.getWriter().write(outputString);
            } else {
                response.getWriter().write("No output,perhaps no service available");
            }
        } else {
            response.getWriter().write("Parameters incorrect");
        }
//		{
//			if("org".equals(type.toLowerCase()))
//			{
//				String parent = request.getParameter("parent");
//				HashMap<String,String> params = new HashMap<String,String>();
//				params.put("parent", parent);
//				SparkXmlServiceInterface service = SparkServiceXmlFactory.getService();
//				String outString = service.orgTree(params);
//				response.setCharacterEncoding("UTF-8");
//				response.getWriter().write(outString);
//				
//			}else if("navi".equals(type.toLowerCase()))
//			{
//				//return menu.xml
//				HashMap<String,String> params = new HashMap<String,String>();
//				SparkXmlServiceInterface service = SparkServiceXmlFactory.getService();
//				String outString = service.menu(params);
//				response.getWriter().write(outString);
//			}
//		}
    }
}
