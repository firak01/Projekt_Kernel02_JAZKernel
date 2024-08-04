package basic.zKernel.file.ini;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public abstract class AbstractKernelIniTagCascadedZZZ<T> extends AbstractKernelIniTagSimpleZZZ<T> implements IKernelUserZZZ, IKernelFileIniUserZZZ{
	private static final long serialVersionUID = -3319737210584524888L;
	
	public AbstractKernelIniTagCascadedZZZ() throws ExceptionZZZ {
		super("init");
		AbstractKernelIniTagCascadedNew_();
	}

	public AbstractKernelIniTagCascadedZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);		
		AbstractKernelIniTagCascadedNew_();
	}
	
	public AbstractKernelIniTagCascadedZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractKernelIniTagCascadedNew_();
	}
	
	public AbstractKernelIniTagCascadedZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
		AbstractKernelIniTagCascadedNew_();
	}
	
	public AbstractKernelIniTagCascadedZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ {
		super(objKernel, saFlag);
		AbstractKernelIniTagCascadedNew_();
	}

	public AbstractKernelIniTagCascadedZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
		super(objKernelUsing);
		AbstractKernelIniTagCascadedNew_();
	}
	
	public AbstractKernelIniTagCascadedZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ {
		super(objKernelUsing,saFlag);
		AbstractKernelIniTagCascadedNew_();
	}
	
	
	/** Wg. Interface IKernelUserZZZ notwendige Redundanz zu 
	 *  AbstractKernelUseObjectZZZ
	 *  und dem dortigen Konstruktor
	 *  
	 * @param objKernel
	 * @param objKernelUsing
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.07.2024, 07:10:15
	 */
	private boolean AbstractKernelIniTagCascadedNew_() throws ExceptionZZZ {
		boolean bReturn = false;		
		main: {
			
			if(this.getFlag("INIT")==true){
				bReturn = true;
				break main; 
			}	
			
			//+++++++++++++++++++++++++++++++						
			bReturn = true;
		}//end main;
		return bReturn;
	}
}// End class