package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

import telran.util.LinkedList.Node;
import telran.util.Map.Entry;

public class LinkedTreeMap<T> implements SortedSet<T> {
	LinkedList<T> list = new LinkedList<>();
	TreeMap<T, LinkedList.Node<T>> map = new TreeMap<>();

	@Override
	public T get(Object pattern) {
		Node<T> node = map.get(pattern);
		return node == null ? null : node.obj;
	}

	@Override
	public boolean add(T obj) {
		boolean res = false;
		Node<T> node = new Node<>(obj);
		if (map.put(obj, node) == null) {
			list.addTail(node);
			res = true;
		}
		return res;
	}

	@Override
	public boolean remove(Object pattern) {
		boolean res = false;
		Node<T> node = map.remove(pattern);
		if (node != null) {
			list.removeNode(node);
			res = true;
		}
		return res;
	}

	@Override
	public boolean contains(Object pattern) {
		return map.containsKey(pattern);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Iterator<T> iterator() {
		var tree = (TreeSet<Entry<T, LinkedList.Node<T>>>) map.set;
		return new FilteredIterator<T>(tree.iterator());
	}

	public class FilteredIterator<T> implements Iterator<T> {
		Entry<T, LinkedList.Node<T>> current;
		boolean isFind = false;
		boolean isRemoveAvailable = false;
		Iterator<Entry<T, LinkedList.Node<T>>> it;

		public FilteredIterator(Iterator<Entry<T, LinkedList.Node<T>>> srcIterator) {
			it = srcIterator;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			current = it.next();
			T res = current.getValue().obj;
			isRemoveAvailable = true;
			return res;
		}

		@Override
		public void remove() {
			if (!isRemoveAvailable) {
				throw new IllegalStateException();
			}
			it.remove();
			isRemoveAvailable = false;
		}
	}

	@Override
	public T first() {
		var tree = (TreeSet<Entry<T, LinkedList.Node<T>>>) map.set;
		var res = tree.first();
		return res.getValue().obj;
	}

	@Override
	public T last() {
		var tree = (TreeSet<Entry<T, LinkedList.Node<T>>>) map.set;
		var res = tree.last();
		return res.getValue().obj;
	}

	@Override
	public T ceiling(T key) {
		var tree = (TreeSet<Entry<T, LinkedList.Node<T>>>) map.set;
		Entry<T, LinkedList.Node<T>> entry = new Entry<>(key, new Node<>(key));
		var res = tree.ceiling(entry);
		return res.getValue().obj;

	}

	@Override
	public T floor(T key) {
		var tree = (TreeSet<Entry<T, LinkedList.Node<T>>>) map.set;
		Entry<T, LinkedList.Node<T>> entry = new Entry<>(key, new Node<>(key));
		var res = tree.floor(entry);
		return res.getValue().obj;
	}

	@Override
	public boolean equals(Object obj) {
		return setEqualsTo(obj);
	}
}
