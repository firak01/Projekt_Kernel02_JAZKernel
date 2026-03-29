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

import basic.zBasic.ExceptionZZZ;

/** 20200219: Dies ist eine alte Klasse, die nicht generisch ist.
 *            Neuer Klassename analog zur HashMapExtended gewählt. 
 */
public class ExtendedVectorZZZ extends VectorZZZ {
	
	public ExtendedVectorZZZ(Vector initVector) {
		for (int i=0; i<initVector.size(); i++)
			this.addElement(initVector.elementAt(i));
	}

	public ExtendedVectorZZZ() {
	}
}
