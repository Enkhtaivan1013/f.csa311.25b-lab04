package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    @Before
    public void setUp() {
        // Switch between implementations for testing
        // mQueue = new LinkedIntQueue();
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(mQueue.isEmpty());
        mQueue.enqueue(1);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNonEmptyQueue() {
        mQueue.enqueue(5);
        assertEquals(Integer.valueOf(5), mQueue.peek());
        assertEquals(1, mQueue.size()); // Size shouldn't change
    }

    @Test
    public void testEnqueue() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        assertNull(mQueue.dequeue()); // Empty queue
        
        mQueue.enqueue(10);
        mQueue.enqueue(20);
        
        assertEquals(Integer.valueOf(10), mQueue.dequeue());
        assertEquals(1, mQueue.size());
        
        assertEquals(Integer.valueOf(20), mQueue.dequeue());
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testClear() {
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.clear();
        
        assertTrue(mQueue.isEmpty());
        assertEquals(0, mQueue.size());
        assertNull(mQueue.peek());
    }

    @Test
    public void testContent() throws IOException {
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(result, mQueue.dequeue());
            }
        }
    }

    @Test
    public void testResize() {
        // Test automatic resizing
        for (int i = 0; i < 20; i++) {
            mQueue.enqueue(i);
        }
        
        assertEquals(20, mQueue.size());
        for (int i = 0; i < 20; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testCircularBehavior() {
        // Fill the queue partially
        for (int i = 0; i < 5; i++) {
            mQueue.enqueue(i);
        }
        
        // Remove some elements to create space at beginning
        assertEquals(Integer.valueOf(0), mQueue.dequeue());
        assertEquals(Integer.valueOf(1), mQueue.dequeue());
        
        // Add more elements to test circular behavior
        mQueue.enqueue(5);
        mQueue.enqueue(6);
        
        assertEquals(5, mQueue.size());
        
        // Verify all elements
        assertEquals(Integer.valueOf(2), mQueue.dequeue());
        assertEquals(Integer.valueOf(3), mQueue.dequeue());
        assertEquals(Integer.valueOf(4), mQueue.dequeue());
        assertEquals(Integer.valueOf(5), mQueue.dequeue());
        assertEquals(Integer.valueOf(6), mQueue.dequeue());
        
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testMultipleOperations() {
        // Test mixed operations
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        assertEquals(Integer.valueOf(1), mQueue.dequeue());
        
        mQueue.enqueue(3);
        mQueue.enqueue(4);
        assertEquals(Integer.valueOf(2), mQueue.dequeue());
        
        mQueue.enqueue(5);
        assertEquals(Integer.valueOf(3), mQueue.peek());
        assertEquals(3, mQueue.size());
    }
}