package Models;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.Random;
public class SmsSender {
    public static final String ACCOUNT_SID = "ACa7469fb2ca6d0555b421285527faab23";
    public static final String AUTH_TOKEN = "0b214c21c6fa664168ecc8a2b92233fe";
    public static String verificationCode; //besh nanesthakesh instance juste naayet lel classe.sendverif

    public static void sendVerificationCode(String toPhoneNumber,String nom) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String twilioPhoneNumber = "+18064508587"; // Remplacez par votre numéro de téléphone Twilio réel
        // Générer un code aléatoire
        Random random = new Random();
        verificationCode = String.format("%04d", random.nextInt(10000));
        String messageBody = "Bonjour,Merci pour votre confiance ,Votre demande pour RDV avec le mécanicien"+" "+nom+" est en cours de traitement nous reviendrons pour vous le plutot possible. Gardez le code de confirmation.: " + verificationCode;
        Message message = Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(twilioPhoneNumber),
                        messageBody)
                .setFrom(new PhoneNumber(twilioPhoneNumber)) // Utilisez votre numéro de téléphone Twilio réel ici
                .create();
        System.out.println("Message SID: " + message.getSid());
    }

}
