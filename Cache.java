import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

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
        cache = new LinkedHashMap<K,V>((int)(cacheSize / 0.74));
        size = cacheSize;
    }

    /**
     * Gets the object associated with the offset
     * @param key the key to insert
     * @return the object associated with the key value or null if not found
    **/
    public V getObject(K key){
        V result = cache.get(key);
        if (result != null){
            cache.remove(key);
            cache.put(key, result);
        }
        return result;
    }

    /**
     * Adds the object to the cache, check if key exists before calling
     * @param object the object to add to the cache
     * @param key the key to associate with the object
     * @return the newly cached object, null if size is 0
    **/
    public V addObject(V object, K key){
        if (cache.size() >= size){
            Iterator<Entry<K,V>> it = cache.entrySet().iterator();
            if (!it.hasNext()){
                return null;
            }
            cache.remove(it.next().getKey());
        }
        return cache.put(key, object);
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
     * Replaces an object in the cache
     * @param key the key of the object to modify
     * @param object the object to newly associate with the key
     * @return the newly replaced object or null if not found
    **/
    public V modifyObject(K key, V object){
        return cache.replace(key, object);
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