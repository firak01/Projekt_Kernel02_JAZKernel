package basic.zBasic.util.file.zip;

import basic.zBasic.ExceptionZZZ;

public interface IFileFilePartFilterZipUserZZZ extends ZipEntryFilter{
	public void setDirectoryPartFilter(FilenamePartFilterPathZipZZZ objDirectoryFilterZip);
	public FilenamePartFilterPathZipZZZ getDirectoryPartFilter();
	
	public void setNamePartFilter(FilenamePartFilterNameZipZZZ objNameFilterZip);//Der ganze Name
	public FilenamePartFilterNameZipZZZ getNamePartFilter();
		
	public void setPrefixPartFilter(FilenamePartFilterPrefixZipZZZ objPrefixFilterZip);
	public FilenamePartFilterPrefixZipZZZ getPrefixPartFilter();
		
	public void setMiddlePartFilter(FilenamePartFilterMiddleZipZZZ objMiddleFilterZip);
	public FilenamePartFilterMiddleZipZZZ getMiddlePartFilter();
	
	
	public void setSuffixPartFilter(FilenamePartFilterSuffixZipZZZ objSuffixFilterZip);
	public FilenamePartFilterSuffixZipZZZ getSuffixPartFilter();
	
	public void setEndingPartFilter(FilenamePartFilterEndingZipZZZ objEndingFilterZip);
	public FilenamePartFilterEndingZipZZZ getEndingPartFilter();
	
	
	public IFilenamePartFilterZipZZZ computeFilePartFilterUsed() throws ExceptionZZZ;
	public String computeCriterionInJarUsed() throws ExceptionZZZ;
	public String computeDirectoryPathInJarUsed() throws ExceptionZZZ;
	public String computeFileNameInJarUsed() throws ExceptionZZZ;
	
}
