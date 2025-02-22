package main.presenters;

import main.usecases.UsersManager;

import java.util.List;

/**
 * AttendeeMessageScreen is the screen where the attendee can choose who to message.
 *
 * @author Yi Tao Li
 * @version 1.0
 * @since 2020-11-13
 */
public class AttendeeMessageScreen {

    protected List<String> recipients;
    protected UsersManager usersManager;

    /**
     * Constructor of an AttendeeMessageScreen.
     */
    public AttendeeMessageScreen(UsersManager usersManager, List<String> recipients) {
        this.usersManager = usersManager;
        this.recipients = recipients;
    }

    /**
     * Displays all users that the attendee can message and prompts them to choose one.
     */
    public void prompt() {
        int count = 1;
        System.out.println("Please select a user to message by entering a number. \n" +
                "Here is a list of users you can message:");
        while (count <= recipients.size()) {
            System.out.println(count + ". " + usersManager.userToString(recipients.get(count - 1)));
            count++;
        }
        System.out.println("Enter 0 to return to the previous screen.");
    }

    /**
     * Temporary prompt for not giving an appropriate input.
     *
     * @param input Invalid input entered by the attendee.
     */
    public void prompt2(String input) {
        System.out.println(input + " was not one of the options.");
    }

    /**
     * Prompts the attendee to enter their message.
     */
    public void messagePrompt() {
        System.out.println("Please enter your message and hit enter. (Enter 0 to return to recipient selection)");
    }

    /**
     * Lets the attendee know that their message has been delivered.
     */
    public void successMessage() {
        System.out.println("Success!");
    }
}
