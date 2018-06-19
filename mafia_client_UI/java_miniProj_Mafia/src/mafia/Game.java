package mafia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import comm.UserVO;

public class Game extends Thread{
	private HashMap<String, UserVO> all;
	private HashMap<String, UserVO> lives;
	private HashMap<String, UserVO> citizen;
	private HashMap<String, UserVO> mafia;
	private HashMap<String, UserVO> dead;
	public boolean day = true;
	public boolean game = false;
	private int time;
	Server server;
	GameSetting gs = null;

	static int count = 0;

	Game() {
		all = new HashMap<String, UserVO>();
		lives = new HashMap<String, UserVO>();
		citizen = new HashMap<String, UserVO>();
		mafia = new HashMap<String, UserVO>();
		dead = new HashMap<String, UserVO>();
		gs=new GameSetting();
	}

	public void run() {
		try {
			while (true) {
				gameWait();
				gameStart();
				while (true) {
					day();
					vote();
					// argument();
					// agree();
					gamecheck();
					night();
					vote();
					if (gs.getPolice() != null) {
						vote();
					}
					if (gs.getDoctor() != null) {
						vote();
					}
					gamecheck();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

	public void addUser(UserVO user) {
		all.put(user.getId(), user);
		lives.put(user.getId(), user);
	}

	public void removeUser(UserVO user) {
		all.remove(user.getId());
		lives.remove(user.getId());
	}

	public HashMap<String, UserVO> getAll() {
		return all;
	}

	public HashMap<String, UserVO> getCitizen() {
		return citizen;
	}

	public HashMap<String, UserVO> getMafia() {
		return mafia;
	}

	public HashMap<String, UserVO> getDead() {
		return dead;
	}

	public HashMap<String, UserVO> getLives() {
		return lives;
	}

	void sendToAll(HashMap<String, String> sendData) { // 전체
		send(all, sendData);
	}

	void sendToDead(HashMap<String, String> sendData) { // 죽음
		send(dead, sendData);
	}

	void sendToMafia(HashMap<String, String> sendData) { // 마피아
		send(mafia, sendData);
	}

	private void send(HashMap<String, UserVO> users, HashMap<String, String> sendData) {
		Set<String> keys = users.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			UserVO user = users.get(key);
			try {
				user.getOos().writeObject(sendData);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void showAliveName() {
		HashMap<String, String> sendData = new HashMap<String, String>();

		Iterator<String> itr = lives.keySet().iterator();

		while (itr.hasNext()) {
			String key = (String) itr.next();
			sendData.put("request", "id");
			sendData.put("id", key);
			if (day) {
				sendToAll(sendData);
				sendData.clear();
			}
			if (!day) {
				sendToMafia(sendData);
				sendData.clear();
			}
		}

	}

	public void showAll() {
		HashMap<String, String> sendData = new HashMap<String, String>();

		Iterator<String> itr = all.keySet().iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			sendData.put("request", "id");
			sendData.put("id", key);
			sendToAll(sendData);
			sendData.clear();
		}
	}

	private void gameEnd() {
		day = true;
		HashMap<String, String> sendData = new HashMap<String, String>();
		sendData.put("request", "gameEnd");
		sendToAll(sendData);
		showAll();
	}

	private void gamecheck() {
		if (gs.getMafias().size() >= gs.getCitizen().size() || gs.getMafias().size() == 0)
			gameEnd();
	}

	private void night() {
		day = false;
		HashMap<String, String> sendData = new HashMap<String, String>();
		sendData.put("request", "msg");
		sendData.put("id", "GM");
		sendData.put("msg", "밤이되었습니다.");
		sendToAll(sendData);
		sendData.clear();
		sendData.put("request", "msg");
		sendData.put("id", "GM");
		sendData.put("msg", "마피아들은 90초간 죽일 사람을 상의해주세요.");
		sendToAll(sendData);
		sendData.clear();

		try {
			Thread.sleep(45000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		sendData.put("request", "msg");
		sendData.put("id", "GM");
		sendData.put("msg", "45초 남았습니다..");
		sendToAll(sendData);
		sendData.clear();

		try {
			Thread.sleep(45000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	// private void agree() {
	// HashMap<String, String> sendData= new HashMap<String,String>();
	// sendData.put("request", "msg");
	// sendData.put("id", "GM");
	// sendData.put("msg", "찬반투표를 시작하겠습니다.");
	// sendToAll(sendData);
	// sendData.clear();
	//
	// sendData.put("request", "agree");
	// sendToAll(sendData);
	// sendData.clear();
	//
	// }

	// private void argument() {
	// HashMap<String, String> sendData= new HashMap<String,String>();
	// sendData.put("request", "msg");
	// sendData.put("id", "GM");
	// sendData.put("msg", "투표가 종료되었습니다.");
	// sendToAll(sendData);
	// sendData.clear();
	//
	// String result = "";
	// sendData.put("request", "msg");
	// sendData.put("id", "GM");
	// sendData.put("msg", result);
	// sendToAll(sendData);
	// sendData.clear();
	//
	// sendData.put("request", "msg");
	// sendData.put("id", "GM");
	// sendData.put("msg", "반론을 시작하세요");
	// sendToAll(sendData);
	// sendData.clear();
	//
	// }

	private void vote() {
		HashMap<String, String> sendData = new HashMap<String, String>();
		sendData.put("request", "vote");
		sendToAll(sendData);
		sendData.clear();

		showAliveName();

		sendData.put("request", "msg");
		sendData.put("msg", "15초 이내로 투표를 진행해주세요");
		sendToAll(sendData);
		sendData.clear();

		try { // 15초간 대기
			Thread.sleep(15000);
		} catch (InterruptedException e) {
		}

		// 살아있는 사람들을 ArrayList에 넣은 뒤 투표수를 기준으로 내림차순 정렬
		List<UserVO> list = new ArrayList<UserVO>();

		Iterator<String> itr = lives.keySet().iterator();

		while (itr.hasNext()) {
			String key = (String) itr.next();
			list.add(lives.get(key));
		}

		Collections.sort(list, new VoteDescCompare());

		// 득표율 1위와 2위를 비교 시 1위가 클 경우
		if (list.get(0).getVote() > list.get(1).getVote()) {
			lives.remove(list.get(0).getId());
			try {
			citizen.remove(list.get(0).getId());
			mafia.remove(list.get(0).getId());
			} catch(Exception e) {
			}
			dead.put(list.get(0).getId(), list.get(0));

			sendData.put("request", "msg");
			sendData.put("msg", "투표가 종료되었습니다.\r\n" + list.get(0).getId() + "님이 사망하셨습니다.");
			sendToAll(sendData);
			sendData.clear();
		}

		// 득표율 1위와 2위를 비교 시 같을경우
		if (list.get(0).getVote() == list.get(1).getVote()) {
			sendData.put("request", "msg");
			sendData.put("msg", "투표가 종료되었습니다.\r\n투표율이 같아 아무도 사망하지 않았습니다.");
			sendToAll(sendData);
			sendData.clear();
		}

		itr = lives.keySet().iterator();

		while (itr.hasNext()) {
			String key = (String) itr.next();
			lives.get(key).resetVote();
		}
		list.clear();
	}

	private void day() {
		day = true;
		HashMap<String, String> sendData = new HashMap<String, String>();
		sendData.put("request", "msg");
		sendData.put("id", "GM");
		sendData.put("msg", "낮이 되었습니다.");
		sendToAll(sendData);
		sendData.clear();
		sendData.put("request", "msg");
		sendData.put("id", "GM");
		sendData.put("msg", "대화를 90초간 시작해주세요.");
		sendToAll(sendData);
		sendData.clear();


		try {
			Thread.sleep(45000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		sendData.put("request", "msg");
		sendData.put("id", "GM");
		sendData.put("msg", "45초 남았습니다..");
		sendToAll(sendData);
		sendData.clear();

		try {
			Thread.sleep(45000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void gameStart() {                     //게임 시작.
		HashMap<String, String> sendData = new HashMap<String, String>();
		sendData.put("request", "start");
		sendToAll(sendData);
		sendData.clear();

		sendData.put("msg", "[Game] 게임을 시작합니다.");
		sendToAll(sendData);

	}

	private void gameWait() {
		System.out.println("[Game] gameWait() 돌입"); // Debug
		while (!game) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("[Game] gameWait() 탈출");
	}

	class TimeCount extends Thread {
		@Override
		public void run() {
			while (time-- == 0) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

class VoteDescCompare implements Comparator<UserVO> {
	/**
	 * 내림차순(DESC)
	 */
	@Override
	public int compare(UserVO user1, UserVO user2) {
		// TODO Auto-generated method stub
		return user1.getVote() > user2.getVote() ? -1 : user1.getVote() < user2.getVote() ? 1 : 0;
	}

}
