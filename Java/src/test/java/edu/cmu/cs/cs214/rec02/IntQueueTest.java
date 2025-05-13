package edu.cmu.cs.cs214.rec02;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntQueueTest {
    private static final Logger logger = LogManager.getLogger(IntQueueTest.class);
    private IntQueue mQueue;

    @Before
    public void setUp() {
        logger.info("Setting up test");
        mQueue = new ArrayIntQueue();
    }

    @Test
    public void testIsEmpty() {
        logger.debug("Running testIsEmpty");
        assertTrue(mQueue.isEmpty());
        mQueue.enqueue(1);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testEnqueueDequeue() {
        logger.debug("Running testEnqueueDequeue");
        int testValue = 42;
        mQueue.enqueue(testValue);
        assertEquals(Integer.valueOf(testValue), mQueue.dequeue());
        logger.info("Successfully enqueued and dequeued value: {}", testValue);
    }

    @Test
    public void testPeekEmptyQueue() {
        logger.debug("Running testPeekEmptyQueue");
        assertNull(mQueue.peek());
    }

    @Test
    public void testDequeueEmptyQueue() {
        logger.debug("Running testDequeueEmptyQueue");
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testResize() {
        logger.info("Testing queue resize behavior");
        int initialCapacity = 10;
        for (int i = 0; i < initialCapacity * 2; i++) {
            mQueue.enqueue(i);
        }
        assertEquals(initialCapacity * 2, m