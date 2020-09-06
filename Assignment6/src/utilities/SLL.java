package utilities;

import java.io.Serializable;

public class SLL implements List, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SLLNode head;
	private int size;
	
	public SLL() {
		head = null;
		size = 0;
	}

	@Override
	public boolean append(Object element) {
		boolean status = false;
		
		
		
		if (!isEmpty()) {
			SLLNode last = head;
			
			while (last.getNext() != null)
				last = last.getNext();
			
			SLLNode insert = new SLLNode(element);
			last.setNext(insert);
			status = true;
			size++;
		}
		else {
			status = add (element);
		}
		
		return status;
	}

	@Override
	public boolean add(Object element) {
boolean status = false;
		
		if (!isEmpty()) {
			SLLNode insert = new SLLNode(element);
			SLLNode curr = head;
			head = insert;
			insert.setNext(curr);
			size++;
			status = true;
		}
		else {
			SLLNode insert = new SLLNode(element);
			head = insert;
			size++;
			status = true;
		}
		
		
		return status;
	}
	

	@Override
	public boolean add(Object element, int position) throws IndexOutOfBoundsException {
		boolean status = false;
		if ( position <= 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		SLLNode pre = head;
		
		for (int i = 0 ; i < position-1; i++) {
			pre = pre.getNext();
		}
		
		SLLNode insert = new SLLNode(element);
		
		insert.setNext(pre.getNext());
		pre.setNext(insert);
		size++;
		status = true;
		
		return status;
	}

	@Override
	public void clear() {
		head = null;
		size = 0;
	}

	@Override
	public Object remove() {
		Object o = null;
		
		if (!isEmpty()) {
			SLLNode curr = head;
			head = head.getNext();
			curr.setNext(null);
			size--;
			o = curr.getElement();
		}
		
		return o;
	}

	@Override
	public Object removeLast() {
		
		Object removed = null;
		
		if (!isEmpty()) {
			SLLNode curr = head;
			SLLNode last = curr;
			
			while (curr.getNext() != null) {
				last = curr;
				curr = curr.getNext();
			}
			removed = curr.getElement();
			
			last.setNext(null);

			size--;
		}
		
		return removed;
	}

	@Override
	public Object remove(int position) throws IndexOutOfBoundsException {
		if ( position <= 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		SLLNode pre = head;
		SLLNode jump = null;
		Object removed = null;
		
		for (int i = 0 ; i < position-1; i++) {
			pre = pre.getNext();
			
		}
		jump = pre.getNext();
		jump = jump.getNext();
		removed = pre.getNext().getElement();
		pre.setNext(jump);
		size--;
		return removed;
	}

	@Override
	public Object get() {
		
		return head.getElement();
	}

	@Override
	public Object getLast() {
		
		if (!isEmpty()) {
			SLLNode last = head;
			
			while (last.getNext() != null)
				last = last.getNext();
			
			return last.getElement();
		}
		else {
			return null;
		}
	
	}

	@Override
	public Object get(int position) throws IndexOutOfBoundsException {
		Object o = null;
		
		if ( position <= 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		SLLNode next = head;
		if (!isEmpty()) {
		for (int i = 0 ; i <= position; i++) {
			o = next.getElement();
			next = next.getNext();
		}
		}
		return o;
	}

	@Override
	public Object set(Object element, int position) throws IndexOutOfBoundsException {
		if ( position <= 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		Object yeet = get(position);
		
		remove(position);
		add(element, position);
		return yeet;
	}

	@Override
	public boolean contains(Object element) {
		
		boolean match = false;
		SLLNode curr = head;
		Object o = element;
		
		for (int i = 0; i < size; i++) {
			if (o == curr.getElement()) {
				match = true;	
			}
			curr = curr.getNext();
		}
		
		return match;
	}

	@Override
	public int indexOf(Object element) {
		int index = -1;
		SLLNode curr = head;
		Object o = element;
		
		for (int i = 0; i < size; i++) {
			if (o == curr.getElement()) {
				index = i;	
			}
			curr = curr.getNext();
		} 
		return index;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}
	
	public void print() {

        SLLNode cool = head;
        while(true) {
            if(cool == null) {
                break;
            }
            System.out.println(cool.getElement());
            cool = cool.getNext();
        }
	}
}
