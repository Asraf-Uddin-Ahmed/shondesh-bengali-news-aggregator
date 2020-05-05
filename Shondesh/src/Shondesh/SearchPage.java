/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SearchPage.java
 *
 * Created on Feb 10, 2012, 12:02:16 AM
 */
package Shondesh;

import java.io.*;
import java.util.*;

import java.awt.print.PrinterException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
/**
 *
 * @author RATUL
 */
public class SearchPage extends javax.swing.JFrame implements Runnable {

    /******************declare variable***********************/
    boolean comboRecent = false;
    boolean needRead = true;
    boolean advanceSearch = false;
    boolean autoSearch = true;
    String userDate = null;
    String  recentPaperName =null ;
    String ages = null;
    String filePath = null;
    String link = null;
    String mainPath = "C:\\Shondesh";
    String dbName = "Shondesh";
    String password = null;
    WordData wd = null;
    Vector fileNameSearch = new Vector();   //contain complete searched & printable file names 
    Vector fileNameFilter = new Vector();   //contain filtering file names for advance search
    Vector headlineFilter = new Vector();   //headlines are showing in top
    Vector headlinePrint = new Vector();    //headlines are showing in side
    Vector newsPrint = new Vector();        //news print in text area
    ArrayList<String> keyFilter = new ArrayList<String>();  //contain keyword for filter
    ArrayList<String> newsLinks = new ArrayList<String>();  //contain link of each news
    Thread t = null;                        //thread for clock
    
    /*********************** showing combo data for this year ************************/
    private void showThisComboData(int maxDay){
        FindToday fd = new FindToday();
        int maxMonth = Integer.parseInt(fd.thisMonth());
        int maxYear = Integer.parseInt(fd.thisYear());
        jComboBox1.removeAllItems();
        jComboBox2.removeAllItems();
        jComboBox3.removeAllItems();
        for(int I=1; I<=maxDay; I++)
            jComboBox1.addItem(String.valueOf(I));
        for(int I=1; I<=maxMonth; I++)
            jComboBox2.addItem(String.valueOf(I));
        for(int I=2006; I<=maxYear; I++)
            jComboBox3.addItem(String.valueOf(I));
    }
    
    /***********************showing initial data of combo 1,2,3**************************/
    private void showInitialComboData(){
        FindToday fd = new FindToday();
        showThisComboData(Integer.parseInt(fd.thisDate()));
        jComboBox1.setSelectedItem(fd.thisDate());
        jComboBox2.setSelectedItem(fd.thisMonth());
        jComboBox3.setSelectedItem(fd.thisYear());
    }
    
    /**************************showing recent data of combo 1,2,3****************************/
    private void showRecentComboData(){
        FindToday fd = new FindToday();
        String day = null;
        String month = null;
        String year = null;
        int userMonth = 0;
        int userYear = 0;
        try{
            day = jComboBox1.getSelectedItem().toString();
            month = jComboBox2.getSelectedItem().toString();
            year = jComboBox3.getSelectedItem().toString();
            userMonth = Integer.parseInt(month);
            userYear = Integer.parseInt(year);
        }catch(NullPointerException npe){
            System.out.println("SP -> showing combo date error.");
            return;
        }
        
        //checking with system date for finding this year & month
        int systemMonth = Integer.parseInt(fd.thisMonth());
        int systemYear = Integer.parseInt(fd.thisYear());
        System.out.println(userMonth+" "+"_"+userYear+" "+systemYear);
        if( userYear==systemYear ){
            int maxDay = 0;
            //if this year & month
            if( userMonth==systemMonth )
                maxDay = Integer.parseInt(fd.thisDate());
            else
                maxDay = fd.maxDay(userMonth, userYear);
            showThisComboData(maxDay);
            jComboBox1.setSelectedItem(day);
            jComboBox2.setSelectedItem(month);
            jComboBox3.setSelectedItem(year);
            return;
        }
        
        //if not this year
        int maxDay = fd.maxDay(userMonth, userYear);    
        int maxMonth = 12;
        jComboBox1.removeAllItems();
        jComboBox2.removeAllItems();
        for(int I=1; I<=maxDay; I++)
            jComboBox1.addItem(String.valueOf(I));
        for(int I=1; I<=maxMonth; I++)
            jComboBox2.addItem(String.valueOf(I));
        jComboBox1.setSelectedItem(day);
        jComboBox2.setSelectedItem(month);
        jComboBox3.setSelectedItem(year);
        //System.out.println(year+month+day);
    }

    
    /************************return the recent date which selected by user***********************/
    void getUserDate(){
        if( jComboBox1.getSelectedItem()!=null && jComboBox2.getSelectedItem()!=null && jComboBox3.getSelectedItem()!=null ){
            String year = jComboBox3.getSelectedItem().toString();
            String month = jComboBox2.getSelectedItem().toString();
            String day = jComboBox1.getSelectedItem().toString();
            if( month.length()==1 )
                month = "0"+month;
            if( day.length()==1 )
                day = "0"+day;
            userDate = year+"-"+month+"-"+day;
            System.out.println(userDate);
        }
        //return userDate;
    }
    
