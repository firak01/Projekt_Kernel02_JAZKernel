package basic.zBasic.util.file.zip;

import java.io.FilenameFilter;

import basic.zBasic.ExceptionZZZ;

public interface IFileDirectoryPartFilterZipZZZ extends ZipEntryFilter {
	public void setCriterion(String sCriterion) throws ExceptionZZZ;
	public String getCriterion();
}
