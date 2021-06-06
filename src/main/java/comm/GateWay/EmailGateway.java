package comm.GateWay;

import java.util.regex.Pattern;

import comm.NotficationModel.Notification;
import comm.NotficationModel.State;

public class EmailGateway implements NotificationGateway{
	private static final String regex = "^(.+)@(.+)$";
	@Override
	public boolean send(Notification toBeSent) {
		toBeSent.setState(State.Failure);
		Pattern pattern = Pattern.compile(regex);
		if(!pattern.matcher(toBeSent.getReceiver()).matches() ||toBeSent.getChannel())
			return false; //not email
		toBeSent.setState(State.Success);
		return true;
		
	}

}
