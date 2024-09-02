package org.example.special_collection;

import org.example.special_collection.exception.CapacityException;
import org.example.special_collection.exception.ExpansionCoefficientException;
import org.example.special_collection.exception.IndexOutOfRangeException;
import org.example.special_collection.exception.NullParamException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

/**
 * не хранит null
 *
 * @param <T>
 */
public class SpecialArrayList<T> implements Comparable<SpecialArrayList<T>> {
    private static final Double HIGHEST_EXPANSION_COEFFICIENT = 2.0;
    private Double expansionCoeff = 1.5;
    private T[] arr;
    private int size = 0;

    public SpecialArrayList() {
        arr = createArr(10);
    }

    public SpecialArrayList(int capacity) {
        if (capacity <= 0)
            throw new CapacityException(capacity);

        arr = createArr(capacity);
    }

    public SpecialArrayList(int capacity, Double expansionCoeff) {
        if (capacity <= 0)
            throw new CapacityException(capacity);
        if (expansionCoeff <= 1.0 || expansionCoeff > HIGHEST_EXPANSION_COEFFICIENT)
            throw new ExpansionCoefficientException(expansionCoeff, HIGHEST_EXPANSION_COEFFICIENT);

        arr = createArr(capacity);
        this.expansionCoeff = expansionCoeff;
    }

    public SpecialArrayList(T[] arr) {
        if (arr == null)
            throw new NullParamException();

        T[] externalArr = prepareReceivingArray(arr);
        this.size = externalArr.length;
        this.arr = createArr(size);

        System.arraycopy(externalArr, 0, this.arr, 0, size);
    }

    public void add(T obj) {
        if (obj == null)
            throw new NullParamException();

        if (size == arr.length)
            expanseArray();

        arr[size++] = obj;
    }

    public void add(int index, T obj) {
        if (index < 0 || index >= arr.length)
            throw new IndexOutOfRangeException(arr.length, index);
        if (obj == null)
            throw new NullParamException();

        if (size == arr.length)
            expanseArray();

        System.arraycopy(arr, index, arr, index + 1, size - index);
        arr[index] = obj;
        size++;

    }

    public T get(int index) {
        if (index < 0 || index >= arr.length)
            throw new IndexOutOfRangeException(arr.length, index);
        return arr[index];
    }

    public void remove(int index) {
        if (index < 0 || index >= arr.length)
            throw new IndexOutOfRangeException(arr.length, index);

        System.arraycopy(arr, index + 1, arr, index, size - index - 1);
        arr[--size] = null;
    }

    public void clean() {
        for (int i = 0; i < size; i++) {
            arr[i] = null;
        }
        size = 0;
    }

    public void sort() {
        if (!(arr[0] instanceof Comparable))
            throw new RuntimeException("Not comparable!");

        quicksort(this.arr, 0, size - 1, Optional.empty());
    }

    public void sort(Comparator<T> comparator) {
        quicksort(this.arr, 0, size - 1, Optional.of(comparator));
    }

    public void replace(int index, T obj) {
        if (index < 0 || index >= arr.length)
            throw new IndexOutOfRangeException(arr.length, index);
        if (obj == null)
            throw new NullParamException();

        arr[index] = obj;
    }

    public int getSize() {
        return this.size;
    }


    // others
    public void addAll(T[] arr) {
        if (arr == null)
            throw new NullParamException();

        T[] externalArr = prepareReceivingArray(arr);
        if (size + externalArr.length >= this.arr.length)
            expanseArray(size + externalArr.length);

        System.arraycopy(externalArr, 0, this.arr, size, externalArr.length);
        size += externalArr.length;
    }

    public void trim() {
        T[] newArr = createArr(size);
        System.arraycopy(this.arr, 0, newArr, 0, newArr.length);
        this.arr = newArr;
    }

    public T[] toArray() {
        T[] publicArr = createArr(this.arr.length);
        System.arraycopy(arr, 0, publicArr, 0, arr.length);
        return publicArr;
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

        //индекс елемента относительно которого сортировался массив
        //обльшие элементы чем указанный находятся справа, меньшие слева
        if (optionalComparator.isEmpty()) {
            pivot = partition(arr, lowest, highest);
        } else {
            pivot = partition(arr, lowest, highest, optionalComparator.get());
        }

        //Сортируем обе части
        quicksort(arr, lowest, pivot - 1, optionalComparator);
        quicksort(arr, pivot + 1, highest, optionalComparator);

    }

    private int partition(T[] arr, int lowest, int highest, Comparator<T> c) {
        T pivot = arr[highest];

        int smallerSideEnd = lowest;

        for (int i = lowest; i < highest; i++) {
            if (c.compare(pivot, arr[i]) >= 0) {
                swap(i, smallerSideEnd++);
            }
        }

        //меняем местами елемент, который находится после половины с елементами меньшими чем pivot, и pivot елемент
        swap(smallerSideEnd, highest);

        //возвращаем pivot елемент
        //гарантированно что слева от этого элемента находятся элементы мельние чем pivot, а справа - бОльшие
        return smallerSideEnd;
    }

    private int partition(T[] arr, int lowest, int highest) {
        if (!(arr[0] instanceof Comparable))
            throw new RuntimeException("Not comparable!");

        Comparable<T> pivot = (Comparable<T>) arr[highest];

        int smallerSideEnd = lowest;

        for (int i = lowest; i < highest - 1; i++) {
            if (pivot.compareTo(arr[i]) >= 0) {
                swap(i, smallerSideEnd++);

            }
        }

        //меняем местами елемент, который находится после половины с елементами меньшими чем pivot, и pivot елемент
        swap(smallerSideEnd, highest);

        //возвращаем pivot елемент
        //гарантированно что слева от этого элемента находятся элементы мельние чем pivot, а справа - бОльшие
        return smallerSideEnd;
    }

    private void swap(int e1, int e2) {
        T temp = arr[e1];
        arr[e1] = arr[e2];
        arr[e2] = temp;
    }

    private void expanseArray() {
        T[] newArr = createArr((int) (arr.length * expansionCoeff) + 1);
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
        if (capacity <= 0)
            throw new CapacityException(capacity);

        return (T[]) new Object[capacity];
    }

    private T[] prepareReceivingArray(T[] arr) {
        T[] solidArray = removeGaps(arr);
        return trim(solidArray);
    }

    private T[] trim(T[] solidArr) {
        int newSize = solidArr.length;
        for (int i = 0; i < solidArr.length; i++)
            if (solidArr[i] == null)
                newSize = i;

        T[] newArr = createArr(newSize);
        System.arraycopy(solidArr, 0, newArr, 0, newArr.length);

        return newArr;
    }

    private T[] removeGaps(T[] arr) {
        T[] newArr = createArr(arr.length);
        System.arraycopy(arr, 0, newArr, 0, newArr.length);

        int actuallySize = 0;
        for (int i = 0; i < newArr.length; i++) {
            if (newArr[i] != null)
                newArr[actuallySize++] = newArr[i];
        }
        return newArr;
    }

    @Override
    public int compareTo(SpecialArrayList<T> o) {
        return size - o.getSize();  //todo придумать лучший способ
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecialArrayList)) return false;

        SpecialArrayList<?> that = (SpecialArrayList<?>) o;
        return Arrays.equals(arr, that.arr);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(arr);
    }

    @Override
    public String toString() {
        return "SpecialArrayList{" +
                Arrays.toString(arr) +
                '}';
    }
}
