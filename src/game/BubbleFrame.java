package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import game.component.Enemy;
import game.component.Player;
import game.music.BGM;
import lombok.Getter;
import lombok.Setter;

//1.윈도우 창이 되었음
//2.윈도우 창은 내부에 패널을 하나 가지고 있다.

@Getter
@Setter
public class BubbleFrame extends JFrame{
	
	private BubbleFrame mContext = this;
	private JLabel backgroundMap;
	private Player player;
	private Enemy enemy;
		
	public BubbleFrame() {
		initObject();
		initSetting();
		initListener();
		setVisible(true);//그림을 그려라
	
	}
	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		setContentPane(backgroundMap);//3개층을 만들필요가 없어짐
		player = new Player(mContext);
		add(player);
		enemy = new Enemy(mContext);
		add(enemy);
		new BGM();
	}

	private void initSetting() {
		setSize(1000,640);
		getContentPane().setLayout(null); //absoulte 레이아웃(자유롭게 그림을 그릴 수 있다)
	
		setLocationRelativeTo(null);//JFrame 가운데 배치하기 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //x버튼 으로 창끌때 JVM같이 종료
		
	}
	
	private void initListener() {
		addKeyListener(new KeyAdapter() {
			//키보드 클릭 이벤트 핸들러
		@Override
		public void keyPressed(KeyEvent e) {
//			System.out.println(e.getKeyCode());
			
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if(!player.isLeft()&& !player.isLeftWallCrash()) {
					player.left();
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(!player.isRight() && ! player.isRightWallCrash()) {
					player.right();
					}
					break;
				case KeyEvent.VK_UP:
					if(!player.isUp()) {
					player.up();
					}
					break;
				case KeyEvent.VK_SPACE:
//					Bubble bubble = new Bubble(mContext);
//					add(bubble);
					player.attack();
					break;
					
		}
			}
		//키보드 해제 이벤트 핸들러
		@Override
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				player.setLeft(false);
				break;
			case KeyEvent.VK_RIGHT:
				player.setRight(false);
				break;
					
			}
		}
		
		});
	}
	
	public static void main(String[] args) {
		new BubbleFrame();
	}

}
