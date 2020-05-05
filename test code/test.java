import java.net.URL;

import java.sql.*;
import java.util.regex.*;
import java.util.*;
import java.io.*;
import java.text.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class test
{
	static boolean isNoun(String word){
		JavaDataBaseConnector jdbc = new JavaDataBaseConnector();
		jdbc.getConnect("shondesh", "123");
		ResultSet rs = jdbc.findStatement("select * from bangladict where word like '%"+word+"%' and preposition not like 'noun';");
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
		System.out.println(isNoun("ratul"));
    }
}