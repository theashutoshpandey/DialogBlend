package com.dialog.blend.dto;

public class QueryConversation {

    private String sessionId;
    private long timeStamp;
    private String message;
    private String botType;
    private String language;
    private String response;
    private String clientId;
    private String appId;
    private int hangup = 0;
    private Object additionalParams;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBotType() {
        return botType;
    }

    public void setBotType(String botType) {
        this.botType = botType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getResponse() {
        return response;
    }

    public QueryConversation setResponse(String response) {
        this.response = response;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public int getHangup() {
        return hangup;
    }

    public QueryConversation setHangup(int hangup) {
        this.hangup = hangup;
        return this;
    }

    public Object getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(Object additionalParams) {
        this.additionalParams = additionalParams;
    }
}
