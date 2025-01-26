package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.AbstractObjectWithExpressionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;

public abstract class AbstractIniTagCascadedZZZ<T> extends AbstractIniTagSimpleZZZ<T> {
	private static final long serialVersionUID = 3041123191138631240L;

	public AbstractIniTagCascadedZZZ() throws ExceptionZZZ {
		super();
		AbstractKernelIniTagNew_();
	}

	private boolean AbstractKernelIniTagNew_() throws ExceptionZZZ {
		return true;
	}// end function AbstractKernelIniTagNew_

		
	//### Aus Interface IKernelExpressionIniZZZ		
	@Override
	public String parse(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			//Bei CASCADED Tags alle Untertags holen.
			//TODOGOON: Das muesste bei cascaded aber eigentlich AllVector sein.
			//Vector<String>vecAll = this.parseAllVector(sLineWithExpression);
			Vector<String>vecAll = this.parseFirstVector(sLineWithExpression);
			
			//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
			sReturn = VectorUtilZZZ.implode(vecAll);
			
		}//end main:
		return sReturn;
	}

	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntryNew(String sLineWithExpression) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>(this); // Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main: {
			if (StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
			objReturn.setRaw(sLineWithExpression);
			
			//TODOGOON: Das muesste bei cascaded aber eigentlich AllVector sein.
			//Vector<String> vecAll = this.parseAllVector(sLineWithExpression);
			Vector<String> vecAll = this.parseFirstVector(sLineWithExpression);

			// Bei einfachen Tag Werten reicht das...
			this.setValue(vecAll.get(1)); //Merke: Internes Entry-Objekt nutzen. 
													//Darin wurden in den vorherigen Methoden auch Zwischenergebnisse gespeichert.
			
			// ...bei verschachtelten (CASCADED) Werten aber zusammenfassen.
			String sExpressionWithTags = VectorUtilZZZ.implode(vecAll);// Der String hat noch alle Z-Tags
			
			// An dieser Stelle die Tags vom akuellen "Solver" Rausnehmen
			String sTagStart = this.getTagStarting();
			String sTagEnd = this.getTagClosing();
			String sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionWithTags, sTagStart, sTagEnd);
			
			
			// Z-Tags entfernen ... wichtig.
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			String sValue = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);
						
			// Den gerade errechneten Wert setzen
			// im gleichen Objekt
			this.setValue(sValue);
			
			// im Return Objekt
			objReturn.setValue(sValue);
			if(!sLineWithExpression.equals(sValue)) {
				objReturn.isParseCalled(true);
			}
			
			// Bei verschachtelten (CASCADED) Tag Werten aber noch ergänzen um den
			// Expression Ausdruck mit Z-Tags
			// Damit kann ggfs. weiter gearbeitet werden
			//objReturn.setValueAsExpression(sExpressionWithTags,false);//Da wir oben schon mit Tag-Expression haben, hier nicht weiter erzwingen.
			objReturn.setValueAsExpression(sExpressionWithTags);
			
		} // end main:
		return objReturn;
	}
	


}// End class