package Shondesh;

/*
word -> existent file names -> news lines of each file
called by WordData
*/

import java.util.ArrayList;


class FileSetNewsSet
{
	ArrayList<Integer> fileSet = new ArrayList<Integer>();	//storing unique file name
	ArrayList<String> newsSet = new ArrayList<String>();	//storing newses

	public FileSetNewsSet(int fileName, String news, FileSetNewsSet prevFsns)
	{
	//assigning previous value of fileSet & newsSet
		if( prevFsns!=null )
		{
			fileSet = prevFsns.fileSet;
			newsSet = prevFsns.newsSet;
		}
	//inserting fileName uniquely & news
		if( prevFsns==null || fileSet.contains(fileName)==false )	//if file name exists
		{
		//inserting new
			fileSet.add(fileName);
			newsSet.add(news+"\n");
		}
		else
		{
		//inserting with previous
			int I = fileSet.indexOf(fileName);
			newsSet.set(I, newsSet.get(I)+news+"\n");
		}

	}
}