package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Container used to store messages read from an inputStream (and
 * therefore possibly from a file).
 */
public final class MessageStore {
    private List<String> messages;
    private Random messageRandomizer;

    public MessageStore(InputStream inputMessages) throws IOException {
        messages = new ArrayList<String>();
        messageRandomizer = new Random(System.currentTimeMillis());

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputMessages));

        String message;
        while((message = reader.readLine()) != null) {
            messages.add(message);
        }
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getRandomMessage() throws EmptyStoreException {
        if(messages.isEmpty()) {
            throw new EmptyStoreException();
        }

        return messages.get(messageRandomizer.nextInt(messages.size()));
    }
}
