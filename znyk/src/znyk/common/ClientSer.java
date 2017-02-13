package znyk.common;


import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
import javax.xml.ws.BindingProvider;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import GDT.Resint;
import localhost.GD_wsdl.GDLocator;
import localhost.GD_wsdl.GDPortType;
import znyk.plc.ReST;



public class ClientSer {
	
	public GDLocator gd =new GDLocator();
	private static ClientSer INSTANCE;
	private ClientSer(){
	//	 ((BindingProvider)gd).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"serviceUrl");
		try{//启动时先预热一下，避免首例处罚。
			getState(10000);
		   }catch(Exception ex){}
		/*
		 Client client = ClientProxy.getClient(gd);
    	 HTTPConduit http = (HTTPConduit) client.getConduit();   
    	 HTTPClientPolicy httpClientPolicy =  new  HTTPClientPolicy();   
    	 httpClientPolicy.setConnectionTimeout( 20000 );   
    	 httpClientPolicy.setAllowChunking( false );   
    	 httpClientPolicy.setReceiveTimeout( 40000 );   
    	 http.setClient(httpClientPolicy);
    	 */
    	// HttpClient cl;
    	
	}
	public static synchronized ClientSer getIntance(){
		if(INSTANCE!=null){
			return INSTANCE;
			}else{
				
			INSTANCE=new ClientSer();
			
			return INSTANCE;
		}
		
	}
	
	public GDPortType getGD() throws Exception{
		return gd.getGD();
	}
	
	public String getState(int t) throws RemoteException, ServiceException{
		//return gd.getGD().getState(t);
		if(t==SqlPro.A区输送线){
			return "501=1|502=0|503=1|504=0|505=1|506=0|507=1|508=0|"+
					"509=1|510=0|511=1|512=0|513=1|514=0"
		;
		}
		if(t==SqlPro.B区输送线){
			return "601=1|602=0|603=1|604=0|605=1|606=0|607=1|608=0|"+
					"609=1|610=0|611=1|612=0|613=1|614=0"
		;
		}
		
		if(t==10){
			return "502=1|504=0|506=1|508=0|510=1|512=0|514=1";
		
		}
		if(t==11){
			return "602=1|604=0|606=1|608=0|610=1|612=0|614=1";
		
		}
		return "1";
	}
	
	public String ReadFromRffid(String message,int id) throws RemoteException, ServiceException{
		//return gd.getGD().readFromRffid(message, id);
		return "1";
		
	}
	public int upPallet(int idEvent,int fromID,int toLocID,int machineID){
		System.out.println("上货");
		return -1;
	}
	public int getPallet(int idEvent,String fromLocID,int toLocID,int machineID){
		System.out.println("下货");
		return -1;}
	public int toBackBuffer(int idEvent, int fromLocID,int toLocID){ 
		System.out.println("回货");
		return -1;}
	public GDT.Resint[] getSirIntValuesFromCTR(String startAddress,int nums,int valueLen,
          int machineID){
		
		return new Resint[]{
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),new Resint(),
			//第2个缓存队列
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),new Resint()};
	
	}


}
