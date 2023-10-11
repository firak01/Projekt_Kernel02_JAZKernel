package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
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

public abstract class AbstractKernelIniTagCascadedZZZ extends AbstractKernelIniTagSimpleZZZ {
	public AbstractKernelIniTagCascadedZZZ() throws ExceptionZZZ {
		super();
	}

	public AbstractKernelIniTagCascadedZZZ(String[] saFlag) throws ExceptionZZZ {
		AbstractKernelIniTagNew_(saFlag);
	}

	public AbstractKernelIniTagCascadedZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
		AbstractKernelIniTagNew_(null);
	}

	public AbstractKernelIniTagCascadedZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ {
		super(objKernel);
		AbstractKernelIniTagNew_(saFlag);
	}

	public AbstractKernelIniTagCascadedZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
		super(objKernelUsing);
		AbstractKernelIniTagNew_(null);
	}

	public AbstractKernelIniTagCascadedZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ {
		super(objKernelUsing);
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

	/**
	 * Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der
	 * ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element
	 * ist der Ausdruck NACH der ersten Expression.
	 * 
	 * @param sLineWithExpression
	 * @return
	 * 
	 * 		lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ
	 */
	public Vector<String> computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ {
		Vector<String> vecReturn = new Vector<String>();
		main: {
			// Bei dem verschachtelten Tag werden die äußeren Tags genommen...
			vecReturn = StringZZZ.vecMid(sLineWithExpression, this.getExpressionTagStarting(), this.getExpressionTagClosing(), false, false);
		}
		return vecReturn;
	}
	
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

			// Bei verschachtelten (CASCADED) Tag Werten aber noch ergänzen um den
			// Expression Ausdruck mit Z-Tags
			// Damit kann ggfs. weiter gearbeitet werden
			objReturn.setValueAsExpression(sExpressionWithTags);

			// An dieser Stelle die Tags vom akuellen "Solver" Rausnehmen
			String sTagStart = this.getExpressionTagStarting();
			String sTagEnd = this.getExpressionTagClosing();
			String sExpression = KernelConfigEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionWithTags,
					sTagStart, sTagEnd);
			
			// Z-Tags entfernen.
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			String sValue = KernelConfigEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);

			// Den gerade errechneten Wert setzen
			objReturn.setValue(sValue);
		} // end main:
		return objReturn;
	}

}// End class