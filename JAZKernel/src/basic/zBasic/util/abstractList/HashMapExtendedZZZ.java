package basic.zBasic.util.abstractList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import basic.javareflection.mopex.Mopex;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.MathZZZ;

/** Diese Klasse erweitert Hashmap um einige Funktionen.
 *   - zum Vergleichen der Inhalte zwischen 2 Hashmaps. Dabei werden unterschiedliche Kriterien herangezogen.
 * @author lindhaueradmin
 *
 */
public class HashMapExtendedZZZ<T,X> extends HashMap implements  IConstantZZZ, IObjectZZZ, IHashMapExtendedZZZ{
	private static final long serialVersionUID = -576703130885041379L;
	private ExceptionZZZ objException;
	
	public HashMapExtendedZZZ(){
	}
	
	
	/** Versuche für das angegebene Objekt den Schlüsselwert zurückzugeben.
	 *   Da ein Objekt mit mehreren Schlüsseln abgelegt sein kann: "First"...
	 * @param hm
	 * @param value
	 * @return
	 */
	public static Object getKeyFromValueFirst(Map hm, Object value) {
	    for (Object o : hm.keySet()) {
	      if (hm.get(o).equals(value)) {
	        return o;
	      }
	    }
	    return null;
	  }
	
	/** Vergleiche die Werte der in der HashMap gespeicherten Objekte. 
	 *   Aber: Gib eine Methode an, über die die Werte der Objekte erst einmal ermittelt werden sollen.
	 *   Die daraus reslutierenden Werte werden mit .equals() miteinander verglichen.
	* @param hm
	* @param sMethod
	* @return
	* 
	* lindhaueradmin; 18.05.2011 06:02:54
	 * @throws ExceptionZZZ 
	 */
	public static HashMapIndexedZZZ getValueDupsIndexedByMethod(HashMap objHashMap, String sMethodName) throws ExceptionZZZ{
		HashMapIndexedZZZ hmIndexed = new HashMapIndexedZZZ();
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare", iERROR_PARAMETER_MISSING,   HashMapExtendedZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(StringZZZ.isEmpty(sMethodName)){
				ExceptionZZZ ez = new ExceptionZZZ("String MethodName", iERROR_PARAMETER_MISSING,   HashMapExtendedZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			}
			hmIndexed = HashMapIndexedZZZ.getValueDupsIndexedByMethod(objHashMap, sMethodName);
		}//end main
		return hmIndexed;
	}
	
	
	/** Vergleiche zwei HashMaps bzgl. der Size
	* @param objHashMap
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 05.09.2011 09:38:25
	 */
	public boolean isSmallerThan(HashMap objHashMap) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			
			//Vergleiche zwei Hashmaps, bzgl. der Size()
			HashMap objHashMapExt = HashMapExtendedZZZ.minElements(this, objHashMap);
			if(objHashMapExt==null) break main;
			if(objHashMapExt.equals(this)) bReturn = true;
		}//end main
		return bReturn;
	}
	
	/** Vergleiche zwei HashMaps bzgl. der Size
	* @param objHashMap
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 05.09.2011 09:38:47
	 */
	public boolean isBiggerThan(HashMap objHashMap) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;
			  }
			
			//Vergleiche zwei Hashmaps, bzgl. der Size()
			HashMap objHashMapExt = HashMapExtendedZZZ.maxElements(this, objHashMap);
			if(objHashMapExt==null) break main;
			if(objHashMapExt.equals(this)) bReturn = true;
		}//end main
		return bReturn;
	}
	
	public boolean isEqualToByMethod(HashMap objHashMap, String sMethodName) throws ExceptionZZZ{
		boolean bReturn = true;
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(StringZZZ.isEmpty(sMethodName)){
				ExceptionZZZ ez = new ExceptionZZZ("String MethodName", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			}
			
			
			//###############################
			String sMethod = null; //F�r das Exception Handling: Name der Methode, die gerade gesucht, invoked, etc. wird
			Object obj = null;        //F�r das Exception Handling: Objekt, das gerade in Arbeit ist, bzgl. der Methode
			try {
			//1. Kann ja nur Teilmenge sein, wenn die �bergebene Hashmap gleich ist.
			boolean	btemp = this.isSameSize(objHashMap);
			if(!btemp){
				bReturn = false;
				break main;
			}
			
		
			//2. Vergleiche zwei Hashmaps, elemtweise.
			//1. �u�ere Schleife: Nimm daf�r "das von aussen kommende Vergleichsobjekt"
			HashMap objHashMapExt = this;
			HashMap objHashMapInt = objHashMap;
						
			/*
			for (objHashMapExt.Entry e : objHashMapExt.entrySet()){
				System.out.println(e.getKey() + " = " + e.getValue());
			}*/
			
			Iterator it = objHashMapExt.keySet().iterator();
			while(it.hasNext()){
				String strKeyExt = (String) it.next();
				
				//Test 1: Key vorhanden?
				Object objTestInt = objHashMapInt.get(strKeyExt);
				if(objTestInt==null){
					bReturn = false;
					break main;  //Noch nicht einmal den Key gefunden, also ist die kleinere Hashmap definitiv keine Teilmenge 
				}
				
				//Test 2: Werte gleich?
				Object objTestExt = objHashMapExt.get(strKeyExt);
				
				
				//+++ Suche nun nach der Methode in den beiden Objekten.
				//Merke: In der Reflection API Paramter f�r die MEthode anzugeben geht so:
				//Class[] classaParam = new Class[1];
				//classaParam[0] = objKeyValueToStore.getClass();
				//
				//aber hier ist classaParm=null;  
				sMethod = sMethodName; //F�r das Exception Handling
				obj = objTestInt; //F�r das Exception Handling
				Method mInt = Mopex.getSupportedMethod(objTestInt.getClass(), sMethodName, null);
				
				sMethod = sMethodName; //Für das Exception Handling
				obj = objTestExt; //Für das Exception Handling
				Method mExt = Mopex.getSupportedMethod(objTestExt.getClass(), sMethodName, null);
				
				if(mInt==null | mExt==null){
					ExceptionZZZ ez = new ExceptionZZZ("NoMethodFound " + sMethodName + " in one of the compared Objects. Key: " + strKeyExt , iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			
				
				
				//+++ Rufe die Methode auf beiden Objekten auf. Das Ergebnis sind wiederrum Objekte.
				Object objTestErgInt = null;
				Object objTestErgExt = null;
				
				//kein Return Objekt integer: int iCode = ((Integer) m.invoke(objWithKeyValue, null)).intValue();
				//System.out.println("Ergebnis der Method invocation addNewKeyPrimary=" + iCode);
				
				//Object objReturnType = m.invoke(objWithKey, null);
				//so w�rde der Parameterwert �bergeben an eine MEthode, die einen Parameter erwartet objTestErgInt = mInt.invoke(objKeyStore, new Object[] {objKeyValueToStore});
				//wir habe aber keinen Parameter, also NULL
				obj = objTestInt; //F�r das Exception Handling
				objTestErgInt = mInt.invoke(objTestInt, null);
				if(objTestErgInt!=null){
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Ergebnisobjekt: " + objTestErgInt.toString());
				}else{
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. ABBRUCH");
					break main;
				}
				
				obj = objTestExt; //F�r das Exception Handling
				objTestErgExt = mExt.invoke(objTestExt, null);
				if(objTestErgExt!=null){
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Ergebnisobjekt: " + objTestErgExt.toString());
				}else{
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. ABBRUCH");
					break main;
				}
				
				
				//+++ Suche die "equals" Methode in dem Ergebnisobjekt und f�hre sie aus.
				//!!! Parameter ist das andere Ergebnisobjekt
				Class[] classaParam = new Class[1];
				classaParam[0] = Object.class; //die MEthode "equals" bekommt als Parameter ein Classe Objekt, nicht genauer wie z.B. String. Dazu wird keine Methode gefunden. Also nicht: objTestErgInt.getClass();
				
				sMethod = "equals(OBJECT)"; //NEIN, das wird nicht gefunden.... " + objTestErgInt.getClass().getName() + ")"; //F�r das Exception Handling
				obj = objTestErgExt; //F�r das Exception Handling
				Method mEqualsExt = Mopex.getSupportedMethod(objTestErgExt.getClass(), "equals", classaParam);
				
			
				Object objErgEquals = mEqualsExt.invoke(objTestErgExt, new Object[] {objTestErgInt});
				if(objErgEquals!=null){
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Ergebnisobjekt: " + objErgEquals.toString());
					if(objErgEquals.toString().equals("false")){
						bReturn = false;
						break main; //wenn false, dann die Schleife verlassen
					}
				}else{
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. R�CKGABEWERT: false");
					bReturn = false;
					break main; //wenn False, dann die Schleife verlassen.
				}
			}
			
			} catch (IllegalArgumentException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IllegalArgumentException bei " + sMethod + " in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (IllegalAccessException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IllegalAccessException bei " + sMethod + " in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (InvocationTargetException e) {
				ExceptionZZZ ez = new ExceptionZZZ("InvocationTargetException bei " + sMethod + " in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (NoSuchMethodException e) {
				ExceptionZZZ ez = new ExceptionZZZ("NoSuchMethodException " + sMethod + " in Object " + obj.getClass().getName() + ": " + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} 
			
		}//end main:
		return bReturn;
	}
	
	/** Vergleiche zwei HashMaps bzgl. der Size().
	* @param objHashMap
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 05.09.2011 09:39:48
	 */
	public boolean isSameSize(HashMap objHashMap) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			
			//Vergleiche zwei Hashmaps, bzgl. der Size()
			bReturn = HashMapExtendedZZZ.isSameSize(this, objHashMap);
		}//end main
		return bReturn;
	}
	
	/** Vergleiche zwei HashMaps bzgl. der Size().
	* @param objHashMap
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 05.09.2011 09:39:48
	 */
	public static boolean isSameSize(HashMap objHashMap1, HashMap objHashMap2) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objHashMap1== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap1 to compare'", iERROR_PARAMETER_MISSING,  HashMapExtendedZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(objHashMap2== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap2 to compare'", iERROR_PARAMETER_MISSING,   HashMapExtendedZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			//###################
			int iSize1 = objHashMap1.size();
			int iSize2 = objHashMap2.size();
			
			if (iSize1 == iSize2) bReturn = true;
		}//end main:
		return bReturn;
	}
	
	/** Vergleicht, ob die Objekte, die in der kleineren Liste gespeichert sind, in der gr��eren Liste vorkommen.
	 *   Achtung: Objekte sind viel mehr als z.B. ein Text, den sie repr�sentieren. Verwende f�r den Vergleich von 'objekt.xyzMEthodenname()' die Methode isSubsetFromByMethod. 
	* @param objHashMap
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 14.04.2011 07:03:00
	 */
	public boolean isSubsetFrom(HashMap objHashMap) throws ExceptionZZZ{
		boolean bReturn = true;
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			
			//1. Kann ja nur Teilmenge sein, wenn die �bergebene Hashmap gr��er oder gleich ist.
			boolean btemp = this.isSmallerThan(objHashMap);
			if(!btemp){
				btemp = this.isSameSize(objHashMap);
				if(!btemp){
					bReturn = false;
					break main;
				}
			}
		
			//2. Vergleiche zwei Hashmaps, elemtweise.
			//1. �u�ere Schleife: Nimm daf�r die Hashmap mit den wenigsten elementen, das aktuelle objekt
			HashMap objHashMapExt = this;
			HashMap objHashMapInt = objHashMap;
						
			/*
			for (objHashMapExt.Entry e : objHashMapExt.entrySet()){
				System.out.println(e.getKey() + " = " + e.getValue());
			}*/
			
			Iterator it = objHashMapExt.keySet().iterator();
			while(it.hasNext()){
				String strKeyExt = (String) it.next();
				
				//Test 1: Key vorhanden?
				Object objTestInt = objHashMapInt.get(strKeyExt);
				if(objTestInt==null){
					bReturn = false;
					break main;  //Noch nicht einmal den Key gefunden, also ist die kleinere Hashmap definitiv keine Teilmenge 
				}
				
				//Test 2: Werte gleich?
				Object objTestExt = objHashMapExt.get(strKeyExt);
				if(!objTestInt.equals(objTestExt)){
					bReturn = false;
					break main;
				}
			}
		}//end main
		return bReturn;
	}
	
	/** Objekte sind viel mehr als z.B. ein Text, den sie repr�sentieren. Verwende diese Methode f�r den Vergleich der beiden Objekte bzgl. des Ergebnisses von 'objekt.xyzMEthodenname()'.
	 *    Gel�st wird dies �ber den 2. Paramter, der dem Methodennamen entspricht, der per Reflection-API in dem Objekt gesucht und aufgerufen wird.
	 *    MERKE: Dabei muss es sich um eine Methode handeln, die KEINEN Parameter erwartet!
	* @param objHashMap
	* @param sMethodName
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 14.04.2011 07:10:50
	 */
	public boolean isSubsetFromByMethod(HashMap objHashMap, String sMethodName) throws ExceptionZZZ{
		boolean bReturn = true;
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(StringZZZ.isEmpty(sMethodName)){
				ExceptionZZZ ez = new ExceptionZZZ("String MethodName", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			}
			//###############################
			String sMethod = null; //F�r das Exception Handling: Name der Methode, die gerade gesucht, invoked, etc. wird
			Object obj = null;        //F�r das Exception Handling: Objekt, das gerade in Arbeit ist, bzgl. der Methode
			
			//Objektvorbereitungen Nimm daf�r die Hashmap mit den wahrscheinlich wenigsten elementen: Das aktuelle objekt
			HashMap objHashMapExt = this;
			HashMap objHashMapInt = objHashMap;
			
			//Die "Untersuchungsmethoden":
			Method mExt = null;
			Method mInt = null; 
			
			//Ergebnisobjekte, d.h. hier wird das Ergebnis des Methodenaufrufs reingepackt:
			Object objTestErgInt = null;
			Object objTestErgExt = null;
			
			try {
			//1. Kann ja nur Teilmenge sein, wenn die �bergebene Hashmap gr��er oder gleich ist.
			boolean btemp = this.isSmallerThan(objHashMap);
			if(!btemp){
				btemp = this.isSameSize(objHashMap);
				if(!btemp){
					bReturn = false;
					break main;
				}
			}
		
			//2. Vergleiche zwei Hashmaps, elemtweise.
			
			/*
			for (objHashMapExt.Entry e : objHashMapExt.entrySet()){
				System.out.println(e.getKey() + " = " + e.getValue());
			}*/
			
			Iterator it = objHashMapExt.keySet().iterator();
			while(it.hasNext()){
				String strKeyExt = (String) it.next();
				
				//Test 1: Key vorhanden?
				Object objTestInt = objHashMapInt.get(strKeyExt);
				if(objTestInt==null){
					bReturn = false;
					break main;  //Noch nicht einmal den Key gefunden, also ist die kleinere Hashmap definitiv keine Teilmenge 
				}
				
				//Test 2: Werte gleich?
				Object objTestExt = objHashMapExt.get(strKeyExt);
				
				
				//+++ Suche nun nach der Methode in den beiden Objekten.
				mExt= null;
				mInt = null;
				
				//Merke: In der Reflection API Paramter f�r die MEthode anzugeben geht so:
				//Class[] classaParam = new Class[1];
				//classaParam[0] = objKeyValueToStore.getClass();
				//
				//aber hier ist classaParm=null;  
				
//				!!! Es ist wichtig, das erst das "This" objekt gepr�ft wird. Ist darin die "untersuchungsmethode" nicht vorhanden == > Fehler werfen. Ansonsten ist der Fehler egal.
				sMethod = sMethodName; //F�r das Exception Handling
				obj = objTestExt; //F�r das Exception Handling
				mExt = Mopex.getSupportedMethod(objTestExt.getClass(), sMethodName, null); //Merke: ..ext... ist das "This" objekt.
				
				sMethod = sMethodName; //F�r das Exception Handling
				obj = objTestInt; //F�r das Exception Handling
				mInt = Mopex.getSupportedMethod(objTestInt.getClass(), sMethodName, null);
				
				if(mInt==null | mExt==null){
					ExceptionZZZ ez = new ExceptionZZZ("NoMethodFound " + sMethodName + " in one of the compared Objects. Key: " + strKeyExt , iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			
				
				
				//+++ Rufe die Methode auf beiden Objekten auf. Das Ergebnis sind wiederrum Objekte.
				objTestErgInt = null;
				objTestErgExt = null;
				
				//kein Return Objekt integer: int iCode = ((Integer) m.invoke(objWithKeyValue, null)).intValue();
				//System.out.println("Ergebnis der Method invocation addNewKeyPrimary=" + iCode);
				
				
				obj = objTestExt; //F�r das Exception Handling
				objTestErgExt = mExt.invoke(objTestExt, null);
				if(objTestErgExt!=null){
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Ergebnisobjekt: " + objTestErgExt.toString());
				}else{
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. ABBRUCH");
					break main;
				}
				
				//Object objReturnType = m.invoke(objWithKey, null);
				//so w�rde der Parameterwert �bergeben an eine MEthode, die einen Parameter erwartet objTestErgInt = mInt.invoke(objKeyStore, new Object[] {objKeyValueToStore});
				//wir habe aber keinen Parameter, also NULL
				obj = objTestInt; //F�r das Exception Handling
				objTestErgInt = mInt.invoke(objTestInt, null);
				if(objTestErgInt!=null){
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Ergebnisobjekt: " + objTestErgInt.toString());
				}else{
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. ABBRUCH");
					break main;
				}
				
			
				
				
				//+++ Suche die "equals" Methode in dem Ergebnisobjekt und f�hre sie aus.
				//!!! Parameter ist das andere Ergebnisobjekt
				Class[] classaParam = new Class[1];
				classaParam[0] = Object.class; //die MEthode "equals" bekommt als Parameter ein Classe Objekt, nicht genauer wie z.B. String. Dazu wird keine Methode gefunden. Also nicht: objTestErgInt.getClass();
				
				sMethod = "equals(OBJECT)"; //NEIN, das wird nicht gefunden.... " + objTestErgInt.getClass().getName() + ")"; //F�r das Exception Handling
				obj = objTestErgExt; //F�r das Exception Handling
				Method mEqualsExt = Mopex.getSupportedMethod(objTestErgExt.getClass(), "equals", classaParam);
				
				Object objErgEquals = mEqualsExt.invoke(objTestErgExt, new Object[] {objTestErgInt});
				if(objErgEquals!=null){
					//System.out.println("Methode " + sMethod + " erfolgreich invoked. Ergebnisobjekt: " + objErgEquals.toString());
					if(objErgEquals.toString().equals("false")){
						bReturn = false;
						break main; //wenn false, dann die Schleife verlassen
					}
				}else{
					//System.out.println("Methode " + sMethod + " erfolgreich invoked. Kein Ergebnisobjekt. R�CKGABEWERT: false");
					bReturn = false;
					break main; //wenn False, dann die Schleife verlassen.
				}
			}
			
			} catch (IllegalArgumentException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IllegalArgumentException bei '" + sMethod + "' in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (IllegalAccessException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IllegalAccessException bei '" + sMethod + "' in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (InvocationTargetException e) {
				ExceptionZZZ ez = new ExceptionZZZ("InvocationTargetException bei '" + sMethod + "' in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (NoSuchMethodException e) {
				//FGL 20131223: Wenn es die Vergleichsmethode im aufrufenden Object nicht vorhanden ist, den Fehler werfen, ansonsten sind die Objekte halt nicht gleich un den Fehler unterdr�cken.
				if(mExt==null){  //Merke: mExt ist die Methode des "This" Objektes. Darum ist es wichtig erst mExt versuchen zu holen und dann mInt. Weil mInt = null soll keinen Fehler werfen.
				   ExceptionZZZ ez = new ExceptionZZZ("NoSuchMethodException '" + sMethod + "' in Object " + obj.getClass().getName() + ": " + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				   throw ez;
				}else{
					bReturn = false;
					break main;
				}
			} 
		}//end main
		return bReturn;
	}
	
	
	/** Gibt von 2 �bergebenen HashMaps diejenige mit den wenigsten Elementen zur�ck.
	 *    Bzw. null, wenn beide HashMaps gleich viele Elemente haben.
	* @param objHashMap1
	* @param objHashMap2
	* @return
	* 
	* lindhaueradmin; 10.04.2011 09:34:03
	 * @throws ExceptionZZZ 
	 */
	public static HashMap minElements(HashMap objHashMap1, HashMap objHashMap2) throws ExceptionZZZ{
		HashMap objReturn = null;
		main:{
			if(objHashMap1== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap1 to compare'", iERROR_PARAMETER_MISSING,  HashMapExtendedZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(objHashMap2== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap2 to compare'", iERROR_PARAMETER_MISSING,   HashMapExtendedZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			//###################
			int iSize1 = objHashMap1.size();
			int iSize2 = objHashMap2.size();
			
			int iResult = MathZZZ.min(iSize1, iSize2);
			if(iResult==iSize1 & iResult == iSize2) break main; //null zur�ckgeben, wenn beide Listen gleich viele Elemente haben.
			if(iResult==iSize1){
				objReturn = objHashMap1;
			}else if(iResult==iSize2){
				objReturn = objHashMap2;
			}
			
		}//end main:
		return objReturn;
	}
	
	/** Gibt von 2 �bergebenen HashMaps diejenige mit den meisten Elementen zur�ck.
	 *    Bzw. null, wenn beide HashMaps gleich viele Elemente haben.
	* @param objHashMap1
	* @param objHashMap2
	* @return
	* 
	* lindhaueradmin; 10.04.2011 09:34:03
	 * @throws ExceptionZZZ 
	 */
	public static HashMap maxElements(HashMap objHashMap1, HashMap objHashMap2) throws ExceptionZZZ{
		HashMap objReturn = null;
		main:{
			if(objHashMap1== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap1 to compare'", iERROR_PARAMETER_MISSING,  HashMapExtendedZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(objHashMap2== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap2 to compare'", iERROR_PARAMETER_MISSING,   HashMapExtendedZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			//###################
			int iSize1 = objHashMap1.size();
			int iSize2 = objHashMap2.size();
			
			int iResult = MathZZZ.max(iSize1, iSize2);
			if(iResult==iSize1 & iResult == iSize2) break main; //null zur�ckgeben, wenn beide Listen gleich viele Elemente haben.
			if(iResult==iSize1){
				objReturn = objHashMap1;
			}else if(iResult==iSize2){
				objReturn = objHashMap2;
			}
			
		}//end main:
		return objReturn;
	}
	
	/**Sortiere die Map. !!! Die Werte m�ssen vergleichbar sein!!!
	 *   http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
	* @param map
	* @return
	* 
	* lindhaueradmin; 22.05.2011 08:54:37
	 */
	static Map sortByEntryValue(Map map) {
	     List list = new LinkedList(map.entrySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue()) .compareTo(((Map.Entry) (o2)).getValue());
	          }
	     });
	     
	    Map result = new LinkedHashMap();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	        Map.Entry entry = (Map.Entry)it.next();
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	} 	
	
	/**Sortiere die Map. !!! Die Werte m�ssen vergleichbar sein!!!
	 *   http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
	* @param map
	* @return
	* 
	* lindhaueradmin; 22.05.2011 08:54:37
	 */
	static Map sortByEntryInteger(Map map) {
	     List list = new LinkedList(map.entrySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	        	  int iReturn = 0;
	        	  
		        	//  iReturn =( ((Map.Entry)o1).getValue()) .compareTo((Comparable)((Map.Entry)o2).getValue());
		        	Integer int1 = ((Integer) o1);
		        	Integer int2 = ((Integer) o2);
		        	iReturn = int1.compareTo(int2);
		      
		            return iReturn; 
	          }
	     });
	     
	    Map result = new LinkedHashMap();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	        Map.Entry entry = (Map.Entry)it.next();
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	} 
	
	/**Sortiere die Map. !!! Die Werte m�ssen vergleichbar sein!!!
	 *   http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
	* @param map
	* @return
	* 
	* lindhaueradmin; 22.05.2011 08:54:37
	 */
	static void sortByKeyValue(Map map) {
	     List list = new LinkedList(map.keySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue()) .compareTo(((Map.Entry) (o2)).getValue());
	          }
	     });
	} 
	
	/**Sortiere die Map. !!! Die Werte m�ssen vergleichbar sein!!!
	 *   http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
	* @param map
	* @return
	* 
	* lindhaueradmin; 22.05.2011 08:54:37
	 */
	static void sortByKeyInteger(Map map) {
	     List list = new LinkedList(map.keySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	        	  int iReturn = 0;
	        	  
	        	//  iReturn =( ((Map.Entry)o1).getValue()) .compareTo((Comparable)((Map.Entry)o2).getValue());
	        	Integer int1 = ((Integer) o1);
	        	Integer int2 = ((Integer) o2);
	        	iReturn = int1.compareTo(int2);
	      
	               return iReturn; 
	          }
	     });
	} 
	
	
	/** Aufbereitete Ausgabe der Daten als String, mit Zeilenumbruch f�r jeden neuen Eintrag.
	* @return
	* 
	* lindhauer; 08.08.2011 10:39:40
	 */
	public String debugString(){
		String sReturn = new String("");
		main:{
			//HashMapOuter durchgehen
			if(this.size()==0) break main;
			
			String sLF = HashMapExtendedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
			String sTabDelim = HashMapExtendedZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
			
			Set setKey = this.keySet();
			Iterator it = setKey.iterator();
			while(it.hasNext()){
				Object obj = it.next();
				sReturn = sReturn + obj.toString();
						
				Object objValue = this.get(obj);
				sReturn = sReturn + sTabDelim + objValue.toString();
				sReturn = sReturn + sLF;
				}//end while itInner.hasnext()
		}//end main
		return sReturn;
	}
	
	/** Entferne aus der HashMap den Eintrag an der �bergebenen Indexposition.
	 * Merke: Man kann nicht nach der beim Bef�llen angegebenen Reihenfolge vorgehen, bzw. den gleichen Index erwarten, da die Indexposition in einer HashMap beliebig ist.
	* @param iIndex; Der Index beginnt mit 0.
	* 
	* lindhauer; 05.09.2011 09:41:21
	 */
	public void removeByIndex(int iIndex){
		main:{
		if(iIndex<0) break main;
		if(iIndex > this.size()-1) break main;
		
		Set setKey = this.keySet();
		Iterator it = setKey.iterator();
		int icount = -1;
		while(it.hasNext()){
			icount++;
			
				Object obj = it.next();
				if(icount==iIndex){
				 	this.remove(obj);
				 break main;
				}
			}//end while itInner.hasnext()
		}//end main
	}
	
	
	/** Gib den Key an der �bergebenen Indexposition zur�ck.
	 * Merke: Man kann nicht nach der beim Bef�llen angegebenen Reihenfolge vorgehen, bzw. den gleichen Index erwarten, da die Indexposition in einer HashMap beliebig ist.
	* @param iIndex; Der Index beginnt mit 0.
	* 
	* lindhauer; 05.09.2011 09:41:21
	 */
	public Object getKeyByIndex(int iIndex){
		Object objReturn = null;
		main:{
			if(iIndex<0) break main;
			if(iIndex > this.size()-1) break main;
			
			Set setKey = this.keySet();
			Iterator it = setKey.iterator();
			int icount = -1;
			while(it.hasNext()){
				icount++;
				
					Object obj = it.next();
					if(icount==iIndex){
					 objReturn = obj;
					 break main;
					}
				}//end while itInner.hasnext()
				
			
		} //end main
		return objReturn;
	}
	
	
	/** Gib den Wert an der �bergebenen Indexposition zur�ck.
	 * Merke: Man kann nicht nach der beim Bef�llen angegebenen Reihenfolge vorgehen, bzw. den gleichen Index erwarten, da die Indexposition in einer HashMap beliebig ist.
	* @param iIndex; Der Index beginnt mit 0.
	* 
	* lindhauer; 05.09.2011 09:41:21
	 */
	public Object getValueByIndex(int iIndex){
		Object objReturn = null;
		main:{
			if(iIndex<0) break main;
			if(iIndex > this.size()-1) break main;
			
			Set setKey = this.keySet();
			Iterator it = setKey.iterator();
			int icount = -1;
			while(it.hasNext()){
				icount++;
				
					Object obj = it.next();
					if(icount==iIndex){
					 objReturn = this.get(obj);
					 break main;
					}
				}//end while itInner.hasnext()
				
			
		} //end main
		return objReturn;
	}

	
	/** TODO Idee aus der HashMap mal eben Schnell einen einzeiligen String machen zu k�nnen.
	 * TODO GOON
	* @param sDelimSet
	* @param sDelimValue
	* @return
	* 
	* lindhauer; 19.09.2012 15:53:00
	 */
	public String toStringImplode(String sDelimSet, String sDelimValue){
		/*
		 * String sDebug = new String("");
		debug:{
		if(hmDebug==null)break debug;
		if(hmDebug.size()==0) break debug;
				
		String sDelimValue = "~";
		String sDelimSet = "#";
						
		Set setKey = hmDebug.keySet();
		Iterator it = setKey.iterator();
		while(it.hasNext()){
			Object obj = it.next();										
			Object objValue = hmDebug.get(obj);
			sDebug = sDelimSet + obj.toString() + sDelimValue + objValue.toString();
		}//end while itInner.hasnext()
		}//end debug
		
		 */
		return "";
	}

	
	//#### GETTER / SETTER
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	}
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}
}