    /*******************************getting preparation for printing searched elements************************/
    void printPreparation(){
        //clearing & initializing    
        StringManipulation sm = new StringManipulation();
        FileDirectory fd = new FileDirectory();
        headlineFilter.clear();
        headlinePrint.clear();
        newsPrint.clear();
        newsLinks.clear();
        
        //if search not found
        if( fileNameSearch.isEmpty()==true ){
            return;
        }
        
        //for seperator of headlines list
        int maxSizeHeadline = 0;
        
        //storing searched element
        for(int I=0; I<fileNameSearch.size(); I++){
            String fileName = fileNameSearch.get(I).toString();
            try{
                //storing headline
                String headline = fd.getFileString(filePath+"\\"+fileName+".head");
                headlineFilter.add(headline);
                //apni kono shiro-nam select koren ni
                headlineFilter.add("\u0986\u09AA\u09A8\u09BF \u0995\u09CB\u09A8 \u09B6\u09BF\u09B0\u09CB\u09A8\u09BE\u09AE \u09B8\u09BF\u09B2\u09C7\u0995\u09CD\u099F \u0995\u09B0\u09C7\u09A8\u09A8\u09BF...");
                headlinePrint.add(sm.getBanglaNumber(I+1) + "| " + headline);
                //headlinePrint.add(fileName+". "+ headline);
                //headlinePrint.add("");
                if( headline.length()>maxSizeHeadline )
                    maxSizeHeadline = headline.length();
                
                //storing news
                String news = fd.getFileString(filePath+"\\"+fileName+".news");
                newsPrint.add(news);
                //apni kono shiro-nam select koren ni
                newsPrint.add("\u0986\u09AA\u09A8\u09BF \u0995\u09CB\u09A8 \u09B6\u09BF\u09B0\u09CB\u09A8\u09BE\u09AE \u09B8\u09BF\u09B2\u09C7\u0995\u09CD\u099F \u0995\u09B0\u09C7\u09A8\u09A8\u09BF...");
                
                //storing new link
                String link = fd.getFileString(filePath+"\\"+fileName+".link");
                newsLinks.add(link);
                newsLinks.add(null);
            }
            catch(IOException ioe){}
        }
        String separator = "";
        for(int I=0; I<maxSizeHeadline+10; I++)
            separator += '-';
        for(int I=1; I<headlinePrint.size(); I+=2)
            headlinePrint.insertElementAt(separator, I);

    }
    
    /*************************** print headlines **************************/
    void printHeadlines(){
        StringManipulation sm = new StringManipulation();
        if( headlinePrint.isEmpty()==true ){
            //System.out.println("SP_printHeadLines -> not found "+headlinePrint.size());
            jList1.setListData(new Vector());
            jTextField2.setText("0 \u099F\u09BF \u09B8\u0982\u09AC\u09BE\u09A6 \u09AA\u09BE\u0993\u09DF\u09BE \u0997\u09C7\u099B\u09C7");
            if( new FileDirectory().isExist(new File(filePath+"\\news"))==true )
                jTextArea1.setText("\u09A6\u09C1\u0983\u0996\u09BF\u09A4\n\u0995\u09BE\u0999\u09CD\u0995\u09CD\u09B7\u09BF\u09A4 \u0995\u09CB\u09A8 \u09B8\u0982\u09AC\u09BE\u09A6 \u09AA\u09BE\u0993\u09DF\u09BE \u09AF\u09BE\u09DF\u09A8\u09BF...");
            else
                jTextArea1.setText("দুঃখিত...\nকাঙ্ক্ষিত দিনের পত্রিকা আপনি ডাউনলোড করেননি ।");
        }
        else{
            //System.out.println("SP_printHeadLines -> found "+headlinePrint.size());
            jTextField2.setText("\u09B6\u09BF\u09B0\u09CB\u09A8\u09BE\u09AE");
            jTextArea1.setText(sm.getBanglaNumber(fileNameSearch.size()) + " \u099F\u09BF \u09B8\u0982\u09AC\u09BE\u09A6 \u09AA\u09BE\u0993\u09DF\u09BE \u0997\u09C7\u099B\u09C7");
            jList1.setListData(headlinePrint);
        }
        
    }
    
