/*
 * PONG GAME REQUIREMENTS
 * This simple "tennis like" game features two paddles and a ball, 
 * the goal is to defeat your opponent by being the first one to gain 3 point,
 *  a player gets a point once the opponent misses a ball. 
 *  The game can be played with two human players, one on the left and one on 
 *  the right. They use keyboard to start/restart game and control the paddles. 
 *  The ball and two paddles should be red and separating lines should be green. 
 *  Players score should be blue and background should be black.
 *  Keyboard requirements:
 *  + P key: start
 *  + Space key: restart
 *  + W/S key: move paddle up/down
 *  + Up/Down key: move paddle up/down
 *  
 *  Version: 0.5
 */
package vn.vanlanguni.ponggame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author Invisible Man
 *
 */
public class PongPanel extends JPanel implements ActionListener, KeyListener {
	private SoundPlayer chamthanh, chamnhanvat;
	private static final long serialVersionUID = -1097341635155021546L;

	private boolean showTitleScreen = true;
	private boolean playing;
	private boolean gameOver;

	/** Background. */
	//private Color backgroundColor = Color.BLACK;
	
	ImageIcon imageback = new ImageIcon("Image\\giphy.gif");
	ImageIcon imaovergame = new ImageIcon("Image\\15218205_369524973393374_1496288073_n.jpg");
	ImageIcon imagetitle = new ImageIcon("Image\\Matrix.gif");
	ImageIcon ponggame = new ImageIcon("Image\\ponggame2.gif");
	ImageIcon pressP = new ImageIcon("Image\\press  pp.gif");
	ImageIcon restart = new ImageIcon("Image\\restart.gif");
	
	ImageIcon imageright = new ImageIcon("Image\\right2.png");
	ImageIcon imageleft = new ImageIcon("Image\\left3.png");
	ImageIcon sumpaddle = new ImageIcon("Image\\plusssssssss.png");
	ImageIcon minuspaddle = new ImageIcon("Image\\minussssssss.png");
	

	/** State on the control keys. */
	private boolean upPressed;
	private boolean downPressed;
	private boolean wPressed;
	private boolean sPressed;

	/** The ball: position, diameter */
	private int ballX = 235;
	private int ballY = 215;
	private int diameter = 20;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;

	/** Player 1's paddle: position and size */
	private int playerOneX = 0;
	private int playerOneY = 205;
	private int playerOneWidth = 10;
	private int playerOneHeight = 50;

	/** Player 2's paddle: position and size */
	private int playerTwoX = 475;
	private int playerTwoY = 205;
	private int playerTwoWidth = 10;
	private int playerTwoHeight = 60;

	/** Speed of the paddle - How fast the paddle move. */
	private int paddleSpeed = 5;

	/** Player score, show on upper left and right. */
	private int playerOneScore;
	private int playerTwoScore;
	
	private int sumX, sumY; // iss 9
	private int sumcX, sumcY; // iss 10
	
	private int count15s = 15; // iss 9
	private int count15s2 = 15;// iss 10
	
	private int timeToDisplay;
	
	
	Random rand = new Random();
	
	private boolean vacham = false;// iss 9
	private boolean vacham2 = false; // iss 10
	
	private static boolean lastcham = false; // iss 9
	private static boolean lastcham2 = false; // iss 10
	
	private static boolean check; // iss 9
	private static boolean check2; // iss 10
	
	//private boolean selectima = false;

	private int countima = 1; // iss 9 10
	
	private int champaddle = 0; // iss 10
	private boolean champaddle2 = false;
	
	

	/** Construct a PongPanel. */
	public PongPanel() {
		//setBackground(backgroundColor);

		sumX = ThreadLocalRandom.current().nextInt(50, 450);
		sumY = ThreadLocalRandom.current().nextInt(50, 450);
		sumcX = ThreadLocalRandom.current().nextInt(50, 450);
		sumcY = ThreadLocalRandom.current().nextInt(50, 450);
		
		// listen to key presses
		setFocusable(true);
		addKeyListener(this);

		// call step() 60 fps
		Timer timer = new Timer(1000 / 60, this);
		timer.start();
	}

	/** Implement actionPerformed */
	public void actionPerformed(ActionEvent e) {
		step();
	}

