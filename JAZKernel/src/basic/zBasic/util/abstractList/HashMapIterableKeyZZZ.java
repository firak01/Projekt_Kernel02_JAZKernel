package basic.zBasic.util.abstractList;

import java.util.HashMap;
import java.util.Iterator;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class HashMapIterableKeyZZZ<T,X>  extends ObjectZZZ implements Iterable<T>{
	private HashMap<T,X> hmOriginal=null;
	private HashMapIndexedZZZ<Integer,T> hmIndexedKey=null;//also integer als Sortierkrierium , X als originaler Key
	
	public void put(T arg0, X arg1)throws ExceptionZZZ {	
		main:{
			if(arg0==null) {
				ExceptionZZZ ez = new ExceptionZZZ("null key element. Index can not be set.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
				  //doesn�t work. Only works when > JDK 1.4
				  //Exception e = new Exception();
				  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
				  throw ez;
			}
			
			HashMapIndexedZZZ<Integer,T> hmIndexed = this.getHashMapIndexedKey();
			hmIndexed.put(arg0);
			
			HashMap<T,X> hmOriginal = this.getHashMap();
			hmOriginal.put(arg0, arg1);
									
		}//end main:			
	}

	public T getKeyFirst() throws ExceptionZZZ {
		T objReturn = null;
		main:{
			
			HashMapIndexedZZZ<Integer,T> hmIndex = this.getHashMapIndexedKey();
			objReturn = (T) hmIndex.getKeyFirst();
			
		}//end main:
		return objReturn;
	}
	public X getValueFirst() throws ExceptionZZZ {
		X objReturn = null;
		main:{
			T objKey = this.getKeyFirst();
			if(objKey==null) break main;
			
			HashMap<T,X>hmOriginal =this.getHashMap();
			objReturn = hmOriginal.get(objKey);
			
		}//end main:
		return objReturn;
	}
	
	public T getKeyLast()  throws ExceptionZZZ {
		T objReturn = null;
		main:{
			
			HashMapIndexedZZZ<Integer,T> hmIndex = this.getHashMapIndexedKey();
			objReturn = (T) hmIndex.getKeyLast();
			
		}//end main:
		return objReturn;
	}
	public X getValueLast() throws ExceptionZZZ {
		X objReturn = null;
		main:{
			T objKey = this.getKeyLast();
			if(objKey==null) break main;
			
			HashMap<T,X>hmOriginal =this.getHashMap();
			objReturn = hmOriginal.get(objKey);
			
		}//end main:
		return objReturn;
	}
	public T getKeyNext()  throws ExceptionZZZ {
		T objReturn = null;
		main:{
			
			HashMapIndexedZZZ<Integer,T> hmIndex = this.getHashMapIndexedKey();
			objReturn = (T) hmIndex.getKeyNext();
			
		}//end main:
		return objReturn;
	}
	public X getValueNext() throws ExceptionZZZ {
		X objReturn = null;
		main:{
			T objKey = this.getKeyNext();
			if(objKey==null) break main;
			
			HashMap<T,X>hmOriginal =this.getHashMap();
			objReturn = hmOriginal.get(objKey);
			
		}//end main:
		return objReturn;
	}
	
	public X getValue(int iIndex) throws ExceptionZZZ {
		X objReturn = null;
		main:{
			Integer intIndex = new Integer(iIndex);
			objReturn = this.getValue(intIndex);
			
		}//end main;
		return objReturn;
	}
	public X getValue(Integer intIndex) throws ExceptionZZZ {
		X objReturn = null;
		main:{
			HashMapIndexedZZZ<Integer,T> hmIndex = this.getHashMapIndexedKey();
			T objKey = (T) hmIndex.getValue(intIndex);
			if(objKey==null)break main;
			
			HashMap<T,X> hmOriginal = this.getHashMap();
			objReturn = (X) hmOriginal.get(objKey);
		}//end main;
		return objReturn;
	}
	
	
	//### GETTER / SETTER
	public HashMap<T,X>getHashMap() {
		if(this.hmOriginal==null) {
			this.hmOriginal=new HashMap<T,X>();
		}
		return this.hmOriginal;
	}
	/** Ist private, weil es von aussen nie gesetzt werden darf, ohne die IndexWerte auch anzupassen.
	 * @param hmOriginal
	 * @author Fritz Lindhauer, 02.03.2020, 08:03:50
	 */
	private void setHashMap(HashMap<T,X> hmOriginal) {
		this.hmOriginal=hmOriginal;
	}
	
	public HashMapIndexedZZZ<Integer,T>getHashMapIndexedKey() throws ExceptionZZZ{
		if(this.hmIndexedKey==null) {
			this.hmIndexedKey=new HashMapIndexedZZZ<Integer,T>();			
		}
		return this.hmIndexedKey;
	}
	private void setHashMapIndexedKey(HashMapIndexedZZZ<Integer,T>hmIndexedKey) {
		this.hmIndexedKey=hmIndexedKey;
	}
	
	//###################
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
