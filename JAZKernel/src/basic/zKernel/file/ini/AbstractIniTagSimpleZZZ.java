package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;

public abstract class AbstractIniTagSimpleZZZ<T>  extends AbstractIniTagBasicsZZZ<T> implements IIniTagSimpleZZZ{
	private static final long serialVersionUID = -5785934791199206030L;
	protected IKernelConfigSectionEntryZZZ objEntry = null;

	public AbstractIniTagSimpleZZZ() throws ExceptionZZZ{
		super();
		AbstractKernelIniTagNew_();
	}
		
	private boolean AbstractKernelIniTagNew_() throws ExceptionZZZ {
	 boolean bReturn = false;	
	 main:{
		
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_

	//### Aus IKernelConfigSectionEntryUserZZZ
	public IKernelConfigSectionEntryZZZ getEntry() {
		return this.objEntry;
	}
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) {
		this.objEntry = objEntry;
	}
	
	//######## Getter / Setter ##################
	@Override	
	public String getValue(){
		return this.getEntry().getValue();
	}

	@Override
	public void setValue(String sValue){
		this.getEntry().setValue(sValue);
	}
	
	
	//### Aus IValueBufferedUserZZZ
	@Override 
	public VectorExtendedDifferenceZZZ<String> getValueVector(){
		return this.getEntry().getValueVector();
	}
	
	/**
	 * Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der
	 * ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element
	 * ist der Ausdruck NACH der ersten Expression.
	 * 
	 * @param sLineWithExpression
	 * @throws ExceptionZZZ
	 */
	@Override
	public Vector<String>computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();		
		main:{
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagStarting(), this.getTagClosing(), false, false);
		}
		return vecReturn;
	}
	
	
	@Override
	public String compute() throws ExceptionZZZ {
		String sExpression = this.getValue();
		return this.compute(sExpression);
	}
	
	
	@Override
	public String computeAsExpression() throws ExceptionZZZ {
		String sExpression = this.getValue();
		return this.computeAsExpression(sExpression);
	}	

	@Override
	public String compute(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
				
			//Bei einfachen Tags den Ersten Vektor holen
			Vector<String> vecAll = this.computeExpressionFirstVector(sLineWithExpression);
			
			//Bei einfachen Tags, den Wert zurückgeben
			sReturn = (String) vecAll.get(1);
			this.setValue(sReturn);
			
			//implode NUR bei CASCADED Tags
//			String sExpressionImploded = VectorZZZ.implode(vecAll);
//			sReturn = sExpressionImploded;
//			this.setValue(sReturn);
		}//end main:
		return sReturn;
	}	
	
	@Override
	public String computeAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
			
			Vector<String> vecAll = this.computeExpressionAllVector(sLineWithExpression);
			
			//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
			sReturn = VectorZZZ.implode(vecAll);
			this.setValue(sReturn);
			
		}//end main:
		return sReturn;
	}	
	
	@Override
	public boolean isExpression(String sLine) throws ExceptionZZZ{
		return KernelConfigSectionEntryUtilZZZ.isExpression(sLine, this.getName());
	}
	
	@Override
	public Vector<String>computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = new Vector<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
						
			//Merke: Das ist der Fall, das ein Ausdruck NICHT verschachtelt ist
			//       Für verschachtelte Tags muss hier extra was programmiert und diese Methode ueberschrieben werden.
			vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
			
		}
		return vecReturn;
	}

	@Override
	public Vector<String>computeAsExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = new Vector<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
						
			//Merke: Das ist der Fall, das ein Ausdruck NICHT verschachtelt ist
			//       Für verschachtelte Tags muss hier extra was programmiert und diese Methode ueberschrieben werden.
			vecReturn = this.computeAsExpressionFirstVector(sLineWithExpression);			
			
		}
		return vecReturn;
	}
	
	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	@Override
	public Vector<String>computeAsExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();		
		main:{
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagStarting(), this.getTagClosing(), true, false);
		}
		return vecReturn;
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ computeAsEntry(String sLineWithExpression) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
											
			Vector<String>vecAll = this.computeExpressionAllVector(sLineWithExpression);
			
			//Das ist bei einfachen Tag Werten so
			String sReturn = (String) vecAll.get(1);
			this.setValue(sReturn); //Merke: Internes Entry-Objekt nutzen. Darin wurden in den vorherigen Methoden auch Zwischenergebnisse gespeichert.
			
			//Bei verschachtelten (CASCADED) Tag Werten so 
//			String sExpressionImploded = VectorZZZ.implode(vecAll);
//			this.setValue(sExpressionImploded);
			
			
			objReturn = this.getEntry();			
		}//end main:
		return objReturn;
	}	
	
	@Override
	public String[] computeAsArray(String sLineWithExpression, String sDelimiterIn) throws ExceptionZZZ{
		String[] saReturn = null; //new String[];//sLineWithExpression;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
			
			String sDelimiter;
			if(StringZZZ.isEmpty(sDelimiterIn)) {
				sDelimiter = IniFile.sINI_MULTIVALUE_SEPARATOR; 
			}else {
				sDelimiter = sDelimiterIn;
			}
				   
			String sExpressionTotal = this.compute(sLineWithExpression); //Hole erst einmal den Kernel-Tag-Wert.
			if(!StringZZZ.isEmpty(sExpressionTotal)) {
				String[] saExpression = StringZZZ.explode(sExpressionTotal, sDelimiter); //Dann löse Ihn als Mehrfachwert auf.
				
				String sValue = null;
				ArrayListExtendedZZZ<String> listasValue = new ArrayListExtendedZZZ<String>();
				for(String sExpression : saExpression) {
					
					//Nur für den etwas komplizierteren Fall einer Verschachtelung ...
					if(this.isExpression(sExpression)){
						sValue = this.compute(sExpression);
					}else {
						sValue = sExpression;
					}
					listasValue.add(sValue);
				}
								
				saReturn = listasValue.toStringArray();				
			}
		}//end main:
		return saReturn;
	}
	

	
}//End class