    /*************************** assigning filePath ***********************************/
    boolean getFilePath(){
        FileDirectory fd = new FileDirectory();
        filePath = mainPath + "\\" + recentPaperName;
        try{
            if( recentPaperName.equals("user")==false )
                filePath = filePath + "\\" + userDate;
        }catch(NullPointerException npe){
            return false;
        }
        return true;
    }
    
    /************************** get news File number ************************************/
    //call getFilePath() first. then this...
    int getMaxFile(){
        int maxFile = 0;
        FileDirectory fd = new FileDirectory(); 
        StringManipulation sm = new StringManipulation();
        try{
            String fileNames[] = fd.getDirectoryChildName(new File(filePath));
            for(String fileName : fileNames){
                if( sm.stringMatching(fileName, ".head", 0)>0 )
                    maxFile++;
            }
        }catch(NullPointerException npe){}
        return maxFile;
    }
    
    /************************* read if needed *******************************/
    void readIfNeed(){
        int maxFile = getMaxFile();
        FileDirectory fd = new FileDirectory();
        if( needRead==true && fd.isExist(new File(filePath))==true ){
            needRead = false;
            wd = new WordData();
            System.out.println(filePath+" = "+maxFile);
            ReadingNewses rn = new ReadingNewses(wd, filePath, maxFile);	//change 4. give the value of total text file that created
            System.out.println(filePath+" reading completed.");
            //Object allKey[] = wd.getAllKeySet().toArray();	//make all existed word array
        }
    }
    
    /********************************* SEARCH , FILTER & PRINT *******************************************/
    void search(){
        //create object and initial variables
        StringManipulation sm = new StringManipulation();
        
        //READING
        //System.out.println("SP_search() ->"+getFilePath());
        if( getFilePath()==false )
            return ;
        //System.out.println("SP_search() ->"+filePath);
        readIfNeed();
        
        //string parisng & store.
        String userInput = "";
        if( jTextField1.getText().equals("Search here...")==false )
            userInput = jTextField1.getText();
        
        //for value of Advance Search
        if( advanceSearch==true ){
            if( userInput.isEmpty()==false )
                userInput +=" ";
            userInput += ages;
        }
        //System.out.println(ages+"###"+userInput);
        
        //is AND operation need
        boolean andOperation = false;
        if( sm.stringMatching(userInput, "&", 0)>0 )
            andOperation = true;
        ArrayList<String> words = sm.parsingString(userInput, "[ ,.(){}<>~`!@#$%^&*_+=-?/\"':;|]");
        //System.out.println(userInput.length()+"_"+userDate+"_"+words.size());
        
        //SEARCHING.........
        if( andOperation==true ){
            AndingSearchOperation aso = new AndingSearchOperation();
            fileNameSearch.clear();
            fileNameSearch = aso.searchOperation(wd, words);
        }
        else{
            WordAllFinder waf = new WordAllFinder(wd, words);
            //System.out.println("*"+waf.fileNamesWordAll.size());
            fileNameSearch.clear();
            fileNameSearch = waf.uniqueFileName();
        }
        //System.out.println("**"+fileNameSearch.size());
        //for(int I=0; I<fileNameSearch.size(); I++)System.out.print(" "+fileNameSearch.get(I));System.out.println();
        //for(int I=0; I<keyFilter.size(); I++)System.out.print(" "+keyFilter.get(I));System.out.println();
        
        //FILTERING
        Vector forFilter = null;
        if( keyFilter.isEmpty()==false ){
            WordAllFinder wafFilter = new WordAllFinder(wd, keyFilter);
            forFilter = wafFilter.uniqueFileName();
            //System.out.println("->"+forFilter.size());
        }
        
        //FILTER & SEARCH
        if( forFilter!=null ){
            AndingSearchOperation asoFS = new AndingSearchOperation();
            fileNameSearch = asoFS.andingFileName(forFilter, fileNameSearch);
        }
        
        //ADVANCE SEARCH
        if( advanceSearch==true ){
            AndingSearchOperation asoFS = new AndingSearchOperation();
            fileNameSearch = asoFS.andingFileName(fileNameFilter, fileNameSearch);
        }
        //System.out.println("***"+fileNameSearch.size());
        
        System.out.println("SP_search() => "+filePath);
        printPreparation();
        //PRINT headlines
        //if search not found
        printHeadlines();
        System.out.println("list printed");

    }
    
