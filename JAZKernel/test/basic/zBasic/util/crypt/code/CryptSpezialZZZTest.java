package basic.zBasic.util.crypt.code;

import java.util.EnumSet;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.EnumSetTestFactoryZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;

public class CryptSpezialZZZTest  extends TestCase{
	 
	    protected void setUp(){
	      
		//try {			
		
			//### Das spezielle Generics Testobjekt			
//			hmTestGenerics = new HashMapExtendedZZZ<String, EnumSetMappedTestTypeZZZ>();
//			
//			Set<EnumSetMappedTestTypeZZZ> allTypes = EnumSet.allOf(EnumSetMappedTestTypeZZZ.class);
//			for(EnumSetMappedTestTypeZZZ myType : allTypes) {
//				//String sType = myType.getAbbreviation();
//				String sName = myType.name();
//				hmTestGenerics.put(sName,myType);
//			}
			
		
			
		/*
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
		*/
		
	}//END setup
	    public void testCryptCompare_ROTnn_VigenereNN(){
			try{				
				String sInput = "A1new2new!ftp"; 
				String sOutputExpected = "F6sjB7sjBdkyu";
				String[]saFlag= {"USEUPPERCASE","USENUMERIC","USELOWERCASE","USEADDITIONALCHARACTER"};
				String sCharacterPoolBase=" abcdefghijklmnopqrstuvwxyz";
				String sCharacterPoolAdditional="!";
				String sCipher=null;
				boolean bFound;
				
				//### 1. Hole die ROTnn - Verschlüsselung
				sCipher = CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTnn.getAbbreviation();
				ICryptZZZ objROTnn = CryptAlgorithmFactoryZZZ.createAlgorithmTypeByCipher(sCipher);
				assertNotNull(objROTnn);				
				
				//TODO: Biete das Array an... objROTnn.setFlag(saFlag, true);				
				for(String sFlag:saFlag) {
					bFound = objROTnn.setFlag(sFlag, true);
					assertTrue("Benoetigtes Flag nicht vorhanden: '"+sFlag+"'", bFound);
				}
				
				objROTnn.setCharacterPoolBase(sCharacterPoolBase);
				objROTnn.setCharacterPoolAdditional(sCharacterPoolAdditional);
				objROTnn.setCryptNumber(5);
				String sReturnROTnn = objROTnn.encrypt(sInput);
				assertTrue("Abweichung vom erwarteten Wert", sReturnROTnn.equals(sOutputExpected));
				
				//### 2. Hole die VigenereNN - Verschlüsselung
				sCipher = CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.VIGENEREnn.getAbbreviation();
				ICryptZZZ objVigenereNN = CryptAlgorithmFactoryZZZ.createAlgorithmTypeByCipher(sCipher);
				assertNotNull(objVigenereNN);				
				
				//TODO: Biete das Array an... objROTnn.setFlag(saFlag, true);				
				for(String sFlag:saFlag) {
					bFound = objVigenereNN.setFlag(sFlag, true);
					assertTrue("Benoetigtes Flag nicht vorhanden: '"+sFlag+"'", bFound);
				}
				
				objVigenereNN.setCharacterPoolBase(sCharacterPoolBase);
				objVigenereNN.setCharacterPoolAdditional(sCharacterPoolAdditional);
				objVigenereNN.setCryptKey("eeeee");
				String sReturnVigenereNN = objVigenereNN.encrypt(sInput);
				assertTrue("Abweichung vom erwarteten Wert", sReturnVigenereNN.equals(sOutputExpected));
									
				
				//### 3. Wg. der CryptNumber 5 = Buchstabe "e" Beziehung ist das Ergebnis immer gleich.
				assertEquals("Abweichung: Ergebnisse der beiden Verschluesselungen sollt immer gleich sein.", sReturnVigenereNN,sReturnROTnn);
				
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    
	   
	    
	
	}//end class
	
