import java.net.URL;

import java.util.regex.*;
import java.util.*;
import java.io.*;
import java.text.*;
import java.sql.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class wordCount
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
		Map<String, Integer> wordCounter = new TreeMap<String, Integer>();

		String text = "";
		try{
			String doc = fd.getFileString("summary.txt");
			String words[] = doc.split("[\u09F7\u0964\u2018\u2019?|.: ,?!;'\"(){}<>_+-/%\r\n\t]");
			System.out.println(words.length);

			//map insert
			for(String word : words )
				if( word.length()>1 ){
					if( wordCounter.containsKey(word)==true ){
						int count = wordCounter.get(word);
						wordCounter.put(word, count+1);
					}
					else{
						wordCounter.put(word, 1);
						//text += (word+"\r\n");
					}
				}

			//map normalize
			/*wordCounter.put("", 0);
			Object ob[] = wordCounter.keySet().toArray();
			String norm[] = new String[ob.length];
			for(int I=0; I<ob.length; I++){
				norm[I] = ob[I].toString();
			}
			for(int I=0; I<norm.length; I++){
				//fd.makeFileUtf("res.txt", norm[I]);
				if( norm[I].length()>1 ){
					int J=I+1;
					while( J<norm.length && norm[J].matches(".*"+norm[I]+"*.")==true ){
						//System.out.println(I+"_"+J);
						//fd.makeFileUtf("res"+I+"_"+J+".txt", norm[I]+" "+norm[J]);
						int count = wordCounter.get(norm[I]);
						wordCounter.put(norm[I], count+wordCounter.get(norm[J]));
						wordCounter.remove(norm[J]);
						norm[J] = norm[0];
						//System.out.println("=>"+len);
						J++;
					}
				}
			}*/

			//map print
			Iterator it = wordCounter.keySet().iterator();
			while( it.hasNext() ){
				String key = it.next().toString();
				if( isNoun(key)==true )
					text += (key+"\t"+wordCounter.get(key)+"\r\n");
			}
			fd.makeFileUtf("res.txt", text);
		}catch(IOException ioe){
			System.out.println(ioe);
		}
		System.out.println(wordCounter.size());
    }
}