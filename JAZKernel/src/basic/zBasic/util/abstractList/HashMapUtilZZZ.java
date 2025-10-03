package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
	
	//#############################################################################
	//### DEBUG HASHMAP-MULTI
	//#############################################################################	

	/** Aufbereitete Ausgabe der Daten als String, mit Zeilenumbruch fuer jeden neuen Eintrag.
	 * @param <T>
	 * @param <X>
	* @return
	* 
	* lindhauer; 08.08.2011 10:39:40
	 */				
	@SuppressWarnings("rawtypes")
	public static String debugString(HashMapMultiZZZ hmDebug, String sKeyDelimiterIn, String sEntryDelimiterIn) throws ExceptionZZZ {
	    String sReturn = "";
	    main: {
	        // HashMapOuter durchgehen
	        if (hmDebug == null) break main;
	        if (hmDebug.size() == 0) break main;

	        String sEntryDelimiter;
	        if (sEntryDelimiterIn == null) {
	            sEntryDelimiter = IOutputDebugNormedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
	        } else {
	            sEntryDelimiter = sEntryDelimiterIn;
	        }

	        String sKeyDelimiter;
	        if (sKeyDelimiterIn == null) {
	            sKeyDelimiter = IOutputDebugNormedWithKeyZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
	        } else {
	            sKeyDelimiter = sKeyDelimiterIn;
	        }

	        Set entrySetOuter = hmDebug.entrySet();
	        Iterator itOuter = entrySetOuter.iterator();
	        while (itOuter.hasNext()) {
	            if (!StringZZZ.isEmpty(sReturn)) {
	                sReturn = sReturn + sEntryDelimiter;
	            }

	            Map.Entry entryOuter = (Map.Entry) itOuter.next();
	            Object objOuterKey = entryOuter.getKey();
	            Object objOuterValue = entryOuter.getValue();

	            String sKeyOuter = String.valueOf(objOuterKey);
	            HashMap hmInner = (HashMap) objOuterValue;

	            // 20190801: HIER DEBUG FUNKTIONALITÄT VON HashMapExtendedZZZ verwenden.
	            String stemp = HashMapExtendedZZZ.computeDebugString(hmInner, sKeyDelimiter, sEntryDelimiter);
	            if (stemp != null) {
	                String[] saValue = StringZZZ.explode(stemp, sEntryDelimiter);
	                String[] saValueWithKey = StringArrayZZZ.plusString(saValue, sKeyOuter + sKeyDelimiter, "BEFORE");
	                sReturn = sReturn + StringArrayZZZ.implode(saValueWithKey, sEntryDelimiter);
	            } else {
	                sReturn = sReturn + sKeyOuter;
	            }
	        } // end while itOuter.hasNext()
	    } // end main
	    return sReturn;
	}
	
	//#############################################################################
	//### DEBUG
	//#############################################################################	
	//================== PUBLIC API: HashMap ==================
	public static String computeDebugString(HashMap hmImplode) throws ExceptionZZZ {
		return computeDebugStringInternal(hmImplode, null, null);
	}

	public static String computeDebugString(HashMap hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ {
		return computeDebugStringInternal(hmImplode, sEntryDelimiterIn, null);
	}

	public static String computeDebugString(HashMap hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {
		return computeDebugStringInternal(hmImplode, sEntryDelimiterIn, sKeyDelimiterIn);
	}
	
	
	//================== PUBLIC API: LinkedHashMap ==================
	//Merke: Linked HashMap soll die Reihenfolge erhalten
	public static String computeDebugString(LinkedHashMap hmImplode) throws ExceptionZZZ {
		return computeDebugStringInternal(hmImplode, null, null);
	}

	public static String computeDebugString(LinkedHashMap hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ {
		return computeDebugStringInternal(hmImplode, sEntryDelimiterIn, null);
	}

	public static String computeDebugString(LinkedHashMap hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {
		return computeDebugStringInternal(hmImplode, sEntryDelimiterIn, sKeyDelimiterIn);
	}
	
	//================== PRIVATE GENERIC IMPLEMENTATION ==================
	@SuppressWarnings("rawtypes")
	private static String computeDebugStringInternal(Map hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {	  
	    String sReturn = null;
	    main: {
	        if (hmImplode == null || hmImplode.size() == 0) break main;

	        String sKeyDelimiter = (sKeyDelimiterIn == null) 
		    		? IHashMapExtendedZZZ.sDEBUG_KEY_DELIMITER_DEFAULT 
		    		: sKeyDelimiterIn;

		    String sEntryDelimiter = (sEntryDelimiterIn == null) 
		    		? IHashMapExtendedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT 
		    		: sEntryDelimiterIn;

	        Set setEntry = hmImplode.entrySet();   // raw usage beibehalten wg. Signaturen
	        Iterator it = setEntry.iterator();
	        while (it.hasNext()) {
	            Map.Entry entry = (Map.Entry) it.next();   // raw cast
	            Object objKey = entry.getKey();
	            Object objValue = entry.getValue();

	            String sPair = String.valueOf(objKey) + sKeyDelimiter + String.valueOf(objValue);

	            if (StringZZZ.isEmpty(sReturn)) {
	                sReturn = sPair;
	            } else {
	                sReturn = sReturn + sEntryDelimiter + sPair;
	            }
	        }
	    }
	    return sReturn;
	}
	
	
	//#############################################################################	
    //### IMPLODE
	//#############################################################################
	//================== PUBLIC API: HashMap ==================
	public static String computeImplodeString(HashMap hmImplode) throws ExceptionZZZ {
		return computeImplodeStringInternal(hmImplode, null, null);
	}

	public static String computeImplodeString(HashMap hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ {
		return computeImplodeStringInternal(hmImplode, sEntryDelimiterIn, null);
	}

	public static String computeImplodeString(HashMap hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {
		return computeImplodeStringInternal(hmImplode, sEntryDelimiterIn, sKeyDelimiterIn);
	}
	
	
	//================== PUBLIC API: LinkedHashMap ==================
	//Merke: Linked HashMap soll die Reihenfolge erhalten
	public static String computeImplodeString(LinkedHashMap hmImplode) throws ExceptionZZZ {
		return computeImplodeStringInternal(hmImplode, null, null);
	}

	public static String computeImplodeString(LinkedHashMap hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ {
		return computeImplodeStringInternal(hmImplode, sEntryDelimiterIn, null);
	}

	public static String computeImplodeString(LinkedHashMap hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {
		return computeImplodeStringInternal(hmImplode, sEntryDelimiterIn, sKeyDelimiterIn);
	}
	
	//================== PRIVATE GENERIC IMPLEMENTATION ==================
	@SuppressWarnings("rawtypes")
	private static String computeImplodeStringInternal(Map hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {	    
	    String sReturn = null;
	    main: {
	        if (hmImplode == null || hmImplode.size() == 0) break main;

	        String sKeyDelimiter = (sKeyDelimiterIn == null) 
		    		? IHashMapExtendedZZZ.sIMPLODE_KEY_DELIMITER_DEFAULT 
		    		: sKeyDelimiterIn;

		    String sEntryDelimiter = (sEntryDelimiterIn == null) 
		    		? IHashMapExtendedZZZ.sIMPLODE_ENTRY_DELIMITER_DEFAULT 
		    		: sEntryDelimiterIn;

	        Set setEntry = hmImplode.entrySet();   // raw usage beibehalten wg. Signaturen
	        Iterator it = setEntry.iterator();
	        while (it.hasNext()) {
	            Map.Entry entry = (Map.Entry) it.next();   // raw cast
	            Object objKey = entry.getKey();
	            Object objValue = entry.getValue();

	            String sPair = String.valueOf(objKey) + sKeyDelimiter + String.valueOf(objValue);

	            if (StringZZZ.isEmpty(sReturn)) {
	                sReturn = sPair;
	            } else {
	                sReturn = sReturn + sEntryDelimiter + sPair;
	            }
	        }
	    }
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
