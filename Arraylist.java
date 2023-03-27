package project;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Arraylist<E> {
	private int defaultCapacity =10;
     private Object[] elementData;
     private Object[] emptyElement= {};
     private int size;
     
     public Arraylist() {
    	 elementData=emptyElement;
     }
     
     public Arraylist(Collection<E> array) {
    	 Object[] d=array.toArray();
    	 if((size = d.length) != 0) {
    		 if(array.getClass() == Arraylist.class) {
    			 elementData = d;
    		 }
    	 }
    	 else {
    		 elementData=emptyElement;
    	 }
     }
     
     public Arraylist(int minCapacity) {
    	 if(minCapacity >0) {
    		 elementData=new Object[minCapacity];
    	 }
    	 else if(minCapacity == 0) {
    		 elementData=emptyElement;
    	 }
    	 else {
    		 throw new IllegalArgumentException("illegal quantity : "+minCapacity);
    	 }
    	 
     }
     
     public boolean add(E e) {
    	 add(e,elementData,size);
    	 return true;
     }
     
     public void add(E e,Object[]ele,int size) {
    	 if(size == ele.length) {
    		 elementData= grow(size+1);
    		 elementData[size]=e;
    		 this.size=size+1;
    	 }
     }
     
     public Object[] grow(int size) {
    	 int oldCapacity=elementData.length;
    	 if(oldCapacity > 0 ) {
    		 int newCapacity=size;
    		 elementData=Arrays.copyOf(elementData, newCapacity);
    	 }
    	 else {
    		 elementData=new Object[size];
    	 }
    	 return elementData;
     }
     
     public String toString() {
    	 StringBuilder buf=new StringBuilder("[");
    	 for(Object o: elementData) {
    		 buf.append(o+",");
    	 }
    	 return buf.substring(0,buf.length()-1)+"]";
     }

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return elementData.length == 0;
	}

	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	public int indexOf(Object o) {
		
		return indexOfRange(o,0,size);
	}
    
	
	public int indexOf(Object o,int start) {
		return indexOfRange(o,start,size);
	}
	private int indexOfRange(Object o, int start, int end) {
		Object[] es=elementData;
		if(o == null) {
			for(int i=start;i<end;i++) {
				if(es[i] == null) {
					return i;
				}
			}
		}
		else {
			for(int i=start;i<end;i++) {
				if(es[i].equals(o)) {
					return i;
				}
			}
		}
		
		return -1;
	}
	

	public Iterator<E> iterator() {
		
		return new Itr();
	}

	public Object[] toArray() {
		return Arrays.copyOf(elementData, size);
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		if(a.length < size) {
			return (T[]) Arrays.copyOf(elementData, size,a.getClass());
		}
		System.arraycopy(elementData,0,a,0,size);
		if(a.length > size) {
			a[size]=null;
		}
		return a;
	}

	public boolean remove(Object o) {
		
		Object[]es=elementData;
		int size=this.size;
		int i=0;
		mm:{
			if(o == null) {
				for(;i<size;i++){
					if(es[i] == null) {
						break mm;
					}
				}
			}
			else {
					for(;i<size;i++){
						if(es[i].equals(o)) {
							break mm;
						}
					}
					return false;
			}
		}
		fastRemove(es,i);
		return true;
		
	}
    
	public E remove(int index) {
		Object[] es=elementData;
		E oldValue=(E) es[index];
		fastRemove(es,index);
		return oldValue;
	}
	private void fastRemove(Object[] es, int i) {
		final int newSize;
		if((newSize = size-1) > i) {
			System.arraycopy(elementData, i+1, es, i, newSize-1);
		}
		es[size = newSize] = null;
	}

	
	public boolean addAll(Collection<? extends E> c) {
		Object[] d=c.toArray();
		elementData=d;
		size= d.length;
		return true;
	}

	

	public void clear() {
		Object[] es=elementData;
		for(int i=0;i<elementData.length;i++) {
			es[i]=null;
		}
		size=0;
		
		
	}

	public int lastIndexOf(Object o) {
		return lastIndexOfRange(o,0,size);
	}

	private int lastIndexOfRange(Object o, int start, int end) {
		Object[]es=elementData;
		if(o == null) {
			for(int i=end-1;i>=start;i--) {
				if(es[i] == null) {
					return i;
				}
			}
		}
		else {
			for(int i=end-1;i>=start;i--) {
				if(es[i].equals(o)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public int lastIndexOf(Object o, int start) {
		return lastIndexOfRange(o,0,start);
	}
	
	int checkindex(Object o) {
		for(int i=0;i<elementData.length;i++) {
			if(elementData[i].equals(o)) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean equals(Object o) {
		return this.equals(o);
	}
	
	class Itr implements Iterator<E>{
      int cursor;
       int lastRet=-1;
		@Override
		public boolean hasNext() {
			return cursor != size;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			int i=cursor;
			if(i>=size) {
				throw new NoSuchElementException();
			}
			Object[]ele=Arraylist.this.elementData;
			
			if(i>=ele.length) {
				throw new ConcurrentModificationException();
			}
			cursor=i+1;
			return (E) ele[lastRet = i];
		}
		public void remove() {
			if(lastRet < 0) {
				throw new IllegalArgumentException();
			}
			else {
				Arraylist.this.remove(lastRet);
				try {
				cursor=lastRet;
				lastRet =-1;
				}
				catch(IndexOutOfBoundsException arr) {
					throw new ConcurrentModificationException();
				}
			}
		}
	}

	
	
	
}
