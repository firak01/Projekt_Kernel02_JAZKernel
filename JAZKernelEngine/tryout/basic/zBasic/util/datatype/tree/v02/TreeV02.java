package basic.zBasic.util.datatype.tree.v02;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**https://stackoverflow.com/questions/3522454/how-to-implement-a-tree-data-structure-in-java
* @author ycoppel@google.com (Yohann Coppel)
* 
* @param <T>
*          Object's type in the tree.
*/
public class TreeV02<T> {

private T head;

private ArrayList<TreeV02<T>> leafs = new ArrayList<TreeV02<T>>();

private TreeV02<T> parent = null;

private HashMap<T, TreeV02<T>> locate = new HashMap<T, TreeV02<T>>();

public TreeV02(T head) {
  this.head = head;
  locate.put(head, this);
}

public void addLeaf(T root, T leaf) {
  if (locate.containsKey(root)) {
    locate.get(root).addLeaf(leaf);
  } else {
    addLeaf(root).addLeaf(leaf);
  }
}

public TreeV02<T> addLeaf(T leaf) {
  TreeV02<T> t = new TreeV02<T>(leaf);
  leafs.add(t);
  t.parent = this;
  t.locate = this.locate;
  locate.put(leaf, t);
  return t;
}

public TreeV02<T> setAsParent(T parentRoot) {
  TreeV02<T> t = new TreeV02<T>(parentRoot);
  t.leafs.add(this);
  this.parent = t;
  t.locate = this.locate;
  t.locate.put(head, this);
  t.locate.put(parentRoot, t);
  return t;
}

public T getHead() {
  return head;
}

public TreeV02<T> getTree(T element) {
  return locate.get(element);
}

public TreeV02<T> getParent() {
  return parent;
}

public Collection<T> getSuccessors(T root) {
  Collection<T> successors = new ArrayList<T>();
  TreeV02<T> tree = getTree(root);
  if (null != tree) {
    for (TreeV02<T> leaf : tree.leafs) {
      successors.add(leaf.head);
    }
  }
  return successors;
}

public Collection<TreeV02<T>> getSubTrees() {
  return leafs;
}

public static <T> Collection<T> getSuccessors(T of, Collection<TreeV02<T>> in) {
  for (TreeV02<T> tree : in) {
    if (tree.locate.containsKey(of)) {
      return tree.getSuccessors(of);
    }
  }
  return new ArrayList<T>();
}

@Override
public String toString() {
  return printTree(0);
}

private static final int indent = 2;

private String printTree(int increment) {
  String s = "";
  String inc = "";
  for (int i = 0; i < increment; ++i) {
    inc = inc + " ";
  }
  s = inc + head;
  for (TreeV02<T> child : leafs) {
    s += "\n" + child.printTree(increment + indent);
  }
  return s;
}
}
