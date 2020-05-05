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

/*creating file*/
	public void makeFile(File f, String text, boolean append)throws IOException{
		PrintWriter pw = new PrintWriter(new FileWriter(f,append));
		pw.println(text);
		pw.close();
	}
}
