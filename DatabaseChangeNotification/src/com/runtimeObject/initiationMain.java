package com.runtimeObject;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.runtimeObject.Department;
import com.runtimeObject.Employee;
import com.runtimeObject.InputClass;

public class initiationMain {
	
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Department d=new Department();
		Employee e = new Employee();
	private static Map<String, Long> IdiCacheData = new ConcurrentHashMap<String, Long>();
	/*	String s=new String("abc");
	s.getBytes();
	System.out.println(s.toString()+"s val");*/
		InputClass ic = new InputClass(d);
		
	ic.getInputClassDetails();
		
	}

	
	
	

}
