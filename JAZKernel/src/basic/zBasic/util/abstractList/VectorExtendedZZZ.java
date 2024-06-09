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

import java.util.*;

import org.apache.commons.lang.ObjectUtils;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;

/** 20170725: Diese Klasse Generisch gemacht. Dabei den Klassennamen analog zur HashMapExtended gewählt. 
 *                    Die alte Klasse beibehalten als nicht genrisch...
 *                    TODO: Hier Methoden ggfs. "TypeCast sicher überschreiben
 *                    
 *  20200219: Die Methoden aus der alten klasse in die neue Klasse übernommen
 */
@SuppressWarnings("rawtypes")
public class VectorExtendedZZZ<T> extends Vector implements IConstantZZZ, ICollectionConstantZZZ{
	private static final long serialVersionUID = 1L;
	protected int iIndexUsedLast=-1;
	
	public VectorExtendedZZZ(Vector initVector) {
		for (int i=0; i<initVector.size(); i++)
			this.addElement(initVector.elementAt(i));
	}

	public VectorExtendedZZZ() {
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
	
	
	
	
	
	
	//##################
	//
	// getIndexOfString: search a string case-dependent or case-independent
	//                   in a vector and return it's index or -1, if not found
	//                   

	int getIndexOfString(String string, boolean ignoreCase, int fromIndex) {
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
	
	public int getIndexUsedLast() {
		if(this.iIndexUsedLast==-1) {
			return this.size()-1;
		}else {
			return this.iIndexUsedLast;
		}
	}
	@SuppressWarnings("unchecked")
	public T getEntryLast() {
		int iIndexUsed = this.getIndexUsedLast();
		return (T) this.get(iIndexUsed);
	}
	
	@SuppressWarnings("unchecked")
	public T getEntryHigh() {
		int iIndexUsed = this.size()-1;
		return (T) this.get(iIndexUsed);
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
	public Vector rightOfString(String string, boolean ignoreCase, int fromIndex) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
		main:{
			vecReturn = VectorZZZ.rightOfString(this, string, ignoreCase);
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

	public int indexOfString(String string) {
		return getIndexOfString(string, true, 0);
	}

	public int indexOfString(String string, int fromIndex) {
		return getIndexOfString(string, true, fromIndex);
	}

	// but index of can work case-dependent and case-independent

	public int indexOfString(String string, boolean ignoreCase) {
		return getIndexOfString(string, ignoreCase, 0);
	}

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
}
