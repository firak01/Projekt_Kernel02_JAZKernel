package basic.zBasic.util.string.formater;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedLogStringFormatUtilZZZ;
import basic.zBasic.util.datatype.enums.EnumSetUtilZZZ;
import basic.zBasic.util.datatype.integer.IntegerArrayZZZ;
import basic.zBasic.util.math.MathZZZ;

/**Von ChatGPT Redundanz verringert am 20260221;
 * @author Fritz Lindhauer, 21.02.2026, 08:49:16
 * 
 */
public class StringFormatManagerUtilZZZ implements IConstantZZZ {

    // #############################################################
    // Öffentliche Hauptmethoden (Hier u.a. Weiterleiten auf besondere Util-Klassen, die gekapselt sind
    // #############################################################

	public static IEnumSetMappedStringFormatZZZ[] adaptFormatArray(IEnumSetMappedStringFormatZZZ[] ienumaFormatStringVorhanden, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringNeu) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[]objaReturn=null;
		main:{
			if(ienumaFormatStringVorhanden==null) {
				objaReturn = ienumaFormatLogStringNeu;
				break main;
			}
			
			if(ienumaFormatLogStringNeu==null) {
				objaReturn = ienumaFormatStringVorhanden;
				break main;
			}
			
			//Hier werden aus dem neuen Array alle Elemente entfernt, die es schon im bestehenden Array gibt.
			//Passiert ueber Differenzmengenbildung
			//aber kein echtes Array, sondern ein enumSet... objaReturn = ArrayUtilZZZ.differenceSet(ienumaFormatStringVorhanden, ienumaFormatLogStringNeu, IEnumSetMappedStringFormatZZZ.class);
			objaReturn = EnumSetMappedLogStringFormatUtilZZZ.differenceMappedArray_rightSet(ienumaFormatStringVorhanden, ienumaFormatLogStringNeu);
			
			//Wenn es keine Differenzmenge gibt, dann ist die Anpassung = AusgangsFormatArray
			if(objaReturn==null || ArrayUtilZZZ.isEmpty(objaReturn)) {
				//System.out.println(ReflectCodeZZZ.getPositionCurrent() +": Keine neuen Felder in der individuellen Formatvorlage. Verwende die Feldreihenfolge wie in zuvor verwendeter (" + ienumaFormatStringVorhanden.length + " Felder) Formatvorlage .");
				objaReturn = ienumaFormatStringVorhanden;
				break main;
			}
			
			//Wenn es eine Differenzmenge gibt, dann wird diese an das Ausgangsformat angehaengt.
			//System.out.println(ReflectCodeZZZ.getPositionCurrent() +": Mische "+objaReturn.length+ " neue Felder der individuelle Formatvorlage in zuvor verwendeter (" + ienumaFormatStringVorhanden.length + " Felder) Formatvorlage ein. ");
			objaReturn = ArrayUtilZZZ.join(ienumaFormatStringVorhanden, objaReturn);
			
		}//end main:
		return objaReturn;
	}
	
    public static IEnumSetMappedStringFormatZZZ[] appendLines_StringType(
            IEnumSetMappedStringFormatZZZ[] formats,
            String... logs) throws ExceptionZZZ {
    	
    	return StringFormatManagerUtil_StringTypeLineAppenderZZZ.appendLines_StringType(formats, logs);
    	
    }

    // #############################################################
}