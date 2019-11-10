package com.sheshuDCN;



public class Testing {
	
	
	
	
	public static void main(String[] args) {
		
		String keyActColName="IDI_IFSC_CODE";
		
		String keyStr[]=keyActColName.toString().split("#");
		
		StringBuffer query=new StringBuffer("Select ");
		
		System.out.println(keyStr.length);
		
		for (int i = 0; i < keyStr.length; i++) {
			
			query.append(keyStr[i]);
			if(i<keyStr.length-1)
				query.append(",");
			
		}
		
		System.out.println(query);
		
		String a="java.lang.String";
		Class cls;
		try {
			cls = Class.forName(a);
			cls.newInstance().getClass().gn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
         
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
