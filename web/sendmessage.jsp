<%@ page import="com.baosight.smirk.SmirkMsgSender,com.baosight.smirk.MessageFactory" %>
<%--
  Created by IntelliJ IDEA.
  User: SaintKnight
  Date: 13-9-4
  Time: 下午8:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
 //   IMSendMessageImpl imSendMessage = new IMSendMessageImpl();
 //   imSendMessage.sender("a@berserker","Hxello World");
    SmirkMsgSender sms = new SmirkMsgSender();
    sms.sendMsg("demo", MessageFactory.create("Hello"), null);
    out.write("Send success");
%>