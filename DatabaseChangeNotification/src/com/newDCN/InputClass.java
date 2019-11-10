package com.newDCN;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;

public class InputClass<T, U, K> {

	private T t;

	private U u;

	private K k;

	ArrayList<String> mflds = new ArrayList<String>();
	ArrayList<String> vflds = new ArrayList<String>();
	ArrayList<Class<?>> vclss = new ArrayList<Class<?>>();
	
	ArrayList<Object> mobjts = new ArrayList<Object>();

	static String tree = "";

	public InputClass() {
		// TODO Auto-generated constructor stub
	}

	public InputClass(T t, U u) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		this.t = t;
		this.u = u;

		Class<?> cls = Class.forName(t.getClass().getName());
		this.t = (T) cls.newInstance();

		cls = Class.forName(u.getClass().getName());
		this.u = (U) cls.newInstance();
		// TODO Auto-generated constructor stub
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public void getInputClassDetails() throws Exception {

		/*
		 * System.out.println(t); System.out.println(t.getClass().getName());
		 * System.out.println(t.getClass().getPackage().getName());
		 * System.out.println(t.getClass().getSimpleName()); System.out.println(u);
		 * System.out.println(u.getClass().getName());
		 * System.out.println(u.getClass().getSimpleName());
		 * System.out.println(t.getClass().getTypeName());
		 * 
		 * Field[] flds = t.getClass().getDeclaredFields();
		 * System.out.println(flds.length); int i = 1;
		 * 
		 * Integer e =new Integer(45);
		 * 
		 * System.out.println(e.getClass());
		 * 
		 * for (Field f : flds) { System.out.println("field " + i + " " +
		 * f.getType().getName()); if
		 * (f.getType().getName().toString().contains("com.newDCN")) { // Object o =
		 * Class.forName(f.getType().getName()); // System.out.println(); this.k = (K)
		 * Class.forName(f.getType().getName().toString()).newInstance(); } i++; }
		 * 
		 * flds = k.getClass().getDeclaredFields(); for (Field f : flds) {
		 * System.out.println("field " + i + " " + f.getName()); i++; }
		 * System.out.println(flds.length); mflds = getallFields(t, mflds, new
		 * StringBuffer("")); vflds = getallFields(u, vflds, new StringBuffer(""));
		 * vflds = getallFields(new String("Naveen"), vflds, new StringBuffer(""));
		 * System.out.println("------------------------------------------"); String
		 * x="Naveen"; vflds = getallFields(x, vflds, new StringBuffer("")); int n=1;
		 * vflds = getallFields(n, vflds, new StringBuffer("")); mflds =
		 * getallFields(new ArrayList<>(), mflds, new StringBuffer("")); vclss =
		 * getallFieldsClasses(t, vclss, new StringBuffer(""));
		 * 
		 * mobjts =getallFieldsObjects(t, mobjts,new StringBuffer(""));
		 * 
		 * for(Object ob : mobjts) { System.out.println(ob.getClass().getName()); }
		 */
		
		/*
		 * int n =0; System.out.println(isJavaLang(n));
		 * System.out.println(isJavaLang(new int[12]));
		 * System.out.println(isJavaLang(t)); System.out.println(isJavaLang(new
		 * ArrayList<String>())); System.out.println(isWrapperType(new int[12]));
		 * System.out.println(isWrapperType(n)); String x="sdfsfsf";
		 * System.out.println(isJavaLang(x)); System.out.println(isJavaLang(new
		 * HashMap<String,String>())); ArrayUtils au= null;
		 * System.out.println(isJavaLang(au));
		 */
		ArrayList<String> inputs =new ArrayList<String>();
		inputs.add("lsdkfhkajsd");
		inputs.add("sdfsdf");
		
		String s ="Ssdfsdf";
		createHaspMap(s,inputs);
		
		createHaspMap(t,inputs);
		
		
		
		
		
		
		
	}
	
	public static boolean isWrapperType(Object clazz) {
		 
	    return clazz.getClass().equals(Boolean.class) || 
	        clazz.getClass().equals(Integer.class) ||
	        clazz.getClass().equals(Character.class) ||
	        clazz.getClass().equals(Byte.class) ||
	        clazz.getClass().equals(Short.class) ||
	        clazz.getClass().equals(Double.class) ||
	        clazz.getClass().equals(Long.class) ||
	        clazz.getClass().equals(Float.class);
	}
	
	public static boolean isJavaLang(Object check) {
	    if (check == null)// lets say that null comes from JRE
	        return true;

	    return isJavaLang(check.getClass());
	}

	public static boolean isJavaLang(Class<?> check) {

	    Package p = check.getPackage();
	    System.out.println(check.getPackage());
	    if (p == null) // default package is package for users classes
	        return true;

	    String title = p.getImplementationTitle();
	    if (title == null)// no title -> class not from Oracle
	        return false;

	    System.out.println(p.getImplementationVendor());
	    System.out.println(p.getImplementationTitle());
	    return title.equals("Java Runtime Environment");

	    
	}


	public ArrayList<String> getallFields(Object o, ArrayList<String> flds, StringBuffer tree)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArrayList<String> aflds = new ArrayList<String>();
		for (String str : flds) {
			aflds.add(str);
		}
		//System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{"+tree);
		
