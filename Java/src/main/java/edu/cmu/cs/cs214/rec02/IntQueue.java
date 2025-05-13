package edu.cmu.cs.cs214.rec02;

/**
 * Interface for a first-in, first-out structure for integers.
 */
public interface IntQueue {
    void clear();
    Integer dequeue();
    boolean enqueue(Integer value);
    boolean isEmpty();
    Integer peek();
    int size();
}