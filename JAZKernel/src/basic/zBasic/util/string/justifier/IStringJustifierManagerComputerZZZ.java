package basic.zBasic.util.string.justifier;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;

public interface IStringJustifierManagerComputerZZZ {

	public String compute(String sJagged) throws ExceptionZZZ;
	public String compute(String sJagged, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ;
	public String compute(String sJagged, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
	public String compute(String sJagged, IEnumSetMappedStringFormatZZZ ienumaFormatLogString) throws ExceptionZZZ;

	
	public ArrayListZZZ<String> compute(ArrayListZZZ<String> listasJagged) throws ExceptionZZZ;
	public ArrayListZZZ<String> compute(ArrayListZZZ<String> listasJagged, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;	
}
