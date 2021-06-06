package comm;

import java.util.Scanner;

import comm.NotificationService.*;;

public class Test {
    public static void main( String[] args ) {
    	Scanner input = new Scanner(System.in);
    	NotificatoinService service = new NotificatoinService(); 
    	while(true) {
    		System.out.println("Dequeue: 1]SMS\n2]Email\n3]Exit");
    		int op = input.nextInt();
    		if(op==1)
    			service.dequeueSMS();
    		else if(op==2) 
    			service.dequeueEmail();
    		else if(op==3)
    			break;
    		else 
    			System.out.println("invalid:D");
    	}
       
        
    }
}
