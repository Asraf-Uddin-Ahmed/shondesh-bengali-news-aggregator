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
		/*String rootUrl = "http://www.prothom-alo.com/";		//("http://jsoup.org/"); //("http://www.prothom-alo.com/"); //("http://www.w1.com/");
		//new MakeUrlListDataFile( rootUrl );

		//new UrlToHtmlFile( "http://www.prothom-alo.com/detail/date/2011-08-22/news/180189", "2011-08-22.html");
		PaperResolve pr = new PaperResolve( rootUrl, "2011-08-22");
		new UrlToTextFile("http://www.prothom-alo.com/detail/date/2011-08-22/news/180189", "2011-08-22", pr);
		*/

    //read all files
    	/*WordData wd = new WordData();

    	ReadingNewses rn = new ReadingNewses(wd, 4);	//change 4. give the value of total text file that created
		System.out.println("reading completed.");


	//find word
		FileToArrayList ftal = new FileToArrayList();
		ArrayList<String> words = new ArrayList<String>(ftal.getString("victim.words"));

		PrintWriter pw = new PrintWriter("out.txt", "UTF8");
		WordAllFinder waf = new WordAllFinder(wd, words);
		pw.println(waf.wordAll);
		pw.println(waf.fileNamesWordAll);
		pw.println(waf.newsLinesWordAll);

*/
		Object allKey[] = wd.getAllKeySet().toArray();	//make all existed word array

		AgeDetector ad = new AgeDetector();
		ad.ageFinder(wd, allKey);
		pw.println(ad.ages);
		pw.println(ad.fileNamesAges);
		pw.println(ad.newsLinesAges);

		pw.close();


    }
}