package com.newDCN;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class initiationMain {
	 
	public static Map<Employee, Student> cache = new ConcurrentHashMap<Employee, Student>();
	
	public static void main(String[] args) throws Exception {
		
		Student s =new Student();
		Employee e = new Employee();
		Department d =new Department();
		String sr ="Ssdfsdf";
		
		HashMap<Object,String> km =new HashMap<Object,String>();
		HashMap<Object,String> vm =new HashMap<Object,String>();

		InputClass ic = new InputClass(s,s);
		
		int a = 0;
		readDCNObject rdcnobj =new readDCNObject(s);

		ArrayList<String> fields=rdcnobj.getallFields();
		
		for(String st : fields ) { System.out.println(st); }
		 
		
		ArrayList<String> inputs =new ArrayList<String>();
		
		inputs.add("lsdkfhkajsd");
		inputs.add("lsdkfhksdfajsd");
		
		/*
		 * inputs.add("lsdksdffhkajsd"); inputs.add("lsdkfsdfsshkajsd");
		 * inputs.add("lsdkfhdfsdkajsd"); inputs.add("lsdkfhkajsd");
		 * inputs.add("lsdkfhksdfajsd"); inputs.add("lsdksdffhkajsd");
		 * inputs.add("lsdkfsdfsshkajsd"); inputs.add("lsdkfhdfsdkajsd");
		 */
		
		//inputs.add("sdfsdf");
		
		
		//rdcnobj.getallFields();
		rdcnobj.getHaspMap(inputs);

		
		

		
		
	//ic.getInputClassDetails();
		
	}

	
	
	

}
