package basic.zBasic.util.file.jar;




import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import basic.zBasic.util.file.ByteLoader;
import basic.zBasic.util.file.zip.HtmlZipEntryFilter;
import basic.zBasic.util.file.zip.ZipEntryFilter;
//import com.ack.tools.jarinfo.ByteLoader;
//import com.ack.tools.jarinfo.JarInfo;
//import com.ack.tools.jarinfo.JarInfoException;
//import com.ack.tools.jarinfo.ZipEntryFilter;
import junit.framework.TestCase;


//https://java.happycodings.com/other/code63.html
//package com.ack.tools.jarinfo.testing;
public class JarInfoFeaturesTest extends TestCase {

  public JarInfoFeaturesTest( String s ) {
    super( s );
  }

  protected void setUp() {
  }

  protected void tearDown() {
  }

  public void testJarInfoFeaturesHtmlSizeLimited() {

      // create the HtmlZipEntryFilter and passed is the maximum size in bytes
      ZipEntryFilter htmlFilter = new HtmlZipEntryFilter( 500 );
      
      doTestHtml_(htmlFilter);
  }
  
  public void testJarInfoFeaturesHtmlAll() {
	      // create the HtmlZipEntryFilter without size limit in bytes
	      ZipEntryFilter htmlFilter = new HtmlZipEntryFilter( );

	      doTestHtml_(htmlFilter);
	  }
  
  	  private void doTestHtml_(ZipEntryFilter htmlFilter) {
  		String archiveName = JarInfoTestConstants.kZIP_FILE_ONE;   
  	    try {
  		 // create JarInfo from the archiveName and HtmlZipEntryFilter
  	      System.out.println( "\nreading archive file -> " + archiveName );
  	      JarInfo htmlJarInfo = new JarInfo( archiveName, htmlFilter );
  	      System.out.println( "\nsuccessing scanned in -> " + archiveName + "\n" );

  	      // dump out the filter entries
  	      System.out.println( "\nthese are the filtered html entries: \n\n" + htmlJarInfo );

  	      // load bytes from the filtered ZipEntrys
  	      ZipInputStream zis = null;
  	      try {
  	        // we know archiveFile exists, because htmlJarInfo was created
  	        FileInputStream fis = new FileInputStream( archiveName );
  	        BufferedInputStream bis = new BufferedInputStream( fis );
  	        zis = new ZipInputStream( bis );

  	        // jarInfoZipEntries are ZipEntrys keyed against they ZipEntry name
  	        Hashtable jarInfoZipEntries = htmlJarInfo.zipEntryTable();

  	        // use ByteLoader to load bytes for ZipEntrys in jarInfoZipEntries
  	        ByteLoader byteLoader = new ByteLoader();
  	        Hashtable ht = byteLoader.loadBytesFromZipEntries( zis, jarInfoZipEntries );

  	        if( ht.size() == jarInfoZipEntries.size() ) {
  	          System.out.println( "\nsuccessfully loaded bytes for all entries in the htmlJarInfo" );
  	        }
  	        else {
  	          System.out.println( "\n" + ( jarInfoZipEntries.size() - ht.size() ) +
  	                              " entries couldn't be loaded" );
  	        }
  	      }
  	          // tidy up, and let the outer IOException report the I/O problems
  	      finally {
  	        if( zis != null )
  	          zis.close();
  	      }
  	    }
  	    catch( JarInfoException jie ) {
  	      jie.printStackTrace();
  	      fail(jie.getDetailAllLast());
  	    }
  	    catch( IOException ioe ) {
  	      ioe.printStackTrace();
  	      fail(ioe.getMessage());
  	    }
  	  }
}

