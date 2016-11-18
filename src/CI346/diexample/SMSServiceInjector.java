package CI346.diexample;

/**
 * Created by jb259 on 31/10/16.
 */
public class SMSServiceInjector implements MessageServiceInjector {

    @Override
    public ServiceConsumer getConsumer() {
        return new WebApplication(new SMSService());
    }
}