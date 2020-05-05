package Shondesh;

/*
UrlToHtmlFile + HtmlFileToText = UrlToTextFile
called by MakeUrlListDataFile(String rootUrl) [temporarily named Main]
*/

import java.io.*;
import java.io.IOException;
//import sun.jdbc.odbc.JdbcOdbc;

public class UrlToTextFile
{
	String text;

	public UrlToTextFile( String url, String FileName, PaperResolve pr ) throws IOException
	{
		String filePath = pr.path+"\\"+pr.paperName+"\\"+pr.date+"\\"+FileName;
		new UrlToHtmlFile( url, filePath+".html" );

		HtmlFileToText hftt = new HtmlFileToText( filePath+".html", pr );
		
                FileDirectory fd = new FileDirectory();
                fd.makeFileUtf(filePath+".link", url);
                fd.makeFileUtf(filePath+".head", hftt.headline);
                fd.makeFileUtf(filePath+".news", hftt.news);
                
	}
}