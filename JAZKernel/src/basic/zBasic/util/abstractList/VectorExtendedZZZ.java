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

/** 20170725: Diese Klasse Generisch gemacht. Dabei den Klassennamen analog zur HashMapExtended gewählt. 
 *                    Die alte Klasse beibehalten als nicht genrisch...
 *                    TODO: Hier Methoden ggfs. "TypeCast sicher überschreiben*/
@SuppressWarnings("rawtypes")
public class VectorExtendedZZZ<T> extends ExtendedVectorZZZ {

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
}
