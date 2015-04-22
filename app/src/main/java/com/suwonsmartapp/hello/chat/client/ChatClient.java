package com.suwonsmartapp.hello.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
//	private final static String SERVER_HOST = "suwonsmartapp.iptime.org";
	private final static String SERVER_HOST = "192.168.0.222";
	private final static int SERVER_PORT = 5000;
	
	private Socket mSocket;

	private ClientReceiver mReceiveThread;

	private ClientCallback mClientCallback;

	public interface ClientCallback {
		void onReceiveMessage(String message);
		String getNickName();
	}

	public void setClientCallback(ClientCallback callback) {
		mClientCallback = callback;
	}

	public static void main(String[] args) {
		new ChatClient().connect();
	}
	
	public void connect() {
		connect(SERVER_HOST, SERVER_PORT);
	}

	public void connect(String serverHost, int serverPort) {
		try {
			mSocket = new Socket(serverHost, serverPort);

			String nickName = "무명씨";
			if (mClientCallback != null) {
				nickName = mClientCallback.getNickName();
			}

			mReceiveThread = new ClientReceiver(mSocket, nickName);
			mReceiveThread.start();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			mReceiveThread = null;
			mSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) {
		mReceiveThread.sendMessage(message);
	}

	class ClientReceiver extends Thread {
		
		private DataInputStream mInputStream;
		private DataOutputStream mOutputStream;
//		ObjectInputStream	// Java 전용
//		ObjectOutputStream	// Java 전용
		
		public ClientReceiver(Socket socket, String nickName) {
			try {
				mInputStream = new DataInputStream(socket.getInputStream());
				mOutputStream = new DataOutputStream(socket.getOutputStream());
				
				mOutputStream.writeUTF(nickName);
				mOutputStream.flush();

				System.out.println("id : " + nickName + "접속 완료");
				
//				try {
//					Thread.sleep(5000);
//					mOutputStream.writeUTF("exit");
//					mOutputStream.flush();
//					System.out.println("id : " + nickName + "접속 종료");
//					System.exit(0);
//				} catch (InterruptedException e) {
//				}
				
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("writeUTF IOException");
			}
		}

		public void sendMessage(String message) {
			if (mOutputStream != null) {
				try {
					mOutputStream.writeUTF(message);
					mOutputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		@Override
		public void run() {
			try {
				// 계속 듣기만
				while (mInputStream != null) {
//					System.out.println(mInputStream.readUTF());
					if (mClientCallback != null) {
						mClientCallback.onReceiveMessage(mInputStream.readUTF());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 접속 종료시	
				mSocket = null;
				
			}	
		}
	}
}
