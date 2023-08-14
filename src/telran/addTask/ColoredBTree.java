package telran.addTask;

import static java.util.Map.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * @author D.Zinchin
 */
public class ColoredBTree {
	Node root = null;
	int size = 0;
	ArrayList<LinkedList<Node>> lists = new ArrayList<>(1);

	public static class Node implements Comparable<Node> {
		public int value;
		public char color; // R, G, B, Y, …
		public Node parent = null;
		public Node left = null;
		public Node right = null;

		Node(int value, char color, Node parent) {
			this.value = value;
			this.color = color;
			this.parent = parent;
		}

		@Override
		public int compareTo(Node o) {
			return ((Comparable<Integer>) value).compareTo(o.value);
		}
	}

	@SafeVarargs
	public ColoredBTree(Entry<Integer, Character>... valueColorEntries) {
		for (var entry : valueColorEntries) {
			add(entry.getKey(), entry.getValue());
		}
	}

	public boolean add(int value, char color) {

		if (root == null) {
			root = new Node(value, color, null);
			size++;
			return true;
		}

		Node current = root;
		while (true) {
			if (value == current.value) {
				return false; // such element already exists
			}
			if (value < current.value) {
				if (current.left == null) {
					current.left = new Node(value, color, current);
					break;
				}
				current = current.left;
			} else { // value > current.value
				if (current.right == null) {
					current.right = new Node(value, color, current);
					break;
				}
				current = current.right;
			}
		}
		size++;
		return true;
	}

	public void printTree() {
		print(root, 0);
	}

	// recursive part of printTree() function
	private void print(Node node, int depth) {
		if (node != null) {
			print(node.right, depth + 1);
			System.out.printf("%s%02d,%c%n", "    ".repeat(depth), node.value, node.color);
			print(node.left, depth + 1);
		}
	}

	/**
	 * Calculates the longest chain of Node-s having the same color
	 */
	public List<Node> maxChain() {
		ArrayList<Node> res = null;

		if (root != null) {
			addEmptyList();
			treeScan(root, lists.get(0), root.color);
			res = findMaxChain(lists);
		}

		return res;
	}

	private void addEmptyList() {
		LinkedList<Node> list = new LinkedList<Node>();
		lists.add(list);
	}

	private ArrayList<Node> findMaxChain(ArrayList<LinkedList<Node>> lists) {
		LinkedList<Node> res = lists.get(0);
		for (LinkedList<Node> list : lists) {
			if (list.size() > res.size()) {
				res = list;
			}
		}
		return toArrayListForThisTest(res);
	}

	private ArrayList<Node> toArrayListForThisTest(LinkedList<Node> list) {
		ArrayList<Node> res = new ArrayList<Node>(list.size());
		list.forEach(node -> res.add(node));
		return res;
	}

	private void treeScan(Node n, LinkedList<Node> curList, char prevColor) {
		if (n != null) {
			LinkedList<Node> rightList, leftList;
			rightList = leftList = curList;

			if (n.color == prevColor) {
				addToCurrentListInRightOrder(n, curList);
			} else {
				addNodeToEmptyList(n);
				rightList = leftList = getLastList();
			}

			if (isFullJunction(n)) {
				addNodeToEmptyList(n);
				rightList = getLastList();
				treeScan(n.right, rightList, n.color);
				treeScan(n.left, rightList, n.color);

				addCloneList(curList);
				rightList = getLastList();
			}

			treeScan(n.right, rightList, n.color);
			treeScan(n.left, leftList, n.color);
		}
	}

	private void addCloneList(LinkedList<Node> curList) {
		addEmptyList();
		@SuppressWarnings("unchecked")
		LinkedList<Node> clone = (LinkedList<Node>) curList.clone();
		lists.add(clone);
	}

	private void addNodeToEmptyList(Node n) {
		addEmptyList();
		getLastList().add(n);
	}

	private LinkedList<Node> getLastList() {
		return lists.get(lists.size() - 1);
	}

	private void addToCurrentListInRightOrder(Node n, LinkedList<Node> curList) {
		if (n.parent == null || n.value > curList.getLast().value) {
			curList.add(n);
		} else {
			curList.add(0, n);
		}
	}

	private boolean isFullJunction(Node n) {
		return n != root && n.left != null && n.right != null && n.right.color == n.color && n.left.color == n.color;
	}
}
