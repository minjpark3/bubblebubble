package game.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import game.component.Enemy;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackgroundEnemyService implements Runnable {

	private BufferedImage image;
	private Enemy enemy;
	

	// 플레이어, 버블 감지 필요
	public BackgroundEnemyService(Enemy enemy) {
		this.enemy = enemy;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		while (enemy.getState()==0) {
					// 색상확인
			Color leftColor = new Color(image.getRGB(enemy.getX() - 5, enemy.getY() + 25));
			Color rightColor = new Color(image.getRGB(enemy.getX() + 50 + 15, enemy.getY() + 25));
			// -2가 나온다는 뜻은 바닥에 색깔이 없이 흰색
			int bottomColor = image.getRGB(enemy.getX() + 20, enemy.getY() + 50 + 5) // -1
					+ image.getRGB(enemy.getX() + 50 + 10, enemy.getY() + 50 + 5);

			// 바닥충돌 확인
			if (bottomColor != -2) {
				// System.out.println("바닥색"+bottomColor);
				// System.out.println("바닥 충돌");
				enemy.setDown(false);
			} else {
				if (!enemy.isUp() && !enemy.isDown()) {
					enemy.down();
				}
			}

			// 외벽 충돌 확인
			if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
				// System.out.println("왼색"+leftColor);
				// System.out.println("왼쪽벽 충돌");
				enemy.setLeft(false);
				if(!enemy.isRight()) {
					enemy.right();
				}
			} else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
				// System.out.println("오른 색"+rightColor);
				// System.out.println("오른벽 충돌");
				enemy.setRight(false);
				if(!enemy.isLeft()) {
					enemy.left();
				}
			} 

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
