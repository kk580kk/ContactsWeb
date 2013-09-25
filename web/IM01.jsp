<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.baosight.spes.ps.domain.Mainmenu"%>
<%@page import="java.util.List"%>
<%@page import="com.baosight.spes.hp.IndexHelp"%>
<%@page import="com.baosight.spes.ps.constant.EnumMainMenuLevel"%>
<row  guid='00000000' url='' name='门户菜单'>
	<%
	String p_username = request.getParameter("p_username");
		List listLeft = IndexHelp.getIndexTreeData(request,p_username,EnumMainMenuLevel.GenGuid);
		int depthLeft = ((Mainmenu)listLeft.get(0)).getDepth();//初始层级
		for(int i=0;i<listLeft.size();i++){
  			Mainmenu menu = (Mainmenu)listLeft.get(i); 	
  			Mainmenu nextMenu = null;
			if(i<listLeft.size()-1){
				nextMenu = (Mainmenu)listLeft.get(i+1); 	
			}
			int curDepth = menu.getDepth();
			String menuguid = menu.getMenuguid();
			String menurl = menu.getUrl();
			int reject = menu.getReject();
			if(nextMenu!=null){				
				int nextDepth = nextMenu.getDepth();
				if(curDepth<nextDepth){//降级
	%>
	<row guid='<%=menuguid%>' url='<%=menurl%>' name='<%=menu.getName() %>'>	
		
	<%
				}else if(curDepth==nextDepth){//平级
	%>
			<row guid='<%=menuguid%>' url='<%=menurl%>' name='<%=menu.getName() %>'></row>
	<%
				}else{//升级
	%>
			<row guid='<%=menuguid%>' url='<%=menurl%>' name='<%=menu.getName() %>'></row>
	<%
					for(int j=0;j<curDepth-nextDepth;j++){
	%>
						
				     </row>
	<%						
					}
				
				}
			}else{
	%>
			<row guid='<%=menuguid%>' url='<%=menurl%>' name='<%=menu.getName() %>'></row>
	<%
				for(int j=0;j<curDepth-depthLeft;j++){
	%>
				
			     </row>
	<%
				}
			}
		}
	%>
</row>		