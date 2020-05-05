import java.net.URL;

import java.util.*;
import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkExtractor {

	ArrayList<String> UrlStore = new ArrayList<String>();

    public LinkExtractor ( String FileName ) throws IOException
    {
		String html="";

		Scanner fin = new Scanner(new FileReader(FileName));

	//making stirng of html file
		while( fin.hasNextLine() )
			html+=fin.nextLine();

	//making document for parsing
		Document doc = Jsoup.parse(html);

	//finding tag <a> & value of href
		Elements links = doc.select("a[href]");
		for (Element link : links)
		{
			if( link.attr("abs:href")!="" )
		    	//System.out.println(link.attr("abs:href"));	//href parse all url & abs:href parse all url with http or malito
		    	UrlStore.add(link.attr("abs:href"));			//collect
    	}
    }

}