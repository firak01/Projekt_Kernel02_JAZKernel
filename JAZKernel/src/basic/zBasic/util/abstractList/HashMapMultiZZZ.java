package basic.zBasic.util.abstractList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
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
 * 
 * Merke: 
 * 20180326: Diese Multi Hashmap muss man leichter durchlaufen können. So kommt ein Fehler:
//		FEHLER     Exception in thread "main" java.lang.ClassCastException: java.util.HashMap$Entry cannot be cast to java.util.HashMap		     
//		     Set<HashMap<?, ?>> setVariant = hmCatalog.entrySet();
//		     //Set<HashMap> setVariant = hmType.entrySet();
//		     for (Iterator<HashMap<?, ?>> iteratorVariantType = setVariant.iterator(); iteratorVariantType.hasNext();) {
//		    	    HashMap<?,?>hmVariantType = iteratorVariantType.next();
//		    	    
//		    	    for(Iterator<?> iteratorVariant = hmVariantType.entrySet().iterator();iteratorVariant.hasNext();){
//		    	    	Entry<String, Box> variant = (Entry<String, Box>) iteratorVariant.next();
//		    	    	String sString = variant.getKey();
//		    	    	Box boxTemp = variant.getValue();
//		    	    	String sNameBoxTemp = boxTemp.getName();
//		    	    	System.out.println("String: '" + sString + "' | '" + sNameBoxTemp + "'");
//		    	    	
//		    	    	this.add(BorderLayout.CENTER, boxTemp);
//		    	    }
//		    	    
//		    	}
 * 
 * 20180326: Daher kann man alle Elemente der HashMapMultiZZZ nun so durchlaufen
   HashMapMultiZZZ hmCatalog = this.getVariantCatalog().getMapCatalog();
   for (Iterator<String> iteratorVariantTypes = hmCatalog.getOuterKeySetIterator(); iteratorVariantTypes.hasNext();) {
	 String sVariantType = (String) iteratorVariantTypes.next();
	 System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXX String: '" + sVariantType + "'");

	 //Set<String> setVariant = (Set<String>) hmCatalog.getInnerKeySet(sVariantType);
	 //for (Iterator<String> iteratorVariant = setVariant.iterator(); iteratorVariant.hasNext();) {
	 for (Iterator<String> iteratorVariant = hmCatalog.getInnerKeySetIterator(sVariantType); iteratorVariant.hasNext();) {
		 String sVariant = (String) iteratorVariant.next();
		 System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXX String: '" + sVariant + "'");

		 Box boxTemp = (Box) hmCatalog.get(sVariantType, sVariant);//z.B.: Box boxTemp = (Box) hmCatalog.get("ARMY","new_sale");		    		 		    		 		     
	     this.add(BorderLayout.CENTER, boxTemp);				    
	 }		    	 		    	
 }
 */
public class HashMapMultiZZZ <X,Z> implements IConstantZZZ, IHashMapExtendedZZZ, Map{
	protected HashMap<String,Object> hmOuter=new HashMap<String,Object>();
	
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
	 * @throws ExceptionZZZ 
	 */
	public String debugString() throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			//HashMapOuter durchgehen
			if(this.hmOuter.size()==0) break main;
								
