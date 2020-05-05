import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HashMap implementation of a one level cache.
 * 
 * @author Aidan Vanleuven
**/

public class CacheTest{
    ConcurrentHashMap<Integer, BTreeNode> cache;
    Queue<Integer> ll;
    long size;

    /**
     * Cache constructor
     * @param cacheSize - desired size of cache
    **/
    public CacheTest(int cacheSize){
        cache = new ConcurrentHashMap<>();
        ll = new LinkedList<Integer>();
        size = cacheSize;
    }

    /**
     * Gets the object associated with the offset
     * @param offset the offset of the node to return
     * @return the node associated with the offset value
    **/
    public BTreeNode getObject(int offset){
        return cache.get(offset);
    }

    /**
     * Adds the node to the cache
     * @param node the node to add to the cache
     * @return the newly cached node
    **/
    public BTreeNode addObject(BTreeNode node){
        ll.add(node.offset);
        if (size <= ll.size()){
            cache.remove(ll.poll());
        }
        return cache.putIfAbsent(node.offset, node);
    }

    /**
     * Clears the cache
    **/
    public void clearCache(){
        ll.clear();
        cache.clear();
    }
}