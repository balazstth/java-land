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
 * Singleton also with static methods
 * @author aladar
 */
public class JA {     
    private JA() {
    }
    
    public static JA getInstance() {
        return JAHolder.INSTANCE;
    }
    
    private static class JAHolder {
        private static final JA INSTANCE = new JA();
    }
    
    //--------------------------------------------------------------------------
    
    public static String getTimestamp() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        return formatter.format(date);         
    }
    
    //--------------------------------------------------------------------------
    
    public static void println(Object line) {
        System.out.println(line);
    }
    
    //--------------------------------------------------------------------------

    /**
     * Usage: JA.times(10, i -> JA.println("Iteration: " + i));
     * Iterates from 1 to times.
     * @param times
     * @param fn 
     */
    public static void times(int times, IntConsumer fn) {
        IntStream.rangeClosed(1, times).forEach(fn);
    }
}
