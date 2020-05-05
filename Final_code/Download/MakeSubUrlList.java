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

/*******************simple bfs************************/
	private void bfs(String RootUrl, PaperResolve pr){

		ArrayList<String> UrlListTemp = new ArrayList<String>();	//contains all url which are in root-url & sub-url

		StringManipulation sm = new StringManipulation();
		UrlToUrl utu = new UrlToUrl( RootUrl );
		UrlListTemp.addAll( utu.UrlStore );
		//System.out.println( UrlListTemp.size() );

		for(String str : UrlListTemp )
		{
			if( sm.stringMatching(str, pr.pageKey, 0)>0 && UrlListPage.contains(str)==false )
			{
				//System.out.println( str );
				UrlListPage.add( str );
			}
		}

		for(int I=0; I<UrlListPage.size() ;I++)
		{
			String str1 = UrlListPage.get(I);
			UrlListTemp.clear();
			utu = new UrlToUrl(str1);
			UrlListTemp.addAll( utu.UrlStore );

			for(String str2 : UrlListTemp )
			{
				if( sm.stringMatching(str2, pr.newsKey, 0)>0 && UrlListNews.contains(str2)==false )
				{
					//System.out.println(str2);
					UrlListNews.add(str2);
					System.out.print(".");
				}
				else if( sm.stringMatching(str2, pr.pageKey, 0)>0 && UrlListPage.contains(str2)==false )
				{
					//System.out.println(str2);
					UrlListPage.add(str2);
					System.out.print(".");
				}
			}

		}
	}

/*******************first generating 101 of url via pr.pageKey. then bfs***************************/
	private void greedyBfs(String RootUrl, PaperResolve pr){

		ArrayList<String> UrlListTemp = new ArrayList<String>();	//contains all url which are in root-url & sub-url

		StringManipulation sm = new StringManipulation();
		UrlToUrl utu = new UrlToUrl( RootUrl );
		UrlListTemp.addAll( utu.UrlStore );
		//System.out.println( UrlListTemp.size() );

		for(int I=0; I<=100; I++)
			UrlListPage.add(pr.pageKey+String.valueOf(I));

		for(int I=0; I<UrlListPage.size() ;I++)
		{
			//System.out.println("< "+I+" >");
			String str1 = UrlListPage.get(I);
			UrlListTemp.clear();
			utu = new UrlToUrl(str1);
			UrlListTemp.addAll( utu.UrlStore );

			for(String str2 : UrlListTemp )
			{
				if( sm.stringMatching(str2, pr.newsKey, 0)>0 && UrlListNews.contains(str2)==false )
				{
					//System.out.println(str2);
					UrlListNews.add(str2);
					System.out.print(".");
				}
				else if( sm.stringMatching(str2, pr.pageKey, 0)>0 && UrlListPage.contains(str2)==false )
				{
					//System.out.println(str2);
					UrlListPage.add(str2);
					System.out.print(".");
				}
			}

		}
	}

    public MakeSubUrlList(String RootUrl, PaperResolve pr)
    {
		System.out.print("loading");

		if( pr.paperName.equals("prothom-alo") ){
			greedyBfs(RootUrl, pr);
		}
		else if( pr.paperName.equals("dailynayadiganta") ){
			bfs(RootUrl, pr);
		}
    }
}