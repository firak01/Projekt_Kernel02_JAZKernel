/*
 * Created on 29.11.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zBasic.util.abstractList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import basic.javareflection.mopex.Mopex;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


/**
 * @author Lindhauer
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ArrayListExtendedZZZ<T> extends ArrayList<T> implements  IConstantZZZ, IObjectZZZ, IArrayListExtendedZZZ{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2859619907770188881L;
	private ExceptionZZZ objException;
	
	/**
	 * @param iCapacityInitial
	 */
	public ArrayListExtendedZZZ(int iCapacityInitial) {
		super(iCapacityInitial);			
	}
	public ArrayListExtendedZZZ(){		
		super();
	}
	public ArrayListExtendedZZZ(T[]obja) {	
		ArrayListExtendedNew_(obja);
	
	}
	
	private boolean ArrayListExtendedNew_(T[] obja){		
		boolean bReturn = false;
		main:{
			if(obja==null) break main;
			
			for(T obj : obja) {
				this.add(obj);
			}
			bReturn = true;
		}//end main:
				
		return bReturn;
	}//end private constructor
	
	public boolean addList(ArrayList<T> obj_alIn, int istartin, int iendin) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(obj_alIn== null){
				ExceptionZZZ ez = new ExceptionZZZ("ArrayList-Source'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			  if(obj_alIn.size()==0) break main; //nothing to to
			  
			  int iend;
			  if(obj_alIn.size()< iendin) iend=obj_alIn.size();  //set the end to the maximum size of the input-list
			  iend = iendin;
			  if(istartin < 0 || iend < 0){ // s. vorherige Zeile
				ExceptionZZZ ez = new ExceptionZZZ( "Invalid Index: istart = '" + istartin + "', iend = '" + iendin + "', but expected > 0", iERROR_PARAMETER_VALUE,   this, ReflectCodeZZZ.getMethodCurrentName());											  
				throw ez;					
			  }
			  
			  //add the elements to the internal list
			  int istart = istartin;
			  iend = iend-istart;
			  for(int icount = 0; icount <= iend; icount++){
			  	this.add((T) obj_alIn.get(istart));
			  	istart++;
			  }
	}//end main:
    return bReturn;
}//end function
	
	@SuppressWarnings("unchecked")
	public void addUnique(Object obj) throws ExceptionZZZ{
		main:{
			if(obj==null){
				ExceptionZZZ ez = new ExceptionZZZ("Object'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			}
			
			if(this.contains(obj)) break main;
			this.add((T) obj);
			
		}//end main
	}
	
	public HashMapExtendedIndexedZZZ getValueDupsIndexedByMethod(String sMethodName) throws ExceptionZZZ{
		HashMapExtendedIndexedZZZ hmReturn = null;
		main:{
			hmReturn = HashMapExtendedIndexedZZZ.getValueDupsIndexedByMethod(this, sMethodName);
		}
		return hmReturn;
	}
		
	public boolean isEqualToByMethod(ArrayList objAL, String sMethodName) throws ExceptionZZZ{
		boolean bReturn = true;
		main:{
			if(objAL== null){
				ExceptionZZZ ez = new ExceptionZZZ("ArrayList to compare'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
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
			boolean	btemp = ArrayListZZZ.isSameSize(this.toArrayList(), objAL);
			if(!btemp){
				bReturn = false;
				break main;
			}
			
		
			//2. Vergleiche zwei Hashmaps, elemtweise.
			//1. �u�ere Schleife: Nimm daf�r "das von aussen kommende Vergleichsobjekt"
			ArrayList objALExt = this.toArrayList();
			ArrayList objALInt = objAL;
						
			/*
			for (objHashMapExt.Entry e : objHashMapExt.entrySet()){
				System.out.println(e.getKey() + " = " + e.getValue());
			}*/
			
			Iterator itExt = objALExt.iterator();
			while(itExt.hasNext()){
				Object objExt = itExt.next();
				
				Iterator itInt = objALInt.iterator();
				while(itInt.hasNext()){
					Object objInt = itInt.next();
					
					
					//+++ Suche nun nach der Methode in den beiden Objekten.
					//Merke: In der Reflection API Paramter f�r die MEthode anzugeben geht so:
					//Class[] classaParam = new Class[1];
					//classaParam[0] = objKeyValueToStore.getClass();
					//
					//aber hier ist classaParm=null;  
					sMethod = sMethodName; //F�r das Exception Handling
					obj = objInt; //F�r das Exception Handling
					Method mInt = Mopex.getSupportedMethod(objInt.getClass(), sMethodName, null);
					
					sMethod = sMethodName; //F�r das Exception Handling
					obj = objExt; //F�r das Exception Handling
					Method mExt = Mopex.getSupportedMethod(objExt.getClass(), sMethodName, null);
					
					if(mInt==null | mExt==null){
						ExceptionZZZ ez = new ExceptionZZZ("NoMethodFound " + sMethodName + " in one of the compared Objects:" + obj.toString() , iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
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
					obj = objInt; //F�r das Exception Handling
					objTestErgInt = mInt.invoke(objInt, null);
					if(objTestErgInt!=null){
						//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Ergebnisobjekt: " + objTestErgInt.toString());
					}else{
						//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. ABBRUCH");
						break main;
					}
					
					obj = objExt; //F�r das Exception Handling
					objTestErgExt = mExt.invoke(objExt, null);
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
					
				}//while intern
			}//while extern
			
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
	
	/** Pr�ft ob alle Objekte in den ArrayListen die gleiche Classe haben.
	* @param alStoring2
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 03.05.2011 07:10:34
	 */
	public boolean isEqualStoringClass(ArrayList alStoring2) throws ExceptionZZZ{
		boolean bReturn = true;
		main:{
			if(alStoring2== null){
				ExceptionZZZ ez = new ExceptionZZZ("ArrayList to compare'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			
			ArrayList alStoring1 = this.toArrayList();
			
//			A) Technisch die gleichen Elemente....
			Iterator itExt = alStoring1.iterator();		
			while(itExt.hasNext()){
				Object objStoredExt = itExt.next();
				
				Iterator itInt = alStoring2.iterator();
				while(itInt.hasNext()){
					Object objStoredInt = itInt.next();
			
					Class objClass1 = objStoredExt.getClass();
					Class objClass2 = objStoredInt.getClass();

					if(objClass1.equals(objClass2)){
						bReturn = true;
					}else{
						bReturn = false;
						break main; //Schleife verlassen.
					}
				}	
			}
			
		}//end main
		return bReturn;
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean addBefore(Object objToAdd, Object objNext) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objNext==null){
				this.add((T) objToAdd);
				break main;
			}
			
			boolean bFound = false; int iFound=-1;
			ArrayList<T>listaTemp2Append = new ArrayList<T>();
			int iIndex = -1;
			Iterator<T> itThis = this.iterator();
			while(itThis.hasNext()){				
				Object objExt = itThis.next();
				iIndex++;
				if(bFound){
					//Wenn nachfoldenden Wert gefunden, zuerst alle Werte in eine neue, temporäre Arraylist speichern.					
					listaTemp2Append.add((T) objExt);										
				}else{
					if(objNext.equals(objNext)){
						bFound = true;
						iFound = iIndex;
					}
				}																					
			}
			
			//Rückwärts alle Werte löschen, immer den letzten
			for(iIndex=this.size()-1;iIndex>=iFound;iIndex--){ 
				this.remove(iIndex);
			}
			
			//Den neuen Wert einfügen
			this.add((T) objToAdd);
			
			//Die temporäre Liste anhängen
			this.addList(listaTemp2Append, 0, listaTemp2Append.size()-1);			
		}
		return bReturn;
	}
	

	
	/** Entferne Dubletten. Dabei wird die IndexHashMap zum Kennzeichnen der Dubletten verwendet.
	 *    Intern werden die Dubletten in einer einzigen ArrayList zusammengefasst und diese dann gel�scht.
	* @param hm
	* @param hmIndexed
	* @param sFlagRemain "KEEPFIRST" / "KEEPLAST", default ist "KEEPLAST"
	* @throws ExceptionZZZ
	* @return Anzahl der entferneten werte
	* 
	* lindhauer; 17.05.2011 11:37:46
	 */
	public int removeDupsFromByIndex( HashMapExtendedIndexedZZZ hmIndexed, String sFlagRemainIn) throws ExceptionZZZ{
		int iReturn = 0;
		main:{
			iReturn = HashMapExtendedIndexedZZZ.removeDupsFromByIndex(this, hmIndexed, sFlagRemainIn);
		}//end main
		return iReturn;
	}
	
	public String debugString(){
		String sReturn = new String("");
		main:{		
			String sEntryDelimiter = ArrayListExtendedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
			
			sReturn = ArrayListExtendedZZZ.debugString(this, sEntryDelimiter);
		}//end main
		return sReturn;
	}
	
	public static String debugString(ArrayList<?>lista) {
		return ArrayListExtendedZZZ.debugString(lista, null);
	}
	
	public static String debugString(ArrayList<?>lista, String sEntryDelimiterIn) {
		String sReturn = new String("");
		main:{
			if(lista==null)break main;
			if(lista.size()==0) break main;
			
			String sEntryDelimiter;			
			if(sEntryDelimiterIn==null){
				sEntryDelimiter = ArrayListExtendedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
			}else {
				sEntryDelimiter = sEntryDelimiterIn;
			}
						
			Iterator it = lista.iterator();
			while(it.hasNext()){
				if(!StringZZZ.isEmpty(sReturn)){
					sReturn = sReturn + sEntryDelimiter;
				}
				
				Object obj = it.next();
				sReturn = sReturn + obj.toString();				
			}//end while it.hasnext()
		}//end main
	return sReturn;
	}
	
	
	
	
 
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#getExceptionObject()
	 */
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	}
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#setExceptionObject(zzzKernel.custom.ExceptionZZZ)
	 */
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException=objException;
	}
	/**
	 * @return ArrayList, internal ArrayList. E.g. this is the result-list aber an .addList-method.
	 */
	public ArrayList toArrayList() {
		return (ArrayList)this;
	}	
	}//end class
