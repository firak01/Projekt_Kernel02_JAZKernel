package basic.zBasic;

public interface IOutputDebugNormedWithKeyZZZ extends IOutputDebugNormedZZZ{		
	public static final String sDEBUG_KEY_DELIMITER_DEFAULT = "\t| ";
	
	public String getDebugKeyDelimiter() throws ExceptionZZZ;
	public void setDebugKeyDelimiter(String sKeyDelimiter) throws ExceptionZZZ;
	
	public String computeDebugString(String sEntryDelimiter,String sKeyDelimiter) throws ExceptionZZZ;
}
