package basic.zKernel.module;

import java.util.ArrayList;
import java.util.Iterator;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.cache.IKernelCacheZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public abstract class AbstractKernelProgramZZZ  extends KernelUseObjectZZZ implements IKernelModuleZZZ {
	protected String sProgramName=null;
	
	/**Z.B. Wg. Refelection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 */
	public AbstractKernelProgramZZZ() {
		super();
	}
	
	public AbstractKernelProgramZZZ(IKernelZZZ objKernel) {
		super(objKernel); //TODO GOON: Hier automatisch das FLAG IKERNELPROGRAM Setzen!!!
	}
	
	public String getProgramName(){
		if(StringZZZ.isEmpty(this.sProgramName)) {
			if(this.getFlag(IKernelModuleZZZ.FLAGZ.ISKERNELPROGRAM.name())) {
				this.sProgramName = this.getClass().getName();
			}
		}
		return this.sProgramName;
	}
	public void setProgramName(String sProgramName){
		this.sProgramName = sProgramName;
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
}
