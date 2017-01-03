package basic.zBasic.util.abstractList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**Diese Klasse erlaubt das "direktere" Arbeiten mit einer HashMap, die in einer anderen HashMap gespeichert ist.
 *  Dabei wird angenommen, dass die Schlüsselwerte der äusseren HashMap vom Typ String sind.
 *  
 *  Merke: Sinnvoll ist das nur, wenn die MetaInformationen über die Objekte, die in der inneren HashMap gespeichert wurden 
 *             an einer anderen Stelle bekannt sind. Wie beispielsweise in der Klasse DataStoreZZZ.  
 *             
 *  Merke: Es ist dabei nicht möglich mehrere Werte unter dem gleichen Schlüssel abzulegen !!!
 * 
 * TODO: Eine Klasse HashmapCascasdedZZZ wäre auch nicht schlecht. Dabei sollte die Anzahl der Hashmaps beliebig tief verschachtelt sein. 
 * @author lindhaueradmin
 *
 */
public class HashMapMultiZZZ implements IConstantZZZ, Map{
	private HashMap hmOuter=new HashMap();
	
	public HashMapMultiZZZ(){		
	}

	//### Methoden, die "direkt" auf einer gespeicherten "inneren" HashMap arbeiten
	/** Setzt ein Objekt mit den zwei "verschachtelten" Keys.
	 *   Falls einer der Keys leer ist, wird ein Fehler geworfen.
	 *   Falls das Objekt null ist, wird es aus der HashMap entfernt.
	* @param sAliasOuter
	* @param sAliasInner
	* @param objToSet
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 20.11.2006 09:22:29
	 */
	public void put(String sAliasOuter, String sAliasInner, Object objToSet) throws ExceptionZZZ{
		
		main:{
		//Guard Klauseln
		if(StringZZZ.isEmpty(sAliasOuter)){
			ExceptionZZZ ez = new ExceptionZZZ("sAliasOuter", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(StringZZZ.isEmpty(sAliasInner)){
			ExceptionZZZ ez = new ExceptionZZZ("sAliasInner", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		HashMap hmInner = null;
		if(objToSet!=null){
			//Nun die Innere, gespeicherte HashMap holen				
			if(hmOuter.containsKey(sAliasOuter)){
			 hmInner = (HashMap) hmOuter.get(sAliasOuter); 
			}else{
				hmInner = new HashMap();
				hmOuter.put(sAliasOuter, hmInner);
			}
		}else{
//			Merke: Objekt == null bedeutet, es zu entfernen
			if(hmOuter.containsKey(sAliasOuter)){
				hmInner = (HashMap) hmOuter.get(sAliasOuter);
				hmInner.remove(sAliasInner);
			}
			break main;			
		}
		
		//Den neuen Wert in die innere gespeicherte HashMap ablegen
		hmInner.put(sAliasInner, objToSet);
		
		
		}//END main:
	}
	
	public Object get(String sAliasOuter, String sAliasInner) throws ExceptionZZZ{
		Object objReturn = null;
		main:{
	//		Guard Klauseln
			if(StringZZZ.isEmpty(sAliasOuter)){
				ExceptionZZZ ez = new ExceptionZZZ("sAliasOuter", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sAliasInner)){
				ExceptionZZZ ez = new ExceptionZZZ("sAliasInner", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(!hmOuter.containsKey(sAliasOuter)) break main;
			
			HashMap hmInner = (HashMap) hmOuter.get(sAliasOuter);
			if(!hmInner.containsKey(sAliasInner)) break main;
			
			objReturn = hmInner.get(sAliasInner);
		
		}//END main:
		return objReturn;
	}
	
	/** Aufbereitete Ausgabe der Daten als String, mit Zeilenumbruch für jeden neuen Eintrag.
	* @return
	* 
	* lindhauer; 08.08.2011 10:39:40
	 */
	public String debugString(){
		String sReturn = new String("");
		main:{
			//HashMapOuter durchgehen
			if(this.hmOuter.size()==0) break main;
			
			String sLF = "\n";
			String sTab = "\t";
			String sTabDelim = sTab +"| ";
			
			
			Set setKeyOuter = this.hmOuter.keySet();
			Iterator itOuter = setKeyOuter.iterator();
			while(itOuter.hasNext()){
				Object objOuter = itOuter.next();
				sReturn = sReturn + objOuter.toString();
				
				HashMap hmInner = (HashMap) this.hmOuter.get(objOuter);
				if(hmInner!=null){
					Set setKeyInner = hmInner.keySet();
					Iterator itInner = setKeyInner.iterator();
					while(itInner.hasNext()){
						Object objInner = itInner.next();
						sReturn = sReturn + sTabDelim + objInner.toString();
						
						Object objValue = hmInner.get(objInner);
						sReturn = sReturn + sTabDelim + objValue.toString();
						sReturn = sReturn + sLF + sTab;
					}//end while itInner.hasnext()
					sReturn = sReturn + sLF;
				}
			}//end while itOuter.hasnext()
		}//end main
		return sReturn;
	}
	
	
	//### AUS Map implementierte Methoden
	public int size() {
		return hmOuter.size();
	}

	public boolean isEmpty() {
		return hmOuter.isEmpty();
	}

	public boolean containsKey(Object arg0) {		
		return hmOuter.containsKey(arg0);
	}

	public boolean containsValue(Object arg0) {
		return hmOuter.containsValue(arg0);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 * 
	 * Merke: Es wird immer das zuletzt hinzugefügte Dokument zurückgegeben !!!
	 */
	public Object get(Object arg0) {	
		return hmOuter.get(arg0);
	}

	public Object put(Object arg0, Object arg1) {	
		Object objReturn = null;
		main:{
			if(arg0==null) break main;
			if(arg1==null){
				objReturn = hmOuter.put(arg0, null);
				break main;
			}
			
			
			//20080219 Testen auf Klasse von arg1
			Class cl = arg1.getClass();		
			if(cl.getName().equals("java.lang.String")){
				objReturn = hmOuter.put(arg0, arg1);				
			}else if(cl.getName().equals(HashMapMultiZZZ.class.getName())){
				objReturn =  hmOuter.put(arg0, (HashMapMultiZZZ) arg1);				
			}else{
				objReturn =  hmOuter.put(arg0, (HashMap) arg1);
			}
		}//End main
		return objReturn;
	}

	public Object remove(Object arg0) {
		return hmOuter.remove((HashMap) arg0);
	}

	public void clear() {
		hmOuter.clear();
	}

	public Set keySet() {
		return hmOuter.keySet();
	}

	public Collection values() {
		return hmOuter.values();
	}

	public Set entrySet() {
		return hmOuter.entrySet();
	}

	public void putAll(Map arg0) {
		// TODO Auto-generated method stub
		
	}
}