		// System.out.println("sldkfjgbslddfjglsdfjil"+o.getClass().getSimpleName());
		if (o.getClass().getSimpleName().equals("String") || (isJavaLang(o.getClass()))) {
			aflds.add(o.getClass().getSimpleName());
			// System.out.println("lakj;gksdjfgn;sldfkjn;kldfj"+o.getClass().getSimpleName()+"#"+o.getClass().getSimpleName());
		} else {
			tree.append(o.getClass().getSimpleName()+".");
			Field[] fs = o.getClass().getDeclaredFields();
			//System.out.println(fs.length);
			String pckg = o.getClass().getPackage().getName();
			//System.out.println(o.getClass().getTypeName() + " " + o.getClass().isPrimitive());
			for (Field f : fs) {
				//System.out.println(tr);
				// System.out.println(""+f.getType().getSimpleName()+"
				// "+f.getType().isPrimitive());
				//StringBuffer tr = tree;
				//System.out.println(":::::::::::::::::::::::");
				//System.out.println(tr.append(f.getName()));
				if (f.getType().getName().toString().contains(pckg)) {
					//System.out.println(o.getClass().getSimpleName());
					//System.out.println("<<<<<<<<<<<<<<<<<<<<<"+tree);
					StringBuffer str = new StringBuffer(tree);
					this.k = (K) Class.forName(f.getType().getName().toString()).newInstance();
					aflds = getallFields(k, aflds, str);
				} else {
					//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>"+tree);
					aflds.add(f.getType().getSimpleName() + "#" + tree + f.getName());
					// System.out.println(f.getType().getSimpleName() + "#" + tree + f.getName());
				}
			}

		}
		return aflds;
	}
	public ArrayList<Class<?>> getallFieldsClasses(Object o,ArrayList<Class<?>> clss,StringBuffer tree) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Field[] fs = o.getClass().getDeclaredFields();
		String pckg =o.getClass().getPackage().getName();
		tree = tree.append(o.getClass().getSimpleName()+".");
		for(Field f : fs) {
			
			if(f.getType().getName().toString().contains(pckg)) {
			 this.k = (K) Class.forName(f.getType().getName().toString()).newInstance();
			 System.out.println(k);
			 getallFieldsClasses(k,clss,tree);
			 
			}else {
				if(f.getType().getName().toString()!="int"&&f.getType().getName().toString()!="char"&&f.getType().getName().toString()!="double"&&f.getType().getName().toString()!="float"&&f.getType().getName().toString()!="long")
				{
				this.k = (K) Class.forName(f.getType().getName()).newInstance();
				clss.add(k.getClass());
				System.out.println(k.getClass()+"  "+f.getType().getName()+" "+tree+f.getName());
			    }else {	
			    	System.out.println(f.getType().getName()+" "+tree+f.getName());	
			    }
				
			}
		}
		
		return clss;
	}

	public ArrayList<Object> getallFieldsObjects(Object o,ArrayList<Object> clss,StringBuffer tree) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Field[] fs = o.getClass().getDeclaredFields();
		String pckg =o.getClass().getPackage().getName();
		tree = tree.append(o.getClass().getSimpleName()+".");
		clss.add(o);
		System.out.println(o);
		System.out.println(clss.size());
		for(Field f : fs) {
			
			if(f.getType().getName().toString().contains(pckg)) {
			 this.k = (K) Class.forName(f.getType().getName().toString()).newInstance();
			 //System.out.println(k);
			 //clss.add(k);
			 //System.out.println(clss.size());
			 getallFieldsObjects(k,clss,tree);
			 System.out.println();
			 
			}else {
				if(!f.getType().isPrimitive()&&f.getType().getName().toString()!="java.lang.String")
				{
				this.k = (K) Class.forName(f.getType().getName()).newInstance();
				//System.out.println(Integer.toHexString(System.identityHashCode(k)));
				
				
				System.out.println(k+"  "+f.getType().getName()+" "+tree+f.getName());
				
			    }else {
			    	 System.out.println(Integer.toHexString(System.identityHashCode(f.getType())));
			    	System.out.println(f.getType());

			    }
				
			}
		}
		return clss;
	}
	
		public Object createObject(Object o,ArrayList<Object> aryObj)
		{
			
			return aryObj;
		
		}
		
		public HashMap<String,String> createHaspMap  (Object o, ArrayList<String> colMaps) throws Exception{
			HashMap<String,String> ObjectColumnMap = new HashMap<String,String>();
 
			ArrayList<String> fields=getallFields(t, vflds, new StringBuffer(""));
			System.out.println(fields.size());
			if(fields.size()==colMaps.size()) {
				for(int i=0; i<fields.size();i++) {
				ObjectColumnMap.put(fields.get(i),colMaps.get(i));
				}
			}else {
				throw new Exception("Object fields count does not match with inputs counts ");
			}
			
			for (Entry<String, String> entry : ObjectColumnMap.entrySet()) 
			{ String key = entry.getKey().toString();
			String value = entry.getValue();
			System.out.println("key: " + key + " value: " + value); 
			}
			return ObjectColumnMap;	
		}
}
