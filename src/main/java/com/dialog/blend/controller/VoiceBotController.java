package com.dialog.blend.controller;

import com.dialog.blend.model.DisposeRequest;
import com.dialog.blend.model.InitRequest;
import com.dialog.blend.dto.QueryConversation;
import com.dialog.blend.handler.SessionManager;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class VoiceBotController {

        @PostMapping(value = "/init", produces = "application/json")
        @ResponseBody
        public ResponseEntity<String> init(@RequestBody InitRequest initRequest) {
                System.out.println(initRequest.toString());
                return SessionManager.getSessionManager().initDiscussion(initRequest);
        }

        @PostMapping(value = "/query", produces = "application/json") // Add the endpoint if needed
        @ResponseBody
        public ResponseEntity<String> query(@RequestBody QueryConversation conversation) {
                // System.out.println(request.toString());
                return SessionManager.getSessionManager().loadDiscussion(conversation);

        }

        @PostMapping(value = "/disclose", produces = "application/json")
        @ResponseBody
        public ResponseEntity<String> dispose(@RequestBody DisposeRequest disposeRequest) {
                System.out.println(new Gson().toJson(disposeRequest));
                return SessionManager.getSessionManager().disposeDiscussion(disposeRequest.getSessionId());
        }

}
