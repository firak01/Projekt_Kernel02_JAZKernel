package basic.zBasic.util.datatype.tree.v01;

import java.io.Serializable;

public interface MutableTree <N extends Serializable> extends Tree<N> {
    boolean add (N parent, N node);
    boolean remove (N node, boolean cascade);
}