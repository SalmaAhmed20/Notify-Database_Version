package comm.NotficationModel;

import comm.Model.Template;

public class Notification {
    private Template Temp;
    private int id;
    private String[] PlaceHolders;
    private boolean channel;
    private State state;
    private String receiver;
    public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Override
    public String toString() {
        return "Notification:" +
                "ID: "+id+
                "\nReceiver: "+receiver+
                "\nSubject :" + Temp.getSubject() +
                "\nContent :" + Temp.getContent()+
                "\nchannel=" + ((channel)? "SMS":"Email") +
                "\nstate=" + state +
                "\n\n";
    }

    public Notification( Template t, boolean channel)
     {
         this.Temp=t;
         this.PlaceHolders=new String[Temp.getNumPlaceHolders()];
         this.channel=channel;
     }
    
    public void setChannel(boolean channel) {
		this.channel = channel;
	}

	public String[] getPlaceHolders() {
        return PlaceHolders;
    }

    public void setPlaceHolders( String[] placeHolders ) {
        PlaceHolders = placeHolders;
    }

    public Template getTemp() {
        return Temp;
    }


    public boolean getChannel() {
        return channel;
    }

    public State getState() {
        return state;
    }

    public void setState( State state ) {
        this.state = state;
    }

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
