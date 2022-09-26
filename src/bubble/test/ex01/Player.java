package bubble.test.ex01;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

//class Player ->new 가능한 애들! 게임에 존재할 수 있음.(추상메서드 가질수 없다.)
@Getter
@Setter
public class Player extends JLabel implements Moveable {

	// 위치상태
	private int x;
	private int y;

	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	private ImageIcon playerR, playerL;

	public Player() {
		initObject();
		initSetting();
	}

	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");

	}

	private void initSetting() {
		x = 55;
		y = 535;

		left = false;
		right = false;
		up = false;
		down = false;

		setIcon(playerR);
		setSize(50, 50);
		setLocation(x, y);
	}

	// 이벤트 핸들러 부분
	@Override
	public void left() {
		System.out.println("Left");
		left = true;
		new Thread(() -> {
			while(left) {
				setIcon(playerL);
				x = x - 1;
				setLocation(x, y);
				try {
					Thread.sleep(10);//0.01초
				} catch (Exception e) {
					}
			}
		}).start();
	}

	@Override
	public void right() {
		right = true;
		System.out.println("Right");
		new Thread(() -> {
			while(right) {
			setIcon(playerR);
			x = x + 1;
			setLocation(x, y);
			try {
				Thread.sleep(10);//0.01초
			} catch (Exception e) {
				}
			}
		}).start();
	
	}

	@Override
	public void up() {
		System.out.println("점프");
	}

	@Override
	public void down() {

	}
}
