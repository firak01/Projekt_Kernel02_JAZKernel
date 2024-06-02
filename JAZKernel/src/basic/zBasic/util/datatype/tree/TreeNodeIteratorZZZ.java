package basic.zBasic.util.datatype.tree;

import java.util.Iterator;

public class TreeNodeIteratorZZZ<T> implements Iterator<TreeNodeZZZ<T>> {

	enum ProcessStages {
		ProcessParent, ProcessChildCurNode, ProcessChildSubNode
	}

	private TreeNodeZZZ<T> treeNode;

	public TreeNodeIteratorZZZ(TreeNodeZZZ<T> treeNode) {
		this.treeNode = treeNode;
		this.doNext = ProcessStages.ProcessParent;
		this.childrenCurNodeIter = treeNode.children.iterator();
	}

	private ProcessStages doNext;
	private TreeNodeZZZ<T> next;
	private Iterator<TreeNodeZZZ<T>> childrenCurNodeIter;
	private Iterator<TreeNodeZZZ<T>> childrenSubNodeIter;

	@Override
	public boolean hasNext() {

		if (this.doNext == ProcessStages.ProcessParent) {
			this.next = this.treeNode;
			this.doNext = ProcessStages.ProcessChildCurNode;
			return true;
		}

		if (this.doNext == ProcessStages.ProcessChildCurNode) {
			if (childrenCurNodeIter.hasNext()) {
				TreeNodeZZZ<T> childDirect = childrenCurNodeIter.next();
				childrenSubNodeIter = childDirect.iterator();
				this.doNext = ProcessStages.ProcessChildSubNode;
				return hasNext();
			}

			else {
				this.doNext = null;
				return false;
			}
		}
		
		if (this.doNext == ProcessStages.ProcessChildSubNode) {
			if (childrenSubNodeIter.hasNext()) {
				this.next = childrenSubNodeIter.next();
				return true;
			}
			else {
				this.next = null;
				this.doNext = ProcessStages.ProcessChildCurNode;
				return hasNext();
			}
		}

		return false;
	}

	@Override
	public TreeNodeZZZ<T> next() {
		return this.next;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
