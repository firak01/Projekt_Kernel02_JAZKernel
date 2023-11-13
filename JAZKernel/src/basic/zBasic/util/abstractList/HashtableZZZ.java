package basic.zBasic.util.abstractList;

import java.util.HashMap;
import java.util.TreeMap;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectZZZ;

public class HashtableZZZ extends AbstractObjectZZZ{

	public static HashMap<String,String> toHashMap_IntegerObject(HashtableIndexedZZZ<Integer,Object> htIndexed){
		HashMap<String,String>hmReturn=null;
		main:{
			if(htIndexed==null) break main;
			hmReturn=new HashMap<String,String>();
			
			//Prüfe, ob überhaupt ein Objekt in der Hashtable (im Index) steht.
			VectorExtendedZZZ<Integer> vecIndex = htIndexed.getVectorIndex();
			if(vecIndex==null) break main;
			if(!vecIndex.hasAnyElement()) break main;
	
			//#############################################################
			//Merke: Der Indexvektor soll aufsteigend sortiert sein.
			for(Object objIndex : vecIndex) {
				Integer intIndex = (Integer) objIndex;
				String sValue = (String) htIndexed.getValue(intIndex);					
				hmReturn.put(intIndex.toString(), sValue);//Merke: Die Sortierung in der HashMap ist dann aber verloren gegangen.
			}
			
			/* ANDERE IDEE, z.B.: 
			HashtableIndexedZZZ objHt =  objBatchReader.getLines();
			VectorExtendedZZZ objVec = objHt.getVectorIndex();
			TreeMap<Integer,Object>tm = objHt.getTreeMap();
			hmReturn.putAll(tm);
			ABER: Wäre das dann zwangsläufig in der Richtigen Reihenfolge sortiert?	
			*/
		
		}
		return hmReturn;
	}
	
	public static HashMap<String,String> toHashMap_IntegerString(HashtableIndexedZZZ<Integer,String> htIndexed){
		HashMap<String,String>hmReturn=null;
		main:{
			if(htIndexed==null) break main;
			hmReturn=new HashMap<String,String>();
			
			//Prüfe, ob überhaupt ein Objekt in der Hashtable (im Index) steht.
			VectorExtendedZZZ<Integer> vecIndex = htIndexed.getVectorIndex();
			if(vecIndex==null) break main;
			if(!vecIndex.hasAnyElement()) break main;
	
			//#############################################################
			//Merke: Der Indexvektor soll aufsteigend sortiert sein.
			for(Object objIndex : vecIndex) {
				Integer intIndex = (Integer) objIndex;
				String sValue = (String) htIndexed.getValue(intIndex);					
				hmReturn.put(intIndex.toString(), sValue);//Merke: Die Sortierung in der HashMap ist dann aber verloren gegangen.
			}
			
			/* ANDERE IDEE, z.B.: 
			HashtableIndexedZZZ objHt =  objBatchReader.getLines();
			VectorExtendedZZZ objVec = objHt.getVectorIndex();
			TreeMap<Integer,Object>tm = objHt.getTreeMap();
			hmReturn.putAll(tm);
			ABER: Wäre das dann zwangsläufig in der Richtigen Reihenfolge sortiert?	
			*/
		
		}
		return hmReturn;
	}
	
	public static HashMap<String,Object> toHashMap_StringObject(HashtableIndexedZZZ<String,Object> htIndexed){
		HashMap<String,Object>hmReturn=null;
		main:{
			if(htIndexed==null) break main;
			hmReturn=new HashMap<String,Object>();
			
			//Prüfe, ob überhaupt ein Objekt in der Hashtable (im Index) steht.
			VectorExtendedZZZ<Integer> vecIndex = htIndexed.getVectorIndex();
			if(vecIndex==null) break main;
			if(!vecIndex.hasAnyElement()) break main;
	
			//#############################################################
			//Merke: Der Indexvektor soll aufsteigend sortiert sein.
			for(Object objIndex : vecIndex) {
				String sIndex = (String) objIndex;
				String sValue = (String) htIndexed.getValue(sIndex);					
				hmReturn.put(sIndex, sValue);//Merke: Die Sortierung in der HashMap ist dann aber verloren gegangen.
			}
			
			/* ANDERE IDEE, z.B.: 
			HashtableIndexedZZZ objHt =  objBatchReader.getLines();
			VectorExtendedZZZ objVec = objHt.getVectorIndex();
			TreeMap<Integer,Object>tm = objHt.getTreeMap();
			hmReturn.putAll(tm);
			ABER: Wäre das dann zwangsläufig in der Richtigen Reihenfolge sortiert?	
			*/
		
		}
		return hmReturn;
	}
}
