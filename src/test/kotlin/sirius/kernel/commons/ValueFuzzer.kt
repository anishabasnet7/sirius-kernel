package sirius.kernel.commons

import edu.berkeley.cs.jqf.fuzz.Fuzz
import edu.berkeley.cs.jqf.fuzz.JQF
import org.junit.Assert
import org.junit.runner.RunWith
import java.math.BigDecimal

@RunWith(JQF::class)
class ValueFuzzer {

    //type conversion 
    //try-catch block isolate expected NumberFormatExceptions, 
    //confirming library do not handle malformed inputs 
    //internally within these conversion methods
   @Fuzz
    fun testSafeConversions(input: String) {
        val value = Value.of(input)
        try {
            value.asInt(0)
            value.asLong(0L)
            value.asDouble(0.0)
            value.asString()
        } catch (e: NumberFormatException) {
            //confirm that Value class has no internal input validation for numeric type
        }
    }

    //boolean parsing logic - truth/false 
    @Fuzz
    fun testBooleanParsing(input: String) {
        val value = Value.of(input)
        val parsedBool = value.asBoolean()

        val normalized = input.trim().lowercase()
        if (normalized in listOf("true", "yes", "1", "on")) {
            Assert.assertTrue(parsedBool)
        } else if (normalized in listOf("false", "no", "0", "off", "")) {
            Assert.assertFalse(parsedBool)
        }
    }
}