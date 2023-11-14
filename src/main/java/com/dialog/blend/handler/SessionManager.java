package com.dialog.blend.handler;

import com.dialog.blend.model.InitRequest;
import com.dialog.blend.dto.InitResponse;
import com.dialog.blend.dto.QueryConversation;
import com.dialog.blend.service.DiscussionHandler;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    private static SessionManager instance;

    private Map<String, DiscussionHandler> sessions;

    private Gson gson;

    private SessionManager() {
        this.sessions = new HashMap<>();
        this.gson = new Gson();
    }

    public static SessionManager getSessionManager() {
        if (instance == null)
            instance = new SessionManager();
        return instance;
    }

    public ResponseEntity<String> initDiscussion(InitRequest initRequest) {
        String session = new String(Base64.getEncoder().encode(initRequest.getRecording().getBytes()));

        if (sessions.isEmpty() || !sessions.containsKey(session)) {
            sessions.put(session, new DiscussionHandler());
        }
        return new ResponseEntity<>(gson.toJson(new InitResponse(session, initRequest)), HttpStatus.OK);
    }

    public ResponseEntity<String> loadDiscussion(QueryConversation conversation) {

        if (!sessions.containsKey(conversation.getSessionId()))
            return new ResponseEntity<>("No Session found to Handle request,please create first", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(sessions.get(conversation.getSessionId()).createResponse(conversation),
                HttpStatus.OK);
    }

    public ResponseEntity<String> disposeDiscussion(String session) {
        if (sessions.remove(session) != null)
            return new ResponseEntity<>("The session has been disposed of successfully.", HttpStatus.OK);
        else
            return new ResponseEntity<>("No such active session was located!", HttpStatus.NOT_FOUND);
    }
}
