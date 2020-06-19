package basic.zBasic.util.file.zip;

import java.util.zip.ZipEntry;

public interface ZipEntryFilter {
	public boolean accept( ZipEntry ze);
}
