package org.example.special_collection;

import org.example.special_collection.exception.CapacityException;
import org.example.special_collection.exception.ExpansionCoefficientException;
import org.example.special_collection.exception.IndexOutOfRangeException;
import org.example.special_collection.exception.NullParamException;

import java.util.*;

/**
 * Собственная реализация ArrayLis
 * хранит значения указанного типа
 * не хранит null
 *
 * @param <T>
 */
public class SpecialArrayList<T> implements Comparable<SpecialArrayList<T>> {
    private static final Double HIGHEST_EXPANSION_COEFFICIENT = 2.0;
    private Double expansionCoeff = 1.5;
    private T[] arr;
    private int size = 0;

    /**
     * Конструктор по умолчанию
     * изначальный размер массива задается равным 10
     * по умолчанию, при превышении указанного размера
     * массив увеличивается в 1.5 раза
     */
    public SpecialArrayList() {
        arr = createArr(10);
    }

    /**
     * Конструктор с указанием размера по умолчанию
     * при превышении указанного размера - массив увеличивается в 1.5 раза
     *
     * @param capacity - размер создаваемого массива
     */
    public SpecialArrayList(int capacity) {
        if (capacity <= 0)
            throw new CapacityException(capacity);

        arr = createArr(capacity);
    }

    /**
     * Конструктор с указанием размера по умолчанию и коэффициента расширения
     * при превышении указанного размера - массив
     * увеличивается в соответствии с указанным коэффициентом
     *
     * @param capacity       - размер создаваемого массива
     * @param expansionCoeff - коэффициент расширения. Должен быть больше 1.0, но
     *                       не больше чем HIGHEST_EXPANSION_COEFFICIENT
     */
    public SpecialArrayList(int capacity, Double expansionCoeff) {
        if (capacity <= 0)
            throw new CapacityException(capacity);
        if (expansionCoeff <= 1.0 || expansionCoeff > HIGHEST_EXPANSION_COEFFICIENT)
            throw new ExpansionCoefficientException(expansionCoeff, HIGHEST_EXPANSION_COEFFICIENT);

        arr = createArr(capacity);
        this.expansionCoeff = expansionCoeff;
    }

    /**
     * Конструктор с заданным массивом
     * создает копию указанного массива
     * при этом размер массива совпадает с размером исходного массива
     * при превышении размера - массив расширяется в 1.5 раза
     *
     * @param arr - исходный массив
     */
    public SpecialArrayList(T[] arr) {
        if (arr == null)
            throw new NullParamException();

        T[] externalArr = prepareReceivingArray(arr);
        this.size = externalArr.length;
        this.arr = createArr(size);

        System.arraycopy(externalArr, 0, this.arr, 0, size);
    }

    /**
     * Конструктор с заданием массива на основе коллекции.
     * Создает копию указанного массива,
     * при этом размер массива совпадает с размером исходного массива.
     * При превышении размера - массив расширяется в 1.5 раза.
     *
     * @param collection - исходная коллекция
     */
    public SpecialArrayList(Collection<T> collection) {
        if (collection == null)
            throw new NullParamException();
        arr = (T[]) collection.toArray();
        size = collection.size();
    }

    /**
     * Добавление элемента в конец массива
     * при превышении вместимости массива - происходит расширение
     * в соответствии с коэффициентом расширения(expansionCoeff)
     *
     * @param obj добавляемый объект
     */
    public void add(T obj) {
        if (obj == null)
            throw new NullParamException();

        if (size == arr.length)
            expanseArray();

        arr[size++] = obj;
    }

    /**
     * Добавление элемента в массив по индексу.
     * При этом сдвигая элементы начиная с этого индекса на один элемент к концу массива.
     * При превышении вместимости массива - происходит расширение
     * в соответствии с коэффициентом расширения(expansionCoeff)
     *
     * @param index место, в которое нужно добавить элемент
     * @param obj   добавляемый объект
     */
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

    /**
     * Получение элемента по индексу
     *
     * @param index - положение(индекс) элемента который нужно получить
     * @return - элемент типа, соответствующего типу коллекции
     */
    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfRangeException(arr.length, index);
        return arr[index];
    }

    /**
     * Удаление элемента по индексу
     * удаляет элемент, находящийся по указанному индексу
     * при этом сдвигая справа-стоящие элементы на
     * один элемент влево
     *
     * @param index - индекс элемента, который нужно удалить
     */
    public void remove(int index) {
        if (index < 0 || index >= arr.length)
            throw new IndexOutOfRangeException(arr.length, index);

        System.arraycopy(arr, index + 1, arr, index, size - index - 1);
        arr[--size] = null;
    }

    /**
     * Отчищает коллекцию
     * при этом не сокращает размер его массива
     */
    public void clean() {
        for (int i = 0; i < size; i++) {
            arr[i] = null;
        }
        size = 0;
    }

    /**
     * Сортирует элементы массива
     *
     * @throws RuntimeException - если указанный тип коллекции,
     *                          не реализует Comparable
     */
    public void sort() {
        if (!(arr[0] instanceof Comparable))
            throw new RuntimeException("Not comparable!");

        quicksort(this.arr, 0, size - 1, Optional.empty());
    }

    /**
     * Сортирует коллекцию, используя Comparator
     *
     * @param comparator компаратор для сравнения элементов коллекции
     */
    public void sort(Comparator<T> comparator) {
        quicksort(this.arr, 0, size - 1, Optional.of(comparator));
    }

    /**
     * Заменяет элемент на указанной позиции
     *
     * @param index позиция элемента, который нужно заменить
     * @param obj   объект которым нужно заменить элемент
     */
    public void replace(int index, T obj) {
        if (index < 0 || index >= arr.length)
            throw new IndexOutOfRangeException(arr.length, index);
        if (obj == null)
            throw new NullParamException();

        arr[index] = obj;
    }

    /**
     * Возвращает количество элементов в коллекции
     *
     * @return количество элементов в коллекции
     */
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

    /**
     * Сокращает размер внутреннего массива до количества элементов в нем
     */
    public void trim() {
        T[] newArr = createArr(size);
        System.arraycopy(this.arr, 0, newArr, 0, newArr.length);
        this.arr = newArr;
    }

    /**
     * Возвращает копию массива коллекции
     *
     * @return копия массива коллекции
     */
    public T[] toArray() {//todo tests
        T[] publicArr = createArr(this.arr.length);
        System.arraycopy(arr, 0, publicArr, 0, arr.length);
        return publicArr;
    }

    /**
     * Возвращает копию массива коллекции
     *
     * @param a массив в который предполагается копирование
     * @return копия массива коллекции
     */
    public T[] toArray(T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(arr, size, a.getClass());
        System.arraycopy(arr, 0, a, 0, size);
        return a;
    }

    /**
     * Проверка пустоты листа
     *
     * @return ture - если лист пуст, в ином случае - false
     */
    public boolean isEmpty() {
        return size == 0;
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

    private int partition(T[] arr, int lowest, int highest, Comparator<T> c) {
        T pivot = arr[highest];

        int smallerSideEnd = lowest;

        for (int i = lowest; i < highest; i++) {
            if (c.compare(pivot, arr[i]) >= 0) {
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
            throw new RuntimeException("Not comparable!");

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
        for (int i = solidArr.length - 1; i >= 0; i--)
            if (solidArr[i] == null) {
                newSize = i;
            }

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
