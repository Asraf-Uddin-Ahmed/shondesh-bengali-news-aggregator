/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Shondesh;

import java.net.*;
import java.io.*;
import java.awt.*;

/**
 *
 * @author RATUL
 */
public class OpenLinkWithBrowser {

    public boolean goToLink(String url) {
        try {
            URL file=new URL(url);
            Desktop.getDesktop().browse(file.toURI());
            return true;
        } 
        catch (MalformedURLException ex) {
            return false;
        }
        catch (IOException ex) {
            return false;
        }
        catch (URISyntaxException ex) {
            return false;    
        }
    }
    
}
