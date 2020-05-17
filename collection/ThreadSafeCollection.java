package collection;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class ThreadSafeCollection<T> implements Collection<T> {

  private final Collection<T> innerCollection;

  public ThreadSafeCollection(Collection<T> collection) {
    super();
    // Documentation here notes that traversing the collection in anyway (outside of overridden methods) 
    // will require the use of a synchronized lock. Therefore overriden methods don't require lock as this
    // is managed internally
    this.innerCollection = Collections.synchronizedCollection(collection);
  }

  @Override
  public int size() {
    return this.innerCollection.size();
  }

  @Override
  public boolean isEmpty() {
    return this.innerCollection.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return this.innerCollection.contains(o);
  }

  @Override
  public Iterator<T> iterator() {
    return this.innerCollection.iterator();
  }

  @Override
  public Object[] toArray() {
    return this.innerCollection.toArray();    
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return this.innerCollection.toArray(a);
  }

  @Override
  public boolean add(T e) {
    return this.innerCollection.add(e);
  }

  @Override
  public boolean remove(Object o) {
    return this.innerCollection.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return this.innerCollection.containsAll(c);    
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    return this.addAll(c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return this.innerCollection.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return this.innerCollection.retainAll(c);
  }

  @Override
  public void clear() {
    this.innerCollection.clear();
  }  
}