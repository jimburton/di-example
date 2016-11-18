package CI346.diexample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jb259 on 31/10/16.
 */
public class WebAppTest {

    private MessageServiceInjector injector;
    @Before
    public void setUp(){
        injector = () -> {
                return new WebApplication(
                        (msg, rec) ->
                        System.out.println("[TEST] Sending " + msg + " to " + rec));

        };
    }
    /*public void setUp(){
        injector = new EmailServiceInjector() {
            @Override
            public ServiceConsumer getConsumer() {
                return new WebApplication(new MessageService() {
                    @Override
                    public void sendMessage(String msg, String rec) {
                        System.out.println("[TEST] Sending " + msg + " to " + rec);
                    }
                });
            }
        };
    }*/

    @Test
    public void test() {
        ServiceConsumer consumer = injector.getConsumer();
        consumer.processMessages("Hello World", "j.burton@brighton.ac.uk");
    }

    @After
    public void tearDown(){
        injector = null;
    }
}
