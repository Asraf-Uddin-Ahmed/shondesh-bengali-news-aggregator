/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Shondesh;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author RATUL
 */
public class TestMainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*FileDirectory fd = new FileDirectory();
        String[] children = fd.getDirectoryChildName(new File("C:\\Shondesh\\prothom-alo"));
        for(String str : children)
            System.out.println(str);
    */
        //new ReadingNewses(new WordData(), "C:\\Shondesh\\prothom-alo\\2012-02-17");
        Map<String, Integer> codon = new HashMap<String, Integer>();
        codon.put("ratul", 47);
        System.out.println(codon.get("ratul"));
        String name = "habijabi";
        System.out.println(name.substring(1, 4));
    }
}
