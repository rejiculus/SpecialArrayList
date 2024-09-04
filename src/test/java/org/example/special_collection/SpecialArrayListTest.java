package org.example.special_collection;

import org.example.special_collection.exception.CapacityException;
import org.example.special_collection.exception.ExpansionCoefficientException;
import org.example.special_collection.exception.NullParamException;
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

        Assertions.assertThrows(NullParamException.class, ()-> arr.add(null));
    }

    //add by index
//    public void addByIndexTest();
//    public void addByIndexNullTest();
//    public void addByIndexOutOfBoundTest();

    //get
//    public void getTest();
//    public void getOutOfBoundTest();

    //remove
//    public void removeFirstTest();
//    public void removeLastTest();
//    public void removeMiddleTest();
//    public void removeOutOfBoundTest();

    //clean
//    public void cleanTest();

    //sort comparable
//    public void sortComparableTest();
//    public void sortNotComparableTest();

    //sort comparator
//    public void sortComparatorTest();
//    public void sortComparatorNullTest();

    //replace
//    public void replaceTest();
//    public void replaceNullTest();
//    public void replaceOutOfBoundTest();

    //getSize
//    public void getSize();

    //others
    //addAll
//    public void addAllTest();
//    public void addAllNullTest();
//    public void addAllEmptyTest();

    //trim
//    public void trimTest();

    //toArray
//    public void toArrayTest();

    //toArray with external Array
//    public void toArrayParamTest();
//    public void toArrayNullTest();

    //is Empty
//    public void isEmptyTest();





    private Object[] getInsideArr(SpecialArrayList<?> arr) throws NoSuchFieldException, IllegalAccessException {
        Field arrField = arr.getClass().getDeclaredField("arr");
        arrField.setAccessible(true);
        return (Object[]) arrField.get(arr);
    }
    private Double getInsideExpansionCoefficient(SpecialArrayList<?> arr) throws NoSuchFieldException, IllegalAccessException {
        Field arrField = arr.getClass().getDeclaredField("expansionCoeff");
        arrField.setAccessible(true);
        return (Double) arrField.get(arr);
    }
}