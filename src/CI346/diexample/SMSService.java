package CI346.diexample;

/**
 * Created by jb259 on 31/10/16.
 */
public class SMSService implements MessageService {

    @Override
    public void sendMessage(String msg, String rec) {
        //logic to send email
        System.out.println("SMS sent to "+rec+ " with Message="+msg);
    }

}
