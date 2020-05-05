/*
retrieve the history of all words in given words array list if exists
called by Main
*/

import java.util.ArrayList;

class WordAllFinder
{
	ArrayList<String> wordAll = new ArrayList<String>();
	ArrayList<Integer> fileNamesWordAll = new ArrayList<Integer>();
	ArrayList<String> newsLinesWordAll = new ArrayList<String>();

	public WordAllFinder(WordData wd, ArrayList<String> words)
	{
		for(String word : words)
		{
			WordFinder wf = new WordFinder(wd, word);
			try
			{
				int size = wf.fileNamesWord.size();
				fileNamesWordAll.addAll(wf.fileNamesWord);
				newsLinesWordAll.addAll(wf.newsLinesWord);
				for(int I = size; I>0; I--)
					wordAll.add(word);

			}
			catch(NullPointerException npe)
			{
				System.out.println(word+" does not found !!");
			}
		}

	}
}