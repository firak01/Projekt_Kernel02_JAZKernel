package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import base.xml.XMLUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ;
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
			
			TODOGOON 20230322;//Mache XMLUtilZZZ basierend(aber nicht erbend!!!) aus XMLUtil. D.h. uebernimm einige sinnvolle Klassen.
			                  //Beachte auch UtfEasyZZZ und FileEncodingUtil, ergaenze dort ggfs. einige Methoden.
			                  
			
			Document objDocument = KernelZFormulaIniLineZZZ.createDocument("Z");											
			org.jdom.Element objRoot = objDocument.getRootElement();
			
			//Merke: Aus KernelZFormulaIniSolverTest. Dort wird im setup die Datei aufgebaut und eine Zeile hat z.B. folgende Struktur:
			//objStreamFile.println("WertCencrypted=<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>"+iKeyNumber+"</z:KeyNumber><z:CharacterPool>"+sCharacterPool+"</z:CharacterPool><z:FlagControl>"+sFlagUppercase+"</Z:FlagControl><Z:Code>"+sEncrypted+"</Z:Code></Z:Encrypted></Z>");
			
			//############
			//Merke: Problematik der gueltigen Zeichen im XML-Elementnamen
			//funktioniert: Element objElementZEncryption = new Element("blabla");
			
			//funktioniert nicht: Element objElementZEncryption = new Element(KernelEncryptionIniSolverZZZ.sTAG_NAME);
			//Das Problem ist, das keine Doppelpunkte im Tag-Element sein duerfen.
			//Das entfernt den Doppelpunkt nicht String stemp = XMLUtil.stripNonValidXMLCharacters(KernelEncryptionIniSolverZZZ.sTAG_NAME);
			//############
			
			//Merke: 0x3a ist der HEXCode in ASCII für den Doppelpunkt
			//siehe:
			//https://stackoverflow.com/questions/655891/converting-utf-8-to-iso-8859-1-in-java-how-to-keep-it-as-single-byte
			//Charset utf8charset = Charset.forName("UTF-8");
			//Charset iso88591charset = Charset.forName("ISO-8859-1");
			//byte[] utf8bytes = { (byte)0xc3, (byte)0xa2, 0x61, 0x62, 0x63, 0x64, 0x58, 0x3a };			    
		    //String string = new String ( utf8bytes, utf8charset );//ergibt den String "a mit Dach" a b c d X :
		    //System.out.println(string);

			String stemp = StringZZZ.replace(KernelEncryptionIniSolverZZZ.sTAG_NAME, ":", "_0x3a_");
			Element objElementZEncryption = new Element(stemp);
			
			stemp = StringZZZ.replace(KernelEncryption_CipherZZZ.sTAG_NAME, ":", "_0x3a_");
			Element objElementZCipher = new Element(stemp);
			
			//sCipher = CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTnn.getAbbreviation();
			//Class objClass = CryptAlgorithmMappedValueZZZ.getEnumClassStatic();			
			String sCipher = objCrypt.getCipherType().getAbbreviation();
			objElementZCipher.addContent(sCipher);
						
			objElementZEncryption.addContent(objElementZCipher);
			
			
			//### Merke: Einen Namespace ergaenzen
			//siehe: https://stackoverflow.com/questions/7085503/set-namespace-using-jdom			
			//Namespace objNamespace = Namespace.getNamespace("Z", "http://fgl.homepage.t-online.de");
			//objElementZEncryption.addNamespaceDeclaration(objNamespace);
			//Das ergibt dann folgendes:
			//Z><Z_encrypted xmlns:Z="http://fgl.homepage.t-online.de" /></Z>
			//was aber für Z:XYZ Ausdruecke nicht weiterhilft...
			//####################
			
			
			XMLUtil.addContent(objRoot, objElementZEncryption);			
			objDocument.setRootElement(objRoot);
									
			try{
				//##########################
				//Ausgabe als Datei							
				//File objFileTemp = new File("c:\\temp\\KernelZFormulaIniLineTest.xml");
				//FileWriter objWriter = new FileWriter(objFileTemp); //true = anhaengen
				//XMLOutputter outputter = new XMLOutputter();
				//Format format = outputter.getFormat();				
				//format.setOmitDeclaration(false); //Merke: Bei true  wird die Zeile <?xml version="1.0" encoding="UTF-8"?> weggelassen, was z.B. bei einem HTML-Dokument ggf. f�r falsche Deutsche Umlaute sorgt.
				 
				//format.setOmitEncoding(false);   //Damit die Encoding Zeile angezeigt wird   
				//      //format.setExpandEmptyElements(true); //aus <aaa/> wird dann <aaa></aaa>    
				 //     //format.setTextMode(TextMode.PRESERVE);//Ohne diesen Formatierungshinweis, wird ggf. auch der META-Tag mit /> als Abschluss versehen. Dann funktioniert scheinbar dieser Tag im Browser nicht mehr. Die deutschen Umlaute gehen verloren. 
				//format.setEncoding("ISO-8859-1");      //Ziel: "ISO-8859-1" f�r deutsch       //Ohne diesen Formatierungshinweis wird UTF-8 verwendet. Das bewirkt, dass z.B. die Deutschen Umlaute �, etc. in die korrespondierende HTML-Umschreibung umgewandelt werden. 
				 
			    //outputter.setFormat(format);  //Das muss man machen, sonst sind werden die neuen Format Einstellungen nicht �bernommen				
				//outputter.output(objDocument,objWriter);
				
				//objWriter.close();	
				//###############################
				
				//##############################
				//Ausgabe als String
				//Merke: Das gibt nicht das gewuenschte Ergebnis:
				//stest = objDocument.toString();
								
				StringWriter objWriter = new StringWriter();				
				XMLOutputter outputter = new XMLOutputter();
				
				 Format format = outputter.getFormat();
				 
				 //Ziel: Fuer die Zeile in einer ini-Property einen einfachen String mit XML Tags erzeugen, ohne Dokumentenergaenzungen
				 format.setOmitDeclaration(true); //Merke: Bei true  wird die Zeile <?xml version="1.0" encoding="UTF-8"?> weggelassen, was z.B. bei einem HTML-Dokument ggf. fuer falsche Deutsche Umlaute sorgt.				 
				 format.setOmitEncoding(true);   //Damit die Encoding Zeile angezeigt wird =false, Ohne Encoding Zeile =true   
				 //format.setExpandEmptyElements(true); //aus <aaa/> wird dann <aaa></aaa>    
				 //format.setTextMode(TextMode.PRESERVE);//Ohne diesen Formatierungshinweis, wird ggf. auch der META-Tag mit /> als Abschluss versehen. Dann funktioniert scheinbar dieser Tag im Browser nicht mehr. Die deutschen Umlaute gehen verloren. 
				format.setEncoding("ISO-8859-1");      //Ziel: "ISO-8859-1" fuer deutsch       //Ohne diesen Formatierungshinweis wird UTF-8 verwendet. Das bewirkt, dass z.B. die Deutschen Umlaute ue, etc. in die korrespondierende HTML-Umschreibung umgewandelt werden. 
				 
			    outputter.setFormat(format);  //Das muss man machen, sonst sind werden die neuen Format Einstellungen nicht uebernommen				
				outputter.output(objDocument,objWriter);				
				//Das ergibt auch keinen sinnvollen String nur mit den XML-Tags sReturn = outputter.toString();
				sReturn = objWriter.toString();
				
				//Aus diesem String die "validen" Tags wieder durch Tags mit : ersetzten
				sReturn = StringZZZ.replace(sReturn, "_0x3a_", ":");
				System.out.println("TESTFGL:\n"+sReturn);
				
				
				String sContent = objDocument.toString();				
				objWriter.write(sContent);
				
				
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
