package collection;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class ThreadSafeCollection<T> implements Collection<T> {

  private final Collection<T> innerCollection;

  public ThreadSafeCollection(Collection<T> collection) {
    super();
    this.innerCollection = Collections.synchronizedCollection(collection);
  }

  @Override
  public int size() {
    synchronized (this) {
      return this.innerCollection.size();
    }
  }

  @Override
  public boolean isEmpty() {
    synchronized (this) {
      return this.innerCollection.isEmpty();
    }
  }

  @Override
  public boolean contains(Object o) {
    synchronized (this) {
      return this.innerCollection.contains(o);
    }
  }

  @Override
  public Iterator<T> iterator() {
    synchronized (this) {
      return this.innerCollection.iterator();
    }
  }

  @Override
  public Object[] toArray() {
    synchronized (this) {
      return this.innerCollection.toArray();
    }
  }

  @Override
  public <T> T[] toArray(T[] a) {
    synchronized (this) {
      return this.innerCollection.toArray(a);
    }
  }

  @Override
  public boolean add(T e) {
    synchronized (this) {
      return this.innerCollection.add(e);
    }
  }

  @Override
  public boolean remove(Object o) {
    synchronized (this) {
      return this.innerCollection.remove(o);
    }
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    synchronized (this) {
      return this.innerCollection.containsAll(c);
    }
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    synchronized (this) {
      return this.addAll(c);
    }
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    synchronized (this) {
      return this.innerCollection.removeAll(c);
    }
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    synchronized (this) {
      return this.innerCollection.retainAll(c);
    }
  }

  @Override
  public void clear() {
    synchronized (this) {
      this.innerCollection.clear();
    }
  }  
}