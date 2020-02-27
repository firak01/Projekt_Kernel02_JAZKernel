package basic.zBasic.util.abstractList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;

/**Klasse dient dazu eine bestehende HashMap zu verwenden, aber das Ergebnis garantiert über eine eindeutigen Schlüsselindex zurückzubekommen.
 * Die Klasse erweitert nicht HashMap, weil sonst alle Methoden auf einen Schlag überschrieben werden müssten.
 * @author Lindhauer
 *
 */
public class HashMapIndexedZZZ<X,T>  extends ObjectZZZ implements Iterable<T>{	
	private HashMap<Integer,Object> hmIndexed=null;
	private VectorExtendedZZZ<Integer> vecIndex=null;	
	private int iIndex=-1;  //Der Index des gerade verarbeiteten Keys im Vektor
	
	private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); //Neu 20130721
	
	
	public HashMapIndexedZZZ() throws ExceptionZZZ{
		HashMapIndexedNew_(null);
	}
	
	/**  Konstruktor: Die übergebenen Hashtable
	 * @param objHt
	 * @throws ExceptionZZZ
	 * 18.02.2020, 18:37:08, Fritz Lindhauer
	 */
	public HashMapIndexedZZZ(HashMap<Integer,Object> hmIndexed) throws ExceptionZZZ{
		HashMapIndexedNew_(hmIndexed);
	}
	
		
	
	private boolean HashMapIndexedNew_(HashMap<Integer,Object> hmIndexedIn) throws ExceptionZZZ{
		boolean bReturn = true;
		main:{
			HashMap<Integer,Object>hmIndexed = null;
			check:{
				if(hmIndexedIn!=null) {
					this.setHashMap(hmIndexedIn);
				}
				hmIndexed = this.getHashMap();
			}//END check:
		
			Vector<Integer>vecIndex =  new Vector<Integer>(hmIndexed.keySet());
			this.setVectorIndex(vecIndex);		
		}//END main:
		return bReturn;
	}
	

	/** Object, Den Key des ersten Objekts zur�ckgeben. (Das ist das erste Element des internen Vectors.)
	* Lindhauer; 27.04.2006 08:13:07
	 * @return Object
	 */
	public Object getKeyFirst(){
		Object objReturn = null;
		try{
		VectorExtendedZZZ<Integer> vecIndex = this.getVectorIndex();
		if(vecIndex!=null){
			objReturn =  vecIndex.elementAt(0);			
			this.setKeyCurrent(0); 			
		}

		}catch(ExceptionZZZ ez){
			objReturn=null;
		}
		return objReturn;
	}
	
	/** Object, Den Key des letzten Objekts zurückgeben. (Das ist das letzte Objekt des inneren Vektors.)
	* Lindhauer; 27.04.2006 08:13:38
	 * @return Object
	 */
	public Object getKeyLast(){
		Object objReturn = null;
		try{
		VectorExtendedZZZ<Integer> vecIndex = this.getVectorIndex();
		if(vecIndex!=null){
			objReturn=vecIndex.lastElement();
			this.setKeyCurrent(vecIndex.size()-1);
		}

		}catch(ExceptionZZZ ez){
			objReturn=null;
		}
		 return objReturn;
	}
	
	/** Object, Den Key an der nächsten Indexposition holen. Dabei wird der Index im 1 erhöht.
	* Lindhauer; 27.04.2006 14:16:21
	 * @return Object
	 */
	public Object getKeyNext(){
		Object objReturn = null;
		main:{
			try{
			VectorExtendedZZZ<Integer> vecIndex = this.getVectorIndex();
			int iIndexTest = this.iIndex + 1;
			if(iIndexTest > vecIndex.size()-1) break main;
			
			objReturn =  vecIndex.elementAt(iIndexTest);			
			this.setKeyCurrent(iIndexTest);
			}catch(ExceptionZZZ ez){
				System.out.println(ez.getDetailAllLast());
			}
		}
		return objReturn;
	}
	
	/** Object, Gibt den Wert der Hashtable f�r den ersten Key (gem�� der Sortierung) zur�ck.
	* Lindhauer; 27.04.2006 11:30:25
	 * @return Object
	 */
	public Object getValueFirst(){
		Object objReturn = null;
		main:{
			Object objKey = this.getKeyFirst();
			if(objKey==null) break main;
					
			HashMap<Integer,Object>hmIndexed = this.getHashMap();
			objReturn = hmIndexed.get(objKey);
		}//END main:
		return objReturn;
	}
	
	/** Object, Gibt den Wert der Hashtable f�r den letzten Key (gem�� der Sortierung) zur�ck.
	* Lindhauer; 27.04.2006 11:30:25
	 * @return Object
	 */
	public Object getValueLast(){
		Object objReturn = null;
		main:{
			Object objKey = this.getKeyLast();
			if(objKey==null) break main;
			
			HashMap<Integer,Object>hmIndexed = this.getHashMap();
			objReturn = hmIndexed.get(objKey);
		}//END main:
		return objReturn;
	}
	
	/** Object, Gibt den Wert der Hashtalbe f�r den n�chsten Key (gem�� der Sortierung) zur�ck und schiebt den Index-Wert um 1 weiter.
	* Lindhauer; 27.04.2006 12:39:15
	 * @return Object
	 */
	public Object getValueNext(){
		Object objReturn = null;
		main:{
			Object objKeyNext = this.getKeyNext();
			if(objKeyNext == null) break main;
			
			HashMap<Integer,Object>hmIndexed = this.getHashMap();
			objReturn = hmIndexed.get(objKeyNext);
		}//end main:
		return objReturn;
	}
	
	/** Object, Gibt den Wert f�r den aktullen Key/den �bergebenen Key zur�ck, ohne den Indexwert weiterzuschieben.
	* Lindhauer; 27.04.2006 14:05:33
	 * @param objKey
	 * @return Object
	 */
	public Object getValue(Object objKey){
		Object objReturn = null;
		main:{
			check:{
				if(objKey==null) break main;
				
				//Das Key-Objekt muss ja im Vektor vorhanden sein
				VectorExtendedZZZ<Integer> vexIndex = this.getVectorIndex();
				if(!vexIndex.contains(objKey)) break main;
								
			}//END check:
			
			HashMap<Integer,Object>hmIndexed = this.getHashMap();
			objReturn = hmIndexed.get(objKey);
		}//END main:
		return objReturn;
	}
	
	/** void, Setze das Objekt, von dem aus mit getNext() weitergegangen werden kann auf eine andere Indexposition des Vektors.
	* Lindhauer; 27.04.2006 11:18:51
	 * @param objCur
	 * @throws ExceptionZZZ 
	 */
	public void setKeyCurrent(int iIndex) throws ExceptionZZZ{
		main:{
			check:{
				if(iIndex <= -1){
					  ExceptionZZZ ez = new ExceptionZZZ( "iIndex <= -1, but expected to be >= 0", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;		 
				}
				VectorExtendedZZZ<Integer> vexIndex = this.getVectorIndex();
				if(iIndex > vexIndex.size() - 1){
					  ExceptionZZZ ez = new ExceptionZZZ("iIndex > " + (vexIndex.size() -1) + ", but expected to be <= Vector.size()", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;							
				}				
			}//END Check:
	
			this.iIndex = iIndex;
		}//END main:
	}
	
	/** HashMap, die Im Kontruktor übergebene HashMap, unver�ndert.
	 * Merke: Es macht keine Sinn zu versuchen eine irgendwie sortierte Hashtable zurückzugeben,
	 * 			da HashMap immer beliebig sortiert ist.
	 * @return
	 * @author Fritz Lindhauer, 27.02.2020, 17:19:53
	 */
	public HashMap<Integer,Object> getHashMap(){
		if(this.hmIndexed==null) {
			this.hmIndexed = new HashMap<Integer,Object>();
		}
		return this.hmIndexed;		
	}
	private void setHashMap(HashMap<Integer,Object>hmIndexed) {
		this.hmIndexed=hmIndexed;
	}
	
	public VectorExtendedZZZ<Integer> getVectorIndex() {
		if(this.vecIndex==null) {
			this.vecIndex = new VectorExtendedZZZ<Integer>();
		}
		return this.vecIndex;
	}
	/**Ist private, da der Vector immer sortiert sein muss, also darf man ihn von Aussen nicht setzen.
	 * @param objV
	 * @author Fritz Lindhauer, 19.02.2020, 08:32:25
	 */
	private void setVectorIndex(Vector<Integer>vecIndexed) {
		VectorExtendedZZZ<Integer> vecIndexedZZZ= new VectorExtendedZZZ<Integer>(vecIndexed);		
		this.vecIndex=vecIndexedZZZ;
	}
	/**Ist private, da der Vector immer sortiert sein muss, also darf man ihn von Aussen nicht setzen.
	 * @param objV
	 * @author Fritz Lindhauer, 19.02.2020, 08:32:25
	 */
	private void setVectorIndex(VectorExtendedZZZ<Integer>vecIndex) {			
		this.vecIndex=vecIndex;
	}
	
	@SuppressWarnings("unchecked")
	public void put(Integer intIndex, Object objValue) throws ExceptionZZZ {		
		VectorExtendedZZZ<Integer> vecIndex = this.getVectorIndex();
		boolean bExists = vecIndex.contains(intIndex);
		
		if(bExists) {
			this.getHashMap().remove(intIndex);			
		}else {			
			//Merke: DER VECTOR MUSS IMMER AUFSTEIGEND SORTIERT SEIN !!!! Das wirkt sich hier beim Updaten aus.			
			if(vecIndex.isLastElementGreaterThan(intIndex)) {
				//Neue Sortierung notwendig
				vecIndex.addSorted(intIndex, VectorExtendedZZZ.iSORT_DIRECTION_ASCENDING);			
			}else {
				vecIndex.add(intIndex);//einfach anhängen.
			}			
		}
		this.getHashMap().put(intIndex, objValue);
		
	}
	
	@SuppressWarnings("unchecked")
	public void put(Object objValue) throws ExceptionZZZ {		
		VectorExtendedZZZ<Integer> vecIndex = this.getVectorIndex();
		Integer intMax = (Integer) vecIndex.lastElement();
		int iMax = -1;
		if(intMax!=null) {
			iMax = intMax.intValue();		
		}
		iMax=iMax+1;
		Integer intMaxNew = new Integer(iMax);
		vecIndex.add(intMaxNew);//einfach anhängen.
							
		this.getHashMap().put(intMaxNew, objValue);		
	}

//	@Override
//	public Iterator<T> iterator() {
//		VectorExtendedZZZ<Integer> vecIndex = this.getVectorIndex();
//				
//		//Iterator<Integer> itIndex = vecIndex.iterator();
//		//return (Iterator<T>) itIndex;
//		
//		//ABER: Man iteriert nicht über den Index, sondern über die Objekte der HashMap.
//		
//	}
	
	@Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>() {

            @Override
            public boolean hasNext() {
            	boolean bReturn = false;
            	main:{
	            	VectorExtendedZZZ<Integer> vec = getVectorIndex();
	            	if(vec==null)break main;
	            	
	            	Integer intLast = (Integer) vec.lastElement();
	            	Integer intCur = new Integer(iIndex);
	            	bReturn = iIndex < intLast.intValue() && getHashMap().get(intCur) != null;
            	}//end main:
            	return bReturn;
            }

            @Override
            public T next() {
                //return arrayList[currentIndex++];
            	return null;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

}//END Class
