package Shondesh;

import javax.sound.sampled.*;
import java.io.*;

public class Sound
{
	public void play(String path) {//throws LineUnavailableException, UnsupportedAudioFileException, IOException
            try{
                File f = new File(path);
                Clip clip=AudioSystem.getClip();
                AudioInputStream ais=AudioSystem.getAudioInputStream(f);
                clip.open(ais);
                clip.loop(0);
                //JOptionPane.showMessageDialog(null,"Close to exit");
                //clip.close();
            }
            catch(Exception e){}
	}
        
}