//////////////////////////////////////////////////////////////////////////////
//                                                                          //
// TYPE			: Java class                                                //
//                                                                          //
// NAME			: ExtendedVector.java                                       //
//                          //
// FIRST VERSION AUTOR		: Stephan Mecking, CoCoNet GmbH                             //
//				            : Peter Kuehn, CoCoNet GmbH                                  //
//                                                                          //
// COMPONENT    : MULTIWEB client -> some different components              //
//                                                                          //
// DESCRIPTION	: This object represents a extension to the standard        //
//				  Vector object. Now a vector can be searched for           //
//                case-independent strings.                                 //
//                                                                          //
// HISTORY		                                                            //
//                                                                          //
// Date       | Changes                                        | Who        //
// 04.08.1997 | first version                                  | SM, PK
// till now   | extended version ZZZ                           | FGL
//                                                                          //
//////////////////////////////////////////////////////////////////////////////

package basic.zBasic.util.abstractList;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Vector;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IOutputDebugNormedZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.log.LogStringZZZ;

/** 20170725: Diese Klasse Generisch gemacht. Dabei den Klassennamen analog zur HashMapExtended gewählt. 
 *                    Die alte Klasse beibehalten als nicht generisch...
 *                    TODO: Hier Methoden ggfs. "TypeCast sicher überschreiben
 *                    
 *  20200219: Die Methoden aus der alten klasse in die neue Klasse übernommen
 */
@SuppressWarnings("rawtypes")
public class VectorZZZ<T> extends Vector implements IVectorZZZ<T>{
	private static final long serialVersionUID = 1L;
	
	protected int iIndexUsedLast=-1;
	
	protected volatile String sDebugEntryDelimiterUsed = null; //zum Formatieren einer Debug Ausgabe
	
	public VectorZZZ() {
		super();
	}

	public VectorZZZ(Vector initVector) {
		for (int i=0; i<initVector.size(); i++)
			this.addElement(initVector.elementAt(i));
	}
	
	public VectorZZZ(VectorZZZ<T> initVector) {
		for (int i=0; i<initVector.size(); i++)
			this.addElement(initVector.elementAt(i));
	}

