package ui.practice;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;

class Exam03_sub1 implements Serializable { // 사용자 정보 저장 및 수정용 클래스,
	private String name;
	private String jumin;
	private String tel;
	private String addr;

	public Exam03_sub1(String name, String j, String t, String a) {
		this.name = name;
		jumin = j;
		tel = t;
		addr = a;
	}

	public void setName(String a) {
		name = a;
	}

	public String getName() {
		return name;
	}

	public void setJumin(String b) {
		jumin = b;
	}

	public String getJumin() {
		return jumin;
	}

	public void setTel(String a) {
		tel = a;
	}

	public String getTel() {
		return tel;
	}

	public void setAddr(String a) {
		addr = a;
	}

	public String getAddr() {
		return addr;
	}

}

class Exam03_sub extends JFrame implements ActionListener, KeyListener, FocusListener {
	private Container con;
	private JLabel jlb = new JLabel("<< 전체 보기 ");
	private JButton jbt = new JButton("전체보기");
	private JTextArea jta = new JTextArea(15, 40);
	private JScrollPane jsp = new JScrollPane(jta);
	private JButton jbt1 = new JButton("등록");
	private JButton jbt2 = new JButton("수정");
	private JButton jbt3 = new JButton("삭제");
	private JButton jbt4 = new JButton("Clear");
	private JButton jbt5 = new JButton("저장");
	private JButton jbt6 = new JButton("로드");
	private JButton jbt7 = new JButton("종료");

	private JLabel lb = new JLabel("이름 :", JLabel.RIGHT);
	private JLabel lb1 = new JLabel("주민번호 :", JLabel.RIGHT);
	private JLabel lb2 = new JLabel("전화번호  : ", JLabel.RIGHT);
	private JLabel lb3 = new JLabel("주소 :", JLabel.RIGHT);

	private JLabel alb = new JLabel("검색", JLabel.RIGHT);
	private JTextField atf = new JTextField(7);
	private JTextField atf1 = new JTextField(7);
	private JButton abt = new JButton("검색");
	private JTextArea ata = new JTextArea(5, 20);
	private JScrollPane ajsp = new JScrollPane(ata);
	private JButton abt1 = new JButton("<<");
	private JButton abt2 = new JButton(">>");
	private ImageIcon im = new ImageIcon("abc.jpg");

	// 필드들
	private JTextField tf = new JTextField(14);
	private JTextField tf1 = new JTextField(7);
	private JTextField tf2 = new JTextField(7);
	private JTextField tf3 = new JTextField(14);
	private JTextField tf4 = new JTextField(14);
	private Vector vc = new Vector(); // 사용자 데이터 저장 공간
	private int pos = 0; // 검색된 데이터의 위치번째를 기억하는 변수
	private int pos1 = 0; // 수정될 데이터의 위치번째 임시 기억 변수

	// 삭제부분
	private JFileChooser jfc = new JFileChooser("C://"); // 파일 선택창 생성
	private JDialog jdlg = new JDialog(this, "삭제", true); // 삭제용 다이얼로그
	private Container condlg; // 다이얼로그 컨테이너 생성
	private JLabel lbdlg = new JLabel("이름 : ", JLabel.RIGHT);
	private JLabel lbdlg1 = new JLabel("주민번호 : ", JLabel.RIGHT);
	private JTextField tfdlg = new JTextField(15);
	private JTextField tfdlg1 = new JTextField(7);
	private JTextField tfdlg2 = new JTextField(7);
	private JButton btdlg = new JButton("삭제 ");
	private JButton btdlg1 = new JButton("취소");

	// 수정 다이얼로그
	private JFileChooser ajfc = new JFileChooser("C://"); // 파일 선택창 생성
	private JDialog ajdlg = new JDialog(this, "수정", true); // 수정용 다이얼로그
	private Container acondlg; // 다이얼로그 컨테이너 생성
	private JLabel albdlg = new JLabel("이름 : ", JLabel.RIGHT);
	private JLabel albdlg1 = new JLabel("주민번호 : ", JLabel.RIGHT);
	private JTextField atfdlg = new JTextField(15);
	private JTextField atfdlg1 = new JTextField(7);
	private JTextField atfdlg2 = new JTextField(7);
	private JButton abtdlg = new JButton("수정 ");
	private JButton abtdlg1 = new JButton("취소");

