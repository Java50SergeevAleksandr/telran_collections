package telran.util;

import java.util.Iterator;

import telran.util.LinkedList.Node;

public class LinkedHashSet<T> implements Set<T> {
	HashMap<T, LinkedList.Node<T>> map = new HashMap<>();
	LinkedList<T> list = new LinkedList<>();

	@Override
	public boolean add(T obj) {
		boolean res = false;
		Node<T> node = new Node<>(obj);
		if (map.put(obj, node) == null) {
			list.addNode(size(), node);
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
		return list.size();
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	@Override
	public T get(Object pattern) {
		Node<T> node = map.get(pattern);
		return node == null ? null : node.obj;
	}

	@Override
	public boolean equals(Object obj) {
		return setEqualsTo(obj);
	}
}