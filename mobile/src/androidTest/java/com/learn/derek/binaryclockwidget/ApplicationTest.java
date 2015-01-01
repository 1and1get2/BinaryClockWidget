package com.learn.derek.binaryclockwidget;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

    }
    public void testMyTest() throws Exception{
        final int expected = 1;
        final int reality = 5;
        Assert.assertEquals(expected, reality);
    }
    public void testGetGreeting() throws Exception {
        Assert.assertEquals("Holamundo", "aa");
    }
}