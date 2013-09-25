package com.baosight.sparkweb.message;//package com.baosight.efmpx.im;

import org.jivesoftware.smack.XMPPConnection;

public class ClientThread implements Runnable
{
	private XMPPConnection connection;

	public ClientThread(XMPPConnection conn)
	{
		connection = new XMPPConnection("10.25.36.197");
		connection = conn;
	}

	public void run()
	{
		while (true)
		{
			try {
				System.out.println("模块正在运行...");
				Thread.sleep(30000);
				if(!connection.isConnected())
				   connection.connect();
			} catch (Exception e) {
			
				e.printStackTrace();
			}
		}
	}
}
