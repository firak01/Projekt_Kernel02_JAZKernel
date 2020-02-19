//////////////////////////////////////////////////////////////////////////////
//                                                                          //
// TYPE			: Java class                                                //
//                                                                          //
// NAME			: ExtendedVector.java                                       //
//                                                                          //
// AUTOR		: Stephan Mecking, CoCoNet GmbH                             //
//				: Peter K�hn, CoCoNet GmbH                                  //
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
// 04.08.1997 | first version                                  | SM, PK     //
//                                                                          //
//////////////////////////////////////////////////////////////////////////////

package basic.zBasic.util.abstractList;

import java.util.*;

import org.apache.commons.lang.ObjectUtils;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;

/** 20170725: Diese Klasse Generisch gemacht. Dabei den Klassennamen analog zur HashMapExtended gewählt. 
 *                    Die alte Klasse beibehalten als nicht genrisch...
 *                    TODO: Hier Methoden ggfs. "TypeCast sicher überschreiben
 *                    
 *  20200219: Die Methoden aus der alten klasse in die neue Klasse übernommen
 *                    */
@SuppressWarnings("rawtypes")
public class VectorExtendedZZZ<T> extends Vector implements IObjectZZZ{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public VectorExtendedZZZ(Vector initVector) {
		for (int i=0; i<initVector.size(); i++)
			this.addElement(initVector.elementAt(i));
	}

	public VectorExtendedZZZ() {
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
	// CompareTo vereinfacht einsetzen
	//###################################
	public boolean isLastElementGreaterThan(Integer intToCompare) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(intToCompare==null) break main;
			
			int itemp = this.compareToLastElement(intToCompare);
			if (itemp>0) {
				bReturn =true;
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

	@Override
	public ExceptionZZZ getExceptionObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExceptionObject(ExceptionZZZ objException) {
		// TODO Auto-generated method stub
		
	}
	

}
