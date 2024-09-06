package org.example.special_collection;

import org.example.special_collection.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.smartcardio.CardException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SpecialArrayListTest {
    @Test
    public void IntegerTest(){
        int capacity = 1000;
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(capacity);
        Random random = new Random();
        ArrayList<Integer> exp = new ArrayList<>(capacity);

        for(int i=0;i<capacity;i++){
            Integer val = random.nextInt(1000)-500;
            arr.add(val);
            exp.add(val);
        }
        arr.sort((x,y)->x-y);
        exp.sort((x,y)->x-y);

        Object[] intArr= arr.toArray();
        Object[] intExp= exp.toArray();

        assertArrayEquals(arr.toArray(),exp.toArray());
    }

    //constructors
    @Test
    public void creteEmptyListTest() throws NoSuchFieldException, IllegalAccessException {
        SpecialArrayList<Integer> arr = new SpecialArrayList<>();
        Object[] insideArr = getInsideArr(arr);

        assertEquals(10, insideArr.length);
        assertArrayEquals(new Integer[10], insideArr);
    }
    @Test
    public void createEmptyListWithCapacityTest() throws NoSuchFieldException, IllegalAccessException {
        int capacity = 42;
        SpecialArrayList<Double> arr = new SpecialArrayList<>(capacity);
        Object[] insideArr = getInsideArr(arr);

        assertEquals(capacity, insideArr.length);
        assertArrayEquals(new Double[capacity], insideArr);
    }
    @Test
    public void createEmptyListWithCapacityWrongTest(){
        int capacity = -1;
        Assertions.assertThrows(CapacityException.class, ()-> new SpecialArrayList<>(capacity));
    }
    @Test
    public void createEmptyListCapacityCoeffTest() throws NoSuchFieldException, IllegalAccessException {
        int capacity = 42;
        Double coefficient = 2.0;
        SpecialArrayList<Double> arr = new SpecialArrayList<>(capacity,coefficient);

        Object[] insideArr = getInsideArr(arr);
        Double actualCoefficient = getInsideExpansionCoefficient(arr);

        assertEquals(capacity, insideArr.length);
        assertArrayEquals(new Double[capacity], insideArr);
        assertEquals(coefficient, actualCoefficient);
    }
    @Test
    public void createEmptyListCapacityCoeffWrongTest(){
        Assertions.assertThrows(CapacityException.class, ()-> new SpecialArrayList<>(-1, 2.0));
        Assertions.assertThrows(ExpansionCoefficientException.class, ()-> new SpecialArrayList<>(5, 1.0));
        Assertions.assertThrows(ExpansionCoefficientException.class, ()-> new SpecialArrayList<>(5, 2.1));//because highest coeff const is 2.0
    }
    @Test
    public void createListByArrayTest() throws NoSuchFieldException, IllegalAccessException {
        String[] exp = new String[]{"texture", "treatment", "jail", "zip", "zinc", "enthusiastic", "argue", "five", "arithmetic", "didactic", "terrify", "pet"};

        SpecialArrayList<String> arr = new SpecialArrayList<>(exp);

        Object[] innerArr = getInsideArr(arr);

        assertArrayEquals(exp, innerArr);
    }
    @Test
    public void createListByCollectionTest() throws NoSuchFieldException, IllegalAccessException {
        ArrayList<String> exp = new ArrayList<>(Arrays.asList("texture", "treatment", "jail", "zip", "zinc", "enthusiastic", "argue", "five", "arithmetic", "didactic", "terrify", "pet"));

        SpecialArrayList<String> arr = new SpecialArrayList<>(exp);

        Object[] innerArr = getInsideArr(arr);
        assertArrayEquals(exp.toArray(), innerArr);
    }

    //add
    @Test
    public void addTest() throws NoSuchFieldException, IllegalAccessException {
        SpecialArrayList<String> arr = new SpecialArrayList<>();

        arr.add("WOW");

        Object[] innerArr = getInsideArr(arr);
        assertEquals("WOW", innerArr[0]);
    }
    @Test
    public void addExpansionTest() throws NoSuchFieldException, IllegalAccessException {
        ArrayList<String> exp = new ArrayList<>(Arrays.asList("texture", "treatment", "jail", "zip", "zinc", "enthusiastic", "argue", "five", "arithmetic", "didactic", "terrify", "pet"));
        SpecialArrayList<String> arr = new SpecialArrayList<>(exp);

        arr.add("WOW");
        exp.add("WOW");

        Object[] innerArr = getInsideArr(arr);
        for(int i=0;i<exp.size();i++){
            assertEquals(exp.get(i),innerArr[i]);
        }
        assertEquals(12*3/2+1, innerArr.length);
    }
    @Test
    public void addNullTest(){
        SpecialArrayList<String> arr = new SpecialArrayList<>();

        Assertions.assertThrows(NullParamException.class, ()-> arr.add((String) null));
    }

    //add by index
    @Test
    public void addByIndexLTest() throws NoSuchFieldException, IllegalAccessException {
        Integer[] inp = new Integer[]{1,2,3,4,5,6,7};
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(inp);

        arr.add(0,42);

        Object[] insideArr = getInsideArr(arr);
        Integer[] exp = new Integer[]{42,1,2,3,4,5,6,7};
        for(int i=0;i<exp.length;i++){
            assertEquals(exp[i],insideArr[i]);
        }
    }
    @Test
    public void addByIndexMTest() throws NoSuchFieldException, IllegalAccessException {
        Integer[] inp = new Integer[]{1,2,3,4,5,6,7};
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(inp);

        arr.add(4,42);

        Object[] insideArr = getInsideArr(arr);
        Integer[] exp = new Integer[]{1,2,3,4,42,5,6,7};
        for(int i=0;i<exp.length;i++){
            assertEquals(exp[i],insideArr[i]);
        }
    }
    @Test
    public void addByIndexRTest() throws NoSuchFieldException, IllegalAccessException {
        Integer[] inp = new Integer[]{1,2,3,4,5,6,7};
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(inp);

        arr.add(inp.length, 42);

        Object[] insideArr = getInsideArr(arr);
        Integer[] exp = new Integer[]{1,2,3,4,5,6,7,42};
        for(int i=0;i<exp.length;i++){
            assertEquals(exp[i],insideArr[i]);
        }
    }
    @Test
    public void addByIndexNullTest() throws NoSuchFieldException, IllegalAccessException {
        Integer[] inp = new Integer[]{1,2,3,4,5,6,7};
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(inp);

        Assertions.assertThrows(NullParamException.class, ()-> arr.add(4,null));
    }
    @Test
    public void addByIndexOutOfBoundTest(){
        Integer[] inp = new Integer[]{1,2,3,4,5,6,7};
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(inp);

        for(int i=-10;i<10;i++){
            if(i<0 || i>inp.length) {
                int finalI = i;
                Assertions.assertThrows(IndexOutOfRangeException.class, ()-> arr.add(finalI,99));
            }
        }
    }

    //get
    @Test
    public void getTest(){
        Integer[] inp = new Integer[]{1,2,3,4,5,6,7,8,9,0};
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(inp);

        for(int i=0;i<10;i++){
            assertEquals(inp[i], arr.get(i));
        }
    }
    @Test
    public void getOutOfBoundTest(){
        Integer[] inp = new Integer[]{1,2,3,4,5,6,7,8,9,0};
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(inp);

        for(int i=-10;i<15;i++){
            if(i<0 || i>=inp.length){
                int index=i;
                Assertions.assertThrows(IndexOutOfRangeException.class, ()-> arr.get(index));
            }
        }
    }

    //remove
    @Test
    public void removeFirstTest() throws NoSuchFieldException, IllegalAccessException {
        Integer[] inp = new Integer[]{1,2,3,4,5,6,7};
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(inp);

        arr.remove(0);

        Object[] insideArr = getInsideArr(arr);
        Integer[] exp = new Integer[]{2,3,4,5,6,7};
        for(int i=0;i<exp.length;i++){
            assertEquals(exp[i],insideArr[i]);
        }
    }
    @Test
    public void removeLastTest() throws NoSuchFieldException, IllegalAccessException {
        Integer[] inp = new Integer[]{1,2,3,4,5,6,7};
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(inp);

        arr.remove(inp.length-1);

        Object[] insideArr = getInsideArr(arr);
        Integer[] exp = new Integer[]{1,2,3,4,5,6};
        for(int i=0;i<exp.length;i++){
            assertEquals(exp[i],insideArr[i]);
        }
    }
    @Test
    public void removeMiddleTest() throws NoSuchFieldException, IllegalAccessException {
        Integer[] inp = new Integer[]{1,2,3,4,5,6,7};
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(inp);

        arr.remove(2);

        Object[] insideArr = getInsideArr(arr);
        Integer[] exp = new Integer[]{1,2,4,5,6,7};
        for(int i=0;i<exp.length;i++){
            assertEquals(exp[i],insideArr[i]);
        }
    }
    @Test
    public void removeOutOfBoundTest(){
        Integer[] inp = new Integer[]{1,2,3,4,5,6,7,8,9,0};
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(inp);

        for(int i=-10;i<15;i++){
            if(i<0 || i>=inp.length){
                int index=i;
                Assertions.assertThrows(IndexOutOfRangeException.class, ()-> arr.remove(index));
            }
        }

    }

    //clean
    @Test
    public void cleanTest() throws NoSuchFieldException, IllegalAccessException {
        Integer[] inp = new Integer[]{1,2,3,4,5,6,7,8,9,0};
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(inp);

        arr.clean();

        Object[] insideArr = getInsideArr(arr);
        assertEquals(inp.length, insideArr.length);
        assertArrayEquals(new Integer[inp.length], insideArr);
    }

    //sort comparable
    @Test
    public void sortComparableTest(){
        Random random = new Random();
        int capacity = 100;
        ArrayList<Integer> exp = new ArrayList<>(capacity);
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(capacity);

        for(int i=0;i<capacity;i++){
            int val =random.nextInt(1000)-500;
            arr.add(val);
            exp.add(val);
        }
        arr.sort();
        exp.sort((x,y)->x-y);

        for(int i=0;i<capacity;i++){
            assertEquals(exp.get(i), arr.get(i));
        }
    }
    @Test
    public void sortNotComparableTest(){
        class TestObj{}
        SpecialArrayList<TestObj> arr = new SpecialArrayList<>(10);
        for(int i=0;i<10;i++){
            arr.add(new TestObj());
        }

        Assertions.assertThrows(NotComparableException.class, arr::sort);
    }

    //sort comparator
    @Test
    public void sortComparatorTest(){
        Random random = new Random();
        int capacity = 100;
        ArrayList<Integer> exp = new ArrayList<>(capacity);
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(capacity);

        for(int i=0;i<capacity;i++){
            int val =random.nextInt(1000)-500;
            arr.add(val);
            exp.add(val);
        }
        arr.sort((x,y)->x-y);
        exp.sort((x,y)->x-y);

        for(int i=0;i<capacity;i++){
            assertEquals(exp.get(i), arr.get(i));
        }
    }
    @Test
    public void sortComparatorNullTest(){
        Random random = new Random();
        int capacity = 100;
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(capacity);

        for(int i=0;i<capacity;i++){
            int val =random.nextInt(1000)-500;
            arr.add(val);
        }
        Assertions.assertThrows(NullParamException.class, ()-> arr.sort(null));

    }

    //replace
    @Test
    public void replaceTest(){
        Random random = new Random();
        int capacity = 100;
        ArrayList<Integer> exp = new ArrayList<>(capacity);
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(capacity);

        for(int i=0;i<capacity;i++){
            int val =random.nextInt(1000)-500;
            arr.add(val);
            exp.add(val);
        }

        arr.replace(0,42);
        exp.set(0,42);

        assertArrayEquals(arr.toArray(new Integer[0]), exp.toArray(new Integer[0]));
    }
    @Test
    public void replaceNullTest(){
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(100);

        arr.addAll(new Integer[]{1,2,3});

        Assertions.assertThrows(NullParamException.class, ()-> arr.replace(1, null));

    }
    @Test
    public void replaceOutOfBoundTest(){
        Random random = new Random();
        int capacity = 100;
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(capacity);

        for(int i=0;i<capacity;i++){
            arr.add(random.nextInt(1000));
        }

        for(int i=-10;i<capacity+10;i++){
            if(i<0 || i>=capacity){
                int val = i;
                Assertions.assertThrows(IndexOutOfRangeException.class, ()-> arr.replace(val, 42));
            } else {
                arr.replace(i, 42);
            }
        }

        for(int i=0;i<capacity;i++){
            assertEquals(42, arr.get(i));
        }
    }

    //getSize
    @Test
    public void getSize(){
        Random random = new Random();
        int capacity = 100;
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(capacity);

        assertEquals(0, arr.getSize());

        for(int i=0;i<capacity;i++){
            arr.add(random.nextInt(1000));
        }
        assertEquals(capacity,arr.getSize());
        arr.remove(42);
        assertEquals(capacity-1, arr.getSize());
    }

    //others

    //addAll
    @Test
    public void addAllTest(){
        Random random = new Random();
        int capacity = 100;
        Integer[] exp = new Integer[capacity/2];
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(capacity);

        for(int i=0;i<capacity/2;i++){
            exp[i] = random.nextInt(1000)-500;
        }

        arr.addAll(exp);


        assertEquals(exp.length, arr.getSize());
        for(int i=0;i<exp.length;i++){
            assertEquals(exp[i], arr.get(i));
        }
    }
    @Test
    public void addAllNullTest(){
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(100);

        Assertions.assertThrows(NullParamException.class, ()-> arr.addAll(null));
    }
    @Test
    public void addAllEmptyTest(){
        int capacity = 100;
        Integer[] exp = new Integer[capacity/2];
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(capacity);

        arr.addAll(exp);

        assertTrue(arr.isEmpty());

    }
    @Test
    public void addAllExpansionTest(){Random random = new Random();
        int capacity = 100;
        Integer[] exp = new Integer[capacity];
        SpecialArrayList<Integer> arr = new SpecialArrayList<>();

        for(int i=0;i<capacity;i++){
            exp[i] = random.nextInt(1000)-500;
        }

        arr.addAll(exp);


        assertEquals(exp.length, arr.getSize());
        for(int i=0;i<exp.length;i++){
            assertEquals(exp[i], arr.get(i));
        }

    }


    //trim
    @Test
    public void trimTest() throws NoSuchFieldException, IllegalAccessException {
        Random random = new Random();
        int capacity = 100;
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(capacity);
        for(int i =0;i<capacity/2;i++){
            arr.add(random.nextInt(1000)-500);
        }

        Object[] insideArr = getInsideArr(arr);

        assertEquals(capacity, insideArr.length);
        assertEquals(capacity/2, arr.getSize());

        arr.trim();

        insideArr = getInsideArr(arr);

        assertEquals(capacity/2, arr.getSize());
        assertEquals(arr.getSize(), insideArr.length);
    }

    //toArray
    @Test
    public void toArrayTest(){
        Random random = new Random();
        int capacity = 100;
        Integer[] exp = new Integer[capacity];
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(capacity);

        for(int i =0;i<capacity;i++){
            Integer val = random.nextInt(1000)-500;
            arr.add(val);
            exp[i] = val;
        }

        Object[] res = arr.toArray();

        assertArrayEquals(exp, res);
    }

    //toArray with external Array
    @Test
    public void toArrayParamTest(){
        Random random = new Random();
        int capacity = 100;
        Integer[] exp = new Integer[capacity];
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(capacity);

        for(int i =0;i<capacity;i++){
            Integer val = random.nextInt(1000)-500;
            arr.add(val);
            exp[i] = val;
        }

        Integer[] res = arr.toArray(new Integer[0]);

        assertArrayEquals(exp, res);
    }
    @Test
    public void toArrayNullTest(){
        Random random = new Random();
        int capacity = 100;
        Integer[] exp = new Integer[capacity];
        SpecialArrayList<Integer> arr = new SpecialArrayList<>(capacity);

        for(int i =0;i<capacity;i++){
            Integer val = random.nextInt(1000)-500;
            arr.add(val);
            exp[i] = val;
        }

        Assertions.assertThrows(NullParamException.class, ()-> arr.toArray(null));
    }

    //is Empty
    @Test
    public void isEmptyTest(){
        SpecialArrayList<Object> arr = new SpecialArrayList<>();
        assertTrue(arr.isEmpty());

        arr.add(new Object());
        assertFalse(arr.isEmpty());

        arr.clean();
        assertTrue(arr.isEmpty());

    }

    //
    @Test
    public void equalsTest(){
        Random random = new Random();
        SpecialArrayList<Integer> arr1 = new SpecialArrayList<>();
        SpecialArrayList<Integer> arr2 = new SpecialArrayList<>();

        for(int i=0;i<100;i++){
            int val = random.nextInt(100000)-50000;
            arr1.add(val);
            arr2.add(val);
        }

        assertEquals(arr1, arr2);
    }
    @Test
    public void hashCodeTest(){
        Random random = new Random();
        SpecialArrayList<Integer> arr1 = new SpecialArrayList<>();
        SpecialArrayList<Integer> arr2 = new SpecialArrayList<>();

        for(int i=0;i<100;i++){
            int val = random.nextInt(100000)-50000;
            arr1.add(val);
            arr2.add(val);
        }

        assertEquals(arr1.hashCode(), arr2.hashCode());
    }



    private Object[] getInsideArr(SpecialArrayList<?> arr) throws NoSuchFieldException, IllegalAccessException {
        Field arrField = arr.getClass().getDeclaredField("arr");
        arrField.setAccessible(true);
        return (Object[]) arrField.get(arr);
    }
    private Double getInsideExpansionCoefficient(SpecialArrayList<?> arr) throws NoSuchFieldException, IllegalAccessException {
        Field arrField = arr.getClass().getDeclaredField("expansionCoefficient");
        arrField.setAccessible(true);
        return (Double) arrField.get(arr);
    }
}