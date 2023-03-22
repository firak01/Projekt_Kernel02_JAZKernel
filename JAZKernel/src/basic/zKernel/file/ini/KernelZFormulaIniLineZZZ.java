package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import base.xml.XMLUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

/**Klasse, in der die Zeile mit einer Z-Formula behandelt wird.
 * Z.B.: Erstelle eine Zeile aus einem übergebenen Objekt heraus (IKernelConfigSectionEntryZZZ)
 *   * 
 * @author Fritz Lindhauer, 20.03.2023, 12:21:15
 * 
 */
public class KernelZFormulaIniLineZZZ  extends ObjectZZZ{
	private static final long serialVersionUID = 890064280935919576L;
	public static String createLineEncryptedFrom(String sValue, ICryptZZZ objCrypt) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(objCrypt==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'KernelConfigSectionEntry Object'",iERROR_PARAMETER_MISSING, KernelZFormulaIniLineZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			Document objDocument = KernelZFormulaIniLineZZZ.createDocument("Z");
			String stest = objDocument.toString();
			System.out.println("FGLTEST" + stest);
			sReturn = stest;
			
			org.jdom.Element objRoot = objDocument.getRootElement();
			//aus KernelZFormulaIniSolverTest. Dort wird im setup die Datei aufgebaut und eine Zeile hat z.B. folgende Struktur:
			//objStreamFile.println("WertCencrypted=<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>"+iKeyNumber+"</z:KeyNumber><z:CharacterPool>"+sCharacterPool+"</z:CharacterPool><z:FlagControl>"+sFlagUppercase+"</Z:FlagControl><Z:Code>"+sEncrypted+"</Z:Code></Z:Encrypted></Z>");
			
			
			//funktioniert: Element objElementZEncryption = new Element("blabla");
			
			//funktioniert nicht: Element objElementZEncryption = new Element(KernelEncryptionIniSolverZZZ.sTAG_NAME);
			//Das Problem ist, das keine Doppelpunkte im Tag-Element sein duerfen.
			//Das entfernt  den Doppelpunkt nicht String stemp = XMLUtil.stripNonValidXMLCharacters(KernelEncryptionIniSolverZZZ.sTAG_NAME);
			
			String stemp = StringZZZ.replace(KernelEncryptionIniSolverZZZ.sTAG_NAME, ":", "_");
			Element objElementZEncryption = new Element(stemp);
			XMLUtil.addContent(objRoot, objElementZEncryption);
			
			objDocument.setRootElement(objRoot);
			stest = objDocument.toString();
			System.out.println("FGLTEST" + stest);
			sReturn = stest;
			
			TODOGOON 20230322;
			//TODO: Das Dokument als String bekommen
			//TODO: Aus diesem String die "validen" Tags wieder durch Tags mit : ersetzten
			
			try{
				File objFileTemp = new File("c:\\temp\\KernelZFormulaIniLineTest.xml");
				
				FileWriter objWriter = new FileWriter(objFileTemp); //true = anh�ngen
				
				XMLOutputter outputter = new XMLOutputter();
				
				 Format format = outputter.getFormat();				
				 format.setOmitDeclaration(false); //Merke: Bei true  wird die Zeile <?xml version="1.0" encoding="UTF-8"?> weggelassen, was z.B. bei einem HTML-Dokument ggf. f�r falsche Deutsche Umlaute sorgt.
				 
				 format.setOmitEncoding(false);   //Damit die Encoding Zeile angezeigt wird   
				// format.setExpandEmptyElements(true); //aus <aaa/> wird dann <aaa></aaa>    
				 //format.setTextMode(TextMode.PRESERVE);//Ohne diesen Formatierungshinweis, wird ggf. auch der META-Tag mit /> als Abschluss versehen. Dann funktioniert scheinbar dieser Tag im Browser nicht mehr. Die deutschen Umlaute gehen verloren. 
				format.setEncoding("ISO-8859-1");      //Ziel: "ISO-8859-1" f�r deutsch       //Ohne diesen Formatierungshinweis wird UTF-8 verwendet. Das bewirkt, dass z.B. die Deutschen Umlaute �, etc. in die korrespondierende HTML-Umschreibung umgewandelt werden. 
				 
			    outputter.setFormat(format);  //Das muss man machen, sonst sind werden die neuen Format Einstellungen nicht �bernommen				
				outputter.output(objDocument,objWriter);
				
				//String sContent = this.getDocument().toString();				
				//objWriter.write(sContent);
				
				objWriter.close();	
			}catch(IOException ioe){
				ExceptionZZZ ez = new ExceptionZZZ("IOException: " + ioe.getMessage(), iERROR_RUNTIME, KernelZFormulaIniLineZZZ.class,ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
			
		
		}//end main
		return sReturn;
	}
	public static String createLineFrom(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(objEntry==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'KernelConfigSectionEntry Object'",iERROR_PARAMETER_MISSING, KernelZFormulaIniLineZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(!objEntry.isExpression()) {
				sReturn = objEntry.getProperty() + "=" + objEntry.getValue();
			}else {
				ICryptZZZ objCrypt = objEntry.getCryptAlgorithmType();
				sReturn = KernelZFormulaIniLineZZZ.createLineFrom(objCrypt);				
			}
			
			
		}
		return sReturn;
	}
	
	public static String createLineFrom(ICryptZZZ objCrypt) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(objCrypt==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'KernelConfigSectionEntry Object'",iERROR_PARAMETER_MISSING, KernelZFormulaIniLineZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			Document objDocument = KernelZFormulaIniLineZZZ.createDocument("Z");
			String stest = objDocument.toString();
			System.out.println("FGLTEST" + stest);
			sReturn = stest;
				
		
		}//end main
		return sReturn;
	}
	
	/** Hiermit wird das Dokument erstellt und auch schon das erste Root-Element.
	* 
	* 
	* lindhauer; 22.02.2008 07:06:39
	 * @throws ExceptionZZZ 
	 */
	private static Document createDocument(String sRootName) throws ExceptionZZZ{
		Document objReturn = new Document();
		main:{
			if(StringZZZ.isEmpty(sRootName)){
				ExceptionZZZ ez = new ExceptionZZZ("RootName", iERROR_PARAMETER_MISSING, KernelZFormulaIniLineZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			Element elem = new Element(sRootName);
			objReturn.setRootElement(elem);
		}//end main:
		return objReturn;
	}
}
