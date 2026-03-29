package basic.zBasic.util.datatype.tree.v03;

/** Original aus:
 *  https://github.com/gt4dev/yet-another-tree-structure
 *  
 * 
 * @author Fritz Lindhauer, 31.05.2024, 12:29:20
 * 
 */
class SampleSearchMain {

	public static void main(String[] args) {
		
		Comparable<String> searchCriteria = new Comparable<String>() {
			@Override
			public int compareTo(String treeData) {
				if (treeData == null)
					return 1;
				boolean nodeOk = treeData.contains("210");
				return nodeOk ? 0 : 1;
			}
		};

		TreeNode<String> treeRoot = SampleData.getSet1();
		TreeNode<String> found = treeRoot.findTreeNode(searchCriteria);

		System.out.println("Found: " + found);
	}

}