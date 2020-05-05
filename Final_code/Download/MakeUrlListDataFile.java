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
	//generating today's date. date willtaken from user or UI;
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String userDate = sdf.format(today);

		PaperResolve pr = new PaperResolve( rootUrl, userDate );
		//System.out.println( pr.PaperName );
		//System.out.println( pr.PageKey );
		//System.out.println( pr.NewsKey );

		MakeSubUrlList msul = new MakeSubUrlList (rootUrl, pr);
		System.out.println( msul.UrlListPage.size() );
		System.out.println( msul.UrlListNews.size() );

		String destPath = pr.path+"\\"+pr.paperName+"\\"+pr.date;
		FileDirectory fd = new FileDirectory();
		fd.makeDirs(new File(destPath));

	//************************************************creating file with full of page's url
		for(String str : msul.UrlListPage )
			fd.makeFile(new File(destPath+"\\page"), str, true);


	//************************************************creating file with full of news's url
		for(String str : msul.UrlListNews )
			fd.makeFile(new File(destPath+"\\news"), str, true);

	//******************************creating file with full of news text. ACTIVE it for all. determine the total news file
		for(int I=0; I<msul.UrlListNews.size(); I++)
			new UrlToTextFile( msul.UrlListNews.get(I) , String.valueOf(I), pr );

	//******************************inactive it. for downloading all acitve up portion.
		//for(int I=0; I<10; I++)
		//	new UrlToTextFile( msul.UrlListNews.get(I) , String.valueOf(I), pr );

    }
}