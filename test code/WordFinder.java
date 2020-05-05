/*
retrieve the history of a word if exists
called by WordAllFinder
*/

import java.util.ArrayList;

class WordFinder
{
	ArrayList<Integer> fileNamesWord;
	ArrayList<String> newsLinesWord;

	public WordFinder(WordData wd, String word)
	{
		try
		{
			wd.clear();
			wd.retrieveNewsLines(word);

			System.out.println(word+" exists.");

			fileNamesWord = new ArrayList<Integer>(wd.fileNames);
			newsLinesWord = new ArrayList<String>(wd.newsLines);

		}
		catch(NullPointerException npe)
		{
			System.out.println(word+" does not exist !!");
		}

	}
}