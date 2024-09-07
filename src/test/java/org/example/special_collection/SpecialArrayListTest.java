package org.example.special_collection;

import org.example.special_collection.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SpecialArrayListTest {
    static final Random RANDOM = new Random();


    //constructors
    @Test
    void creteEmptyListTest() {
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>();
        assertTrue(currentArr.isEmpty());
    }


    //get
    @Test
    void getTest() {
        String[] expectedArr = new String[]{"texture", "treatment", "jail", "zip", "zinc", "enthusiastic", "argue", "five", "arithmetic", "didactic", "terrify", "pet"};
        SpecialArrayList<String> currentArr = new SpecialArrayList<>(expectedArr);

        assertEquals(expectedArr.length, currentArr.size());
        for (int i = 0; i < expectedArr.length; i++) {
            assertEquals(expectedArr[i], currentArr.get(i));
        }
    }

    @Test
    void getOutOfBoundTest() {
        Integer[] expectedArr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(expectedArr);

        for (int i = -10; i < 15; i++) {
            if (i < 0 || i >= expectedArr.length) {
                int index = i;
                Assertions.assertThrows(IndexOutOfRangeException.class, () -> currentArr.get(index));
            }
        }
    }

    //constructors

    @Test
    void createEmptyListWithCapacityTest() {
        SpecialArrayList<Double> currentArr = new SpecialArrayList<>(42);

        assertTrue(currentArr.isEmpty());
    }

    @Test
    void createEmptyListCapacityCoefficientTest() {
        SpecialArrayList<Double> arr = new SpecialArrayList<>(42, 2.0);

        assertTrue(arr.isEmpty());
    }

    @Test
    void createEmptyListWithCapacityWrongTest() {
        Assertions.assertThrows(CapacityException.class, () -> new SpecialArrayList<>(0));
        Assertions.assertThrows(CapacityException.class, () -> new SpecialArrayList<>(-1));
    }

    @Test
    void createEmptyListCapacityCoefficientWrongTest() {
        Assertions.assertThrows(CapacityException.class, () -> new SpecialArrayList<>(-1, 2.0));
        Assertions.assertThrows(ExpansionCoefficientException.class, () -> new SpecialArrayList<>(5, 1.0));
    }

    @Test
    void createListByCollectionTest() {
        ArrayList<String> expectedArr = new ArrayList<>(Arrays.asList("texture", "treatment", "jail", "zip", "zinc", "enthusiastic", "argue", "five", "arithmetic", "didactic", "terrify", "pet"));
        SpecialArrayList<String> resultArr = new SpecialArrayList<>(expectedArr);

        for (int i = 0; i < expectedArr.size(); i++) {
            assertEquals(expectedArr.get(i), resultArr.get(i));
        }
    }


    //add
    @Test
    void addTest() {
        SpecialArrayList<String> arr = new SpecialArrayList<>();

        arr.add("WOW");

        assertEquals("WOW", arr.get(0));
    }

    @Test
    void add100000Test() {
        int capacity = 100_000;
        SpecialArrayList<String> currentArr = new SpecialArrayList<>();

        for (int i = 0; i < capacity; i++) {
            currentArr.add("WOW " + i);
        }

        for (int i = 0; i < capacity; i++) {
            assertEquals("WOW " + i, currentArr.get(i));
        }
    }

    @Test
    void addExpansionTest() {
        ArrayList<String> expectedArr = new ArrayList<>(Arrays.asList("texture", "treatment", "jail", "zip", "zinc", "enthusiastic", "argue", "five", "arithmetic", "didactic", "terrify", "pet"));
        SpecialArrayList<String> currentArr = new SpecialArrayList<>(10);

        for (String element : expectedArr) {
            currentArr.add(element);
        }

        assertEquals(expectedArr.size(), currentArr.size());
        for (int i = 0; i < expectedArr.size(); i++) {
            assertEquals(expectedArr.get(i), currentArr.get(i));
        }
    }

    @Test
    void addNullTest() {
        int capacity = 100;
        SpecialArrayList<String> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            currentArr.add(null);
        }

        assertEquals(capacity, currentArr.size());
        for (int i = 0; i < capacity; i++) {
            assertNull(currentArr.get(i));
        }
    }

    //add by index
    @Test
    void addByIndexLTest() {
        int capacity = 100_000;
        ArrayList<Integer> expectedArr = new ArrayList<>(capacity);
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            Integer val = RANDOM.nextInt(1000);
            currentArr.add(val);
            expectedArr.add(val);
        }
        //to first index
        for (int i = 0; i < 10; i++) {
            currentArr.add(0, -1);
            expectedArr.add(0, -1);
        }

        //to middle
        for (int i = 0; i < 10; i++) {
            currentArr.add(capacity/2, 42);
            expectedArr.add(capacity/2, 42);
        }

        //to end
        for (int i = 0; i < 10; i++) {
            currentArr.add(currentArr.size() - 1, 99999999);
            expectedArr.add(expectedArr.size() - 1, 99999999);
        }

        assertEquals(expectedArr.size(), currentArr.size());
        for (int i = 0; i < expectedArr.size(); i++) {
            assertEquals(expectedArr.get(i), currentArr.get(i));
        }
    }

    @Test
    void addByIndexNullTest() {
        int capacity = 100;
        ArrayList<Integer> expectedArr = new ArrayList<>(capacity);
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            Integer val = RANDOM.nextInt(1000);
            currentArr.add(val);
            expectedArr.add(val);
        }

        //to middle
        for (int i = 0; i < 10; i++) {
            currentArr.add(capacity / 2, null);
            expectedArr.add(capacity / 2, null);
        }

        assertEquals(expectedArr.size(), currentArr.size());
        for (int i = 0; i < expectedArr.size(); i++) {
            assertEquals(expectedArr.get(i), currentArr.get(i));
        }
    }

    @Test
    void addByIndexOutOfBoundTest() {
        Integer[] expectedArr = new Integer[]{1, 2, 3, 4, 5, 6, 7};
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(expectedArr);

        for (int i = -10; i < 10; i++) {
            if (i < 0 || i > expectedArr.length) {
                int finalI = i;
                Assertions.assertThrows(IndexOutOfRangeException.class, () -> currentArr.add(finalI, 99));
            }
        }
    }

    //remove
    @Test
    void removeFirstTest() {
        Integer[] inputArr = new Integer[]{1, 2, 3, 4, 5, 6, 7};
        Integer[] expectedArr = new Integer[]{2, 3, 4, 5, 6, 7};
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(inputArr);

        currentArr.remove(0);

        assertEquals(expectedArr.length, currentArr.size());
        for (int i = 0; i < expectedArr.length; i++) {
            assertEquals(expectedArr[i], currentArr.get(i));
        }
    }

    @Test
    void removeLastTest() {
        Integer[] inputArr = new Integer[]{1, 2, 3, 4, 5, 6, 7};
        Integer[] expectedArr = new Integer[]{1, 2, 3, 4, 5, 6};
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(inputArr);

        currentArr.remove(inputArr.length - 1);

        assertEquals(expectedArr.length, currentArr.size());
        for (int i = 0; i < expectedArr.length; i++) {
            assertEquals(expectedArr[i], currentArr.get(i));
        }
    }

    @Test
    void removeMiddleTest() {
        Integer[] inputArr = new Integer[]{1, 2, 3, 4, 5, 6, 7};
        Integer[] expectedArr = new Integer[]{1, 2, 4, 5, 6, 7};
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(inputArr);

        currentArr.remove(2);

        assertEquals(expectedArr.length, currentArr.size());
        for (int i = 0; i < expectedArr.length; i++) {
            assertEquals(expectedArr[i], currentArr.get(i));
        }
    }

    @Test
    void removeAllTest() {
        Integer[] expectedArr = new Integer[]{1, 2, 3, 4, 5, 6, 7};
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(expectedArr);

        int size = currentArr.size();
        for (int i = expectedArr.length - 1; i >= 0; i--) {
            currentArr.remove(i);
            if (!currentArr.isEmpty())
                assertEquals(--size, currentArr.size());
        }

        assertTrue(currentArr.isEmpty());
    }

    @Test
    void removeOutOfBoundTest() {
        Integer[] expectedArr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(expectedArr);

        for (int i = -10; i < 15; i++) {
            if (i < 0 || i >= expectedArr.length) {
                int index = i;
                Assertions.assertThrows(IndexOutOfRangeException.class, () -> currentArr.remove(index));
            }
        }
    }

    //clean
    @Test
    void cleanTest() {
        Integer[] expectedArr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(expectedArr);

        currentArr.clean();

        assertEquals(0, currentArr.size());
        assertTrue(currentArr.isEmpty());
    }

    //sort comparable
    @Test
    void sortComparableTest() {
        int capacity = 100;
        ArrayList<Integer> expectedArr = new ArrayList<>(capacity);
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            int val = RANDOM.nextInt(1000) - 500;
            currentArr.add(val);
            expectedArr.add(val);
        }

        currentArr.sort();
        expectedArr.sort(Comparator.comparingInt(x -> x));

        for (int i = 0; i < capacity; i++) {
            assertEquals(expectedArr.get(i), currentArr.get(i));
        }
    }

    @Test
    void sortNotComparableTest() {
        class TestObj {}
        SpecialArrayList<TestObj> currentArr = new SpecialArrayList<>(10);

        for (int i = 0; i < 10; i++) {
            currentArr.add(new TestObj());
        }

        Assertions.assertThrows(NotComparableException.class, currentArr::sort);
    }

    //sort comparator
    @Test
    void sortComparatorTest() {
        int capacity = 100;
        ArrayList<Integer> expectedArr = new ArrayList<>(capacity);
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            int val = RANDOM.nextInt(1000) - 500;
            currentArr.add(val);
            expectedArr.add(val);
        }

        currentArr.sort(Comparator.comparingInt(x -> x));
        expectedArr.sort(Comparator.comparingInt(x -> x));

        for (int i = 0; i < capacity; i++) {
            assertEquals(expectedArr.get(i), currentArr.get(i));
        }
    }

    @Test
    void sortComparatorWithNullValueTest() {
        int capacity = 100;
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            currentArr.add(RANDOM.nextInt(1000) - 500);
        }

        currentArr.add(null);

        Assertions.assertThrows(SortNullElementException.class, () -> currentArr.sort(Comparator.comparingInt(x -> x)));
    }

    @Test
    void sortComparatorNullTest() {
        int capacity = 100;
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            int val = RANDOM.nextInt(1000) - 500;
            currentArr.add(val);
        }
        Assertions.assertThrows(NullParamException.class, () -> currentArr.sort(null));

    }

    //replace
    @Test
    void replaceTest() {
        int capacity = 100;
        ArrayList<Integer> expectedArr = new ArrayList<>(capacity);
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            int val = RANDOM.nextInt(1000) - 500;
            currentArr.add(val);
            expectedArr.add(val);
        }

        currentArr.replace(0, 42);
        expectedArr.set(0, 42);

        assertEquals(expectedArr.size(), currentArr.size());
        for (int i = 0; i < expectedArr.size(); i++) {
            assertEquals(expectedArr.get(i), currentArr.get(i));
        }
    }

    @Test
    void replaceAllTest() {
        int capacity = 100;
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            currentArr.add(RANDOM.nextInt(1000) - 500);
        }

        for (int i = 0; i < capacity; i++) {
            currentArr.replace(i, 42);
        }

        Integer[] resultArr = currentArr.toArray(new Integer[0]);
        for (int i = 0; i < currentArr.size(); i++) {
            assertEquals(42, resultArr[i]);
        }
    }

    @Test
    void replaceNullTest() {
        Integer[] inputArr = new Integer[]{1, 2, 3};
        Integer[] expectedArr = new Integer[]{1, null, 3};
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(100);

        currentArr.addAll(inputArr);

        currentArr.replace(1, null);

        for (int i = 0; i < expectedArr.length; i++) {
            assertEquals(expectedArr[i], currentArr.get(i));
        }

    }

    @Test
    void replaceOutOfBoundTest() {
        int capacity = 100;
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            currentArr.add(RANDOM.nextInt(1000));
        }

        for (int i = -10; i < capacity + 10; i++) {
            if (i < 0 || i >= capacity) {
                int val = i;
                Assertions.assertThrows(IndexOutOfRangeException.class, () -> currentArr.replace(val, 42));
            } else {
                currentArr.replace(i, 42);
            }
        }

        for (int i = 0; i < capacity; i++) {
            assertEquals(42, currentArr.get(i));
        }
    }

    //getSize
    @Test
    void sizeTest() {
        int capacity = 100;
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        assertEquals(0, currentArr.size());

        for (int i = 0; i < capacity; i++) {
            currentArr.add(RANDOM.nextInt(1000));
        }
        assertEquals(capacity, currentArr.size());

        currentArr.remove(42);
        assertEquals(capacity - 1, currentArr.size());
    }

    //others

    //addAll
    @Test
    void addAllTest() {
        int capacity = 100;
        Integer[] expectedArr = new Integer[capacity / 2];
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity / 2; i++) {
            expectedArr[i] = RANDOM.nextInt(1000) - 500;
        }

        currentArr.addAll(expectedArr);

        assertEquals(expectedArr.length, currentArr.size());
        for (int i = 0; i < expectedArr.length; i++) {
            assertEquals(expectedArr[i], currentArr.get(i));
        }
    }

    @Test
    void addAllNullTest() {
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(100);

        Assertions.assertThrows(NullParamException.class, () -> currentArr.addAll(null));
    }

    @Test
    void addAllEmptyTest() {
        int capacity = 100;
        Integer[] inputArr = new Integer[capacity / 2];
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        currentArr.addAll(inputArr);

        assertFalse(currentArr.isEmpty());
        assertEquals(capacity/2, currentArr.size());
        assertTrue(currentArr.hasNull());

    }

    @Test
    void addAllExpansionTest() {
        int capacity = 100;
        Integer[] expectedArr = new Integer[capacity];
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>();

        for (int i = 0; i < capacity; i++) {
            expectedArr[i] = RANDOM.nextInt(1000) - 500;
        }

        currentArr.addAll(expectedArr);

        assertEquals(expectedArr.length, currentArr.size());
        for (int i = 0; i < expectedArr.length; i++) {
            assertEquals(expectedArr[i], currentArr.get(i));
        }

    }

    //toArray
    @Test
    void toArrayTest() {
        int capacity = 100;
        Integer[] expectedArr = new Integer[capacity];
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            Integer val = RANDOM.nextInt(1000) - 500;
            currentArr.add(val);
            expectedArr[i] = val;
        }

        assertArrayEquals(expectedArr, currentArr.toArray());
    }

    //toArray with external Array
    @Test
    void toArrayParamTest() {
        int capacity = 100;
        Integer[] expectedArr = new Integer[capacity];
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            Integer val = RANDOM.nextInt(1000) - 500;
            currentArr.add(val);
            expectedArr[i] = val;
        }

        Integer[] resultArr = currentArr.toArray(new Integer[0]);

        assertArrayEquals(expectedArr, resultArr);
    }

    @Test
    void toArrayNullTest() {
        int capacity = 100;
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            currentArr.add(RANDOM.nextInt(1000) - 500);
        }

        Assertions.assertThrows(NullParamException.class, () -> currentArr.toArray(null));
    }

    //is Empty
    @Test
    void isEmptyTest() {
        SpecialArrayList<Object> currentArr = new SpecialArrayList<>();
        assertTrue(currentArr.isEmpty());

        currentArr.add(new Object());
        assertFalse(currentArr.isEmpty());

        currentArr.clean();
        assertTrue(currentArr.isEmpty());

    }

    @Test
    void hasNullTest() {
        SpecialArrayList<Object> currentArr = new SpecialArrayList<>();
        assertFalse(currentArr.hasNull());

        currentArr.add(null);
        assertTrue(currentArr.hasNull());

        currentArr.clean();
        assertFalse(currentArr.hasNull());

    }

    //
    @Test
    void equalsTest() {
        SpecialArrayList<Integer> arr1 = new SpecialArrayList<>();
        SpecialArrayList<Integer> arr2 = new SpecialArrayList<>();

        for (int i = 0; i < 100; i++) {
            int val = RANDOM.nextInt(100000) - 50000;
            arr1.add(val);
            arr2.add(val);
        }

        assertEquals(arr1, arr2);
    }

    @Test
    void hashCodeTest() {
        SpecialArrayList<Integer> arr1 = new SpecialArrayList<>();
        SpecialArrayList<Integer> arr2 = new SpecialArrayList<>();

        for (int i = 0; i < 100; i++) {
            int val = RANDOM.nextInt(100000) - 50000;
            arr1.add(val);
            arr2.add(val);
        }

        assertEquals(arr1.hashCode(), arr2.hashCode());
    }

    @Test
    void integerTest() {
        int capacity = 1_000_000;
        ArrayList<Integer> expectedArr = new ArrayList<>(capacity);
        SpecialArrayList<Integer> currentArr = new SpecialArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            Integer val = RANDOM.nextInt(1000) - 500;
            expectedArr.add(val);
            currentArr.add(val);
        }

        expectedArr.sort(Comparator.comparingInt(x -> x));
        currentArr.sort(Comparator.comparingInt(x -> x));

        assertArrayEquals(expectedArr.toArray(), currentArr.toArray());
    }
}