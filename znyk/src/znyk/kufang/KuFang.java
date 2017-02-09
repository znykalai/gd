package znyk.kufang;

import java.util.Vector;

import localhost.GD_wsdl.GDLocator;
import znyk.common.SqlPro;
import znyk.plc.PLC;
import znyk.server.SqlTool;

public class KuFang {
	
public void startLine(){
	//更新托盘在7条输送线上的位置
	 GDLocator gd=PLC.getIntance().gd;
	 try{
	String ss= gd.getGD().getState(SqlPro.A区输送线);
	String sm[]=ss.split("|");
	String sql1= "select  货位序号,托盘编号   from 货位表  where  货位序号  between 501 and 514 order by 货位序号";
	String sql2= "select  货位序号,托盘编号   from 货位表  where  货位序号  between 601 and 614 order by 货位序号";
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


	
}
