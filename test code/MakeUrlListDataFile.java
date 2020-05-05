import java.net.URL;

import java.util.*;
import java.io.*;
import java.text.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MakeUrlListDataFile
{

    public MakeUrlListDataFile(String rootUrl) throws IOException
    {
	//generating today's date
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PaperResolve pr = new PaperResolve( rootUrl, sdf.format(today) );
		//System.out.println( pr.PaperName );
		//System.out.println( pr.PageKey );
		//System.out.println( pr.NewsKey );

		MakeSubUrlList msul = new MakeSubUrlList (rootUrl, pr);
		System.out.println( msul.UrlListPage.size() );
		System.out.println( msul.UrlListNews.size() );

		PrintWriter pw;

	//creating file with full of page's url
		pw = new PrintWriter( pr.paperName+"_page" );
		for(String str : msul.UrlListPage )
		{
			//System.out.println(str);
			pw.println(str);
		}
		pw.close();

	//creating file with full of news's url
		pw = new PrintWriter( pr.paperName+"_news" );
		for(String str : msul.UrlListNews )
		{
			//System.out.println(str);
			pw.println(str);
		}
		pw.close();

	//creating file with full of news text
	//active it for all. determine the total news file
		//for(int I=0; I<msul.UrlListNews.size(); I++)
		//	new UrlToTextFile( msul.UrlListNews.get(I) , String.valueOf(I), pr );

	//inactive it
		new UrlToTextFile( msul.UrlListNews.get(0) , String.valueOf(0), pr );

    }
}