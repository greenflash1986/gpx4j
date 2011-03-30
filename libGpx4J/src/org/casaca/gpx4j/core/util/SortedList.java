package org.casaca.gpx4j.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.casaca.gpx4j.core.data.CoordinatesObject;

public class SortedList<E extends CoordinatesObject> extends ArrayList<E> implements List<E>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7453463546587136086L;

	@Override
	public boolean add(E e) {
		if(this.size()==0)
			return this.add(e);
		else{
			int index = Collections.binarySearch(this, e);
			if(index<0)	
				this.add(-index-1, e);
			else
				this.add(index+1, e);
			
			return true;
		}
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException("This list does not support this method. All items in this list are sorted. Please use add(CoordinatesObject e");
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		Iterator<? extends E> i = c.iterator();
		while(i.hasNext()){
			this.add(i.next());
		}
		
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException("This list does not support this method. All items in this list are sorted.");
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException("This list does not support this method. All items in this list are sorted.");
	}
}