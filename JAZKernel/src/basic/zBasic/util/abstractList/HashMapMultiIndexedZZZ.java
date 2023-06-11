package basic.zBasic.util.abstractList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class HashMapMultiIndexedZZZ<T,X,Z> extends HashMapMultiZZZ<T,X,Z>{
	private int iIndexLast = -1;
	private int iIndexHigh = -1;
	
	public void put(String sKey, String sValue) throws ExceptionZZZ{
		int iIndexLast = this.increaseIndexLast();
		Integer intKey = new Integer(iIndexLast);
		this.put(intKey, sKey, sValue);				
	}
	
	public void put(Integer intAliasOuter, String sAliasInner, Object objToSet) throws ExceptionZZZ{
		
		main:{
		//Guard Klauseln
		if(intAliasOuter == null){
			ExceptionZZZ ez = new ExceptionZZZ("intAliasOuter", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(StringZZZ.isEmpty(sAliasInner)){
			ExceptionZZZ ez = new ExceptionZZZ("sAliasInner", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		HashMap hmOuter = this.getOuterHashMap();		
		HashMap hmInner = null;
		if(objToSet!=null){
			//Nun die Innere, gespeicherte HashMap holen				
			if(hmOuter.containsKey(intAliasOuter)){
			 hmInner = (HashMap) hmOuter.get(intAliasOuter); 
			}else{
				hmInner = new HashMap();
				hmOuter.put(intAliasOuter, hmInner);
				
				//indexspezifisch
				this.setIndexLast(intAliasOuter.intValue());
			}
		}else{
//			Merke: Objekt == null bedeutet, es zu entfernen
			if(hmOuter.containsKey(intAliasOuter)){
				hmInner = (HashMap) hmOuter.get(intAliasOuter);
				hmInner.remove(sAliasInner);
			}
			break main;			
		}
		
		//Den neuen Wert in die innere gespeicherte HashMap ablegen
		hmInner.put(sAliasInner, objToSet);
		
		
		}//END main:
	}
	
	public Object get(Integer intIndex, String sAliasInner) throws ExceptionZZZ{
		Object objReturn = null;
		main:{
	//		Guard Klauseln
			if(intIndex==null){
				ExceptionZZZ ez = new ExceptionZZZ("intIndex", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
			if(intIndex.intValue()<=-1){
				ExceptionZZZ ez = new ExceptionZZZ("intIndex", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(StringZZZ.isEmpty(sAliasInner)){
				ExceptionZZZ ez = new ExceptionZZZ("sAliasInner", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			HashMap hmOuter = this.getOuterHashMap();
			if(!hmOuter.containsKey(intIndex)) break main;
			
			HashMap hmInner = (HashMap) hmOuter.get(intIndex);
			if(!hmInner.containsKey(sAliasInner)) break main;
			
			objReturn = hmInner.get(sAliasInner);
		
		}//END main:
		return objReturn;
		
	}
	
	public Iterator getInnerKeySetIterator(Integer intOuterKey){
		Iterator iteratorReturn = null;
		main:{
			if(intOuterKey==null) break main;
			if(!this.getOuterHashMap().containsKey(intOuterKey)){
				break main;
			}
			
			Set<?> set = this.getInnerKeySet(intOuterKey);
			if(set!=null){
				iteratorReturn = set.iterator();
			}
		}//end main:
		return iteratorReturn;
	}
	
	public Set<?> getInnerKeySet(Integer intOuterKey){
		Set<?> setReturn = null;
		main:{
			if(intOuterKey==null) break main;
			if(!this.getOuterHashMap().containsKey(intOuterKey)){
				break main;
			}
						
			HashMap hmOuterReturn = this.getOuterHashMap();
			HashMap hmInnerReturn = (HashMap) hmOuterReturn.get(intOuterKey);
			setReturn = hmInnerReturn.keySet();
		}//end main:
		return setReturn;
	}
	
	//Überschreibe das Einfache leersetzen und nimm die Index-Zurücksetzung mit auf
	public void clear() {
		super.clear();
		this.setIndexLast(-1);
		this.setIndexHigh(-1);
	}
	
	private int increaseIndexLast(){
		this.iIndexLast=this.iIndexLast + 1 ;
		if(this.iIndexHigh<this.iIndexLast){
			this.setIndexHigh(this.iIndexLast);
		}
		return this.iIndexLast;
	}
	
	public int getIndexLast(){
		return this.iIndexLast;
	}
	private void setIndexLast(int iIndex){
		if (iIndex <= -2){
			iIndex = -1;
		}
		if(iIndex>this.getIndexHigh()){
			this.setIndexHigh(iIndex);
		}
		this.iIndexLast = iIndex;
	}
	
	
	public int getIndexHigh(){
		return this.iIndexHigh;
	}
	private void setIndexHigh(int iIndex){
		this.iIndexHigh = iIndex;
	}
}