	/** Repeated task */
	public void step() {

		if (playing) {

			/* Playing mode */

			chamnhanvat = new SoundPlayer(new File("Sounds\\beep-21.wav"));
			chamthanh = new SoundPlayer(new File("Sounds\\beep-07.wav"));

			// move player 1
			// Move up if after moving, paddle is not outside the screen
			if (wPressed && playerOneY - paddleSpeed >= 0) {
				playerOneY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (sPressed && playerOneY + playerOneHeight + paddleSpeed <= getHeight()) {
				playerOneY += paddleSpeed;
			}

			// move player 2
			// Move up if after moving paddle is not outside the screen
			if (upPressed && playerTwoY - paddleSpeed >= 0) {
				playerTwoY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (downPressed && playerTwoY + playerTwoHeight + paddleSpeed <= getHeight()) {
				playerTwoY += paddleSpeed;
			}

			/*
			 * where will the ball be after it moves? calculate 4 corners: Left,
			 * Right, Top, Bottom of the ball used to determine whether the ball
			 * was out yet
			 */
			int nextBallLeft = ballX + ballDeltaX;
			int nextBallRight = ballX + diameter + ballDeltaX;
			// FIXME Something not quite right here
			int nextBallTop = ballY + ballDeltaY;
			int nextBallBottom = ballY + diameter + ballDeltaY;

			// Player 1's paddle position
			int playerOneRight = playerOneX + playerOneWidth;
			int playerOneTop = playerOneY;
			int playerOneBottom = playerOneY + playerOneHeight;

			// Player 2's paddle position
			float playerTwoLeft = playerTwoX;
			float playerTwoTop = playerTwoY;
			float playerTwoBottom = playerTwoY + playerTwoHeight;

			// ball bounces off top and bottom of screen
			if (nextBallTop < 0 || nextBallBottom > getHeight()) {
				
				ballDeltaY *= -1;
				if (champaddle2 == true) {
					champaddle++;
					
				}
				chamthanh.play();
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {

					playerTwoScore++;
					playerOneHeight = 50;
					playerTwoHeight = 50;

					playerTwoX = 475;
					playerTwoY = 205;
					playerOneX = 0;
					playerOneY = 205;

					

					lastcham = true;

					// Player 2 Win, restart the game
					if (playerTwoScore == 3) {
						playing = false;
						gameOver = true;
					}
					ballX = 235;
					ballY = 215;
				} else {
					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
					lastcham = false;
					check = true; // lastcham player one
					
					if (champaddle2 == true) {
						champaddle++;
					} // iss 10

					chamthanh.play();
				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {

					playerOneScore++;

					playerOneHeight = 50;
					playerTwoHeight = 50;

					playerTwoX = 475;
					playerTwoY = 205;
					playerOneX = 0;
					playerOneY = 205;

					lastcham = true;
					
					// Player 1 Win, restart the game
					if (playerOneScore == 3) {
						playing = false;
						gameOver = true;
					}
					ballX = 235;
					ballY = 215;
				} else {

					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;

					if (champaddle2 == true) {
						champaddle++;
					}
					check = false; // lastcham player 2
					lastcham = false;
					chamthanh.play();
				}
			}

			// move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;
		}

		// stuff has moved, tell this JPanel to repaint itself
		count15s += 15;
		count15s2 += 15;
		
		repaint();
	}

	/** Paint the game screen. */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		
		if (showTitleScreen) {

			 // background title
            g.drawImage(imagetitle.getImage(), 0, 0, 500, 500, null);

			/* Show welcome screen */
          

			// Draw game title and start message
//            g.setColor(Color.red);
//			g.setFont(new Font("NewellsHand", Font.PLAIN, 46));
//			g.drawString("Pong Game", 130, 200);
            
            g.drawImage(ponggame.getImage(), 35, 75, null);
            

			// FIXME Wellcome message below show smaller than game title
//			g.setColor(Color.red);
//            g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
//            g.drawString("Press 'P' to play.", 175, 400);
            g.drawImage(pressP.getImage(), 20, 350, null);
            
		} else if (playing) {

			/* Game is playing */
			g.drawImage(imageback.getImage(), 0, 0, 500, 500, null);

			// set the coordinate limit
			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;

			// draw dashed line down center
			g.setColor(Color.WHITE);
			for (int lineY = 0; lineY < getHeight(); lineY += 50) {
				g.drawLine(240, lineY, 240, lineY + 25);

			}

			// draw "goal lines" on each side
			g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
			g.drawLine(playerTwoLeft, 0, playerTwoLeft, getHeight());

			// draw the scores
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 100, 100); // Player 1
																	// score
			g.drawString(String.valueOf(playerTwoScore), 400, 100); // Player 2
																	// score

			// draw the ball
			g.setColor(Color.WHITE);
			g.fillOval(ballX, ballY, diameter, diameter);

			// draw the paddles
			g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
			g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
			
			if (count15s % 15000 == 0) {
				// g.clearRect(sumX, sumY, sumpaddle.getIconWidth(),
				// sumpaddle.getIconHeight());
				countima++;

				

				sumX = ThreadLocalRandom.current().nextInt(50, 450);
				sumY = ThreadLocalRandom.current().nextInt(50, 450);

				// g.drawImage(sumpaddle.getImage(), sumX, sumY, null);
				vacham = false;
				
		
			}
			if (vacham == false) {
				if (countima % 2 == 0) {
					g.drawImage(sumpaddle.getImage(), sumX, sumY, null);
					
				} else {
					g.drawImage(minuspaddle.getImage(), sumX, sumY, null);
				}

			}
			int xwA1 = sumX + sumpaddle.getIconWidth() / 2;
			int xwB1 = ballX + diameter / 2;
			int wwAB1 = (sumpaddle.getIconWidth() + diameter) / 2;

			// =================================== Huong Y
			int yHA1 = sumY + sumpaddle.getIconHeight() / 2;
			int yHB1 = ballY + diameter / 2;
			int HHAB1 = (sumpaddle.getIconHeight() + diameter) / 2;

			// dieu kien khi cham
			if (((Math.abs(xwA1 - xwB1) < Math.abs(wwAB1)) && (Math.abs(yHA1 - yHB1) < Math.abs(HHAB1)))) {
				if (lastcham == false) {
					if (check == true) {
						if (countima % 2 == 0) {
							if (playerOneHeight <= 50) {
								playerOneHeight = playerOneHeight + 25;
							} else {
								playerOneHeight = playerOneHeight;
							}
						}

						if (countima % 2 != 0) {
							if (playerOneHeight <= 25) {
								playerOneHeight = playerOneHeight;
							} else {
								playerOneHeight = playerOneHeight - 25;
							}
						}
					}

					if (check == false) {
						if (countima % 2 == 0) {
							if (playerTwoHeight <= 50) {
								playerTwoHeight = playerTwoHeight + 25;
							} else {
								playerTwoHeight = playerTwoHeight;
							}
						}

						if (countima % 2 != 0) {
							if (playerTwoHeight <= 25) {
								playerTwoHeight = playerTwoHeight;
							} else {
								playerTwoHeight = playerTwoHeight - 25;
							}
						}

					}
				}

				vacham = true;

			}

			// ISS 9 END
			// ISS 9 END	
			// ISS 10
			if (count15s2 % 10000 == 0) {
				// g.clearRect(sumX, sumY, sumpaddle.getIconWidth(),
				// sumpaddle.getIconHeight());
				countima++;

				

				sumcX = ThreadLocalRandom.current().nextInt(50, 450);
				sumcY = ThreadLocalRandom.current().nextInt(50, 450);

				// g.drawImage(sumpaddle.getImage(), sumX, sumY, null);
				vacham2 = false;
				
				
			}
			if (vacham2 == false) {
				if (countima % 2 == 0) {
					g.drawImage(imageleft.getImage(), sumcX, sumcY, null);
					
				} else {
					g.drawImage(imageright.getImage(), sumcX, sumcY, null);
				}

			}
			
			
			int xwA2 = sumcX + imageleft.getIconWidth() / 2;
			int xwB2 = ballX + diameter / 2;
			int wwAB2 = (imageleft.getIconWidth() + diameter) / 2;

			// =================================== Huong Y
			int yHA2 = sumcY + imageleft.getIconHeight() / 2;
			int yHB2 = ballY + diameter / 2;
			int HHAB2 = (imageleft.getIconHeight() + diameter) / 2;

			// dieu kien khi cham
			if (((Math.abs(xwA2 - xwB2) < Math.abs(wwAB2)) && (Math.abs(yHA2 - yHB2) < Math.abs(HHAB2)))) {
				
			vacham2 = true;

			}
		} else if (gameOver) {

			/* Show End game screen with winner name and score */
			g.drawImage(imagetitle.getImage(), 0, 0, 500, 500, null);
			// Draw scores
			// TODO Set Blue color
			g.setColor(Color.white);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 100, 60);
			g.drawString(String.valueOf(playerTwoScore), 400, 60);
			// Draw the winner name
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			if (playerOneScore > playerTwoScore) {
				g.drawString("Player 1 Wins!", 165, 200);
			} else {
				g.drawString("Player 2 Wins!", 165, 200);
			}

			// Draw Restart message
			// g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
//			g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
//			g.drawString("Press 'space' to restart.", 175, 400);
			g.drawImage(restart.getImage(), 70, 400, null);
			// TODO Draw a restart message
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (showTitleScreen) {
			if (e.getKeyChar() == 'p') {
				showTitleScreen = false;
				playing = true;
			}
		} else if (playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = true;
			}
		} else if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
			gameOver = false;
			showTitleScreen = true;
			playerOneY = 205;
			playerTwoY = 205;
			ballX = 235;
			ballY = 215;
			playerOneScore = 0;
			playerTwoScore = 0;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			wPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			sPressed = false;
		}
	}

}
