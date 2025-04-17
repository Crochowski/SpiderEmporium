package com.example.spideremporium.StackHeapTest;

public class StackTest {
    private int finalCount;

    public void testStackOverflow() {
        long start = System.currentTimeMillis();
        try {
            stackOverflow(0);
        }
        catch (StackOverflowError e) {
            long end = System.currentTimeMillis();
            System.out.println(finalCount + " levels of recursion!");
            System.out.println("Stack overflow occurs after " + (end-start) + "ms ("
            + (end-start)/1000 + " seconds)");
        }
}

    private void stackOverflow(int count) {
    finalCount = count;
    stackOverflow(count+1);
}



    public static void main(String[] args) {
        StackTest stackTest = new StackTest();
        stackTest.testStackOverflow();
    }
}
