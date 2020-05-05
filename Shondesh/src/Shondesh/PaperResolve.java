package Shondesh;

/*
called by MakeUrlListDataFile(String rootUrl) [temporarily named Main]
*/


public class PaperResolve
{
	String paperName = "";

	String pageKey = "";		//unique format of url for page
	String newsKey = "";		//unique format of url for news

	String newsTextKey = "";	//unique format of string for news to begin parsing
	String newsStopKey = "";	//unique format of string for news to stop parsing

	String headlineTextKey = "";    //unique format of string for healine to begin parsing
	String headlineStopKey = "";    //unique format of string for headline to stop parsing

	String date = "";               //date which given by user
        
        String dbTable = "";
        
	String path = "C:\\Shondesh";	//path of storing temporarily *.html file

	public PaperResolve( String RootUrl, String userDate )		// 'yyyy-MM-dd' : default date format for prothom_alo. change it for other paper.
	{
		char ch;
		int start = 0;

		date = userDate;
		start = 11;
		for(int I=start; (ch=RootUrl.charAt(I))!='.'; I++)
			paperName+=ch;
		System.out.println(paperName);

	//load it from database...
		if( paperName.equals("prothom-alo") )
		{
			pageKey = "http://www.prothom-alo.com/section/date/"+date+"/category/";	//concat 0 to 100 for taking approximate page url
			newsKey = "http://www.prothom-alo.com/detail/date/"+date+"/news/";
			newsTextKey = "<p id=\"content\" style=\"font-size:18px;line-height:24px;\">";
			newsStopKey = "</p>";
			headlineTextKey = "<h2 class=\"title mb10\">";
			headlineStopKey = "</h2>";
                        dbTable = "p_alo_";
		}
		else if( paperName.equals("dailynayadiganta") )
		{
			pageKey = "http://www.dailynayadiganta.com/category/";
			newsKey = "http://www.dailynayadiganta.com/details/";
			newsTextKey = "<p class=\"mb10 db\">";
			newsStopKey = "</p>";
			headlineTextKey = "<title>";
			headlineStopKey = "::";
                        dbTable = "n_dig_";
		}

		//System.out.println(PageKey);
		//System.out.println(NewsKey);

	}
}