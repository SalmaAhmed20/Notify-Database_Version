package comm.GateWay;

import comm.NotficationModel.Notification;
import comm.NotficationModel.State;

public class SMSgateway implements NotificationGateway {

	@Override
	public boolean send(Notification toBeSent) {
		toBeSent.setState(State.Failure);
		if(!toBeSent.getChannel())
		return false;
		var phoneNumber = toBeSent.getReceiver();
		for(int i = 0 ; i < phoneNumber.length();i++)
			if(phoneNumber.charAt(i)>'9'||phoneNumber.charAt(i)<'0')
				return false;
		toBeSent.setState(State.Success);
		return true;
	}
	
}
