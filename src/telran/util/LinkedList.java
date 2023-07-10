package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class LinkedList<T> implements List<T> {
	private static class Node<T> {
		T obj;
		Node<T> next;
		Node<T> prev;

		Node(T obj) {
			this.obj = obj;
		}
	}

	Node<T> head;
	Node<T> tail;
	int size;

	private class LinkedListIterator implements Iterator<T> {
		Node<T> current = head;
		int index = 0;
		boolean isRemoveAvailable = false;

		@Override
		public boolean hasNext() {
			return index < size;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			index++;
			isRemoveAvailable = true;
			T res = current.obj;
			current = current.next;
			return res;
		}

		@Override
		public void remove() {
			if (!isRemoveAvailable) {
				throw new IllegalStateException();
			}
			LinkedList.this.remove(index);
			isRemoveAvailable = false;
		}
	}

	@Override
	public boolean add(T obj) {
		Node<T> node = new Node<>(obj);
		addNode(size, node);
		return true;
	}

	private void addNode(int index, Node<T> node) {

		if (index == size) {
			addTail(node);
		} else if (index == 0) {
			addHead(node);
		} else {
			addMiddle(index, node);
		}
		size++;

	}

	private void addMiddle(int index, Node<T> node) {

		Node<T> nextNode = getNode(index);
		Node<T> prevNode = nextNode.prev;
		node.next = nextNode;
		nextNode.prev = node;
		prevNode.next = node;
		node.prev = prevNode;

	}

	private void addHead(Node<T> node) {
		node.next = head;
		head.prev = node;
		head = node;

	}

	private void addTail(Node<T> node) {
		if (tail == null) {
			head = tail = node;
		} else {
			tail.next = node;
			node.prev = tail;
			tail = node;
		}

	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new LinkedListIterator();
	}

	@Override
	public void add(int index, T obj) {
		indexValidation(index, true);
		Node<T> node = new Node<>(obj);
		addNode(index, node);

	}

	@Override
	public T get(int index) {
		indexValidation(index, false);
		Node<T> node = getNode(index);
		return node.obj;
	}

	private Node<T> getNode(int index) {

		return index < size / 2 ? getNodeFromHead(index) : getNodeFromTail(index);
	}

	private Node<T> getNodeFromTail(int index) {
		Node<T> current = tail;
		for (int i = size - 1; i > index; i--) {
			current = current.prev;
		}
		return current;
	}

	private Node<T> getNodeFromHead(int index) {
		Node<T> current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}

	@Override
	public T set(int index, T obj) {
		indexValidation(index, false);
		Node<T> node = getNode(index);
		T res = node.obj;
		node.obj = obj;
		return res;
	}

	@Override
	public T remove(int index) {
		indexValidation(index, false);
		Node<T> removedNode = getNode(index);
		size--;

		if (index == 0) {
			Node<T> nextNode = removedNode.next;
			removedNode.next = null;
			nextNode.prev = null;
		} else if (index == size - 1) {
			Node<T> prevNode = removedNode.prev;
			removedNode.prev = null;
			prevNode.next = null;
		} else {
			Node<T> nextNode = removedNode.next;
			Node<T> prevNode = removedNode.prev;
			removedNode.next = null;
			removedNode.prev = null;
			nextNode.prev = prevNode;
			prevNode.next = nextNode;
		}

		return removedNode.obj;
	}

	@Override
	public int indexOf(Object pattern) {
		return indexOf(v -> v == pattern);
	}

	@Override
	public int lastIndexOf(Object pattern) {
		return lastIndexOf(v -> v == pattern);
	}

	@Override
	public int indexOf(Predicate<T> predicate) {
		int res = -1;
		if (size != 0) {
			for (int index = 0; index < size; index++) {
				Node<T> node = getNode(index);
				if (predicate.test(node.obj)) {
					res = index;
					break;
				}
			}
		}
		return res;

	}

	@Override
	public int lastIndexOf(Predicate<T> predicate) {
		int res = -1;
		if (size != 0) {
			for (int index = size - 1; index >= 0; index--) {
				Node<T> node = getNode(index);
				if (predicate.test(node.obj)) {
					res = index;
					break;
				}
			}
		}
		return res;
	}

}