package Shondesh;

/*
reading all files
store all of words & its history to WordData via FileSetNewsSet
called by Main
*/

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.*;

class ReadingNewses
{
	public ReadingNewses(WordData wd, String destPath, int maxFile)
	{
            FileDirectory fd = new FileDirectory();
            int countFile = 0;
		for(int I=0; I<100000&&countFile<maxFile;I++)   //I<100000 for breaking infinite loop 
		{
			System.out.println("RN ->"+I+"_"+maxFile);
			String str="";
			String newses="";
                        String headline = "";
			String headFileName = destPath + "\\" + String.valueOf(I) + ".head";
                        String newsFileName = destPath + "\\" + String.valueOf(I) + ".news";
                        
			try
			{
                            //HEADLINE
                                 headline = fd.getFileString(headFileName);
				//System.out.println(headline);
				wd.insertWordOfDocument(headline,I);
                           //NEWS
				newses = fd.getFileString(newsFileName);
				//System.out.println(newses);
				wd.insertWordOfDocument(newses,I);
                                countFile++;
			}
			catch(IOException ioe)
			{
				System.out.println("RN -> file "+I+" not found !!");
                                //break;
			}
		}
	}
}