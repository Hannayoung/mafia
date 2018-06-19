package ui.practice;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MouseExam extends JFrame implements MouseMotionListener, MouseListener
{
	JLabel label = new JLabel("마우스 드래그 해보자");
	JTextField jtextfiled = new JTextField();

	public MouseExam()
	{
		this.add(label, "North");
		this.add(jtextfiled, "South");

		this.setSize(300, 400);
		this.setVisible(true);

		//X버튼 눌렀을 때 종료
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//이벤트 리스너 추가
		this.addMouseMotionListener(this);
		this.addMouseListener(this);

	}

	public static void main(String[] args)
	{
		new MouseExam();
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		//드래그시 좌표 출력
		jtextfiled.setText("(" + e.getX() + ", " + e.getY() + ")");
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{}

	@Override
	public void mouseClicked(MouseEvent e)
	{}

	@Override
	public void mousePressed(MouseEvent e)
	{}

	@Override
	public void mouseReleased(MouseEvent e)
	{}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		//마우스 위에 있으면 안쪽 메시지 출력
		jtextfiled.setText("마우스 안쪽?");
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		//마우스 위에 있으면 바깥쪽 메시지 출력
		jtextfiled.setText("마우스 바깥쪽?");
	}

}

