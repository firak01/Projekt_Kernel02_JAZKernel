package basic.zBasic.util.file.zip;

import java.util.zip.ZipEntry;

/**
 * ZipEntryFilter for HTML files that are less
 * than <b>fSize</b> bytes in size
 */
public class HtmlZipEntryFilter implements ZipEntryFilter {
  private static final String kHTMLSUFFIX = ".html";
  private long fSize;

  public HtmlZipEntryFilter() {
	  fSize = 0;
  }
  public HtmlZipEntryFilter( long size ) {
    fSize = size;
  }
  
  public boolean accept( ZipEntry ze ) {
    boolean outcome = false;
    if( ze != null ) {
      if(fSize>0) {
    	  if( ze.getName().endsWith( kHTMLSUFFIX ) && ze.getSize() < fSize ) outcome = true;      	  
      }else {
    	  if( ze.getName().endsWith( kHTMLSUFFIX )) outcome = true; 
      }
    }
    return outcome;
  }
}