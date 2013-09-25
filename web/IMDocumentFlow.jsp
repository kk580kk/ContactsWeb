<%@ page contentType="text/html; charset=UTF-8" import="com.baosight.efmpx.cw.cr.common.CWCRPortalUtil,com.baosight.efmpx.portal.domain.PortletData,com.baosight.efmpx.cw.pt.portlet.domain.ShowTextData"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="net.simpleframework.web.page.PageRequestResponse"%> 
<%@ page import="net.simpleframework.web.page.component.ui.portal.PageletBean"%>
<%@ page import="net.simpleframework.web.page.component.ui.portal.PortalUtils"%>
<%@ page import="net.simpleframework.web.page.component.ui.portal.module.PortalModuleRegistryFactory"%>
<%@ page import="com.baosight.efmpx.cw.pt.portlet.DocumentFlowModuleHandle"%>
<%@ page import="com.baosight.efmpx.cw.pt.portlet.domain.ShowTextData"%>
<%@ page import="com.baosight.efmpx.system.util.DateUtil" %>
<%@ page import="com.baosight.efmpx.cw.pt.portlet.util.TextPortalModuleUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.Map"%>
<%@page import="com.baosight.efmpx.system.util.PropertiesUtil"%>
<%@page import="com.baosight.efmpx.im.IMSendMessageImpl"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	com.baosight.iplat4j.core.threadlocal.UserSession.web2Service(request);
	String username = request.getParameter("p_username");
	List<PortletData> listCWCRXZ = CWCRPortalUtil.getDaiBan(username,"XZ", 0, 6, request);
	List<PortletData> listCWCRDW = CWCRPortalUtil.getDaiBan(username,"DW", 0, 6, request);

	final PageRequestResponse requestResponse = new PageRequestResponse(request, response);
	final PageletBean pagelet = PortalUtils.getPageletBean(requestResponse);
	final DocumentFlowModuleHandle textModule = (DocumentFlowModuleHandle) PortalModuleRegistryFactory.getInstance().getModuleHandle(pagelet);

	Map map = PropertiesUtil.read("im");
	String IMWebURL = (String)map.get("IMWebURL");
	
	String getNum = request.getParameter("getNum");
    if(getNum!=null&&getNum.equals("getNum"))
    {
    	
	  int num = 0;
	  if(listCWCRXZ!=null)
		  num+=listCWCRXZ.size();
	  if(listCWCRDW!=null)
		  num+=listCWCRDW.size();
	  IMSendMessageImpl im = new IMSendMessageImpl();
	  im.sender(username+"@10.25.36.197", "document", num,IMWebURL+"/DispatchAction.do?efFormEname=CWCRFLDB&serviceName=CWCRFile&methodName=queryTaskList&i-0-fileKind2=XZ");
	  System.out.println("数量:"+num);
	  out.print("");
	  return;
    }
	
	
	   
	List<PortletData> items = listCWCRXZ;
	List<PortletData> items2 = listCWCRDW;
	if (items == null)
		items = new ArrayList<PortletData>();
	
	String 	format = "yyyy-MM-dd HH:mm:ss";
	String path = IMWebURL+"/EFMPX/IM/IMDocumentDetail.jsp";			
