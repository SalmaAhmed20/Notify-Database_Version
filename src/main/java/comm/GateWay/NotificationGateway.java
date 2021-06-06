package comm.GateWay;

import comm.NotficationModel.Notification;

public interface NotificationGateway {
	
	public boolean send(Notification notificatoin);
}
