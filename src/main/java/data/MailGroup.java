package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sydney on 05.04.17.
 *
 * Container for various mail addresses, and a random sender in the mail group
 */
public final class MailGroup {
    private List<String> mailAddresses;
    private String senderMail;

    public MailGroup(InputStream input) throws IOException {
        mailAddresses = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        Random random = new Random(System.currentTimeMillis());

        String mail;
        while((mail = reader.readLine()) != null) {
            mailAddresses.add(mail);
        }

        int randomSender = random.nextInt(mailAddresses.size());
        senderMail = mailAddresses.get(randomSender);
        mailAddresses.remove(randomSender);
    }

    public String getSender() {
        return senderMail;
    }

    public List<String> getMailAddresses() {
        return mailAddresses;
    }
}
