import java.net.URL;

import java.util.*;
import java.io.*;
import java.text.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main
{

    public static void main(String[] args) throws IOException
    {
		/* "http://www.prothom-alo.com/";
		   "http://www.dailynayadiganta.com/";
		*/String rootUrl = "http://www.dailynayadiganta.com/";		//("http://www.w1.com/");//("http://jsoup.org/");
		new MakeUrlListDataFile( rootUrl );

		//new UrlToHtmlFile( "http://www.prothom-alo.com/detail/date/2011-08-22/news/180189", "2011-08-22.html");
		//PaperResolve pr = new PaperResolve( rootUrl, "2012-02-12");
		//new UrlToTextFile("http://www.prothom-alo.com/detail/date/2011-08-22/news/180189", "2011-08-22", pr);
		//for(int I=0; I<10; I++)
			//new HtmlFileToText(String.valueOf(I), pr);

    }
}