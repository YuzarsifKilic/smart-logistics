package com.yuzarsif.productcatalogueservice.exception;

public final class ExceptionMessages {

    private ExceptionMessages() {
        // Utility class, prevent instantiation
    }

    // User not found messages
    public static final String USER_NOT_FOUND_BY_ID = "User not found with ID: %s";
    public static final String USER_NOT_FOUND_BY_EMAIL = "User not found with email: %s";

    // Email already exists messages
    public static final String EMAIL_ALREADY_EXISTS = "Email address already in use: %s";

    // User creation messages
    public static final String USER_CREATION_FAILED = "Failed to create user: %s";

    // User update messages
    public static final String USER_UPDATE_FAILED = "Failed to update user with ID: %s";

    // Password change messages
    public static final String PASSWORD_CHANGE_FAILED = "Failed to change password for user ID: %s";

    // User enable/disable messages
    public static final String USER_STATUS_CHANGE_FAILED = "Failed to change status for user ID: %s";

    // User deletion messages
    public static final String USER_DELETION_FAILED = "Failed to delete user with ID: %s";

    // Validation messages
    public static final String INVALID_PASSWORD = "Password cannot be null or empty";
    public static final String INVALID_ENABLED_STATUS = "Enabled status cannot be null";
    public static final String INVALID_USER_DATA = "Invalid user data provided";
}
