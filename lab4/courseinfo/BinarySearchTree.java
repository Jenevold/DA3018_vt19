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
			} else if (node.getCourseCode().compareTo(currentKey) == 0){ // add else if for when courseCode already exsists in tree shoudl reain but the information should overwrite.
			    root.courseName = node.getCourseName(); // Resave the old node with new updated info; name and credits
			    root.credits = node.getCredits();
			}
			
			root.setChildren(left, right);
			return root; // return node? instead of root
		}
	}

	/**
	 * size: Count the number of nodes in the search tree
	 * @return
	 */
	
	public int size() {  
		return (size(root));  // add if root == null exception?

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
	 * isRightChild: Gives true if the right child of node has the given cours code.
	 * @param node, a BST node
	 * @param courseCode, e.g 'MM2018'
	 */
	private Boolean isRightChild(BSTNode node, String courseCode) {
		if (node.getRightChild().getCourseCode().equals(courseCode)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * remove: Remove a node (a course) given a course code
	 * @param courseCode, e.g 'MM2018'
	 */
	public void remove(String courseCode) {
		remove(root, courseCode);
	}

	private void remove(BSTNode rootNode, String courseCode) {
		BSTNode target = find(rootNode, courseCode);
		BSTNode parent = findParent(rootNode, courseCode);
		if (target.getLeftChild() != null && target.getRightChild() != null) {
		    BSTNode newNode = findSmallest(target.getRightChild());
		    if (target == rootNode) {
		    	root = newNode;
		    	root.setChildren(target.getLeftChild(), target.getRightChild());
			} else if (isRightChild(parent, courseCode)) {
                parent.setChildren(parent.getLeftChild(), newNode);
                newNode.setChildren(target.getLeftChild(), target.getRightChild());
            } else {
		        parent.setChildren(newNode, parent.getRightChild());
		        newNode.setChildren(target.getLeftChild(), target.getRightChild());
            }
            // Case 1:
            // The target rootNode that we want to remove have both left and right childes.
            // The smallest leaf in the left sub-tree to target will replace target.
            //
		} else if (target.getLeftChild()!=null) {
		    if (target == rootNode) {
		    	root = target.getLeftChild();
			} else if (isRightChild(parent, courseCode)) {
                parent.setChildren(parent.getLeftChild(), target.getLeftChild());
            } else {
		        parent.setChildren(target.getLeftChild(), parent.getRightChild());
            }
			// Case 2:
            // The target rootNode only has a right child:
            // Replace target with the right child.
            //
		} else if (target.getRightChild()!=null) {
			if (target == rootNode) {
				root = target.getRightChild();
			} else if (isRightChild(parent, courseCode)) {
                parent.setChildren(parent.getLeftChild(), target.getRightChild());
            } else {
                parent.setChildren(target.getRightChild(), parent.getRightChild());
            }
            // Case 3:
            // The target rootNode only has a left child:
            // Replace target with the left child
            //
		} else {
			if (target == rootNode) {
				root = null;
			} else if (isRightChild(parent, courseCode)) {
                parent.setChildren(parent.getLeftChild(), null);
            } else {
                parent.setChildren(null, parent.getRightChild());
            }
            // Case 4:
            // The target rootNode has no children:
            // Replace the target rootNode with null.
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
