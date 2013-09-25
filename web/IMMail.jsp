<%@page import="java.util.List" import="com.baosight.efmpx.cw.ml.domain.MlMail"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.baosight.efmpx.system.util.BeanContext"%>
<%@page import="com.baosight.efmpx.system.util.SessionUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="com.baosight.efmpx.system.util.PropertiesUtil"%>
<%@page import="com.baosight.efmpx.im.IMSendMessageImpl"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@page import="com.baosight.efmpx.cw.ml.manager.api.MailManagerService"%>
<%@page import="com.baosight.efmpx.cw.ml.manager.impl.MailManagerServiceImpl"%>
<% 

   Map map = PropertiesUtil.read("im");
   String IMWebURL = (String)map.get("IMWebURL");
   
   String username = request.getParameter("p_username");
   MailManagerService mailManager = (MailManagerServiceImpl) BeanContext.getBean("mailManager");
   List<MlMail> resultList=  mailManager.queryIMMailList(username);
   
   String getNum = request.getParameter("getNum");
   if(getNum!=null&&getNum.equals("getNum"))
   {
	  int num =  mailManager.getIMUnReadCount(username);
	  IMSendMessageImpl im = new IMSendMessageImpl();
	  im.sender(username+"@10.25.36.197", "mail", num,IMWebURL+"/CW/ML/CWML0004.jsp");
	  
	  out.print("");
	  return;
   }
   
   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   //String webIP = SessionUtil.getIpAddr(request);
  //  String webIP = request.getLocalAddr();
  //  request.setAttribute("webIP",webIP);
  //  int webPort = request.getLocalPort();
  //  request.setAttribute("webPort",webPort);
  //  System.out.println("IP:"+webIP+"端口"+webPort);
   
   
	String result="<list>";
	result +="<tab title='邮件'";
	result +=">";
		result +="<metadata>";
			result +="<column>";
			result +="<title>"+"URl";
			result +="</title>";
			result +="<name>";
			result +="</name>";
			result +="</column>";
			result +="<column>";
			result +="<title>"+"发件人";
			result +="</title>";
			result +="<name>";
			result +="</name>";
			result +="</column>";
			result +="<column>";
			result +="<title>"+"主题";
			result +="</title>";
			result +="<name>";
			result +="</name>";
			result +="</column>";
			result +="<column>";
			result +="<title>"+"时间";
			result +="</title>";
			result +="<name>";
			result +="</name>";
			result +="</column>";
		result +="</metadata>";
		result +="<content>";
		for(int i = 0;i<resultList.size();i++)
		{
			MlMail m =  (MlMail)resultList.get(i);
			String id = m.getMailId();
			String nodeId = m.getNodeId();
			String from = m.getMailFrom();
			String subject = m.getMailSubject();
			Date date = m.getDate();
			String dateString = format.format(date);
			String url =IMWebURL+"/CW/ML/CWML0018.jsp"+"?mailId="+id+"&nodeId="+nodeId;
			url = url.replace("&", "&amp;");
			if(subject==null)
				subject="";
			subject.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
			if(from==null)
				from="";
			from.replace("<", "&lt;").replace(">", "&gt;").replace("&", "&amp;");
			if(dateString==null)
				dateString="";
			dateString.replace("<", "&lt;").replace(">", "&gt;").replace("&", "&amp;");
			result +="<row>";
			result +="<c0>"+url;
			result +="</c0>";
			result +="<c1>"+from;
			result +="</c1>";
			result +="<c2>"+subject;
			result +="</c2>";
			result +="<c3>"+dateString;
			result +="</c3>";
			result +="</row>"; 
			
		}
		result +="</content>";
	    result +="</tab>";
	    result += "</list>";
	
   out.print(result);

%>


