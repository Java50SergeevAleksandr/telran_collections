package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class HashSet<T> implements Set<T> {
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
		if ((float) size / hashTable.length >= factor) {
			hashTableRecreation();
		}

		if (!contains(obj)) {
			int index = getIndex(obj);
			LinkedList<T> list = null;

			if (hashTable[index] == null) {
				hashTable[index] = new LinkedList<>();
			}

			list = hashTable[index];
			res = true;
			list.add(obj);
			size++;
		}

		return res;
	}

	private void hashTableRecreation() {
		HashSet<T> tmp = new HashSet<>(hashTable.length * 2);

		for (LinkedList<T> list : hashTable) {
			if (list != null) {
				for (T obj : list) {
					tmp.add(obj);
				}
			}
		}

		hashTable = tmp.hashTable;
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
		int checkedSize = 0;
		int tableIndex = 0;
		int positionInList = 0;

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

}
