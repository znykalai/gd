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


                  return "��¼���³ɹ���";

    }  
    
    public static String update7Line(String tp,String fromID,String toID)  {
	       ConnactionPool p=ConnactionPool.getPool();
           Conn conn=p.getCon2("");

          Statement st=null;
          Connection con=conn.getCon();
     try{
            st=con.createStatement();
            con.setAutoCommit(false);
            String sql1="update ��λ��     set ���̱��='' where ��λ���="+"'"+fromID+"'";
            String sql2="update ��λ��     set ���̱��='"+tp+"' where ��λ���="+"'"+toID+"'";
            String sql3="update �������  set ��λ��="+toID+" where ���̱��="+"'"+tp+"'";
           
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


           return "��¼���³ɹ���";

}  
    
   public static String  add����ָ��(String tp,String fromID,String toID,String type/*�ϻ����»��������߻���*/, 
		  int todaku/*1=�ش�⣬��1=����*/, String machineID){
	   ConnactionPool p=ConnactionPool.getPool();
       Conn conn=p.getCon2("");

      Statement st=null;
      ResultSet set=null;
      Connection con=conn.getCon();
      boolean can=true;
      String back="δ����";
 try{
        st=con.createStatement();
        con.setAutoCommit(false);
        String sql="select idEvent,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ���̱��='"+tp+"' order by ID";
        set=st.executeQuery(sql);
        if(set.next()){
        	can=true;
        	back="����������̵�ָ������ִ��";
        }
          
        
       if(type.equals("�����߻���")){
    	   //����ж������TP�ǲ������������
    	   String  ql="insert into ���⶯��ָ�� (��Դ,�������,����,"+
                   "���̱��,״̬,��Դ��λ��,�Żػ�λ��,״̬2,������,�Ƿ�ش��,�½�ʱ��) values("+
                   "'����',"+
                   "'PACKװ��',"+
                   "'�����߻���',"+
                   "'"+tp+"',"+
                    "'�Ŷ�',"+
                     "'"+fromID+"',"+
                     "'"+toID+"',"+
                      "'"+(todaku==1?0:1)+"',"+
                      "'"+machineID+"',"+
                      "'"+todaku+"',"+
                      SqlPro.getDate()[1]+
                   ")";
			    st.executeUpdate(ql);
         }else{
        	
        	 if(type.equals("�ϻ�")){
        		 //�ж�ȥ���Ļ�λ��û������
        		 if(!can){
         	        String sql2="select ���̱��  from ��λ��   where  ��λ���='"+toID+"'";	
         	        set=st.executeQuery(sql);
         	         if(set.next()){
         	        	Object t=set.getObject(1)==null?"":set.getObject(1);
         	        	if(!t.equals("")){
         	        	         can=true;
         	        	         back="ȥ���Ļ�λ�Ѿ�������";
         	        	          }
         	           }	
         	        }
        		 //�ж�ȥ���Ļ�λ��ָ�����������û�б�ָ���������
        		 if(!can){
        		String sql2="select idEvent,�Żػ�λ��,״̬  from ���⶯��ָ��  where ״̬<>'���' and �Żػ�λ��='"+toID+"' order by ID";
          	        set=st.executeQuery(sql);
          	         if(set.next()){
          	        	Object t=set.getObject(1)==null?"":set.getObject(1);
          	        	if(!t.equals("")){
          	        	         can=true;
          	        	         back="��ָ�������ȥ���Ļ�λ����������";
          	        	          }
          	           }	
          	        }
        		 //��������ж϶�û�У���ô��ָ����������������
        		  if(!can){
        			  String  ql="insert into ���⶯��ָ�� (��Դ,�������,����,"+
                              "���̱��,״̬,��Դ��λ��,�Żػ�λ��,״̬2,������,�Ƿ�ش��,�½�ʱ��) values("+
                              "'����',"+
                              "'PACKװ��',"+
                              "'�ϻ�',"+
                              "'"+tp+"',"+
                               "'�Ŷ�',"+
                                "'"+fromID+"',"+
                                "'"+toID+"',"+
                                 "'"+1+"',"+
                                 "'"+machineID+"',"+
                                 "'"+1+"',"+
                                 SqlPro.getDate()[1]+
                              ")";
        			    st.executeUpdate(ql);
        			  
        		  }
        		 
        	 }//end�ϻ�

        	 if(type.equals("�»�")){
        		 //��ȡ�Ļ�����û�л�
        		 if(!can){
         	        String sql2="select ��λ���   from ��λ��   where  ���̱��='"+tp+"' and ��λ���='"+fromID+"'";	
         	        set=st.executeQuery(sql);
         	         if(!set.next()){
         	        	can=true;
         	        	 back="��ָ��λ��û�������";
         	           }	
         	        }
        		 if(toID.equals("60001")){
        		 //�ж�ȡ������̨��û������
        		 if(!can){//��ʱ�Ȳ���
        		
          	        }
        		 
        		   }else{
        		  //�ж�7����������û�л�	   
        			   if(!can){
                	        String sql2="select ���̱��  from ��λ��   where  ��λ���='"+toID+"'";	
                	        set=st.executeQuery(sql);
                	         if(set.next()){
                	        	Object t=set.getObject(1)==null?"":set.getObject(1);
                	        	if(!t.equals("")){
                	        	         can=true;
                	        	         back="ȥ���Ļ�λ�Ѿ�������";
                	        	          }
                	           }	
                	         }    
        		   }
        		 //��������ж϶�û�У���ô��ָ����������������
        		  if(!can){
        			  int t=Integer.parseInt(toID);
        			   if(t>500&&t<615)todaku=0;
        			  String  ql="insert into ���⶯��ָ�� (��Դ,�������,����,"+
                              "���̱��,״̬,��Դ��λ��,�Żػ�λ��,״̬2,������,�Ƿ�ش��,�½�ʱ��) values("+
                              "'����',"+
                              "'PACKװ��',"+
                              "'�»�',"+
                              "'"+tp+"',"+
                               "'�Ŷ�',"+
                                "'"+fromID+"',"+
                                "'"+toID+"',"+
                                 "'"+(todaku==1?0:1)+"',"+
                                 "'"+machineID+"',"+
                                 "'"+todaku+"',"+
                                 SqlPro.getDate()[1]+
                              ")";
        			    st.executeUpdate(ql);
        			  
        		  }
        		 
        	 }//end�»�
        		 	 
        	 
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
