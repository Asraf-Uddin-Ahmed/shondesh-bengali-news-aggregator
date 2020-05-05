/*
called by MakeUrlListDataFile(String rootUrl) [temporarily named Main]
*/

import java.net.URL;

import java.util.*;
import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MakeSubUrlList
{
	ArrayList<String> UrlListNews = new ArrayList<String>();		//contains all valid url which is only & only sub-url & news of root-url
	ArrayList<String> UrlListPage = new ArrayList<String>();	//contains all url which are in root-url & sub-url & page of the paper

    public MakeSubUrlList(String RootUrl, PaperResolve pr)
    {
		System.out.print("loading");

		ArrayList<String> UrlListTemp = new ArrayList<String>();	//contains all url which are in root-url & sub-url

		StringManipulation sm = new StringManipulation();
		UrlToUrl utu = new UrlToUrl( RootUrl );
		UrlListTemp.addAll( utu.UrlStore );
		//System.out.println( UrlListTemp.size() );

		for(String str : UrlListTemp )
		{
			//if( str.regionMatches(false, 0, pr.PageKey, 0, pr.PageKey.length())==true )
			if( sm.stringMatching(str, pr.pageKey)>0 && UrlListPage.contains(str)==false )
			{
				//System.out.println( str );
				UrlListPage.add( str );
			}
		}

		//for(String str1 : UrlListPage )
		for(int I=0; I<UrlListPage.size() ;I++)
		{
			String str1 = UrlListPage.get(I);
			UrlListTemp.clear();
			utu = new UrlToUrl(str1);
			UrlListTemp.addAll( utu.UrlStore );

			for(String str2 : UrlListTemp )
			{
				//if( str2.regionMatches(false, 0, pr.NewsKey, 0, pr.NewsKey.length())==true && UrlListNews.contains(str2)==false )
				if( sm.stringMatching(str2, pr.newsKey)>0 && UrlListNews.contains(str2)==false )
				{
					//System.out.println(str2);
					UrlListNews.add(str2);
					System.out.print(".");
				}
				else if( sm.stringMatching(str2, pr.pageKey)>0 && UrlListPage.contains(str2)==false )
				{
					//System.out.println(str2);
					UrlListPage.add(str2);
					System.out.print(".");
				}
			}

		}

    }
}