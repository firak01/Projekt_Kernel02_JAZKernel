package basic.zBasic.util.string.justifier;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;

public interface IStringJustifierManagerComputerZZZ {

	public ArrayListZZZ<String> compute(ArrayListZZZ<String> listasJagged) throws ExceptionZZZ;
	public ArrayListZZZ<String> compute(ArrayListZZZ<String> listasJagged, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;	
}
