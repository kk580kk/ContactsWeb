package com.baosight.sparkweb.message;//package com.baosight.sparkweb.message;

/**
 * 
 */

/**
 * @author boernuo
 *
 */
public interface IMSendMessage {
	 public static final String SERVER_NAME = "localhost";
	 public static final int SERVER_PORT = 5222; 
	 public static final String modelJID= IMReceiveMessage.cid+"@localhost";
	/**
	 * 
	 * @param springBeanId
	 * @param methName
	 * @param msg
	 * @return LinkedHashMap<String, String> content
	 */
	public boolean sender(String springBeanId, String methName, Object[] paramters);
	
	
	/**
	 * -->openfire-->spark
	 * xml format
	 * @param receiver
	 * @param msg
	 * @return
	 */
	public boolean sender(String receiver, String msg);
	
	/**
	 * -->openfire-->IMReceiveMessage-->spark
	 * @param receiver
	 * @param type
	 * @param count
	 * @return
	 */
	public boolean sender(String receiver, String type, int count);
	
}
