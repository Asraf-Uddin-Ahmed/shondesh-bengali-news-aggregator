/*
UrlToHtmlFile + HtmlFileToText = UrlToTextFile
called by MakeUrlListDataFile(String rootUrl) [temporarily named Main]
*/

import java.io.*;
import java.io.IOException;

public class UrlToTextFile
{
	String text;

	public UrlToTextFile( String url, String FileName, PaperResolve pr ) throws IOException
	{
		String filePath = pr.path+"\\"+pr.paperName+"\\"+pr.date+"\\"+FileName;
		new UrlToHtmlFile( url, filePath+".html" );

		HtmlFileToText hftt = new HtmlFileToText( filePath+".html", pr );
		text = hftt.headline + " # " + hftt.news;
		//storing database
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath+".txt"), "UTF-8"));
		out.write(text);
		out.close();
	}
}