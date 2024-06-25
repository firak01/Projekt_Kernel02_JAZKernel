package basic.zBasic;

public interface IOutputDebugNormedZZZ {			
	public static final String sDEBUG_ENTRY_DELIMITER_DEFAULT = "\n";
	
	public String getDebugEntryDelimiter();
	public void setDebugEntryDelimiter(String sEntryDelimiter);
		
	public String computeDebugString() throws ExceptionZZZ;	
	public String computeDebugString(String sEntryDelimiter) throws ExceptionZZZ;
}
