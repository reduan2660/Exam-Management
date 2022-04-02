package dev.examsmanagement;

import dev.examsmanagement.modal.User;

public class Session {
    private static User currentUser = null;

    public static void setCurrentUser(User _currentUser) {
        currentUser = _currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logout() { currentUser = null; }
}
