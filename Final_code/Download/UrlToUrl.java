/*
called by MakeSubUrlList
*/

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UrlToUrl
{
	ArrayList<String> UrlStore = new ArrayList<String>();

	public UrlToUrl( String url )
	{
		Document doc=null;
		try
		{
			//making document for parsing
			doc = Jsoup.connect(url).get();

			//finding tag <a> & value of href
			Elements links = doc.select("a[href]");

			// href ...
			for (Element link : links)
			{
				System.out.println("-> "+link.attr("abs:href"));
				UrlStore.add(link.attr("abs:href"));	//href parse all url or abs:href parse all url with http
			}
		}
	//if net not connected
		catch(IOException ioe)
		{
			System.out.println("Internet not connected...\nPlease take connect.");
			//System.out.println("\n\""+url+"\" not found !!\n"+ioe);
		}

	}

}