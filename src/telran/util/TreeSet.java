package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class TreeSet<T> implements SortedSet<T> {

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

		private void clearNode() {
			left = null;
			obj = null;
			parent = null;
			right = null;
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
		if (comp.compare(key, node.obj) != 0) {
			parent = node;
		}
		return parent;
	}

	private Node<T> getNode(T key) {
		// returns null in the case the object matching key doesn't exist
		Node<T> node = getParentOrNode(key);
		Node<T> res = null;
		if (node != null && comp.compare(key, node.obj) == 0) {
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
		Node<T> node = getNode((T) pattern);

		if (node == null) {
			return false;
		}

		if (node.left != null && node.right != null) {
			T newElem = floor(node.obj);
			remove(newElem);
			size++;
			node.obj = newElem;

		} else if (node.left == null && node.right == null) {
			if (node != root) {
				updateParent(node, null);
			} else {
				root = null;
			}
			node.clearNode();

		} else {
			Node<T> newChild = node.left != null ? node.left : node.right;

			if (node.parent != null) {
				updateParent(node, newChild);
			} else {
				root = newChild;
			}

			newChild.parent = node.parent;
			node.clearNode();
		}

		size--;
		return true;
	}

	private void updateParent(Node<T> node, Node<T> newObj) {
		if (comp.compare(node.obj, node.parent.obj) > 0) {
			node.parent.right = newObj;
		} else {
			node.parent.left = newObj;
		}
	}

	@Override
	public boolean contains(Object pattern) {
		return getNode((T) pattern) != null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private Node<T> current = getLeastFrom(root);
			private boolean isRemoveAvailable = false;
			private int checkedSize = 0;
			private Node<T> res = null;

			@Override
			public boolean hasNext() {
				return checkedSize < size();
			}

			@Override
			public T next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				res = current;
				isRemoveAvailable = true;
				checkedSize++;
				getCurrent();

				return res.obj;
			}

			private void getCurrent() {
				if (current.right != null) {
					current = getNode(ceiling(current.obj));
				} else {
					Node<T> parent = current.parent;
					while (parent != null && comp.compare(current.obj, parent.obj) > 0) {
						parent = parent.parent;
					}
					current = parent;
				}
			}

			@Override
			public void remove() {
				if (!isRemoveAvailable) {
					throw new IllegalStateException();
				}
				isRemoveAvailable = false;
				TreeSet.this.remove(res.obj);
				checkedSize--;
			}
		};
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
		T res = null;
		Node<T> node = getNode(key);
		if (node != null && node.right != null) {
			node = node.right;
			res = getLeastFrom(node).obj;
		}

		return res;
	}

	@Override
	public T floor(T key) {
		T res = null;
		Node<T> node = getNode(key);
		if (node != null && node.left != null) {
			node = node.left;
			res = getGreatestFrom(node).obj;
		}

		return res;
	}

}