package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.ICryptUserZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;
import basic.zKernel.IKernelConfigSectionEntryUserZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelEntryReferenceSolveUserZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Merke: Mit dieser abstrakten Klasse werden Redundante Methoden aller Solver vermieden, die Solver fuer ein Child-Tag sind.
 * @author fl86kyvo
 *
 * @param <T>
 */
public abstract class AbstractKernelIniSolver4ChildTagZZZ<T> extends AbstractKernelIniSolverZZZ<T>{
	
	public AbstractKernelIniSolver4ChildTagZZZ() throws ExceptionZZZ{
		super("init");
	}
		
	public AbstractKernelIniSolver4ChildTagZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
	}
			
	public AbstractKernelIniSolver4ChildTagZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
	}
	
	public AbstractKernelIniSolver4ChildTagZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
	}
	
	//Merke: Konstruktoren ohne ini-File machen keinen Sinn und muessen ohne "init"-Flag zu Exception fuehren
	public AbstractKernelIniSolver4ChildTagZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
	}
	
	//Merke: Konstruktoren ohne ini-File machen keinen Sinn und muessen ohne "init"-Flag zu Exception fuehren
	public AbstractKernelIniSolver4ChildTagZZZ(FileIniZZZ<T> objFileIniKernelConfig) throws ExceptionZZZ{
		super(objFileIniKernelConfig); //Inifile als IKernelUserZZZ				
	}
	
	public AbstractKernelIniSolver4ChildTagZZZ(FileIniZZZ<T> objFileIniKernelConfig, String[] saFlag) throws ExceptionZZZ{
		super(objFileIniKernelConfig, saFlag);//Inifile als IKernelUserZZZ		
	}
	
	public AbstractKernelIniSolver4ChildTagZZZ(FileIniZZZ<T> objFileIniKernelConfig, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objFileIniKernelConfig, hmVariable);//Inifile als IKernelUserZZZ		
	}
	
	public AbstractKernelIniSolver4ChildTagZZZ(FileIniZZZ<T> objFileIniKernelConfig, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIniKernelConfig, hmVariable, saFlag);//Inifile als IKernelUserZZZ				
	}
	
	
	public AbstractKernelIniSolver4ChildTagZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objKernel, objFileIni);
	}
	
	public AbstractKernelIniSolver4ChildTagZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objKernel, objFileIni, hmVariable);		
	}
	
	public AbstractKernelIniSolver4ChildTagZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, objFileIni, saFlag);
	}
	
	public AbstractKernelIniSolver4ChildTagZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, objFileIni, hmVariable, saFlag);
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++
	//+++ Folgende Methoden koennen ueberschrieben werden um fuer den konkreten Solver eine Loesung einzubauen.
	//### aus ISolveZZZ
	
	TODOGOON20250908;//das müsste eigentlich solvePost ersetzen vom abstractKernelSolverZZZ, nur halt für Child-Tags.
	                 //solvePostCustom bleibt dann wirklich für ...custom-Belange erhalten.
	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.solvePostCustom_(vecExpression, null, true);
	}

	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators)throws ExceptionZZZ {
		return this.solvePostCustom_(vecExpression, null, bRemoveSurroundingSeparators);
	}
	
	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.solvePostCustom_(vecExpression, objReturnReference, true);
	}
	
	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.solvePostCustom_(vecExpression, objReturnReference, bRemoveSurroundingSeparators);
	}
	
	//!!! nur eine Blaupause, die vom konkreten Solver ueberschrieben werden kann.
	//!!! hier wuerde dann etwas konkretes stehen.
	private Vector3ZZZ<String> solvePostCustom_(Vector3ZZZ<String> vecExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = vecExpressionIn;		
		String sReturn = null;
		String sReturnTag = null; String sReturnLine = null;
		String sExpressionIn=null;
		boolean bUseExpression = false; boolean bUseSolver = false; boolean bUseSolverThis = false; //boolean bUseSolver = false;
			
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}	
			
		sReturnTag = this.getValue();
		sReturnLine = sExpressionIn;
				
		main:{	
			if(vecExpressionIn==null) break main;
				
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
			
			
			
			bUseSolver = this.isSolverEnabledEveryRelevant(); //this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
			
			bUseSolverThis = this.isSolverEnabledThis(); //this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);		
			if(!bUseSolverThis) break main;
				
			sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
			this.setRaw(sExpressionIn);
			objEntry.setRaw(sExpressionIn);
			
			//##############
			//### Hier die konkrete Erweiterung
			
			//Es muss nicht nur der eigene Tag und der Z-Tag entfernt werden,
			//sondern auch der JSON-Tag, quasi als "Elterntag" auch entfernen.			
			if(bUseExpression & bUseSolver & bUseSolverThis){
				String sTagStart = this.getTagPartOpening();
				String sTagEnd = this.getTagPartClosing();
				if(sTagStart.equalsIgnoreCase("<Z>")) {
					//dann mache nix... der Tag wird anders behandelt...
				}else {
					sTagStart = XmlUtilZZZ.computeTagPartOpening(this.getParentName());
					sTagEnd = XmlUtilZZZ.computeTagPartClosing(this.getParentName());  					
					KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionIn, sTagStart,sTagEnd, true, false); //also AN JDEDER POSITION (d.h. nicht nur am Anfang) von aussen nach innen!!!
				}
			}	
						
			//##############
			sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
			sReturnLine = VectorUtilZZZ.implode(vecReturn);
			
			this.updateValueSolved();
			this.updateValueSolved(objReturnReference);
		}//end main:
	
		//#################################
		//Den Wert ersetzen, wenn es was zu ersetzen gibt.
		if(vecReturn!=null) vecReturn.replace(sReturnTag);
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
						
		if(objEntry!=null) {
			//NUN DEN INNERHALB DER EXPRESSION BERECHNUNG ERSTELLTEN WERT uebernehmen
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(sExpressionIn!=null) {												
				if(!sExpressionIn.equals(sReturn)) {
					this.updateValueSolvedChanged();
					this.updateValueSolvedChanged(objReturnReference); //zur Not nur, weil die Z-Tags entfernt wurden.									
				}
			}			
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		}
		return vecReturn;
	}		
	
}//End class