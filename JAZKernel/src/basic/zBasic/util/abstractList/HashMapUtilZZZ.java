package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IOutputDebugNormedWithKeyZZZ;
import basic.zBasic.IOutputDebugNormedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class HashMapUtilZZZ {
	
	public static boolean isEmpty(HashMap hm) {
		boolean bReturn = false;
		main:{
			if(hm==null) {
				bReturn = true;
				break main;
			}
			if(hm.size()==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	
	//### fuer IOutputNormedZZ	
	/** Aufbereitete Ausgabe der Daten als String, mit Zeilenumbruch fuer jeden neuen Eintrag.
	 * @param <T>
	 * @param <X>
	* @return
	* 
	* lindhauer; 08.08.2011 10:39:40
	 */			
	public static String computeDebugString(HashMap hmDebug, String sKeyDelimiterIn, String sEntryDelimiterIn){
		String sReturn = new String("");
		main:{
			//HashMapOuter durchgehen
			if(hmDebug==null)break main;
			if(hmDebug.size()==0) break main;
			
			String sEntryDelimiter;			
			if(sEntryDelimiterIn==null){
				sEntryDelimiter = IOutputDebugNormedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
			}else {
				sEntryDelimiter = sEntryDelimiterIn;
			}
						
			String sKeyDelimiter;
			if(sKeyDelimiterIn==null){
				sKeyDelimiter = IOutputDebugNormedWithKeyZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
			}else{
				sKeyDelimiter = sKeyDelimiterIn;
			}
			
			Set setKey = hmDebug.keySet();
			Iterator it = setKey.iterator();
			while(it.hasNext()){
				if(!StringZZZ.isEmpty(sReturn)){
					sReturn = sReturn + sEntryDelimiter;
				}
				
				Object obj = it.next();
				sReturn = sReturn + obj.toString();
						
				Object objValue = hmDebug.get(obj);
				sReturn = sReturn + sKeyDelimiter + objValue.toString();				
			}//end while itInner.hasnext()
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
				sEntryDelimiter = IOutputDebugNormedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
			}else {
				sEntryDelimiter = sEntryDelimiterIn;
			}
						
			String sKeyDelimiter;
			if(sKeyDelimiterIn==null){
				sKeyDelimiter = IOutputDebugNormedWithKeyZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
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
				String stemp = HashMapExtendedZZZ.computeDebugString(hmInner, sKeyDelimiter, sEntryDelimiter);
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
	//############################################################################
	
	public static String computeImplodeString(HashMap hmImplode) throws ExceptionZZZ{
		return HashMapUtilZZZ.computeImplodeString(hmImplode, null, null);
	}
	
	public static String computeImplodeString(HashMap hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ{
		return HashMapUtilZZZ.computeImplodeString(hmImplode, sEntryDelimiterIn, null);
	}
	
	public static String computeImplodeString(HashMap hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn)  throws ExceptionZZZ{
		
		String sKeyDelimiter;
		if(sKeyDelimiterIn==null){
			sKeyDelimiter = IHashMapExtendedZZZ.sIMPLODE_KEY_DELIMITER_DEFAULT;
		}else {
			sKeyDelimiter = sKeyDelimiterIn;
		}
		
		String sEntryDelimiter; 
		if(sEntryDelimiterIn==null){
			sEntryDelimiter = IHashMapExtendedZZZ.sIMPLODE_ENTRY_DELIMITER_DEFAULT;
		}else {
			sEntryDelimiter = sEntryDelimiterIn;
		}
		
	    String sReturn = null;
		main:{		
			if(hmImplode.size()==0) break main;
			
			todogoon20251001;
			
			 // Durchläuft alle Schlüssel-Wert-Paare
			/*
	        for (Map.Entry entry : hmImplode.entrySet()) {
	            String schluessel = entry.getKey();
	            Integer wert = entry.getValue();
	            System.out.println("Schlüssel: " + schluessel + ", Wert: " + wert);
	        }
	        */
		/*	
			Set setKey = hmImplode.keySet();
			Iterator it = setKey.iterator();
			while(it.hasNext()){
				java.util.Map.Entry entry = (Entry) it.next();
				Object obj = entry.getKey();
				Object objValue = entry.getValue(); //hmImplode.get(obj);
				if(StringZZZ.isEmpty(sReturn)) {
					sReturn = obj.toString() + sKeyDelimiter + objValue.toString();
				}else {
					sReturn = sEntryDelimiter + obj.toString() + sKeyDelimiter + objValue.toString();
				}
			}//end while itInner.hasnext()
		*/
		}//end main

		return sReturn;
		
	}
	
	
	//#############################################################################
	public static String computeAsHashMapEntry(String sKey, String sValue) {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sKey)) break main;
			
			sReturn = "{"+sKey+"="+sValue + "}";
		}//end main:
		return sReturn;
	}
}
