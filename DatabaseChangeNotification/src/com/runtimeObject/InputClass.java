package com.runtimeObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class InputClass<T,U,K> {
	
	private T t;
	
	private U u;
	
	private K k;
	
	public InputClass() {
		// TODO Auto-generated constructor stub
	}
	public InputClass(T t,U u) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		this.t=t;
		this.u=u;
		
		Class<?> cls = Class.forName(t.getClass().getName());
		this.t=(T) cls.newInstance();
		
		cls = Class.forName(u.getClass().getName());
		this.u=(U) cls.newInstance();
		// TODO Auto-generated constructor stub
	}
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	
	public void getInputClassDetails() throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, InstantiationException, IllegalAccessException {
		
		

		System.out.println(t );
		System.out.println(t.getClass().getName());
		System.out.println(t.getClass().getSimpleName());
		System.out.println(u );
		System.out.println(u.getClass().getName());
		System.out.println(u.getClass().getSimpleName());
		System.out.println(t.getClass().getTypeName());
		
		Field[] flds = t.getClass().getDeclaredFields();
		System.out.println(flds.length);
		int i=1;
		
		for(Field f : flds) {
			System.out.println("field "+i+" "+f.getType().getName());
			if(f.getType().getName().toString().contains("com.runtimeObject")) {
			//Object o = Class.forName(f.getType().getName());
			//System.out.println();
			 this.k = (K) Class.forName(f.getType().getName().toString()).newInstance();
			}
			i++;
		}
		
		flds = k.getClass().getDeclaredFields();
		for(Field f : flds) {
			System.out.println("field "+i+" "+f.getName());
			i++;
		}
		System.out.println(flds.length);

	}
	
	
	public String[] getallFields(Object o) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String[] flds =null;
		
		Field[] fs = o.getClass().getDeclaredFields();
		int i=1;
		
		for(Field f : fs) {
			System.out.println("field "+i+" "+f.getType().getName());
			if(f.getType().getName().toString().contains("com.runtimeObject")) {
			 this.k = (K) Class.forName(f.getType().getName().toString()).newInstance();
			 String[] flds2= getallFields(k);
			 flds = ArrayUtils.addAll(flds, flds2);
			}
			i++;
		}
		
		
		return null;
	}
		
}
	

