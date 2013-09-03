<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.google.gson.Gson" %>
<%--
  Created by IntelliJ IDEA.
  User: SaintKnight
  Date: 13-9-3
  Time: 下午1:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Gson gson = new GsonBuilder().create();
    String jsonData = gson.toJson(getContactsG()) ;
    out.write(jsonData);
%>
<%!
    private List<ContactsGBean> getContactsG(){
         List<ContactsGBean> contactsGBeans = new ArrayList<ContactsGBean>();
        contactsGBeans.add(contactsGBean);
        contactsGBeans.add(contactsGBean1);
        contactsGBeans.add(contactsGBean2);
        contactsGBeans.add(contactsGBean3);
        contactsGBeans.add(contactsGBean4);
        contactsGBeans.add(contactsGBean5);
        contactsGBeans.add(contactsGBean6);
        return contactsGBeans;
    }
    ContactsGBean contactsGBean = new ContactsGBean("a","a","a@berserker","root");
    ContactsGBean contactsGBean1 = new ContactsGBean("b","b","b@berserker","root");
    ContactsGBean contactsGBean2 = new ContactsGBean("c","c","c@berserker","root");
    ContactsGBean contactsGBean3 = new ContactsGBean("d","d","d@berserker","root");
    ContactsGBean contactsGBean4 = new ContactsGBean("e","e","e@berserker","root");
    ContactsGBean contactsGBean5 = new ContactsGBean("f","f","f@berserker","root");
    ContactsGBean contactsGBean6 = new ContactsGBean("g","g","g@berserker","root");
    ContactsGBean contactsGBean7 = new ContactsGBean("g","g","g@berserker","root");
    class ContactsGBean {
        private String alias;
        private String nickname;
        private String fullyQualifiedJID;
        private String groupName;

        ContactsGBean(String alias, String nickname, String fullyQualifiedJID, String groupName) {
            this.alias = alias;
            this.nickname = nickname;
            this.fullyQualifiedJID = fullyQualifiedJID;
            this.groupName = groupName;
        }

        ContactsGBean(String alias, String nickname, String fullyQualifiedJID) {
            this.alias = alias;
            this.nickname = nickname;
            this.fullyQualifiedJID = fullyQualifiedJID;
        }

        void setAlias(String alias) {
            this.alias = alias;
        }

        void setNickname(String nickname) {
            this.nickname = nickname;
        }

        void setFullyQualifiedJID(String fullyQualifiedJID) {
            this.fullyQualifiedJID = fullyQualifiedJID;
        }

        void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        String getAlias() {
            return alias;
        }

        String getNickname() {
            return nickname;
        }

        String getFullyQualifiedJID() {
            return fullyQualifiedJID;
        }

        String getGroupName() {
            return groupName;
        }
    }
%>