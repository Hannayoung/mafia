package ui;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.ScrollPaneConstants;

import mapia.Game;

import javax.swing.JLabel;
import java.awt.List;
import javax.swing.JTextField;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	JTextField gameTextField;
	JTextField gameTextField_role;
	JTextArea ganeTextArea;
	
	JScrollPane gameScrollPane;
	JLabel lblGame;
	List aliveList;
	JLabel lblAliveList;
	List deadList;
	JLabel lblDead;
	JLabel lblRole;
	
	JButton gamebtnEnter;

	Dialog voteDialog;
	JButton btnVote;
	JRadioButton[] candidate;
	ButtonGroup group;
	Enumeration<AbstractButton> enums;
		
	ObjectOutputStream out;
	
	Game game;


	
	/**
	 * Create the panel.
	 */
	public GamePanel(MafiaFrame win, ObjectOutputStream out) {
		this.win = win;
		this.out = out;
		
		setLayout(null);

		ganeTextArea = new JTextArea();

		ganeTextArea.setEditable(false);

		gameScrollPane = new JScrollPane(ganeTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		gameScrollPane.setBounds(27, 53, 489, 402);
		add(gameScrollPane);

		lblGame = new JLabel("GAME");
		lblGame.setBounds(237, 28, 57, 15);
		add(lblGame);

		aliveList = new List();
		aliveList.setBounds(540, 53, 132, 201);
		add(aliveList);

		lblAliveList = new JLabel("USERLIST");
		lblAliveList.setBounds(577, 28, 74, 15);
		add(lblAliveList);

		deadList = new List();
		deadList.setBounds(540, 296, 132, 102);
		add(deadList);

		lblDead = new JLabel("DEAD");
		lblDead.setBounds(592, 275, 57, 15);
		add(lblDead);

		gameTextField = new JTextField();
		
		
		gameTextField.setColumns(10);
		gameTextField.setBounds(27, 498, 374, 30);
		add(gameTextField);

		gamebtnEnter = new JButton("ENTER");
		gamebtnEnter.setBounds(408, 497, 111, 30);
		add(gamebtnEnter);

		lblRole = new JLabel("ROLE :");
		lblRole.setBounds(37, 465, 47, 15);
		add(lblRole);

		gameTextField_role = new JTextField();
		gameTextField_role.setEditable(false);
		gameTextField_role.setText("시민");
		
		gameTextField_role.setBounds(81, 465, 116, 21);
		add(gameTextField_role);
		gameTextField_role.setColumns(10);
		
		
		gameTextField.addKeyListener(this);
		gamebtnEnter.addActionListener(this);
	}



	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource().equals(gameTextField)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
				try {
					out.writeUTF(gameTextField.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(gamebtnEnter)) {
			try {
				out.writeUTF(gameTextField.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		
		
		if(e.getSource().equals(btnVote)) {
			boolean selected = false;
			String selectedPerson = "";
			while(enums.hasMoreElements()) { 
				JRadioButton result = (JRadioButton)enums.nextElement();
				if(result.isSelected()) {
					selectedPerson = result.getText();
					selected = true;
				}
				
			}
			if(selected) {
				win.elected = selectedPerson;
				voteDialog.dispose();
			}else {
				JDialog warning = new JDialog(win, "WARNING!", true);
				JLabel msg = new JLabel("You didn't select!",JLabel.CENTER);
				JButton ok = new JButton("OK");
				ok.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						warning.dispose();
					}
				});
				
				warning.getContentPane().setLayout(new FlowLayout());;
				warning.getContentPane().add(msg);
				warning.getContentPane().add(ok);
				
			}

		}
	}
	
	public void vote(Set<String> candidates) {
		voteDialog = new Dialog(win,"VOTE",true);
		voteDialog.setLayout(new FlowLayout());
		
		win.elected="";
		
		JLabel msg = new JLabel("Select who will die",JLabel.CENTER);
		candidate = new JRadioButton[candidates.size()];
		group = new ButtonGroup();
		
		Iterator<String> li = candidates.iterator();
		int index=0;
		while(li.hasNext()) {
			candidate[index].setText(li.next());
			group.add(candidate[index]);
		}
		btnVote = new JButton("VOTE");
		
		voteDialog.add(msg);
		for(int i=0;i<candidates.size();i++) {
			voteDialog.add(candidate[i]);
		}
		voteDialog.add(btnVote);
		
		enums = group.getElements();
		
		
		btnVote.addActionListener(this);
		
		
	}
	

	
}
