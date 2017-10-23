package org.semux.gui;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

public class SwingUtilTest {

    private String goodDoubleString1 = "1.0000";
    private String goodDoubleString2 = "2.25";

    private String badDoubleString1 = "2f.25test";
    private String badDoubleString2 = "worstWordt";

    @Before
    public void setup() {
        Locale.setDefault(new Locale("us", "US"));
    }

    @Test
    public void testFormatNumber() {
        double x = 12345678.1234;
        assertEquals("12,345,678", SwingUtil.formatNumber(x, 0));
        assertEquals("12,345,678.12", SwingUtil.formatNumber(x, 2));
    }

    @Test
    public void testParseNumber() throws ParseException {
        assertEquals(12345678.12, SwingUtil.parseNumber("12,345,678.12").doubleValue(), 10e-9);
    }

    @Test
    public void testFromatAndEncodeValue() throws ParseException {
        long x = 1_234_123_000_000L;
        assertEquals("1,234.12 SEM", SwingUtil.formatValue(x));
        assertEquals(x, SwingUtil.parseValue("1,234.123 SEM"));
    }

    @Test
    public void testFromatAndEncodePercentage() throws ParseException {
        double x = 12.3456;
        assertEquals("12.3 %", SwingUtil.formatPercentage(x));
        assertEquals(12.3, SwingUtil.parsePercentage("12.3 %"), 10e-9);
    }

    @Test
    public void testNumberComparator() {
        // String 1 < String 2
        long compareResult1 = SwingUtil.NUMBER_COMPARATOR.compare(goodDoubleString1, goodDoubleString2);
        assertEquals(-1L, compareResult1, 0L);

        // String 1 > String 2
        long compareResult2 = SwingUtil.NUMBER_COMPARATOR.compare(goodDoubleString2, goodDoubleString1);
        assertEquals(1L, compareResult2, 0L);

        // String 1 == String 2
        long compareResult3 = SwingUtil.NUMBER_COMPARATOR.compare(goodDoubleString1, goodDoubleString1);
        assertEquals(0L, compareResult3, 0L);
    }

    @Test(expected = NumberFormatException.class)
    public void testNumberComparatorExceptionPartiallyWrongInput() {
        SwingUtil.NUMBER_COMPARATOR.compare(goodDoubleString1, badDoubleString1);
    }

    @Test(expected = NumberFormatException.class)
    public void testNumberComparatorExceptionTotallyWrongInput() {
        SwingUtil.NUMBER_COMPARATOR.compare(goodDoubleString1, badDoubleString2);
    }

}