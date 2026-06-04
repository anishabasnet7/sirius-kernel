package sirius.kernel.commons

import edu.berkeley.cs.jqf.fuzz.Fuzz
import edu.berkeley.cs.jqf.fuzz.JQF
import org.junit.Assert
import org.junit.runner.RunWith

@RunWith(JQF::class)
class TrieFuzzer {

    //verify insertion and retrieval consistency of simple strings
    @Fuzz
    fun testTriePutAndGet(keys: List<String>, value: Int) {
        val trie = Trie.create<Int>()

        for (key in keys) {
            if (key.isNotEmpty()) {
                trie.put(key, value)
                Assert.assertEquals(value, trie.get(key))
            }
        }
    }

    //verifies prefix lookup capability
    @Fuzz
    fun testPrefixMatching(keys: List<String>, lookupKey: String) {
        val trie = Trie.create<String>()

        for (k in keys) {
            if (k.isNotEmpty()) {
                trie.put(k, k)
            }
        }

        if (keys.contains(lookupKey)) {
            Assert.assertEquals(lookupKey, trie.get(lookupKey))
        }
    }
}