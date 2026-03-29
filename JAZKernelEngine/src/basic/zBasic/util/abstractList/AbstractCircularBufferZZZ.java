package basic.zBasic.util.abstractList;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;

/**Implentation eines Ringspeichers.
 *  
 * 
 * @author Fritz Lindhauer, 07.11.2023, 16:05:02
 * angelehnt an: 
 * https://www.baeldung.com/java-ring-buffer
 * https://github.com/eugenp/tutorials/blob/master/data-structures/src/main/java/com/baeldung/circularbuffer/CircularBuffer.java
 */
public abstract class AbstractCircularBufferZZZ<E>  extends AbstractObjectWithExceptionZZZ {
	private static final int DEFAULT_CAPACITY = 9;

    private final int capacity;
    private final E[] data;
    private volatile int writeSequence, readSequence;
	
	
	public AbstractCircularBufferZZZ() {
		super();	
		
		this.capacity = DEFAULT_CAPACITY;
	    this.data = (E[]) new Object[this.capacity];
	    this.readSequence = 0;
	    this.writeSequence = -1;
	}
	
	public AbstractCircularBufferZZZ(int capacity) {
		super();
		
	    this.capacity = (capacity < 1) ? DEFAULT_CAPACITY : capacity;
	    this.data = (E[]) new Object[this.capacity];
	    this.readSequence = 0;
	    this.writeSequence = -1;
	}
	
	 /** Damit das mit dem Circular klappt, muss ich hier eine Erweiterung reinbauen, mit der quasi oben wieder angefangen wird.
	 * @param element
	 * @return
	 * @author Fritz Lindhauer, 14.11.2023, 08:35:42
	 */
	public boolean offer(E element) {

	        boolean bOffered = this.offerOnCapacityFree(element);
	        if(!bOffered) {
	        	bOffered = this.offerOnCapacityFull(element);
	        }
	        return bOffered;
	    }
	 
	 	/**Damit das mit dem Circular klappt, muss ich hier eine Erweiterung reinbauen, mit der quasi oben wieder angefangen wird.
	 	 * @param element
	 	 * @return
	 	 * @author Fritz Lindhauer, 14.11.2023, 08:35:59
	 	 */
	 	public boolean offerOnCapacityFree(E element) {
	        if (isNotFull()) {

	            int nextWriteSeq = writeSequence + 1;
	            data[nextWriteSeq % capacity] = element;

	            writeSequence++;
	            return true;
	        }
	        return false;
	    }	  
	       
        /**Damit das mit dem Circular klappt, muss ich hier eine Erweiterung reinbauen, mit der quasi oben wieder angefangen wird.
         * @param element
         * @return
         * @author Fritz Lindhauer, 14.11.2023, 08:36:06
         */
        public boolean offerOnCapacityFull(E element) {
	        if (isFull()) {		       
	        	//FGL: Damit das mit dem Circular klappt, muss ich hier eine Erweiterung reinbauen, mit der quasi oben wieder angefangen wird.
	        	//this.readSequence = 0;
	     	    //this.writeSequence = -1;
	        	
	     	    int nextWriteSeq = writeSequence + 1;
	            data[nextWriteSeq % capacity] = element;

	            writeSequence++;
	            return true;
	        }
	        return false;
        }
 

	    public E poll() {

	        if (isNotEmpty()) {

	            E nextValue = data[readSequence % capacity];
	            readSequence++;
	            return nextValue;
	        }

	        return null;
	    }

	    public int getCapacity() {
	        return this.capacity;
	    }
	    
	    //geht nicht, das Variable final ist.
//	    private void setCapacity(int iCapacity) {
//	    	this.capacity = iCapacity;
//	    }

	    public int size() {

	        return (writeSequence - readSequence) + 1;
	    }

	    public boolean isEmpty() {

	        return writeSequence < readSequence;
	    }

	    public boolean isFull() {

	        return size() >= capacity;
	    }
	    
	    private boolean isNotEmpty() {

	        return !isEmpty();
	    }

	    private boolean isNotFull() {

	        return !isFull();
	    }
	    
	    
	    //Erweiterungen: Methoden, die auch in einer ArrayList vorhanden sind.
	    public E get(int iIndex) {
	    	if(iIndex<0) {
	    		return null;
	    	}else {
	    		int iIndexComputed = -1;
	    		if(iIndex > this.getCapacity()-1) {
	    			iIndexComputed = iIndex % this.getCapacity();
	    		}else {
	    			iIndexComputed = iIndex;
	    		}
	    		return this.data[iIndexComputed];
	    	}
	    }
	    
	    public E getLast() {
	    	int iIndex = this.writeSequence;
	    	return this.get(iIndex);
	    }
	    
	    public E getPrevious() {
	    	//int iIndex = this.writeSequence - 1;	    	
	    	int iIndex = this.computeIndexForStepPrevious(1);
	    	return this.get(iIndex);
	    }
	    
	    public E getPrevious(int iIndexStepsBack) {
	    	//int iIndex = this.writeSequence - iIndexStepsBack;
	    	int iIndex = this.computeIndexForStepPrevious(iIndexStepsBack);
	    	return this.get(iIndex);
	    }
	    
	    
	    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    public boolean replaceLastWith(E element) {
	    	boolean bReturn = false;
	    	main:{
	    		int iIndex = this.writeSequence;
	    		bReturn = this.replacePositionWith(iIndex, element);	    		
	    	}//end main:
	    	return bReturn;
	    }
	    
	    public boolean replacePreviousWith(E element) {
	    	boolean bReturn = false;
	    	main:{
	    		//int iIndex = this.writeSequence - 1;
	    		int iIndex = this.computeIndexForStepPrevious(1);
	    		bReturn = this.replacePositionWith(iIndex, element);	    		
	    	}//end main:
	    	return bReturn;
	    }
	    
	    public boolean replacePositionWith(int iIndex, E element) {
	    	boolean bReturn = false;
	    	main:{
	    		if(iIndex<0)break main;
	    		if(iIndex>this.writeSequence)break main;
	    		
	    		data[iIndex]=element;
	    		
	    		bReturn = true;
	    	}//end main:
	    	return bReturn;
	    }
	   
	    //+++++++++++++++++++++++++++++++++
	    public int computeIndexForStepPrevious(int iIndexStepsBack) {
	    	int iReturn = -1;
	    	main:{
	    		if(iIndexStepsBack<=-1) break main;
	    		if(iIndexStepsBack==0) {
	    			iReturn = this.writeSequence;
	    			break main;
	    		}
	    		
	    		int iIndextemp = this.writeSequence - iIndexStepsBack; 
	    		if(iIndextemp<0) {
	    			iReturn = iIndexStepsBack % this.getCapacity();
	    		}else {
	    			iReturn = iIndextemp;
	    		}
	    	}	    	
	    	return iReturn;
	    }
	    
	    //Wichtig: Hierfür extra kein Setter, da nicht an den Methoden vorbei der Wert geaendert werden soll.
	    public int getWriteSequence() {
	    	return this.writeSequence;//Merke: Variable ist volatil, daher 
	    }

	    //Wichtig: Hierfür extra kein Setter, da nicht an den Methoden vorbei der Wert geaendert werden soll.
	    public  int getReadSequence() {
	    	return this.readSequence;//Merke: Variable ist volatil
	    }
	    
	    //Wichtig: Hierfür extra kein Setter, da nicht an den Methoden vorbei der Wert geaendert werden soll.
	    public E[] getObjectArray() {
	    	return this.data;
	    }
}
