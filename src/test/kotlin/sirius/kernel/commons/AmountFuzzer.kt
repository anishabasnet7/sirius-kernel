/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package sirius.kernel.commons

import com.pholser.junit.quickcheck.generator.InRange
import edu.berkeley.cs.jqf.fuzz.Fuzz
import edu.berkeley.cs.jqf.fuzz.JQF
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.runner.RunWith
import org.mockito.internal.matchers.LessOrEqual
import java.math.BigDecimal

@RunWith(JQF::class)
class AmountFuzzer {
    @Fuzz
    fun testAmountScientificString(
            @InRange(min = "-999000000000", max = "999000000000") number: BigDecimal,
            @InRange(minInt = 0) digits: Int
    ) {
        val amount = Amount.of(number)
        val scientificString = amount.toScientificString(digits, null)

        var expectedLength: Long = 3 + 2 // digits before decimal separator + (space + unit) at the end
        if (digits > 0) {
            expectedLength += digits + 1 // decimal places + decimal separator
        }
        if (number < BigDecimal.ZERO) {
            expectedLength++ // additional "-" sign
        }

        Assert.assertNotNull(scientificString)
        MatcherAssert.assertThat(
                "Input: ($number, $digits) gets $scientificString, which is too long!",
                scientificString.length.toLong(),
                LessOrEqual(expectedLength)
        )
    }
}