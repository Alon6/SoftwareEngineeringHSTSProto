package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class InputErrorEvent {
    private String message;

    public String getMessage() {
        return message;
    }

    public InputErrorEvent(String message) {this.message = message;}
}
