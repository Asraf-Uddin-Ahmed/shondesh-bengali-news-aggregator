/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Shondesh;

import java.util.*;

/**
 *
 * @author RATUL
 */
public class DataBaseForFilter {

    String dbName = null;
    String password = null;
    
    /*************** CONSTRUCTOR *******************/
    public DataBaseForFilter(String dbname, String pass) {
        dbName = dbname;
        password = pass;
    }
    
    /**/
    void initCrime(){
        String crimeKey[] = {"\u0996\u09C1\u09A8", "\u09B9\u09A4\u09CD\u09AF\u09BE", "\u09A7\u09B0\u09CD\u09B7\u09A3",
                             "\u09AE\u09BE\u09AE\u09B2\u09BE", "\u099B\u09BF\u09A8\u09A4\u09BE\u0987", "\u09AE\u09BE\u09A6\u0995",
                             "\u09AA\u0995\u09C7\u099F\u09AE\u09BE\u09B0", "\u09AA\u09C1\u09B2\u09BF\u09B6", "\u099A\u09CB\u09B0", 
                             "\u09A1\u09BE\u0995\u09BE\u09A4", "\u0997\u09CB\u09DF\u09C7\u09A8\u09CD\u09A6\u09BE", 
                             "\u09AE\u09BE\u09B0\u09BE\u09AE\u09BE\u09B0\u09BF", "\u09B9\u09BE\u09A8\u09BE\u09B9\u09BE\u09A8\u09BF", 
                             "\u09B8\u0982\u0998\u09B0\u09CD\u09B7", "\u09B8\u0982\u0998\u09BE\u09A4", "\u09AC\u09BE\u09A6\u09C0",
                             "\u09B0\u09BE\u09B9\u09BE\u099C\u09BE\u09A8\u09BF", "\u0997\u09CD\u09B0\u09C7\u09AB\u09A4\u09BE\u09B0",
                             "\u09AA\u09BF\u099F\u09C1\u09A8\u09BF", "\u0986\u09B8\u09BE\u09AE\u09C0",
                             "\u0986\u09A8\u09B8\u09BE\u09B0", "\u0986\u09B8\u09BE\u09AE\u09BF", "র‍্যাব",
                             "\u0985\u09AA\u09B0\u09BE\u09A7\u09C0", "\u09B8\u09A8\u09CD\u09A4\u09CD\u09B0\u09BE\u09B8\u09C0", 
                             "\u09AC\u09BF\u09AC\u09BE\u09A6\u09C0", "\u0989\u0995\u09BF\u09B2", "\u09AE\u09BE\u09A4\u09BE\u09B2",
                             "\u0997\u09CD\u09B0\u09C7\u09AA\u09CD\u09A4\u09BE\u09B0", "\u0985\u09AA\u09C1\u09B9\u09B0\u09A3",
                             "\u0997\u09CD\u09B0\u09C7\u09AA\u09A4\u09BE\u09B0", "\u0987\u09AD\u099F\u09BF\u099C\u09BF\u0982",
                             "\u0985\u09B8\u09CD\u09A4\u09CD\u09B0", "\u09AC\u09BF\u099C\u09BF\u09AC\u09BF",
                             "\u09B8\u09CD\u09AC\u09B0\u09BE\u09B7\u09CD\u099F\u09CD\u09B0\u09AE\u09A8\u09CD\u09A4\u09CD\u09B0\u09C0"};
        for(int I=0; I<crimeKey.length; I++)
            addKey("crime", crimeKey[I]);
        
    }
    
    /**/
    void initSports(){
        String sportsKey[] = {"\u0996\u09C7\u09B2\u09BE", "\u09AB\u09C1\u099F\u09AC\u09B2", "\u099F\u09C7\u09A8\u09BF\u09B8",
                          "\u0995\u09CD\u09B0\u09BF\u0995\u09C7\u099F", "\u0995\u09BE\u09AC\u09BE\u09A1\u09BF",
                          "\u09AC\u09CD\u09AF\u09BE\u09A1\u09AE\u09BF\u09A8\u09CD\u099F\u09A8", "\u0997\u09B2\u09AB", 
                          "\u09AE\u09CD\u09AF\u09BE\u099A", "\u09AC\u09BE\u09B8\u09CD\u0995\u09C7\u099F\u09AC\u09B2", 
                          "\u09B9\u09CD\u09AF\u09BE\u09A8\u09CD\u09A1\u09AC\u09B2", "\u09AD\u09B2\u09BF\u09AC\u09B2",
                          "\u09B9\u0995\u09BF", "\u09A6\u09CC\u09DC", "\u09B9\u09BE\u0987\u099C\u09BE\u09AE\u09CD\u09AA", 
                          "\u09B2\u0982\u099C\u09BE\u09AE\u09CD\u09AA", "\u099C\u09BE\u09AE\u09CD\u09AA", "\u09AC\u09B2", 
                          "\u0997\u09CB\u09B2", "\u0986\u0989\u099F", "\u09B8\u09CD\u09AA\u09CD\u09B0\u09BF\u09A8\u09CD\u099F",
                          "\u09B0\u09C7\u09B8", "\u09B0\u09C7\u09B8\u09BF\u0982", "\u0996\u09C7\u09B2\u09BE\u09A7\u09C1\u09B2\u09BE"};
        for(int I=0; I<sportsKey.length; I++)
            addKey("sports", sportsKey[I]);
    
    }
    
