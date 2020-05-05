package Shondesh;

import java.util.*;
import java.io.*;
import java.text.*;

class FileDirectory{

/*directory deleting*/
	public boolean deleteDir(File dir) {
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    // The directory is now empty so delete it
	    return dir.delete();
	}

/*creating directory*/
	public boolean makeDirs(File dir){
		return dir.mkdirs();
	}

/*creating file in append mode*/
	public void makeFileAppend(File f, String text, boolean append)throws IOException{
		PrintWriter pw = new PrintWriter(new FileWriter(f,append));
		pw.println(text);
		pw.close();
	}
/*creating file utf-8 format*/
	public void makeFileUtf(String filePath, String text)throws IOException{
		PrintWriter pw = new PrintWriter(filePath, "UTF-8");
		pw.println(text);
		pw.close();
	}
        
/*checking file or directory are exist or not*/
        public boolean isExist(File f){
            return f.exists();
        }

/*read a file & return a string*/
        public String getFileString(String path) throws IOException
	{
		String text = "";
                String str;
                BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(path), "UTF8"));
                while( (str=br.readLine())!=null )
                        text += str;
		return text;
	}
        
/*get all subdirectory & file name */
        String[] getDirectoryChildName(File dir){
            return dir.list();
        }

/*get the numbers of subdirecotries & files*/
        int getDirectoryChildNumber(File dir){
            return dir.list().length;
        }
}
