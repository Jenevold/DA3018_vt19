package courseinfo;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * Store course information in a binary search tree
 * where the root is the 'starting point' of the tree.
 */
public class BinarySearchTree implements Iterable<BinarySearchTree.BSTNode> { //Iterates ove Nodes
	BSTNode root=null;
	public BinarySearchTree() {
		// Constructor: new BinarySearchTree will make an empty BST with a root = null.
	}

	/**
	* implements iterable
	*/
	public BSTIterator iterator() {
		return new BSTIterator();
	}


	private class BSTIterator implements Iterator<BSTNode> { //Implements Iterator that iterates over Nodes
		ArrayList<BSTNode> array; //Uses arrayList to save the order in witch we want to iterate
		int index;
		BSTNode current;

		public BSTIterator() { // Konstruktorn
            index = 0;
            array = new ArrayList();
            iterate(root); // Bestämmer itereringsordningen med funktionen iterate genom att kalla på denna med root-noden
            current = array.get(index); //Efter att iterate skapat en arraylist med alla noder kan current peka på första objektet i listan
        }

        private void iterate(BSTNode node) { // Iterate tar en "startnod" och sparar ner alla noder i trädet/subbträdet i en arrayList med minsta värdet först
		    if (node == null) { //När vi stöter på en tom possition återvänder vi
		        return;
            }
		    iterate(node.getLeftChild()); //annars fortsätt ner i vänster
		    array.add(node); // Addera noden till array list
		    iterate(node.getRightChild()); // gå sedan höger
        }


		public boolean hasNext(){
			return this.current != null;
			} //Är current null? Denna är fel!

		public BSTNode next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException(); // om det inte finns en nästa skicka exeption
            }
            current = array.get(index++); // Annars är nästa; nästa nod i array
            return current;
        }
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
	 * @return The node that should be the root of this subtree. //why?
	 * @param root, the first node in a tree/subtree
	 * @param node, the node that we want to insert
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
	 * @return the number of nonempty nodes in the BST.
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
	 * find: For receving
	 * @param courseCode
	 * @return
	 */
	public BSTNode find(String courseCode) {
		try {
			return find(root, courseCode);
		} catch (NullPointerException e) {
			System.out.println("The course " + courseCode + " not in tree");
			return null;
		}
		//return find(root, courseCode);
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
			//return null;
            throw new NullPointerException();
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
	public BSTNode findParent(BSTNode target, String courseCode) {
		if (courseCode == target.getCourseCode()) {
			return null;
		} else {
			BSTNode left = target.getLeftChild();
			BSTNode right = target.getRightChild();
			if (left==null) {
				if (courseCode.compareTo(right.getCourseCode()) == 0) {
					return target;
				} else {
					return findParent(right, courseCode);
				}
			} else if (right==null) {
				if (courseCode.compareTo(left.getCourseCode()) == 0 ) {
					return target;
				} else {
					return findParent(left, courseCode);
				}
			} else if (courseCode.compareTo(left.getCourseCode()) == 0 || courseCode.compareTo(right.getCourseCode()) == 0) {
				return target;
			} else if (courseCode.compareTo(target.getCourseCode()) < 0) { // go left
				return findParent(left, courseCode);
			} else { 							// go right
				return findParent(right, courseCode);
			}
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
	 * this function looks at the node and detiermines it it in it self is a rigth child of a parent
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


	// delete leftover child
	private void remove(BSTNode currentNode, String courseCode) {
		try {
			find(currentNode, courseCode);
		} catch (NullPointerException e) {
			System.out.println("Not possible to remove " + courseCode + ", course not in tree");
		}
		BSTNode target = find(currentNode, courseCode);
		BSTNode parent = findParent(currentNode, courseCode);
		if (target.getLeftChild() != null && target.getRightChild() != null) {
		    BSTNode newNode = findSmallest(target.getRightChild()); // Find smallest child
		    remove(target, newNode.getCourseCode()); // Remove smallest child
		    if (target == currentNode) {
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
            // The target currentNode that we want to remove have both left and right childes.
            // The smallest leaf in the left sub-tree to target will replace target.
            //
		} else if (target.getLeftChild()!=null) {
		    if (target == currentNode) {
		    	root = target.getLeftChild();
			} else if (isRightChild(parent, courseCode)) {
                parent.setChildren(parent.getLeftChild(), target.getLeftChild());
            } else {
		        parent.setChildren(target.getLeftChild(), parent.getRightChild());
            }
			// Case 2:
            // The target currentNode only has a left child:
            // Replace target with the left child.
            //
		} else if (target.getRightChild()!=null) {
			if (target == currentNode) {
				root = target.getRightChild();
			} else if (isRightChild(parent, courseCode)) {
                parent.setChildren(parent.getLeftChild(), target.getRightChild());
            } else {
                parent.setChildren(target.getRightChild(), parent.getRightChild());
            }
            // Case 3:
            // The target currentNode only has a right child:
            // Replace target with the right child
            //
		} else {
			if (target == currentNode) {
				root = null;
			} else if (isRightChild(parent, courseCode)) {
                parent.setChildren(parent.getLeftChild(), null);
            } else {
                parent.setChildren(null, parent.getRightChild());
            }
            // Case 4:
            // The target currentNode has no children:
            // Replace the target currentNode with null.
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
