package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;
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

public abstract class AbstractKernelIniTagSimpleZZZ  extends AbstractKernelUseObjectZZZ implements IKernelZTagIniZZZ{
	private IKernelConfigSectionEntryZZZ objEntry = null;
	
	public AbstractKernelIniTagSimpleZZZ() throws ExceptionZZZ{
		String[] saFlag = {"init"};
		AbstractKernelIniTagNew_(saFlag);
	}
		
	public AbstractKernelIniTagSimpleZZZ(String[] saFlag) throws ExceptionZZZ{		
		AbstractKernelIniTagNew_(saFlag);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		AbstractKernelIniTagNew_(null);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		AbstractKernelIniTagNew_(saFlag);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ{
		super(objKernelUsing);
		AbstractKernelIniTagNew_(null);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ{
		super(objKernelUsing);
		AbstractKernelIniTagNew_(saFlag);
	}
	
	
	private boolean AbstractKernelIniTagNew_(String[] saFlagControlIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
	 main:{
		 	
	 	//try{	 		
	 			//setzen der uebergebenen Flags	
				if(saFlagControlIn != null){
					for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
						stemp = saFlagControlIn[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){
							ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
							throw ez;		 
						}
					}
					if(this.getFlag("init")==true){
						bReturn = true;
						break main;
					}
				}			
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	//### aus IValueSolvedUserZZZ

	@Override
	public VectorExtendedDifferenceZZZ<String> getValueVector() {
		return this.getEntry().getValueVector();
	}

	@Override
	public String getValue() {
		return this.getEntry().getValue();
	}

	@Override
	public void setValue(String sValue) {
		this.getEntry().setValue(sValue);
	}

	@Override
	public VectorExtendedDifferenceZZZ<String> getRawVector() {
		return this.getEntry().getRawVector();
	}

	@Override
	public String getRaw() {
		return this.getEntry().getRaw();
	}

	@Override
	public void setRaw(String sRaw) {
		this.getEntry().setValue(sRaw);
	}
	//##############################
	
	
	
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

	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	public Vector<String>computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();		
		main:{
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getExpressionTagStarting(), this.getExpressionTagClosing(), false, false);
		}
		return vecReturn;
	}
	
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
	public Vector<String>computeAsExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();		
		main:{
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getExpressionTagStarting(), this.getExpressionTagClosing(), true, false);
		}
		return vecReturn;
	}
	
	public boolean isExpression(String sLine) throws ExceptionZZZ{
		return KernelConfigEntryUtilZZZ.isExpression(sLine, this.getExpressionTagName());
	}
		
	//###### Getter / Setter
	public abstract String getExpressionTagName();
	
	public String getExpressionTagStarting() throws ExceptionZZZ{
		return AbstractKernelIniTagSimpleZZZ.computeExpressionTagStarting(this.getExpressionTagName());		
	}
	public String getExpressionTagClosing() throws ExceptionZZZ{
		return AbstractKernelIniTagSimpleZZZ.computeExpressionTagClosing(this.getExpressionTagName());		
	}	
	public String getExpressionTagEmpty()throws ExceptionZZZ{
		return AbstractKernelIniTagSimpleZZZ.computeExpressionTagEmpty(this.getExpressionTagName());		
	}
	
	public static String computeExpressionTagStarting(String sTagName) throws ExceptionZZZ{
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractKernelIniTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return "<" + sTagName + ">";
	}
	
	public static String computeExpressionTagClosing(String sTagName)throws ExceptionZZZ{
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractKernelIniTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return "</" + sTagName + ">"; 
	}
	
	public static String computeExpressionTagEmpty(String sTagName)throws ExceptionZZZ{
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractKernelIniTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return "<" + sTagName + "/>";
	}
	

	//### Aus Interface IKernelZTagIniZZZ			
	/* (non-Javadoc)
	 * @see basic.zKernel.file.ini.IKernelZTagIniZZZ#compute(java.lang.String)
	 */
	@Override
	public String compute(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
						
			Vector<String> vecAll = this.computeExpressionFirstVector(sLineWithExpression);
			
			//Bei einfachen Tags, den Wert zurückgeben
			sReturn = (String) vecAll.get(1);
			this.setValue(sReturn);
			
			//implode nur bei CASCADED Tags
//			String sExpressionImploded = VectorZZZ.implode(vecAll);
//			this.setValue(sExpressionImploded);
			
								
		}//end main:
		return sReturn;
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
				ArrayListExtendedZZZ listasValue = new ArrayListExtendedZZZ();
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
	
	@Override
	public String computeAsExpression(String sLineWithExpression) throws ExceptionZZZ{
	String sReturn = sLineWithExpression;
	main:{
		if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
		
		Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);
		
		//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
		sReturn = VectorZZZ.implode(vecAll);
		this.setValue(sReturn);
		
	}//end main:
	return sReturn;
	}	
	
	public IKernelConfigSectionEntryZZZ getEntry() {
		if(this.objEntry==null) {
			this.objEntry = new KernelConfigSectionEntryZZZ();			
		}
		return this.objEntry;
	}
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) {
		this.objEntry = objEntry;
	}
}//End class