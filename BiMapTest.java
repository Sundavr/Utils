import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 * BiMap tests.
 * @author JOHAN
 */
public class BiMapTest {
    /**
     * Test of clear method, of class BiMap.
     */
    @Test
    public void testClear() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        biMap.clear();
        assertEquals(0, biMap.size());
        assertEquals(0, biMap.getKeyFromValueMap().size());
        assertEquals(0, biMap.getValueFromKeyMap().size());
    }

    /**
     * Test of put method, of class BiMap.
     */
    @Test
    public void testPut() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        biMap.put(1, "One");
        
        assertEquals(3, biMap.size());
        assertEquals("One", biMap.getValue(1));
        assertEquals("Two", biMap.getValue(2));
        assertEquals("Three", biMap.getValue(3));
        assertEquals(1, (int)biMap.getKey("One"));
        assertEquals(2, (int)biMap.getKey("Two"));
        assertEquals(3, (int)biMap.getKey("Three"));
        //KeyFromValueMap
        assertEquals(3, biMap.getKeyFromValueMap().size());
        assertEquals(true, biMap.getKeyFromValueMap().containsKey("One"));
        assertEquals(1, (int)biMap.getKeyFromValueMap().get("One"));
        assertEquals(true, biMap.getKeyFromValueMap().containsKey("Two"));
        assertEquals(2, (int)biMap.getKeyFromValueMap().get("Two"));
        assertEquals(true, biMap.getKeyFromValueMap().containsKey("Three"));
        assertEquals(3, (int)biMap.getKeyFromValueMap().get("Three"));
        //ValueFromKeyMap
        assertEquals(3, biMap.getValueFromKeyMap().size());
        assertEquals(true, biMap.getValueFromKeyMap().containsKey(1));
        assertEquals("One", biMap.getValueFromKeyMap().get(1));
        assertEquals(true, biMap.getValueFromKeyMap().containsKey(2));
        assertEquals("Two", biMap.getValueFromKeyMap().get(2));
        assertEquals(true, biMap.getValueFromKeyMap().containsKey(3));
        assertEquals("Three", biMap.getValueFromKeyMap().get(3));
        
        //ajout d'une valeur déjà dans la map
        biMap.put(4, "One");
        assertEquals(3, biMap.size());
        assertEquals(3, biMap.getValueFromKeyMap().size());
        assertEquals(3, biMap.getValueFromKeyMap().size());
        assertEquals(null, biMap.get(1));
        assertEquals("One", biMap.get(4));
        assertEquals(4, (int)biMap.getKey("One"));
    }

    /**
     * Test of putAll method, of class BiMap.
     */
    @Test
    public void testPutAll() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        
        BiMap<Integer, String> secondMap = new BiMap();
        secondMap.put(1, "One");
        secondMap.put(4, "Four");
        
        secondMap.putAll(biMap);
        
        assertEquals(4, secondMap.size());
        assertEquals("One", secondMap.getValue(1));
        assertEquals("Two", secondMap.getValue(2));
        assertEquals("Three", secondMap.getValue(3));
        assertEquals("Four", secondMap.getValue(4));
        assertEquals(1, (int)secondMap.getKey("One"));
        assertEquals(2, (int)secondMap.getKey("Two"));
        assertEquals(3, (int)secondMap.getKey("Three"));
        assertEquals(4, (int)secondMap.getKey("Four"));
        //KeyFromValueMap
        assertEquals(4, secondMap.getKeyFromValueMap().size());
        assertEquals(true, secondMap.getKeyFromValueMap().containsKey("One"));
        assertEquals(1, (int)secondMap.getKeyFromValueMap().get("One"));
        assertEquals(true, secondMap.getKeyFromValueMap().containsKey("Two"));
        assertEquals(2, (int)secondMap.getKeyFromValueMap().get("Two"));
        assertEquals(true, secondMap.getKeyFromValueMap().containsKey("Three"));
        assertEquals(3, (int)secondMap.getKeyFromValueMap().get("Three"));
        assertEquals(true, secondMap.getKeyFromValueMap().containsKey("Four"));
        assertEquals(4, (int)secondMap.getKeyFromValueMap().get("Four"));
        //ValueFromKeyMap
        assertEquals(4, secondMap.getValueFromKeyMap().size());
        assertEquals(true, secondMap.getValueFromKeyMap().containsKey(1));
        assertEquals("One", secondMap.getValueFromKeyMap().get(1));
        assertEquals(true, secondMap.getValueFromKeyMap().containsKey(2));
        assertEquals("Two", secondMap.getValueFromKeyMap().get(2));
        assertEquals(true, secondMap.getValueFromKeyMap().containsKey(3));
        assertEquals("Three", secondMap.getValueFromKeyMap().get(3));
        assertEquals(true, secondMap.getValueFromKeyMap().containsKey(4));
        assertEquals("Four", secondMap.getValueFromKeyMap().get(4));
        
        //ajout de valeurs déjà dans la map
        BiMap<Integer, String> thirdMap = new BiMap();
        thirdMap.put(5, "One");
        thirdMap.put(6, "Six");
        thirdMap.put(7, "Three");
        
        biMap.putAll(thirdMap);
        assertEquals(4, biMap.size());
        assertEquals(4, biMap.getKeyFromValueMap().size());
        assertEquals(4, biMap.getValueFromKeyMap().size());
        assertEquals("One", biMap.get(5));
        assertEquals("Six", biMap.get(6));
        assertEquals("Three", biMap.get(7));
        assertEquals(null, biMap.get(1));
        assertEquals(null, biMap.get(3));
        assertEquals(5, (int)biMap.getKey("One"));
        assertEquals(6, (int)biMap.getKey("Six"));
        assertEquals(7, (int)biMap.getKey("Three"));
    }
    
    /**
     * Test of putIfAbsent method, of class BiMap.
     */
    @Test
    public void testPutIfAbsent() {
        BiMap<Integer, String> biMap = new BiMap();
        assertEquals(null, biMap.putIfAbsent(1, "One"));
        assertEquals(null, biMap.putIfAbsent(2, "Two"));
        assertEquals(null, biMap.putIfAbsent(3, "Three"));
        
        assertEquals(3, biMap.size());
        assertEquals("One", biMap.getValue(1));
        assertEquals("Two", biMap.getValue(2));
        assertEquals("Three", biMap.getValue(3));
        assertEquals(1, (int)biMap.getKey("One"));
        assertEquals(2, (int)biMap.getKey("Two"));
        assertEquals(3, (int)biMap.getKey("Three"));
        
        assertEquals("One", biMap.putIfAbsent(1, "Two"));
        assertEquals(3, biMap.size());
        assertEquals("One", biMap.getValue(1));
        assertEquals(1, (int)biMap.getKey("One"));
        
        //ajout d'une valeur déjà dans la map
        assertEquals(null, biMap.putIfAbsent(4, "One"));
        assertEquals(3, biMap.size());
        assertEquals(3, biMap.getValueFromKeyMap().size());
        assertEquals(3, biMap.getValueFromKeyMap().size());
        assertEquals(null, biMap.get(1));
        assertEquals("One", biMap.get(4));
        assertEquals(4, (int)biMap.getKey("One"));
    }
    
    /**
     * Test of remove method, of class BiMap.
     */
    @Test
    public void testRemove() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        
        //remove(Key)
        assertEquals("Three", biMap.remove(3));
        
        assertEquals(2, biMap.size());
        assertEquals("One", biMap.getValue(1));
        assertEquals("Two", biMap.getValue(2));
        assertEquals(1, (int)biMap.getKey("One"));
        assertEquals(2, (int)biMap.getKey("Two"));
        //KeyFromValueMap
        assertEquals(2, biMap.getKeyFromValueMap().size());
        assertEquals(true, biMap.getKeyFromValueMap().containsKey("One"));
        assertEquals(1, (int)biMap.getKeyFromValueMap().get("One"));
        assertEquals(true, biMap.getKeyFromValueMap().containsKey("Two"));
        assertEquals(2, (int)biMap.getKeyFromValueMap().get("Two"));
        //ValueFromKeyMap
        assertEquals(2, biMap.getValueFromKeyMap().size());
        assertEquals(true, biMap.getValueFromKeyMap().containsKey(1));
        assertEquals("One", biMap.getValueFromKeyMap().get(1));
        assertEquals(true, biMap.getValueFromKeyMap().containsKey(2));
        assertEquals("Two", biMap.getValueFromKeyMap().get(2));
        
        //remove(Key, Value)
        assertEquals(false, biMap.remove(1, "Two"));
        assertEquals(2, biMap.size());
        assertEquals("One", biMap.getValue(1));
        assertEquals(1, (int)biMap.getKey("One"));
        
        assertEquals(true, biMap.remove(1, "One"));
        assertEquals(1, biMap.size());
        assertEquals(null, biMap.getValue(1));
        assertEquals(null, biMap.getKey("One"));
    }
    
    /**
     * Test of replace method, of class BiMap.
     */
    @Test
    public void testReplace() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        
        //replace(Key, Value)
        assertEquals("One", biMap.replace(1, "Zero"));
        assertEquals(3, biMap.size());
        assertEquals(1, (int)biMap.getKey("Zero"));
        assertEquals(null, biMap.getKey("One"));
        assertEquals("Zero", biMap.getValue(1));
        
        assertEquals(null, biMap.replace(4, "Four"));
        
        //test avec une valeur déjà dans la map
        assertEquals("Zero", biMap.replace(1, "Two"));
        assertEquals(2, biMap.size());
        assertEquals(2, biMap.getKeyFromValueMap().size());
        assertEquals(2, biMap.getValueFromKeyMap().size());
        assertEquals(1, (int)biMap.getKey("Two"));
        assertEquals(null, biMap.getKey("Zero"));
        assertEquals("Two", biMap.getValue(1));
        assertEquals(null, biMap.getValue(2));
        
        //replace(Key, oldValue, newValue)
        assertEquals(true, biMap.replace(3, "Three", "Four"));
        assertEquals(2, biMap.size());
        assertEquals(3, (int)biMap.getKey("Four"));
        assertEquals(null, biMap.getKey("Three"));
        assertEquals("Four", biMap.getValue(3));
        
        assertEquals(false, biMap.replace(1, "One", "Five"));
        
        //test avec une valeur déjà dans la map
        assertEquals(true, biMap.replace(1, "Two", "Four"));
        assertEquals(1, biMap.size());
        assertEquals(1, biMap.getKeyFromValueMap().size());
        assertEquals(1, biMap.getValueFromKeyMap().size());
        assertEquals(1, (int)biMap.getKey("Four"));
        assertEquals(null, biMap.getKey("Two"));
        assertEquals("Four", biMap.getValue(1));
        assertEquals(null, biMap.getValue(3));
    }
    
    /**
     * Test of replaceAll method, of class BiMap.
     */
    @Test
    public void testReplaceAll() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        
        biMap.replaceAll((k,v) -> v + "Replaced");
        assertEquals("OneReplaced", biMap.get(1));
        assertEquals("TwoReplaced", biMap.get(2));
        assertEquals("ThreeReplaced", biMap.get(3));
        
        biMap.replaceAll((k,v) -> k * k + "");
        assertEquals("1", biMap.get(1));
        assertEquals("4", biMap.get(2));
        assertEquals("9", biMap.get(3));
        
        //test avec une valeur déjà dans la map
        biMap.replaceAll((k,v) -> (k==3) ? "4" : v);
        assertEquals(2, biMap.size());
        assertEquals(2, biMap.getKeyFromValueMap().size());
        assertEquals(2, biMap.getValueFromKeyMap().size());
        assertEquals(3, (int)biMap.getKey("4"));
        assertEquals(null, biMap.getKey("9"));
        assertEquals("4", biMap.getValue(3));
        assertEquals(null, biMap.getValue(2));
    }
    
    /**
     * Test of merge method, of class BiMap.
     */
    @Test
    public void testMerge() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        
        biMap.merge(1, "Merged", (v1,v2) -> v1 + v2);
        assertEquals("OneMerged", biMap.get(1));
        
        biMap.merge(4, "Four", (v1,v2) -> v1 + v2);
        assertEquals("Four", biMap.get(4));
        
        //test avec une valeur déjà dans la map
        biMap.merge(2, "Three", (v1,v2) -> v2);
        assertEquals(3, biMap.size());
        assertEquals(3, biMap.getKeyFromValueMap().size());
        assertEquals(3, biMap.getValueFromKeyMap().size());
        assertEquals(2, (int)biMap.getKey("Three"));
        assertEquals(null, biMap.getKey("Two"));
        assertEquals("Three", biMap.getValue(2));
        assertEquals(null, biMap.getValue(3));
    }
    
    /**
     * Test of compute method, of class BiMap.
     */
    @Test
    public void testCompute() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        
        assertEquals("ONE", biMap.compute(1, (k,v) -> "ONE"));
        assertEquals("ONE", biMap.get(1));
        
        assertEquals("2Two", biMap.compute(2, (k,v) -> k + v));
        assertEquals("2Two", biMap.get(2));
        
        assertEquals("ThreeComputed", biMap.compute(3, (k,v) -> v + "Computed"));
        assertEquals("ThreeComputed", biMap.get(3));
        
        //test avec une valeur déjà dans la map
        biMap.compute(1, (k,v) -> "2Two");
        assertEquals(2, biMap.size());
        assertEquals(2, biMap.getKeyFromValueMap().size());
        assertEquals(2, biMap.getValueFromKeyMap().size());
        assertEquals(1, (int)biMap.getKey("2Two"));
        assertEquals(null, biMap.getKey("ONE"));
        assertEquals("2Two", biMap.getValue(1));
        assertEquals(null, biMap.getValue(2));
    }
    
    /**
     * Test of computeIfAbsent method, of class BiMap.
     */
    @Test
    public void testComputeIfAbsent() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        
        assertEquals("One", biMap.computeIfAbsent(1, k -> "Two"));
        assertEquals("One", biMap.get(1));
        
        assertEquals("Four", biMap.computeIfAbsent(4, k -> "Four"));
        assertEquals("Four", biMap.get(4));
        
        assertEquals("5Five", biMap.computeIfAbsent(5, k -> k + "Five"));
        assertEquals("5Five", biMap.get(5));
        
        //test avec une valeur déjà dans la map
        biMap.computeIfAbsent(6, k -> "Two");
        assertEquals(5, biMap.size());
        assertEquals(5, biMap.getKeyFromValueMap().size());
        assertEquals(5, biMap.getValueFromKeyMap().size());
        assertEquals(6, (int)biMap.getKey("Two"));
        assertEquals("Two", biMap.getValue(6));
        assertEquals(null, biMap.getValue(2));
    }
    
    /**
     * Test of computeIfPresent method, of class BiMap.
     */
    @Test
    public void testComputeIfPresent() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        
        assertEquals("1One", biMap.computeIfPresent(1, (k,v) -> k + v));
        assertEquals("1One", biMap.get(1));
        
        assertEquals(null, biMap.computeIfPresent(4, (k,v) -> "Four"));
        assertEquals(null, biMap.get(4));
        
        //test avec une valeur déjà dans la map
        biMap.computeIfPresent(1, (k,v) -> "Two");
        assertEquals(2, biMap.size());
        assertEquals(2, biMap.getKeyFromValueMap().size());
        assertEquals(2, biMap.getValueFromKeyMap().size());
        assertEquals(1, (int)biMap.getKey("Two"));
        assertEquals("Two", biMap.getValue(1));
        assertEquals(null, biMap.getValue(2));
    }
    
    /**
     * Test of forEach method, of class BiMap.
     */
    @Test
    public void testForEach() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        
        biMap.forEach((k, v) -> {
            switch (k) {
                case 1:
                    assertEquals("One", v);
                    break;
                case 2:
                    assertEquals("Two", v);
                    break;
                default:
                    assertEquals(3, (int)k);
                    assertEquals("Three", v);
                    break;
            }
        });
    }

    /**
     * Test of equals method, of class BiMap.
     */
    @Test
    public void testEquals() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        assertEquals(biMap, biMap);
        
        BiMap<Integer, String> secondMap = new BiMap();
        secondMap.put(1, "One");
        secondMap.put(2, "Two");
        secondMap.put(3, "Three");
        assertEquals(biMap, secondMap);
        secondMap.put(4, "Four");
        assertNotEquals(biMap, secondMap);
        
        HashMap<Integer, String> thirdMap = new HashMap();
        thirdMap.put(1, "One");
        thirdMap.put(2, "Two");
        thirdMap.put(3, "Three");
        assertEquals(biMap, thirdMap);
        biMap.put(4, "Four");
        assertNotEquals(biMap, thirdMap);
    }
    
    /**
     * Test of toString method, of class BiMap.
     */
    @Test
    public void testToString() {
        BiMap<Integer, String> biMap = new BiMap();
        assertEquals("{}", biMap.toString());
        
        biMap.put(1, "One");
        assertEquals("{1=One}", biMap.toString());
        
        biMap.put(2, "Two");
        assertEquals("{1=One, 2=Two}", biMap.toString());
        
        biMap.put(3, "Three");
        assertEquals("{1=One, 2=Two, 3=Three}", biMap.toString());
        
    }
    
    /**
     * Test of getValueFromKeyMap method, of class BiMap.
     */
    @Test
    public void getValueFromKeyMap() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        
        assertEquals(3, biMap.getValueFromKeyMap().size());
        assertEquals(true, biMap.getValueFromKeyMap().containsKey(1));
        assertEquals(true, biMap.getValueFromKeyMap().containsKey(2));
        assertEquals(true, biMap.getValueFromKeyMap().containsKey(3));
        assertEquals(false, biMap.getValueFromKeyMap().containsKey(4));
        assertEquals(true, biMap.getValueFromKeyMap().containsValue("One"));
        assertEquals(true, biMap.getValueFromKeyMap().containsValue("Two"));
        assertEquals(true, biMap.getValueFromKeyMap().containsValue("Three"));
        assertEquals(false, biMap.getValueFromKeyMap().containsValue("Four"));
        
        biMap.remove(2);
        biMap.remove(3);
        
        assertEquals(1, biMap.getValueFromKeyMap().size());
        assertEquals(true, biMap.getValueFromKeyMap().containsKey(1));
        assertEquals(false, biMap.getValueFromKeyMap().containsKey(2));
        assertEquals(false, biMap.getValueFromKeyMap().containsKey(3));
        assertEquals(true, biMap.getValueFromKeyMap().containsValue("One"));
        assertEquals(false, biMap.getValueFromKeyMap().containsValue("Two"));
        assertEquals(false, biMap.getValueFromKeyMap().containsValue("Three"));
        
        biMap.remove(1);
        assertEquals(true, biMap.getValueFromKeyMap().isEmpty());
    }
    
    /**
     * Test of getKeyFromValueMap method, of class BiMap.
     */
    @Test
    public void getKeyFromValueMap() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        
        assertEquals(3, biMap.getValueFromKeyMap().size());
        assertEquals(true, biMap.getKeyFromValueMap().containsKey("One"));
        assertEquals(true, biMap.getKeyFromValueMap().containsKey("Two"));
        assertEquals(true, biMap.getKeyFromValueMap().containsKey("Three"));
        assertEquals(false, biMap.getKeyFromValueMap().containsKey("Four"));
        assertEquals(true, biMap.getKeyFromValueMap().containsValue(1));
        assertEquals(true, biMap.getKeyFromValueMap().containsValue(2));
        assertEquals(true, biMap.getKeyFromValueMap().containsValue(3));
        assertEquals(false, biMap.getKeyFromValueMap().containsValue(4));
        
        biMap.remove(2);
        biMap.remove(3);
        
        assertEquals(1, biMap.getKeyFromValueMap().size());
        assertEquals(true, biMap.getKeyFromValueMap().containsKey("One"));
        assertEquals(false, biMap.getKeyFromValueMap().containsKey("Two"));
        assertEquals(false, biMap.getKeyFromValueMap().containsKey("Three"));
        assertEquals(true, biMap.getKeyFromValueMap().containsValue(1));
        assertEquals(false, biMap.getKeyFromValueMap().containsValue(2));
        assertEquals(false, biMap.getKeyFromValueMap().containsValue(3));
        
        biMap.remove(1);
        assertEquals(true, biMap.getKeyFromValueMap().isEmpty());
    }

    /**
     * Test of getValue method, of class BiMap.
     */
    @Test
    public void testGetValue() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        assertEquals("One", biMap.getValue(1));
        assertEquals("Two", biMap.getValue(2));
        assertEquals("Three", biMap.getValue(3));
        
        biMap.remove(1);
        assertEquals(null, biMap.getValue(1));
    }

    /**
     * Test of getKey method, of class BiMap.
     */
    @Test
    public void testGetKey() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");        
        assertEquals(1, (int)biMap.getKey("One"));
        assertEquals(2, (int)biMap.getKey("Two"));
        assertEquals(3, (int)biMap.getKey("Three"));
        
        biMap.remove(1);
        assertEquals(null, biMap.getKey("One"));
    }

    /**
     * Test of removeKey method, of class BiMap.
     */
    @Test
    public void testRemoveKey() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        
        assertEquals("One", biMap.removeKey(1));
        assertEquals(2, biMap.size());
        assertEquals(2, biMap.getKeyFromValueMap().size());
        assertEquals(2, biMap.getValueFromKeyMap().size());
        
        assertEquals(null, biMap.removeKey(1));
        assertEquals(2, biMap.size());
        assertEquals(2, biMap.getKeyFromValueMap().size());
        assertEquals(2, biMap.getValueFromKeyMap().size());
        
        assertEquals("Two", biMap.removeKey(2));
        assertEquals("Three", biMap.removeKey(3));
        assertEquals(true, biMap.isEmpty());
        assertEquals(true, biMap.getKeyFromValueMap().isEmpty());
        assertEquals(true, biMap.getValueFromKeyMap().isEmpty());
    }

    /**
     * Test of removeValue method, of class BiMap.
     */
    @Test
    public void testRemoveValue() {
        BiMap<Integer, String> biMap = new BiMap();
        biMap.put(1, "One");
        biMap.put(2, "Two");
        biMap.put(3, "Three");
        
        assertEquals(1, (int)biMap.removeValue("One"));
        assertEquals(2, biMap.size());
        assertEquals(2, biMap.getKeyFromValueMap().size());
        assertEquals(2, biMap.getValueFromKeyMap().size());
        
        assertEquals(null, biMap.removeValue("One"));
        assertEquals(2, biMap.size());
        assertEquals(2, biMap.getKeyFromValueMap().size());
        assertEquals(2, biMap.getValueFromKeyMap().size());
        
        assertEquals(2, (int)biMap.removeValue("Two"));
        assertEquals(3, (int)biMap.removeValue("Three"));
        assertEquals(true, biMap.isEmpty());
        assertEquals(true, biMap.getKeyFromValueMap().isEmpty());
        assertEquals(true, biMap.getValueFromKeyMap().isEmpty());
    }
}
