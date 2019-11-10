package com.sheshuDCN;

import java.util.HashMap;

public class DCNObject<T,U> {
	
	private T t;
	private U u;
	
	private HashMap<Object,Object> keyMap=null;
	private HashMap<Object,Object> valMap=null;
	
	DCNObject(T t,U u,HashMap<Object,Object> k,HashMap<Object,Object> v){
		this.t=t;
		this.u=u;
		this.keyMap=k;
		this.valMap=v;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public U getU() {
		return u;
	}

	public void setU(U u) {
		this.u = u;
	}

	public HashMap<Object, Object> getKeyMap() {
		return keyMap;
	}

	public void setKeyMap(HashMap<Object, Object> keyMap) {
		this.keyMap = keyMap;
	}

	public HashMap<Object, Object> getValMap() {
		return valMap;
	}

	public void setValMap(HashMap<Object, Object> valMap) {
		this.valMap = valMap;
	}
}
