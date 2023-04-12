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
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

/**Klasse, in der die Zeile mit einer Z-Formula behandelt wird.
 * Z.B.: Erstelle eine Zeile aus einem übergebenen Objekt heraus (IKernelConfigSectionEntryZZZ)
 *   * 
 * @author Fritz Lindhauer, 20.03.2023, 12:21:15
 * 
 */
public class KernelZFormulaIniLineZZZ  extends ObjectZZZ{
	private static final long serialVersionUID = 890064280935919576L;
	
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
				if(objCrypt!=null) {
					String sValue = objEntry.getValue();
					if(objEntry.isEncrypted()) {
						sReturn = KernelZFormulaIniLineZZZ.createLineFromEncrypted(sValue,objCrypt);
					}else {
						sReturn = KernelZFormulaIniLineZZZ.createLineFrom(sValue,objCrypt);
					}
				}							
			}
		}
		return sReturn;
	}
	
	public static String createLineFrom(String sValue, ICryptZZZ objCrypt) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(objCrypt==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'KernelConfigSectionEntry Object'",iERROR_PARAMETER_MISSING, KernelZFormulaIniLineZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//1. Verschluesseln
			String sValueEncrypted = objCrypt.encrypt(sValue);
			
			//2. Zeile erzeugen
			sReturn = KernelZFormulaIniLineZZZ.createLineFromEncrypted(sValueEncrypted, objCrypt);
			
		}//end main
		return sReturn;
	}
	
	public static String createLineFromEncrypted(String sValue, ICryptZZZ objCrypt) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(objCrypt==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'KernelConfigSectionEntry Object'",iERROR_PARAMETER_MISSING, KernelZFormulaIniLineZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			              			
			//Merke: Aus KernelZFormulaIniSolverTest. Dort wird im setup die Datei aufgebaut und eine Zeile hat z.B. folgende Struktur:
			//objStreamFile.println("WertCencrypted=<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>"+iKeyNumber+"</z:KeyNumber><z:CharacterPool>"+sCharacterPool+"</z:CharacterPool><z:FlagControl>"+sFlagUppercase+"</Z:FlagControl><Z:Code>"+sEncrypted+"</Z:Code></Z:Encrypted></Z>");
			//bzw:     //TestGetParameter_Encrypted=<Z><Z:Encrypted><Z:Cipher>VigenereNn</Z:Cipher><z:KeyString>Hundi</z:KeyString><z:CharacterPool> abcdefghijklmnopqrstuvwxyz</z:CharacterPool><z:CharacterPoolAdditional>!</z:CharacterPoolAdditional><z:FlagControl>USEUPPERCASE,USENUMERIC,USELOWERCASE,USEADDITIONALCHARACTER</Z:FlagControl><Z:Code>pzGxiMMtsuOMsmlPt</Z:Code></Z:Encrypted></Z>
			Document objDocument = XmlUtilZZZ.createDocumentWithRootTag("Z");
			org.jdom.Element objRoot = objDocument.getRootElement();
									
			String stemp = XmlUtilZZZ.replaceColonInXmlTag(KernelEncryptionIniSolverZZZ.sTAG_NAME);
			Element objElementZEncryption = new Element(stemp);
			
			stemp = XmlUtilZZZ.replaceColonInXmlTag(KernelEncryption_CipherZZZ.sTAG_NAME);
			Element objElementZCipher = new Element(stemp);
			
			//Alternative Ansätze, aber jetzt hat ICryptZZZ die Methode getCipherType(), da braucht man die Workarounds nicht
			//sCipher = CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTnn.getAbbreviation();
			//Class objClass = CryptAlgorithmMappedValueZZZ.getEnumClassStatic();			
			String sCipher = objCrypt.getCipherType().getAbbreviation();
			objElementZCipher.addContent(sCipher);						
			objElementZEncryption.addContent(objElementZCipher);
			
			stemp = XmlUtilZZZ.replaceColonInXmlTag(KernelEncryption_CodeZZZ.sTAG_NAME);
			Element objElementZCode = new Element(stemp);	
			String sCode = sValue;
			objElementZCode.addContent(sCode);
			objElementZEncryption.addContent(objElementZCode);
		
			TODOGOON20230412;//Es fehlen noch: 
			//z:KeyNumber
			//z:KeyString
			//z:CharacterPool
			//z:CharacterPoolAdditional
			//z:FlagControl
			
			XMLUtil.addContent(objRoot, objElementZEncryption);			
			objDocument.setRootElement(objRoot);
			String sXmlReplaced = XmlUtilZZZ.documentToString(objDocument);

			//Aus diesem String die "validen" Tags wieder durch Tags mit : ersetzten, was dann der ZExpression entspricht
			sReturn = XmlUtilZZZ.placeColonInZExpression(sXmlReplaced);
		}//end main
		return sReturn;
	}
	
	
}
