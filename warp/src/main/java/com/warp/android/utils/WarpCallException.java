package com.warp.android.utils;

/**
 * Created by TrieNoir on 16/01/2017.
 */

public class WarpCallException extends Exception {

    private int code;
    private Throwable throwable;
    private String message, reason;

    public WarpCallException(Throwable throwable, int code, String message) {
        this.code = code;
        this.throwable = throwable;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getReason() {
        return s(code);
    }

    private String s(int code) {
        /*
        Missing Configuration: 300 - a parameter is missing or a process contains been omitted
        Internal Sever Error: 100 - an unexpected error occurred internally
        Query Error: 101 - an error occurred when querying the database
        Invalid Credentials: 102 - credentials for a user operation are missing or are invalid
        Invalid Session Token: 103 - session token is not set or is invalid
        Invalid Object Key: 104 - an object could not be saved because a key failed validations
        Invalid Pointer: 105 - a given pointer is invalid
        Forbidden Operation: 106 - a user is not authorized to perform a requested operation
        Username Taken: 107 - the provided username is already taken
        Email Taken: 108 - the provided email is already taken
        Invalid API Key: 109 - an API Key is not set or the given key is invalid
        Model Not Found: 110 - the requested model/class does not exist
        Function Not Found: 111 - the requested function does not exist
         */
        String message = "";
        switch (code) {
            case 300: message = "Missing Configuration (code:300), A parameter is missing or a process contains been omitted."; break;
            case 100: message = "Internal Sever Error (code:100), An unexpected error occurred internally."; break;
            case 101: message = "Query Error (code:101), An error occurred when querying the database."; break;
            case 102: message = "Invalid Credentials (code:102), Credentials for a user operation are missing or are invalid."; break;
            case 103: message = "Invalid Session Token (code:103), Session token is not set or is invalid."; break;
            case 104: message = "Invalid Object Key (code:104), An object could not be saved because a key failed validations."; break;
            case 105: message = "Invalid Pointer (code:105), A given pointer is invalid."; break;
            case 106: message = "Forbidden Operation (code:106), A user is not authorized to perform a requested operation."; break;
            case 107: message = "Username Taken (code:107), The provided username is already taken."; break;
            case 108: message = "Email Taken (code:108), The provided email is already taken."; break;
            case 109: message = "Invalid API Key (code:109), An API Key is not set or the given key is invalid."; break;
            case 110: message = "Model Not Found (code:110), The requested model/class does not exist."; break;
            case 111: message = "Function Not Found (code:111), The requested function does not exist."; break;
        }

        return message;
    }
}
