/*
called by MakeUrlListDataFile(String rootUrl) [temporarily named Main]
*/


public class PaperResolve
{
	String paperName = "";

	String pageKey = "";
	String newsKey = "";

	String newsTextKey = "";
	char newsStopChar = ' ';

	String headlineTextKey = "";
	char headlineStopChar = ' ';

	public PaperResolve( String RootUrl, String date )		// 'yyyy-MM-dd' : default date format. change it for other paper.
	{
		char ch;

		for(int I=11; (ch=RootUrl.charAt(I))!='.'; I++)
			paperName+=ch;
		//System.out.println(PaperName);

	//load it from database...
		if( paperName.equals("prothom-alo") )
		{
			pageKey = "http://www.prothom-alo.com/section/date/"+date+"/category/";
			newsKey = "http://www.prothom-alo.com/detail/date/"+date+"/news/";
			newsTextKey = "<meta property=\"og:description\" content=";
			newsStopChar = '\"';
			headlineTextKey = "<title>";
			headlineStopChar = '-';
		}

		//System.out.println(PageKey);
		//System.out.println(NewsKey);

	}
}