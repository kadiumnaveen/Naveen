package com.sheshuDCN;



import java.awt.Window.Type;
import java.util.Map;

public class TableData<T,K,P,D,E,F> {
	
	private Map<T,K> tcacheDataData=null;
	
	private Map<P,D> tkeyvalMetaData=null;
	
	private E keySeparator=null;

	private F valSeparator=null;
	
	private String query=null;
	
	private String keyStr[]=null;
	
	private String valStr[]=null;
	
	public TableData(Map<T,K> tcacheDataData,Map<P,D> tkeyvalMetaData,E keySeparator,F valSeparator) {
		super();
		this.tcacheDataData = tcacheDataData;
		this.tkeyvalMetaData = tkeyvalMetaData;
		this.keySeparator = keySeparator;
		this.valSeparator = valSeparator;
	}

	public Map<T,K>  getTcacheDataData() {
		return tcacheDataData;
	}

	public void setTcacheDataData(Map<T,K> tcacheDataData) {
		this.tcacheDataData = tcacheDataData;
	}

	public Map<P,D> getTkeyvalMetaData() {
		return tkeyvalMetaData;
	}

	public void setTkeyvalMetaData(Map<P,D> tkeyvalMetaData) {
		this.tkeyvalMetaData = tkeyvalMetaData;
	}

	public E getKeySeparator() {
		return keySeparator;
	}

	public void setKeySeparator(E keySeparator) {
		this.keySeparator = keySeparator;
	}

	public F getValSeparator() {
		return valSeparator;
	}

	public void setValSeparator(F valSeparator) {
		this.valSeparator = valSeparator;
	}

	public String[] getKeyStr() {
		return keyStr;
	}

	public String[] getValStr() {
		return valStr;
	}
	
	
	public String getQuery(String tName) {
		
		D keyColName=tkeyvalMetaData.get("KEY_COLMS_"+tName);
		
		D valColName =tkeyvalMetaData.get("VAL_COLMS_"+tName);
		
		StringBuffer query = new StringBuffer("Select ");

		keyStr = keyColName.toString().split("#");

		for (int i = 0; i < keyStr.length; i++) {

			query.append(keyStr[i] + ",");

		}
		
		valStr = valColName.toString().split("#");

		for (int i = 0; i < valStr.length; i++) {

			query.append(valStr[i]);
			
			 if(i<valStr.length-1) 
				 query.append(",");
		}
		return query.toString();
	}

	

	
	



	
	
	
	
	

}
