<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.baosight.efmpx.system.util.BeanContext"%>

<%@page import="com.baosight.efmpx.cw.ml.manager.api.MailManagerService"%>
<%@page import="com.baosight.efmpx.cw.ml.manager.impl.MailManagerServiceImpl"%>

<% 
   //com.baosight.iplat4j.core.threadlocal.UserSession.web2Service(request);
   String mailId = request.getParameter("mailId");
   MailManagerService mailManager = (MailManagerServiceImpl) BeanContext.getBean("mailManager");
   String mailXML =  mailManager.queryIMMailContent(mailId);
   out.print(mailXML);

%>


