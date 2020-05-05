/*
called by UrlToTextFile
*/

import java.io.*;
import org.jsoup.Jsoup;

import java.util.Scanner;

public class HtmlFileToText
{
	//String banglaText="";
	String news="";
	String headline="";

	public HtmlFileToText( String FileName, PaperResolve pr )
	{
		try
		{
			char ch;
			int I;
			File f = new File( FileName );
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF8"));
			String line = "";
			String str;
			while( (str=br.readLine())!=null )
				line+=str;

			StringManipulation sm = new StringManipulation();
		//headline
			I = sm.stringMatching(line, pr.headlineTextKey, 0);
			for(I++; (ch=line.charAt(I))!=pr.headlineStopChar; I++)
				headline+=ch;
		//news
			I = sm.stringMatching(line, pr.newsTextKey, 0);
			for(I++; (ch=line.charAt(I))!=pr.newsStopChar; I++)
				news+=ch;

		}
		catch( IOException ioe )
		{
			System.out.println(FileName+" not found !! \n"+ioe);
		}
	}
}