	// 수정 서브 인터페이스 다이얼로그
	private JDialog bjdlg = new JDialog(this, "수정", true);
	private Container bcondlg;
	private JLabel blbdlg = new JLabel("이름 : ", JLabel.RIGHT);
	private JLabel blbdlg1 = new JLabel("주민번호 : ", JLabel.RIGHT);
	private JLabel blbdlg2 = new JLabel("전화번호 : ", JLabel.RIGHT);
	private JLabel blbdlg3 = new JLabel("주소 : ", JLabel.RIGHT);
	private JLabel btfdlg = new JLabel();
	private JLabel btfdlg1 = new JLabel();
	private JTextField btfdlg2 = new JTextField(15);
	private JTextField btfdlg3 = new JTextField(15);
	private JButton bbtdlg = new JButton("수정");
	private JButton bbtdlg1 = new JButton("취소");

	public Exam03_sub(String str) {
		super(str);
		this.init();
		this.setIconImage(im.getImage()); // 아이콘 이미지
		this.setResizable(false); // 크기 변화 사용 여부
		this.start();
		this.setSize(750, 350);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension di = tk.getScreenSize();
		Dimension di1 = this.getSize();
		// 폼 위치 중앙에 위치
		this.setLocation((int) (di.getHeight() / 2 - di1.getHeight() / 2),
				(int) (di.getWidth() / 2 - di1.getWidth() / 2));

		this.setVisible(true);
	}

