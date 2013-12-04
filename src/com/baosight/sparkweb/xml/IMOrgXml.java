/**
 * 
 */
package com.baosight.efmpx.im;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.baosight.efmpx.system.service.ad.IGroup;
import com.baosight.efmpx.system.service.ad.IUser;
import com.baosight.efmpx.system.service.factory.PubServiceFactory;
import com.baosight.spes.ad.enums.EnumGroupKind1;
/**
 * @author Administrator
 * 
 */
public class IMOrgXml {

	private String ctx;
	
	public String getCtx() {
		return ctx;
	}

	public void setCtx(String ctx) {
		this.ctx = ctx;
	}
	public String getIMXml(String ctx, String parentGuid, String level,
			String top, String labelParent) {
		setCtx(ctx);
		Document document = DocumentHelper.createDocument();
		Element elementRoot = document.addElement("blocks");
		Element elementUser = elementRoot.addElement("block");
		elementUser.setAttributeValue("name", "user");
		Element elementOrg = elementRoot.addElement("block");
		elementOrg.setAttributeValue("name", "org");

		// addIMChildren(parentGuid, elementOrg, level);
		if (top != null && top.equals("top")) {
			List<IGroup> list = PubServiceFactory.getAdGroupService()
					.getTopGroup(EnumGroupKind1.getOrgList());
			for (int i = 0; i < list.size(); i++) {
				String label = list.get(i).getGroupLabel();
				String name = list.get(i).getGroupName();
				Element elementRow = elementOrg.addElement("row");
				Element elementGroupName = elementRow.addElement("groupName");
				Element elementGroupLabel = elementRow.addElement("groupLabel");
				elementGroupName.addText(name);
				elementGroupLabel.addText(label);
			}

		} else {
			List<IGroup> list = PubServiceFactory.getAdGroupService()
					.getSubGroup(labelParent, EnumGroupKind1.getOrgList());
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					String label = list.get(i).getGroupLabel();
					String name = list.get(i).getGroupName();
					Element elementRow = elementOrg.addElement("row");
					Element elementGroupName = elementRow
							.addElement("groupName");
					Element elementGroupLabel = elementRow
							.addElement("groupLabel");
					elementGroupName.addText(name);
					elementGroupLabel.addText(label);
				}
			}

			List<IUser> userList = PubServiceFactory.getAdGroupService()
					.getGroupMember(labelParent);
			if (userList != null && userList.size() > 0) {
				for (int i = 0; i < userList.size(); i++) {
					String label = userList.get(i).getUserLabel();
					String name = userList.get(i).getUserName();
					Element elementRow = elementUser.addElement("row");
					Element elementUserName = elementRow.addElement("userName");
					Element elementUserLabel = elementRow
							.addElement("userLabel");
					elementUserName.addText(name);
					elementUserLabel.addText(label);
				}
			}

		}

		return document.asXML();
	}

	public Element getIMGroupAndUser(Element elementRoot, String labelParent) {

		List<IUser> userList = PubServiceFactory.getAdGroupService()
				.getGroupMember(labelParent);
		if (userList != null && userList.size() > 0) {
			for (int i = 0; i < userList.size(); i++) {
				String label = userList.get(i).getUserLabel();
				String name = userList.get(i).getUserName();
				Element elementRow = elementRoot.addElement("row");
				label.replace("&", "&amp;").replace("<", "&lt;").replace(">",
						"&gt;");
				name.replace("&", "&amp;").replace("<", "&lt;").replace(">",
						"&gt;");
				elementRow.setAttributeValue("name", name);
				elementRow.setAttributeValue("guid", label);
				elementRow.setAttributeValue("type", "2");
				// Element elementUserName = elementRow.addElement("name");
				// Element elementUserLabel = elementRow.addElement("guid");
				// Element elementUserType = elementRow.addElement("type");
				// elementUserName.addText(name);
				// elementUserLabel.addText(label);
				// elementUserType.addText("2");
			}
		}

		List<IGroup> groupList = PubServiceFactory.getAdGroupService()
				.getSubGroup(labelParent, EnumGroupKind1.getOrgList());
		if (groupList != null && groupList.size() > 0) {
			for (int i = 0; i < groupList.size(); i++) {
				String label = groupList.get(i).getGroupLabel();
				String name = groupList.get(i).getGroupName();
				Element elementRow = elementRoot.addElement("row");
				label.replace("&", "&amp;").replace("<", "&lt;").replace(">",
						"&gt;");
				name.replace("&", "&amp;").replace("<", "&lt;").replace(">",
						"&gt;");
				elementRow.setAttributeValue("name", name);
				elementRow.setAttributeValue("guid", label);
				elementRow.setAttributeValue("type", "1");
				// Element elementGroupName = elementRow.addElement("name");
				// Element elementGroupLabel = elementRow.addElement("guid");
				// Element elementGroupType = elementRow.addElement("type");
				// elementGroupName.addText(name);
				// elementGroupLabel.addText(label);
				// elementGroupType.addText("1");
				if (PubServiceFactory.getAdGroupService().getSubGroup(label,
						EnumGroupKind1.getOrgList()) != null
						|| PubServiceFactory.getAdGroupService()
								.getGroupMember(label) != null) {
					getIMGroupAndUser(elementRow, label);
				}
			}
		}

		return elementRoot;
	}

	public String getIMBroadCastXml(String ctx, String parentGuid,
			String level, String top, String labelParent) {
		setCtx(ctx);
		Document document = DocumentHelper.createDocument();
		Element elementRoot = document.addElement("row");
		elementRoot.setAttributeValue("name", "");
		elementRoot.setAttributeValue("guid", parentGuid);
		elementRoot.setAttributeValue("type", "1");

		elementRoot = getIMGroupAndUser(elementRoot, labelParent);
		System.out.println(document.asXML());

		return document.asXML();
	}
}
