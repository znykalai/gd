package znyk.server;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class SqlTool {
	
	public static Vector findInVector(String sql) {
        Vector has=new Vector();


        ConnactionPool p=ConnactionPool.getPool();
		Conn conn= p.getCon2("") ;
		Connection con=conn.getCon(); 
       
          Statement  st=null;
          ResultSet set=null;
     try{
     //    System.out.println(sql+ this.getClientHost());

   
         st=con.createStatement();
        set=st.executeQuery(sql);

     ResultSetMetaData rs=set.getMetaData();
     int num=rs.getColumnCount();


    while(set.next()){
        Vector v=new Vector();

    for(int i=1;i<num+1;i++){
       v.addElement(set.getObject(i));
      }
       has.addElement(v);
    }
    set.close();
    st.close();
    conn.realseCon();
    return has;
     }
   catch(Exception e){
        e.printStackTrace() ;
       try {
    	   conn.realseCon();
           set.close();
           st.close();

       } catch (SQLException ex) {

           ex.printStackTrace();
           conn.realseCon();
       }
          conn.realseCon();
          e.printStackTrace();
       return null;

   }

     //   return has;
 }
	
    public static String findOneRecord(String sql) {

         String val=null;
         ConnactionPool p=ConnactionPool.getPool();
         Conn fac=p.getCon2("");
         Statement  st=null;
     try{


     Connection con=fac.getCon();
     st=con.createStatement();
     ResultSet set=st.executeQuery(sql);

     ResultSetMetaData rs=set.getMetaData();
     int num=rs.getColumnCount();
     while(set.next()){
    for(int i=1;i<num+1;i++){
        if(i==1)
        val=(set.getObject(i)==null?0:set.getObject(i))+"!_!";
    else val=val+(set.getObject(i)==null?0:set.getObject(i))+"!_!";

      }
       val=val.substring(0,val.length()-3);
       break;
    }
    st.close();
    fac.realseCon();
    return val;
     }
   catch(Exception e){
       try {
           st.close();
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
        fac.realseCon();
        e.printStackTrace();
       return null;

   }


      }	
    
    public static String insert(String[] sql)  {
    	       ConnactionPool p=ConnactionPool.getPool();
               Conn conn=p.getCon2("");

                 Statement st=null;
                 Connection con=conn.getCon();
            try{
                   st=con.createStatement();
                   con.setAutoCommit(false);
             for(int i=0;i<sql.length;i++)
                   st.executeUpdate(sql[i]);



                  con.commit();
                  con.setAutoCommit(true);
                  st.close();
                  conn.realseCon();
             }catch(Exception ex){ ex.printStackTrace();
                try {
                    con.rollback();
                    con.setAutoCommit(true);
                    st.close();
                    conn.realseCon();
                     return ex.getMessage();
                  } catch (SQLException ex1) {
                      ex1.printStackTrace() ;
                      conn.realseCon();
                     return ex1.getMessage();
                  }

               }
                  conn.realseCon();


                  return "记录更新成功！";

    }  
}
