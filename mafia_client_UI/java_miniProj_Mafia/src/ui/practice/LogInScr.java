package ui.practice;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JFormattedTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LogInScr extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnJoin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInScr frame = new LogInScr();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LogInScr() {
		setTitle("MAFIA - LOG IN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 566, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblId = new JLabel("ID : ");
		lblId.setBounds(163, 138, 23, 15);
		panel.add(lblId);
		
		JLabel lblPw = new JLabel("PW : ");
		lblPw.setBounds(163, 162, 30, 15);
		panel.add(lblPw);
		
		textField = new JTextField();
		
		textField.setBounds(198, 135, 116, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(198, 159, 116, 21);
		panel.add(passwordField);
		
		
		
		JButton btnNewButton = new JButton("LOG IN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(login(textField.getText(),pwToString(passwordField.getPassword()))) {
					
					JOptionPane.showMessageDialog(btnNewButton,"로그인 성공" );
				}else {
					
					JOptionPane.showMessageDialog(btnNewButton,"로그인 실패");
				}
				
			}
		});
		btnNewButton.setBounds(326, 134, 77, 43);
		panel.add(btnNewButton);
		JLabel lblMafiaV = new JLabel("MAFIA v.1.0");
		lblMafiaV.setBounds(257, 104, 77, 21);
		panel.add(lblMafiaV);
		
		JButton btnFindIdpw = new JButton("Find ID/PW");
		btnFindIdpw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(btnFindIdpw,"아이디 비밀번호 찾기");
			}
		});
		btnFindIdpw.setBounds(184, 187, 97, 23);
		panel.add(btnFindIdpw);
		
		btnJoin = new JButton("JOIN");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JoinScr();
				
			}
		});
		btnJoin.setBounds(293, 187, 97, 23);
		panel.add(btnJoin);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPane}));
	}
	
	public boolean login(String id, String pw) {
		if(id.equals("두루미") && pw.equals("rjqnrdl"))
			return true;
		return false;
	}
	
	public String pwToString(char[] pw) {
		StringBuilder result = new StringBuilder();
		for(char c : pw) {
			result.append(c);
		}
		return result.toString();
	}
	
}
