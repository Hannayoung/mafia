package ui.practice;

import java.awt.BorderLayout;

import java.awt.Dimension;

import java.awt.FlowLayout;

import java.awt.Toolkit;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.OutputStreamWriter;

import java.net.Socket;

import java.net.UnknownHostException;

import javax.swing.JButton;

import javax.swing.JDialog;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JPanel;

import javax.swing.JScrollPane;

import javax.swing.JTextArea;

import javax.swing.JTextField;

import javax.swing.ScrollPaneConstants;

class MyFrame extends JFrame {

	private Socket socket = null;

	private String ipNumber = "IP주소..."; // 연결할 IP

	private int portNumber = 3001; // 연결할 Port 번호

//	private GUIClientThread guiClientThread;

	private BufferedReader br;

	private BufferedWriter bw;

	private JPanel panel;

	private JTextField clientId, inputText;

	private JButton button;

	private JLabel label;

	private JDialog dialog;

	private Dimension frameSize, screenSize;

	private JTextArea chatting, clientList;

	private JScrollPane scrollPane;

	public MyFrame() {

		setSize(500, 405); // 가로, 세로

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setTitle("채팅 프로그램");

		screenSizeLocation(); // 모니터 중앙에 프레임 띄우기

		try {

			socket = new Socket(ipNumber, portNumber); // 소켓 생성

			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		// true 로 설정하면 JDialog의 버튼을 누르기 전에는 parent Frame 클릭이 불가하다.

		dialog = new JDialog(this, "아이디 입력", true);

		/////////////////////////////////////////////////////

		// 채팅 화면

		/////////////////////////////////////////////////////

		setLayout(new BorderLayout());

		panel = new JPanel();

		panel.setLayout(null); // 배치관리자 설정 안함 - 절대 위치 사용

		chatting = new JTextArea();

		chatting.setEditable(false); // 편집 불가능

		// chatting.setLineWrap(false);

		// 세로 스크롤 사용, 가로 스크롤 사용 안함
		scrollPane = new JScrollPane(chatting, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		label = new JLabel();

		label.setText("#### 사용자 목록 ####");

		clientList = new JTextArea();

		clientList.setEditable(false); // 편집 불가능

//		guiClientThread = new GUIClientThread(socket, chatting);

		inputText = new JTextField();

		button = new JButton("보내기");

		button.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent arg0) { // 메시지 입력

				String msg = inputText.getText(); // JtextField에 있는 데이터 얻어오기

				try {

					bw.write(msg + "\n");

					bw.flush();

				} catch (IOException e) {

					e.printStackTrace();

				}

				inputText.setText(""); // JTextField 초기화

			}

		});

		///////////////////////////////////////////////

		// 컴포넌트 절대 위치 정하기

		// - setBounds(x 좌표, y 좌표, 폭, 높이)

		///////////////////////////////////////////////

		// chatting.setBounds(4, 4, 340, 330);

		scrollPane.setBounds(4, 4, 340, 330);

		label.setBounds(349, 4, 131, 15);

		clientList.setBounds(349, 20, 131, 314);

		inputText.setBounds(4, 339, 393, 25);

		button.setBounds(400, 339, 80, 25);

		///////////////////////////////////////////////

		// panel.add(chatting);

		panel.add(scrollPane);

		panel.add(label);

		panel.add(clientList);

		panel.add(inputText);

		panel.add(button);

		add(panel);

		setVisible(true);

		/////////////////////////////////////////////////////

	}

	// 화면 중앙에 Frame 위치

	public void screenSizeLocation() {

		frameSize = getSize(); // 컴포넌트 크기

		screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 모니터 화면의 크기 구하기

		// (모니터화면 가로 - 프레임화면 가로) / 2, (모니터화면 세로 - 프레임화면 세로) / 2

		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

	}

	// JDialog 창 띄우기 - Client ID 입력

	public void clientId() {

		dialog.setLayout(new FlowLayout());

		dialog.setSize(230, 105);

		dialog.setLocationRelativeTo(this); // JFrame(부모) 가운데 위치

		label = new JLabel("아이디", JLabel.CENTER);

		clientId = new JTextField(13);

		button = new JButton("확인");

		dialog.add(label);

		dialog.add(clientId);

		dialog.add(button);

		button.addActionListener(new ActionListener() { // 확인버튼 클릭 시 실행

			@Override

			public void actionPerformed(ActionEvent arg0) { // ID 입력

				// System.out.println("ID : " + textId.getText());

				String result = "";

				try {

					bw.write(clientId.getText() + "\n");

					bw.flush();

					result = br.readLine();

					guiClientThread.start();

				} catch (IOException e) {

					e.printStackTrace();

				}

				// Server 로 부터 ok 받아오면 화면 사라지기

				if (result.equals("ok")) {

					System.out.println("result if : " + result);

					dialog.setVisible(false);

				} else {

					System.out.println("result else : " + result);

				}

			}

		});

		dialog.setVisible(true);

	}

}

public class GUIClient {

	public static void main(String[] args) {

		MyFrame f = new MyFrame();

		f.clientId();

	}

}
