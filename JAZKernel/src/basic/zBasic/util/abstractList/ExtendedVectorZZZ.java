//////////////////////////////////////////////////////////////////////////////
//                                                                          //
// TYPE			: Java class                                                //
//                                                                          //
// NAME			: ExtendedVector.java                                       //
//                                                                          //
// AUTOR		: Stephan Mecking, CoCoNet GmbH                             //
//				: Peter Kühn, CoCoNet GmbH                                  //
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

import basic.zBasic.ExceptionZZZ;

public class ExtendedVectorZZZ extends Vector {


	//
	// getIndexOfString: search a string case-dependent or case-independent
	//                   in a vector and return it´s index or -1, if not found
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
	
	
	/** Durchsucht den aktuellen String-Vector und  gibt alle Werte der Einträge rechts von dem Suchstring zurück. 
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

	public ExtendedVectorZZZ(Vector initVector) {
		for (int i=0; i<initVector.size(); i++)
			this.addElement(initVector.elementAt(i));
	}

	public ExtendedVectorZZZ() {
	}
}
