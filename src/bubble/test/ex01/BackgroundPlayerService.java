package bubble.test.ex01;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackgroundPlayerService implements Runnable {

	private BufferedImage image;
	private Player player;

	public BackgroundPlayerService(Player player) {
		this.player = player;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		while (true) {
			
			// 색상확인
			Color leftColor = new Color(image.getRGB(player.getX() -10, player.getY() +25));
			Color rightColor = new Color(image.getRGB(player.getX() +50+15, player.getY() +25));
			int bottomColor = image.getRGB(player.getX(), player.getY() +50 +5);
			
			//바닥충돌 확인
			if(bottomColor !=-1) {
				System.out.println("바닥색"+bottomColor);
				System.out.println("바닥 충돌");
				player.setDown(false);
			}
			
			//외벽 충돌 확인
			if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
					System.out.println("왼색"+leftColor);
					System.out.println("왼쪽벽 충돌");
					player.setLeftWallCrash(true);
					player.setLeft(false);
			} else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
				System.out.println("오른 색"+rightColor);
				System.out.println("오른벽 충돌");
				player.setRightWallCrash(true);
				player.setRight(false);
			} else {
				player.setLeftWallCrash(false);
				player.setRightWallCrash(false);
			}

				try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
