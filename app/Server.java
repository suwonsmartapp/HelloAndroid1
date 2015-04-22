import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * remotecontroller.client/RemoteControllerActivity.java 를 실행하기 위한 서버 파일
 */
public class Server extends Thread {
	private static final int PORT = 6000;
	private ServerSocket mServerSocket;
	
	private DataInputStream mInputStream;
	
	private Robot mRobot;
	
	@Override
	public void run() {
		try {
			try {
				mRobot = new Robot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("연결 대기");
			// 계속 듣기만
			mServerSocket = new ServerSocket(PORT);
			Socket socket = mServerSocket.accept();
			System.out.println("연결 성공");
			
			mInputStream = new DataInputStream(socket.getInputStream());
			
			while (mInputStream != null) {
				String input = mInputStream.readUTF();
				
				switch (input) {
				case "u":
					mRobot.keyPress(KeyEvent.VK_UP);
					break;
				case "d":
					mRobot.keyPress(KeyEvent.VK_DOWN);
					break;
				case "l":
					mRobot.keyPress(KeyEvent.VK_LEFT);
					break;
				case "r":
					mRobot.keyPress(KeyEvent.VK_RIGHT);
					break;
				case "e":
					mRobot.keyPress(KeyEvent.VK_ENTER);
					break;
				}
			}
		} catch (IOException e) {
			
		} finally {
			// 접속 종료시	
		}
	}
	
	public static void main(String[] args) {
		new Server().start();
	}

}
