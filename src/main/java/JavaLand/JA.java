/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaLand;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * Java Land Application - Utility Library
 * @author aladar
 */
public class JA {     

    //==========================================================================
    // Singleton
    //==========================================================================
    private JA() {
    }
    
    public static JA getInstance() {
        return JAHolder.INSTANCE;
    }
    
    private static class JAHolder {
        private static final JA INSTANCE = new JA();
    }
    
    //==========================================================================
    // Forms a standard timestamp
    //==========================================================================
    public static String getTimestamp() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        return formatter.format(date);         
    }
    
    //==========================================================================
    // Forms a unique ID for this run
    //==========================================================================
    private static int uniqueCounter = 0;
            
    public static long uniqueID() {
        return uniqueCounter++;
    }
    
    //==========================================================================
    // Will it ever be remembered to be used...?
    //==========================================================================
    public static void println(Object line) {
        System.out.println(line);
    }
    
    //==========================================================================
    // Simple iteration
    //==========================================================================
    /**
     * Iterates from 0 to times-1.
     * Usage example: JA.times(10, i -> JA.println("Iteration: " + i));
     * @param times
     * @param fn 
     */
    public static void times(int times, IntConsumer fn) {
        IntStream.range(0, times).forEach(fn);
    }
}