	//### Ueberschriebene Originalmethoden, um noch weitere Funktionen zu bieten
	@SuppressWarnings("unchecked")
	@Override
	public void addElement(Object obj) {
		super.addElement(obj);
		this.iIndexUsedLast = this.size()-1;		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(Object obj) {
		boolean bReturn = super.add(obj);		
		this.iIndexUsedLast = this.size()-1;
		return bReturn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void add(int iIndex, Object obj) {
		super.add(iIndex, obj);
		this.iIndexUsedLast = iIndex;		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(Collection col) {
		boolean bReturn = super.addAll(col);
		this.iIndexUsedLast = this.size()-1;
		return bReturn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(int iIndex, Collection col) {
		boolean bReturn = super.addAll(iIndex, col);
		this.iIndexUsedLast = this.size()-1;		
		return bReturn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void insertElementAt(Object obj, int iIndex) {
		super.insertElementAt(obj, iIndex);
		this.iIndexUsedLast = iIndex;
	}
	
	
	
	
	//### aus IVectorZZZ
	@Override
	public int sizeNext() {
		return this.size() + 1;
	}
	
	@Override
	public Object replace(int iIndex, Object obj) throws ExceptionZZZ{
		Object objReturn = null;
		
		main:{
			if(this.get(iIndex)!=null) {
				this.remove(iIndex);
			}
			this.add(iIndex, obj);
			this.iIndexUsedLast = iIndex;
			objReturn = obj;
		}//end main:
		
		return objReturn;
	}
	
	//##################
	//
	// getIndexOfString: search a string case-dependent or case-independent
	//                   in a vector and return it's index or -1, if not found
	//                   
	@Override
	public int getIndexOfString(String string, boolean ignoreCase, int fromIndex) {
		int tempIndex = -1;
		for (int i=fromIndex;i<this.size();i++) {
			if (this.elementAt(i) instanceof String) {
				if (!ignoreCase) {
					if (((String) this.elementAt(i)).equals(string)){
						tempIndex = i;
						break; //FGL 20060423 Performance and the right value
					}
				} else {
					if (((String) this.elementAt(i)).equalsIgnoreCase(string)){
						tempIndex = i;
						break; //FGL 20060423 Performance and the right value
					}
				}
			}
		}
		return tempIndex;
	}
	
	@Override
	public int getIndexUsedLast() {
		if(this.iIndexUsedLast==-1) {
			return this.size()-1;
		}else {
			return this.iIndexUsedLast;
		}
	}
	
	@Override
	public int getIndexHigh() {
		return this.size()-1;
	}
	
	@Override 
	public int getIndexNext() {
		return this.getIndexHigh() + 1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getElementByIndex(int iIndex) {
		if(iIndex<=-1) {
			return null;
		}else {
			if(iIndex <= this.size()-1) {
				return (T) this.get(iIndex);
			}else {
				return null;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getEntryLast() {
		int iIndexUsed = this.getIndexUsedLast();
		if(iIndexUsed <= -1) {
			return null;
		}else {
			return (T) this.get(iIndexUsed);
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getEntryHigh() {
		int iIndexUsed = this.size()-1;
		if(iIndexUsed <= -1) {
			return null;
		}else {
			return (T) this.get(iIndexUsed);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getEntryLow() {
		if(this.size()>=1) {
			return (T) this.get(0);
		}else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getEntry(int iIndex) {  //stellt einfach sicher, dass keine Exception geworfen wird, falls der Index nicht passt.
		if(this.size()>=iIndex+1) {
			return (T) this.get(iIndex);
		}else {
			return null;
		}
	}
	
	
	/** Durchsucht den aktuellen String-Vector und  gibt alle Werte der Eintr�ge rechts von dem Suchstring zur�ck. 
	 *   Dabei wird von links gesucht.
	 *   
	* @param string
	* @param ignoreCase
	* @param fromIndex
	* @return
	* 
	* lindhauer; 17.08.2012 10:46:20
	 * @throws ExceptionZZZ 
	 */
	@Override
	public Vector rightOfString(String string, boolean ignoreCase, int fromIndex) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
		main:{
			vecReturn = VectorUtilZZZ.rightOfString(this, string, ignoreCase);
		}//end main
		return vecReturn;
	}

	//////////////////////////////////////////////////////////////////////////
	// containsString: The following methods extend the standard method     //
	//                 contains in the Vector object for use of             //
	//                 case-independent strings.                            //
	//////////////////////////////////////////////////////////////////////////

	// containsString works case-independent by default
    public boolean containsString(String string) {

		if (getIndexOfString(string,true,0)==-1) {
			return false;}
		else {
			return true;}
	}

	// but containsString can work case-dependent and case-independent
    public boolean containsString(String string, boolean ignoreCase) {

		if (getIndexOfString(string,ignoreCase,0)==-1) {
			return false;}
		else {
			return true;}

	}
	
	//////////////////////////////////////////////////////////////////////////
	// indexOfString: The following methods extend the standard method      //
	//                indexOf in the Vecotr object for use of               //
	//                case-independent strings.                             //
	//////////////////////////////////////////////////////////////////////////

	// indexOf works case-independent by default
    
    @Override
	public int indexOfString(String string) {
		return getIndexOfString(string, true, 0);
	}

    @Override
	public int indexOfString(String string, int fromIndex) {
		return getIndexOfString(string, true, fromIndex);
	}

	// but index of can work case-dependent and case-independent
    @Override
	public int indexOfString(String string, boolean ignoreCase) {
		return getIndexOfString(string, ignoreCase, 0);
	}

    @Override
	public int indexOfString(String string, boolean ignoreCase, int fromIndex) {
		return getIndexOfString(string, ignoreCase, fromIndex);
	}
	
	//###################################
	// Sortierung
	//###################################
	/**Nur sort() reicht mir nicht. Ich will das steuern, dafür habe ich ja die VectorExtendedZZZ-Klasse im Einsatz. 
	 * https://www.java-examples.com/sort-java-vector-descending-order-using-comparator-example
	 * @param intNew
	 * @return
	 * @throws ExceptionZZZ
	 */
    @Override
	public boolean addSorted(Integer intNew, int iSortDirectionAscDesc) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(intNew==null) break main;
			if(iSortDirectionAscDesc!=ICollectionConstantZZZ.iSORT_DIRECTION_ASCENDING && iSortDirectionAscDesc != ICollectionConstantZZZ.iSORT_DIRECTION_DESCENDING);
			
			Comparator comparator = null;
			if(iSortDirectionAscDesc==ICollectionConstantZZZ.iSORT_DIRECTION_ASCENDING) {
				//Merke: Erst in Java 8 gibt es das 
				//https://stackoverflow.com/questions/3241063/does-a-natural-comparator-exist-in-the-standard-api
				comparator = Collections.reverseOrder(Collections.reverseOrder());//Das ist ein dreckiger Workaround
			}else {
				comparator = Collections.reverseOrder();
			}
			
			//Nun den Vector um das neue Element erweitern
			this.addElement(intNew);
			
			//Anschliessenden sortieren
			
			/*
		      To sort an Vector using comparator use,
		      static void sort(List list, Comparator c) method of Collections class.
		    */		   
		    Collections.sort(this,comparator);
			bReturn = true;
		}//End main:
		return bReturn;
	}
	
	
	//###################################
	// CompareTo vereinfacht einsetzen
	//###################################
    @Override
	public boolean hasAnyElement() {
		boolean bReturn = false;
		main:{
			try {
				Object obj = this.lastElement();//prüfe ob der Vector einfach leer ist. Also noch nicht einmal NULL.			
				bReturn = true;
			}catch(NoSuchElementException nsee) {
				//mache nix.			
			}	
		}//End main:
		return bReturn;		
	}
    
    @Override
	public boolean isLastElementGreaterThan(Integer intToCompare) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(intToCompare==null) break main;
			if(!this.hasAnyElement()) break main;
			
			int itemp = this.compareToLastElement(intToCompare);
			if (itemp>0) {
				bReturn = true;
			}						
		}//end main:
		return bReturn;
	}
	
	/**
	 * @param intToCompare
	 * @return
	 * siehe ApacheCommons:
				//a negative value if c1 < c2, zero if c1 = c2 and a positive value if c1 > c2
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 19.02.2020, 09:13:34
	 */
    @Override
	public int compareToLastElement(Integer intToCompare) throws ExceptionZZZ{
		int iReturn = 0;
		main:{
			if(!this.hasAnyElement()) {
				ExceptionZZZ ez = new ExceptionZZZ("Not any element available '",  iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			Object obj = this.lastElement();			
			if(obj instanceof Integer) {				
				iReturn = ObjectUtils.compare((Comparable) obj,intToCompare);
			}else {
				ExceptionZZZ ez = new ExceptionZZZ("Type of last element is not Integer '",  iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;	
			}
		}//end main
		return iReturn;
	}

    
    /**Overwritten and using an object of jakarta.commons.lang
	 * to create this string using reflection. 
	 * Remark: this is not yet formated. A style class is available in jakarta.commons.lang. 
	 */
	@Override
	public String toString(){
		String sReturn = "";
		sReturn = ReflectionToStringBuilder.toString(this);
		return sReturn;
	}
	
	//### aus Clonable
//	@Override
	//https://www.geeksforgeeks.org/clone-method-in-java-2/
	//Hier wird also "shallow clone" gemacht. Statt einem deep Clone.
	//Beim Deep Clone müssten alle intern verwendeten Objekte ebenfalls neu erstellt werden.	
//	public Object clone() throws CloneNotSupportedException{
//		return super.clone();
//	}
	
	//### aus IObjectZZZ
	//Meine Variante Objekte zu clonen, aber erzeugt nur einen "Shallow Clone".
	@Override
	public Object clonez() throws ExceptionZZZ {
//		try {
			return this.clone();
//		}catch(CloneNotSupportedException e) {
//			ExceptionZZZ ez = new ExceptionZZZ(e);
//			throw ez;
//				
//		}
	}
	
	
    
    //### aus IOutputDebugNormed
	@Override
	public String computeDebugString() throws ExceptionZZZ{		
		String sEntryDelimiter = this.getDebugEntryDelimiter();
		return this.computeDebugString(sEntryDelimiter);
	}
	
	@Override
	public String computeDebugString(String sEntryDelimiter) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			int iSize = this.size();
			if(iSize==0) break main;
			
			for(int i=0; i<= iSize-1; i++ ) {
				T obj = (T) this.get(i);
				if(sReturn == null) {
					sReturn = obj.toString();
				}else {
					sReturn = sReturn + sEntryDelimiter + obj.toString();
				}
			}
			
		}//end main:
		return sReturn;
	}
		
	@Override
	public String getDebugEntryDelimiter() {
		String sEntryDelimiter;			
		if(this.sDebugEntryDelimiterUsed==null){
			sEntryDelimiter = IOutputDebugNormedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
		}else {
			sEntryDelimiter = this.sDebugEntryDelimiterUsed;
		}
		return sEntryDelimiter;
	}
	
	@Override
	public void setDebugEntryDelimiter(String sEntryDelimiter) {
		this.sDebugEntryDelimiterUsed = sEntryDelimiter;
	}	
	
	//### aus ILogZZZ	
		@Override
		public void logLineDate(String sLog) throws ExceptionZZZ {
			ObjectZZZ.logLineDate(this, sLog);
		}
		
		//++++++++++++++++++++++++++++++++++++++++++++++++
		@Override
		public synchronized void logProtocolString(String[] saLog) throws ExceptionZZZ{
			this.logProtocolString(this, saLog); //Merke: In der aehnlichen Methode von KerneleLosgZZZ (also static) "null" statt this
		}
		
		@Override
		public synchronized void logProtocolString(String sLog) throws ExceptionZZZ{
			this.logProtocolString(this, sLog); //Merke: In der aehnlichen Methode von KerneleLosgZZZ (also static) "null" statt this
		}
		
		@Override
		public synchronized void logProtocolString(Object obj, String[] saLog) throws ExceptionZZZ{
			main:{
				if(ArrayUtilZZZ.isNull(saLog)) break main;
				
				if(obj==null) {
					for(String sLog : saLog) {
						this.logProtocolString(sLog);
					}
				}else {
					for(String sLog : saLog) {
						this.logProtocolString(obj, sLog);
					}	
				}
				
			}//end main:
		}
		
		@Override
		public synchronized void logProtocolString(Object obj, String sLog) throws ExceptionZZZ{
			String sLogUsed;
			if(obj==null) {
				sLogUsed = LogStringZZZ.getInstance().compute(sLog);			
			}else {
				sLogUsed = LogStringZZZ.getInstance().compute(obj, sLog);			
			}
			System.out.println(sLogUsed);
		}

		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		@Override
		public synchronized void logProtocolString(String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString) throws ExceptionZZZ {
			this.logProtocolString(this, saLog, ienumaMappedLogString); //Merke: In der aehnlichen Methode von KerneleLosgZZZ (also static) "null" statt this
		}
		
		@Override
		public synchronized void logProtocolString(String sLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
			this.logProtocolString(this, sLog, ienumMappedLogString); //Merke: In der aehnlichen Methode von KerneleLosgZZZ (also static) "null" statt this
		}
		
		@Override
		public synchronized void logProtocolString(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString) throws ExceptionZZZ {
			main:{
				if(ArrayUtilZZZ.isNull(saLog)) break main;
				if(ArrayUtilZZZ.isNull(ienumaMappedLogString)){
					this.logProtocolString(saLog);
					break main;
				}
				
				int iIndex=0;
				if(obj==null) {			
					for(String sLog : saLog) {
						if(ienumaMappedLogString.length>iIndex) {
							this.logProtocolString(sLog,ienumaMappedLogString[iIndex]);
							iIndex++;
						}else {
							this.logProtocolString(saLog);
						}
					}
				}else {
					for(String sLog : saLog) {
						if(ienumaMappedLogString.length>iIndex) {
							this.logProtocolString(obj, sLog,ienumaMappedLogString[iIndex]);
							iIndex++;
						}else {
							this.logProtocolString(saLog);
						}
					}			
				}
			}//end main:
		}
		
		@Override
		public synchronized void logProtocolString(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
			String sLogUsed;
			if(obj==null) {
				sLogUsed = LogStringZZZ.getInstance().compute(sLog, ienumMappedLogString);
			}else {
				sLogUsed = LogStringZZZ.getInstance().compute(obj, sLog, ienumMappedLogString);
			}
			System.out.println(sLogUsed);
		}
}
