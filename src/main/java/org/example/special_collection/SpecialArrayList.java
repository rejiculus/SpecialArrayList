package org.example.special_collection;

import org.example.special_collection.exception.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

/**
 * Custom implementation of an ArrayList.
 * Stores values of the specified type.
 *
 * @param <T> the type of stored elements.
 */
public class SpecialArrayList<T> {
    private Double expansionCoefficient = 1.5;
    private T[] arr;
    private int size = 0;

    /**
     * Default constructor.
     * The initial array size is set to 10.
     * By default, when the specified size is exceeded,
     * the array is increased by 1.5 times.
     */
    public SpecialArrayList() {
        arr = createArr(10);
    }

    /**
     * Constructor with a specified default size.
     * When the specified size is exceeded, the array is increased by 1.5 times.
     *
     * @param capacity the size of the array to be created.
     * @throws CapacityException if the specified size is less than zero.
     */
    public SpecialArrayList(int capacity) {
        if (capacity <= 0)
            throw new CapacityException(capacity);

        arr = createArr(capacity);
    }

    /**
     * Constructor with a specified default size and expansion coefficient.
     * When the specified size is exceeded, the array
     * is increased according to the specified coefficient.
     *
     * @param capacity             the size of the array to be created.
     * @param expansionCoefficient the expansion coefficient. Must be greater than 1.0.
     * @throws CapacityException             if the specified size is less than zero.
     * @throws ExpansionCoefficientException if the coefficient is less than (or equal to) 1.0.
     */
    public SpecialArrayList(int capacity, Double expansionCoefficient) {
        if (capacity <= 0)
            throw new CapacityException(capacity);
        if (expansionCoefficient <= 1.0)
            throw new ExpansionCoefficientException(expansionCoefficient);

        arr = createArr(capacity);
        this.expansionCoefficient = expansionCoefficient;
    }

    /**
     * Constructor with a specified array.
     * Creates a copy of the given array,
     * with the size matching the original array's size.
     * When the size is exceeded, the array expands by 1.5 times.
     *
     * @param externalArr the original array.
     * @throws NullParamException if the provided parameter is null.
     */
    public SpecialArrayList(T[] externalArr) {
        if (externalArr == null)
            throw new NullParamException();

        this.size = externalArr.length;
        this.arr = createArr(size);
        System.arraycopy(externalArr, 0, this.arr, 0, size);
    }

    /**
     * Constructor that creates an array based on a collection.
     * Creates a copy of the specified array,
     * with the size matching the original collection's size.
     * When the size is exceeded, the array expands by 1.5 times.
     *
     * @param collection the original collection.
     * @throws NullParamException if the provided parameter is null.
     */
    public SpecialArrayList(Collection<T> collection) {
        if (collection == null)
            throw new NullParamException();

        arr = (T[]) collection.toArray();
        size = collection.size();
    }

    /**
     * Adds an element to the end of the array.
     * When the array's capacity is exceeded, it is expanded
     * according to the expansion coefficient (expansionCoefficient).
     *
     * @param obj the object to be added.
     * @throws NullParamException if the provided parameter is null.
     */
    public void add(T obj) {

        if (size == arr.length)
            expanseArray();

        arr[size++] = obj;
    }

    /**
     * Adds an element to the array at the specified index,
     * shifting elements starting from this index one position towards the end of the array.
     * When the array's capacity is exceeded, it is expanded
     * according to the expansion coefficient (expansionCoefficient).
     *
     * @param index the position where the element should be added.
     * @param obj   the object to be added.
     * @throws IndexOutOfRangeException if the index is less than zero or greater than the current size.
     * @throws NullParamException       if the provided parameter is null.
     */
    public void add(int index, T obj) {
        if (index < 0 || index > size)
            throw new IndexOutOfRangeException(size, index);

        if (size == arr.length)
            expanseArray();

        System.arraycopy(arr, index, arr, index + 1, size - index);
        arr[index] = obj;
        size++;
    }

