/*
reading all files
store all of words & its history to WordData via FileSetNewsSet
called by Main
*/

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

class ReadingNewses
{
	public ReadingNewses(WordData wd, int totalFile)
	{
		for(int I=0; I<totalFile;I++)
		{
			System.out.println(I);
			String str="";
			String newses="";
			String fileName = String.valueOf(I)+".txt";

			try
			{
				BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(fileName), "UTF8"));
				while( (str=br.readLine())!=null )
					newses += str;
				//System.out.println(newses);
				wd.insertWordOfDocument(newses,I);
			}
			catch(IOException ioe)
			{
				System.out.println("file "+I+" not found !!");
			}
		}
	}
}