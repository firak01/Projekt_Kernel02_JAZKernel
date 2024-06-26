package basic.zBasic.formula;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.formula.AbstractIniTagSimpleZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigEntryUtilZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public abstract class AbstractIniTagCascadedZZZ extends AbstractIniTagSimpleZZZ {
	public AbstractIniTagCascadedZZZ() throws ExceptionZZZ {
		super();
	}

	public AbstractIniTagCascadedZZZ(String[] saFlag) throws ExceptionZZZ {
		AbstractKernelIniTagNew_(saFlag);
	}

	private boolean AbstractKernelIniTagNew_(String[] saFlagControlIn) throws ExceptionZZZ {
		boolean bReturn = false;
		String stemp;
		boolean btemp;
		main: {

			// try{
			// setzen der uebergebenen Flags
			if (saFlagControlIn != null) {
				for (int iCount = 0; iCount <= saFlagControlIn.length - 1; iCount++) {
					stemp = saFlagControlIn[iCount];
					btemp = setFlag(stemp, true);
					if (btemp == false) {
						ExceptionZZZ ez = new ExceptionZZZ("the flag '" + stemp + "' is not available.",
								IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}
				if (this.getFlag("init") == true) {
					bReturn = true;
					break main;
				}
			}
		} // end main:
		return bReturn;
	}// end function KernelExpressionMathSolverNew_

	// In den erbenden Klassen werden darin die enthaltenden Tags aufgelöst
	abstract public Vector<String> computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ;

		
	//### Aus Interface IKernelExpressionIniZZZ		
	@Override
	public String compute(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			Vector<String>vecAll = this.computeExpressionAllVector(sLineWithExpression);
			
			//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
			sReturn = VectorZZZ.implode(vecAll);
			
		}//end main:
		return sReturn;
	}

	@Override
	public IKernelConfigSectionEntryZZZ computeAsEntry(String sLineWithExpression) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); // Hier schon die Rückgabe
																					// vorbereiten, falls eine weitere
																					// Verarbeitung nicht konfiguriert
																					// ist.
		main: {
			if (StringZZZ.isEmptyTrimmed(sLineWithExpression))
				break main;

			Vector<String> vecAll = this.computeExpressionAllVector(sLineWithExpression);

			// Bei einfachen Tag Werten reicht das...
			// String sReturn = (String) vecAll.get(1);
			// this.setValue(sReturn); //Merke: Internes Entry-Objekt nutzen. Darin wurden
			// in den vorherigen Methoden auch Zwischenergebnisse gespeichert.

			// ...bei verschachtelten (CASCADED) Werten aber zusammenfassen.
			String sExpressionWithTags = VectorZZZ.implode(vecAll);// Der String hat noch alle Z-Tags
			objReturn = this.getEntry();

			// An dieser Stelle die Tags vom akuellen "Solver" Rausnehmen
			String sTagStart = this.getExpressionTagStarting();
			String sTagEnd = this.getExpressionTagClosing();
			String sExpression = KernelConfigEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionWithTags, sTagStart, sTagEnd);
			
			
			// Z-Tags entfernen ... wichtig.
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			String sValue = KernelConfigEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);
						
			// Den gerade errechneten Wert setzen
			objReturn.setValue(sValue);
			
			// Bei verschachtelten (CASCADED) Tag Werten aber noch ergänzen um den
			// Expression Ausdruck mit Z-Tags
			// Damit kann ggfs. weiter gearbeitet werden
			objReturn.setValueAsExpression(sExpressionWithTags,false);//Da wir oben schon mit Tag-Expression haben, hier nicht weiter erzwingen.
			
		} // end main:
		return objReturn;
	}

}// End class