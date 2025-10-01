package basic.zBasic;

public interface IOutputDebugNormedWithKeyZZZ extends IOutputDebugNormedZZZ{		
	public static final String sDEBUG_KEY_DELIMITER_DEFAULT = "\t| ";
	
	public String getDebugKeyDelimiter();
	public void setDebugKeyDelimiter(String sKeyDelimiter);
	
	public String computeDebugString(String sEntryDelimiter,String sKeyDelimiter) throws ExceptionZZZ;
}
