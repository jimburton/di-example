package CI346.diexample;

/**
 * Created by jb259 on 31/10/16.
 */
public class WebApplication implements ServiceConsumer {

    private MessageService service;

    public WebApplication(MessageService service){
        this.service=service;
    }

    @Override
    public void processMessages(String msg, String rec){
        // do some msg validation, manipulation logic etc
        this.service.sendMessage(msg, rec);
    }
}
