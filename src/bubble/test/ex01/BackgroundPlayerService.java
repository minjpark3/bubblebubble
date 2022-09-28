package bubble.test.ex01;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackgroundPlayerService implements Runnable {

	private BufferedImage image;
	private Player player;
	private List<Bubble> bubbleList;

	// 플레이어, 버블 감지 필요
	public BackgroundPlayerService(Player player) {
		this.player = player;
		this.bubbleList = player.getBubbleList();
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		while (true) {
			// 1.버블충돌 체크
			for (int i = 0; i < bubbleList.size(); i++) {
				Bubble bubble = bubbleList.get(i);
				if (bubble.getState() == 1) {
					if ((Math.abs(player.getX() - bubble.getX()) < 10) && Math.abs(player.getY() - bubble.getY()) > 0
							&& Math.abs(player.getY() - bubble.getY()) < 50) {
						System.out.println("적군 사살 완료 ");
						bubble.clearBubbled();
						break;
					}
				}
			}

			// 2.벽충돌 체크
			// 색상확인
			Color leftColor = new Color(image.getRGB(player.getX() - 5, player.getY() + 25));
			Color rightColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));
			// -2가 나온다는 뜻은 바닥에 색깔이 없이 흰색
			int bottomColor = image.getRGB(player.getX() + 20, player.getY() + 50 + 5) // -1
					+ image.getRGB(player.getX() + 50 + 10, player.getY() + 50 + 5);

			// 바닥충돌 확인
			if (bottomColor != -2) {
				// System.out.println("바닥색"+bottomColor);
				// System.out.println("바닥 충돌");
				player.setDown(false);
			} else {
				if (!player.isUp() && !player.isDown()) {
					player.down();
				}
			}

			// 외벽 충돌 확인
			if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
				// System.out.println("왼색"+leftColor);
				// System.out.println("왼쪽벽 충돌");
				player.setLeftWallCrash(true);
				player.setLeft(false);
			} else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
				// System.out.println("오른 색"+rightColor);
				// System.out.println("오른벽 충돌");
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
