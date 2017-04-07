import data.EmptyStoreException;
import data.MailGroup;
import data.MessageStore;
import protocol.Smtp;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailSpammer {
    private static Logger LOG;

    public static void main(String[] args) throws IOException, EmptyStoreException {
        LOG = Logger.getLogger(MailSpammer.class.getName());

        String server = "localhost";
        String domain = "sydney";
        int port = 2525;

        Socket clientSocket = new Socket(server, port);

        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter output = new PrintWriter(clientSocket.getOutputStream());

        MailGroup mailgroup = new MailGroup(new FileInputStream("resources/grp-1"));
        List<String> mailList = mailgroup.getMailAddresses();

        MessageStore messageStore = new MessageStore(new FileInputStream("resources/messages"));

        /* Random emails sending */

        LOG.log(Level.INFO,input.readLine());
        output.println(Smtp.CMD_EHLO + domain + '\r');
        output.flush();
        String response;
        boolean cont = false;
        while(!cont) {
            response = input.readLine();
            LOG.log(Level.INFO, response);
            if(response.contains("250 ")) {
                cont = true;
            }
        }

        output.println(Smtp.CMD_MAIL_FROM + mailgroup.getSender() + '\r');
        output.flush();
        LOG.log(Level.INFO, input.readLine());

        for(String mail : mailList) {
            output.println(Smtp.CMD_RCPT_TO + mail + '\r');
            output.flush();
            LOG.log(Level.INFO, input.readLine());
        }

        output.println(Smtp.CMD_DATA + '\r');
        output.flush();
        LOG.log(Level.INFO, input.readLine());

        output.println("From : " + mailgroup.getSender() + '\r');
        output.flush();
        output.print("To : ");
        for(String mail : mailList) {
            output.print(mail + ",");
        }
        output.println('\r');
        output.flush();
        output.println("Subject : Hello ;)" + '\r');
        output.println('\r');

        output.println(messageStore.getRandomMessage() + '\r');
        output.flush();

        output.println("" + '\r');
        output.flush();
        output.println(Smtp.CMD_END_OF_DATA + '\r');
        output.flush();
        LOG.log(Level.INFO, input.readLine());

        output.println(Smtp.CMD_QUIT + '\r');
        output.flush();

        clientSocket.close();
        input.close();
        output.close();

    }

}
