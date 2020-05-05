package Shondesh;

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
			int I,start,end;
			File f = new File( FileName );
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF8"));
			String line = "";
			String str;
			while( (str=br.readLine())!=null )
				line+=str;

			StringManipulation sm = new StringManipulation();
		//headline
			start = sm.stringMatching(line, pr.headlineTextKey, 0);
			end = sm.stringMatching(line, pr.headlineStopKey, start) - pr.headlineStopKey.length() - 1;
			//System.out.println(start+" "+end);
			for(I=start; I<=end; I++)
				headline += line.charAt(I);
		//news
			if( pr.paperName.equals("prothom-alo") ){
				start = sm.stringMatching(line, pr.newsTextKey, 0);
				end = sm.stringMatching(line, pr.newsStopKey, start) - pr.newsStopKey.length() - 1;

				for(I=start; I<=end; I++){
					ch=line.charAt(I);
					if( ch!='b' && ch!='r' && ch!='<' && ch!='>' && ch!='/' )	//utf8 bangla range ch>=2433 && ch<=2555
						news += ch;
				}
			}
			else if( pr.paperName.equals("dailynayadiganta") ){
				start = sm.stringMatching(line, pr.newsTextKey, 0);
				end = sm.stringMatching(line, pr.newsStopKey, start);
				start = sm.stringMatching(line, "<p>", end);
				end = sm.stringMatching(line, pr.newsStopKey, start) - pr.newsStopKey.length() - 1;

				for(I=start; I<=end; I++){
					ch=line.charAt(I);
					if( ch!='b' && ch!='r' && ch!='<' && ch!='>' && ch!='/' )	//utf8 bangla range ch>=2433 && ch<=2555
						news += ch;
				}
			}

			//System.out.println();
			/*Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileName+".txt"), "UTF-8"));
				out.write(headline+" * "+news);
			out.close();*/

		}
		catch( IOException ioe )
		{
			System.out.println(FileName+" not found !! \n"+ioe);
		}
	}
}