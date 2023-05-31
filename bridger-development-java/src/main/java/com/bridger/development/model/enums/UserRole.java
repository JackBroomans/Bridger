package com.bridger.development.model.enums;

/**
 * <strong>User Role</strong><br>
 * Enumerates the roles which can be assigned to a participant.
 */
public enum UserRole {
    ADMIN,
    SUPPORT,
    MODERATOR,
    USER;

    UserRole() {}

    /**
     * <strong>getDefaultSetting()</strong><br>
     * The default setting is immutable, so the default setting can be returned immediately.<br>
     * @return The default user role which is UserRole.USER.
     */
    public static UserRole getDefaultSetting() { return UserRole.USER; }

    /**
     * <strong>toString()</strong><br>
     * Present the usser role in a more readable way.
     */
    @Override
    public String toString() {
        return  "User Role" +
                "\n\tElement: " + this.name();
    }
}