%>
 
    
   <list>
	<tab title="行政">
			  <metadata>
				<column>
				    <title>URL</title>
					<name></name>
				</column>
			  <column>
					<title>主题</title>
					<name></name>
				</column>
				<column>
					<title>时间</title>
					<name></name>
				</column>
			</metadata>
			<content>
				 <%
		    	      for (int i=0 ;i<items.size();i++ ) { 
		    	    	  PortletData item = items.get(i);
		    	    	  String linkUrl = item.getLinkUrl();
		    	    	  String serviceName = linkUrl.substring( (linkUrl.indexOf("serviceName=")+"serviceName=".length()), linkUrl.indexOf("&efFormEname="));
					      String efFormEname = linkUrl.substring( (linkUrl.indexOf("&efFormEname=")+"&efFormEname=".length()), linkUrl.indexOf("&methodName="));
					      String methodName = linkUrl.substring( (linkUrl.indexOf("&methodName=")+"&methodName=".length()), linkUrl.indexOf("&i-0-fileGuid="));
					      String fileGuid = linkUrl.substring( (linkUrl.indexOf("&i-0-fileGuid=")+"&i-0-fileGuid=".length()), linkUrl.indexOf("&i-0-taskId="));
					      String taskId = linkUrl.substring( (linkUrl.indexOf("&i-0-taskId=")+"&i-0-taskId=".length()), linkUrl.indexOf("&i-0-gridId="));
					      String gridId = linkUrl.substring( (linkUrl.indexOf("&i-0-gridId=")+"&i-0-gridId=".length()));
				  	      String params = "?serviceName=" + serviceName+ "&efFormEname=" + efFormEname+ "&methodName=" + methodName+ "&fileGuid=" + fileGuid+ "&taskId=" + taskId;
				  	      params = params.replace("&", "&amp;");
				  	%>
				    	<row>  		
				    		 <c0><%=path%><%=params%></c0>
				    		 <%if(item.getTitle()!=null&&item.getTitle().startsWith("<img src='/efmpx/CW/CR/images/flag.gif' alt='警告'/>"))
				    		 {
				    			 String t  = item.getTitle();
	                		    String [] titleArr = t.split("<img src='/efmpx/CW/CR/images/flag.gif' alt='警告'/>");
	                		    if(titleArr!=null&&titleArr.length>0)
	                		    item.setTitle(titleArr[1]);
				    		 }
				    		 %>
				    		 <c1><%=item.getTitle().replace("<", "&lt;").replace(">", "&gt;").replace("&", "&amp;") %></c1>
				    		<% if (item.getShowDate()!=null) {%>
				    		  <c2><%=DateUtil.toDateString(item.getShowDate(),format) %></c2>
				    	    <%}  else { %>
				    	     <c2>  </c2>
				    	    <%} %>
				    	</row>
				    	
				    <% } %>
			</content>
	</tab>
	
	<tab title="党委">
			  <metadata>
				<column>
				    <title>URL</title>
					<name></name>
				</column>
			  <column>
					<title>主题</title>
					<name></name>
				</column>
				<column>
					<title>时间</title>
					<name></name>
				</column>
			</metadata>
			<content>
				 <%
		    	      for (int i=0 ;i<items2.size();i++ ) { 
		    	    	  PortletData item = items2.get(i);
		    	    	  String linkUrl = item.getLinkUrl();
		    	    	  String serviceName = linkUrl.substring( (linkUrl.indexOf("serviceName=")+"serviceName=".length()), linkUrl.indexOf("&efFormEname="));
					      String efFormEname = linkUrl.substring( (linkUrl.indexOf("&efFormEname=")+"&efFormEname=".length()), linkUrl.indexOf("&methodName="));
					      String methodName = linkUrl.substring( (linkUrl.indexOf("&methodName=")+"&methodName=".length()), linkUrl.indexOf("&i-0-fileGuid="));
					      String fileGuid = linkUrl.substring( (linkUrl.indexOf("&i-0-fileGuid=")+"&i-0-fileGuid=".length()), linkUrl.indexOf("&i-0-taskId="));
					      String taskId = linkUrl.substring( (linkUrl.indexOf("&i-0-taskId=")+"&i-0-taskId=".length()), linkUrl.indexOf("&i-0-gridId="));
					      String gridId = linkUrl.substring( (linkUrl.indexOf("&i-0-gridId=")+"&i-0-gridId=".length()));
				  	      String params = "?serviceName=" + serviceName+ "&efFormEname=" + efFormEname+ "&methodName=" + methodName+ "&fileGuid=" + fileGuid+ "&taskId=" + taskId;
				  	      params = params.replace("&", "&amp;");
				  	%>
				    	<row>  		
				    		 <c0><%=path%><%=params%></c0>
				    		 <%if(item.getTitle()!=null&&item.getTitle().startsWith("<img src='/efmpx/CW/CR/images/flag.gif' alt='警告'/>"))
				    		 {
				    			 String t  = item.getTitle();
	                		    String [] titleArr = t.split("<img src='/efmpx/CW/CR/images/flag.gif' alt='警告'/>");
	                		    if(titleArr!=null&&titleArr.length>0)
	                		    item.setTitle(titleArr[1]);
				    		 }
				    		 %>
				    		 <c1><%=item.getTitle().replace("<", "&lt;").replace(">", "&gt;").replace("&", "&amp;") %></c1>
				    		<% if (item.getShowDate()!=null) {%>
				    		  <c2><%=DateUtil.toDateString(item.getShowDate(),format) %></c2>
				    	    <%}  else { %>
				    	     <c2>  </c2>
				    	    <%} %>
				    	</row>
				    	
				    <% } %>
			</content>
	</tab>
</list>
    
    
   