    /************************ FILTER , SEARCH & SHOW *********************************/
    void filterSearch(String filterName){
        DataBaseForFilter dbff = new DataBaseForFilter(dbName, password);
        keyFilter.clear();
        keyFilter = dbff.getKeysArrayList(filterName);
        System.out.println("***** filterSearch() *****");
        search();
    }
    
    /************************ Advance Search ***************************/
    void doAdvanceSearch(){
        if(advanceSearch==true){
            StringManipulation sm =new StringManipulation();     
            int start = Integer.parseInt(jComboBox4.getSelectedItem().toString());
            int end = Integer.parseInt(jComboBox5.getSelectedItem().toString());
            ages = "";
            for(int I=start; I<=end; I++){
                //System.out.print(" "+sm.getBanglaNumber(I));
                ages += (sm.getBanglaNumber(I)+" ");
            }
            //System.out.println(" => "+keyFilter.size());
            System.out.println("***** doAdvanceSearch() *****");
            search();
        }
    }
    
    /************************ closing message. need confirmation to EXIT *******************************************/
    void closingWindow(){
        int result = JOptionPane.showConfirmDialog(rootPane, "Do you want to close it?");
        if( result==0 ){
            t.stop();
            //this.dispose();   //ACTIVE when all processes are demolished
            System.exit(1);     //ACTIVE when any processes are not demolished
        }
    }
    
    /************************* searching a string in internet with default browser *****************************/
    void searchWithBrowser(){
        String searchEngine = jComboBox8.getSelectedItem().toString();
        String searchFor = jTextField3.getText().replace(' ', '+');
        String link = null;
        if( searchEngine.equals("yahoo")==true )
            link = "http://search."+searchEngine+".com/search?p="+searchFor;
        else
            link = "http://www."+searchEngine+".com/search?q="+searchFor;
        OpenLinkWithBrowser olwb = new OpenLinkWithBrowser();
        olwb.goToLink(link);
    }
    
/**************************************************************** Creates new form SearchPage ****************************************************************/
   
    public SearchPage() {
        setExtendedState(SearchPage.MAXIMIZED_BOTH);  //maximzaing current window
        initComponents();
        //jLabel5.setText("ratul");
        
        //Combo box
        jComboBox4.setEnabled(false);
        jComboBox5.setEnabled(false);
        for(int I=1; I<=100; I++)
            jComboBox4.addItem(String.valueOf(I));
        for(int I=1; I<=100; I++)
            jComboBox5.addItem(String.valueOf(I));
        showInitialComboData();
        comboRecent = true;
        getUserDate();
        recentPaperName = "prothom-alo";
        FileDirectory fd = new FileDirectory();
        try{
            password = fd.getFileString(mainPath+"\\info\\mysql.pass");
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Password initializing error.\nPlease restart it.", "Password Not Found", JOptionPane.ERROR_MESSAGE);
        }
        t = new Thread(this);
        t.start();
    }

