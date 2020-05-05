import java.net.URL;

import java.util.regex.*;
import java.util.*;
import java.io.*;
import java.text.*;
import java.sql.*;


public class SummariseDocument2
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
		int sentenceValue[] = null;
		Map<String, ArrayList<Integer> > wordValue = new HashMap<String, ArrayList<Integer> >();
		Set<Integer> occurance = new TreeSet<Integer>();
		String wordText = null;
		String text = null;

		try{
			String doc = fd.getFileString("summary.txt");
			String sentences[] = doc.split("[\u09F7\u0964\u2018\u2019.?!:|]");	//utf8 value of dari, uddhorn chinho for spliting bangla line

			//map init
			sentenceValue = new int[sentences.length];
			for(int I=0; I<sentences.length; I++){
				sentenceValue[I] = 0;
				String words[]  = sentences[I].split("[ ,;'\"(){}<>_+-/%`~\r\n\t]");
				for(String word : words){
					ArrayList<Integer> a = new ArrayList<Integer>();
					if( wordValue.containsKey(word)==true )
						a = wordValue.get(word);
					a.add(I);
					wordValue.put(word, a);
				}
			}
			System.out.println("total sentences = "+sentenceValue.length);
			System.out.println("total words = "+wordValue.size());

			//frequency count
			wordText = "";
			Iterator it = wordValue.keySet().iterator();
			while( it.hasNext() ){
				String key = it.next().toString();
				if( key.length()>1 && isNoun(key)==true ){
					ArrayList<Integer> a = new ArrayList<Integer>();
					a = wordValue.get(key);
					for(int I : a)
						sentenceValue[I] += (a.size()-1);
					wordText += (key+"\t"+wordValue.get(key)+"\r\n");
				}
			}
			for(int I=0; I<sentenceValue.length; I++)
				occurance.add(sentenceValue[I]);
			System.out.println(occurance);
			System.out.println("value size = "+occurance.size());
			fd.makeFileUtf("words.txt", wordText);

			//summary value
			it = occurance.iterator();
			int size = occurance.size();
			int summaryLines = sentences.length/10+1;	//chk it
			while( size>summaryLines ){
				it.next();
				size--;
			}
			int value = Integer.parseInt(it.next().toString());
			System.out.println("value = "+value);

			//summary
			text = "";
			for(int I=0; I<sentences.length; I++)
				if( sentenceValue[I]>=value )
					text += (sentenceValue[I]+"\t"+sentences[I]+"|\r\n");
			fd.makeFileUtf("summary2.txt", text);

		}catch(IOException ioe){
			System.out.println(ioe);
		}

    }
}