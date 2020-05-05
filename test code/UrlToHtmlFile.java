/*
called by UrlToTextFile
*/

import java.io.*;
import java.io.PrintWriter;
import java.util.Scanner;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UrlToHtmlFile
{
	public UrlToHtmlFile(  String url, String FileName )
	{
		try
		{
			URL my_url = new URL(url);
			String str = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(my_url.openStream(),"UTF8"));
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileName), "UTF8"));
			while( (str=br.readLine())!=null )
			{
				out.write(str);
			}
			out.close();


		}
		catch (IOException ex)
		{
			System.out.println("\""+url+"\" not found !!");
        }
	}
}