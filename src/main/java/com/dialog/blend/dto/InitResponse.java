package com.dialog.blend.dto;

import com.dialog.blend.model.InitRequest;

public class InitResponse {

    private String sessionId;
    private String clientId;
    private String botId;
    private String mobile;
    private String recording;

    public InitResponse(String sessionId, InitRequest initRequest) {
        this.sessionId = sessionId;
        this.clientId = initRequest.getClientId();
        this.botId = initRequest.getBotId();
        this.mobile = initRequest.getMobile();
        this.recording = initRequest.getRecording();
    }
}
