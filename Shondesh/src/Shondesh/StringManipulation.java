package Shondesh;

import java.util.*;

/*
return next index of matched pattern
called by HtmlFileToText
called by BanglaNumbersFinder
called by AgeDetector
called by MakeSubUrlList
*/

public class StringManipulation
{
/***************************finding matched portion of 2 strings.****************************************/
    /*
     * return the value of index+findingString
     */
        public int stringMatching(String strRoot, String strFind, int startIndex)
	{
		for(int I=startIndex; I<strRoot.length(); I++)
		{
			if( strRoot.regionMatches(false, I, strFind, 0, strFind.length())==true )
			{
				//System.out.println(I+strFind.length());
				return I+strFind.length();
			}
		}
		return 0;
	}

/**********************checking number is bangla or not**********************/
	public boolean isBanglaNumber(String word)
	{
		if( word==null )
			return false;
		if( word.length()==0 )
			return false;
		char ch = word.charAt(0);
		return ( ch>='\u09E6' && ch<='\u09EF' );
	}
     
/********************* return bangla number ***********************************/
        public String getBanglaNumber(int N){
            StringBuffer banglaNumber = new StringBuffer(); 
            char banglaDigits[] = new char[10];
            for(char I='\u09E6'; I<='\u09EF'; I++)
                banglaDigits[I-'\u09E6'] = I;
            while( N>0 ){
                banglaNumber.append(banglaDigits[N%10]);
                N/=10;
            }
            return new String(banglaNumber.reverse());
        }
        
/************************* parsing a string. return an ArrayList ***********************************/
        public ArrayList<String> parsingString(String text, String separator){
            ArrayList<String> wordsAL = new ArrayList<String>();
            String[] wordsA = text.split(separator);
            for(String str : wordsA ){
                System.out.println(str);
                wordsAL.add(str);
            }
            return wordsAL;
        }

}