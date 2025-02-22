package main.controllers;

import main.entities.Room;
import main.entities.User;
import main.usecases.EventBuilder;
import main.usecases.RoomManager;
import main.usecases.UsersManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Apart from the responsibilities listed in the UserController, the OrganizerController handles advance actions
 * which could be done by an organizer: checking and managing events, creating speaker accounts, and creating
 * rooms.
 *
 * @author Ruoming Ren
 * @version 2.1
 * @since 2020-11-10
 */

public class OrganizerController extends AttendeeController {

    UsersManager usersManager;
    RoomManager roomManager;


    /**
     * Constructor of OrganizerController for a logged in user.
     *
     * @param programController pre-defined programController
     */
    public OrganizerController(ProgramController programController) {
        super(programController);
        this.usersManager = programController.getUsersManager();
        this.roomManager = programController.getRoomManager();
    }

    /**
     * create a new room given roomNum and capacity
     *
     * @param roomNum  the roomNum of the room
     * @param capacity the capacity of the room
     * @return true if the room have been successfully created
     */
    public boolean createRoom(int roomNum, int capacity) {
        return roomManager.addRoom(roomNum, capacity);
    }


    /**
     * Build an event by giving the title, time, room ID, and speaker ID. The room must already exist.
     *
     * @param title   the title of the event
     * @param time    the time of the event
     * @param roomNum the roomNum of the event
     * @param speaker the speaker of the event
     * @return true if the event be created successfully.
     */
    public boolean createEvent(String title, LocalDateTime time, int roomNum, String speaker) {
        if (roomManager.getRoomGivenRoomNum(roomNum) == null) {
            return false;
        }
        EventBuilder newEvent = new EventBuilder();
        newEvent.setTitle(title);
        newEvent.setTime(time);
        newEvent.setRoom(roomManager.getRoomIDGivenRoomNum(roomNum));
        newEvent.setSpeaker(speaker);
        return eventController.createEvent(newEvent);
    }

    /**
     * Remove the event from the schedule and return true if the event have been successfully removed
     *
     * @param event the uuid of the event
     * @return true if the event have been successfully removed
     */
    public boolean removeEvent(String event) {
        return eventController.removeEvent(event);
    }

    /**
     * Update the time of the event
     *
     * @param event the uuid of the event
     * @param time  the new time of the event
     * @return true if the event's time have been successfully update or the new time has no difference with the
     * old time. Return false if the new time is conflict with other event and the time haven't been successfully
     * updated.
     */
    public boolean updateTime(String event, LocalDateTime time) {
        String roomId = eventController.getSingleEvent(event).getRoomID();
        return eventController.updateEventInfo(event, time, roomId);
    }

    /**
     * change the speaker of the event
     *
     * @param event   the uuid of the event
     * @param speaker the new speaker of the event
     * @return true if the new speaker have been successfully updated or the new speaker is already inside the
     * old speakers' list. Return false if the time of the new speaker is not available
     */
    public boolean updateSpeaker(String event, String speaker) {

        return eventController.removeSpeaker(event, eventController.getSingleEvent(event).getSpeakerID())
                && eventController.addSpeaker(event, speaker);

    }

    /**
     * update the room of the event
     *
     * @param event   the uuid of the event
     * @param roomNum the new room of the event
     * @return true if room of the event have been successfully update of the new room has no difference with the
     * old room. Return false if the room has been occupied at that time
     */
    public boolean updateRoom(String event, int roomNum) {
        LocalDateTime time = eventController.getSingleEvent(event).getTime();
        String room = roomManager.getRoomIDGivenRoomNum(roomNum);
        return eventController.updateEventInfo(event, time, room);
    }

    /**
     * create a new speaker account
     *
     * @param userName the user name of the speaker
     * @param password the password of the speaker
     * @return true if the account have been successfully created
     */
    public boolean createSpeaker(String userName, String password) {
        return usersManager.addUser(userName, password, "Speaker");
    }


    public List<String> getAllSpeakers() {
        List<String> speakers = new ArrayList<>();
        for (String user : usersManager.getAllUsers()) {
            // check if he is a speaker
            if (this.usersManager.fetchRole(user).equals("Speaker")) {
                speakers.add(user);
            }
        }
        return speakers;
    }

    public String speakerToString() {
        String ret = "";
        int count = 1;
        for (String speakerId : this.getAllSpeakers()) {
            User speaker = usersManager.fetchUser(speakerId);
            ret = ret + count + ". " + speaker.getUsername() + "\n";
            count++;
        }
        return ret;
    }

    public List<Integer> getAllRooms() {
        return this.roomManager.getAllRooms();
    }

    public String roomToString() {
        String ret = "";
        int count = 1;
        for (Room room : roomManager.getAllRoomsObject()) {
            ret = ret + count + ". Room #" + room.getRoomNum() + "\n";
            count++;
        }
        return ret;
    }

    public EventController getEventController() {
        return eventController;
    }


}
