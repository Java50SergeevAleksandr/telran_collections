package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class TreeSet<T> implements SortedSet<T>, Cloneable {

	Node<T> root;
	int size;
	Comparator<T> comp;

	public TreeSet(Comparator<T> comp) {
		this.comp = comp;
	}

	public TreeSet() {
		this((Comparator<T>) Comparator.naturalOrder());
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

	@Override
	public boolean equals(Object obj) {
		return setEqualsTo(obj);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		TreeSet<T> clone = new TreeSet<>(comp);
		clone.addAll(this);
		return clone;
	}

	public SortedSet<T> headSetCopy(T toElement, boolean inclusive) {
		// Returns a shallow copy of the portion of this set whose elements are
		// less than (or equal to, if inclusive is true) toElement.
		Node<T> tail = root == null ? null : getLeastFrom(root);
		Node<T> head = root == null ? null : getParentOrNode(toElement);
		if (head != null) {
			int compRes = comp.compare(head.obj, toElement);
			if (compRes > 0 || compRes == 0 && !inclusive) {
				head = head.left != null ? getGreatestFrom(head.left) : getLeastParent(head);
			}
		}

		return createRange(tail, head);
	}

	public SortedSet<T> tailSetCopy(T fromElement, boolean inclusive) {
		// Returns a shallow copy of the portion of this set whose elements are
		// greater than (or equal to, if inclusive is true) fromElement.

		Node<T> head = root == null ? null : getGreatestFrom(root);
		Node<T> tail = root == null ? null : getParentOrNode(fromElement);
		if (tail != null) {
			int compRes = comp.compare(tail.obj, fromElement);
			if (compRes < 0 || compRes == 0 && !inclusive) {
				tail = tail.right != null ? getLeastFrom(tail.right) : getGreaterParent(tail);
			}
		}

		return createRange(tail, head);
	}

	public SortedSet<T> subSetCopy(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
		// Returns a shallow copy of the portion of this set whose elements range
		// from fromElement to toElement.
		if (comp.compare(fromElement, toElement) > 0) {
			throw new IllegalArgumentException("fromElement > toElement");
		}
		Node<T> tail = root == null ? null : getParentOrNode(fromElement);
		if (tail != null) {
			int compRes = comp.compare(tail.obj, fromElement);
			if (compRes < 0 || compRes == 0 && !fromInclusive) {
				tail = tail.right != null ? getLeastFrom(tail.right) : getGreaterParent(tail);
			}
		}
		Node<T> head = root == null ? null : getParentOrNode(toElement);
		if (head != null) {
			int compRes = comp.compare(head.obj, toElement);
			if (compRes > 0 || compRes == 0 && !toInclusive) {
				head = head.left != null ? getGreatestFrom(head.left) : getLeastParent(head);
			}
		}
		return createRange(tail, head);
	}

	private TreeSet<T> createRange(Node<T> first, Node<T> last) {
		TreeSet<T> res = new TreeSet<>(comp);
		if (first != null && last != null && comp.compare(first.obj, last.obj) <= 0) {
			Node<T> node = first;
			while (node != last) {
				res.add(node.obj);
				node = getCurrent(node);
			}
			res.add(node.obj);
		}
		return res;
	}

}