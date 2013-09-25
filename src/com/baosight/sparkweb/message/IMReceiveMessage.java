package com.baosight.sparkweb.message;//package com.baosight.sparkweb.message;

import java.lang.reflect.Method;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;



public class IMReceiveMessage  implements PacketListener{
	public static final String SERVER_NAME = "10.25.36.197";
	public static final int SERVER_PORT = 5222; 
	private final String clientName="00000000_00001";
	private final String clientPassword ="00000000_00001";

	private XMPPConnection connection = null;
	public static String cid = null;
	public IMReceiveMessage()
	{
        try {
			final ConnectionConfiguration config = new ConnectionConfiguration(
					IMReceiveMessage.SERVER_NAME, IMReceiveMessage.SERVER_PORT);
			config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
			config.setCompressionEnabled(false);

			connection = new XMPPConnection(config);
			connection.connect();
			//connection.login(this.clientName, this.clientPassword);
			connection.loginAnonymously();
			System.out.println("connection id============="+connection.getConnectionID());
			cid = connection.getConnectionID();
			PacketFilter messageFilter = new PacketTypeFilter(Message.class);
			connection.addPacketListener(this, messageFilter);
			
//			ClientThread thread = new ClientThread(connection);
//			thread.run();
			
			new Thread(new ClientThread(connection)).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Object handleMessage(String beanId,String methodName,Object paramters[]){
//		Object returnObj = null;
//		try{
////			Object obj = SpringApplicationContext.getApplicationContext().getBean(beanId);
//			Class[] paramTypes = new Class[paramters.length];
//
//			//init params
//			for(int i=0;i<paramters.length;i++){
//				paramTypes[i]=paramters[i].getClass();
//			}
//			Method m = obj.getClass().getMethod(methodName, paramTypes);
//			returnObj = m.invoke(obj, paramters);
//
//		}catch(Exception e){
//
//		};
//		return returnObj;
        return  null;
		
	}

	
    private class SimpleMessageListner implements MessageListener {
        public void processMessage(Chat chat, Message message) {
        }
    }
	
	public void processPacket(Packet packet) {
		 if (packet instanceof Message) {
	            final Message message = (Message)packet;
	            String jid ="";
	            
	            if(message!=null && message.getProperty("notice")!=null)
				{
	            	if(message.getProperty("notice").equals("mail"))
	            	{
			             System.out.println("模块收到推送消息");
						 message.setBody("系统提示：你有新邮件,请查收");
						 message.setProperty("notice", "邮件");
						 jid = (String)message.getProperty("receives");
						 try {
							final MessageListener messageListner = new SimpleMessageListner();
							final Chat chat = connection.getChatManager().createChat(jid, messageListner);
							chat.sendMessage(message);
						 } catch (Exception e) {
							e.printStackTrace();
						 }
	            	}
				}
	            
	            if(message!=null && message.getProperty("business")!=null)
	            {
	            	System.out.println("模块收到处理消息消息");
	            	if(message.getProperty("business").equals("type1"))
	            	{
	            	   String springBeanId = (String)message.getProperty("springBeanId");
	            	   String methodName = (String)message.getProperty("methodName");
	            	   Object[] obj = (Object[])message.getProperty("paramters");
	            	   handleMessage(springBeanId,methodName,obj);
	            	   System.out.println(obj.length);
	            	}
	            }
	            
		 }
	}//

  }
