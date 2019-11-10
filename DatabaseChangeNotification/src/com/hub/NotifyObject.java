
package com.hub;


public class NotifyObject {
	
	private Object mapValue=null;
	private String rowId = null;
	
	public NotifyObject(Object MapValue,String RowID) {
		this.mapValue = MapValue;
		this.rowId=RowID;
	}

	public Object getMapValue() {
		return mapValue;
	}

	public void setMapValue(Object mapValue) {
		this.mapValue = mapValue;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

}
