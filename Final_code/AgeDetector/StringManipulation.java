/*
return next index of matched pattern
called by HtmlFileToText
called by BanglaNumbersFinder
called by AgeDetector
*/

public class StringManipulation
{
	public int stringMatching(String strRoot, String strFind, int startIndex)
	{
		for(int I=startIndex; I<strRoot.length(); I++)
		{
			if( strRoot.regionMatches(false, I, strFind, 0, strFind.length())==true )
			{
				//System.out.println(I+strFind.length());
				return I+strFind.length();
			}
		}
		return 0;
	}

//checking number is bangla or not
	public boolean isBanglaNumber(String word)
	{
		if( word==null )
			return false;
		if( word.length()==0 )
			return false;
		char ch = word.charAt(0);
		return ( ch>='\u09E6' && ch<='\u09EF' );
	}

}