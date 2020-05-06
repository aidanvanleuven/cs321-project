import java.util.LinkedHashMap;

/**
 * HashMap implementation of a one level cache.
 * 
 * @author Aidan Vanleuven
 * @param <K> type of the key value
 * @param <V> type of the object to store
**/

public class Cache <K,V>{
    LinkedHashMap<K,V> cache;
    int size;

    /**
     * Cache constructor
     * @param cacheSize desired size of cache
    **/
    public Cache (int cacheSize){
        cache = new LinkedHashMap<K,V>((int)(cacheSize / 0.70));
        size = cacheSize;
    }

    /**
     * Gets the object associated with the offset
     * @param key the key to insert
     * @return the object associated with the key value or null if not found
    **/
    public V getObject(K key){
        return cache.get(key);
    }

    /**
     * Adds the object to the cache
     * @param object the object to add to the cache
     * @param key the key to associate with the object
     * @return the newly cached object
    **/
    public V addObject(V object, K key){
        V find = cache.get(key);
        if (find != null){
            return find;
        } else {
            if (cache.size() >= size){
                cache.remove(cache.entrySet().iterator().next().getKey());
            }
            return cache.put(key, object);
        }
    }

    /**
     * Removes an object from the cache
     * @param key the key of the object to remove
     * @return the deleted object or null if not found
    **/
    public V removeObject(K key){
        return cache.remove(key);
    }

    /**
     * Clears the cache
    **/
    public void clearCache(){
        cache.clear();
    }

    /**
     * Prints the cache to stdout
    **/
    public void printCache(){
        for (K key : cache.keySet()){
            System.out.println(cache.get(key));
        }
    }
}