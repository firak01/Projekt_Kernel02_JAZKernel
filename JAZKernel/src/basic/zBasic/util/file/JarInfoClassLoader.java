package basic.zBasic.util.file;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureClassLoader;

/**https://java.happycodings.com/other/code28.html
 * 
 * This extension to a the SecureClassLoader is responsible for
 * loading classes from the supplied zip/jar file.  These classes
 * will be loaded if they cannot be found using the parent
 * class loader.
 *
 * @see java.security.SecureClassLoader
 */
public class JarInfoClassLoader extends SecureClassLoader {
  private JarClassByteCache fClassTable;
  private String fJarURLPath;

  /**
   * Create a class loader that will read the files
   * from the supplied zip/jar file
   * @param the zip/jar file to check for loaded classes
   * @exception is thrown if any errors occur during the
   *  reading of the jar/zip file
   */
  public JarInfoClassLoader( String zipFile ) throws JarInfoException {
    this( zipFile, ClassLoader.getSystemClassLoader() );
  }

  /**
   * Create a class loader that will read the files
   * from the supplied zip/jar file
   * @param the zip/jar file to check for loaded classes
   * @param the parent class loader for this class
   * @exception is thrown if any errors occur during the
   *  reading of the jar/zip file
   */
  public JarInfoClassLoader( String zipFile, ClassLoader cl ) throws JarInfoException {
    super( cl );
    fClassTable = new JarClassByteCache( zipFile );
  }

  /**
   * accessor method to get hold of the byte cache
   *
   * @return the jar byte cache of classes
   */
  public JarClassByteCache getClassCache() {
    return fClassTable;
  }

  /**
   * Under Java 2 this method is called to find a class.
   * It is based on new delegation model for loading classes,
   * and will be called by the loadClass method after checking
   * the parent class loader for the requested class.  The parent
   * class loader is supplied by the constructors of this class.
   */
  public Class findClass( String className ) throws ClassNotFoundException {
    // get the bytes for a classes from the class table
    byte[] b = null;
    try {
      b = fClassTable.getClassBytes( className );
    }
    catch( JarInfoException jie ) {
    }

    // and if there are some, use the inherited method defineClass
    // to convert a these bytes into an instance of a Class
    if( b != null ) {
      return defineClass( className, b, 0, b.length );
    }

    // otherwise if there is not such class
    return null;
  }

  /**
   * check to see if the supplied resource is in the jar.  This method
   * is call if all other attempts by the Java Runtime to find this
   * resource failed.
   *
   *@param the name of the resource
   *@return the URL for the resource
   *@see java.lang.ClassLoader#findResource
   */
  protected URL findResource( String resource ) {
    if( fClassTable.getJarInfo().hasResource( resource ) ) {
      try {
        return new URL( getJarURL() + resource );
      }
      catch( MalformedURLException mue ) {
      }
    }
    return null;
  }

  /**
   *@return the URL for the supplied jar
   */
  private String getJarURL() {
    if( fJarURLPath == null ) {
      StringBuffer buf = new StringBuffer( "jar:file:" );
      String jarFile = fClassTable.getJarInfo().zipFileName();
      buf.append( jarFile.replace( '\\', '/' ) );
      buf.append( "!/" );
      fJarURLPath = buf.toString();
    }
    return fJarURLPath;
  }
}

