package com.newDCN;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class readDCNObject<T, K> {

	private T t;

	private K k;

	ArrayList<String> mflds = new ArrayList<String>();
	ArrayList<String> oflds = new ArrayList<String>();
	ArrayList<Class<?>> vclss = new ArrayList<Class<?>>();

	ArrayList<Object> mobjts = new ArrayList<Object>();

	String tree = "";

	public readDCNObject(T t) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		this.t = t;

		//System.out.println(t.getClass().getName());
		if (!isJavaLang(t)) {
			Class<?> cls = Class.forName(t.getClass().getName());
			this.t = (T) cls.newInstance();
		}

		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> getallFields(Object o, ArrayList<String> flds, StringBuffer tree)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArrayList<String> aflds = new ArrayList<String>();
		for (String str : flds) {
			aflds.add(str);
		}
		if (o.getClass().getSimpleName().equals("String") || (isJavaLang(o.getClass()))) {
			aflds.add(o.getClass().getSimpleName());
		} else {
			tree.append(o.getClass().getSimpleName()+".");
			Field[] fs = o.getClass().getDeclaredFields();
			String pckg = o.getClass().getPackage().getName();
			for (Field f : fs) {
				if (f.getType().getName().toString().contains(pckg)) {
					StringBuffer str = new StringBuffer(tree);
					this.k = (K) Class.forName(f.getType().getName().toString()).newInstance();
					aflds = getallFields(k, aflds, str);
				} else {
					aflds.add(f.getType().getSimpleName() + "#" + tree + f.getName());
				}
			}

		}
		return aflds;
	}

	public HashMap<String, String> getHaspMap(ArrayList<String> colMaps) throws Exception {
		HashMap<String, String> ObjectColumnMap = new HashMap<String, String>();

		ArrayList<String> fields = getallFields(t, oflds, new StringBuffer(""));
		System.out.println(fields.size()+"::::::::::"+colMaps.size());
		if (fields.size() == colMaps.size()) {
			for (int i = 0; i < fields.size(); i++) {
				ObjectColumnMap.put(fields.get(i), colMaps.get(i));
			}
		} else {
			throw new Exception("Object fields count does not match with inputs counts ");
		}

		for (Entry<String, String> entry : ObjectColumnMap.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue();
			System.out.println("key: " + key + " value: " + value);
		}
		return ObjectColumnMap;
	}

	public static boolean isWrapperType(Object clazz) {

		return clazz.getClass().equals(Boolean.class) || clazz.getClass().equals(Integer.class)
				|| clazz.getClass().equals(Character.class) || clazz.getClass().equals(Byte.class)
				|| clazz.getClass().equals(Short.class) || clazz.getClass().equals(Double.class)
				|| clazz.getClass().equals(Long.class) || clazz.getClass().equals(Float.class);
	}

	public ArrayList<String> getallFields()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
		ArrayList<String> flds = new ArrayList<String>();
		flds = getallFields(t, flds, new StringBuffer(""));
		/* for(String s : flds) { System.out.println(s); } */
		return flds;
	}
	
	public static boolean isJavaLang(Object check) {
	    if (check == null)// lets say that null comes from JRE
	        return true;

	    return isJavaLang(check.getClass());
	}

	public static boolean isJavaLang(Class<?> check) {

	    Package p = check.getPackage();
	    //System.out.println(check.getPackage());
	    if (p == null) // default package is package for users classes
	        return true;

	    String title = p.getImplementationTitle();
	    if (title == null)// no title -> class not from Oracle
	        return false;

	    //System.out.println(p.getImplementationVendor());
	    //System.out.println(p.getImplementationTitle());
	    return title.equals("Java Runtime Environment");

	    
	}

}
