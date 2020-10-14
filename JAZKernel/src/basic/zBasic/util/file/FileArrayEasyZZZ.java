package basic.zBasic.util.file;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.regex.Matcher;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.KernelKernelZZZ;
import basic.zKernel.file.ini.KernelExpressionIni_EmptyZZZ;
import basic.zKernel.file.ini.KernelExpressionIni_NullZZZ;

/**Einfache Dateioperationen
 * @author lindhaueradmin
 *
 */
public class FileArrayEasyZZZ extends ObjectZZZ{
	
private FileArrayEasyZZZ(){
	//Zum Verstecken des Konstruktors
}

public static boolean contains(File[] objaFile, String sFileName) {
	if (FileArrayEasyZZZ.isEmptyNull(objaFile)) return false;
	
	for(int iCounter = 0; iCounter <= objaFile.length-1; iCounter++) {
		if(objaFile[iCounter]!=null) {
			String sName = objaFile[iCounter].getName();
			if(sName.endsWith(sFileName)){
				return true;
			}
		}
	}
	return false;	 
}

/**returns true if the string is empty or null.
 * FGL: D.h. NULL oder Leerstring 
 * 
 * @param sString
 * @return, 
 *
 * @return boolean
 *
 * javadoc created by: 0823, 24.07.2006 - 08:52:50
 */
public static boolean isEmpty(File[] objaFile){
	if (FileArrayEasyZZZ.isEmptyNull(objaFile)) return true;
	for(int iCounter = 0; iCounter <= objaFile.length-1; iCounter++) {
		File objFile = objaFile[iCounter];
		if(objFile!=null) return false;
	}
	return true;
}

public static boolean isEmptyNull(File[] objaFile){		
	if(objaFile==null) return true;
	if(objaFile.length==0) return true;
	return false;
}



}//END class
