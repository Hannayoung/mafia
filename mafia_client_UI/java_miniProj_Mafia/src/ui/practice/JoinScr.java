package ui.practice;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JDesktopPane;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class JoinScr extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JoinScr frame = new JoinScr();
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
	public JoinScr() {
		setTitle("Join");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 572, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID :");
		lblNewLabel.setBounds(108, 101, 76, 40);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(196, 111, 157, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblPw = new JLabel("PW :");
		lblPw.setBounds(108, 142, 76, 40);
		contentPane.add(lblPw);
		
		JLabel lblConfirmPw = new JLabel("Confirm PW :");
		lblConfirmPw.setBounds(108, 181, 76, 40);
		contentPane.add(lblConfirmPw);
		
		JButton btnIdCheck = new JButton("ID Check");
		btnIdCheck.setBounds(389, 110, 97, 23);
		contentPane.add(btnIdCheck);
		
		JButton btnJoin = new JButton("JOIN");
		btnJoin.setBounds(229, 246, 97, 23);
		contentPane.add(btnJoin);
		
		JLabel lblJoin = new JLabel("JOIN");
		lblJoin.setBounds(269, 64, 57, 15);
		contentPane.add(lblJoin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(196, 152, 157, 21);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(196, 191, 157, 21);
		contentPane.add(passwordField_1);
	}
}
