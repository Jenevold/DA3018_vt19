package courseinfo;

/**
 * Store course information in a binary search tree
 * 
 */
public class BinarySearchTree {
	BSTNode root=null;
	
	public BinarySearchTree() {
		// BinarySearchTree tree = new BinarySearchTree();// Empty constructor?
	}
	
	/**
	 * Public interface for inserting data into the datastructure. Utilizes 
	 * a private, more technical method.
	 * @param courseCode, eg "DA3018"
	 * @param courseName, eg "Computer Science"
	 * @param courseCredits, eg 7,5
	 */
	public void insert(String courseCode, String courseName, double courseCredits) {
		BSTNode node = new BSTNode(courseCode, courseName, courseCredits);
		root = insert(root, node);
	}
	
	/**
	 * Insert 'node' into the tree pointed at by 'root'.
	 * @returns The node that should be the root of this subtree.
	 * @param root
	 * @param node
	 * 
	 * WARNING! This method has a bug, it does not behave according to specification!
	 */
	public BSTNode insert(BSTNode root, BSTNode node) {
		if (root==null) {
			return node; // The easy case. Let the current node be the root of a new (sub?)tree.
		} else {
			String currentKey = root.getCourseCode();
			BSTNode left = root.getLeftChild();
			BSTNode right = root.getRightChild();
			if (node.getCourseCode().compareTo(currentKey) < 0) { // left string "before" right string
				left = insert(left, node);
			} else if (node.getCourseCode().compareTo(currentKey) > 0) { // left string "after" right string
				right = insert(right, node); // right instead of left?
			} // add else if for when courseCode already exsists in tree shoudl reain but the information should overwrite.
			
			root.setChildren(left, right);
			return root; // return node? instead of root
		}
	}

	/**
	 * size: Count the number of nodes in the search tree
	 * @return
	 */
	
	public int size() {  
		return (size(root));

	   // Dummy return value, to make it compile. Should be replace with proper algorithm.
	}

	private int size(BSTNode node){  // counts nodes recursively
		int size = 1;
		if (node == null) {
			return 0;
		}
		if (node.getLeftChild() != null) {
			size += size(node.getLeftChild());
		}
		if (node.getRightChild() != null) {
			size += size(node.getRightChild());
		}
		return size;
	}


	/**
	 *
	 * @param courseCode
	 * @return
	 */
	public BSTNode find(String courseCode) {
		return find(root, courseCode);
		//return null; // Dummy return value. Should be replaced with a proper algorithm.
	}

	/**
	 *
	 * @param target
	 * @param courseCode
	 * @return
	 */
	private BSTNode find(BSTNode target, String courseCode){

		if (target == null) {
			return null;
		} else {

			if (courseCode.compareTo(target.getCourseCode()) == 0) { 
				return target;
			}
			else if (courseCode.compareTo(target.getCourseCode()) < 0) { // go left
				return find(target.getLeftChild(), courseCode);
			}
			else { 																			// go right
				return find(target.getRightChild(), courseCode);	
			}
		}
	}

	/**
	 * findParent: works allmost like find but returns the parent of the node with the matching courseCode
	 * @param target, The node we are looking at
	 * @param courseCode, the course code
	 * @return the parent of the node with the matching courseCode
	 */
	private BSTNode findParent(BSTNode target, String courseCode) {
		BSTNode left = target.getLeftChild();
		BSTNode right = target.getRightChild();
		if (courseCode.compareTo(left.getCourseCode()) == 0 || courseCode.compareTo(right.getCourseCode()) == 0) {
			return target;
		}
		else if (courseCode.compareTo(target.getCourseCode()) < 0) { // go left
			return findParent(left, courseCode);
		}
		else { 							// go right
			return findParent(right, courseCode);
		}
	}

	/**
	 * findSmallest: Finds the smallest leaf of a subtree of a binary search tree.
	 * @param node, the node that marks the starting point of the subtree
	 * @return  The smallest leaf of the subtree/branch.
	 */
	private BSTNode findSmallest(BSTNode node) {
		if (node.getLeftChild() == null) {
			return node;
		} else {
			return findSmallest(node.getLeftChild());
		}
	}

	/**
	 * findLargest: Finds the largest leaf of a subtree of a binary search tree.
	 * @param node, the node that marks the starting point of the subtree
	 * @return The largest leaf of the subtree/branch.
	 */
	private BSTNode findLargest(BSTNode node) {
		if (node.getRightChild() == null) {
			return node;
		} else {
			return findLargest(node.getRightChild());
		}
	}

	/**
	 *
	 * @param node, a node
	 * @param courseCode
	 */
	private Boolean isRightChild(BSTNode node, String courseCode) {
		if (node.getRightChild().getCourseCode().equals(courseCode)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * remove: Remove a course given a course code
	 * @param courseCode
	 */
	public void remove(String courseCode) {
		remove(root, courseCode);
	}

	private void remove(BSTNode root, String courseCode) {
		BSTNode target = find(root, courseCode);
		BSTNode parent = findParent(root, courseCode);
		if (target.getLeftChild() != null && target.getRightChild() != null) {
			if (isRightChild(parent, courseCode) == true) {
				BSTNode right = findSmallest(pa)
				parent.setChildren(parent.getLeftChild(), );
			}
			// Då skall det minsta lövet i det högra sub-trädet (till target) ersätta target.
			// Gör en hjälpfunktion som letar fram det "vänstraste" lövet på rightChild.
		} else if (target.getLeftChild()!=null) {
			// gör en hjälpfunktion som flyttar vänstersidan "uppåt" ett hack i trädet
		} else if (target.getRightChild()!=null) {

		} else {
			// sätt target = null, men hur??
		}
	}
	
	/**
	 * Nodes in the search tree
	 * This class should be sufficient for the project.
	 * 
	 */
	public class BSTNode {
		private String courseCode;
		private String courseName;
		private double credits;
		private BSTNode left = null;
		private BSTNode right = null;
		
		/**
		 * Constructor
		 * 
		 */
		public BSTNode(String code, String name, double credits) {
			this.courseCode = code;
			this.courseName = name;
			this.credits = credits;
		}
		
		/**
		 * Specify the children of the current node
		 * @param left
		 * @param right
		 */
		public void setChildren(BSTNode left, BSTNode right) {
			this.left = left;
			this.right = right;
		}

		public String getCourseCode() {
			return courseCode;
		}

		public String getCourseName() {
			return courseName;
		}

		public double getCredits() {
			return credits;
		}

		public BSTNode getLeftChild() {
			return left;
		}

		public BSTNode getRightChild() {
			return right;
		}

		
		
	}
}
