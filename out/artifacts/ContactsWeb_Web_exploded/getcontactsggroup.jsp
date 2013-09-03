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
    ContactsGGroupBean contactsGGroupBean = new ContactsGGroupBean("root",null);
    ContactsGGroupBean contactsGGroupBean1 = new ContactsGGroupBean("a","root");
    ContactsGGroupBean contactsGGroupBean2 = new ContactsGGroupBean("b","root");
    ContactsGGroupBean contactsGGroupBean3 = new ContactsGGroupBean("c","a");
    ContactsGGroupBean contactsGGroupBean4 = new ContactsGGroupBean("d","a");
    ContactsGGroupBean contactsGGroupBean5 = new ContactsGGroupBean("e","d");
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
        contactGGroupsTest.add(contactsGGroupBean);
        contactGGroupsTest.add(contactsGGroupBean1);
        contactGGroupsTest.add(contactsGGroupBean2);
        contactGGroupsTest.add(contactsGGroupBean3);
        contactGGroupsTest.add(contactsGGroupBean4);
        contactGGroupsTest.add(contactsGGroupBean5);
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