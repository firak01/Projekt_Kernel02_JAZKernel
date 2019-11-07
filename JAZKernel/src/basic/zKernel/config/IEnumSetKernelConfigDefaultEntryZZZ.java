package basic.zKernel.config;

import java.util.EnumSet;

import basic.zBasic.util.abstractEnum.IEnumSetZZZ;

public interface IEnumSetKernelConfigDefaultEntryZZZ extends IEnumSetZZZ{
			
		//Das sind meine Erweiterungen, was ENUM von sich aus mitbringt ist in IEnumSetZZZ enthalten.
		public int getType();
		public String getConfigProperty();
		public String getValueDefault();
		public String getDescription();
		public int getPosition(); //das ist normalerweies order()+1	
	}
