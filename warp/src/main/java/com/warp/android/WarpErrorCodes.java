package com.warp.android;

public enum WarpErrorCodes {

    MISSING_CONFIGURATION(300),
    INTERNAL_SERVER_ERROR(100),
    QUERY_ERROR(101),
    INVALID_CREDENTIALS(102),
    INVALID_SESSION_TOKEN(103),
    INVALID_OBJECT_KEY(104),
    INVALID_POINTER(105),
    FORBIDDEN_OPERATION(106),
    USERNAME_TAKEN(107),
    EMAIL_TAKEN(108),
    INVALID_API_KEY(109),
    MODEL_NOT_FOUND(110),
    FUNCTION_NOT_FOUND(111),
    //
    SUCCESS(200);

    private int code;
    WarpErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() { return code; }
}
