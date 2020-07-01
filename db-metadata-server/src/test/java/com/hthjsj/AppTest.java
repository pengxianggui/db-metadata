package com.hthjsj;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    public static void main(String[] args) {
        System.out.println(Splitter.on(",").trimResults().omitEmptyStrings().splitToList("dfasd,f"));
        System.out.println(Splitter.on(",").trimResults().omitEmptyStrings().splitToList("dfasd,"));
        System.out.println(Splitter.on(",").trimResults().omitEmptyStrings().splitToList(",f"));
        System.out.println(Splitter.on(",").trimResults().omitEmptyStrings().splitToList("sdf"));
        System.out.println("==========================");
        System.out.println(Arrays.toString("dfasd,f".split(",")));
        System.out.println(Arrays.toString("dfasd,".split(",")));
        System.out.println(Arrays.toString(",f".split(",")));
        System.out.println(Arrays.toString("sdf".split(",")));
    }
}
