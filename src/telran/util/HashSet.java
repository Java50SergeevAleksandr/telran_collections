package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class HashSet<T> implements Set<T>, Cloneable {
	private static final int DEFAULT_TABLE_LENGTH = 16;
	private LinkedList<T>[] hashTable;
	private float factor = 0.75f;
	private int size;

	@SuppressWarnings("unchecked")
	public HashSet(int tableLength) {
		hashTable = new LinkedList[tableLength];
	}

	public HashSet() {
		this(DEFAULT_TABLE_LENGTH);
	}

	@Override
	public boolean add(T obj) {
		boolean res = false;

		if (!contains(obj)) {
			if (((float) size / hashTable.length) >= factor) {
				hashTableRecreation();
			}

			addHashTable(obj, hashTable);
			size++;
			res = true;
		}

		return res;
	}

	@SuppressWarnings("unchecked")
	private void hashTableRecreation() {
		LinkedList<T>[] tmp = new LinkedList[hashTable.length * 2];

		for (LinkedList<T> list : hashTable) {
			if (list != null) {
				for (T obj : list) {
					addHashTable(obj, tmp);
				}
			}
		}
		hashTable = tmp;
	}

	private void addHashTable(T obj, LinkedList<T>[] tmp) {
		int index = Math.abs(obj.hashCode() % tmp.length);
		if (tmp[index] == null) {
			tmp[index] = new LinkedList<>();
		}
		tmp[index].add(obj);

	}

	private int getIndex(Object obj) {
		int hashCode = obj.hashCode();
		return Math.abs(hashCode) % hashTable.length;
	}

	@Override
	public boolean remove(Object pattern) {
		int index = getIndex(pattern);
		boolean res = false;
		LinkedList<T> list = hashTable[index];
		if (list != null) {
			res = list.remove(pattern);
			if (res) {
				size--;
			}
		}
		return res;
	}

	@Override
	public boolean contains(Object pattern) {
		int index = getIndex(pattern);
		boolean res = false;
		LinkedList<T> list = hashTable[index];
		if (list != null) {
			res = list.contains(pattern);
		}
		return res;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new HashSetIterator();
	}

	private class HashSetIterator implements Iterator<T> {
		private boolean isRemoveAvailable = false;
		private int checkedSize = 0;
		private int tableIndex = 0;
		private int positionInList = 0;

		@Override
		public boolean hasNext() {
			return checkedSize < size;
		}

		private LinkedList<T> getCurrentList() {
			LinkedList<T> list = hashTable[tableIndex];
			while (list == null || list.size() == 0) {
				list = hashTable[++tableIndex];
			}
			return list;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			if (positionInList >= getCurrentList().size()) {
				tableIndex++;
				positionInList = 0;
			}

			isRemoveAvailable = true;
			checkedSize++;
			return getCurrentList().get(positionInList++);
		}

		@Override
		public void remove() {
			if (!isRemoveAvailable) {
				throw new IllegalStateException();
			}
			getCurrentList().remove(--positionInList);
			size--;
			checkedSize--;
			isRemoveAvailable = false;
		}
	}

	@Override
	public T get(Object pattern) {
		int index = getIndex(pattern);
		T res = null;
		LinkedList<T> list = hashTable[index];

		if (list != null) {
			Iterator<T> it = list.iterator();

			while (it.hasNext() && res == null) {
				T obj = it.next();

				if (Objects.equals(pattern, obj)) {
					res = obj;
				}
			}
		}
		return res;
	}

	@Override
	public boolean equals(Object obj) {
		return setEqualsTo(obj);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// Fix this method to return Shallow Copy of this collection
		HashSet<T> res = new HashSet<>(hashTable.length);
		res.addAll(this);
		return res;
	}
}