    /**/
    void initEconomics(){
        String economicsKey[] = {"\u0985\u09B0\u09CD\u09A5\u09A8\u09C0\u09A4\u09BF", "\u0985\u09B0\u09CD\u09A5", "\u098B\u09A3",
                             "\u09B6\u09C7\u09DF\u09BE\u09B0", "\u09AC\u09BE\u099C\u09BE\u09B0", "\u099F\u09BE\u0995\u09BE",
                             "\u09AE\u09C1\u09A6\u09CD\u09B0\u09BE", "\u09B2\u09C7\u09A8\u09A6\u09C7\u09A8",
                             "\u09AE\u09C1\u09A6\u09CD\u09B0\u09BE\u09B8\u09CD\u09AB\u09C0\u09A4\u09BF", "\u09B8\u09C1\u09A6" ,
                             "\u09AC\u09CD\u09AF\u09BE\u0982\u0995", "\u09AE\u09C2\u09B2\u09CD\u09AF", "\u0986\u09DF\u0995\u09B0"};
        for(int I=0; I<economicsKey.length; I++)
            addKey("economics", economicsKey[I]);
    
    }
    
    /**/
    void initPolitics(){
        String politicsKey[] = {"\u09B9\u09B0\u09A4\u09BE\u09B2", "\u0986\u09A8\u09CD\u09A6\u09CB\u09B2\u09A8", 
                            "\u09AE\u09BF\u099B\u09BF\u09B2", "\u09AE\u09BF\u099F\u09BF\u0982", "\u09AD\u09BE\u09B7\u09A3",
                            "\u09B8\u09CD\u09B2\u09CB\u0997\u09BE\u09A8", "\u0995\u09B0\u09CD\u09AE\u09B8\u09C2\u099A\u09C0", 
                            "\u0985\u09AC\u09B0\u09CB\u09A7", "\u09B8\u0982\u0997\u09CD\u09B0\u09BE\u09AE", 
                            "\u09B2\u0982\u09AE\u09BE\u09B0\u09CD\u099A", "\u09B0\u09CB\u09A1\u09AE\u09CD\u09AF\u09BE\u09AA",
                            "\u09AE\u09BE\u09A8\u09AC\u09AC\u09A8\u09CD\u09A7\u09A8", "\u09B8\u09AD\u09BE",
                            "\u09AA\u09CD\u09B0\u09A4\u09BF\u09AC\u09BE\u09A6", "\u09B8\u09AE\u09BE\u09AC\u09C7\u09B6", 
                            "\u09AA\u09BE\u09B0\u09CD\u09B2\u09BE\u09AE\u09C7\u09A8\u09CD\u099F", "\u09B8\u0982\u09B8\u09A6",
                            "\u09AC\u09BF\u09B0\u09CB\u09A7\u09C0\u09A6\u09B2", "\u09B8\u09B0\u0995\u09BE\u09B0", 
                            "\u0985\u09A7\u09BF\u09AC\u09C7\u09B6\u09A8", "\u09AE\u09A8\u09CD\u09A4\u09CD\u09B0\u09C0",
                            "\u09B8\u09CD\u09AC\u09B0\u09BE\u09B7\u09CD\u099F\u09CD\u09B0\u09AE\u09A8\u09CD\u09A4\u09CD\u09B0\u09C0", 
                            "\u0986\u0987\u09A8\u09AE\u09A8\u09CD\u09A4\u09CD\u09B0\u09C0", 
                            "\u09B0\u09BE\u09B7\u09CD\u099F\u09CD\u09B0\u09AA\u09A4\u09BF", 
                            "\u09AA\u09CD\u09B0\u09A7\u09BE\u09A8\u09AE\u09A8\u09CD\u09A4\u09CD\u09B0\u09C0"};
        for(int I=0; I<politicsKey.length; I++)
            addKey("politics", politicsKey[I]);
    
    }
    
