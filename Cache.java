import java.util.Iterator;
import java.util.LinkedList;

/**
 * Linked list implementation of a one or two-level cache.
 * 
 * @author Aidan Vanleuven
 * 
 * @param <T> - class of objects stored in the cache
**/

public class Cache<T>{
    private int cacheOneSize;               // specified size of cache 1
    private int cacheTwoSize;               // specified size of cache 2
    public int cacheOneHit;                 // number of times cache 1 hits
    public int cacheOneMiss;                // number of times cache 1 misses
    public int cacheTwoHit;                 // number of times cache 2 hits
    public int cacheTwoMiss;                // number of times cache 2 misses
    private boolean twoLevel;               // is the cache two levels?
    private LinkedList<T> cacheOne;         // the actual linked list for cache 1
    private LinkedList<T> cacheTwo;         // the actual linked list for cache 2

    /**
     * Creates an empty one-level cache.
     * @param oneSize - desired size of the cache. 
    **/
    public Cache(int oneSize){
        twoLevel = false;
        cacheOneSize = oneSize;
        cacheOne = new LinkedList<T>();
    } 

    /**
     * Creates an empty two-level cache.
     * @param oneSize - desired size of the first-level cache. 
     * @param twoSize - desired size of the second-level cache.
    **/
    public Cache(int oneSize, int twoSize){
        twoLevel = true;
        cacheOneSize = oneSize;
        cacheTwoSize = twoSize;
        cacheOne = new LinkedList<T>();
        cacheTwo = new LinkedList<T>();
    }

    /**
     * Returns a specified object from the cache.
     * @param obj - the object to retrieve.
     * @return the object.
    **/
    public T getObject(T obj){
        SearchResult result1 = search(cacheOne, obj);   // get the result of the search operation on cache one
        if (result1.found){
            cacheOneHit++;                              // increment hit counter
            result1.iterator.remove();                  // remove item from cache with iterator to prevent searching twice
            cacheOne.addFirst(obj);                     // add item to the front of cache one
            if (twoLevel){
                cacheTwo.remove(obj);                   // remove the item from cache two
                cacheTwo.addFirst(obj);                 // add item to the front of cache two
            }
            return result1.result;                      // return the found item
        } else {
            cacheOne.addFirst(obj);                     // if not found, add it to the front of cache one
            if (cacheOne.size() > cacheOneSize){
                cacheOne.removeLast();                  // check specified size, if too big, remove last item from cache one
            }
            cacheOneMiss++;                             // increment miss counter
        }

        if (twoLevel) {
            SearchResult result2 = search(cacheTwo, obj);   // get the result of the search operation on cache two
            if (result2.found){
                cacheTwoHit++;                              // increment hit counter
                result2.iterator.remove();                  // remove item from cache with iterator to prevent searching twice
                cacheTwo.addFirst(obj);                     // add item to the front of cache two
                return result2.result;                      // return the found item
            } else {
                cacheTwo.addFirst(obj);                     // if not found, add it to the front of cache two
                if (cacheTwo.size() > cacheTwoSize){
                    cacheTwo.removeLast();                  // check specified size, if too big, remove last item from cache one
                }
                cacheTwoMiss++;                             // increment miss counter
            }
        }
        return cacheOne.getFirst();                     // at this point, the first item will always be the requested item
    }

    /**
     * Removes a specified object from the cache.
     * @param obj - the object to delete 
     * @return if found, the object. If not, null.
    **/
    public T removeObject(T obj){
        SearchResult result1 = search(cacheOne, obj);   // search cache one for match
        if (result1.found){
            result1.iterator.remove();                  // remove object from cache one
            return result1.result;                      // return removed object
        } else {
            System.out.println("1st cache miss.");
            if (twoLevel){
                SearchResult result2 = search(cacheTwo, obj);   // search cache two for match
                if (result2.found){
                    result2.iterator.remove();                  // remove object from cache
                    return result2.result;                      // return removed object
                } else {
                    System.out.println("2nd cache miss.");
                    return null;                                // if not found in cache two, return null
                }
            } else {
                return null;                                    // if not found in cache one, return null
            }
        }
    }

    /**
     * Wipes the cache.
    **/
    public void clearCache(){
        cacheOne = new LinkedList<T>();         // replaces cache one with a new empty cache
        if (twoLevel){
            cacheTwo = new LinkedList<T>();     // replaces cache two with a new empty cache
        }
    }

    /**
     * Adds an object to the cache. The cache is searched
     * for the object and if found, moved to the front.
     * @param obj - The object to add to the cache.
     * @return - The newly created object
    **/
    public T addObject(T obj){
        SearchResult result1 = search(cacheOne, obj);   // get the result of the search operation on cache one
        if (result1.found){
            cacheOneHit++;                              // increment hit counter
            result1.iterator.remove();                  // remove item from cache with iterator to prevent searching twice
            cacheOne.addFirst(obj);                     // add item to the front of cache one
            if (twoLevel){
                cacheTwo.remove(obj);                   // remove the item from cache two
                cacheTwo.addFirst(obj);                 // add item to the front of cache two
            }
            return result1.result;                      // return the found item
        } else {
            cacheOne.addFirst(obj);                     // if not found, add it to the front of cache one
            if (cacheOne.size() > cacheOneSize){
                cacheOne.removeLast();                  // check specified size, if too big, remove last item from cache one
            }
            cacheOneMiss++;                             // increment miss counter
        }

        if (twoLevel) {
            SearchResult result2 = search(cacheTwo, obj);   // get the result of the search operation on cache two
            if (result2.found){
                cacheTwoHit++;                              // increment hit counter
                result2.iterator.remove();                  // remove item from cache with iterator to prevent searching twice
                cacheTwo.addFirst(obj);                     // add item to the front of cache two
                return result2.result;                      // return the found item
            } else {
                cacheTwo.addFirst(obj);                     // if not found, add it to the front of cache two
                if (cacheTwo.size() > cacheTwoSize){
                    cacheTwo.removeLast();                  // check specified size, if too big, remove last item from cache one
                }
                cacheTwoMiss++;                             // increment miss counter
            }
        }
        return cacheOne.getFirst();                     // at this point, the first item will always be the requested item
    }

    /**
     * Private function used to search the cache.
     * @param cache - the cache to search.
     * @param obj - the object to search for. 
     * @return the iterator, the found object, and whether or not it was found.
    **/
    private SearchResult search(LinkedList<T> cache, T obj){
        Iterator<T> it = cache.iterator();                      // make a new iterator
        while (it.hasNext()){
            T next = it.next();                                 // get the next element in specified cache
            if (next.equals(obj)){
                return new SearchResult(it, next, true);        // return the iterator, object, and that the item was found
            }
        }
        return new SearchResult(null, null, false);             // if not found, return false, other fields don't matter
    }

    /**
     * Object for returning search results.
    **/
    class SearchResult {
        // returning an iterator allows us to remove an object without traversing the list another time unlike linkedList.remove(object)
        Iterator<T> iterator;
        T result;
        boolean found;

        SearchResult(Iterator<T> it, T res, boolean f){
            iterator = it;
            result = res;
            found = f;
        }
    }
}