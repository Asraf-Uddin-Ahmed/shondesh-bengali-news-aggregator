package Shondesh;

/*
use FileSetNewsSet
storing the history of unique word of documents
called by Main
*/

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

public class WordData
{
//class variable
	private HashMap<String,FileSetNewsSet> wordHistory = new HashMap<String,FileSetNewsSet>();	//key=string, value=object of FileSetNewsSet

	ArrayList<String> newsLines = new ArrayList<String>();		//store the news lines of seraching word
	ArrayList<Integer> fileNames = new ArrayList<Integer>();	//store the file names of seraching word(each index contains the file name of corresponding news line)

//inserting all the words of a document
	public void insertWordOfDocument(String doc, int fileName)
	{
		FileSetNewsSet fsns;

		String sentences[] = doc.split("[\u09F7\u0964\u2018\u2019.?!]");	//utf8 value of dari, uddhorn chinho for spliting bangla line

		for(String sentence : sentences)
		{
			String words[] = sentence.split("[. ,?!;'\"(){}<>_+-/%\n]");
			for(String word : words)
			{
				//System.out.println(word);
				fsns = new FileSetNewsSet(fileName, sentence, wordHistory.get(word));
				wordHistory.put(word,fsns);
			}
		}
	}

//geting the news lines and file names for searching word
	public void retrieveNewsLines(String word)
	{
		FileSetNewsSet fsns = wordHistory.get(word);	//get the object of word

		int fileName;
		int I=0;								//for tracking the index of current text from fsns.newsSet
		for(String text : fsns.newsSet )		//text contains a string with various lines from fsns.newsSet index by index
		{
			fileName = fsns.fileSet.get(I);		//I index of fileSet contains file name
			String lines[] = text.split("\n");	//spliting for FileSetNewsSet made string including '\n' atlast
			for(String line : lines)
			{
				//System.out.println("=>"+line);
				fileNames.add( fileName );
				newsLines.add(line);
			}
			I++;

		}

	}

//get all the keys stored
	public Set getAllKeySet()
	{
		return wordHistory.keySet();
	}

	public void clear()
	{
		newsLines.clear();
		fileNames.clear();
	}
}