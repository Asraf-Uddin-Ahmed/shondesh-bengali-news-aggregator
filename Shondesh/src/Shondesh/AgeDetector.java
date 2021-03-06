package Shondesh;

/*
find which number is age
called by Main
*/
import java.io.*;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

class AgeDetector
{
	private ArrayList<String> key = new ArrayList<String>();	//store key word. 1st is starting & 2nd is ending point

	ArrayList<String> ages = new ArrayList<String>();
	ArrayList<String> newsLinesAges = new ArrayList<String>();
	ArrayList<String> fileNamesAges = new ArrayList<String>();
        Vector uniqueFileNamesAges = new Vector();
        
//initializes key with keyword of age from age.words
	public AgeDetector()
	{
		key.add("(");
                key.add(")");
                key.add("বয়স"); //boyosh
                key.add("বছর"); //bochor
                key.add("বছর"); //bochor
                key.add("বয়সে");//boyoshe
                key.add("বছর"); //bochor
                key.add("বয়সী");//boyoshi
	}

//return the string age between begI & endI from news
	/****************************** checking middle *****************************/
	private String getAge(String news, int begI, int endI)
	{
		String age="";
		for(int J=begI; J<endI; J++)
		{
			char ch = news.charAt(J);
			if( ch>='\u09E6' && ch<='\u09EF')	//bangla 0 to 9
				age+=ch;
		}
		return age;
	}
        
	/********************************* checking front ******************************/
	private String getAge(String news, int begI)
	{
		StringBuffer sb = new StringBuffer("");
		for(int J=begI; J>=0; J--)
		{
			char ch = news.charAt(J);
			if( ch>='\u09E6' && ch<='\u09EF')	//bangla 0 to 9
				sb.insert(0,ch);
		}
		String age = new String(sb);
		return age;
	}

/*********************** return all the ages of given file ******************************/
	public void ageFinder(WordData wd, Object allKey[]) 
	{
		BanglaNumbersFinder bnf = new BanglaNumbersFinder(allKey);		//bnf.banglaNumbers
		WordAllFinder waf = new WordAllFinder(wd, bnf.banglaNumbers);

	//finding ages from compare key ArrayList 
		String start="";
		String stop="";
		int I=0;

		while( I<8 )	// used 8 for to get 4 pair of start & stop key of age detection
		{
			start = key.get(I);
			I++;
			stop = key.get(I);
			I++;
                        //System.out.println(I+" -> "+start+" "+stop);
                        
			int J=-1;
			for(String news : waf.newsLinesWordAll)
			{
				J++;
				int begI = 0;
				int endI = 0;
				StringManipulation sm = new StringManipulation();
				while( (begI=sm.stringMatching(news, start, endI))>0 && (endI=sm.stringMatching(news, stop, begI))>0 )	//starting from 0 index
				{
					//System.out.println(I+" => "+begI+"_"+endI);
					String age = "";
					if( I<=4 )		//if age exists at middle
					{       
                                                //System.out.println("<"+waf.fileNamesWordAll.get(J)+">");
						age=getAge(news, begI, endI-1);
					}
					else
					{
						System.out.println("<"+waf.fileNamesWordAll.get(J)+">");
						//System.out.println("-> "+begI+"_"+endI);
						age=getAge(news, begI);
					}

					if( age.length()>0)		//if it is valid
					{
                                            
						ages.add(age);
						newsLinesAges.add(news);
						fileNamesAges.add(waf.fileNamesWordAll.get(J));
					}
				}
			}
		}
                waf.fileNamesWordAll = fileNamesAges;
                uniqueFileNamesAges = waf.uniqueFileName();
	}
}