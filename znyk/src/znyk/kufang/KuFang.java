package znyk.kufang;

import java.rmi.RemoteException;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import localhost.GD_wsdl.GDLocator;
import znyk.common.ClientSer;
import znyk.common.SqlPro;
import znyk.plc.PLC;
import znyk.server.SqlTool;

public class KuFang {
	
public void startLine(){
	//����������7���������ϵ�λ��
	
	 try{
	String ss= ClientSer.getIntance().getGD().getState(SqlPro.A��������);
	String sm[]=ss.split("|");
	String sql1= "select  ��λ���,���̱��   from ��λ��  where  ��λ���  between 501 and 514 order by ��λ���";
	String sql2= "select  ��λ���,���̱��   from ��λ��  where  ��λ���  between 601 and 614 order by ��λ���";
	Vector<Vector> tem1=SqlTool.findInVector(sql1);
	Vector<Vector> tem2=SqlTool.findInVector(sql2);
   
	for(int r=0;r<tem1.size();r++){
		Vector line1=tem1.get(r*2);
		Vector line2=tem1.get(r*2+1);
		String firstTP=line1.get(1)==null?"":line1.get(1).toString();
		String secTP=line2.get(1)==null?"":line2.get(1).toString();
		if(secTP.equals("")){
			if(sm[r*2+1].equals("0")){
				String fromID=line1.get(0)+"";
				String toID=line2.get(0)+"";
				SqlTool.update7Line(firstTP, fromID, toID);
			}
			
		 }
		
	 } 
	
	for(int r=0;r<tem2.size();r++){
		Vector line1=tem2.get(r*2);
		Vector line2=tem2.get(r*2+1);
		String firstTP=line1.get(1)==null?"":line1.get(1).toString();
		String secTP=line2.get(1)==null?"":line2.get(1).toString();
		if(secTP.equals("")){
			if(sm[r*2+1].equals("0")){
				String fromID=line1.get(0)+"";
				String toID=line2.get(0)+"";
				SqlTool.update7Line(firstTP, fromID, toID);
			}
			
		 }
		
	 } 
	}catch(Exception ex){}
	
}

public void start�Ѷ��ָ��(){
	
	 Vector ��1��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�ϻ�' and ������= '1' order by idEvent");
     Vector ��1��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�»�' and ������= '1' order by idEvent");
    
     boolean run=false;
     int last1=1;
     if(��1��.size()>0){
    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
    	 Vector up=(Vector)��1��.get(0);
    	 if(up.get(8).equals("ִ����")||up.get(8).equals("�ѷ���")){run=true;}
      }
     
     if(��1��.size()>0){
    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
    	 Vector down=(Vector)��1��.get(0);
    	 if(down.get(8).equals("ִ����")||down.get(8).equals("�ѷ���")){run=true;}
      
     }
    //�������Ѷ��û��ִ�е�ָ���ô���� 
    if(last1==1){//�ϻ�
    if(!run) {
    	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
    	
    	try {
			String state=ClientSer.getIntance().getGD().getState(2);//��ȡ�Ѷ��1��״̬
			if(state.equals("1")){
				 if(��1��.size()>0){
			    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��1��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().getGD().upPallet(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID), 1);
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			      }
				
			}
		   } catch (Exception e) {}
    	last1=2;
       }
	
  }
    if(last1==2){//�»�
        if(!run) {
        	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
        	
        	try {
    			String state=ClientSer.getIntance().getGD().getState(2);//��ȡ�Ѷ��1��״̬
    			if(state.equals("1")){
    				//�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��1��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().getGD().getPallet(Integer.parseInt(eventID), 
			    			 fromID, Integer.parseInt(toID), 1);
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
    				
    			}
    		   } catch (Exception e) {}
        	last1=1;
           }
    	
      }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    Vector ��2��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�ϻ�' and ������= '2' order by idEvent");
    Vector ��2��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�»�' and ������= '2' order by idEvent");
    
    boolean run2=false;
    int last2=1;
    if(��2��.size()>0){
   	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
   	 Vector up=(Vector)��2��.get(0);
   	 if(up.get(8).equals("ִ����")||up.get(8).equals("�ѷ���")){run2=true;}
     }
    
    if(��2��.size()>0){
   	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
   	 Vector down=(Vector)��2��.get(0);
   	 if(down.get(8).equals("ִ����")||down.get(8).equals("�ѷ���")){run2=true;}
     
    }
   //�������Ѷ��û��ִ�е�ָ���ô���� 
   if(last2==1){//�ϻ�
   if(!run2) {
   	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
   	
   	try {
			String state=ClientSer.getIntance().getGD().getState(3);//��ȡ�Ѷ��2��״̬
			if(state.equals("1")){
				 if(��2��.size()>0){
			    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��2��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().getGD().upPallet(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID), 2);
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			      }
				
			}
		   } catch (Exception e) {}
   	last2=2;
      }
	
 }
   if(last2==2){//�»�
       if(!run2) {
       	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
       	
       	try {
   			String state=ClientSer.getIntance().getGD().getState(3);//��ȡ�Ѷ��2��״̬
   			if(state.equals("1")){
   				//�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��2��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().getGD().getPallet(Integer.parseInt(eventID), 
			    			 fromID, Integer.parseInt(toID), 2);
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
   				
   			}
   		   } catch (Exception e) {}
       	last2=1;
          }
   	
     } 
    
     }

public void startlineAGV(){
	
	 Vector ��1��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�����߻���' and ������= '1' order by idEvent");
     Vector ��2��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�����߻���' and ������= '2' order by idEvent");
   
    boolean run=false;
    boolean run2=false;
   
    if(��1��.size()>0){
   	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
   	 Vector up=(Vector)��1��.get(0);
   	 if(up.get(8).equals("ִ����")||up.get(8).equals("�ѷ���")){run=true;}
     }
    
    if(��2��.size()>0){
      	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
      	 Vector up=(Vector)��2��.get(0);
      	 if(up.get(8).equals("ִ����")||up.get(8).equals("�ѷ���")){run2=true;}
        }
    
 if(!run) {
   	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
   	
   	try {
			String state=ClientSer.getIntance().getGD().getState(SqlPro.AGV1);//��ȡAGV1��״̬
			if(state.equals("1")){
				 if(��1��.size()>0){
			    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��1��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().getGD().toBackBuffer(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID));
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			      }
				
			}
		   } catch (Exception e) {}
  
      }
	
 //////////////////////////////////////////////////////////////////////////////////////////////////////////
 
       if(!run2) {
       	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
       	
       	try {
   			String state=ClientSer.getIntance().getGD().getState(SqlPro.AGV2);//��ȡAGV2��״̬
   			if(state.equals("1")){
   				//�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��2��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().getGD().toBackBuffer(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID));
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
   				
   			}
   		   } catch (Exception e) {}
       
          }
   	

  
   
    }
	
}
