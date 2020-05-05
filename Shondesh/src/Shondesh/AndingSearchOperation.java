/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Shondesh;

import java.util.*;

/**
 *
 * @author RATUL
 */
public class AndingSearchOperation {
    
    /*************anding all file names for desired ArrayList******************/
    public Vector searchOperation(WordData wd, ArrayList<String> words){
        WordAllFinder wafTemp = new WordAllFinder(wd, words.get(0));
        Vector a = wafTemp.uniqueFileName();
        for(int I=1; I<words.size(); I++){
            wafTemp = new WordAllFinder(wd, words.get(I));
            Vector b = wafTemp.uniqueFileName();
            a = andingFileName(a, b);
        }
        return a;
    }
    
    /********************make a set of common fileName*******************/
    public Vector andingFileName(Vector a, Vector b){
        Vector and = new Vector();
        for(int I=0; I<a.size(); I++)
            if( b.contains(a.get(I))==true )
                and.add(a.get(I));
        return and;
    }
    
    
}
