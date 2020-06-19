package basic.zBasic.util.file.jar;

import basic.zBasic.util.file.SuffixZipEntryFilter;
import basic.zBasic.util.file.zip.ZipEntryFilter;
//import com.ack.tools.jarinfo.JarByteLoader;
//import com.ack.tools.jarinfo.JarInfoException;
//import com.ack.tools.jarinfo.SuffixZipEntryFilter;
//import com.ack.tools.jarinfo.ZipEntryFilter;
//import com.ack.tools.jarinfo.testing.TestJarInfoConstants;
import junit.framework.TestCase;

/**https://java.happycodings.com/other/code64.html
package com.ack.tools.jarinfo.testing;

 * demonstrates how to load resources from
 * a given jar using the JarInfo package.
 */

public class JarResourcesReadingTest extends TestCase {
  
  public JarResourcesReadingTest( String s ) {
    super( s );
  }

  protected void setUp() {
  }

  protected void tearDown() {
  }

  public void testAccessingJarResources() {
    String archiveName = JarInfoTestConstants.kJAR_FILE_ONE;
    ZipEntryFilter zipFilter = new SuffixZipEntryFilter( ".ovpn" );
    //String resourceName = "org/apache/tools/ant/taskdefs/optional/junit/xsl/junit-frames.xsl";
    String resourceName = "template/template_server_TCP_443.ovpn";
    
    JarByteLoader jarByteLoader = null;
    try {
      // load zip entries based upon filter
      jarByteLoader = new JarByteLoader( archiveName, zipFilter );
      System.out.println( "\nJarByteLoader successfully loaded -> " + archiveName );

      // view contents of zip entries match the filter
      System.out.println( jarByteLoader.getJarInfo() );

      //load the specified resource      
      byte[] theBytes = jarByteLoader.getResourceAsBytes( resourceName );
      System.out.println( "\nJarByteLoader loaded " + theBytes.length + " bytes for "
                          + resourceName + " from " + archiveName );

      // print out the resource
      System.out.println( new String( theBytes ) );
    }
    catch( JarInfoException jie ) {    	
      jie.printStackTrace();      
      fail(jie.getDetailAllLast());
    }
  }
}

