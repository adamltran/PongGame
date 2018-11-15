package application;


import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {

	//Collision/Input Flags
	public boolean leftUP, leftDOWN, rightUP, rightDOWN, topCollide, botCollide, leftCollide, rightCollide, paddleCollide;
	public boolean gameOver = false;
	//Window Size
	public int WIDTH = 800;
	public int HEIGHT = 400;
	//Ball Velocities
	public double ballxSpeed = 6;
	public double bDX = ballxSpeed;
	public double bDY = 9;
	public double pDY = 9;
	//Player Scores
	public int player1Score = 0;
	public int player2Score = 0;
	//Winning Point
	public int winningPoint = 3;

	//Sounds ==========================
	//Ping Pong Hit
	String musicFile = "resource/Ping Pong.wav";
	AudioClip mediaPlayer = new AudioClip(new File(musicFile).toURI().toString());

	
	@Override
	public void start(Stage primaryStage) {

		try {
			
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,WIDTH,HEIGHT);

			//Title
			primaryStage.setTitle("Pong");
			primaryStage.setScene(scene);

			//Background
			//
			Rectangle rect = new Rectangle(WIDTH, HEIGHT, Color.BLACK);
			//
			scene.setFill(Color.BLACK);
			Color color = Color.LAWNGREEN;
			Rectangle[] midLine = new Rectangle[24];
			Rectangle r = new Rectangle(2, 10, color);
			r.setTranslateX(scene.getWidth()/2);
			r.setTranslateY(40);
			midLine[0] = r;
			for(int i = 1; i < 24; i++) {
				midLine[i] = new Rectangle(2, 10, color);
				midLine[i].setTranslateX(midLine[i-1].getTranslateX());
				midLine[i].setTranslateY(midLine[i-1].getTranslateY() + midLine[i-1].getHeight()*1.5);
			}

			root.getChildren().add(rect);
			root.getChildren().addAll(midLine);

			//Dashboard
			HBox hbox1 = new HBox();
			hbox1.setTranslateX(scene.getWidth()/2 - 118);
			Text dashboard = new Text();
			dashboard.setFont(Font.font("Calibri", 20));
			dashboard.setText("Player 1: \t" + player1Score + "\t" + "Player 2: \t" + player2Score);
			dashboard.setStroke(Color.WHITE);
			dashboard.setFill(Color.WHITE);

			
			
			
			//Win Screen
//			HBox hbox2 = new HBox();
			Text winScreen = new Text();
			winScreen.setTranslateX(-230);
			winScreen.setTranslateY(HEIGHT/2.5);
			winScreen.setFont(Font.font("Calibri", 30));
			winScreen.setStroke(Color.RED);
			winScreen.setFill(Color.RED);
		
			hbox1.getChildren().addAll(dashboard, winScreen);
//			hbox2.getChildren().addAll(winScreen);
			root.getChildren().addAll(hbox1);

			//Player1
			Paddle player1 = new Paddle(10, 80, Color.GHOSTWHITE);
			placeObject(player1, 0, scene.getHeight()/2);

			
			//Player2
			Paddle player2 = new Paddle(10, 80, Color.GHOSTWHITE);
			placeObject(player2, WIDTH - player2.getWidth(), scene.getHeight()/2);

			//Ball
//			Ellipse b = new Ellipse(200, 8);
//			b.setStroke(Color.LIGHTBLUE);
			Ball b = new Ball(8, color);
			placeObject(b, scene.getWidth()/2, scene.getHeight()/2);
			root.getChildren().addAll(player1, player2, b);

			primaryStage.show();

			AnimationTimer timer = new AnimationTimer() {

				@Override
				public void handle(long now) {
					double leftPDY = 0;
					double rightPDY = 0;
					topCollide = false;
					botCollide = false;
					leftCollide = false;
					rightCollide = false;
					paddleCollide = false;

					dashboard.setText("Player 1: \t" + player1Score + "\t" + "Player 2: \t" + player2Score);

					try {
						endScreen();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if(gameOver) {
						HBox hbox1 = new HBox();
						hbox1.setTranslateX(scene.getWidth()/2.64);
						hbox1.setTranslateY(scene.getHeight()/2);
						//Play Again Button
						Button playAgain = new Button("Play Again");
						playAgain.setMinWidth(200);
						playAgain.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								System.out.println("Reset Attempt");
							}
	
						});
						hbox1.getChildren().add(playAgain);
						root.getChildren().add(hbox1);
						if(player1Score > player2Score) {
							winScreen.setText("PLAYER 1 WINS");
						}
						else{
							winScreen.setText("PLAYER 2 WINS");

						}
						
						this.stop();
					}

					//Player1 Controls
//					if(leftUP) {
//						leftPDY = -pDY;
//					}
//					if(leftDOWN) {
//						leftPDY = pDY;
//					}
					
					//Player1 CPU
					if(Math.abs(player1.getTranslateY() + player1.getHeight()/2 - b.getTranslateY()) > player1.getHeight()/4 &&
							b.getTranslateX() < WIDTH/*2*WIDTH/5*/)  {
						if(player1.getTranslateY() + player1.getHeight()/2 > b.getTranslateY()) {
							leftPDY = -pDY;
						}
						else {
							leftPDY = pDY;
						}
					}
					
					//Left Paddle Movement
					moveObject(player1, leftPDY);

					//PLayer2 Controls
//					if(rightUP) {
//						rightPDY = -pDY;
//					}
//					if(rightDOWN) {
//						rightPDY = pDY;
//					}
					
					//Player2 CPU
					if(Math.abs(player2.getTranslateY() + player2.getHeight()/2 - b.getTranslateY()) > player2.getHeight()/4 &&
							b.getTranslateX() > 2*WIDTH/5) {
						if(player2.getTranslateY() + player2.getHeight()/2 > b.getTranslateY()) {
							rightPDY = -pDY;
						}
						else {
							rightPDY = pDY;
						}
					}
					
					//Right Paddle Movement
					moveObject(player2, rightPDY);

					//Handles Ball Movement
					moveObject(b, bDX, bDY);
					
					//Collisions with Edge of Windows
					checkWallCollisions(b);
					//Handles Paddle-Ball Collisions
					checkCollisions(player1, b);
					checkCollisions(player2, b);

					//Reverse ball velocity when colliding with paddles
					if(paddleCollide) {
						mediaPlayer.setRate(((Math.random() - .5) * 2)*.1 + 1 );
						mediaPlayer.play();
						bDX = -bDX*((Math.abs(bDX) < 33) ? 1.05 : 1);
//						System.out.println(bDX);
						bDY = ((Math.random() - .5) * 2 * 7);
						moveObject(b, bDX, bDY);
						paddleCollide = false;
					}
					else if(rightCollide) {
						//Player1 points incremented if Player2 misses the ball
						if (!gameOver) {
							player1Score += 1;
							bDX = ballxSpeed;
							//Resets ball
							try {
								//Small pause before starting next round
								Thread.sleep(1000);
								placeObject(b, scene.getWidth()*.25, Math.random()*scene.getHeight());
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

					}
					else if(leftCollide) {
						if(!gameOver) {
							//Player2 points incremented if Player1 misses the ball
							player2Score += 1;
							bDX = ballxSpeed;
							placeObject(b, scene.getWidth()*.75, Math.random()*scene.getHeight());
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					else if(topCollide || botCollide) {
						//top/bottom Edge of Window Bounce
						bDY = -bDY;
					}

					


				}
			};
			timer.start();



			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					switch(event.getCode()) {
					//Player1
					case W :
						leftUP = true;
						break;
					case S :
						leftDOWN = true;
						break;
						//Player2
					case UP :
						rightUP = true;
						break;
					case DOWN :
						rightDOWN = true;
						break;
					default :
						break;
					}
				}	
			});

			scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					switch(event.getCode()) {
					//Player1
					case W :
						leftUP = false;
						break;
					case S :
						leftDOWN = false;
						break;
						//Player2
					case UP :
						rightUP = false;
						break;
					case DOWN :
						rightDOWN= false;
						break;
					default :
						break;
					}

				}

			});


		} catch(Exception e) {
			System.out.print("exception");
			e.printStackTrace();
		}
	}

	//Animates paddles
	public void moveObject(Shape shape, double dy) {
		if(dy == 0) {
			return;
		}
		else if(shape.getTranslateY() + dy > HEIGHT - shape.getBoundsInLocal().getHeight() ||
				shape.getTranslateY() + dy < 0) {
			return;
		}
		else {
			shape.setTranslateY(shape.getTranslateY() + dy);
		}

	}

	//Animates ball
	public void moveObject(Shape shape, double dx, double dy) {
		if(dx == 0 && dy == 0) {
			return;
		}
		else {
			shape.setTranslateX(shape.getTranslateX() + dx);
			shape.setTranslateY(shape.getTranslateY() + dy);
		}
	}

	//Puts Object in Given Coordinates
	public void placeObject(Shape shape, double x, double y) {
		shape.setTranslateX(x);
		shape.setTranslateY(y);
	}

	//Prints X,Y Coordinates
	public String locationToString(Shape shape) {
		return "X: " + shape.getTranslateX() + " " + "Y: " + shape.getTranslateY();
	}

	//Handles Wall Collisions
	public void checkWallCollisions(Shape shape) {
		//Right Wall
		if(shape.getTranslateX() > WIDTH - shape.getBoundsInLocal().getWidth()/2) {
			rightCollide = true;
		}
		//Left Wall
		if (shape.getTranslateX() < shape.getBoundsInLocal().getWidth()/2) {
			leftCollide = true;
		}
		//Bottom Wall
		if(shape.getTranslateY() + bDY > HEIGHT - shape.getBoundsInLocal().getHeight() +  shape.getBoundsInLocal().getHeight()/2) {
			botCollide = true;
			//			System.out.println("Bottom Side collision");

		}
		//Top Wall
		if (shape.getTranslateY() + bDY < shape.getBoundsInLocal().getHeight() - shape.getBoundsInLocal().getHeight()/2) {
			topCollide = true;
			//			System.out.println("Top Side collision");
		}
	}

	//Handles Paddle-Ball Collisions
	public void checkCollisions(Shape paddle, Shape ball) {
		if (paddle.getBoundsInParent().intersects(ball.getBoundsInParent())) {
			paddleCollide = true;
			//			System.out.println("paddle collision");
		}
	}

	//Triggers End of Game
	public void endScreen() throws InterruptedException {
		if (player1Score >= winningPoint || player2Score >= winningPoint) {
			gameOver = true;
		}
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}
