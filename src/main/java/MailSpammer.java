import data.EmptyStoreException;
import data.MailGroup;
import data.MessageStore;
import protocol.Smtp;
import sun.plugin2.message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MailSpammer {

    public static void main(String[] args) throws IOException, EmptyStoreException {
        String server = "mailcl0.heig-vd.ch";
        String domain = "heig-vd.ch";
        int port = 25;

        Socket clientSocket = new Socket(server, port);

        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter output = new PrintWriter(clientSocket.getOutputStream());

        MailGroup mailgroup = new MailGroup(file_emails);
        List<String> mailList = new ArrayList<String>();

        MessageStore messageStore = new MessageStore(file_messages);

        /* Random emails sending */

        output.println(Smtp.CMD_EHLO + domain);
        output.println(Smtp.CMD_MAIL_FROM + mailgroup.getSender());

        for(String mail : mailList) {
            output.println(Smtp.CMD_RCPT_TO + mail);
        }

        output.println(Smtp.CMD_DATA);

        output.println(messageStore.getRandomMessage());

        output.println(Smtp.CMD_END_OF_DATA);
        output.flush();
    }

}
