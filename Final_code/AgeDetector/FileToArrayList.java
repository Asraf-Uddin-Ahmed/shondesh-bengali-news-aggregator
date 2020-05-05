/*
storing each line of file into array list
called by Main
*/

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.IOException;
import java.util.ArrayList;

class FileToArrayList
{
	public ArrayList<String> getString(String inFile)
	{
		ArrayList<String> words = new ArrayList<String>();
		try
		{
			String str;
			BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(inFile), "UTF8"));
			while( (str=br.readLine())!=null )
				words.add(str);
		}
		catch(IOException ioe)
		{
			System.out.println(inFile+" does not found !!");
		}
		return words;
	}

}