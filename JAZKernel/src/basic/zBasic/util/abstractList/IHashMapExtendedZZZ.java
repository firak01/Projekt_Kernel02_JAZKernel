package basic.zBasic.util.abstractList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IOutputDebugNormedWithKeyZZZ;
import basic.zBasic.IOutputDebugNormedZZZ;

public interface IHashMapExtendedZZZ extends IOutputDebugNormedWithKeyZZZ, IConstantZZZ{
	public static final String sDEBUG_KEY_DELIMITER_DEFAULT = IOutputDebugNormedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
	public static final String sDEBUG_ENTRY_DELIMITER_DEFAULT = IOutputDebugNormedWithKeyZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
	
	public static final String sIMPLODE_KEY_DELIMITER_DEFAULT = "=";
	public static final String sIMPLODE_ENTRY_DELIMITER_DEFAULT = ", ";
	public void setImplodeEntryDelimiter(String sEntryDelimiter) throws ExceptionZZZ;
	public String getImplodeEntryDelimiter() throws ExceptionZZZ;
	public void setImplodeKeyDelimiter(String sKeyDelimiter) throws ExceptionZZZ;
	public String getImplodeKeyDelimiter() throws ExceptionZZZ;
	
	public String computeImplodeString() throws ExceptionZZZ;
	public String computeImplodeString(String sEntryDelimiter) throws ExceptionZZZ;
	public String computeImplodeString(String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ;
	
	public String toStringImplode() throws ExceptionZZZ;
	public String toStringImplode(String sEntryDelimiter, String sKeyDelimiter) throws ExceptionZZZ;
}
