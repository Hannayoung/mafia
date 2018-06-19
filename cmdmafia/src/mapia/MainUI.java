package mapia;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

public class MainUI {
	String user;
	boolean vote=false;
	public static void main(String[] args) {
		MainUI mainUI = new MainUI();
		// String url="70.12.110.161";
		String url="70.12.110.162";
		//String url = "127.0.0.1";

		ClientSocket socket = new ClientSocket(url, 7777);
		ObjectOutputStream out = socket.getOos();
		ObjectInputStream in = socket.getOis();
		String user = null;
		Login login = new Login(in, out);
		Scanner s = new Scanner(System.in);

		System.out.println("[client] ���� ���� ���� "); // Debug
		while (true) {
			System.out.println("[client] 1.ȸ������ 2.�α���"); // Debug
			String input = s.nextLine();
			String result = null;
			if (input.equals("1")) {
				System.out.println("[client] ȸ�������� ������ �ּ���."); // Debug
				String id = s.nextLine();
				String pw = s.nextLine();
				String pw2 = s.nextLine();
				result = login.signup(id, pw);
				System.out.println("[client] ȸ������ ��� : " + result);
				if (result.equals("1"))
					System.out.println("[client] ȸ������ ����"); // Debug
				else
					System.out.println("[client] ȸ������ ����"); // Debug
			} else if (input.equals("2")) {
				System.out.println("[client] �α����� ������ �ּ���."); // Debug
				String id = s.nextLine();
				String pw = s.nextLine();
				result = login.signin(id, pw);
				System.out.println("[client] �α��� ��� : " + result); // Debug
				if (result.equals("1")) {
					user = id;
					break;
				}
				System.out.println("[client] �α��� ����"); // Debug
			}
		}
		System.out.println("[client] �α��� �Ϸ�Ǿ����ϴ�."); // Debug
		// ���� ���� ����.

		Game game = new Game(out, user);
		System.out.println("[client] game��ü ����"); // Debug
		Receiver userReceiver = mainUI.new Receiver(socket, user, game);
		System.out.println("[client] userReceiver thread ����"); // Debug
		userReceiver.start();
		System.out.println("[client] userReceiver thread ����"); // Debug
	}

	/**
	 * 
	 * ������ �Է¹޴� ������
	 * 
	 *
	 */
	class Receiver extends Thread { // ������ ������(ä�ó���)
		ClientSocket socket;
		ObjectInputStream in;
		String user;
		Game game;
		Sender sender;

		public Receiver(ClientSocket socket, String user, Game game) {
			System.out.println("[client [Receiver]] ������ : " + in + ", " + user + ", " + game); // Debug
			this.socket = socket;
			this.user = user;
			this.game = game;
			in = socket.getOis();
		}

		@Override
		public void run() {
			// ���ӹ濡 ������ �� ���ӹ��� ���� ���¸� �Է¹���.(������ ���� ���� ��)
			System.out.println("[client [Receiver]] run()"); // Debug
			game.start();
			// HashMap<String, String> receiveMsg = null;
			// Object tmp;
			// try {
			// tmp = in.readObject();
			// System.out.println("[client [Receiver]] �����κ��� Object�� �޾ҽ��ϴ�. "+tmp); //Debug
			// if (tmp instanceof HashMap<?, ?>)
			// receiveMsg = (HashMap<String, String>) tmp;
			// } catch (ClassNotFoundException e){
			// e.printStackTrace();
			// } catch (IOException e){
			// e.printStackTrace();
			// }
			// System.out.println("[client [Receiver]] �����κ��� ���� ��û :
			// "+receiveMsg.get("request")); //Debug
			// System.out.println("[client [Receiver]] �����κ��� ���� full �޼��� : "+receiveMsg);
			// //Debug

			// game.init(receiveMsg);
			sender = new Sender(game);
			sender.start();
			// ���� ��� ����
			while (true) {
				// ���� �����
				System.out.println("[client [Receiver]] gameWait()�� ����"); // Debug
				gameWait(in);
				System.out.println("[client [Receiver]] ���Ӵ�� ����"); // Debug
				// ���� ����
				System.out.println("[client [Receiver]] gameStart()�� ����"); // Debug
				gameStart(in);
				System.out.println("[client [Receiver]] ���� ����"); // Debug
			}
		}

		public void gameWait(ObjectInputStream in) {
			System.out.println("[client [Receiver]] gameWait()"); // Debug
			while (true) {
				HashMap<String, String> receiveMsg = null;
				Object tmp;
				try {
					tmp = in.readObject();
					if (tmp instanceof HashMap<?, ?>)
						receiveMsg = (HashMap<String, String>) tmp;

					String request = receiveMsg.get("request");
					if (request.equals("msg")) {
						game.receiveChat(receiveMsg);
					} else if (request.equals("addUser")) { // ������ ����
						game.addUser(receiveMsg.get("id"));
					} else if (request.equals("removeUser")) { // ������ ����
						game.removeUser(receiveMsg.get("id"));
					} else if (request.equals("host")) { // ���� �ٲ�
						game.sethost(receiveMsg);
					} else if (request.equals("init")) { // ���� �ٲ�
						game.init(receiveMsg);
					} else if (request.equals("start")) { // ���� �ٲ�
						return;
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public void gameStart(ObjectInputStream in) {
			System.out.println("[client [Receiver]] gameStart()"); // Debug
			// ���� �ޱ�
			HashMap<String, String> receiveMsg = null;
			Object tmp;
			try {

				tmp = in.readObject();
				if (tmp instanceof HashMap<?, ?>)
					receiveMsg = (HashMap<String, String>) tmp;

				System.out.println("receiveMsg.get(\"status\") : " + receiveMsg.get("status"));

				game.setStatus(receiveMsg.get("status"));

			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// ���� �ް� ���� ����
			while (true) {
				try {
					tmp = in.readObject();
					if (tmp instanceof HashMap<?, ?>)
						receiveMsg = (HashMap<String, String>) tmp;

					String request = receiveMsg.get("request");
					if (request.equals("msg")) { // ä��
						game.receiveChat(receiveMsg);
					} else if (request.equals("vote")) { // ��ǥ���� ��û ����
						game.requestVote();
						vote=true;
					} else if (request.equals("night")) { // ���� ��ǥ ��û
						vote=false;
						game.night();
					} else if (request.equals("day")) { // ���� ��ǥ ��û
						vote=false;
						game.day();
					} else if (request.equals("removeUser")) { // ������ ����
						game.removeUser(receiveMsg.get("id"));
					} else if (request.equals("host")) { // ���� �ٲ�
						game.sethost(receiveMsg);
					} else if (request.equals("setStatus")) { // ��� ���� �� �������� ���� ����
						game.setStatus(receiveMsg.get("status"));
					} else if (request.equals("gameEnd")) { // ���� ����
						vote=false;
						game.gameEnd();
						return;
					}

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
	}

	class Sender extends Thread {
		ClientSocket socket;
		Game game;

		public Sender(Game game) {
			super();
			this.game = game;
		}

		public void run() {
			Scanner s = new Scanner(System.in);
			String msg;
			while (true) {
				msg = s.nextLine();
				if(vote) {
					game.vote(msg);
				}
				else if(msg.equals("start")) {
					game.sendStart();
				}
				else
					game.sendChat(msg);
			}
		}
	}
}
