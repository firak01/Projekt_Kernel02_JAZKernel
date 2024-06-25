package basic.zBasic.util.abstractList;

import basic.zBasic.IConstantZZZ;
import basic.zBasic.IOutputDebugNormedWithKeyZZZ;
import basic.zBasic.IOutputDebugNormedZZZ;

public interface IHashMapExtendedZZZ extends IOutputDebugNormedWithKeyZZZ, IConstantZZZ{
	public static final String sDEBUG_KEY_DELIMITER_DEFAULT = IOutputDebugNormedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
	public static final String sDEBUG_ENTRY_DELIMITER_DEFAULT = IOutputDebugNormedWithKeyZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
}
