package com.sheshuDCN;



import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestDatabaseChangeMain {

	private static Map<String, Long> IdiCacheData = new ConcurrentHashMap<String, Long>();
	private static Map<String, String> mqdCacheData = new ConcurrentHashMap<String, String>();
	private static Map<String, String> erdCacheData = new ConcurrentHashMap<String, String>();
	private static Map<String, String> endCacheData = new ConcurrentHashMap<String, String>();
	private static Map<String, String> CacheData = new ConcurrentHashMap<String, String>();

	// private List<String> bankList= Arrays.asList("IDI","MQD");

	public static void main(String[] args) {

		HashMap<String, TableData> allTab = new HashMap<String, TableData>();
		
		Map<String, String> keyvalMetaDataidi=new HashMap<String, String>();		
		keyvalMetaDataidi.put("KEY_COLMS_IDI_IFSC_DIR_INFO", "IDI_IFSC_CODE");		
		keyvalMetaDataidi.put("VAL_COLMS_IDI_IFSC_DIR_INFO", "IDI_TRNG_FLG");
		
		
		/*Map<String, Object> keyvalClsMetaDataidi=new HashMap<String, Object>();		
		keyvalClsMetaDataidi.put("KEY_CLS_IDI_IFSC_DIR_INFO", String.class);		
		keyvalClsMetaDataidi.put("VAL_CLS_IDI_IFSC_DIR_INFO", Long.class);*/
		
		
		
		TableData<String,Long,String,String,String,String> tDataidi=new TableData<String,Long,String,String,String,String>(IdiCacheData,keyvalMetaDataidi,null,null);
		allTab.put("IDI_IFSC_DIR_INFO", tDataidi);
		
		
		/*Map<String, String> keyvalMetaDatamqd=new HashMap<String, String>();
		keyvalMetaDatamqd.put("KEY_COLNAME_MQD", "COLUM1");		
		keyvalMetaDatamqd.put("VAL_COLNAME_MQD", "COLUM2");	
		
		
		TableData<String,String,String,String> tDatamqd=new TableData<String,String,String,String>(mqdCacheData,keyvalMetaDatamqd);		
		allTab.put("MQD_MQ_QUEUE_DEF", tDatamqd);*/

		DatabaseChangeConnectionDtls conDetls = new DatabaseChangeConnectionDtls("sfmsbr", "sfms",
				"jdbc:oracle:thin:@172.16.105.8:1521:SFMS");
		TestDatabaseChangeEvent testDataChangeevent = new TestDatabaseChangeEvent(allTab, conDetls);

		testDataChangeevent.enableDatabaseChangeEvents();

	}

}
