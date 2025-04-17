package com.example.spideremporium.StackHeapTest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HeapTest {
    private long start;
    private ArrayList<long[]> objectList;


    public void heapTest() {
        FileWriter writer = null;
        int count = 0;          // counts the objects
        objectList = new ArrayList<>();
        start = System.currentTimeMillis();
        long objCheckPointStart = start;
        System.out.println("Start time: " + start);

        try {
            writer = new FileWriter("src/main/java/com/example/spiderEmporium/StackHeapTest/heapResults.txt");
            writer.write("Test started @ " + start + "\n\n");
            writer.flush();
        }
        catch (IOException e) {
            System.out.println("Could not write to file: " + e.getMessage());
        }

        while (true) {
            objectList.add(new long[100000]);
            count++;

            System.out.println("Time: " + System.currentTimeMillis());
            System.out.println(count + " objects created!");

            if (count % 100 == 0) {
                long objCheckPoint = System.currentTimeMillis() - objCheckPointStart;

                System.out.println("\n*****");
                System.out.println("100 objects created @ " + System.currentTimeMillis());
                System.out.println("Time since last 100 = " + objCheckPoint + "ms");
                System.out.println("*****\n");

                try {
                    writer.write(count + " objects created @ " + System.currentTimeMillis());
                    writer.write("Time since last 100: " + objCheckPoint + "ms\n\n");
                    writer.flush();
                }
                catch (IOException e) {
                    System.out.println("Could not write to file: " + e.getMessage());
                }

                objCheckPointStart = System.currentTimeMillis();

            }
        }
    }



    public static void main(String[] args) {
        HeapTest test = new HeapTest();
        test.heapTest();
    }
}
