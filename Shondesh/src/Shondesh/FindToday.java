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
public class FindToday {
    
    Date today;
    String day[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thirsday", "Friday", "Saturday"};
    
    FindToday(){
        today = new Date();
    }
    
    public String thisDate(){
        return String.valueOf(today.getDate());
    }
    
    public String thisMonth(){
        return String.valueOf(today.getMonth()+1);
    }
    
    public String thisYear(){
        return String.valueOf(1900+today.getYear());
    }
    
    public String thisHour(){
        return String.valueOf(today.getHours());
    }
    
    public String thisMinute(){
        return String.valueOf(today.getMinutes());
    }
    
    public String thisSecond(){
        return String.valueOf(today.getSeconds());
    }
    
    public String thisDay(){
        return day[today.getDay()];
    }
    
    /*return max day of selected month & year*/
    public int maxDay(int month, int year){
        if( month==2 ){
            if( year%4==0 )
                    return 29;
            return 28;
        }
        else if( month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12 )
            return 31;
        return 30;
    }
    
    /* return current time in am/pm format*/
    public String getCurrentTime(){
        String time = "";
        String hour = thisHour();
        String minute = thisMinute();
        String second = thisSecond();
        String ampm = "am";
        int H = Integer.parseInt(hour);
        int M = Integer.parseInt(minute);
        int S = Integer.parseInt(second);
        if( H>=12 ){
            H -= 12;
            ampm = "pm";
        }
        if( H==0 ){
            H = 12;
        }
        
        if( H<10 )
            time += "0";
        time += String.valueOf(H)+":";
        if( M<10 )
            time += "0";
        time += String.valueOf(M)+":";
        if( S<10 )
            time += "0";
        time += String.valueOf(S)+" "+ampm;
        return time;
    }
}
