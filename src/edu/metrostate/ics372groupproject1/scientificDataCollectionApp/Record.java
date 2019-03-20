package edu.metrostate.ics372groupproject1.scientificDataCollectionApp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * This class is a collection of study in the program 
 * It stores a study object along with the sites and 
 * the readings associated with the study
 */
public class Record implements List<Study>{
	List<Study> studyList = new ArrayList<Study>();
	
	@Override
	public boolean add(Study s) {
		return studyList.add(s);
	}

	@Override
	public void add(int arg0, Study arg1) {
		studyList.add(arg0, arg1);
	}

	@Override
	public boolean addAll(Collection<? extends Study> c) {
		return studyList.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Study> c) {
		return studyList.addAll(index, c);
	}

	@Override
	public void clear() {
		studyList.clear();
	}

	@Override
	public boolean contains(Object o) {
		return studyList.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return studyList.containsAll(c);
	}

	@Override
	public Study get(int index) {
		return studyList.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return studyList.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return studyList.isEmpty();
	}

	@Override
	public Iterator<Study> iterator() {
		/*
		 * Returns an iterator over the elements in this list
		 * in proper sequence
		 */
		return studyList.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		/*
		 * Returns the index of the last occurrence of the
		 * specified element in this list
		 */
		return studyList.lastIndexOf(o);
	}

	@Override
	public ListIterator<Study> listIterator() {
		/* Returns a list iterator over the elements in this
		 * list (in proper sequence).
		 */
		return studyList.listIterator();
	}

	@Override
	public ListIterator<Study> listIterator(int index) {
		//Return a list Iterator by an index  
		return studyList.listIterator(index);
	}

	@Override
	public boolean remove(Object o) {
		// Remove a study from the list by passing in a study object to be remove
		return studyList.remove(o);
	}

	@Override
	public Study remove(int index) {
		// Remove a study from the list by index
		return studyList.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// Removes from this list all of its elements that are contained
		// in the specified collection (optional operation)
		return studyList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// Retains only the elements in this list that are contained 
		// in the specified collection 
		return studyList.retainAll(c);
	}

	@Override
	public Study set(int index, Study element) {
		// Replaces the element at the specified position in this list
		// with the specified element (optional operation).
		return studyList.set(index, element);
	}

	@Override
	public int size() {
		// Returns the number of elements in this list.
		return studyList.size();
	}

	@Override
	public List<Study> subList(int fromIndex, int toIndex) {
		// Returns a view of the portion of this list between the specified
		//from Index, inclusive, and toIndex, exclusive.
		return studyList.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		// Returns an array containing all of the elements in this list in
		// proper sequence (from first to last element).
		return studyList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// Returns an array containing all of the elements in this list
		// in proper sequence (from first to last element); 
		return studyList.toArray(a);
	}
}
