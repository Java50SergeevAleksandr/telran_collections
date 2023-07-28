package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class TreeSet<T> implements SortedSet<T> {

	Node<T> root;
	int size;
	Comparator<T> comp;
	private int spacesPerLevel = 2;

	public TreeSet(Comparator<T> comp) {
		this.comp = comp;
	}

	public TreeSet() {
		this((Comparator<T>) Comparator.naturalOrder());
	}

	public int getSpacesPerLevel() {
		return spacesPerLevel;
	}

	public void setSpacesPerLevel(int spacesPerLevel) {
		this.spacesPerLevel = spacesPerLevel;
	}

	private static class Node<T> {
		T obj;
		Node<T> parent;
		Node<T> left;
		Node<T> right;

		Node(T obj) {
			this.obj = obj;
		}
	}

	private Node<T> getParentOrNode(T key) {
		Node<T> current = root;
		Node<T> parent = null;
		int compRes;
		while (current != null && (compRes = comp.compare(key, current.obj)) != 0) {
			parent = current;
			current = compRes < 0 ? current.left : current.right;
		}
		return current == null ? parent : current;
	}

	private Node<T> getParent(T key) {
		// returns null in the case the object matching key exists
		Node<T> node = getParentOrNode(key);
		Node<T> parent = null;
//		if (comp.compare(key, node.obj) != 0) {
//			parent = node;
//		}
		if (!node.obj.equals(key)) {
			parent = node;
		}
		return parent;
	}

	private Node<T> getNode(T key) {
		// returns null in the case the object matching key doesn't exist
		Node<T> node = getParentOrNode(key);
		Node<T> res = null;
//		if (node != null && comp.compare(key, node.obj) == 0) {
//			res = node;
//		}
		if (node != null && node.obj.equals(key)) {
			res = node;
		}
		return res;
	}

	@Override
	public T get(Object pattern) {
		Node<T> node = getNode((T) pattern);
		T res = null;
		if (node != null) {
			res = node.obj;

		}
		return res;
	}

	@Override
	public boolean add(T obj) {
		Node<T> node = new Node<T>(obj);
		boolean res = false;
		if (root == null) {
			res = true;
			root = node;
		} else {
			Node<T> parent = getParent(obj);
			if (parent != null) {
				res = true;
				node.parent = parent;
				int compRes = comp.compare(obj, parent.obj);
				if (compRes > 0) {
					parent.right = node;
				} else {
					parent.left = node;
				}
			}
		}

		if (res) {
			size++;
		}
		return res;
	}

	@Override
	public boolean remove(Object pattern) {
		Node<T> node = getParentOrNode((T) pattern);

		if (node != null && node.obj.equals(pattern)) {
			removeNode(node);
			return true;
		}
		return false;
	}

	@Override
	public boolean contains(Object pattern) {
		return getNode((T) pattern) != null;
	}

	@Override
	public int size() {
		return size;
	}

	private Node<T> getCurrent(Node<T> current) {
		return current.right != null ? getLeastFrom(current.right) : getGreaterParent(current);
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private Node<T> current = root == null ? null : getLeastFrom(root);
			private Node<T> prev;

			@Override
			public boolean hasNext() {
				return current != null;
			}

			@Override
			public T next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				prev = current;
				current = getCurrent(current);
				return prev.obj;
			}

			@Override
			public void remove() {
				if (prev == null) {
					throw new IllegalStateException();
				}
				boolean prevWasJunction = isHaveFullChilds(prev);
				TreeSet.this.removeNode(prev);
				if (prevWasJunction) { // prev stilled the value from current
					current = prev;
				}
				prev = null;
			}
		};
	}

	private boolean isHaveFullChilds(Node<T> node) {
		return node.left != null && node.right != null;
	}

	protected void removeNode(Node<T> node) {
		if (isHaveFullChilds(node)) {
			removeFullNode(node);
		} else {
			removeNonFullNode(node);
		}
		size--;
	}

	private void removeFullNode(Node<T> node) {
		Node<T> targetNode = getLeastFrom(node.right);
		node.obj = targetNode.obj;
		removeNonFullNode(targetNode);
	}

	private void removeNonFullNode(Node<T> node) {
		Node<T> parentNode = node.parent;
		Node<T> childNode = getChild(node);
		if (parentNode != null) {
			if (node == parentNode.right) {
				parentNode.right = childNode;
			} else {
				parentNode.left = childNode;
			}
		} else {
			root = childNode;
		}
		if (childNode != null) {
			childNode.parent = parentNode;
		}
	}

	private Node<T> getChild(Node<T> node) {
		return node.left != null ? node.left : node.right;
	}

	@Override
	public T first() {
		T res = null;
		if (root != null) {
			res = getLeastFrom(root).obj;
		}

		return res;
	}

	private Node<T> getLeastFrom(Node<T> node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	@Override
	public T last() {
		T res = null;
		if (root != null) {
			res = getGreatestFrom(root).obj;
		}

		return res;
	}

	private Node<T> getGreatestFrom(Node<T> node) {
		while (node.right != null) {
			node = node.right;
		}
		return node;
	}

	@Override
	public T ceiling(T key) {
		Node<T> node = null;
		if (root != null) {
			node = getParentOrNode(key);
			int compRes = comp.compare(key, node.obj);
			if (compRes > 0) {
				node = getGreaterParent(node);
			}
		}
		return node == null ? null : node.obj;
	}

	private Node<T> getGreaterParent(Node<T> node) {
		while (node.parent != null && node.parent.left != node) {
			node = node.parent;
		}
		return node.parent;
	}

	@Override
	public T floor(T key) {
		Node<T> node = null;
		if (root != null) {
			node = getParentOrNode(key);
			int compRes = comp.compare(key, node.obj);
			if (compRes < 0) {
				node = getLeastParent(node);
			}
		}
		return node == null ? null : node.obj;
	}

	private Node<T> getLeastParent(Node<T> node) {
		while (node.parent != null && node.parent.right != node) {
			node = node.parent;
		}
		return node.parent;
	}

	public void displayRotated() {
		displayRotated(root, 1);
	}

	private void displayRotated(Node<T> root, int level) {
		if (root != null) {
			displayRotated(root.right, level + 1);
			displayRoot(root, level);
			displayRotated(root.left, level + 1);

		}

	}

	private void displayRoot(Node<T> root, int level) {

		System.out.print(" ".repeat(level * spacesPerLevel));
		System.out.println(root.obj);

	}

	public int width() {

		return width(root);
	}

	private int width(Node<T> root) {
		int res = 0;
		if (root != null) {
			if (root.left == null && root.right == null) {
				res = 1;
			} else {
				res = width(root.left) + width(root.right);
			}
		}
		return res;
	}

	public int height() {

		return height(root);
	}

	private int height(Node<T> root) {
		int res = 0;
		if (root != null) {
			int leftHeight = height(root.left);
			int rightHeight = height(root.right);
			res = Math.max(leftHeight, rightHeight) + 1;
		}
		return res;
	}

	public void balance() {
		Node<T>[] arrayNodes = getSortedArrayNodes();
		root = balanceArray(arrayNodes, 0, size - 1, null);

	}

	private Node<T> balanceArray(Node<T>[] arrayNodes, int left, int right, Node<T> parent) {
		Node<T> root = null;
		if (left <= right) {
			int rootIndex = (left + right) / 2;
			root = arrayNodes[rootIndex];
			root.parent = parent;
			root.left = balanceArray(arrayNodes, left, rootIndex - 1, root);
			root.right = balanceArray(arrayNodes, rootIndex + 1, right, root);
		}
		return root;
	}

	private Node<T>[] getSortedArrayNodes() {
		Node<T>[] res = new Node[size];
		int index = 0;
		Node<T> current = getLeastFrom(root);
		while (current != null) {
			res[index++] = current;
			current = getCurrent(current);
		}
		return res;
	}

	public void inverse() {
		reverse(root);
		comp = comp.reversed();
	}

	private void reverse(Node<T> point) {
		if (point != null) {
			Node<T> tmp;
			tmp = point.left;
			point.left = point.right;
			point.right = tmp;
			reverse(point.left);
			reverse(point.right);
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Fix this method to return Shallow Copy of this collection
		return super.clone();
	}

	public SortedSet<T> headSetCopy(T toElement, boolean inclusive) {
		// TODO Returns a shallow copy of the portion of this set whose elements are
		// less than (or equal to, if inclusive is true) toElement.
		return null;
	}

	public SortedSet<T> tailSetCopy(T fromElement, boolean inclusive) {
		// TODO Returns a shallow copy of the portion of this set whose elements are
		// greater than (or equal to, if inclusive is true) fromElement.
		return null;
	}

	public SortedSet<T> subSetCopy(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
		// TODO Returns a shallow copy of the portion of this set whose elements range
		// from fromElement to toElement.
		return null;
	}

}