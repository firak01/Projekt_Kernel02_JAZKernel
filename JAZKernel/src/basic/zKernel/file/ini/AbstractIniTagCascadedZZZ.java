package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.AbstractObjectWithFormulaZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;

public abstract class AbstractIniTagCascadedZZZ<T> extends AbstractIniTagComputableZZZ<T> {
	private static final long serialVersionUID = 3041123191138631240L;

	public AbstractIniTagCascadedZZZ() throws ExceptionZZZ {
		super();
	}
	
	public AbstractIniTagCascadedZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
		AbstractKernelIniTagNew_();
	}

	public AbstractIniTagCascadedZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractKernelIniTagNew_();
	}

	private boolean AbstractKernelIniTagNew_() throws ExceptionZZZ {
		boolean bReturn = false;		
		main: {			
			if (this.getFlag("init") == true) {
				bReturn = true;
				break main;
			}	
			
			
		} // end main:
		return bReturn;
	}// end function KernelExpressionMathSolverNew_

	// In den erbenden Klassen werden darin die enthaltenden Tags aufgelöst
	//abstract public Vector<String> computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ;

		
	//### Aus Interface IKernelExpressionIniZZZ		
	@Override
	public String compute(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			//Bei CASCADED Tags alle Untertags holen.
			Vector<String>vecAll = this.computeExpressionAllVector(sLineWithExpression);
			
			//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
			sReturn = VectorZZZ.implode(vecAll);
			
		}//end main:
		return sReturn;
	}

	@Override
	public IKernelConfigSectionEntryZZZ computeAsEntry(String sLineWithExpression) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); // Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
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
			String sTagStart = this.getTagStarting();
			String sTagEnd = this.getTagClosing();
			String sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionWithTags, sTagStart, sTagEnd);
			
			
			// Z-Tags entfernen ... wichtig.
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			String sValue = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);
						
			// Den gerade errechneten Wert setzen
			objReturn.setValue(sValue);
			
			// Bei verschachtelten (CASCADED) Tag Werten aber noch ergänzen um den
			// Expression Ausdruck mit Z-Tags
			// Damit kann ggfs. weiter gearbeitet werden
			//objReturn.setValueAsExpression(sExpressionWithTags,false);//Da wir oben schon mit Tag-Expression haben, hier nicht weiter erzwingen.
			objReturn.setValueAsExpression(sExpressionWithTags);
			
		} // end main:
		return objReturn;
	}
	


}// End class