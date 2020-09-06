package utilities;

import java.io.Serializable;

public class SLLNode implements List, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object element;
	private  SLLNode next;
	
	public SLLNode(Object element, SLLNode next) {
		this.element = element;
		this.next = next;
	}
	
	public SLLNode(Object element) {
		this.element = element;
		this.next = null;
	}

	public Object getElement() {
		return element;
	}

	public void setElement(Object element) {
		this.element = element;
	}

	@Override
	public boolean append(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean add(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean add(Object element, int position) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object removeLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object remove(int position) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(int position) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object set(Object element, int position) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int indexOf(Object element) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public SLLNode getNext() {
		return next;
	}

	public void setNext(SLLNode next) {
		this.next = next;
	}

}