			String sEntryDelimiter = HashMapMultiZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
			String sKeyDelimiter = HashMapMultiZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
			sReturn = this.debugString(this, sKeyDelimiter, sEntryDelimiter);		
		}//end main:
		return sReturn;
	}
	
	public String debugString(String sKeyDelimiter,String sEntryDelimiter) throws ExceptionZZZ{
		return HashMapMultiZZZ.debugString(this, sKeyDelimiter, sEntryDelimiter);	
	}
	
	public static String debugString(HashMapMultiZZZ hmDebug) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{		
			String sEntryDelimiter = HashMapMultiZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
			String sKeyDelimiter = HashMapMultiZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
			sReturn = HashMapMultiZZZ.debugString(hmDebug, sKeyDelimiter, sEntryDelimiter);
		}//end main
		return sReturn;
	}
	
	public static String debugString(HashMapMultiZZZ hmDebug, String sKeyDelimiterIn, String sEntryDelimiterIn) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			//HashMapOuter durchgehen
			if(hmDebug==null)break main;
			if(hmDebug.size()==0) break main;
			
			String sEntryDelimiter;			
			if(sEntryDelimiterIn==null){
				sEntryDelimiter = HashMapMultiZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
			}else {
				sEntryDelimiter = sEntryDelimiterIn;
			}
						
			String sKeyDelimiter;
			if(sKeyDelimiterIn==null){
				sKeyDelimiter = HashMapMultiZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
			}else{
				sKeyDelimiter = sKeyDelimiterIn;
			}
		
			String sKeyOuter = null;
			Set setKeyOuter = hmDebug.keySet();
			Iterator itOuter = setKeyOuter.iterator();
			while(itOuter.hasNext()){
				if(!StringZZZ.isEmpty(sReturn)){
					sReturn = sReturn + sEntryDelimiter;
				}
				
				Object objOuter = itOuter.next();
				sKeyOuter = objOuter.toString();
				
				HashMap hmInner = (HashMap) hmDebug.get(objOuter);
				
				//20190801: HIER DEBUG FUNKTIONALITÄT VON HashMapExtendedZZZ verwenden.
				String stemp = HashMapExtendedZZZ.debugString(hmInner, sKeyDelimiter, sEntryDelimiter);
				if(stemp!=null){
					String[] saValue = StringZZZ.explode(stemp, sEntryDelimiter);
					String[] saValueWithKey = StringArrayZZZ.plusString(saValue, sKeyOuter+sKeyDelimiter,"BEFORE");
					sReturn = sReturn + StringArrayZZZ.implode(saValueWithKey,sEntryDelimiter);				
				}else{
					sReturn = sReturn + sKeyOuter;
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
	 * Merke: Es wird immer das zuletzt hinzugefuegte Object zurueckgegeben !!!
	 */	
	public Object get(Object arg0){	
		return hmOuter.get(arg0);
	}
	
	public Set<?> getInnerKeySet(String sOuterKey){
		Set<?> setReturn = null;
		main:{
			if(sOuterKey==null) break main;
			if(!this.getOuterHashMap().containsKey(sOuterKey)){
				break main;
			}
						
			HashMap hmOuterReturn = this.getOuterHashMap();
			HashMap hmInnerReturn = (HashMap) hmOuterReturn.get(sOuterKey);
			setReturn = hmInnerReturn.keySet();
		}//end main:
		return setReturn;
	}
	
	public Iterator getInnerKeySetIterator(String sOuterKey){
		Iterator iteratorReturn = null;
		main:{
			if(sOuterKey==null) break main;
			if(!this.getOuterHashMap().containsKey(sOuterKey)){
				break main;
			}
			
			Set<?> set = this.getInnerKeySet(sOuterKey);
			if(set!=null){
				iteratorReturn = set.iterator();
			}
		}//end main:
		return iteratorReturn;
	}
	
	public Set<?> getOuterKeySet(){
		Set<?> setReturn = null;
		main:{
			setReturn = this.getOuterHashMap().keySet();
		}//end main:
		return setReturn;
	}
	
	public Iterator getOuterKeySetIterator(){
		Iterator iteratorReturn = null;
		main:{
			Set<?> set = this.getOuterKeySet();
			if(set!=null){
				iteratorReturn = set.iterator();
			}
		}//end main:
		return iteratorReturn;
	}

	public Object put(Object arg0, Object arg1) {	
		Object objReturn = null;
		main:{
			if(arg0==null) break main;
			
			if(arg1==null){
				objReturn = hmOuter.put((String) arg0, null);
				break main;
			}
			
			
			//20080219 Testen auf Klasse von arg1
			Class<?> cl = arg1.getClass();		
			//if(cl.getName().equals("java.lang.String")){
			if(cl.getName().equals(HashMap.class.getName())){
				objReturn = hmOuter.put((String) arg0, (HashMap) arg1);				
			}else if(cl.getName().equals(HashMapMultiZZZ.class.getName())){
				objReturn =  hmOuter.put((String) arg0, (HashMapMultiZZZ) arg1);				
			}else{
				objReturn =  hmOuter.put((String) arg0, arg1);
			}
		}//End main
		return objReturn;
	}

	public Object remove(Object arg0) {
		return hmOuter.remove((HashMap) arg0);
	}
	
	public boolean remove(Object sAliasOuter, Object sAliasInner){	
		boolean bReturn = false;
		main:{
			HashMap hmInner = this.getInnerHashMap(sAliasOuter);
			if(hmInner==null) break main;
			
			
			Object objReturn = hmInner.remove(sAliasInner);
			if(objReturn!=null) bReturn = true;
			
			//Wenn nun die Innere HashMap "weg" ist, dann as Objekt in der äußern HashMap auch eintfernen.
			if(hmInner.size()==0){
				hmOuter.remove(sAliasOuter);
			}
		}//end main:
		return bReturn;
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
	
	public HashMap<String, Object> getHashMap(){
		return this.hmOuter;
	}
	
	/* Damit das anwndungsfreundlicher wird zwischen Outer und Inner Maps ggfs. unterscheiden.
	 * 
	 */
	public HashMap<String, Object> getOuterHashMap(){
		return this.hmOuter;
	}
	
	public HashMap getInnerHashMap(Object objOuterKey){
		HashMap hmReturn = null;
		main:{
			if(objOuterKey==null) break main;
		
			hmReturn = (HashMap) this.get(objOuterKey);
		}//end main:
		return hmReturn;
	}
}
