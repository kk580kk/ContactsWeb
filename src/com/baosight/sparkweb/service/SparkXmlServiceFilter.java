package com.baosight.sparkweb.service;

import java.util.Map;

public class SparkXmlServiceFilter {
	
	Map<String,SparkXmlServiceInterface> filterMap;
	
	public void setFilterMap(Map filterMap) {
		this.filterMap = filterMap;
	}
	
	public String doFilter(String type,Map params)
	{
		String outputString = null;
		if(type != null)
		{
			SparkXmlServiceInterface serviceIns= filterMap.get(type.toLowerCase());
			if(serviceIns != null){
				outputString = serviceIns.doService(params);
			}
		}
		return outputString;
	}
}
