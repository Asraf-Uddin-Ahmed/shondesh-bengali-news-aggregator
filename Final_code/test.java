import java.net.URL;

import java.util.*;
import java.io.*;
import java.text.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class test
{

    public static void main(String[] args) throws IOException
    {
		String RootUrl = "http://www.prothom-alo.com/";		//("http://jsoup.org/"); //("http://www.prothom-alo.com/"); //("http://www.w1.com/");

		UrlToUrl utu = new UrlToUrl( RootUrl );

		for(String str : utu.UrlStore )
		{
			System.out.println(str);
		}

    }
}