    /**/
    void initLocal(){
        String localKey[] = {"কক্সবাজার", "কুমিল্লা", "কুঁড়িগ্রাম", "কুষ্টিয়া", "কিশোরগন্জ", "খাগড়াছড়ি", "খুলনা", "গাইবান্ধা",
                             "গাজীপুর", "গোপালগঞ্জ", "চাঁদপুর", "চট্টগ্রাম", "চুয়াডাঙ্গা", "জয়পুরহাট", "জামালপুর", "ঝিনাইদহ", 
                             "ঝালকাঠি", "দিনাজপুর", "নওগাঁ", "নাটোর", "নড়াইল", "নারায়নগঞ্জ", "নরসিংদী", "নওয়াবগঞ্জ", 
                             "নেত্রকোনা", "নীলফামারী", "নোয়াখালী", "টাঙ্গাইল", "ঠাকুরগাঁও", "ঢাকা", "পাবনা", "পঞ্চগড়", 
                             "রাঙামাটি", "পটুয়াখালী", "পিরোজপুর", "ফরিদপুর", "ফেনী", "বাগেরহাট", "বগুড়া", "বান্দরবান", "বরিশাল", 
                             "বরগুনা", "ব্রাহ্মণবাড়িয়া", "ভোলা", "মাদারীপুর", "মাগুরা", "মানিকগঞ্জ", "মেহেরপুর", "মৌলভীবাজার", 
                             "মুন্সীগঞ্জ", "ময়মনসিংহ", "যশোহর", "রাজবাড়ী", "রাজশাহী", "রংপুর", "লক্ষীপুর", "লালমনিরহাট", 
                             "শরীয়তপুর", "শেরপুর", "সাতক্ষীরা", "সিরাজগঞ্জ", "সুনামগঞ্জ", "সিলেট", "হবিগঞ্জ"};
        for(int I=0; I<localKey.length; I++)
            addKey("local", localKey[I]);
    
    }
    
    /*********** initialize database for filtering ***********/
    void initializeDB(){
        //creating tables
        String dbTables[] = {"crime", "sports", "local", "politics", "economics", "user"};
        JavaDataBaseConnector jdbc = new JavaDataBaseConnector();
        jdbc.getConnect(dbName, password);
        for(int I=0; I<dbTables.length; I++)
            jdbc.createTable("CREATE TABLE "+dbTables[I]+"(keyword varchar(100) PRIMARY KEY)ENGINE=InnoDB DEFAULT CHARSET=utf8");
        jdbc.connenctionClose();
    
        //initialize crime
        initCrime();
        //initialize sports
        initSports();
        //initialize economics
        initEconomics();
        //initialize politics
        initPolitics();
        //initialize local
        initLocal();
        
    }
    
    /****************** adding keyword into table ************************/
    void addKey(String tableName, String key){
        JavaDataBaseConnector jdbc = new JavaDataBaseConnector();
        jdbc.getConnect(dbName, password);
        jdbc.insertTable("INSERT INTO "+tableName+" values( CONVERT('"+key+"' USING utf8) )");
        jdbc.connenctionClose();
    }
    
    /******************* getting keywords from table **************************/
    Vector getKeysVector(String tableName){
        JavaDataBaseConnector jdbc = new JavaDataBaseConnector();
        jdbc.getConnect(dbName, password);
        Vector keys = jdbc.getTableElement(jdbc.findStatement("SELECT * from "+tableName), 1);
        jdbc.connenctionClose();
        return keys;
    }
    
    /***************** transform Vector into ArrayList of pure keyword without any garbage character **************/
    ArrayList<String> getKeysArrayList(String tableName){
        Vector v = getKeysVector(tableName);
        ArrayList<String> keys = new ArrayList<String>(); 
        for(int I=0; I<v.size(); I++){
            String str = v.get(I).toString();
            String key = str.substring(1, str.length()-1);
            keys.add(key);
        }
        return keys;
    }
    
    /****************** adding keyword into table ************************/
    void deleteKey(String tableName, String key){
        JavaDataBaseConnector jdbc = new JavaDataBaseConnector();
        jdbc.getConnect(dbName, password);
        jdbc.insertTable("DELETE FROM "+tableName+" where keyword='"+key+"'");
        jdbc.connenctionClose();
    }
    
}
