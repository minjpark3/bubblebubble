package game.component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import game.BubbleFrame;
import game.Moveable;
import game.service.BackgroundBubbleService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable {
	// 의존성 콤포지션이 필요함(버블이 플레이어에게서 발사되니깐)
	private BubbleFrame mContext;
	private Player player;
	private Enemy enemy;
	private BackgroundBubbleService backgroundBubbleService;

	// 위치상태
	private int x;
	private int y;

	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;

	// 적군을 맞춘 상태
	private int state; // 0(물방울),1(적을 가둔 물방울)
	private ImageIcon bubble; // 물방울
	private ImageIcon bubbled; // 적을 가둔 물방울
	private ImageIcon bomb; // 물방울이 터진 상태

	public Bubble(BubbleFrame mContext ) {
		this.mContext = mContext;
		this.player = mContext.getPlayer();
		this.enemy = mContext.getEnemy();
		initObject();
		initSetting();
		//initThread();

	}

	private void initObject() {
		bubble = new ImageIcon("image/bubble.png");
		bubbled = new ImageIcon("image/bubbled.png");
		bomb = new ImageIcon("image/bomb.png");

		backgroundBubbleService = new BackgroundBubbleService(this);

	}

	private void initSetting() {
		left = false;
		right = false;
		up = false;

		x = player.getX();
		y = player.getY();

		setIcon(bubble);
		setSize(50, 50);

		state = 0;

	}
/**
*	private void initThread() {
*		new Thread(() -> {
*			if (player.getPlayerWay() == PlayerWay.LEFT) {
*				left();
*			} else {
*				right();
*			}
*
*		}).start();
* }
*/	
	@Override
	public void left() {
		left = true;
		for (int i = 0; i < 400; i++) {
			x--;
			setLocation(x, y);
			if (backgroundBubbleService.leftWall()) {
				left = false;
				break;
			}
			if((Math.abs(x-enemy.getX())<10) &&
					Math.abs(y-enemy.getY())>0 && Math.abs(y-enemy.getY()) <50) {
				System.out.println("물방울과 적군 충돌 ");
				if(enemy.getState()==0) {
					attack();
					break;
				}
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		up();
	}

	@Override
	public void right() {
		right = true;
		for (int i = 0; i < 400; i++) {
			x++;
			setLocation(x, y);
			if (backgroundBubbleService.rightWall()) {
				right = false;
				break;
			}
			if((Math.abs(x-enemy.getX())<10) &&
					Math.abs(y-enemy.getY())>0 && Math.abs(y-enemy.getY()) <50) {
				System.out.println("물방울과 적군 충돌 ");
				if(enemy.getState()==0) {
					attack();
					break;
				}
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		up();

	}

	@Override
	public void up() {
		up = true;
		while (up) {
			y--;
			setLocation(x, y);

			if (backgroundBubbleService.topWall()) {
				up = false;
				break;
			}
			
			try {
				if(state== 0){//기본물방울
					Thread.sleep(1);
				}else {//적을 가둔 물방울
					Thread.sleep(10);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(state==0) clearBubble();
		
	}
	@Override
	public void attack() {
		state =1;
		enemy.setState(1);
		setIcon(bubbled);
		mContext.remove(enemy);//메모리에서 사라지게 한다.(가비지컬렉션에 있고 즉시 삭제되는것은 아님)
		mContext.repaint();//화면갱신
	}
	
	private void clearBubble() {
		try {
			Thread.sleep(1000);
			setIcon(bomb);
			Thread.sleep(500); 
			mContext.remove(this); //BubbleFrame의 bubble이 heap에서 소멸
			mContext.repaint(); //BubbleFrame을 다시 그린다(메모리에 없는건 안그림)
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public void clearBubbled() {
		new Thread(() -> {
			System.out.println("clearBubbled");
			try {
				up= false;
				setIcon(bomb);
				Thread.sleep(1000);
				mContext.remove(this);
				mContext.repaint();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		
	}
	
}
