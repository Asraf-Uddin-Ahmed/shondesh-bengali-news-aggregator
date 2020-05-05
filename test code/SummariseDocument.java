import java.net.URL;

import java.util.regex.*;
import java.util.*;
import java.io.*;
import java.text.*;
import java.sql.*;


public class SummariseDocument
{
	static boolean isNoun(String word){
		JavaDataBaseConnector jdbc = new JavaDataBaseConnector();
		jdbc.getConnect("shondesh", "123");
		ResultSet rs = jdbc.findStatement("select * from bangladict where meaning like '%"+word+"%' and preposition not like 'noun';");
		try{
			//System.out.println();
			return rs.first()==false;
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
		//return jdbc.getTableElement(rs,4).isEmpty();
	}

    public static void main(String args[]){
		FileDirectory fd = new FileDirectory();
		Map<String, Integer> sentenceValue = new LinkedHashMap<String, Integer>();
		Set<Integer> occurance = new TreeSet<Integer>();
		String wordText = "";
		String text = "";
		try{
			String doc = fd.getFileString("init.txt");
			String sentences[] = doc.split("[\u09F7\u0964\u2018\u2019.?!:|]");	//utf8 value of dari, uddhorn chinho for spliting bangla line

			//map init
			for(String sentence : sentences)
				sentenceValue.put(sentence.trim(), 0);
			System.out.println("total sentences = "+sentenceValue.size());

			//frequency count
			StringManipulation sm = new StringManipulation();
			for(String sentence : sentences)
			{
				String words[] = sentence.split("[ ,;'\"(){}<>_+-/%`~\r\n\t]");
				for(String word : words)
				{
				/*
				word length differ from here. use only NOUN for counting. NOUN filtering.
				*/
					if( word.length()>1 ){
					//if( word.length()>1 && isNoun(word)==true ){
						wordText +=(word+"\r\n");
						Iterator it = sentenceValue.keySet().iterator();
						while( it.hasNext() ){
							String key = it.next().toString();
							if( key.equals(sentence)==false && sm.stringMatching(key, word, 0)>0 ){
								int count = sentenceValue.get(key);
								sentenceValue.put(key, count+1);
							}
						}
					}
				}
			}
			fd.makeFileUtf("words.txt", wordText);

			//map print
			Iterator it = sentenceValue.keySet().iterator();
			while( it.hasNext() ){
				String key = it.next().toString();
				text += (sentenceValue.get(key)+"\t"+key+"\r\n");
				occurance.add(sentenceValue.get(key));
			}
			System.out.println(occurance);
			fd.makeFileUtf("sen.txt", text);

			//summary value
			it = occurance.iterator();
			int size = occurance.size();
		/*
		approx summary lines. also use HEADLINE as summary line.
		*/
			int summaryLines = sentenceValue.size()/10;
			while( size>summaryLines ){
				it.next();
				size--;
			}
			int value = Integer.parseInt(it.next().toString());
			System.out.println("value = "+value);

			//summary print
			text = "";
			it = sentenceValue.keySet().iterator();
			while( it.hasNext() ){
				String key = it.next().toString();
				if( sentenceValue.get(key)>=value )
					text += (sentenceValue.get(key)+"\t"+key+" |\r\n");
			}
			fd.makeFileUtf("summary.txt", text);

		}catch(IOException ioe){
			System.out.println(ioe);
		}

    }
}