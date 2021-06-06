package comm.NotiAPI;

import comm.NotficationModel.Notification;
import comm.NotificationService.NotificatoinService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotificatoinController {
	
	private final NotificatoinService notifcatonService;

	public NotificatoinController( @Qualifier("NS") NotificatoinService ns) {
		notifcatonService = ns;
	}

	//localhost:8080/Noti/API/5?ch=SMS
	@RequestMapping(method = RequestMethod.GET, value = "Noti/API/{id}")
	public Notification AddNotification( @PathVariable("id") int id, @RequestParam(name="ch",required = true) String ch,
                                         @RequestBody String[] PH) {
		return (notifcatonService.AddNotification(id, PH, ch.equals("SMS")));

	}
}