	public void init() {
		con = this.getContentPane();
		con.setLayout(new BorderLayout(5, 5));
		JPanel jp = new JPanel(new BorderLayout(3, 3)); // 전체 레이아웃
		JPanel jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp1.add(jbt);
		jp1.add(jlb);
		jp.add("North", jp1); // 상단부 버튼과 라벨
		jp.add("Center", jsp); // 중앙부 텍스트 지역
		JPanel jp2 = new JPanel(new GridLayout(1, 7, 3, 3)); // 버튼 담을 패널
		jp2.add(jbt1);
		jp2.add(jbt2);
		jp2.add(jbt3);
		jp2.add(jbt4);
		jp2.add(jbt5);
		jp2.add(jbt6);
		jp2.add(jbt7);
		jbt.setEnabled(false);
		jbt1.setEnabled(false);
		jbt2.setEnabled(false);
		jbt3.setEnabled(false);
		jbt4.setEnabled(false);
		jbt5.setEnabled(false);
		jp.setBorder(new TitledBorder(new SoftBevelBorder(SoftBevelBorder.RAISED), "개인 정보 확인"));
		jp.add("South", jp2); // 기본패널 하단부에 버튼 7개 추가
		con.add(jp); // 기본 패널 추가
		JPanel jp3 = new JPanel(new GridLayout(2, 1, 3, 3));
		JPanel jp4 = new JPanel(new BorderLayout());
		JPanel jp5 = new JPanel(new GridLayout(4, 1));
		// 왼쪽에 달아놓을 라벨들을 jp5 에 담고, jp4의 왼쪽에 추가
		jp5.add(lb);
		jp5.add(lb1);
		jp5.add(lb2);
		jp5.add(lb3);
		jp4.add("West", jp5);
		// 라벨에 맞는 텍스트 필드 각각 패널에 담아서 추가
		JPanel jp6 = new JPanel(new GridLayout(4, 1));
		// 이름 필드
		jp6.add(tf);
		// 주민번호는 필드가 2개라, 패널로 묶어서 추가
		JPanel jp7 = new JPanel(new GridLayout(1, 2, 5, 5));
		jp7.add(tf1);
		jp7.add(tf2);
		jp6.add(jp7); // 주번 필드

		jp6.add(tf3);// 전화번호
		jp6.add(tf4);// 주소필드

		// 모두 담긴 내용들 jp4 패널에, 추가

		jp4.add("Center", jp6);
		// 타이틀 보더
		jp4.setBorder(new TitledBorder(new SoftBevelBorder(SoftBevelBorder.RAISED), "개인정보"));
		jp3.add(jp4); // 상단부

		// 하단부
		JPanel jp8 = new JPanel(new BorderLayout());
		JPanel jp9 = new JPanel(new BorderLayout());
		jp9.add("West", alb);
		JPanel jp10 = new JPanel(new GridLayout(1, 2, 4, 5));
		jp10.add(atf);
		jp10.add(atf1);
		jp9.add("Center", jp10);
		jp9.add("East", abt);
		JPanel jp11 = new JPanel(new FlowLayout());
		jp11.add(abt1);
		jp11.add(abt2);
		jp8.add("North", jp9);
		jp8.add("Center", ajsp);
		jp8.add("South", jp11);
		abt.setBorder(new BevelBorder(BevelBorder.RAISED));
		abt1.setBorder(new BevelBorder(BevelBorder.RAISED));
		abt2.setBorder(new BevelBorder(BevelBorder.RAISED));
		abt.setEnabled(false);
		abt1.setEnabled(false);
		abt2.setEnabled(false);
		jp8.setBorder(new TitledBorder(new SoftBevelBorder(SoftBevelBorder.RAISED), "정보검색"));
		jp3.add(jp8); // 하단부

		con.add("West", jp3);
		jta.setEditable(false); // 텍스트 아레아 수정 금지
		ata.setEditable(false); // 검색 텍스트 아레아 수정 금지
		jta.setDisabledTextColor(Color.black); // 화면 잘 보이게 색상 지정
		ata.setDisabledTextColor(Color.black);

		// 삭제 다이얼로그 구성
		condlg = jdlg.getContentPane();
		condlg.setLayout(new BorderLayout());
		JPanel jpdlg = new JPanel(new GridLayout(2, 1));
		jpdlg.add(lbdlg);
		jpdlg.add(lbdlg1);
		condlg.add("West", jpdlg);
		JPanel jpdlg1 = new JPanel(new GridLayout(2, 1));
		JPanel jpdlg1_1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jpdlg1.add(tfdlg);
		jpdlg1_1.add(tfdlg1);
		jpdlg1_1.add(tfdlg2);
		jpdlg1.add(jpdlg1_1);
		condlg.add("Center", jpdlg1);
		JPanel jpdlg2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jpdlg2.add(btdlg);
		jpdlg2.add(btdlg1);

		condlg.add("South", jpdlg2);
		jdlg.pack();
		jdlg.setResizable(false); // 크기 변환 여부 선택

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension di = tk.getScreenSize();
		Dimension di1 = jdlg.getSize();

		jdlg.setLocation((int) (di.getWidth() / 2 - di1.getWidth()), (int) (di.getHeight() / 2 - di1.getHeight() / 2));

		// 삭제 다이얼로그 구성 끝

		// 수정 다이얼로그 구성
		acondlg = ajdlg.getContentPane();
		acondlg.setLayout(new BorderLayout());
		JPanel ajpdlg = new JPanel(new GridLayout(2, 1));
		ajpdlg.add(albdlg);
		ajpdlg.add(albdlg1);
		acondlg.add("West", ajpdlg);
		JPanel ajpdlg1 = new JPanel(new GridLayout(2, 1));
		JPanel ajpdlg1_1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		ajpdlg1.add(atfdlg);
		ajpdlg1_1.add(atfdlg1);
		ajpdlg1_1.add(atfdlg2);
		ajpdlg1.add(ajpdlg1_1);
		acondlg.add("Center", ajpdlg1);
		JPanel ajpdlg2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		ajpdlg2.add(abtdlg);
		ajpdlg2.add(abtdlg1);

		acondlg.add("South", ajpdlg2);
		ajdlg.pack();
		ajdlg.setResizable(false); // 크기 변환 여부 선택

		Toolkit atk = Toolkit.getDefaultToolkit();
		Dimension adi = atk.getScreenSize();
		Dimension adi1 = ajdlg.getSize();

		ajdlg.setLocation((int) (adi.getWidth() / 2 - adi1.getWidth()),
				(int) (adi.getHeight() / 2 - adi1.getHeight() / 2));

		// 수정 다이얼로그 구성 끝

		// 수정 두번째 다이얼 로그 구성
		bcondlg = bjdlg.getContentPane();
		bcondlg.setLayout(new BorderLayout());
		JPanel bjp = new JPanel(new GridLayout(4, 1));
		bjp.add(blbdlg);
		bjp.add(blbdlg1);
		bjp.add(blbdlg2);
		bjp.add(blbdlg3);
		bcondlg.add("West", bjp);
		JPanel bjp1 = new JPanel(new GridLayout(4, 1));
		bjp1.add(btfdlg);
		bjp1.add(btfdlg1);
		bjp1.add(btfdlg2);
		bjp1.add(btfdlg3);
		bcondlg.add("Center", bjp1);
		JPanel bjp2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bjp2.add(bbtdlg);
		bjp2.add(bbtdlg1);
		bcondlg.add("South", bjp2);

		bjdlg.pack();
		bjdlg.setResizable(false); // 크기 변환 여부 선택

		Toolkit btk = Toolkit.getDefaultToolkit();
		Dimension bdi = btk.getScreenSize();
		Dimension bdi1 = bjdlg.getSize();

		bjdlg.setLocation((int) (bdi.getWidth() / 2 - bdi1.getWidth()),
				(int) (bdi.getHeight() / 2 - bdi1.getHeight() / 2));

		// 수정 하부 다이얼 로그 구성 끝
	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jbt7.addActionListener(this); // 종료 버튼
		tf.addKeyListener(this); // 이름 필드
		tf1.addFocusListener(this); // 주민번호 첫번째 필드
		tf2.addFocusListener(this); //
		tf3.addFocusListener(this);// 전번
		tf4.addFocusListener(this); // 주소
		tf4.addKeyListener(this); // 주소필드에 키 이벤트 추가
		atf.addKeyListener(this);
		atf1.addKeyListener(this);
		atf1.addFocusListener(this);
		jbt1.addActionListener(this);
		jbt4.addActionListener(this);
		jbt.addActionListener(this); // 전체 보기
		abt.addActionListener(this);
		abt1.addActionListener(this);
		abt2.addActionListener(this);
		jbt5.addActionListener(this); // 저장버튼
		jbt6.addActionListener(this); // 로드 버튼

		jbt3.addActionListener(this); // 삭제 버튼
		tfdlg1.addKeyListener(this); // 삭제다이얼로그의 주민 1 필드
		tfdlg2.addKeyListener(this); // 삭제 다이얼로그의 주민 2 필드
		btdlg1.addActionListener(this); // 삭제 다이얼로그의 취소버튼 액션 이벤트
		btdlg.addActionListener(this); // 삭제 다이얼로그의 삭제 버튼 액션 이벤트

		// 수정버튼
		jbt2.addActionListener(this); // 수정 버튼 액션 이벤트
		atfdlg1.addKeyListener(this); // 수정 다이얼로그의 주민 1 필드
		atfdlg2.addKeyListener(this); // 수정 다이얼로그의 주민 2 필드
		abtdlg1.addActionListener(this); // 수정 다이얼로그의 취소버튼 액션 이벤트
		abtdlg.addActionListener(this); // 수정 다이얼로그의 수정 버튼 액션 이벤트
		bbtdlg.addActionListener(this); // 수정 다이얼로그 2 버튼 액션 이벤트
		bbtdlg1.addActionListener(this); // 수정 다이얼로그 2 의 취소 버튼 액션 이벤트
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbt7) {
			System.exit(0);
		} else if (e.getSource() == jbt1) {
			String str = tf1.getText().trim() + tf2.getText().trim();
			int[] jumin = new int[13];
			for (int i = 0; i < 13; i++) {
				jumin[i] = str.charAt(i) - 48; // 숫자화
			}
			float hap = 0.0f;
			float temp = 0.0f;
			float temp1 = 0.0f;
			float cre = 2.0f;
			for (int i = 0; i < 12; i++) {
				if (cre == 10.0f)
					cre = 2.0f;
				hap += (jumin[i] * cre);
				cre += 1.0f;
			}
			temp = 11.0f * (int) (hap / 11.0f) + 11.0f - hap;
			temp1 = temp - 10.0f * (int) (temp / 10.0f);
			if (temp1 != jumin[12]) {
				tf1.setText("");
				tf2.setText("");
				jbt1.setEnabled(false);
				tf1.requestFocus();
				return;
			}
			// 여기까지 오면 등록의 준비가 다됨
			String a = tf.getText().trim();
			String b = tf1.getText().trim() + tf2.getText().trim();
			String c = tf3.getText().trim();
			String d = tf4.getText().trim();
			Exam03_sub1 ess = new Exam03_sub1(a, b, c, d);
			vc.add(ess);
			jta.setText("저장이 잘 되었습니다. ");
			jta.append("현재 " + vc.size() + "명의 데이터가 존재 합니다.");
			tf.setText(""); // 저장완료후 각 필드 초기화
			tf1.setText("");
			tf2.setText("");
			tf3.setText("");
			tf4.setText("");
			jbt1.setEnabled(false); // 저장 버튼
			jbt4.setEnabled(false); // 클리어버튼 사용 x
			jbt.setEnabled(true);
			jbt2.setEnabled(true);
			jbt3.setEnabled(true);
			jbt5.setEnabled(true);
			tf.requestFocus();

		} else if (e.getSource() == jbt4) {
			tf.setText(""); // 저장완료후 각 필드 초기화
			tf1.setText("");
			tf2.setText("");
			tf3.setText("");
			tf4.setText("");
		} else if (e.getSource() == jbt) {
			jta.setText("전체 인원의 개인 정보 입니다.\n");
			jta.append(" 이름     |    주민번호      |    전화번호    |    주소  \n");
			jta.append(" - - - - -  - - - - - - - - - - \n");
			for (int i = 0; i < vc.size(); i++) {
				Exam03_sub1 imsi = (Exam03_sub1) vc.elementAt(i);
				jta.append(imsi.getName() + " ");
				jta.append(imsi.getJumin() + " ");
				jta.append(imsi.getTel() + " ");
				jta.append(imsi.getAddr() + " \n");
			}
			jta.append("========================");
			jta.append(" 총 " + vc.size() + "명");
		}