    /**
     * Retrieves an element by index.
     *
     * @param index the position (index) of the element to be retrieved.
     * @return the element of the type corresponding to the collection's type.
     * @throws IndexOutOfRangeException if the index is less than zero or greater than the current size.
     */
    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfRangeException(size, index);

        return arr[index];
    }

    /**
     * Removes an element by index.
     * Removes the element at the specified index,
     * shifting elements to the right of the index one position to the left.
     *
     * @param index the index of the element to be removed.
     * @throws IndexOutOfRangeException if the index is less than zero or greater than the current size.
     */
    public void remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfRangeException(size, index);

        System.arraycopy(arr, index + 1, arr, index, size - index - 1);
        arr[--size] = null;
    }

    /**
     * Clears the collection.
     * This does not reduce the size of the underlying array.
     */
    public void clean() {
        while (size > 0) {
            arr[--size] = null;
        }
    }

    /**
     * Sorts the elements of the array.
     * Array have to be without null elements.
     *
     * @throws NotComparableException   if the specified collection type
     *                                  does not implement Comparable.
     * @throws SortNullElementException if collection contains null elements.
     */
    public void sort() {
        if (!(arr[0] instanceof Comparable))
            throw new NotComparableException();
        if (hasNull())
            throw new SortNullElementException();

        quicksort(this.arr, 0, size - 1, Optional.empty());
    }

    /**
     * Sorts the collection using a Comparator.
     * Array have to be without null elements.
     *
     * @param comparator the comparator used for comparing collection elements.
     * @throws NullParamException       if the provided parameter is null.
     * @throws SortNullElementException if collection contains null elements.
     */
    public void sort(Comparator<T> comparator) {
        if (comparator == null)
            throw new NullParamException();
        if (hasNull())
            throw new SortNullElementException();

        quicksort(this.arr, 0, size - 1, Optional.of(comparator));
    }

    /**
     * Replaces the element at the specified position.
     *
     * @param index the position of the element to be replaced.
     * @param obj   the object to replace the element with.
     * @throws IndexOutOfRangeException if the index is less than zero or greater than the current size.
     * @throws NullParamException       if the provided parameter is null.
     */
    public void replace(int index, T obj) {
        if (index < 0 || index >= size)
            throw new IndexOutOfRangeException(size, index);

        arr[index] = obj;
    }

    /**
     * Returns the number of elements in the collection.
     *
     * @return the number of elements in the collection.
     */
    public int size() {
        return this.size;
    }


    // others

    /**
     * Adds all specified elements.
     * Expands the array as needed to accommodate both arrays.
     *
     * @param externalArr the elements to be added.
     * @throws NullParamException if the provided parameter is null.
     */
    public void addAll(T[] externalArr) {
        if (externalArr == null)
            throw new NullParamException();

        if (size + externalArr.length >= this.arr.length)
            expanseArray(size + externalArr.length);

        System.arraycopy(externalArr, 0, this.arr, size, externalArr.length);
        size += externalArr.length;
    }

    /**
     * Reduces the size of the internal array to the number of elements in it.
     */
    public void trim() {
        T[] newArr = createArr(size);
        System.arraycopy(this.arr, 0, newArr, 0, newArr.length);
        this.arr = newArr;
    }

    /**
     * Returns a copy of the collection's array.
     *
     * @return a copy of the collection's array.
     */
    public Object[] toArray() {
        Object[] publicArr = new Object[this.arr.length];
        System.arraycopy(arr, 0, publicArr, 0, arr.length);
        return publicArr;
    }

    /**
     * Returns a copy of the collection's array.
     *
     * @param externalArr the array into which the copy is to be placed.
     * @return a copy of the collection's array.
     * @throws NullParamException if the provided parameter is null.
     */
    public T[] toArray(T[] externalArr) {
        if (externalArr == null)
            throw new NullParamException();

        if (externalArr.length < size)
            return (T[]) Arrays.copyOf(arr, size, externalArr.getClass());

        System.arraycopy(arr, 0, externalArr, 0, size);
        return externalArr;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty, otherwise false.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Check is collection contains null elements.
     *
     * @return true - if collection contains, false - if not.
     */
    public boolean hasNull() {
        for (int i = 0; i < size; i++) {
            if (arr[i] == null)
                return true;
        }
        return false;
    }

    //private methods
    private void quicksort(T[] arr, int lowest, int highest, Optional<Comparator<T>> optionalComparator) {
        if (lowest >= highest)
            return;
        if (lowest < 0)
            throw new IndexOutOfRangeException(lowest);
        if (highest >= arr.length)
            throw new IndexOutOfRangeException(arr.length, highest);

        int pivot;

        //индекс элемента относительно которого сортировался массив
        //Большие элементы чем указанный находятся справа, меньшие слева
        if (optionalComparator.isEmpty()) {
            pivot = partition(arr, lowest, highest);
        } else {
            pivot = partition(arr, lowest, highest, optionalComparator.get());
        }

        //Сортируем обе части
        quicksort(arr, lowest, pivot - 1, optionalComparator);
        quicksort(arr, pivot + 1, highest, optionalComparator);

    }

    private int partition(T[] arr, int lowest, int highest, Comparator<T> comparator) {
        T pivot = arr[highest];

        int smallerSideEnd = lowest;

        for (int i = lowest; i < highest; i++) {
            if (comparator.compare(pivot, arr[i]) >= 0) {
                swap(i, smallerSideEnd++);
            }
        }

        //меняем местами элемент, который находится после половины с элементами меньшими чем pivot, и pivot элемент
        swap(smallerSideEnd, highest);

        //возвращаем pivot элемент
        //гарантированно что слева от этого элемента находятся элементы меньшие чем pivot, а справа - бОльшие
        return smallerSideEnd;
    }

    private int partition(T[] arr, int lowest, int highest) {
        if (!(arr[0] instanceof Comparable))
            throw new NotComparableException();

        Comparable<T> pivot = (Comparable<T>) arr[highest];

        int smallerSideEnd = lowest;

        for (int i = lowest; i < highest; i++) {
            if (pivot.compareTo(arr[i]) >= 0) {
                swap(i, smallerSideEnd++);

            }
        }

        //меняем местами элемент, который находится после половины с элементами меньшими чем pivot, и pivot элемент
        swap(smallerSideEnd, highest);

        //возвращаем pivot элемент
        //гарантированно что слева от этого элемента находятся элементы меньшие чем pivot, а справа - бОльшие
        return smallerSideEnd;
    }

    private void swap(int e1, int e2) {
        T temp = arr[e1];
        arr[e1] = arr[e2];
        arr[e2] = temp;
    }

    private void expanseArray() {
        T[] newArr = createArr((int) (arr.length * expansionCoefficient) + 1);
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        this.arr = newArr;
    }

    private void expanseArray(int capacity) {
        if (capacity <= arr.length)
            throw new CapacityException(capacity, arr.length);

        T[] newArr = createArr(capacity);
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        this.arr = newArr;
    }

    private static <T> T[] createArr(int capacity) {
        if (capacity < 0)
            throw new CapacityException(capacity);

        return (T[]) new Object[capacity];
    }

    /**
     * Comparing array from this object with array form obj
     *
     * @param obj the object we want to compare with.
     * @return true - if equals arrays in both objects, false - if arrays has differences
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SpecialArrayList)) return false;

        SpecialArrayList<?> that = (SpecialArrayList<?>) obj;
        return Arrays.equals(arr, that.arr);
    }

    /**
     * Returns a hash code value of this object.
     * The value is calculated by the hash code of the internal array
     *
     * @return a hash code value of this object.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(arr);
    }

    /**
     * Describes the current object and the elements it stores.
     *
     * @return a string description of the current object
     */
    @Override
    public String toString() {
        return "SpecialArrayList{" +
                Arrays.toString(arr) +
                '}';
    }
}
