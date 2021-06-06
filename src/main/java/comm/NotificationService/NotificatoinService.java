package comm.NotificationService;

import comm.DatabaseSim.DBconnection;
import comm.DatabaseSim.SqlConnection;
import comm.Model.Languages;
import comm.Model.Template;
import comm.NotficationModel.Notification;
import comm.NotficationModel.State;

import org.springframework.stereotype.Service;

import comm.GateWay.EmailGateway;
import comm.GateWay.NotificationGateway;
import comm.GateWay.SMSgateway;

import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;
import java.util.regex.Matcher;;

@Service("NS")
public class NotificatoinService {
	private static final Queue<Notification> EmailQueue = new LinkedList<Notification>();
	private static final Queue<Notification> SMSQueue = new LinkedList<Notification>();
	private static DBconnection myDB;
	private static NotificationGateway sender;

	public NotificatoinService() {
		myDB = new SqlConnection();
		try {
			ResultSet resultSet = myDB.getStatement()
					.executeQuery("SELECT * FROM notifqueue WHERE statue = 'Ready_to_send' or statue = 'corrupted'  ;");
			while (resultSet.next()) {
					Languages l = Languages.valueOf(resultSet.getString("Language"));
					Template t = new Template(resultSet.getString("Subjet"), resultSet.getString("Content"), l, 1);
					State s = State.valueOf(resultSet.getString("statue"));
					if (resultSet.getInt("ch") == 1) {
						Notification N = new Notification(t, true);
						N.setState(s);
						N.setId(resultSet.getInt("ID"));
						N.setReceiver(resultSet.getString("receiver"));
						SMSQueue.add(N);
					} else {
						Notification N = new Notification(t, false);
						N.setState(s);
						N.setId(resultSet.getInt("ID"));
						N.setReceiver(resultSet.getString("receiver"));
						EmailQueue.add(N);
				}
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public Notification BuildNotification(Template t, String[] PH, boolean ch) {
		Notification Noti = new Notification(t, ch);
		String toModify = Noti.getTemp().getContent();
		Noti.setPlaceHolders(PH);
		if (PH.length == Noti.getPlaceHolders().length) {
			for (int i = 1; i < PH.length; i++) {
				toModify = toModify.replaceFirst("\\{([^}]*.?)}", PH[i]);
				System.out.println(toModify);
			}
			Noti.getTemp().setContent(toModify);
			Noti.setReceiver(PH[0]);
			Pattern pattern = Pattern.compile("\\{([^}]*.?)}");
			Matcher matcher = pattern.matcher(toModify);
			Noti.setState((matcher.results().count()==0)? State.Ready_to_send : State.corrupted);
			return Noti;
		}
		return null; // if empty
	}

	public Notification AddNotification(int id, String[] PH, boolean ch) {

		try {
			var resultSet = myDB.getStatement().executeQuery("SELECT * FROM template WHERE ID = " + "'" + id + "';");

			if (resultSet.next()) {
				Languages l = Languages.valueOf(resultSet.getString("Language"));
				Template t = new Template(resultSet.getString("Subjet"), resultSet.getString("Content"), l,
						resultSet.getInt("numofPlace"));
				t.setID(resultSet.getInt("ID"));
				Notification N = BuildNotification(t, PH, ch);
				int ch1 = (ch) ? 1 : 0;
				myDB.getStatement()
						.executeUpdate("INSERT INTO notifqueue (Subjet,Content,receiver,Language,ch,statue) VALUES(" + "'"
								+ N.getTemp().getSubject() + "'," + "'" + N.getTemp().getContent() + "'," + "'" + PH[0]
								+ "'," + "'" + N.getTemp().getSupportedLanguages().toString() + "'," + "'" + ch1 + "',"
								+ "'" + N.getState().toString() + "');");
				N.setReceiver(PH[0]);
				if (N.getChannel()) {
					SMSQueue.add(N);

				} else 
					EmailQueue.add(N);				
				return N;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public boolean dequeueSMS() {
		if(SMSQueue.isEmpty())
			return false;
		var toBeSent = SMSQueue.poll();
		if(toBeSent.getState().equals(State.corrupted)) {
			toBeSent.setState(State.Failure);
			updateState(toBeSent);
			return false;
		}
		sender = new SMSgateway();
		var sent =  sender.send(toBeSent);
		updateState(toBeSent);
		if(sent) {
			System.out.println(toBeSent.toString());
			return true;
		}
		return false;
	}
	public void updateState(Notification n) {
		
		try {
			myDB.getStatement().executeUpdate("UPDATE notifqueue SET statue = '"+n.getState()+ "'WHERE ID = '"+n.getId()+"';");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public boolean dequeueEmail() {
		if(EmailQueue.isEmpty())
			return false;
		var toBeSent = EmailQueue.poll();
		if(toBeSent.getState()==State.corrupted) {
			toBeSent.setState(State.Failure);
			updateState(toBeSent);
			return false;
		}
		sender = new EmailGateway();
		var sent = sender.send(toBeSent);
		updateState(toBeSent);
		if(sent) {
			System.out.println(toBeSent.toString());
			return true;
		}
		return false;
	}
	
}