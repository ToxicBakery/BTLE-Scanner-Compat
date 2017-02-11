package com.ToxicBakery.library.btle.scanning;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Validate scan result wrapper works as intended.
 */
public class ScanResultCompatTest {

    @org.junit.Test
    public void testWriteToParcel() throws Exception {
        // Have to use mocking to test this.
    }

    @org.junit.Test
    public void testDescribeContents() throws Exception {
        assertEquals(0, new ScanResultCompat(null, 0, 0).describeContents());
    }

    @org.junit.Test
    public void testGetDevice() throws Exception {
        ScanResultCompat scanResultCompat = new ScanResultCompat(null, 0, 0);
        assertNull(scanResultCompat.getDevice());
    }

    @org.junit.Test
    public void testGetRssi() throws Exception {
        assertEquals(42, new ScanResultCompat(null, 42, 0).getRssi());
    }

    @org.junit.Test
    public void testGetTimestampNanos() throws Exception {
        assertEquals(42, new ScanResultCompat(null, 0, 42).getTimestampNanos());
    }

    @org.junit.Test
    public void testHashCode() throws Exception {
        ScanResultCompat scanResultCompat1 = new ScanResultCompat(null, 1, 2);
        ScanResultCompat scanResultCompat2 = new ScanResultCompat(null, 2, 1);
        assertEquals(scanResultCompat1.hashCode(), scanResultCompat2.hashCode());
    }

    @org.junit.Test
    public void testEquals() throws Exception {
        ScanResultCompat scanResultCompat1 = new ScanResultCompat(null, 1, 2);
        ScanResultCompat scanResultCompat2 = new ScanResultCompat(null, 2, 1);
        assertEquals(scanResultCompat1, scanResultCompat2);
    }

    @org.junit.Test
    public void testToString() throws Exception {
        ScanResultCompat scanResultCompat = new ScanResultCompat(null, 1, 2);
        String expected = "ScanResultCompat{mDevice=null, mRssi=1, mTimestampNanos=2}";
        assertEquals(expected, scanResultCompat.toString());
    }

}