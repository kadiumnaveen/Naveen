
package sfmshub.dbChangeNotification;


public class NotifyObject {
	
	private Object mapValue=null;
	private String rowId = null;
	
	public NotifyObject() {
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
