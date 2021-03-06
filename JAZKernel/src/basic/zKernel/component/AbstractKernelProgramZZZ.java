package basic.zKernel.component;

import java.util.ArrayList;
import java.util.Iterator;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.cache.IKernelCacheZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public abstract class AbstractKernelProgramZZZ  extends KernelUseObjectZZZ implements IKernelProgramZZZ, IKernelModuleUserZZZ {
	protected IKernelModuleZZZ objModule=null; //Das Modul, z.B. die Dialogbox, in der das Program gestartet wird.
	protected String sModuleName=null;	
	protected String sProgramName=null;
	
	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 */
	public AbstractKernelProgramZZZ() {
		super();
	}
	
	public AbstractKernelProgramZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel); 
		
		//Da dies ein KernelProgram ist automatisch das FLAG IKERNELPROGRAM Setzen!!!
		this.setFlag(IKernelProgramZZZ.FLAGZ.ISKERNELPROGRAM.name(), true);		
	}
	
	public String getProgramName(){
		if(StringZZZ.isEmpty(this.sProgramName)) {
			if(this.getFlag(IKernelProgramZZZ.FLAGZ.ISKERNELPROGRAM.name())) {
				this.sProgramName = this.getClass().getName();
			}
		}
		return this.sProgramName;
	}
	public void setProgramName(String sProgramName){
		this.sProgramName = sProgramName;
	}	
	
	public void resetProgramUsed() {
		this.sProgramName = null;
		//this.sProgramAlias = null;
	}
	
	
	
	/**Merke: Man kann den konkreten Program Alias nicht ermitteln, wenn man nicht weiss, in welchen Wert er gesetzt werden soll.
	 *        Darum kann hier nur eine ArrayListe zurückgegeben werden.
	 * @return
	 * @throws ExceptionZZZ
	 */
	public ArrayList<String> getProgramAliasList() throws ExceptionZZZ{
		ArrayList<String> listasReturn = new ArrayList<String>();
		main:{			
		IKernelZZZ objKernel = this.getKernelObject();
		
		FileIniZZZ objFileIniConfig = objKernel.getFileConfigIni();
		String sMainSection = this.getModuleName();
		String sProgramName = this.getProgramName();
		String sSystemNumber = objKernel.getSystemNumber();
		listasReturn = objKernel.getProgramAliasUsed(objFileIniConfig, sMainSection, sProgramName, sSystemNumber);

		}//end main:
		return listasReturn;
	}
	/**Gehe die ProgramAlias-Namen durch und prüfe, wo der Wert gesetzt ist . 
	 * Das ist dann der verwendete Alias.
	 * @param sPropertyName
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public String getProgramAlias(String sProperty) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			ArrayList<String> listasProgramAlias = this.getProgramAliasList();
			
			String sModule = this.getModuleUsed();
			FileIniZZZ objFileIniConfig = this.getKernelObject().getFileConfigIniByAlias(sModule);
			
			//+++ Als Program mit Alias:
			Iterator<String> itAlias = listasProgramAlias.iterator();
			while(itAlias.hasNext()){				
				String sProgramAliasUsed = itAlias.next();
				String sSection = sProgramAliasUsed;
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": (x) Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");
				if(!StringZZZ.isEmpty(sSection)){
					boolean bSectionExists = objFileIniConfig.proofSectionExists(sSection);
					if(bSectionExists==true){
						String sValue = objFileIniConfig.getPropertyValue(sSection, sProperty).getValue(); 
						if(sValue != null){
							System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": (x)Value gefunden für Property '" + sProperty + "'='" + sReturn + "'");
							sReturn = sSection;
							break main;
						}else{
							System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": (x) Kein Value gefunden in Section '" + sSection + "' für die Property: '" + sProperty + "'.");
						}
					}
				}
			}
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": (x) Keinen Value gefunden in einem möglichen Programalias. Suche direkter nach der Property.'" + sProperty +"'.");			
		}
		return sReturn;
	}
	
	@Override
	public String getProgramAlias() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String readModuleName() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			IKernelModuleZZZ objModule = this.getModule();
			if(objModule!=null) {
				sReturn = objModule.getModuleName();
				if(StringZZZ.isEmpty(sReturn)) {
					sReturn = this.getKernelObject().getSystemKey();
				}
			}else {
				sReturn = this.getKernelObject().getSystemKey();
			}
		}//end main:
		return sReturn;
	}
	
	public String getModuleName() throws ExceptionZZZ{
		if(StringZZZ.isEmpty(this.sModuleName)) {
			this.sModuleName = this.readModuleName();
		}
		return this.sModuleName;
	}
	public void setModuleName(String sModuleName){
		this.sModuleName=sModuleName;
	}
	
	public void resetModuleUsed() {
		this.objModule = null;
		this.sModuleName = null;
	}
	
	//### Aus IKernelModuleUserZZZ
	public IKernelModuleZZZ getModule() {
		return this.objModule;
	}
	public void setModule(IKernelModuleZZZ objModule) {
		this.objModule = objModule;
	}
			
	//### Methoden
	public abstract void reset();
}
