package alai.znyk.test;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import alai.znyk.server.webService.ServiceServer;



public class ServiceClient {
	 public static void main(String[] args) throws Exception {
         
    	 JaxWsProxyFactoryBean svr = new JaxWsProxyFactoryBean();
         svr.setServiceClass(ServiceServer.class);
         svr.setAddress("http://localhost:8080/ServiceServer");
 
        final ServiceServer hw = (ServiceServer) svr.create();
    	/* Client client = ClientProxy.getClient(hw);
    	 HTTPConduit http = (HTTPConduit) client.getConduit();   
    	 HTTPClientPolicy httpClientPolicy =  new  HTTPClientPolicy();   
    	 httpClientPolicy.setConnectionTimeout( 20000 );   
    	 httpClientPolicy.setAllowChunking( false );   
    	 httpClientPolicy.setReceiveTimeout( 20000 );   
    	 http.setClient(httpClientPolicy);*/
    	       JFrame f=new JFrame();
    	       f.setLayout(new FlowLayout());
    	       JButton b=new JButton("ddd");
    	       JButton b2=new JButton("ddd2");
    	       b.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					 long statTime=System.currentTimeMillis();
                	  hw.setStateForEventID(1, 1, "1");
                	  
                	  System.out.println(System.currentTimeMillis()-statTime);	
					
				}
			});
    	       
    	       b2.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						 long statTime=System.currentTimeMillis();
						// hw.test();
	                	  
	                	  System.out.println(System.currentTimeMillis()-statTime);	
						
					}
				});
               
             f.getContentPane().add(b); f.getContentPane().add(b2);
             f.pack();
             f.show();
              
     }
}
