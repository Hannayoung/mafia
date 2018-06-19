package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mapia.Game;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WatingPanel extends JPanel implements ActionListener, KeyListener {

	
	JScrollPane scrollPane;
	JTextField watingTextfield;
	MafiaFrame win;
	JTextArea watingTextArea;
	List watingUserList;
	JButton watingBtnEnter;
	JButton btnStart;
	JLabel watingLblTitle;
	JLabel watingLblList;
	
	
	ObjectOutputStream out;
	
	Game game;
	
	
	public WatingPanel(MafiaFrame win, ObjectOutputStream out) {
		this.win = win;
		this.out = out;
		
		setLayout(null);
		
		
		watingTextArea = new JTextArea();
		watingTextArea.setEditable(false);
		
		scrollPane = new JScrollPane(watingTextArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(12, 35, 489, 442);
		add(scrollPane);
		
		watingLblTitle = new JLabel("Wating");
		watingLblTitle.setBounds(247, 10, 57, 15);
		add(watingLblTitle);
		
		watingTextfield = new JTextField();
		watingTextfield.setBounds(12, 487, 374, 30);
		add(watingTextfield);
		watingTextfield.setColumns(10);
		
		watingBtnEnter = new JButton("ENTER");
		watingBtnEnter.setBounds(393, 486, 111, 30);
		add(watingBtnEnter);
		
		watingUserList = new List();
		watingUserList.setBounds(531, 61, 132, 300);
		add(watingUserList);
		
		watingLblList = new JLabel("WATING LIST");
		watingLblList.setBounds(559, 35, 77, 15);
		add(watingLblList);
		
		
		
		btnStart = new JButton("START");
		btnStart.setBounds(547, 382, 97, 23);
		add(btnStart);

		
		watingTextfield.addKeyListener(this);
		watingBtnEnter.addActionListener(this);
		btnStart.addActionListener(this);
		

	}


	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource().equals(watingTextfield)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				watingTextArea.append(watingTextfield.getText());
//				game.sendChat(textField.getText());
			}
		}		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(watingBtnEnter)) { 
			game.sendChat(watingTextfield.getText());
		}
		
		if(e.getSource().equals(btnStart)) {
//			game.sendStart();
			win.change("game");
		}
	}
}
