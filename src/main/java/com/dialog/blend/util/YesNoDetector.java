package com.dialog.blend.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YesNoDetector {

    private List<String> yesWords;
    private List<String> noWords;

    public YesNoDetector() {
        // Initialize the yes and no word lists
        yesWords = new ArrayList<>(
                Arrays.asList("yes", "sure", "absolutely", "indeed", "okay", "yup", "hn", "ok", "yeah",
                        "agreed", "fine", "why not"));

        noWords = new ArrayList<>(Arrays.asList("no", "nope", "nah", "never", "not at all", "nahi", "nay"));

    }

    public String detectResponse(String userResponse) {
        // Convert the user response to lowercase for case-insensitive comparison
        userResponse = userResponse.replaceAll("[^\\w]|\\d", "").trim().toLowerCase();
        // Check if the user's response is a yes or no
        if (yesWords.contains(userResponse)) {
            return "Yes";
        } else if (noWords.contains(userResponse)) {
            return "No";
        } else {
            return "Other";
        }
    }

}
