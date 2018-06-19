package mapia;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainUI {
	public static void main(String[] args) {
		MainUI mainUI=new MainUI();
		//String url="70.12.110.161";
		//String url="70.12.110.162";
		String url="127.0.0.1";

		ClientSocket socket = new ClientSocket(url, 7777);
		ObjectOutputStream out = socket.getOos();
		ObjectInputStream in = socket.getOis();
		String user=null;
		Login login=new Login(in,out);
		Scanner s=new Scanner(System.in);

		System.out.println("[client] 서버 접속 성공 ");  //Debug
		while(true) {
			System.out.println("[client] 1.회원가입 2.로그인");  //Debug
			String input=s.nextLine();
			String result=null;
			if(input.equals("1")) {
				System.out.println("[client] 회원가입을 시작해 주세요.");  //Debug
				String id=s.nextLine();
				String pw=s.nextLine();
				String pw2=s.nextLine();
				result=login.signup(id,pw);
				System.out.println("[client] 회원가입 결과 : "+result);
				if(result.equals("1")) System.out.println("[client] 회원가입 성공");  //Debug
				else System.out.println("[client] 회원가입 실패");  //Debug
			}else if(input.equals("2")) {
				System.out.println("[client] 로그인을 시작해 주세요.");  //Debug
				String id=s.nextLine();
				String pw=s.nextLine();
				result=login.signin(id, pw);
				System.out.println("[client] 로그인 결과 : "+result);  //Debug
				if(result.equals("1")) {
					user=id;
					break;
				}
				System.out.println("[client] 로그인 실패");  //Debug
			}
		}
		System.out.println("[client] 로그인 완료되었습니다.");  //Debug
		//여기 까지 성공.



		Game game=new Game(out,user);
		System.out.println("[client] game객체 생성");  //Debug
		Receiver userReceiver=mainUI.new Receiver(socket,user,game);
		System.out.println("[client] userReceiver thread 생성");  //Debug
		userReceiver.start();
		System.out.println("[client] userReceiver thread 시작");  //Debug
	}


	/**
	 *
	 * 정보를 입력받는 스레드
	 *
	 *
	 */
	class Receiver extends Thread { // 들어오는 데이터(채팅내용)
		ClientSocket socket;
		ObjectInputStream in;
		String user;
		Game game;
		Sender sender;

		public Receiver(ClientSocket socket, String user, Game game) {
			System.out.println("[client [Receiver]] 생성자 : "+in+", "+user+", "+game);  //Debug
			this.socket=socket;
			this.user=user;
			this.game=game;
			in=socket.getOis();
		}

		@Override
		public void run() {
			//게임방에 접속한 후 게임방의 현재 상태를 입력받음.(현재의 유저 정보 등)
			System.out.println("[client [Receiver]] run()");  //Debug
			game.start();
//			HashMap<String, String> receiveMsg = null;
//			Object tmp;
//			try {
//				tmp = in.readObject();
//				System.out.println("[client [Receiver]] 서버로부터  Object를 받았습니다. "+tmp);  //Debug
//				if (tmp instanceof HashMap<?, ?>)
//					receiveMsg = (HashMap<String, String>) tmp;
//			} catch (ClassNotFoundException e){
//				e.printStackTrace();
//			} catch (IOException e){
//				e.printStackTrace();
//			}
//			System.out.println("[client [Receiver]] 서버로부터  받은 요청 : "+receiveMsg.get("request"));  //Debug
//			System.out.println("[client [Receiver]] 서버로부터  받은 full 메세지 : "+receiveMsg);  //Debug

			//game.init(receiveMsg);
			sender=new Sender(game);
			sender.start();
			//게임 대시 시작
			while (true) {
				// 게임 대기중
				System.out.println("[client [Receiver]] gameWait()로 진입");  //Debug
				gameWait(in);
				System.out.println("[client [Receiver]] 게임대기 종료");  //Debug
				// 게임 시작
				System.out.println("[client [Receiver]] gameStart()로 진입");  //Debug
				gameStart(in);
				System.out.println("[client [Receiver]] 게임 종료");  //Debug
			}
		}

		public void gameWait(ObjectInputStream in) {
			System.out.println("[client [Receiver]] gameWait()");  //Debug
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
					} else if (request.equals("addUser")) { // 유저가 들어옴
						game.addUser(receiveMsg.get("id"));
					} else if (request.equals("removeUser")) { // 유저가 나감
						game.removeUser(receiveMsg.get("id"));
					} else if (request.equals("host")){ // 방장 바뀜
						game.sethost(receiveMsg);
					} else if (request.equals("init")){ // 방장 바뀜
						game.init(receiveMsg);
					}else {  //게임 시작
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
			System.out.println("[client [Receiver]] gameStart()");  //Debug
			// 역할 받기
			HashMap<String, String> receiveMsg = null;
			Object tmp;
			try {
				tmp = in.readObject();
				if (tmp instanceof HashMap<?, ?>)
					receiveMsg = (HashMap<String, String>) tmp;
				game.setStatus(receiveMsg.get("status"));

			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// 역할 받고 게임 시작
			while (true) {
				try {
					tmp = in.readObject();
					if (tmp instanceof HashMap<?, ?>)
						receiveMsg = (HashMap<String, String>) tmp;

					String request = receiveMsg.get("request");
					if (request.equals("msg")) { // 채팅
						game.receiveChat(receiveMsg);
					} else if (request.equals("vote")) { // 투표시작 요청 받음
						game.requestVote();
					} else if (request.equals("night")) { // 찬반 투표 요청
						game.night();
					}else if (request.equals("day")) { // 찬반 투표 요청
						game.day();
					}else if (request.equals("removeUser")) { // 유저가 나감
						game.removeUser(receiveMsg.get("id"));
					} else if (request.equals("host")){ // 방장 바뀜
						game.sethost(receiveMsg);
					}else if (request.equals("gameEnd")) { // 게임 종료
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

	class Sender extends Thread{
		ClientSocket socket;
		Game game;

		public Sender(Game game) {
			super();
			this.game = game;
		}

		public void run() {
			Scanner s=new Scanner(System.in);
			String msg;
			while(true) {
				msg=s.nextLine();
				if(msg.equals("start")) game.sendStart();
				else game.sendChat(msg);
			}
		}
	}
}