		else if (e.getSource() == abt) {
			String str = atf.getText().trim() + atf1.getText().trim();
			int k = -1; // 벡터의 i 번째 저장을 위한 변수
			for (int i = 0; i < vc.size(); i++) {
				Exam03_sub1 imsi = (Exam03_sub1) vc.elementAt(i);
				if (imsi.getJumin().equals(str)) {
					k = i;
				}
			}
			if (k == -1) {
				ata.setText(" 검색된 내용이 없습니다 ");
				atf.setText("");
				atf1.setText("");
				abt.setEnabled(false);
				return;
			}
			pos = k;
			view(pos);
			if (vc.size() <= 1) {
				abt1.setEnabled(false);
				abt2.setEnabled(false);
			} else {
				if (pos == 0) {
					abt1.setEnabled(true);
					abt2.setEnabled(false);
				} else if (pos == vc.size() - 1) {// 마지막 위치
					abt1.setEnabled(false);
					abt2.setEnabled(true);
				} else {
					abt1.setEnabled(true);
					abt2.setEnabled(true);

				}
			}
			atf.setText("");
			atf1.setText("");
			abt.setEnabled(false);
		} else if (e.getSource() == abt2) {
			pos--;
			if (pos == 0) {
				abt2.setEnabled(false);
			}
			abt1.setEnabled(true);
			view(pos);
		} else if (e.getSource() == abt1) {
			pos++;
			if (pos == vc.size() - 1) {
				abt1.setEnabled(false);
			}
			abt2.setEnabled(true);
			view(pos);
		} else if (e.getSource() == jbt5) {
			jfc.showSaveDialog(this);
			File file = jfc.getSelectedFile(); // 리턴 받은 파일이름 저장
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				ObjectOutputStream oos = new ObjectOutputStream(bos);
				oos.writeObject(vc);
				oos.close();
				bos.close();
				fos.close();
			} catch (FileNotFoundException ee) {
			} catch (IOException ee) {
			}
			// 파일 저장 끝
			jta.setText("저장 완료 ! \n");
			jta.append(file + " 의 이름으로 저장 되었습니다.");
		}

		else if (e.getSource() == jbt6) {
			jfc.showOpenDialog(this);
			File file = jfc.getSelectedFile();
			try {
				FileInputStream fi = new FileInputStream(file);
				BufferedInputStream bi = new BufferedInputStream(fi);
				ObjectInputStream ois = new ObjectInputStream(bi);
				vc.clear();
				vc = (Vector) ois.readObject();
				ois.close();
				bi.close();
				fi.close();
			} catch (FileNotFoundException ee) {
			} catch (IOException ee) {
			} catch (ClassNotFoundException ee) {
			}
			jta.setText(" 파일 읽기 완료 \n ");
			jta.append(file + " 의 파일로 부터 로드 하였습니다.");
			jta.append(" 총 데이터 개수 : " + vc.size() + "개");
			if (vc.size() >= 1) {
				jbt.setEnabled(true);
				jbt2.setEnabled(true);
				jbt3.setEnabled(true);
				jbt5.setEnabled(true);

			}
		} else if (e.getSource() == jbt3) {
			// 다이얼 로그 띄우기
			jdlg.setVisible(true);

		} else if (e.getSource() == btdlg1) {
			tfdlg.setText("");
			tfdlg1.setText("");
			tfdlg2.setText("");
			jdlg.setVisible(false);
		} else if (e.getSource() == btdlg) {
			String name = tfdlg.getText().trim();
			String str1 = tfdlg1.getText().trim() + tfdlg2.getText().trim();
			int kk = -1;
			for (int i = 0; i < vc.size(); i++) {
				Exam03_sub1 imsi = (Exam03_sub1) vc.elementAt(i);
				if (imsi.getName().equals(name) && imsi.getJumin().equals(str1)) {
					kk = i;
				}
			}
			if (kk == -1) { // 해당 사용자 없을 시
				jta.setText(" 사용자 존재 하지 않습니다. \n ");
				jta.append("정보 : " + name + " 이름의 " + str1.substring(0, 6) + "-" + str1.substring(6, 13) + "\n");
				jta.append("다시 확인 하세요 !! \n");
				tfdlg.setText("");
				tfdlg1.setText("");
				tfdlg2.setText("");
				jdlg.setVisible(false);
				return;
			}
			tfdlg.setText("");
			tfdlg1.setText("");
			tfdlg2.setText("");
			jdlg.setVisible(false);
			vc.removeElementAt(kk); // 실제 데이터 삭제 부분
			jta.setText(" 다음의 사용자를 데이터 저장소에서 삭제 하였습니다.\n");
			jta.append("현재 남은 데이터 개수 : " + vc.size() + "개 ");
		} else if (e.getSource() == jbt2) {
			// 수정 다이얼로그...
			ajdlg.setVisible(true);
		} else if (e.getSource() == abtdlg1) {
			atfdlg.setText("");
			atfdlg1.setText("");
			atfdlg2.setText("");
			ajdlg.setVisible(false);
		} else if (e.getSource() == abtdlg) {
			String name = atfdlg.getText().trim();
			String str1 = atfdlg1.getText().trim() + atfdlg2.getText().trim();
			int kk = -1;
			for (int i = 0; i < vc.size(); i++) {
				Exam03_sub1 imsi = (Exam03_sub1) vc.elementAt(i);
				if (imsi.getName().equals(name) && imsi.getJumin().equals(str1)) {
					kk = i;
				}
			}
			if (kk == -1) { // 해당 사용자 없을 시
				jta.setText(" 사용자 존재 하지 않습니다. \n ");
				jta.append("정보 : " + name + " 이름의 " + str1.substring(0, 6) + "-" + str1.substring(6) + "\n");
				jta.append("다시 확인 하세요 !! \n");
				atfdlg.setText("");
				atfdlg1.setText("");
				atfdlg2.setText("");
				ajdlg.setVisible(false);
				return;
			}
			atfdlg.setText("");
			atfdlg1.setText("");
			atfdlg2.setText("");
			jdlg.setVisible(false);
			// 수정 할 수 있는 다이얼 로그 띄우기
			pos1 = kk; // 해당 사용자 임시 기억
			jta.setText("해당 사용자를 찾았습니다. 수정 하세요.");
			Exam03_sub1 imsi = (Exam03_sub1) vc.elementAt(kk);
			btfdlg.setText(imsi.getName());
			btfdlg1.setText(imsi.getJumin().substring(0, 6) + "-" + imsi.getJumin().substring(6));
			btfdlg2.setText(imsi.getTel());
			btfdlg3.setText(imsi.getAddr());
			bjdlg.setVisible(true);
		}

		else if (e.getSource() == bbtdlg) { // 수정 버튼
			Exam03_sub1 imsi = (Exam03_sub1) vc.elementAt(pos1);
			if (btfdlg2.getText().trim().length() != 0 && btfdlg3.getText().trim().length() != 0) {
				imsi.setTel(btfdlg2.getText().trim());
				imsi.setAddr(btfdlg3.getText().trim());
			}
			jta.setText(imsi.getName() + "님의 정보가 정상 수정 되었습니다.");
			bjdlg.setVisible(false);

		} else if (e.getSource() == bbtdlg1) { // 취소 버튼
			bjdlg.setVisible(false);
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if (e.getSource() == atf) {
			if (atf.getText().trim().length() == 6) {
				atf1.requestFocus();
				return;
			}
		} else if (e.getSource() == atf1) {
			if (atf1.getText().trim().length() == 7) {
				abt.setEnabled(true);
				abt.requestFocus();
				return;
			}
		}

		// 다이얼로그 삭제 에서의 주민번호
		if (e.getSource() == tfdlg1) {
			if (tfdlg1.getText().trim().length() == 6) {
				tfdlg2.requestFocus();
				return;
			}
		} else if (e.getSource() == tfdlg2) {
			if (tfdlg2.getText().trim().length() == 7) {
				btdlg.setEnabled(true);
				btdlg.requestFocus();
				return;
			}
		}
		// 다이얼로그 수정 에서의 주민번호
		if (e.getSource() == atfdlg1) {
			if (atfdlg1.getText().trim().length() == 6) {
				atfdlg2.requestFocus();
				return;
			}
		} else if (e.getSource() == atfdlg2) {
			if (atfdlg2.getText().trim().length() == 7) {
				abtdlg.setEnabled(true);
				abtdlg.requestFocus();
				return;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		// 눌럿다 땠다
		// 개인정보 입력시, clear 버튼 활성화
		if (e.getSource() == tf) {
			String str = tf.getText().trim();
			if (str.length() != 0) {
				jbt4.setEnabled(true);
			} else {
				jbt1.setEnabled(false);
				if (tf1.getText().trim().length() != 0 || tf2.getText().trim().length() != 0
						|| tf3.getText().trim().length() != 0 || tf4.getText().trim().length() != 0) {
					return;
				}
				jbt4.setEnabled(false);

			}
		}

		else if (e.getSource() == tf4) {
			String str = tf4.getText().trim();
			if (str.length() != 0) {
				jbt1.setEnabled(true);
			} else {
				jbt1.setEnabled(false);
			}
		}

		else if (e.getSource() == atf) {
			if (atf.getText().trim().length() == 6) {
				atf1.requestFocus();
				return;
			}
		} else if (e.getSource() == atf1) {
			if (atf1.getText().trim().length() == 7) {
				abt.setEnabled(true);
				abt.requestFocus();
				return;
			}
		}

		// 다이얼로그 삭제 에서의 주민번호
		if (e.getSource() == tfdlg1) {
			if (tfdlg1.getText().trim().length() == 6) {
				tfdlg2.requestFocus();
				return;
			}
		} else if (e.getSource() == tfdlg2) {
			if (tfdlg2.getText().trim().length() == 7) {
				btdlg.setEnabled(true);
				btdlg.requestFocus();
				return;
			}
		}

		// 다이얼로그 수정 에서의 주민번호
		if (e.getSource() == atfdlg1) {
			if (atfdlg1.getText().trim().length() == 6) {
				atfdlg2.requestFocus();
				return;
			}
		} else if (e.getSource() == atfdlg2) {
			if (atfdlg2.getText().trim().length() == 7) {
				abtdlg.setEnabled(true);
				abtdlg.requestFocus();
				return;
			}
		}

	}

	public void focusGained(FocusEvent e) {
		if (e.getSource() == tf1) {
			String str = tf.getText().trim();
			if (str.length() == 0) {
				tf.setText("");
				tf.requestFocus();
				return;
			}
		} else if (e.getSource() == tf2) {
			String str = tf1.getText().trim();
			if (str.length() != 6) {
				tf1.setText("");
				tf1.requestFocus();
				return;
			}
			// 주번필드에 문자 삽입 안되게 설정
			try {
				int i = Integer.parseInt(str);
			} catch (NumberFormatException ee) {
				tf1.setText("");
				tf1.requestFocus();
				return;
			}
		}

		else if (e.getSource() == tf3) {
			String str = tf2.getText().trim();
			if (str.length() != 7) {
				tf2.setText("");
				tf2.requestFocus();
				return;
			}
			// 주번필드에 문자 삽입 안되게 설정
			try {
				int i = Integer.parseInt(str);
			} catch (NumberFormatException ee) {
				tf2.setText("");
				tf2.requestFocus();
				return;
			}
		}

		else if (e.getSource() == tf4) {
			String str = tf3.getText().trim();
			if (str.length() == 0) {
				tf3.setText("");
				tf3.requestFocus();
				return;
			}
		}
	}

	public void focusLost(FocusEvent e) {

	}

	public void view(int ii) {

		Exam03_sub1 imsi = (Exam03_sub1) vc.elementAt(ii);
		ata.setText("이름 : " + imsi.getName() + "\n");
		ata.append("주민번호  : " + imsi.getJumin().substring(0, 6) + "-" + imsi.getJumin().substring(7, 13) + "\n");
		ata.append("연락처 : " + imsi.getTel() + "\n");
		ata.append("주소: " + imsi.getAddr() + "\n");

		int[] jumin = new int[13];
		for (int i = 0; i < 13; i++) {
			jumin[i] = imsi.getJumin().charAt(i) - 48;
		}
		int year = 0, month = 0, day = 0;
		if (jumin[6] == 0 || jumin[6] == 0) {
			year = 1800;
		} else if (jumin[6] == 1 || jumin[6] == 2) {
			year = 1900;
		} else if (jumin[6] == 3 || jumin[6] == 4) {
			year = 2000;
		}
		year += jumin[0] * 10 + jumin[1];
		month = jumin[2] * 10 + jumin[3];
		day = jumin[4] * 10 + jumin[5];
		ata.append("생년월일 " + year + " 년" + month + "월" + day + "일 \n");
		Calendar ca = Calendar.getInstance();
		ata.append("나이 : " + ((int) ca.get(Calendar.YEAR) - year) + "세 \n");
		ata.append("성별 " + ((jumin[6] % 2 == 0) ? "여성" : "남성"));
	}

}

public class Exam03 {
	public static void main(String[] ar) {
		Exam03_sub es = new Exam03_sub(" 사용자 관리 프로그램 ");
	}
}
