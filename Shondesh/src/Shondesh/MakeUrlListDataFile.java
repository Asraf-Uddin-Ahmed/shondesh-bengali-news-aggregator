package Shondesh;

/*
called by MakeUrlListDataFile(String rootUrl) [temporarily named Main]
*/

import java.net.URL;

import java.util.*;
import java.io.*;

import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MakeUrlListDataFile implements Runnable
{
    
	ArrayList<String> UrlListNews = new ArrayList<String>();		//contains all valid url which is only & only sub-url & news of root-url
	ArrayList<String> UrlListPage = new ArrayList<String>();	//contains all url which are in root-url & sub-url & page of the paper
        int totalNews = 0;          //store total number of news. value will change after callinh storeNewsList.
        String destPath = null;
        String RootUrl = null;
        ProgressBar pb = null;
        FileDirectory fd = null;
        PaperResolve pr = null;
        Thread t = null;
/*******************simple bfs************************/
        //return true if canceled.
	private boolean bfs(){

		ArrayList<String> UrlListTemp = new ArrayList<String>();	//contains all url which are in root-url & sub-url

		StringManipulation sm = new StringManipulation();
		UrlToUrl utu = new UrlToUrl( RootUrl, pb );
		UrlListTemp.addAll( utu.UrlStore );
		
		for(int I=0; I<UrlListPage.size()&&pb.cancelDownload==false ;I++)
		{
			//System.out.println("< "+I+" >"+UrlListPage.get(I));
			
                        String str1 = UrlListPage.get(I);
			UrlListTemp.clear();
			utu = new UrlToUrl(str1, pb);
			UrlListTemp.addAll( utu.UrlStore );

			for(String str2 : UrlListTemp )
			{
				if( sm.stringMatching(str2, pr.newsKey, 0)>0 && UrlListNews.contains(str2)==false )
				{
					System.out.println("->"+str2);
					//pb.progMonitor(str2);
					UrlListNews.add(str2);
					//System.out.print(".");
				}
				else if( sm.stringMatching(str2, pr.pageKey, 0)>0 && UrlListPage.contains(str2)==false )
				{
					System.out.println("=>"+str2);
					//pb.progMonitor(str2);
					UrlListPage.add(str2);
					//System.out.print(".");
				}
			}
                        //if no paper is found after half of node traversed
                        if( UrlListNews.isEmpty()==true && UrlListPage.size()/2==I ){
                            pb.cancelDownload = true;
                            pb.cancelSaving = true;
                            JOptionPane.showMessageDialog(pb, "Your internet speed is too slow.\nPlease check this out.", "Internet connenction failure", JOptionPane.ERROR_MESSAGE);
                        }
                        
                        int size = UrlListPage.size();
                        double progress = (double)I / (double)size * 100.00;
                        System.out.println(progress);
                        pb.progBar((int)progress);
                        pb.setTitle("Downloading...   "+String.valueOf((int)progress)+"%");
                        
		}
                return pb.cancelDownload;
	}

/*******************first generating 101 of url via pr.pageKey. then bfs***************************/
	private boolean greedyBfs(int N){
            //creating source more than one.
		for(int I=0; I<=N; I++)
			UrlListPage.add(pr.pageKey+String.valueOf(I));
                return bfs();
	}

/*creating file with full of page's url*/
    void storePageList()throws IOException{
        for(String str : UrlListPage )
            fd.makeFileAppend(new File(destPath+"\\page"), str, true);
    }
    
/*creating file with full of news's url*/
    void storeNewsList()throws IOException{
        for(String str : UrlListNews )
            fd.makeFileAppend(new File(destPath+"\\news"), str, true);
       totalNews = UrlListNews.size();
    }
    
/*creating file with full of news text. ACTIVE it for all. determine the total news file*/
    void storeNews()throws IOException{
        for(int I=0; I<totalNews&&pb.cancelSaving==false; I++){
            new UrlToTextFile( UrlListNews.get(I) , String.valueOf(I), pr );
            double progress = (double)I / (double)totalNews * 100.00;
            System.out.println(progress);
            pb.progBar((int)progress);
            pb.setTitle("Saving...   "+String.valueOf((int)progress)+"%");
            pb.progMonitor("\n"+UrlListNews.get(I));
                
        }
    }

/******************************* CONSTRUCTOR ****************************************************/
    public MakeUrlListDataFile(String rootUrl, String userDate)
    {   
        RootUrl = rootUrl;
        pr = new PaperResolve( rootUrl, userDate );
        destPath = pr.path+"\\"+pr.paperName+"\\"+pr.date;
        fd = new FileDirectory();
        pb = new ProgressBar(); //paper name
        t = new Thread(this);
        t.start();
        System.out.print("loading");
        pb.setPaperName(pr.paperName);
        pb.setVisible(true);	
    }
    
    public void run(){
            
            //if it is previously downloades then this gives alert option.
            if( fd.isExist(new File(destPath+"\\news"))==true ){
                int result = JOptionPane.showConfirmDialog(pb, "This paper is downloaded previously.\nDo you want to download for new update?");
                if( result==0 )
                    fd.deleteDir(new File(destPath));
                else
                    pb.cancelSaving = true;
            }
            
            //using algo for finding next sub-url of root-url.
            if( pb.cancelSaving==false && pr.paperName.equals("prothom-alo") ){
               pb.cancelSaving = greedyBfs(100);
            }
            else if( pb.cancelSaving==false && pr.paperName.equals("dailynayadiganta") ){
                //bfs is efficienct for this. but it is using for slow net connection. 
                //bfs();
                pb.cancelSaving = greedyBfs(50);
            }
            System.out.println("********************* SUB-URL *********************");
            
            //SAVING...
            if( pb.cancelSaving==false ){
                pb.progBar(100);
                pb.setTitle("Download completed");
                pb.progMonitor("\n\n\n\n\n\n\nSaving...\nPlease wait.....");
                System.out.println("Saving.....");
                try{
                    fd.makeDirs(new File(destPath));
                    storePageList();
                    storeNewsList();
                    storeNews();
                }
                catch(IOException ioe){}
                pb.progBar(100);
                pb.setTitle("Saving completed");
                pb.progMonitor("\nSaved");
                JOptionPane.showMessageDialog(pb, "Saving completed :-)");
            }
            new SearchPage().setVisible(true);
            pb.dispose();
            t.stop();   //its working.some process(???) is running...to solve this make System.exit() from SearchPage().
            System.out.println("saving completed");

    }
    
}
