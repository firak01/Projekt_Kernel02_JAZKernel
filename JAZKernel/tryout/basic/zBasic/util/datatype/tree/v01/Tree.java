package basic.zBasic.util.datatype.tree.v01;

import java.io.Serializable;
import java.util.List;

public interface Tree <N extends Serializable> extends Serializable {
    List<N> getRoots ();
    N getParent (N node);
    List<N> getChildren (N node);
}
