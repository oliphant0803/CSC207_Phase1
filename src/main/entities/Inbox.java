package main.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * The Inbox holds the messages of a user;
 *
 * @author David Zhao
 * @version 2.0
 * @since 2020-11-13
 */
public class Inbox {

    private String id;
    private final List<String> messages = new LinkedList<String>();
    private String user;

    /**
     * Empty constructor for deserialization
     */
    public Inbox() {

    }

    /**
     * Class constructor that defaults to an empty chatroom.
     */
    public Inbox(String user) {
        this.id = UUID.randomUUID().toString();
        this.user = user;

    }


    /**
     * Gets the unique identifier of the chatroom
     *
     * @return the String of the chatroom
     */
    public String getId() {
        return this.id;
    }

    public String getUser() {
        return this.user;
    }

    /**
     * Adds a message to the chatroom
     *
     * @param message String of the message to add
     */
    public void addMessage(String message) {
        this.messages.add(message);
    }

    /**
     * Gets the messages in the room
     *
     * @return list of UUIDs of the messages in the room.
     */
    public List<String> getMessages() {
        return this.messages;
    }


}
