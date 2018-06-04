package comm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class TcpIpMultichatServer {
	HashMap clients;

	TcpIpMultichatServer() {
		clients = new HashMap();
		Collections.synchronizedMap(clients);
	}

	public void start() {
		ServerSocket serversocket = null;
		Socket socket = null;

		try {
			serversocket = new ServerSocket(7777);
			System.out.println("������ ���۵Ǿ����ϴ�.");

			while (true) {
				socket = serversocket.accept();
				System.out.println("[ " + socket.getInetAddress() + " : " + socket.getPort() + " ] ���� �����Ͽ����ϴ�.");

				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void sendToAll(String msg) {
		Iterator it = clients.keySet().iterator();

		while (it.hasNext()) {
			try {
				DataOutputStream out = (DataOutputStream) clients.get(it.next());
				out.writeUTF(msg);
			} catch (IOException e) {
			}

		}

	}

	public static void main(String args[]) {
		new TcpIpMultichatServer().start();
	}

	class ServerReceiver extends Thread {

		Socket socket;
		DataInputStream in;
		DataOutputStream out;

		ServerReceiver(Socket socket) {
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
			}

		}

		public void run() {
			String name = "";

			try {
				name = in.readUTF();
				sendToAll("#" + name + "���� �����̽��ϴ�.");

				clients.put(name, out);
				System.out.println("���� ���������� ���� " + clients.size() + "�Դϴ�.");

				while (in != null) {
					sendToAll(in.readUTF());
				}

			} catch (Exception e) {

			} finally {
				sendToAll("#" + name + "���� �����̽��ϴ�.");
				clients.remove(name);
				System.out.println("[ " + socket.getInetAddress() + " : " + socket.getPort() + " ] ���� ������ �����Ͽ����ϴ�.");

				System.out.println("���� ���������� ���� " + clients.size() + "�Դϴ�.");

			}

		}

	}

}
