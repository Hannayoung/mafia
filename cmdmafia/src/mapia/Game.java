package mapia;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Game {
	private ObjectOutputStream out;
	private int gametime = 0;
	private String id;
	private boolean host=false;
	private String status;

	public Game(ObjectOutputStream out, String id) {
		this.out = out;
		this.id=id;
	}

	public void start() {
		System.out.println("[game] �濡 �����ϼ̽��ϴ�.");   //debug
	}


	public void init(HashMap<String,String> initInfo) {
		Set<String> keys=initInfo.keySet();
		Iterator<String> it=keys.iterator();
		String key;
		System.out.println("[game] init() keySet : "+keys);   //debug	
		while(it.hasNext()) {
			key=it.next();
			if(key.equals("request"));
			else if(key.equals("host")) this.host=Boolean.valueOf(initInfo.get(key)).booleanValue();
			else addUser(initInfo.get(key));
		}
		System.out.println("[Game] ���� ���� : "+id+", "+this.host+", "+status);
	}
	
	public void sethost(HashMap<String, String> host) {
		if(host.get("id").equals(id)) {
			this.host=true;
		}
		System.out.println("[Game] [GM] "+host.get("id")+"���� ������ �Ǽ̽��ϴ�.");
		System.out.println("[Game] ���� host ���� "+this.host);
	}
	public void sendChat(String msg) { // ä�� �޼��� ������
		HashMap<String, String> send = new HashMap<>();
		send.put("request","msg");
		send.put("id", id);
		send.put("msg", msg);
		System.out.println("[game] sendChat() msg : "+send);   //debug
		sendMsg(send);
	}

	public void sendStart() { // ���� ����(ȣ��Ʈ�� ���ӹ濡 ���ӽ����� ��û)
		HashMap<String, String> send = new HashMap<>();
		send.put("request", "start");
		send.put("id", id);
		System.out.println("[game] ���ӽ��� ȣ�� sendStart() : "+send);   //debug
		sendMsg(send);
	}
	
	public void vote(String user) { // ��ǥ
		HashMap<String, String> send = new HashMap<>();
		send.put("request","vote");
		send.put("id", id);
		send.put("vote", user);
		System.out.println("[game] ��ǥ vote() : "+send);   //debug
		sendMsg(send);
	}

//	public void agree(String agreement) { // ����
//		HashMap<String, String> result;
//		HashMap<String, String> send = new HashMap<>();
//		send.put("request","agree");
//		send.put("id", id);
//		send.put("agree", agreement);
//		System.out.println("������ǥ�� �Ͽ����ϴ�.");
//		sendMsg(send);
//	}

	public void receiveChat(HashMap<String, String> msgData) { // ä�� �ޱ�
		String id=null;
		String msg=null;
		if(msgData.get("id")!=null) id=msgData.get("id");
		msg=msgData.get("msg");
		System.out.println("[game] ���� �޼��� : ["+id+"] "+msg);  //debug
	}
	
	public void gameStart(String msg) {
		System.out.println("[game] ���� ����");   //debug
		day();
	}

	public void setStatus(String status) { // ���� ����
		StringBuilder str=new StringBuilder();
		this.status=status;
		str.append("[GM] ");
		str.append(this.id);
		str.append("���� ");
		str.append(status);
		if(status.equals("���"))str.append("�߽��ϴ�.");
		else str.append("�Դϴ�.");
		System.out.println(str);
	}

	
	public synchronized void addUser(String id) { // ���� ����
		StringBuilder str=new StringBuilder();
		str.append("[GM] ");
		str.append(id);
		str.append("���� ���Խ��ϴ�.");
		System.out.println(str);
	}

	public synchronized void removeUser(String id) { // ���� ����
		StringBuilder str=new StringBuilder();
		str.append("[GM] ");
		str.append(id);
		str.append("���� �������ϴ�.");
		System.out.println(str);
	}

	public void dieUser(String id) { // ���� ����
		StringBuilder str=new StringBuilder();
		str.append("[GM] ");
		str.append(id);
		str.append("���� �׾����ϴ�.");
		System.out.println(str);
	}

	public void requestVote() { // ��ǥ ���� ��û
		StringBuilder str=new StringBuilder();
		str.append("[GM] ");
		str.append("��ǥ�� ���۵Ǿ����ϴ�.");
		str.append("���� ����� �����ϼ���.");
		System.out.println(str);
	}

	public void night() {            //�����
		StringBuilder str=new StringBuilder();
		str.append("[GM] ");
		str.append("���� ���۵Ǿ����ϴ�.");
		System.out.println(str);
	}
	public void day() {           //�� ����
		StringBuilder str=new StringBuilder();
		str.append("[GM] ");
		str.append("���� ���۵Ǿ����ϴ�.");
		System.out.println(str);
	}
	
	public void gameEnd() {
		StringBuilder str=new StringBuilder();
		str.append("[GM] ");
		str.append("������ ����Ǿ����ϴ�.");
		System.out.println(str);
	}
//	public void requestAgree(String msg) { // ���� ���� ��û
//
//	}

	public void sendMsg(HashMap<String, String> send) { // �����͸� ������ ����� ��ٸ��� ����.
		try {
			out.writeObject(send);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
