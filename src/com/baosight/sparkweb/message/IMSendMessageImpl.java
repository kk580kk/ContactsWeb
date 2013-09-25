package com.baosight.sparkweb.message;//package com.baosight.sparkweb.message;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class IMSendMessageImpl implements IMSendMessage {
	
	public boolean sender(String springBeanId,String methodName,Object[] paramters)
	{
		  try {
			final ConnectionConfiguration config = new ConnectionConfiguration(IMSendMessageImpl.SERVER_NAME, IMSendMessageImpl.SERVER_PORT);
			  config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
			  config.setCompressionEnabled(false);
			  final XMPPConnection connection = new XMPPConnection(config);
			  connection.connect();
			  connection.loginAnonymously();
  
			  Chat chat = connection.getChatManager().createChat(IMSendMessageImpl.modelJID,new IMMessageListner());
			  Message newMessage = new Message();
			
			  newMessage.setProperty("business","type1");
			  newMessage.setProperty("springBeanId",springBeanId);
			  newMessage.setProperty("methodName",methodName);
			  newMessage.setProperty("paramters",paramters);
     
			  System.out.println(newMessage.toXML());

			  chat.sendMessage(newMessage);
			  connection.disconnect();
			return true;
		} catch (XMPPException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * -->openfire-->IMReceiveMessage
	 * xml format
	 * @param receiver
	 * @param msg
	 * @return
	 */
	public boolean sender(String receiver,String msg)
	{
		try {
			final ConnectionConfiguration config = new ConnectionConfiguration(IMSendMessageImpl.SERVER_NAME, IMSendMessageImpl.SERVER_PORT);
			  config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
			  config.setCompressionEnabled(false);
			  final XMPPConnection connection = new XMPPConnection(config);
			  connection.connect();
			  connection.loginAnonymously();
  
			  Chat chat = connection.getChatManager().createChat(IMSendMessageImpl.modelJID,new IMMessageListner());
			  Message newMessage = new Message();
			  newMessage.setProperty("business","type2");
     
			  System.out.println(newMessage.toXML());

			  chat.sendMessage(newMessage);
			  connection.disconnect();
			return true;
		} catch (XMPPException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * -->openfire-->spark
	 * @param receiver
	 * @param type
	 * @param count
	 * @return
	 */
	public boolean sender(String receiver,String type,int count)  //jid,mail,number
	{
		try {
			final ConnectionConfiguration config = new ConnectionConfiguration(IMSendMessageImpl.SERVER_NAME, IMSendMessageImpl.SERVER_PORT);
			  config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
			  config.setCompressionEnabled(false);
			  final XMPPConnection connection = new XMPPConnection(config);
			  connection.connect();
			  connection.loginAnonymously();
  
			  Chat chat = connection.getChatManager().createChat(IMSendMessageImpl.modelJID,new IMMessageListner());
			  Message newMessage = new Message();
			  newMessage.setProperty("notice", type);
	          newMessage.setProperty("num", String.valueOf(count));
			  newMessage.setProperty("receives",receiver);
     
			  System.out.println(newMessage.toXML());

			  chat.sendMessage(newMessage);
			  connection.disconnect();
			  return true;
		} catch (XMPPException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
}
