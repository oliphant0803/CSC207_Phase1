package main.controllers;

import main.entities.User;
import main.screencontrollers.*;
import main.usecases.UserInformationManager;
import main.usecases.UsersManager;

import java.util.UUID;

/**
 * The AuthController handles authentication, maintains a record of who is logged in, and
 * returns the appropriate controller
 */

public class AuthController {

    private String loggedInUser = null;
    private UsersManager usersManager;
    private ProgramController programController;

    /**
     * Default constructor for when there is no pre-defined UsersManager
     */
    public AuthController(ProgramController programController) {
        this.usersManager = new UsersManager();
        this.programController = programController;
    }

    /**
     * Constructor for when these is a pre-defined UsersManager
     *
     * @param usersManager pre-defined UsersManager
     */
    public AuthController(ProgramController programController, UsersManager usersManager) {
        this.usersManager = usersManager;
        this.programController=programController;
    }

    /**
     * Set the UsersManager of the AuthController
     *
     * @param usersManager
     */
    public void setUsersManager(UsersManager usersManager) {
        this.usersManager = usersManager;
    }

    /**
     * Logins the user and stores the logged in user's String in the private store
     *
     * @param username Username
     * @param password Password
     * @return whether or not the user is logged in
     */
    public boolean login(String username, String password) {
        this.loggedInUser = this.usersManager.authenticateUser(username, password);
        return this.isLoggedIn();
    }

    /**
     * Logs out by resetting the current logged in user
     */
    public void logout() {
        this.loggedInUser = null;
        this.programController.setNewScreenController(new AnonymousScreenController(this.programController));
        this.programController.clearScreenHistory();
    }

    /**
     * Checks if there is a logged in user
     *
     * @return whether or not the user is logged in
     */
    public boolean isLoggedIn() {
        return (this.loggedInUser != null);
    }

    /**
     * Fetches the current logged in user
     *
     * @return String of the current logged in user
     */
    public String fetchLoggedInUser() {
        return this.loggedInUser;
    }

    /**
     * Registers a new user
     *
     * @param username Username
     * @param password Password
     * @param userType The type of the User
     * @return whether or not the user was successfully registered
     */
    public boolean registerUser(String username, String password, String userType) {
        return this.usersManager.addUser(username, password, userType);
    }

    public ScreenController getScreenController() {
        User currentUser = this.usersManager.fetchUser(this.loggedInUser);
        UserInformationManager userInfo = new UserInformationManager(currentUser);
        String userRole = userInfo.getType();

        switch(userRole){
            case "Attendee": return new AttendeeScreenController(this.programController);
            case "Organizer": return new OrganizerScreenController(this.programController);
            case "Speaker": return new SpeakerScreenController(this.programController);
            default: return null;
        }
    }


}
