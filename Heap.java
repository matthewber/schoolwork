/**
* Name: Matthew Berntsen
* ID: v00884371
* Date: Apr 7, 2018
* Filename: Heap.java
* Details: CSC 115 Assignment 5
*/

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

// This is the beginning of the source code to help you get started.
// Do not change the following:

public class Heap<E extends Comparable<E>> {

	private ArrayList<E> heap;
	private int size;
	private static final int CAPACITY = 100;

	/**
	 * Creates an empty heap.
	 */
	public Heap() {
		heap = new ArrayList<E>(CAPACITY);
		for (int i=0; i<CAPACITY; i++) {
			heap.add(null);
		}
	}

	public E lookAtRootItem(){
		E rootItem = heap.get(0);
		return rootItem;
	}

	/**
	* getRootItem() removes and returns element at root
	* @return E item in root node
	*/
	public E getRootItem() throws NoSuchElementException{
		if(isEmpty()){
			return null;
		}
		E rootItem = heap.get(0);
		int rightLeafIndex = size()-1;
		E newItem = heap.get(rightLeafIndex);
		heap.set(rightLeafIndex, null);
		heap.set(0, newItem);
		bubbleDown(0);
		return rootItem;
	}

	/**
	* helper method for getRootItem()
	* bubbles down assuming that the root node has been replaced with the right most leaf
	* @param int index that allows for recursive calling of the bubbleDown method
	*/
	public void bubbleDown(int index){
		E leftChild = heap.get((index*2)+1);
		E rightChild = heap.get((index*2)+2);
		E compareChild;
		int childIndex;
		if(leftChild == null && rightChild == null){
			return;
		}else if(leftChild == null){
			compareChild = rightChild;
			childIndex = (index*2)+2;
		}else if(rightChild == null){
			compareChild = leftChild;
			childIndex = (index*2)+1;
		}else if(leftChild.compareTo(rightChild) < 0){
			compareChild = leftChild;
			childIndex = (index*2)+1;
		}else{
			compareChild = rightChild;
			childIndex = (index*2)+2;
		}
		E parent = heap.get(index);
		if(parent.compareTo(compareChild) > 0){
			heap.set(childIndex, parent);
			heap.set(index, compareChild);
			bubbleDown(childIndex);
		}
	}

	/**
	* inserts the element into the Heap
	* @param E element to be inserted
	*/
	public void insert(E element){
 		//first insert into the rightmost leaf, maintaining complete tree status insertionindex = size()
 		heap.set(size(), element);
		// bubble up if this inserted node is not in place use element.compareTo method assuming E has an implemented compareTo method
		try{
			bubbleUp(size()-1);
		}catch(IndexOutOfBoundsException ioobe){
		}
	}

	/**
	* helper method for insert()
	* redorders the data to maintain heap status.
	* @param index of the node to be bubbled up
	*/
	public void bubbleUp(int index){
		if(size() == 1){
			return;
		}
		int parentIndex = ((index/2)-1);
		E child = heap.get(index);
		E parent = heap.get(parentIndex);
		if(child.compareTo(parent) < 0){
			heap.set(index, parent);
			heap.set(parentIndex, child);
			bubbleUp(parentIndex);
		}
	}

	/**
	* checks if the heap is empty
	*/
	public boolean isEmpty(){
		if(size() > 0){
			return false;
		}else{
			return true;
		}
	}

	/**
	* returns the number of elements in the heap
	*/
	public int size(){
		int count = 0;
		for(int i = 0; i < CAPACITY; i++){
			if(heap.get(i) != null){
				count++;
			}
		}
		return count;
	}

	/**
	*used for testing purposes only
	*/
	public static void main(String[] args){
		Heap<Integer> testHeap = new Heap<Integer>();
		boolean testEmpty = testHeap.isEmpty();
		System.out.println(testEmpty);
		int testSize = testHeap.size();
		System.out.println(testSize);
		Integer intTest = testHeap.heap.get(0);
		Integer newInt = new Integer(3);
		testHeap.insert(newInt);
		testSize = testHeap.size();
		System.out.println(testSize);
		System.out.println(testHeap.getRootItem());
		testSize = testHeap.size();
		System.out.println(testSize);
	}
}
