package basic.zBasic.util.file.jar;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/** https://java.happycodings.com/other/code27.html
 * package com.ack.tools.jarinfo;
 * 
 * JarClassLoader provides a convenient abstraction for
 * for loading both classes and locating resources from jar
 * files whose locations are specified as URLs. It makes
 * extensive use of the Java 2 URLClassLoader.
 *
 * @see java.net.URLClassLoader
 */
public class JarClassLoader extends URLClassLoader {
  public JarClassLoader( URL url ) {
    super( new URL[]{url} );
  }

  /**
   * Creates a JarClassLoader object a URL string
   *
   * @param url from which to load classes and locating resources
   * @exception reports problems converting string into a URL
   */
  public JarClassLoader( String urlString ) throws MalformedURLException {
    this( new URL( "jar:" + urlString + "!/" ) );
  }

  /**
   * Adds urls to the JarClassLoader search path
   *
   * @param urls from which to load classes and locating resources
   * @exception reports problems converting strings into a URLs
   */
  public void addJarURLs( String[] urlStrings ) throws MalformedURLException {
    if( urlStrings != null )
      for( int i = 0; i < urlStrings.length; i++ )
        addJarURL( urlStrings[i] );
  }

  /**
   * Adds url to the JarClassLoader search path
   *
   * @param url from which to load classes and locating resources
   * @exception reports problems converting string into a URL
   */
  public void addJarURL( String urlString ) throws MalformedURLException {
    if( urlString != null )
      addURL( new URL( "jar:" + urlString + "!/" ) );
  }

}
