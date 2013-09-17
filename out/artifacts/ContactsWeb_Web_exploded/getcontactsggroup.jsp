<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%--
  Created by IntelliJ IDEA.
  User: SaintKnight
  Date: 13-8-29
  Time: 下午3:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String type = request.getParameter("getType");
    String parentGroup = request.getParameter("parentGroup");
    String groupName = request.getParameter("groupName");

    if (null==type) {
        String root = getRoot();
        out.write(root);
    } else {
        String partition = getChild(parentGroup);
        out.write(partition);
    }
%>
<%!
    class ContactsGGroupBean {
        private String groupName;
        private String parentGroup;

        private ContactsGGroupBean(String groupName, String parentGroup) {
            this.groupName = groupName;
            this.parentGroup = parentGroup;
        }

        private String getGroupName() {
            return groupName;
        }

        private String getParentGroup() {
            return parentGroup;
        }

        private void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        private void setParentGroup(String parentGroup) {
            this.parentGroup = parentGroup;
        }
    }

//    String root = "{\n" +
//            "    \"groupName\": \"root\",\n" +
//            "    \"parentGroup\": null\n" +
//            "}";
//    String a = "{\n" +
//            "    \"groupName\": \"a\",\n" +
//            "    \"parentGroup\": \"root\"\n" +
//            "}";
//    String b = "{\n" +
//            "    \"groupName\": \"b\",\n" +
//            "    \"parentGroup\": \"root\"\n" +
//            "}";
//    String c = "{\n" +
//            "    \"groupName\": \"c\",\n" +
//            "    \"parentGroup\": \"a\"\n" +
//            "}";
//    String d = "{\n" +
//            "    \"groupName\": \"d\",\n" +
//            "    \"parentGroup\": \"a\"\n" +
//            "}";
//    String e = "{\n" +
//            "    \"groupName\": \"e\",\n" +
//            "    \"parentGroup\": \"d\"\n" +
//            "}";

    private String getRoot() {
        List<ContactsGGroupBean> contactGGroupsTest = new ArrayList<ContactsGGroupBean>();

        //测试数据，2013年9月3日14:24:41
        contactGGroupsTest.add(new ContactsGGroupBean("解决方案事业部",null));
        contactGGroupsTest.add(new ContactsGGroupBean("Software Dept",null));
        contactGGroupsTest.add(new ContactsGGroupBean("JAVA","Software Dept"));
        contactGGroupsTest.add(new ContactsGGroupBean("C#","Software Dept"));
        contactGGroupsTest.add(new ContactsGGroupBean("Design Department",null));
        contactGGroupsTest.add(new ContactsGGroupBean("Graphic design department","Design Department"));
        contactGGroupsTest.add(new  ContactsGGroupBean("Interface design","Design Department"));
        contactGGroupsTest.add( new ContactsGGroupBean("Java Server Pages","JAVA"));
        contactGGroupsTest.add(new ContactsGGroupBean("J2EE","JAVA"));
        contactGGroupsTest.add( new ContactsGGroupBean("Finance Department",null));
        contactGGroupsTest.add( new ContactsGGroupBean("Audit Department",null));

        String jsonData =new GsonBuilder().create().toJson(contactGGroupsTest, new TypeToken<List<ContactsGGroupBean>>() {
        }.getType());
        return jsonData;
//        return "["+root+"]";
    }

    private String getChild(String parentGroup) {
        String groupString = "[";
//        if ("a".equals(parentGroup)) {
//            groupString += c;
//            groupString += d;
//        } else if ("d".equals(parentGroup)) {
//            groupString += e;
//        } else if ("root".equals(parentGroup)) {
//            groupString += a;
//        } else {
//            groupString +=root;
//        }
        groupString+="]"      ;
        return groupString;

    }
%>