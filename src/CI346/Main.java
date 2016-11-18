package CI346;

import CI346.diexample.EmailServiceInjector;
import CI346.diexample.MessageServiceInjector;
import CI346.diexample.SMSServiceInjector;
import CI346.diexample.ServiceConsumer;

public class Main {

    public static void main(String[] args) {
        String msg = "Hello World!";
        String email = "j.burton@brighton.ac.uk";
        String phone = "01273652544";
        MessageServiceInjector injector = null;
        ServiceConsumer app = null;

//Send email
        injector = new EmailServiceInjector();
        app = injector.getConsumer();
        app.processMessages(msg, email);

//Send SMS
        injector = new SMSServiceInjector();
        app = injector.getConsumer();
        app.processMessages(msg, phone);
    }
}