    /******************************************** run() for thread *********************************************/
    public void run(){
        while(true){
            FindToday ft = new FindToday();
            jLabel5.setText(ft.getCurrentTime());
            jLabel9.setText(ft.thisDay());
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        jComboBox7 = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jComboBox8 = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        jMenuItem17 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Shondesh");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jList1.setFont(new java.awt.Font("SolaimanLipi", 2, 14));
        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "শিরোনামঃ" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setRequestFocusEnabled(false);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jTextField1.setFont(new java.awt.Font("Vrinda", 0, 13)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField1.setText("Search here...");
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 10));
        jButton1.setText("Search");
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel1.setText("Filter by:");

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel2.setText("Day:");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Month:");

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Year:");

        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setFont(new java.awt.Font("SolaimanLipi", 0, 14));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("বিস্তারিত....");
        jTextArea1.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1058, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
        );

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel6.setText("Newspaper:");

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jCheckBox1.setText("Advance search");
        jCheckBox1.setFocusPainted(false);
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        jLabel7.setText("Age:");

        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel8.setText("to");

        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("SolaimanLipi", 1, 18));
        jTextField2.setText("শিরোনামঃ");
        jTextField2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 255, 51), null));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 1045, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Time");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Day");

        jComboBox6.setFont(new java.awt.Font("Tahoma", 2, 14));
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Crime", "Economics", "Local", "Politics", "Sports", "User" }));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        jComboBox7.setFont(new java.awt.Font("SolaimanLipi", 1, 16)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "প্রথম-আলো", "নয়া-দিগন্ত", "User" }));
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jButton2.setText("Go To Main News");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField3.setFont(new java.awt.Font("SolaimanLipi", 0, 12)); // NOI18N
        jTextField3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField3MouseClicked(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "google", "yahoo", "bing" }));

        jLabel10.setText("Search with");

        jButton3.setText("GO");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jMenu1.setText("Window");

        jMenuItem1.setText("New window");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem1MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem16.setText("Home");
        jMenuItem16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem16MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem16);
        jMenu1.add(jSeparator9);

        jMenuItem2.setText("Exit");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem2MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu8.setText("File");

        jMenuItem13.setText("Add");
        jMenuItem13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem13MousePressed(evt);
            }
        });
        jMenu8.add(jMenuItem13);

        jMenuItem14.setText("Delete");
        jMenuItem14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem14MousePressed(evt);
            }
        });
        jMenu8.add(jMenuItem14);

        jMenuBar1.add(jMenu8);

        jMenu2.setText("News");

        jMenuItem3.setText("Save...");
        jMenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem3MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Print...");
        jMenuItem4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem4MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem4);
        jMenu2.add(jSeparator10);

        jMenuItem17.setText("Go To Main News");
        jMenu2.add(jMenuItem17);
        jMenu2.add(jSeparator7);

        jMenuItem15.setText("Download");
        jMenuItem15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem15MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem15);

        jMenuBar1.add(jMenu2);

        jMenu6.setText("Settings");

        jMenu7.setText("Customize Filtering");

        jMenuItem7.setText("Crime");
        jMenuItem7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem7MousePressed(evt);
            }
        });
        jMenu7.add(jMenuItem7);

        jMenuItem8.setText("Sports");
        jMenuItem8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem8MousePressed(evt);
            }
        });
        jMenu7.add(jMenuItem8);

        jMenuItem9.setText("Local");
        jMenuItem9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem9MousePressed(evt);
            }
        });
        jMenu7.add(jMenuItem9);

        jMenuItem10.setText("Politics");
        jMenuItem10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem10MousePressed(evt);
            }
        });
        jMenu7.add(jMenuItem10);

        jMenuItem11.setText("Economics");
        jMenuItem11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem11MousePressed(evt);
            }
        });
        jMenu7.add(jMenuItem11);

        jMenuItem12.setText("User");
        jMenuItem12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem12MousePressed(evt);
            }
        });
        jMenu7.add(jMenuItem12);

        jMenu6.add(jMenu7);
        jMenu6.add(jSeparator8);

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Auto Search");
        jCheckBoxMenuItem1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxMenuItem1ItemStateChanged(evt);
            }
        });
        jMenu6.add(jCheckBoxMenuItem1);

        jMenuBar1.add(jMenu6);

        jMenu3.setText("Help");

        jMenuItem5.setText("User manual");
        jMenu3.add(jMenuItem5);

        jMenuItem6.setText("About us");
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 1328, Short.MAX_VALUE)
            .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 1328, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1328, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(45, 45, 45)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel6)
                .addGap(41, 41, 41)
                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCheckBox1))
                .addGap(144, 144, 144))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1037, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 687, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))))
                .addGap(11, 11, 11)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel9))
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    //play sound
    Sound s = new Sound();
    s.play("C:/Windows/Media/ARROW.WAV");
    System.out.println("***** Search button *****");
    search();
}//GEN-LAST:event_jButton1ActionPerformed

private void jMenuItem1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MousePressed
    new SearchPage().setVisible(true);
}//GEN-LAST:event_jMenuItem1MousePressed

