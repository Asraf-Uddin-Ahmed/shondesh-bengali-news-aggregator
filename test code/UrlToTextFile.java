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
		new UrlToHtmlFile( url, FileName+".html" );
		HtmlFileToText hftt = new HtmlFileToText( FileName+".html", pr );
		text = hftt.headline + " # " + hftt.news;

		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileName+".txt"), "UTF-8"));
		out.write(text);
		out.close();
	}
}