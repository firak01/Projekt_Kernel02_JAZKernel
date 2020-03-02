package basic.zBasic.util.abstractList;

import java.util.HashMap;
import java.util.Iterator;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class HashMapIterableKeyZZZ<X,T>  extends ObjectZZZ implements Iterable<T>{
	private HashMap<X,T> hmOriginal=null;
	private HashMapIndexedZZZ<Integer,X> hmIndexedKey=null;//also integer als Sortierkrierium , X als originaler Key
	
	public void put(X arg0, T arg1)throws ExceptionZZZ {	
		main:{
			if(arg0==null) {
				ExceptionZZZ ez = new ExceptionZZZ("null key element. Index can not be set.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
				  //doesn�t work. Only works when > JDK 1.4
				  //Exception e = new Exception();
				  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
				  throw ez;
			}
			
			HashMapIndexedZZZ<Integer,X> hmIndexed = this.getHashMapIndexedKey();
			hmIndexed.put(arg0);
			
			HashMap<X,T> hmOriginal = this.getHashMap();
			hmOriginal.put(arg0, arg1);
									
		}//end main:			
	}

	public X getKeyFirst() throws ExceptionZZZ {
		X objReturn = null;
		main:{
			
			HashMapIndexedZZZ<Integer,X> hmIndex = this.getHashMapIndexedKey();
			objReturn = (X) hmIndex.getKeyFirst();
			
		}//end main:
		return objReturn;
	}
	public T getValueFirst() throws ExceptionZZZ {
		T objReturn = null;
		main:{
			X objKey = this.getKeyFirst();
			if(objKey==null) break main;
			
			HashMap<X,T>hmOriginal =this.getHashMap();
			objReturn = hmOriginal.get(objKey);
			
		}//end main:
		return objReturn;
	}
	
	public X getKeyLast()  throws ExceptionZZZ {
		X objReturn = null;
		main:{
			
			HashMapIndexedZZZ<Integer,X> hmIndex = this.getHashMapIndexedKey();
			objReturn = (X) hmIndex.getKeyLast();
			
		}//end main:
		return objReturn;
	}
	public T getValueLast() throws ExceptionZZZ {
		T objReturn = null;
		main:{
			X objKey = this.getKeyLast();
			if(objKey==null) break main;
			
			HashMap<X,T>hmOriginal =this.getHashMap();
			objReturn = hmOriginal.get(objKey);
			
		}//end main:
		return objReturn;
	}
	public X getKeyNext()  throws ExceptionZZZ {
		X objReturn = null;
		main:{
			
			HashMapIndexedZZZ<Integer,X> hmIndex = this.getHashMapIndexedKey();
			objReturn = (X) hmIndex.getKeyNext();
			
		}//end main:
		return objReturn;
	}
	public T getValueNext() throws ExceptionZZZ {
		T objReturn = null;
		main:{
			X objKey = this.getKeyNext();
			if(objKey==null) break main;
			
			HashMap<X,T>hmOriginal =this.getHashMap();
			objReturn = hmOriginal.get(objKey);
			
		}//end main:
		return objReturn;
	}
	
	public T getValue(int iIndex) throws ExceptionZZZ {
		T objReturn = null;
		main:{
			Integer intIndex = new Integer(iIndex);
			objReturn = this.getValue(intIndex);
			
		}//end main;
		return objReturn;
	}
	public T getValue(Integer intIndex) throws ExceptionZZZ {
		T objReturn = null;
		main:{
			HashMapIndexedZZZ<Integer,X> hmIndex = this.getHashMapIndexedKey();
			X objKey = (X) hmIndex.getValue(intIndex);
			if(objKey==null)break main;
			
			HashMap<X,T> hmOriginal = this.getHashMap();
			objReturn = (T) hmOriginal.get(objKey);
		}//end main;
		return objReturn;
	}
	
	
	//### GETTER / SETTER
	public HashMap<X,T>getHashMap() {
		if(this.hmOriginal==null) {
			this.hmOriginal=new HashMap<X,T>();
		}
		return this.hmOriginal;
	}
	/** Ist private, weil es von aussen nie gesetzt werden darf, ohne die IndexWerte auch anzupassen.
	 * @param hmOriginal
	 * @author Fritz Lindhauer, 02.03.2020, 08:03:50
	 */
	private void setHashMap(HashMap<X,T> hmOriginal) {
		this.hmOriginal=hmOriginal;
	}
	
	public HashMapIndexedZZZ<Integer,X>getHashMapIndexedKey() throws ExceptionZZZ{
		if(this.hmIndexedKey==null) {
			this.hmIndexedKey=new HashMapIndexedZZZ<Integer,X>();			
		}
		return this.hmIndexedKey;
	}
	private void setHashMapIndexedKey(HashMapIndexedZZZ<Integer,X>hmIndexedKey) {
		this.hmIndexedKey=hmIndexedKey;
	}
	
	//###################
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
