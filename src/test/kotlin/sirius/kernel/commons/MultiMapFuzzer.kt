package sirius.kernel.commons

import edu.berkeley.cs.jqf.fuzz.Fuzz
import edu.berkeley.cs.jqf.fuzz.JQF
import org.junit.Assert
import org.junit.runner.RunWith

@RunWith(JQF::class)
class MultiMapFuzzer {

    //verify put and get operation with generated string list inputs
    @Fuzz
    fun testPutAndGet(key: String, values: List<String>) {
        val map = MultiMap.create<String, String>()

        for (v in values) {
            map.put(key, v)
        }

        val retrieved = map.get(key)
        Assert.assertEquals(values.size, retrieved.size)
        Assert.assertTrue(retrieved.containsAll(values))
    }

    //structural state consistency of key sets and value collections
    @Fuzz
    fun testStructuralConsistency(keys: List<String>, values: List<Int>) {
        if (keys.isEmpty() || values.isEmpty()) return

        val map = MultiMap.create<String, Int>()

        for (i in keys.indices) {
            val k = keys[i]
            val v = values[i % values.size]
            map.put(k, v)
        }

        val uniqueKeys = keys.toSet()
        Assert.assertEquals(uniqueKeys.size, map.keySet().size)
    }
}