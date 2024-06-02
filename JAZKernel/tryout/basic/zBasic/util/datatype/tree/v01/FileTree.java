package basic.zBasic.util.datatype.tree.v01;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**https://stackoverflow.com/questions/3522454/how-to-implement-a-tree-data-structure-in-java
 *  
 * 
 * 
 */
public class FileTree implements Tree<File> {

    @Override
    public List<File> getRoots() {
       //JAVA 8: Collectors... return Arrays.stream(File.listRoots()).collect(Collectors.toList());
    	return null;
    }

    @Override
    public File getParent(File node) {
        return node.getParentFile();
    }

    @Override
    public List<File> getChildren(File node) {
        if (node.isDirectory()) {
            File[] children = node.listFiles();
            if (children != null) {
            	//JAVA 8: Collectors...         return Arrays.stream(children).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}
