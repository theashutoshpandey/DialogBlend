package com.dialog.blend.model;

public class QueryRequest {

    private String userId;
    private String sessionId;
    private String msg;

    public String getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "QueryRequest{" +
                "userId='" + userId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
