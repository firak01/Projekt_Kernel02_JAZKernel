package basic.zBasic.util.abstractList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class HashMapMultiIndexedZZZ<T,X,Z> extends HashMapMultiZZZ<T,X,Z>{
	protected int iIndexLast = -1;
	protected int iIndexHigh = -1;
	
	public void putAsHigh(Object arg0, Object arg1) throws ExceptionZZZ {	
		int iIndexHigh = this.increaseIndexHigh();
		Integer intKey = new Integer(iIndexHigh);
		this.put(intKey, arg0, arg1);			
	}
	
	public void putAsLast(Object arg0, Object arg1) throws ExceptionZZZ {	
		int iIndexLast = this.increaseIndexLast();
		Integer intKey = new Integer(iIndexLast);
		this.put(intKey, arg0, arg1);			
	}
	
	public Object put(Integer intAliasOuter, Object objAliasInner, Object objToSet) throws ExceptionZZZ{
		//Merke: Aus irgendeinem Grund wird bei HashMap.put auch ein Ojekt zurückgegebe.
		//       Das hier übernommen (auch damit einfacher Überschrieben werden kann)
		Object objReturn = null;
		
		main:{
		//Guard Klauseln
		if(intAliasOuter == null){
			ExceptionZZZ ez = new ExceptionZZZ("intAliasOuter", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(objAliasInner==null){
			ExceptionZZZ ez = new ExceptionZZZ("objAliasInner", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
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
				hmInner.remove(objAliasInner);
			}
			break main;			
		}
		
		//Den neuen Wert in die innere gespeicherte HashMap ablegen
		objReturn = hmInner.put(objAliasInner, objToSet);
		
		
		}//END main:
		return objReturn;
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	public Object put(Object objKey, Object objValue){
		//Merke: Wenn ich das nicht ueberschreibe, wird die Methode von HashMapMultiZZZ genommen und alle Einträge werden in die äussere HashMap geschriben.
		Object objReturn = null;
		main:{
			int iIndexLast = this.increaseIndexLast();
			Integer intKey = new Integer(iIndexLast);
			try {
				return this.put(intKey, objKey, objValue);
			} catch (ExceptionZZZ e) {
				e.printStackTrace();
				System.out.println(e.getDetailAllLast());
			}		
		}//end main:		
		return objReturn;
	}
	
	public Object put(String sKey, String sValue) throws ExceptionZZZ{
		int iIndexLast = this.increaseIndexLast();
		Integer intKey = new Integer(iIndexLast);
		return this.put(intKey, sKey, sValue);				
	}
	
	
	public Object put(Integer intAliasOuter, String sAliasInner, Object objToSet) throws ExceptionZZZ{
		//Merke: Aus irgendeinem Grund wird bei HashMap.put auch ein Ojekt zurückgegebe.
		//       Das hier übernommen (auch damit einfacher Überschrieben werden kann)
		Object objReturn = null;
		
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
		objReturn = hmInner.put(sAliasInner, objToSet);
		
		}//END main:
		return objReturn;
	}
	
	
	//++++ KEY ++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++
	public Object getKey() throws ExceptionZZZ{
		return this.getKeyLast();//!!! Konvention
	}
	/** Gib den Wert vom zuletzt verwendeten Indexeintrag zurueck.
	 *  Wenn nicht nach einem anderen Index gesucht worden ist, sollte das mit getHigh() uebereinstimmen.
	 *  Hier wird durch die keys der inneren HashMap iteriert und der letze Eintrag als Einzelwert zurueckgegeben.
	 *  Merke: Das funktioniert 1:1 wenn es sich nur um 1 Eintrag in der inneren HashMap handelt.
	 *        
	 * @param sAliasInner
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.05.2024, 08:15:11
	 */
	public Object getKeyLast() throws ExceptionZZZ{
		Object objReturn = null;
		
		main:{
			int iIndexUsed = this.getIndexLast();
			
			//Hole die aeussere HashMap und dann daraus ueber den Index die Innere HashMap
			HashMap hmOuter = this.getOuterHashMap();
			HashMap hmInner = (HashMap) hmOuter.get(iIndexUsed);
			objReturn = HashMapZZZ.getKeyLast(hmInner);
			
		}//end main:
		return objReturn;
	}
	
	/** Gib den Wert vom Hoechsten Indexeintrag zurueck, also im Grunde den zuletzt neu hinzugefuegten.
	 * 	Hier wird durch die keys der inneren HashMap iteriert und der letze Eintrag als Einzelwert zurueckgegeben.
	 *  Merke: Das funktioniert 1:1 wenn es sich nur um 1 Eintrag in der inneren HashMap handelt.
	 * @param sAliasInner
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.05.2024, 08:14:07
	 */
	public Object getKeyHigh() throws ExceptionZZZ{
		Object objReturn = null;
		
		main:{
			int iIndexUsed = this.getIndexLast();
			
			//Hole die aeussere HashMap und dann daraus ueber den Index die Innere HashMap
			HashMap hmOuter = this.getOuterHashMap();
			HashMap hmInner = (HashMap) hmOuter.get(iIndexUsed);
			objReturn = HashMapZZZ.getKeyLast(hmInner);
			
		}//end main:
		return objReturn;
	}
	
	
	public Object getKeyLastWithIndex(int iIndex) throws ExceptionZZZ{
		Object objReturn = null;
		main:{
			if(iIndex < 0 ) break main;
			
			int iHmIndexUsed = this.getIndexLast();
			HashMap hm = this.getInnerHashMap(iHmIndexUsed);
			
			objReturn = HashMapZZZ.getKeyByIndex(hm, iIndex);
		}//end main:
		return objReturn;
	}
	
	public Object getKey(Integer intIndex, int iIndex) throws ExceptionZZZ{
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
			
			if(iIndex<0) break main;
			
			HashMap hmOuter = this.getOuterHashMap();
			if(!hmOuter.containsKey(intIndex)) break main;
			
			HashMap hmInner = (HashMap) hmOuter.get(intIndex);
			objReturn = HashMapZZZ.getKeyByIndex(hmInner, iIndex);
		
		}//END main:
		return objReturn;
		
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//++++ HOLE DIE HASCHMAPS ++++++++++++++++++++++++++++++
	public HashMap getLast() throws ExceptionZZZ{
		int iIndexUsed = this.getIndexLast();
		return this.getInnerHashMap(iIndexUsed);
	}
	
	public HashMap getHigh() throws ExceptionZZZ{
		int iIndexUsed = this.getIndexHigh();
		return this.getInnerHashMap(iIndexUsed);
	}

	public HashMap get() throws ExceptionZZZ{
		return this.getLast();//!!! Konvention
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++ HIER DIREKT AUS DER INNEREN HASHMAP (letzer / hoechster Index der aeussern HashMap) etwas holen ++++++++++++++++++++++++++++++++
	public Object getEntry() throws ExceptionZZZ{
		return this.getEntryLast();//!!! Konvention
	}
	
	public Object getEntry(String sAliasInner) throws ExceptionZZZ{
		return this.getEntryLast(sAliasInner); //!!! Konvention
	}
	
	public Object getEntryWithIndex(int iIndex) throws ExceptionZZZ{
		return this.getEntryLastWithIndex(iIndex); //!!! Konvention
	}
	
	//++++++++++
	public Object getEntryLast() throws ExceptionZZZ{
		Object objReturn = null;
		main:{
			int iIndexUsed = this.getIndexLast();
			objReturn = this.getEntryLast(iIndexUsed);			
		}//end main:
		return objReturn;
	}
	
	
	/** Gib den Wert vom zuletzt verwendeten Indexeintrag zurueck.
	 *  Wenn nicht nach einem anderen Index gesucht worden ist, sollte das mit getHigh() uebereinstimmen.
	 * @param sAliasInner
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.05.2024, 08:15:11
	 */
	public Object getEntryLast(String sAliasInner) throws ExceptionZZZ{
		int iIndexUsed = this.getIndexLast();
		return this.getEntry(iIndexUsed, sAliasInner);
	}
	
	public Object getEntryLastWithIndex(int iIndex) throws ExceptionZZZ{
		Object objReturn = null;
		main:{
			if(iIndex < 0 ) break main;
			
			int iHmIndexUsed = this.getIndexLast();
			return this.getEntry(iHmIndexUsed, iIndex);
		}//end main:
		return objReturn;
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public Object getEntryHigh() throws ExceptionZZZ{
		Object objReturn = null;
		main:{
			objReturn = this.getEntryHighLast(); //!!!Konvention	
		}//end main:
		return objReturn;
	}
	
	/** Gib den Wert vom Hoechsten Indexeintrag zurueck, also im Grunde den zuletzt neu hinzugefuegten.
	 * @param sAliasInner
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.05.2024, 08:14:07
	 */
	public Object getEntryHigh(String sAliasInner) throws ExceptionZZZ{
		int iIndexUsed = this.getIndexHigh();
		return this.getEntry(iIndexUsed, sAliasInner);
	}

	public Object getEntryHighWithIndex(int iIndex) throws ExceptionZZZ{
		Object objReturn = null;
		main:{
			if(iIndex < 0 ) break main;
			
			int iHmIndexUsed = this.getIndexHigh();
			Integer intHmIndexUsed = new Integer(iHmIndexUsed);
			objReturn = this.getEntry(intHmIndexUsed, iIndex);
		}//end main:
		return objReturn;
	}
	
	public Object getEntryHighLast() throws ExceptionZZZ{
		Object objReturn = null;
		main:{
			int iHmIndexUsed = this.getIndexHigh();
			Integer intHmIndexUsed = new Integer(iHmIndexUsed);
			objReturn = this.getEntryLast(intHmIndexUsed);
		}//end main:
		return objReturn;
	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++ HIER AUS DER AEUSSERN HASHMAP ETWAS HOLEN UND DANN IN DIE INNERE HASHMAP
	public Object getEntry(Integer intIndex, String sAliasInner) throws ExceptionZZZ{
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
	
	public Object getEntry(Integer intIndex, int iIndex) throws ExceptionZZZ{
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
			
			if(iIndex<0) break main;
			
			HashMap hmOuter = this.getOuterHashMap();
			if(!hmOuter.containsKey(intIndex)) break main;
			
			HashMap hmInner = (HashMap) hmOuter.get(intIndex);
			objReturn = HashMapZZZ.getEntryByIndex(hmInner, iIndex);
		
		}//END main:
		return objReturn;
		
	}
	
	public Object getEntryLast(Integer intIndex) throws ExceptionZZZ{
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

			HashMap hmOuter = this.getOuterHashMap();
			if(!hmOuter.containsKey(intIndex)) break main;
			
			HashMap hmInner = (HashMap) hmOuter.get(intIndex);
			objReturn = HashMapZZZ.getEntryLast(hmInner);
		
		}//END main:
		return objReturn;
		
	}
	
	public Object getKeyLast(Integer intIndex) throws ExceptionZZZ{
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

			HashMap hmOuter = this.getOuterHashMap();
			if(!hmOuter.containsKey(intIndex)) break main;
			
			HashMap hmInner = (HashMap) hmOuter.get(intIndex);
			objReturn = HashMapZZZ.getKeyLast(hmInner);
		
		}//END main:
		return objReturn;
		
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	
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
	
	private int increaseIndexHigh() {
		int iIndexHigh=this.getIndexHigh() + 1;
		this.setIndexHigh(iIndexHigh);
		return this.getIndexHigh();
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
	
	/** eine passende HashMap wird hintenangehaengt
	 * @param hmToAdd
	 * @author Fritz Lindhauer, 10.05.2024, 15:30:13
	 * @throws ExceptionZZZ 
	 */
	public void add(HashMapMultiIndexedZZZ<T,X,Z> hmToAdd) throws ExceptionZZZ {
		main:{
			if(hmToAdd==null)break main;
			
			int iIndexHighToAdd = hmToAdd.getIndexHigh();
			int iInnerIndexToAdd=0;
			for(int iIndex=0; iIndex<=iIndexHighToAdd; iIndex++) {
				Integer intIndex = new Integer(iIndex);			
							
				Object objKeyToAdd = hmToAdd.getKey(intIndex, iInnerIndexToAdd);
				if(objKeyToAdd==null) break;
				
				Object objToAdd = hmToAdd.getEntry(intIndex, iInnerIndexToAdd);
				
				this.putAsHigh(objKeyToAdd, objToAdd);//Merke: Das ruft die eigene MultiIndexed - Methode auf (um den Index zu setzen)
				
			}
		}//end main:		
	}
	
}
