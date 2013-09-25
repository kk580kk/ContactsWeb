<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/EFMPX/UI/bootstrap/BootstrapHead.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%
  String serviceName = request.getParameter("serviceName"); 
  String efFormEname = request.getParameter("efFormEname"); 
  String methodName = request.getParameter("methodName"); 
  String fileGuid = request.getParameter("fileGuid"); 
  String taskId = request.getParameter("taskId"); 
%>
    
     <script type="text/javascript">
       $(document).ready(function(){
         doJumper();
       });
      
       function doJumper(){
      	   var url = "DispatchAction.do?serviceName="+"<%=serviceName%>"+"&efFormEname="+"<%=efFormEname%>"+"&methodName="+"<%=methodName%>"+"&i-0-fileGuid="+"<%=fileGuid%>"+"&i-0-taskId="+"<%=taskId%>";
      	   var path = "${ctx}/"+url+"&i-0-gridId=null";
      	   createModal("CWCRFL06","处理",path,"950px");
      	}
      	
      	function doJumper1(){
      	   var url = "DispatchAction.do?serviceName="+"<%=serviceName%>"+"&efFormEname="+"<%=efFormEname%>"+"&methodName="+"<%=methodName%>"+"&i-0-fileGuid="+"<%=fileGuid%>"+"&i-0-taskId="+"<%=taskId%>";
      	   var path = "${ctx}/"+url+"&i-0-gridId=null";
      	   createModal("CWCRFL06","处理",path,"950px");
      	}    
      </script>


<%@ include file="/EFMPX/UI/bootstrap/BootstrapFoot.jsp"%>
