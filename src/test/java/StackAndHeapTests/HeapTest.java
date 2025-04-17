package StackAndHeapTests;

import org.junit.Test;

import java.util.ArrayList;


public class HeapTest {
    private int finalCount = 0;
    private long start;
    private ArrayList<long[]> objectList;


    //@Test
//    public void stackTest() {
//        try {
//            start = System.currentTimeMillis();
//            stackOverflow();
//        }
//        catch (StackOverflowError e) {
//            long end = System.currentTimeMillis();
//            System.out.println(finalCount + " levels of recursion!");
//            System.out.println("Memory Exception thrown after " + (end-start) + "ms ("
//            + (end-start)/1000 + " seconds)");
//        }
//    }

    @Test
    public void heapTest() {
        int count = 0;          // counts the objects
        objectList = new ArrayList<>();
        start = System.currentTimeMillis();
        try {
            while (true) {
                objectList.add(new long[100000]);
                count++;
                if (count % 100 == 0) {
                    System.out.println("100 objects created @ " + System.currentTimeMillis());
                }
            }
        }
        finally{
            long end = System.currentTimeMillis();
            System.out.println(count + " objects created!");
            System.out.println("Memory Exception thrown after " + (end-start) + "ms ("
            + (end-start)/1000 + " seconds");
        }
    }

//    private void stackOverflow(int count) {
//        finalCount = count;
//        stackOverflow(count+1);
//    }
//
//    private void stackOverflow() {
//        stackOverflow(0);
//    }

}