private void jMenuItem2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MousePressed
    closingWindow();
}//GEN-LAST:event_jMenuItem2MousePressed

private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
    advanceSearch = (jCheckBox1.getSelectedObjects()!=null);
    jComboBox4.setEnabled(advanceSearch);
    jComboBox5.setEnabled(advanceSearch);
    
    jComboBox6.setEnabled(!advanceSearch);
    
    if( advanceSearch==true ){
        readIfNeed();
        Object allKey[];
        try{
            allKey = wd.getAllKeySet().toArray();  //make all existed word array
        }catch(NullPointerException npe){
            return;
        }
        AgeDetector ad = new AgeDetector();
        ad.ageFinder(wd, allKey);
        
        keyFilter.clear();
        fileNameFilter.clear();
        fileNameFilter = ad.uniqueFileNamesAges;
        System.out.println("=>"+fileNameFilter.size());
        //for(int I=0; I<fileNameFilter.size(); I++)System.out.print(" "+fileNameFilter.get(I));System.out.println();
        //for(int I=0; I<ad.fileNamesAges.size(); I++)System.out.print(" "+ad.fileNamesAges.get(I));System.out.println();
        doAdvanceSearch();
    }
}//GEN-LAST:event_jCheckBox1ItemStateChanged

private void jMenuItem4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem4MousePressed
    try {
        boolean print =jTextArea1.print();
    } 
    catch (PrinterException ex) {
        Logger.getLogger(SearchPage.class.getName()).log(Level.SEVERE, null, ex);
    }    
}//GEN-LAST:event_jMenuItem4MousePressed

private void jMenuItem3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem3MousePressed
    
    JFileChooser jfc = new JFileChooser();
    
    //file filtering for save dialogue box. only show the *.txt files.
    FileFilter filter = new FileNameExtensionFilter("Text file", "txt");
    jfc.addChoosableFileFilter(filter);
    int ret = jfc.showSaveDialog(null);

    //file saving button clicked.
    if (ret == JFileChooser.APPROVE_OPTION) 
    {
        try {
            //file saving with existing file for replace.
            if ( jfc.getSelectedFile().getName().contains(".txt") ){
                int replace = JOptionPane.showConfirmDialog(rootPane,"Do you want to replace existing file?");
                //yes button action perform
                if( replace==0 ){
                    FileDirectory fd = new FileDirectory();
                    fd.makeFileUtf(jfc.getSelectedFile().getPath(), jTextArea1.getText());
                }
                //cancel button action perform
                else if( replace==2 ){
                    jMenuItem3MousePressed(evt);
                }
                //no button action perform
            }
            //file saving with given file name.
            else {
                FileDirectory fd = new FileDirectory();
                fd.makeFileUtf(jfc.getSelectedFile().getPath()+".txt", jTextArea1.getText());
            }
            
        }
        catch (IOException ex) {
            Logger.getLogger(SearchPage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured choosing the save file. Please try again.", "Saving File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}//GEN-LAST:event_jMenuItem3MousePressed

private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
    if( jTextField1.getText().equals("Search here...")==true )
        jTextField1.setText(null);
    else
        jTextField1.selectAll();
}//GEN-LAST:event_jTextField1MouseClicked
       
private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
    int I = jList1.getSelectedIndex();
    jTextArea1.setText(newsPrint.elementAt(I).toString());
    jTextArea1.setCaretPosition(0);
    jTextField2.setText(headlineFilter.elementAt(I).toString());
    try{
        link = newsLinks.get(I);
    }catch(Exception e){
        //JOptionPane.showMessageDialog(rootPane, "This news has no link.", "URL not found", JOptionPane.WARNING_MESSAGE);
    }
    
}//GEN-LAST:event_jList1MouseClicked

private void jMenuItem7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem7MousePressed
    CustomFilter cf = new CustomFilter("crime", dbName, password);
}//GEN-LAST:event_jMenuItem7MousePressed

private void jMenuItem8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem8MousePressed
    CustomFilter cf = new CustomFilter("sports", dbName, password);
}//GEN-LAST:event_jMenuItem8MousePressed

private void jMenuItem9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem9MousePressed
    CustomFilter cf = new CustomFilter("local", dbName, password);
}//GEN-LAST:event_jMenuItem9MousePressed

private void jMenuItem10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem10MousePressed
    CustomFilter cf = new CustomFilter("politics", dbName, password);
}//GEN-LAST:event_jMenuItem10MousePressed

