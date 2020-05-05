/*
find which number contains bangla number at first
called by AgeDetctor
*/

import java.util.ArrayList;

class BanglaNumbersFinder
{
	ArrayList<String> banglaNumbers = new ArrayList<String>();

	public BanglaNumbersFinder(Object allKey[])
	{
		StringManipulation sm = new StringManipulation();

		for(int I=allKey.length-1; I>=0; I--)
		{
			String word = (String)allKey[I];
			if( sm.isBanglaNumber(word) )
			{
				banglaNumbers.add(word);
			}
		}

	}
}