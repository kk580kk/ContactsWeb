<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.baosight.efmpx.system.util.BeanContext"%>
<%@page import="com.baosight.efmpx.jqtree.JQueryTree"%>
<%
com.baosight.iplat4j.core.threadlocal.UserSession.web2Service(request);
	String nodeid  = request.getParameter("nodeid");
	String level  = request.getParameter("n_level");
	System.out.println("nodeid=========="+nodeid);
	System.out.println("level=========="+level);
	String serviceName = request.getParameter("serviceName");
	
	String topid = request.getParameter("topid");
	String top = request.getParameter("top");
	String broad = request.getParameter("broad");
	String labelParent = request.getParameter("labelParent");
	System.out.println("topid=========="+topid);
	if(nodeid==null&&topid!=null&&!topid.equals("")){
		nodeid = topid;
	}
	if(serviceName==null){
		out.println("serviceName为null,请重新配置");
		return;
	}
	
	if(nodeid==null){
		nodeid = "";
	}
	if(broad!=null)
	{
		//广播消息时查询人员
		String ctx = request.getContextPath();
		JQueryTree jQueryTree = (JQueryTree)BeanContext.getBean(serviceName);
		String outStr = jQueryTree.getIMBroadCastXml(ctx,nodeid, level,top,labelParent);
		response.getWriter().print(outStr);
	}
	else
	{
		//点击组织机构查询人员
		String ctx = request.getContextPath();
		JQueryTree jQueryTree = (JQueryTree)BeanContext.getBean(serviceName);
		String outStr = jQueryTree.getIMXml(ctx,nodeid, level,top,labelParent);
		response.getWriter().print(outStr);
	}

%>