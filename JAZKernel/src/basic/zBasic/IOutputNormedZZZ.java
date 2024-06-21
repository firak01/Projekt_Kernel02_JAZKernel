package basic.zBasic;

public interface IOutputNormedZZZ {		
	public static final String sDEBUG_KEY_DELIMITER_DEFAULT = "\t| ";
	public static final String sDEBUG_ENTRY_DELIMITER_DEFAULT = "\n";
	
	public String getDebugEntryDelimiter();
	public void setDebugEntryDelimiter(String sEntryDelimiter);
	
	public String getDebugKeyDelimiter();
	public void setDebugKeyDelimiter(String sEntryDelimiter);
	
	public String computeDebugString() throws ExceptionZZZ;	
	public String computeDebugString(String sKeyDelimiter,String sEntryDelimiter) throws ExceptionZZZ;
}
