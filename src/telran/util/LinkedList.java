package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class LinkedList<T> implements List<T>, Cloneable {
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
		boolean isRemoveAvailable = false;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T res = current.obj;
			current = current.next;
			isRemoveAvailable = true;
			return res;
		}

		@Override
		public void remove() {
			if (!isRemoveAvailable) {
				throw new IllegalStateException();
			}
			Node<T> removedNode = current != null ? current.prev : tail;
			removeNode(removedNode);
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

	private void removeNode(Node<T> removedNode) {
		if (removedNode == head) {
			removeHead();
		} else if (removedNode == tail) {
			removeTail();
		} else {
			removeMiddle(removedNode);
		}
		size--;
	}

	private void removeMiddle(Node<T> removedNode) {
		Node<T> prevNode = removedNode.prev;
		Node<T> nextNode = removedNode.next;
		removedNode.prev = removedNode.next = null;
		removedNode.obj = null;
		prevNode.next = nextNode;
		nextNode.prev = prevNode;

	}

	private void removeTail() {
		Node<T> prevTail = tail.prev;
		tail.obj = null;
		tail.prev = null;
		prevTail.next = null;
		tail = prevTail;

	}

	private void removeHead() {
		if (head == tail) {
			head = tail = null;
		} else {
			Node<T> nextHead = head.next;
			head.obj = null;
			head.next = null;
			head = nextHead;
			nextHead.prev = null;
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
		Node<T> node = getNode(index);
		T res = node.obj;
		removeNode(node);
		return res;
	}

	@Override
	public int indexOf(Predicate<T> predicate) {
		int index = 0;
		Node<T> current = head;
		while (current != null && !predicate.test(current.obj)) {
			current = current.next;
			index++;
		}
		return current == null ? -1 : index;
	}

	@Override
	public int lastIndexOf(Predicate<T> predicate) {
		int index = size - 1;
		Node<T> current = tail;
		while (current != null && !predicate.test(current.obj)) {
			current = current.prev;
			index--;
		}
		return current == null ? -1 : index;
	}

	@Override
	public boolean equals(Object obj) {
		return listEqualsTo(obj);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		LinkedList<T> res = new LinkedList<>();
		res.addAll(this);
		return res;
	}
}