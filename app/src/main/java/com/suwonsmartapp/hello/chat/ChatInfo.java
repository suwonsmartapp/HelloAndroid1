
package com.suwonsmartapp.hello.chat;

/**
 * Created by junsuk on 15. 4. 20..
 */
public class ChatInfo implements ChatHistory {

    boolean isMe;
    String nickName;
    String message;
    String time;

    public ChatInfo() {
    }

    public ChatInfo(String nickName, String message, String time) {
        this.message = message;
        this.nickName = nickName;
        this.time = time;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setIsMe(boolean isMe) {
        this.isMe = isMe;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
