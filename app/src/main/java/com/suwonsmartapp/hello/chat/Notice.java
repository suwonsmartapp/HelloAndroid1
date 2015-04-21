
package com.suwonsmartapp.hello.chat;

/**
 * Created by junsuk on 15. 4. 20..
 */
public class Notice implements ChatHistory {
    String message;

    public Notice(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
