package com.baosight.sparkweb.service;

public class SparkServiceXmlFactory {
	
	private  static String serviceClass = null;
	
	
	public void setServiceClass(String classname)
	{
		this.serviceClass = classname;
	}
	
	public static SparkXmlServiceInterface getService()
	{
		try{
			Class<?> imple = Class.forName(serviceClass);
			return (SparkXmlServiceInterface) imple.newInstance();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
