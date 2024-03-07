package edu.esprit.Controllers;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class TwilioSms {
    // Set your Twilio Account SID and Auth Token
    public static final String ACCOUNT_SID = "YOUR_ACCOUNT_SID";
    public static final String AUTH_TOKEN = "YOUR_AUTH_TOKEN";

    // Set your Twilio phone number
    public static final String TWILIO_PHONE_NUMBER = "YOUR_TWILIO_PHONE_NUMBER";

    public static void sendSMS(String toPhoneNumber, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        messageBody)
                .create();

        System.out.println("SMS sent successfully to " + toPhoneNumber + " with message: " + messageBody);
    }
}
