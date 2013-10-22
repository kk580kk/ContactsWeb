package com.baosight.sparkweb.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baosight.sparkweb.service.SparkMenu;
import com.baosight.sparkweb.service.SparkOrgTree;
import com.baosight.sparkweb.service.SparkXmlServiceInterface;

public class SparkServiceImpl implements SparkXmlServiceInterface{

	@Override
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

	@Override
	public String menu(HashMap params) {
		SparkMenu menu = new SparkMenu();
		
		String rootUUID = menu.addRow("root", "门户菜单", "");
		menu.addRow(rootUUID,"测试ItemA","");
		String subMenuUUID = menu.addRow(rootUUID,"测试ItemB","");
		
		//子菜单
		menu.addRow(subMenuUUID,"测试ItemB","http://www.baidu.com");
		return menu.toXmlString();
	}
}
