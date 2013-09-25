<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.baosight.efmpx.cw.ml.domain.MlMenutree"%>
<%@page import="com.baosight.efmpx.cw.ml.common.EmailModuleHandleUtil"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ include file="BootstrapHead1.jsp"%>	
<%@page import="com.baosight.spes.ad.enums.EnumGroupKind1"%>
<%@page import="com.baosight.spes.ad.common.Constant"%>


<%
	String mailId = request.getParameter("mailId");
	String nodeId = request.getParameter("nodeId");

	MlMenutree node = EmailModuleHandleUtil.queryMenuTree(nodeId);
	String nodeUrl = node.getNodeUrl();
	String nodeName = node.getNodeName();
	String nodeParentId = node.getNodeParentId();
	
%>

	<div class="span2">
			<%@ include file="/CW/ML/MLTREE.jsp"%>
    </div>
   
   
   <div class="box span10">
		  <div class="row-fluid" id="div-read" >
			    <div class="span5" >
			    	<div id="div-information">
			    	</div>
			    </div>
			    <div class="span7" style="">
			         <div id="div-content"><h2>欢迎使用</h2>
			    	</div>
			    </div>
		</div>	  
		
		 <!-- 写邮件 -->   
		  <div class="row-fluid" id="div-write" >
			   
		 </div>	  
		 <!-- 写邮件结束 -->   
		 
		 <form id="receiveMailForm" action="" method="post">
		     <p></p>
		 </form>
		
   </div>
   

	
	<script type="text/javascript">
	
	function load(){
	
	    //邮件列表
	    $('#div-read').css("display","block");
	    $('#div-write').css("display","none");
	       
      
	    var nodeId = '<%=nodeId%>';
        document.getElementById("nodeIdHidden").value = nodeId;
        
        var nodeUrl= '<%=nodeUrl%>';
        document.getElementById("nodeUrlHidden").value = nodeUrl;
        
       var nodeValue =0;
       var nodeName = '<%=nodeName%>';
       var nodeParentId = '<%=nodeParentId%>';
        
       var mailId = '<%=mailId%>';
        
       if(nodeName=="收件箱")
           nodeValue = 1;
       if(nodeName=="发件箱")
      	 nodeValue = 2;
       if(nodeName=="草稿箱")
         nodeValue = 3;
       if(nodeName=="已删除箱")
         nodeValue = 4;
         
       var action="${ctx }/DispatchAction.do?serviceName=MlMail&efFormEname=CWML0005&methodName=queryMailList&i-0-nodeId="+nodeId+"&i-0-nodeUrl="+nodeUrl+"&i-0-nodeParentId="+nodeParentId+"&i-0-nodeValue="+nodeValue;	
	  

	  
		jQuery.ajax({
			type:'post',
			url:action,
			async:false,
			success:function(msg){
				$('#div-information').html(msg);
			},
		});
		
		$('#div-content').html("");
		
		 //邮件详情  mailId,nodeId,nodeValue
		   var pageCode="CWML0006";
	       if(nodeValue==2)
	          pageCode="CWML0011";
	       if(nodeValue==3)
	          pageCode="CWML0012";
	       if(nodeValue==4)
	          pageCode="CWML0013";
	       var action="${ctx }/DispatchAction.do?serviceName=MlMail&efFormEname="+pageCode+"&methodName=queryMailById&i-0-mailId="+mailId+"&i-0-nodeId="+nodeId+"&i-0-nodeValue="+nodeValue;			  
		 
		 	jQuery.ajax({
			type:'post',
			url:action,
			async:false,
			success:function(msg){
				$('#div-content').html(msg);
			},
		});
		
	  
	  
	  }

	load();
	
	
	
	
	     function receive()
        {  
            var x=   document.getElementById("nodeIdHidden").value; 
            var y=   document.getElementById("nodeUrlHidden").value; 
            if(y=="")
            {
               alert("请选择需要邮箱！");
               return;
            }
		  //  var action="${ctx}/DispatchAction.do?serviceName=MlMail&efFormEname=CWML0007&methodName=receiveMail&i-0-nodeUrl="+y;
		 //    document.all.receiveMailForm.action = action;
		 //    document.all.receiveMailForm.submit();
		  	
		    var path ="${ctx}/DispatchAction.do?serviceName=MlMail&efFormEname=CWML0008&methodName=ajaxMail&i-0-nodeUrl="+y;
        	createModal("CWML0004","收邮件中",path,"600px");
        	
        }
	
	   function writeMail()
        {
            $('#div-read').css("display","none");
            $('#div-write').css("display","block");
		  	var path="${ctx}/DispatchAction.do?serviceName=MlMail&efFormEname=CWML0009&methodName=writeMail";
        	jQuery.ajax({
			type:'post',
			url:path,
			async:false,
			success:function(msg){
				$('#div-write').html(msg);
			   }
		     });
        }
	
	 function attachment()
       {
            $('#div-read').css("display","none");
            $('#div-write').css("display","block");
            var x=   document.getElementById("nodeIdHidden").value; 
            var y=   document.getElementById("nodeUrlHidden").value; 
            
            if(x=="")
            {
               alert("请选择需要查看附件的邮箱！");
               return;
            }
              
		  	var path ="${ctx}/DispatchAction.do?serviceName=MlMail&efFormEname=CWML0010&methodName=attachemntManage&i-0-nodeUrl="+y;
           //	createModal("CWML0004","附件管理",path,"800px");
            jQuery.ajax({
			type:'post',
			url:path,
			async:false,
			success:function(msg){
				$('#div-write').html(msg);
			   }
		     });
        
       }
	
	  function account()
       {
            $('#div-read').css("display","none");
            $('#div-write').css("display","block");
           var path ="${ctx}/DispatchAction.do?serviceName=AccountManager&efFormEname=CWML0001&methodName=queryAccountList";
           // createModal("CWML0004","账户管理",path,"900px");
            jQuery.ajax({
			type:'post',
			url:path,
			async:false,
			success:function(msg){
				$('#div-write').html(msg);
			   }
		     });
         
       }
       
     function changeDisplay(){ 
       var val = document.getElementById("i-0-cc").innerText;
       if(val=="添加抄送")
       {
         document.getElementById("i-0-cc").innerText="隐藏抄送";
	     $("#i-0-CcChoose").css("display","block");
	     return;
	   }
	   if(val=="隐藏抄送")
	   {
	     document.getElementById("i-0-recipientCc").value="";
         document.getElementById("i-0-cc").innerText="添加抄送";
	     $("#i-0-CcChoose").css("display","none");
	   
	   }
	   
	}
	
	
	</script>
<%@ include file="/EFMPX/UI/bootstrap/BootstrapFoot1.jsp"%>