private void jMenuItem11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem11MousePressed
    CustomFilter cf = new CustomFilter("economics", dbName, password);
}//GEN-LAST:event_jMenuItem11MousePressed

private void jMenuItem12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem12MousePressed
    CustomFilter cf = new CustomFilter("user", dbName, password);
}//GEN-LAST:event_jMenuItem12MousePressed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    closingWindow();   
}//GEN-LAST:event_formWindowClosing

/*add user doc*/
private void jMenuItem13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem13MousePressed
    UserDocument ud = new UserDocument();
    ud.add(mainPath+"\\User");
}//GEN-LAST:event_jMenuItem13MousePressed

/*remove user doc*/
private void jMenuItem14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem14MousePressed
    UserDocument ud = new UserDocument();
    ud.delete(mainPath+"\\User");
}//GEN-LAST:event_jMenuItem14MousePressed

private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
    getUserDate();
    needRead = true;
    link = null;
    System.out.println("***** Day *****");
    search();
}//GEN-LAST:event_jComboBox1ActionPerformed

private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
    if( comboRecent==true ){
        getUserDate();
        comboRecent = false;
        showRecentComboData();
        comboRecent = true;
        needRead = true;
        link = null;
        System.out.println("***** Month *****");
        search();
    }
}//GEN-LAST:event_jComboBox2ActionPerformed

private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
    if( comboRecent==true ){
        getUserDate();
        comboRecent = false;
        showRecentComboData();
        comboRecent = true;
        needRead = true;
        link = null;
        System.out.println("***** Year *****");
        search();
    }

}//GEN-LAST:event_jComboBox3ActionPerformed

private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
    String filterBy = jComboBox6.getSelectedItem().toString();
    if( filterBy.equals("All")==true ){
        keyFilter.clear();
        System.out.println("***** Filter By All *****");
        search();
    }
    //if( filterBy.equals("Crime")==true || filterBy.equals("Economics")==true || filterBy.equals("Local")==true || filterBy.equals("Politics")==true || filterBy.equals("Sports")==true|| filterBy.equals("User")==true )
    else{
        filterSearch(filterBy);
    }
}//GEN-LAST:event_jComboBox6ActionPerformed

private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
    int newspaper = jComboBox7.getSelectedIndex();
    if( newspaper==0 )
        recentPaperName = "prothom-alo";
    else if( newspaper==1 )
        recentPaperName = "dailynayadiganta";
    else
        recentPaperName = "user";
    needRead = true;
    System.out.println("***** Paper *****");
    search();
}//GEN-LAST:event_jComboBox7ActionPerformed

private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
    doAdvanceSearch();
}//GEN-LAST:event_jComboBox4ActionPerformed

private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
    doAdvanceSearch();
}//GEN-LAST:event_jComboBox5ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    System.out.println("SP -> "+link);
    if( link!=null && recentPaperName.equals("user")==false ){
        OpenLinkWithBrowser olwb = new OpenLinkWithBrowser();
        olwb.goToLink(link);
    }
    else{
        JOptionPane.showMessageDialog(rootPane, "This news has no link.", "URL not found", JOptionPane.WARNING_MESSAGE);
    }
}//GEN-LAST:event_jButton2ActionPerformed

private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
    if( autoSearch==true ){
        System.out.println("***** Auto Search *****");
        search();
    }
}//GEN-LAST:event_jTextField1KeyReleased

private void jMenuItem15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem15MousePressed
    new Download().setVisible(true);
    this.dispose();
}//GEN-LAST:event_jMenuItem15MousePressed

private void jMenuItem16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem16MousePressed
    new Home().setVisible(true);
    this.dispose();
}//GEN-LAST:event_jMenuItem16MousePressed

private void jCheckBoxMenuItem1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ItemStateChanged
    autoSearch = jCheckBoxMenuItem1.getState();
}//GEN-LAST:event_jCheckBoxMenuItem1ItemStateChanged

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    searchWithBrowser();
}//GEN-LAST:event_jButton3ActionPerformed

private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
    if( evt.getKeyChar()=='\n' )
        searchWithBrowser();
}//GEN-LAST:event_jTextField3KeyReleased

private void jTextField3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseClicked
    jTextField3.selectAll();
}//GEN-LAST:event_jTextField3MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SearchPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchPage().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
