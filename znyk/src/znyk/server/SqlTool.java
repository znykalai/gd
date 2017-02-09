package znyk.server;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import znyk.common.SqlPro;

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
    
    public static String update7Line(String tp,String fromID,String toID)  {
	       ConnactionPool p=ConnactionPool.getPool();
           Conn conn=p.getCon2("");

          Statement st=null;
          Connection con=conn.getCon();
     try{
            st=con.createStatement();
            con.setAutoCommit(false);
            String sql1="update 货位表     set 托盘编号='' where 货位序号="+"'"+fromID+"'";
            String sql2="update 货位表     set 托盘编号='"+tp+"' where 货位序号="+"'"+toID+"'";
            String sql3="update 库存托盘  set 货位号="+toID+" where 托盘编号="+"'"+tp+"'";
           
            st.executeUpdate(sql1);
            st.executeUpdate(sql2);
            st.executeUpdate(sql3);


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
    
   public static String  add动作指令(String tp,String fromID,String toID,String type/*上货，下货，输送线回流*/, 
		  int todaku/*1=回大库，非1=不回*/, String machineID){
	   ConnactionPool p=ConnactionPool.getPool();
       Conn conn=p.getCon2("");

      Statement st=null;
      ResultSet set=null;
      Connection con=conn.getCon();
      boolean can=true;
      String back="未处理";
 try{
        st=con.createStatement();
        con.setAutoCommit(false);
        String sql="select idEvent,状态,状态2 from 立库动作指令  where 状态<>'完成' and 托盘编号='"+tp+"' order by ID";
        set=st.executeQuery(sql);
        if(set.next()){
        	can=true;
        	back="关于这个托盘的指令正在执行";
        }
          
        
       if(type.equals("输送线回流")){
    	   //最好判断下这个TP是不是这个输送线
    	   String  ql="insert into 立库动作指令 (来源,任务类别,动作,"+
                   "托盘编号,状态,来源货位号,放回货位号,状态2,请求区,是否回大库,新建时间) values("+
                   "'立库',"+
                   "'PACK装配',"+
                   "'输送线回流',"+
                   "'"+tp+"',"+
                    "'排队',"+
                     "'"+fromID+"',"+
                     "'"+toID+"',"+
                      "'"+(todaku==1?0:1)+"',"+
                      "'"+machineID+"',"+
                      "'"+todaku+"',"+
                      SqlPro.getDate()[1]+
                   ")";
			    st.executeUpdate(ql);
         }else{
        	
        	 if(type.equals("上货")){
        		 //判断去往的货位有没有托盘
        		 if(!can){
         	        String sql2="select 托盘编号  from 货位表   where  货位序号='"+toID+"'";	
         	        set=st.executeQuery(sql);
         	         if(set.next()){
         	        	Object t=set.getObject(1)==null?"":set.getObject(1);
         	        	if(!t.equals("")){
         	        	         can=true;
         	        	         back="去往的货位已经有托盘";
         	        	          }
         	           }	
         	        }
        		 //判断去往的货位在指令队列里面有没有被指定别的托盘
        		 if(!can){
        		String sql2="select idEvent,放回货位号,状态  from 立库动作指令  where 状态<>'完成' and 放回货位号='"+toID+"' order by ID";
          	        set=st.executeQuery(sql);
          	         if(set.next()){
          	        	Object t=set.getObject(1)==null?"":set.getObject(1);
          	        	if(!t.equals("")){
          	        	         can=true;
          	        	         back="在指令队列里去往的货位被安排托盘";
          	        	          }
          	           }	
          	        }
        		 //如果上面判断多没有，那么想指令队列里面插入数据
        		  if(!can){
        			  String  ql="insert into 立库动作指令 (来源,任务类别,动作,"+
                              "托盘编号,状态,来源货位号,放回货位号,状态2,请求区,是否回大库,新建时间) values("+
                              "'立库',"+
                              "'PACK装配',"+
                              "'上货',"+
                              "'"+tp+"',"+
                               "'排队',"+
                                "'"+fromID+"',"+
                                "'"+toID+"',"+
                                 "'"+1+"',"+
                                 "'"+machineID+"',"+
                                 "'"+1+"',"+
                                 SqlPro.getDate()[1]+
                              ")";
        			    st.executeUpdate(ql);
        			  
        		  }
        		 
        	 }//end上货

        	 if(type.equals("下货")){
        		 //所取的货架有没有货
        		 if(!can){
         	        String sql2="select 货位序号   from 货位表   where  托盘编号='"+tp+"' and 货位序号='"+fromID+"'";	
         	        set=st.executeQuery(sql);
         	         if(!set.next()){
         	        	can=true;
         	        	 back="在指定位置没这个托盘";
         	           }	
         	        }
        		 if(toID.equals("60001")){
        		 //判断取料升降台有没有托盘
        		 if(!can){//暂时先不做
        		
          	        }
        		 
        		   }else{
        		  //判断7条输送线有没有货	   
        			   if(!can){
                	        String sql2="select 托盘编号  from 货位表   where  货位序号='"+toID+"'";	
                	        set=st.executeQuery(sql);
                	         if(set.next()){
                	        	Object t=set.getObject(1)==null?"":set.getObject(1);
                	        	if(!t.equals("")){
                	        	         can=true;
                	        	         back="去往的货位已经有托盘";
                	        	          }
                	           }	
                	         }    
        		   }
        		 //如果上面判断多没有，那么想指令队列里面插入数据
        		  if(!can){
        			  int t=Integer.parseInt(toID);
        			   if(t>500&&t<615)todaku=0;
        			  String  ql="insert into 立库动作指令 (来源,任务类别,动作,"+
                              "托盘编号,状态,来源货位号,放回货位号,状态2,请求区,是否回大库,新建时间) values("+
                              "'立库',"+
                              "'PACK装配',"+
                              "'下货',"+
                              "'"+tp+"',"+
                               "'排队',"+
                                "'"+fromID+"',"+
                                "'"+toID+"',"+
                                 "'"+(todaku==1?0:1)+"',"+
                                 "'"+machineID+"',"+
                                 "'"+todaku+"',"+
                                 SqlPro.getDate()[1]+
                              ")";
        			    st.executeUpdate(ql);
        			  
        		  }
        		 
        	 }//end下货
        		 	 
        	 
         }


       con.commit();
       con.setAutoCommit(true);
       set.close();
       st.close();
       conn.realseCon();
  }catch(Exception ex){ ex.printStackTrace();
     try {
         con.rollback();
         con.setAutoCommit(true);
         set.close();
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


       return back;
	 
    	
    }
    
}
