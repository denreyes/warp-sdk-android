package com.warp.android;

public enum Constraint {

    /*
    Here are the available constraints:
    - eq: equal to
    - neq: not equal to
    - gt: greater than
    - gte: greater than or equal to
    - lt: less than
    - lte: less than or equal to
    - ex: is null/is not null (value is either true or false)
    - in: contained in array
    - nin: not contained in array
    */

    EQ("eq"),
    NEQ("neq"),
    GT("gt"),
    GTE("gte"),
    LT("lt"),
    LTE("lte"),
    EX("ex"),
    IN("in"),
    NIN("nin");

    private String code;
    Constraint(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
