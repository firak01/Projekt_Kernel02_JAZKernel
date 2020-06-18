package basic.zBasic.util.file;

import java.util.zip.ZipEntry;

public interface ZipEntryFilter {
	public boolean accept( ZipEntry ze);
}
