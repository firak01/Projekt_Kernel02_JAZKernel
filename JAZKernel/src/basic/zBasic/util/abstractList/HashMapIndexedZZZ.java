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
	private int iIndexCurrent4Vector=-1;  //Der Index des gerade verarbeiteten Keys im Vektor
	
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
	 * @throws ExceptionZZZ 
	 */
	public Object getKeyFirst() throws ExceptionZZZ{
		Object objReturn = null;
//		try{
			VectorExtendedZZZ<Integer> vecIndex = this.getVectorIndex();		
			if(vecIndex!=null){
				if(vecIndex.hasAnyElement()) {
					Integer intIndex  =  (Integer) vecIndex.elementAt(0);			
					this.setIndexCurrent(0); 
					
					HashMap<Integer,Object>hmIndexed = this.getHashMap();
					objReturn = hmIndexed.get(intIndex);
				}
			}
//		}catch(ExceptionZZZ ez){
//			objReturn=null;
//		}
		return objReturn;
	}
	
	/** Object, Den Key des letzten Objekts zurückgeben. (Das ist das letzte Objekt des inneren Vektors.)
	* Lindhauer; 27.04.2006 08:13:38
	 * @return Object
	 * @throws ExceptionZZZ 
	 */
	public Object getKeyLast() throws ExceptionZZZ{
		Object objReturn = null;
//		try{
			VectorExtendedZZZ<Integer> vecIndex = this.getVectorIndex();
			if(vecIndex!=null){
				if(vecIndex.hasAnyElement()) {
					Integer intIndex=(Integer) vecIndex.lastElement();
					this.setIndexCurrent(vecIndex.size()-1);
					
					HashMap<Integer,Object>hmIndexed = this.getHashMap();
					objReturn = hmIndexed.get(intIndex);
				}
			}
//		}catch(ExceptionZZZ ez){
//			objReturn=null;
//		}
		 return objReturn;
	}
	
	/** Object, Den Key an der nächsten Indexposition holen. Dabei wird der Index im 1 erhöht.
	* Lindhauer; 27.04.2006 14:16:21
	 * @return Object
	 * @throws ExceptionZZZ 
	 */
	public Object getKeyNext() throws ExceptionZZZ{
		Object objReturn = null;
		main:{
			//try{
				VectorExtendedZZZ<Integer> vecIndex = this.getVectorIndex();
				if(vecIndex!=null){
					if(vecIndex.hasAnyElement()) {
						int iIndexTest = this.getIndexCurrent() + 1;
						if(iIndexTest > vecIndex.size()-1) break main;
				
						Integer intIndex =  (Integer) vecIndex.elementAt(iIndexTest);			
						this.setIndexCurrent(iIndexTest);
						
						HashMap<Integer,Object>hmIndexed = this.getHashMap();
						objReturn = hmIndexed.get(intIndex);
					}
				}
//			}catch(ExceptionZZZ ez){
//				System.out.println(ez.getDetailAllLast());
//			}
		}
		return objReturn;
	}
	
	/** Object, Den Key an der nächsten Indexposition holen. Dabei wird der Index im 1 erhöht.
	* Lindhauer; 27.04.2006 14:16:21
	 * @return Object
	 * @throws ExceptionZZZ 
	 */
	public Object getKeyCurrent() throws ExceptionZZZ{
		Object objReturn = null;
		main:{
//			try{
				VectorExtendedZZZ<Integer> vecIndex = this.getVectorIndex();
				if(vecIndex!=null){
					if(vecIndex.hasAnyElement()) {
						int iIndexTest = this.getIndexCurrent() + 1;
						if(iIndexTest > vecIndex.size()-1) break main;
				
						Integer intIndex =  (Integer) vecIndex.elementAt(iIndexTest);
						this.setIndexCurrent(iIndexTest);
						
						HashMap<Integer,Object>hmIndexed = this.getHashMap();
						objReturn = hmIndexed.get(intIndex);
					}
				}
//			}catch(ExceptionZZZ ez){
//				System.out.println(ez.getDetailAllLast());
//			}
		}
		return objReturn;
	}
	
	/** Object, Gibt den Wert der Hashtable f�r den ersten Key (gem�� der Sortierung) zur�ck.
	* Lindhauer; 27.04.2006 11:30:25
	 * @return Object
	 * @throws ExceptionZZZ 
	 */
	public Object getValueFirst() throws ExceptionZZZ{
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
	 * @throws ExceptionZZZ 
	 */
	public Object getValueLast() throws ExceptionZZZ{
		Object objReturn = null;
		main:{
			Object objKey = this.getKeyLast();
			if(objKey==null) break main;
			
			HashMap<Integer,Object>hmIndexed = this.getHashMap();
			objReturn = hmIndexed.get(objKey);
		}//END main:
		return objReturn;
	}
	
	/** Object, Gibt den Wert der Hashtalbe für den nächsten Key (gemäß der Sortierung) zurück und schiebt den Index-Wert um 1 weiter.
	* Lindhauer; 27.04.2006 12:39:15
	 * @return Object
	 * @throws ExceptionZZZ 
	 */
	public Object getValueNext() throws ExceptionZZZ{
		Object objReturn = null;
		main:{
			Object objKeyNext = this.getKeyNext();
			if(objKeyNext == null) break main;
			
			HashMap<Integer,Object>hmIndexed = this.getHashMap();
			objReturn = hmIndexed.get(objKeyNext);
		}//end main:
		return objReturn;
	}
	
	/** Object, Gibt den Wert für den aktullen Key/den übergebenen Key zurück, ohne den Indexwert weiterzuschieben.
	* Lindhauer; 27.04.2006 14:05:33
	 * @param objKey
	 * @return Object
	 * @throws ExceptionZZZ 
	 */
	public Object getValue(Integer objKey) throws ExceptionZZZ{
		Object objReturn = null;
		main:{
			check:{
				if(objKey==null) break main;
				
				//Das Key-Objekt muss ja im Vektor vorhanden sein
				VectorExtendedZZZ<Integer> vecIndex = this.getVectorIndex();
				if(vecIndex==null) break main;
				if(!vecIndex.hasAnyElement()) break main;				
				if(!vecIndex.contains(objKey)) break main;
				
				//Nicht vergessen: Den aktuell verarbeiteten Index setzten
				int iIndex = objKey.intValue();
				this.setIndexCurrent(iIndex);
			}//END check:
			
			HashMap<Integer,Object>hmIndexed = this.getHashMap();
			objReturn = hmIndexed.get(objKey);
		}//END main:
		return objReturn;
	}
	
	public Object getValue(int iKey) throws ExceptionZZZ{
		Object objReturn = null;
		main:{
			if(iKey<0)break main;
			
			Integer intKey = new Integer(iKey);
			objReturn = this.getValue(intKey);
		}
		return objReturn;
	}
	
	/** void, Setze das Objekt, von dem aus mit getNext() weitergegangen werden kann auf eine andere Indexposition des Vektors.
	* Lindhauer; 27.04.2006 11:18:51
	 * @param objCur
	 * @throws ExceptionZZZ 
	 */
	public void setIndexCurrent(int iIndex) throws ExceptionZZZ{
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
				if(!vecIndex.hasAnyElement()) {
					ExceptionZZZ ez = new ExceptionZZZ("no element exists iIndex cann not be set.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;	
				}
				if(iIndex > vexIndex.size() - 1){
					  ExceptionZZZ ez = new ExceptionZZZ("iIndex > " + (vexIndex.size() -1) + ", but expected to be <= Vector.size()", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;							
				}				
			}//END Check:
	
			this.iIndexCurrent4Vector = iIndex;
		}//END main:
	}
	
	public int getIndexCurrent() {
		return this.iIndexCurrent4Vector;
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
		
		boolean bExists = false;		
		if(vecIndex.hasAnyElement()) {
			bExists = vecIndex.contains(intIndex);
		}
		if(bExists) {
			this.getHashMap().remove(intIndex);			
		}else {			
			//Merke: DER VECTOR MUSS IMMER AUFSTEIGEND SORTIERT SEIN !!!! Das wirkt sich hier beim Updaten aus.			
			if(vecIndex.isLastElementGreaterThan(intIndex)) {
				//Neue Sortierung notwendig
				vecIndex.addSorted(intIndex, ICollectionConstantZZZ.iSORT_DIRECTION_ASCENDING);			
			}else {
				vecIndex.add(intIndex);//einfach anhängen.
			}			
		}
		this.getHashMap().put(intIndex, objValue);
		
	}
	
	@SuppressWarnings("unchecked")
	public void put(Object objValue) throws ExceptionZZZ {		
		VectorExtendedZZZ<Integer> vecIndex = this.getVectorIndex();
		int iMax = -1;
		
		if(vecIndex.hasAnyElement()) {
			Integer intMax = (Integer) vecIndex.lastElement();
			if(intMax!=null) {
				iMax = intMax.intValue();
			}
		}
		iMax=iMax+1;
		
		Integer intMaxNew = new Integer(iMax);
		vecIndex.add(intMaxNew);//einfach anhängen.
		
		HashMap<Integer,Object>hmIndexed = this.getHashMap();
		hmIndexed.put(intMaxNew, objValue);		
	}

	//++++ "Komfortmethoden", die eine normale HashMap auch hat anbieten.
	public int size() {
		int iReturn = 0;
		main:{
			if(this.getHashMap()!=null) {
				iReturn = this.getHashMap().size();
			}
		}//end main;
		return iReturn;
	}
	
	public boolean isEmpty() {
		boolean bReturn = true;
		main:{
			if(this.size()>=1) {
				bReturn=false;
			}
		}//end main;
		return bReturn;
	}

	
	
	
	//### Interfaces ++++++++++++
	
	
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
        	private int iIndexIterator=-1; //Der Index des gerade verarbeiteten Keys im Iterator
        	private int iIndexWatched=-1;//Der Index des gerade mit hasNext() betrachteten Keys im Iterator
        	
        	
            @Override
            public boolean hasNext() {
            	boolean bReturn = false;
            	main:{
	            	VectorExtendedZZZ<Integer> vec = getVectorIndex();
	            	if(vec==null)break main;
	            	if(!vec.hasAnyElement())break main;
	            	
	            	
	            	Integer intLast = (Integer) vec.lastElement();
	            		            
	            	iIndexWatched = iIndexWatched+1;//das nächste Element halt, ausgehend von -1
	            	Integer intNext = new Integer(iIndexWatched);
	            	bReturn = iIndexWatched <= intLast.intValue() && getHashMap().get(intNext) != null;	            	
            	}//end main:
            	return bReturn;
            }

            @SuppressWarnings("unchecked")
			@Override
            public T next() {
                T objReturn = null;
                main:{
                	VectorExtendedZZZ<Integer> vec = getVectorIndex();
	            	if(vec==null)break main;
	            	if(!vec.hasAnyElement())break main;
	            	
                	int iIndexCur = this.iIndexIterator;
                	if(iIndexCur<this.iIndexWatched) {
                		iIndexCur = this.iIndexWatched;
                	}else {
                		iIndexCur = iIndexCur + 1;
                	}
                	
	            	Integer intLast = (Integer) vec.lastElement();
	            	boolean bReturn = iIndexCur <= intLast.intValue() && getHashMap().get(iIndexCur) != null;	 
	            	if(bReturn) {
	            		this.iIndexIterator = iIndexCur;
	            		objReturn = (T) getHashMap().get(iIndexCur);
	            	}
                }//end main:
            	return objReturn;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
	
	
	

}//END Class
