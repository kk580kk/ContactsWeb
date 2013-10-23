package com.baosight.sparkweb.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baosight.iplat4j.security.bridge.SecurityBridge;
import com.baosight.iplat4j.security.bridge.SecurityBridgeFactory;
import com.baosight.platform.core.security.base.Filter;
import com.baosight.sparkweb.service.SparkMenu;
import com.baosight.sparkweb.service.SparkOrgTree;
import com.baosight.sparkweb.service.SparkXmlServiceInterface;

import com.baosight.iplat4j.es.domain.ESOrganization;
import com.baosight.iplat4j.es.domain.ESPost;
import com.baosight.iplat4j.es.domain.ESUser;

public class SparkServiceImpl implements SparkXmlServiceInterface{

	public String orgTree(HashMap params) {
		// TODO Auto-generated method stub
		
		//example data
		
		List<Map> userret = new ArrayList<Map>();
		List<Map> groupret = new ArrayList<Map>();
		HashMap user1 = new HashMap();
		user1.put("username", "洞里");
		user1.put("userlabel", "10000");
		
		HashMap user2 = new HashMap();
		user2.put("username", "洞里1");
		user2.put("userlabel", "10001");
		userret.add(user1);
		userret.add(user2);

		HashMap group1 = new HashMap();
		group1.put("groupname", "组1");
		group1.put("grouplabel", "1000011");
		groupret.add(group1);
		
		//get some info
		String parent = (String) params.get("parent");
		SparkOrgTree orgTree = new SparkOrgTree();
		//do query
		if(parent != null)
		{
			//query with parent
			Map temp;
			
			//add user
			for(int i=0 ; i<userret.size() ; i++)
			{
				temp = userret.get(i);
				orgTree.addUser((String)temp.get("username"), (String)temp.get("userlabel"));
			}
			
			//add group
			for(int i=0 ; i<groupret.size() ; i++)
			{
				temp = groupret.get(i);
				orgTree.addOrg((String)temp.get("groupname"), (String)temp.get("grouplabel"));
			}
		}else{
			//query the first node
			Map temp;
			orgTree.addBlock("user");
			for(int i=0 ; i<groupret.size() ; i++)
			{
				temp = groupret.get(i);
				orgTree.addOrg((String)temp.get("groupname"), (String)temp.get("grouplabel"));
			}
		}
		return orgTree.toXmlString();
	}

	public String menu(HashMap params) {
		SparkMenu menu = new SparkMenu();
		
		String rootUUID = menu.addRow("root", "门户菜单", "");
		menu.addRow(rootUUID,"测试ItemA","");
		String subMenuUUID = menu.addRow(rootUUID,"测试ItemB","");
		
		//子菜单
		menu.addRow(subMenuUUID,"测试ItemB","http://www.baidu.com");
		return menu.toXmlString();
	}
	private String getParam(HashMap params,String key)
	{
		String[] tempStr = (String[])params.get(key);
		if(tempStr != null)
		{
			return tempStr[0];
		}
		return null;
	}
	
	private String realOrgTree(HashMap params)
	{
		//
		SparkOrgTree orgTree = new SparkOrgTree();
		String virtual = getParam(params,"parent");
		String limit = getParam(params,"limit");
		String offset = getParam(params,"offset");
		if(limit == null) limit = "-1";
		if(offset == null) offset = "-1";
		
		orgTree.addBlock("user");
		orgTree.addBlock("org");
		//org nodes
		SecurityBridge b = SecurityBridgeFactory.getBridge();
		List orgNodes;
		List userNodes;
		
		try{
		if (virtual == null) {
			orgNodes =  b.getTopAuthzNodes(Integer.parseInt(offset),Integer.parseInt(limit));
		} else {
			orgNodes = b.getSubAuthzNodes(virtual,Integer.parseInt(offset),Integer.parseInt(limit));
		}
		
		
		ESOrganization temp ;
		Filter filter = new Filter();
		//need user
		for(int i = 0 ; i < orgNodes.size() ; i++)
		{
			temp = (ESOrganization)orgNodes.get(i);
			orgTree.addOrg(temp.getName(), temp.getLabel());
	    }
		
		if(virtual != null)
		{
			//search user  what filter do?
			ESPost  tempPost;
			List ret = b.getProjectRoles(virtual, filter,Integer.parseInt(offset), Integer.parseInt(limit));
			List members = null;
			ESUser tempUser;
			for(int i = 0 ; i  < ret.size() ; i++)
			{
				tempPost = (ESPost) ret.get(i);
				members = b.getRoleMembers(tempPost.getPostLabel(), null, null,Integer.parseInt(offset),Integer.parseInt(limit));
				if(members != null)
				{
					for(int ptr = 0 ; ptr < members.size() ; ptr++)
					{
						tempUser = (ESUser) members.get(ptr);
						orgTree.addUser(tempUser.getDisplayName(), tempUser.getLoginName());
					}
				}
			}
		}
		}catch(Exception e)
		{
			
		}
		return orgTree.toXmlString();
		//user nodes
	}
	
	public String doService(Map params) {
		// TODO Auto-generated method stub
		return realOrgTree((HashMap) params);
	}
}
