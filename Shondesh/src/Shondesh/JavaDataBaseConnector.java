package Shondesh;


import java.sql.*;
import javax.swing.JOptionPane;
import java.util.*;

class JavaDataBaseConnector{

	Connection connect;
	static String rst="";
	static ResultSet result_stat;
	static Statement st_stat;

	/*method for connect with database*/
	boolean getConnect(String dbName, String password){ 
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName+"?useUnicode=true&amp;characterEncoding=UTF8;","root",password);
                return true;
            }
            catch(Exception ee){
                JOptionPane.showMessageDialog(null,"Database not connected....\nPlease Check database connection....");
                System.out.println("Driver error "+ee);
                return false;
            }
        }

	/*checking caonnection status*/
	boolean isConnected(){
		try{
                    return !connect.isClosed();
                }
                catch(SQLException sqle){return false;}
	}

	/*closing connenction*/
	void connenctionClose(){
		try{
                    connect.close();
                }catch(SQLException sqle){};
	}


	/*creating table*/
	public boolean createTable(String sql)
	{
		try{
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("create table ");
			stmt.close();
			return true;
		}
		catch(SQLException e){
			System.out.println(e+"create table error");
			return false;
		}
	}

	/*inserting table*/
	boolean insertTable(String sql){
		return createTable(sql);
	}


	/*return ResultSet of sql for retrieving data*/
	public ResultSet findStatement(String par)
	{
             try{
                st_stat = connect.createStatement();
                result_stat = st_stat.executeQuery(par);
            }
            catch(SQLException e){
                System.out.println(e+"show findResult error");
            }
            return result_stat;
	}

        /*convert ResultSet of data to vector for showing*/
        public Vector getTableElement(ResultSet rs, int column){
            Vector data= new Vector();
            try{
               while(rs.next()) {
                    Vector tmp= new Vector();
                    for(int I=1; I<=column; I++)
                        tmp.addElement(rs.getString(I));
                    data.addElement(tmp);
                }
            }
            catch(Exception ex){}
            return data;
        }
        
}
