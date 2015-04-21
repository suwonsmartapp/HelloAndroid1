package com.suwonsmartapp.hello.chat.server;

import java.io.DataOutput;

public class ClientInfo {
	private String nickName;
	private String ip;
	private DataOutput output;

	public ClientInfo() {
	}

	public ClientInfo(String nickName, String ip, DataOutput output) {
		this.nickName = nickName;
		this.output = output;
	}
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public DataOutput getOutput() {
		return output;
	}
	public void setOutput(DataOutput output) {
		this.output = output;
	}
}
