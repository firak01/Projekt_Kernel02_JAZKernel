package basic.zBasic.util.abstractList;

import basic.zKernel.status.IStatusBooleanMessageZZZ;

/**Implentation eines Ringspeichers.
 *  
 * 
 * @author Fritz Lindhauer, 07.11.2023, 16:05:02
 * angelehnt an: 
 * https://www.baeldung.com/java-ring-buffer
 * https://github.com/eugenp/tutorials/blob/master/data-structures/src/main/java/com/baeldung/circularbuffer/CircularBuffer.java
 */
public class CircularBufferForStatusBooleanMessageZZZ<E extends IStatusBooleanMessageZZZ>  extends AbstractCircularBufferZZZ implements ICircularbufferForStatusLocalMessageZZZ {	
	public CircularBufferForStatusBooleanMessageZZZ() {
		super();
	}
	
	public CircularBufferForStatusBooleanMessageZZZ(int capacity) {
		super(capacity);
	}
	
    //+++++++++++++++++++++++++++++++++
    //+++ Die Message mit einem besonderen Wert ueberbschreiben
    public boolean replaceLastWith(String sMessage) {
    	boolean bReturn = false;
    	main:{
    		int iIndex = this.getWriteSequence();
    		bReturn = this.replacePositionWith(iIndex, sMessage);	    		
    	}//end main:
    	return bReturn;
    }
    
    public boolean replacePreviousWith(String sMessage) {
    	boolean bReturn = false;
    	main:{
    		//int iIndex = this.writeSequence - 1;
    		int iIndex = this.computeIndexForStepPrevious(1);
    		bReturn = this.replacePositionWith(iIndex, sMessage);	    		
    	}//end main:
    	return bReturn;
    }
    
    public boolean replacePositionWith(int iIndex, String sMessage) {
    	boolean bReturn = false;
    	main:{
    		if(iIndex<0)break main;
    		if(iIndex>this.getWriteSequence())break main;
    		
    		//ne, so eben nicht... this.getObjectArray()[iIndex]=sMessage;
    		IStatusBooleanMessageZZZ objStatusBooleanMessage = (IStatusBooleanMessageZZZ) this.getObjectArray()[iIndex];
    		objStatusBooleanMessage.setMessage(sMessage);
    		
    		bReturn = true;
    	}//end main:
    	return bReturn;
    }
}
