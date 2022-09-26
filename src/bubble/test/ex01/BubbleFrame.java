package bubble.test.ex01;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

//1.윈도우 창이 되었음
//2.윈도우 창은 내부에 패널을 하나 가지고 있다.
public class BubbleFrame extends JFrame{
	
	private JLabel backgroundMap;
	private Player player;
		
	public BubbleFrame() {
		initObject();
		initSetting();
		initListener();
		setVisible(true);//그림을 그려라
	
	}
	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		setContentPane(backgroundMap);//3개층을 만들필요가 없어짐
		player = new Player();
		add(player);
	}

	private void initSetting() {
		setSize(1000,640);
		setLayout(null); //absoulte 레이아웃(자유롭게 그림을 그릴 수 있다)
	
		setLocationRelativeTo(null);//JFrame 가운데 배치하기 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x버튼 으로 창끌때 JVM같이 종료
		
	}
	private void initListener() {
		addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println(e.getKeyCode());
			
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					player.left();
					break;
				case KeyEvent.VK_RIGHT:
					player.right();
					break;
				case KeyEvent.VK_UP:
					player.up();
					break;
				}
			}
		});
	}
	
	public static void main(String[] args) {
		new BubbleFrame();
	}

}
