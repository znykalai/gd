package znyk.server.webService;

import javax.jws.WebService;

@WebService(endpointInterface="znyk.server.webService.ServiceServer",serviceName="ServiceServer")
public class ServiceServerImp implements ServiceServer {

	@Override
	public void setStateForEventID(int idEvent, int state, String ext) {
		// TODO Auto-generated method stub
		System.out.println("1111");

	}

	@Override
	public String getLastComment(int machineID,int type) {
		// TODO Auto-generated method stub
		return "test";
	}

	@Override
	public String exeComment(String comment,int type) {
		// TODO Auto-generated method stub
		return "test";
	}
}
