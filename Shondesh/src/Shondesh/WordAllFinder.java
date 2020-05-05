package Shondesh;

/*
retrieve the history of all words in given words array list if exists
called by Main
*/

import java.util.*;

class WordAllFinder
{
	ArrayList<String> wordAll = new ArrayList<String>();
	ArrayList<String> fileNamesWordAll = new ArrayList<String>();
	ArrayList<String> newsLinesWordAll = new ArrayList<String>();

/*****************constructor for all words*********************/
	public WordAllFinder(WordData wd, ArrayList<String> words)
	{
		for(String word : words)
		{
			WordFinder wf = new WordFinder(wd, word);
			try
			{
				newsLinesWordAll.addAll(wf.newsLinesWord);
				int size = wf.fileNamesWord.size();
				for(int I=0; I<size; I++)
                                        fileNamesWordAll.add (String.valueOf(wf.fileNamesWord.get(I)));
                                for(int I = size; I>0; I--)
					wordAll.add(word);

			}
			catch(NullPointerException npe)
			{
				System.out.println(word+" does not found !!");
			}
		}

	}

/*****************constructor for a word*********************/
	public WordAllFinder(WordData wd, String word)
	{
                WordFinder wf = new WordFinder(wd, word);
                try
                {
                        newsLinesWordAll.addAll(wf.newsLinesWord);
                        int size = wf.fileNamesWord.size();
                        for(int I=0; I<size; I++)
                                fileNamesWordAll.add (String.valueOf(wf.fileNamesWord.get(I)));
                        for(int I = size; I>0; I--)
                                wordAll.add(word);

                }
                catch(NullPointerException npe)
                {
                        System.out.println(word+" does not found !!");
                }

	}

/************************return unique file name***************************/
        public Vector uniqueFileName(){
            Vector unique = new Vector();
            for(int I=0; I<fileNamesWordAll.size(); I++){
                String name = fileNamesWordAll.get(I);
                if( unique.contains(name)==false )
                    unique.add(name);
                //System.out.println("-> "+name);
            }
            return unique;
        }
        

}