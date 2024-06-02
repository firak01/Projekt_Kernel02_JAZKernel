package basic.zBasic.util.datatype.tree.v03;

/** Original aus:
 *  https://github.com/gt4dev/yet-another-tree-structure
 *  
 * 
 * @author Fritz Lindhauer, 31.05.2024, 12:29:20
 * 
 */
class SampleIterationMain {

	public static void main(String[] args) {
		TreeNode<String> treeRoot = SampleData.getSet1();
		for (TreeNode<String> node : treeRoot) {
			String indent = createIndent(node.getLevel());
			System.out.println(indent + node.data);
		}
	}

	private static String createIndent(int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			sb.append(' ');
		}
		return sb.toString();
	}

}