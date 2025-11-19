package chen.julian;

//imports
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.Animation.Status;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.ScaleTransition;

public class App extends Application {
    // Create exit button
    public static Button exit = new Button("EXIT");
    // Create change theme button
    public static Button theme = new Button("Change Theme");
    // Create settings button
    public static Button settings = new Button();
    // create start button
    public static Button start = new Button("START");
    // create instructions page button
    public static Button instruction = new Button("How to Play");
    // create yellow go first button
    public static Button yellowfirst = new Button("Yellow");
    // create blue go first button
    public static Button bluefirst = new Button("Blue");
    // create restart game button
    public static Button restart = new Button("Return to home");

    // import board image
    ImageView board = new ImageView("connect4.5.png");
    // import settings picture
    ImageView settingpic = new ImageView("setting.png");
    // import pause icon
    ImageView pausepic = new ImageView("pause.png");
    // import background animation pictures
    ImageView backgroundanimation1 = new ImageView("smiley2.png");
    ImageView backgroundanimation2 = new ImageView("smiley3.png");
    ImageView backgroundanimation3 = new ImageView("smileyface.png");
    ImageView backgroundanimation4 = new ImageView("patel.png");

    // create backgrounds
    public static Rectangle background = new Rectangle(600, 600, Color.LIGHTSKYBLUE);
    public static Rectangle sbackground = new Rectangle(600, 600, Color.LIGHTSKYBLUE);
    public static Rectangle gbackground = new Rectangle(600, 600, Color.LIGHTSKYBLUE);
    public static Rectangle ibackground = new Rectangle(600, 600);

    // Create title
    public static Text title = new Text("CONNECT FOUR.5");
    // Create variable to change theme colours
    public static int colour;
    // Create settings on/off variable
    public static Boolean settingsshow = false;
    // create turn counter variable
    public static Boolean turn;
    // create tracking variable for start button(in settings or not)
    public static Boolean setstart = false;
    // variable to drop new disks everytime
    public static int count = 0;
    // counter that tracks paused/unpaused states of the game
    public static boolean ifpause = false;

    // create column counter variables
    public static int onec = 0;
    public static int twoc = 0;
    public static int threec = 0;
    public static int fourc = 0;
    public static int fivec = 0;
    public static int sixc = 0;
    public static int sevenc = 0;

    // create values for the game
    public static int columns = 7;
    public static int rows = 6;
    // public static long startTime = 0;

    // drop animation
    public static TranslateTransition drop;

    // array counter to check win conditions
    public static int[][] grid = new int[6][7];

    // public static AnimationTimer gameloop;

    @Override
    public void start(Stage gamewindow) {

        gamewindow.show();
        // create start screen pane
        Pane startlayout = new Pane();
        // create new scene for start window
        Scene startscene = new Scene(startlayout, 600, 600);
        gamewindow.setScene(startscene);
        startlayout.getChildren().add(background);

        // create menu pane
        Pane menulayout = new Pane();
        // create scene for settings
        Scene menuscene = new Scene(menulayout, 600, 600);
        menulayout.getChildren().add(sbackground);

        // create game pane
        Pane gamelayout = new Pane();
        // create game scene
        Scene gamescene = new Scene(gamelayout, 600, 600);
        gamelayout.getChildren().add(gbackground);

        // create instructions page
        Pane gameManual = new Pane();
        Scene manual = new Scene(gameManual, 600, 600);
        gameManual.getChildren().add(ibackground);

        // background animations
        gamelayout.getChildren().add(backgroundanimation1);
        backgroundanimation1.setLayoutX(0);
        backgroundanimation1.setLayoutY(5);
        backgroundanimation1.setFitWidth(100);
        backgroundanimation1.setPreserveRatio(true);
        roll(backgroundanimation1, 720, 3);

        gamelayout.getChildren().add(backgroundanimation2);
        backgroundanimation2.setLayoutX(-50);
        backgroundanimation2.setLayoutY(300);
        backgroundanimation2.setFitWidth(200);
        backgroundanimation2.setPreserveRatio(true);
        roll(backgroundanimation2, 720, 4);

        gamelayout.getChildren().add(backgroundanimation3);
        backgroundanimation3.setLayoutX(-20);
        backgroundanimation3.setLayoutY(200);
        backgroundanimation3.setFitWidth(140);
        backgroundanimation3.setPreserveRatio(true);
        roll(backgroundanimation3, 360, 7);

        gamelayout.getChildren().add(backgroundanimation4);
        backgroundanimation4.setLayoutX(-20);
        backgroundanimation4.setLayoutY(490);
        backgroundanimation4.setFitWidth(100);
        backgroundanimation4.setPreserveRatio(true);
        roll(backgroundanimation4, 360, 5);

        // background image(gameboard)
        gamelayout.getChildren().add(board);
        board.setFitWidth(600);
        board.setLayoutY(150);
        board.setVisible(false);

        // create player disks + animation
        Circle[] player = new Circle[42];
        for (int i = 0; i < player.length; i++) {
            player[i] = new Circle(20);
            player[i].setVisible(false);
            gamelayout.getChildren().add(player[i]);
        }
        drop = new TranslateTransition(Duration.millis(500));

        // create columns + column counter
        Rectangle one = new Rectangle(60, 323, Color.TRANSPARENT);
        gamelayout.getChildren().add(one);
        one.setX(33);
        one.setY(153);
        one.setVisible(false);
        Rectangle two = new Rectangle(60, 323, Color.TRANSPARENT);
        gamelayout.getChildren().add(two);
        two.setX(114);
        two.setY(153);
        two.setVisible(false);
        Rectangle three = new Rectangle(60, 323, Color.TRANSPARENT);
        gamelayout.getChildren().add(three);
        three.setX(194);
        three.setY(153);
        three.setVisible(false);
        Rectangle four = new Rectangle(60, 323, Color.TRANSPARENT);
        gamelayout.getChildren().add(four);
        four.setX(271);
        four.setY(153);
        four.setVisible(false);
        Rectangle five = new Rectangle(60, 323, Color.TRANSPARENT);
        gamelayout.getChildren().add(five);
        five.setX(347);
        five.setY(153);
        five.setVisible(false);
        Rectangle six = new Rectangle(60, 323, Color.TRANSPARENT);
        gamelayout.getChildren().add(six);
        six.setX(429);
        six.setY(153);
        six.setVisible(false);
        Rectangle seven = new Rectangle(60, 323, Color.TRANSPARENT);
        gamelayout.getChildren().add(seven);
        seven.setX(507);
        seven.setY(153);
        seven.setVisible(false);

        // who goes first text
        Text gofirst = new Text("Who goes first?");
        // create text size
        Font size = new Font(50);
        gamelayout.getChildren().add(gofirst);
        gofirst.setFont(size);
        gofirst.relocate(130, 150);

        // Title
        startlayout.getChildren().add(title);
        title.setFont(size);
        title.relocate(120, 100);

        // start game button
        startlayout.getChildren().add(start);
        start.relocate(215, 250);
        start.setPrefSize(170, 100);
        start.setOnAction(event -> {
            // start game
            if (setstart) {
                startlayout.getChildren().add(start);
                setstart = false;
            }
            gamewindow.setScene(gamescene);
            // gameloop.start();
        });

        // blue go first button
        gamelayout.getChildren().add(bluefirst);
        bluefirst.setPrefSize(100, 100);
        bluefirst.relocate(175, 350);
        bluefirst.setOnAction(event -> {
            gofirst.setVisible(false);
            turn = true;
            startgame(one, two, three, four, five, six, seven);
        });

        // yellow go first button
        gamelayout.getChildren().add(yellowfirst);
        yellowfirst.setPrefSize(100, 100);
        yellowfirst.relocate(325, 350);
        yellowfirst.setOnAction(event -> {
            gofirst.setVisible(false);
            turn = false;
            startgame(one, two, three, four, five, six, seven);
        });

        // set win screen text for blue win
        Text bluewin = new Text("BLUE WINS!");
        bluewin.setFont(size);
        gamelayout.getChildren().add(bluewin);
        bluewin.setVisible(false);
        bluewin.relocate(150, 80);

        //text for yellow win
        Text yellowwin = new Text("YELLOW WINS!");
        yellowwin.setFont(size);
        gamelayout.getChildren().add(yellowwin);
        yellowwin.setVisible(false);
        yellowwin.relocate(130, 80);

        //its a tie text
        Text nothing = new Text("IT'S A TIE");
        nothing.setFont(size);
        gamelayout.getChildren().add(nothing);
        nothing.setVisible(false);
        nothing.relocate(200, 80);

        // restart button
        gamelayout.getChildren().add(restart);
        restart.relocate(250, 500);
        restart.setPrefSize(100, 50);
        restart.setVisible(false);
        restart.setOnAction(event -> {
            gofirst.setVisible(true);
            restartgame(one, two, three, four, five, six, seven, player, bluewin, yellowwin, nothing);
            gamewindow.setScene(startscene);
        });

        // highlight columns when mouse hovers over it
//when mouse enters column, it is highlighted, until it is fully filled, where otherwise it will not be highlighted anymore 
        one.setOnMouseEntered(event -> {
            if (onec <= 270) {
                one.setFill(Color.rgb(211, 211, 211, 0.5));
            } else {
                one.setFill(Color.TRANSPARENT);
            }
        });
//when mouse exits column, it is not highlighted
        one.setOnMouseExited(event -> {
            if (onec <= 270) {
                one.setFill(Color.TRANSPARENT);
            }
        });
        // drop disk in column
        one.setOnMouseClicked(event -> {
            if (onec <= 270) {
                player[count].setCenterX(one.getX() + 30);
                player[count].setVisible(true);
                if (turn) {
                    player[count].setFill(Color.DARKBLUE);
                    grid[(270 - onec) / 54][0] = 1;
                } else {
                    player[count].setFill(Color.YELLOW);
                    grid[(270 - onec) / 54][0] = -1;
                }
                drop.setByY(450 - onec);
                dropDisk(player[count]);
                onec += 54;
            }
            check(bluewin, yellowwin, nothing);
            if (count == 42)
                nothing.setVisible(true);
        });

        //highlight column when mouse is hovering over it
        two.setOnMouseEntered(event -> {
            if (twoc <= 270) {
                two.setFill(Color.rgb(211, 211, 211, 0.5));
            } else {
                two.setFill(Color.TRANSPARENT);
            }
        });

        //un-highlight collumn when mouse exits collumn
        two.setOnMouseExited(event -> {
            if (twoc <= 270) {
                two.setFill(Color.TRANSPARENT);
            }
        });

        //drop disc when collumn is clicked
        two.setOnMouseClicked(event -> {
            if (twoc <= 270) {
                player[count].setCenterX(two.getX() + 30);
                player[count].setVisible(true);
                if (turn) {
                    player[count].setFill(Color.DARKBLUE);
                    grid[(270 - twoc) / 54][1] = 1;
                } else {
                    player[count].setFill(Color.YELLOW);
                    grid[(270 - twoc) / 54][1] = -1;
                }
                drop.setByY(450 - twoc);
                dropDisk(player[count]);
                twoc += 54;
            }
            check(bluewin, yellowwin, nothing);
            if (count == 42)
                nothing.setVisible(true);
        });

        //highlight column when mouse hovers over it
        three.setOnMouseEntered(event -> {
            if (threec <= 270) {
                three.setFill(Color.rgb(211, 211, 211, 0.5));
            } else {
                three.setFill(Color.TRANSPARENT);
            }
        });

        //unhighlight column when mouse exits
        three.setOnMouseExited(event -> {
            if (threec <= 270) {
                three.setFill(Color.TRANSPARENT);
            }
        });

        //drop disc when collumn is clicked
        three.setOnMouseClicked(event -> {
            if (threec <= 270) {
                player[count].setCenterX(three.getX() + 30);
                player[count].setVisible(true);
                if (turn) {
                    player[count].setFill(Color.DARKBLUE);
                    grid[(270 - threec) / 54][2] = 1;
                } else {
                    player[count].setFill(Color.YELLOW);
                    grid[(270 - threec) / 54][2] = -1;
                }
                drop.setByY(450 - threec);
                dropDisk(player[count]);
                threec += 54;
            }
            check(bluewin, yellowwin, nothing);
            if (count == 42)
                nothing.setVisible(true);
        });

        //highlight column when mouse hovers over it
        four.setOnMouseEntered(event -> {
            if (fourc <= 270) {
                four.setFill(Color.rgb(211, 211, 211, 0.5));
            } else {
                four.setFill(Color.TRANSPARENT);
            }
        });

        //un-highlight when mouse leaves column
        four.setOnMouseExited(event -> {
            if (fourc <= 270) {
                four.setFill(Color.TRANSPARENT);
            }
        });

        //drop disc when collumn is clicked
        four.setOnMouseClicked(event -> {
            if (fourc <= 270) {
                player[count].setCenterX(four.getX() + 30);
                player[count].setVisible(true);
                if (turn) {
                    player[count].setFill(Color.DARKBLUE);
                    grid[(270 - fourc) / 54][3] = 1;
                } else {
                    player[count].setFill(Color.YELLOW);
                    grid[(270 - fourc) / 54][3] = -1;
                }
                drop.setByY(450 - fourc);
                dropDisk(player[count]);
                fourc += 54;
            }
            check(bluewin, yellowwin, nothing);
            if (count == 42)
                nothing.setVisible(true);
        });
        
        //highlight column when mouse hovers over it
        five.setOnMouseEntered(event -> {
            if (fivec <= 270) {
                five.setFill(Color.rgb(211, 211, 211, 0.5));
            } else {
                five.setFill(Color.TRANSPARENT);
            }
        });

        //un-highlight column when mouse exits it
        five.setOnMouseExited(event -> {
            if (fivec <= 270) {
                five.setFill(Color.TRANSPARENT);
            }
        });

        //drop disk when collumn is clicked
        five.setOnMouseClicked(event -> {
            if (fivec <= 270) {
                player[count].setCenterX(five.getX() + 32);
                player[count].setVisible(true);
                if (turn) {
                    player[count].setFill(Color.DARKBLUE);
                    grid[(270 - fivec) / 54][4] = 1;
                } else {
                    player[count].setFill(Color.YELLOW);
                    grid[(270 - fivec) / 54][4] = -1;
                }
                drop.setByY(450 - fivec);
                dropDisk(player[count]);
                fivec += 54;
            }
            //check for win
            check(bluewin, yellowwin, nothing);
            if (count == 42)
                nothing.setVisible(true);
        });
        
        //highlight column when mouse hovers over it
        six.setOnMouseEntered(event -> {
            if (sixc <= 270) {
                six.setFill(Color.rgb(211, 211, 211, 0.5));
            } else {
                six.setFill(Color.TRANSPARENT);
            }
        });
        //unhightlight collumn when mouse exits
        six.setOnMouseExited(event -> {
            if (sixc <= 270) {
                six.setFill(Color.TRANSPARENT);
            }
        });
        //drop disc when collumn is clicked
        six.setOnMouseClicked(event -> {
            if (sixc <= 270) {
                player[count].setCenterX(six.getX() + 30);
                player[count].setVisible(true);
                if (turn) {
                    player[count].setFill(Color.DARKBLUE);
                    grid[(270 - sixc) / 54][5] = 1;
                } else {
                    player[count].setFill(Color.YELLOW);
                    grid[(270 - sixc) / 54][5] = -1;
                }
                drop.setByY(450 - sixc);
                dropDisk(player[count]);
                sixc += 54;
            }
            //check for wins
            check(bluewin, yellowwin, nothing);
            if (count == 42)
                nothing.setVisible(true);
        });
        //highlight column when mouse is hovering collumn
        seven.setOnMouseEntered(event -> {
            if (sevenc <= 270) {
                seven.setFill(Color.rgb(211, 211, 211, 0.5));
            } else {
                seven.setFill(Color.TRANSPARENT);
            }
        });
        //unhightlight collumn when mouse exits
        seven.setOnMouseExited(event -> {
            if (sevenc <= 270) {
                seven.setFill(Color.TRANSPARENT);
            }
        });

        //drop disk when collumn is clicked
        seven.setOnMouseClicked(event -> {
            if (sevenc <= 270) {
                player[count].setCenterX(seven.getX() + 32);
                player[count].setVisible(true);
                if (turn) {
                    player[count].setFill(Color.DARKBLUE);
                    grid[(270 - sevenc) / 54][6] = 1;
                } else {
                    player[count].setFill(Color.YELLOW);
                    grid[(270 - sevenc) / 54][6] = -1;
                }
                drop.setByY(450 - sevenc);
                dropDisk(player[count]);
                sevenc += 54;
            }

            //check for wins
            check(bluewin, yellowwin, nothing);
            if (count == 42)
                nothing.setVisible(true);
        });

        // exit button
        menulayout.getChildren().add(exit);
        exit.relocate(265, 550);
        exit.setPrefSize(70, 40);
        exit.setOnAction(event -> {
            // exit game when clicked
            Platform.exit();
        });

        // change theme button
        menulayout.getChildren().add(theme);
        theme.relocate(225, 400);
        theme.setPrefSize(150, 100);
        theme.setOnAction(event -> {
            // change background colour when clicked(10 random colours)
            colour = (int) (Math.random() * 10);
            switch (colour) {
                case 0:
                    change(Color.CHOCOLATE);
                    break;
                case 1:
                    change(Color.CORNFLOWERBLUE);
                    break;
                case 2:
                    change(Color.DARKORCHID);
                    break;
                case 3:
                    change(Color.DARKGREEN);
                    break;
                case 4:
                    change(Color.GOLDENROD);
                    break;
                case 5:
                    change(Color.DARKSALMON);
                    break;
                case 6:
                    change(Color.ROSYBROWN);
                    break;
                case 7:
                    change(Color.CORNSILK);
                    break;
                case 8:
                    change(Color.LIGHTCYAN);
                    break;
                case 9:
                    change(Color.OLIVE);
                    break;
            }
        });

        // show instructions/game manual
        menulayout.getChildren().add(instruction);
        instruction.relocate(225, 100);
        instruction.setPrefSize(150, 100);
        instruction.setOnAction(event -> {
            gamewindow.setScene(manual);
        });

        // settings button
        startlayout.getChildren().add(settings);
        settings.relocate(550, 10);
        settings.setPrefSize(30, 30);
        settings.setOnAction(event -> {
            setstart = true;
            gamewindow.setScene(menuscene);
            menulayout.getChildren().add(start);
        });
        //add settings picture to button
        settingpic.setFitHeight(30);
        settingpic.setPreserveRatio(true);
        settings.setGraphic(settingpic);

        // instructions page
        Text instructions = new Text(
                "1. Players take turns dropping discs from the\n    top of the board to the lowest available space.\n2. Players must connect 4 of the same colored discs in a row to win.\n3. The game ends when there is a 4-in-a-row or a stalemate.\n4. Click \"esc\" to pause, pause icon to resume");
        Font instructionFont = new Font(18);
        instructions.setFont(instructionFont);
        instructions.setY(250);
        instructions.setX(40);
        instructions.setFill(Color.WHITE);
        gameManual.getChildren().add(instructions);

        //button to return to game from instructions page
        Button goback = new Button("Return to Game");
        gameManual.getChildren().add(goback);
        goback.relocate(250, 450);
        goback.setPrefSize(100, 100);
        goback.setOnAction(event -> {
            gamewindow.setScene(menuscene);
        });

        // unpause button
        Button unpause = new Button();
        gamelayout.getChildren().add(unpause);
        unpause.relocate(200, 200);
        unpause.setPrefSize(200, 200);
        unpause.setStyle("-fx-background-color: transparent");
        unpause.setVisible(false);
        unpause.setOnAction(event -> {
            unpause.setVisible(false);
            // pbackground.setVisible(false);
            restart.setDisable(false);
            ifpause = false;
            pause(one, two, three, four, five, six, seven);
        });
        pausepic.setFitHeight(200);
        pausepic.setPreserveRatio(true);
        unpause.setGraphic(pausepic);

        // pause game when esc key is pressed
        gamescene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                // pbackground.setVisible(true);
                unpause.setVisible(true);
                restart.setDisable(true);
                one.setDisable(true);
                ifpause = true;
                pause(one, two, three, four, five, six, seven);
            }
        });

    }

    //background animation animations
    public static void roll(ImageView pic, int angle, int time) {
        TranslateTransition tAnimation = new TranslateTransition(Duration.seconds(time), pic);
        // move the image 500 pixels
        tAnimation.setByX(500);
        // repeat animation "forever"
        tAnimation.setCycleCount(Animation.INDEFINITE);
        // reverse animation when at the end of its path(500px)
        tAnimation.setAutoReverse(true);
        // rotate image whileits moving
        RotateTransition rAnimation = new RotateTransition(Duration.seconds(time), pic);
        // rotate image 360 degrees
        rAnimation.setByAngle(angle);
        // rotate the image "forever"
        rAnimation.setCycleCount(Animation.INDEFINITE);
        // reverse animation when cycle is done
        rAnimation.setAutoReverse(true);
        // play both animations
        tAnimation.play();
        rAnimation.play();
    }

    // drop disk to lowest available space
    public void dropDisk(Circle Player) {
        drop.setNode(Player);
        drop.play();
        count++;
        turn = !turn;
    }

    // change colours
    public void change(Color c) {
        background.setFill(c);
        sbackground.setFill(c);
        gbackground.setFill(c);
    }

    // start game
    private void startgame(Rectangle r1, Rectangle r2, Rectangle r3, Rectangle r4, Rectangle r5, Rectangle r6,
            Rectangle r7) {
        bluefirst.setVisible(false);
        yellowfirst.setVisible(false);
        restart.setVisible(true);
        board.setVisible(true);
        r1.setVisible(true);
        r2.setVisible(true);
        r3.setVisible(true);
        r4.setVisible(true);
        r5.setVisible(true);
        r6.setVisible(true);
        r7.setVisible(true);
    }

    // restart game
    // reset all variables
    private void restartgame(Rectangle r1, Rectangle r2, Rectangle r3, Rectangle r4, Rectangle r5, Rectangle r6,
            Rectangle r7, Circle[] player, Text blue, Text yellow, Text no) {
        bluefirst.setVisible(true);
        yellowfirst.setVisible(true);
        start.setVisible(true);
        restart.setVisible(false);
        board.setVisible(false);
        r1.setVisible(false);
        r2.setVisible(false);
        r3.setVisible(false);
        r4.setVisible(false);
        r5.setVisible(false);
        r6.setVisible(false);
        r7.setVisible(false);
        onec = twoc = threec = fourc = fivec = sixc = sevenc = count = 0;
        for (int i = 0; i < player.length; i++) {
            player[i].setVisible(false);
            player[i].setTranslateY(0);
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = 0;
            }
        }
        blue.setVisible(false);
        yellow.setVisible(false);
        no.setVisible(false);
    }

    // pause/unpause game(cannot drop discs when paused)
    public static void pause(Rectangle r1, Rectangle r2, Rectangle r3, Rectangle r4, Rectangle r5, Rectangle r6,
            Rectangle r7) {
        // if trying to pause, disable board
        if (ifpause) {
            r1.setDisable(true);
            r2.setDisable(true);
            r3.setDisable(true);
            r4.setDisable(true);
            r5.setDisable(true);
            r6.setDisable(true);
            r7.setDisable(true);
        } else {
            // unpause, enable board
            r1.setDisable(false);
            r2.setDisable(false);
            r3.setDisable(false);
            r4.setDisable(false);
            r5.setDisable(false);
            r6.setDisable(false);
            r7.setDisable(false);
        }
    }

    // method to check if there is a horizontal blue 4 in a row(blue horizontal win)
    public static int CheckBlueHorizontal() {
        for (int j = 0; j < 6; j++) {
            int counter = 0;
            for (int i = 0; i < 7; i++) {
                if (grid[j][i] == 1) {
                    counter++;
                    if (counter == 4) {
                        // four blue pieces connected horizontally
                        return 1;
                    }
                } else {
                    // reset the counter for yellow pieces
                    counter = 0;
                }
            }
        }
        // no horizontal four in a row
        return -1;
    }

    // method to check if there is a horizontal yellow win
    public static int CheckYellowHorizontal() {
        for (int j = 0; j < 6; j++) {
            int counter = 0;
            for (int i = 0; i < 7; i++) {
                if (grid[j][i] == -1) {
                    counter--;
                    // four yellow pieces connected horizontally
                    if (counter == -4) {
                        return 1;
                    }
                    // reset the counter for blue pieces
                } else {
                    counter = 0;
                }
            }
        }
        // no four pieces connected horizontally
        return -1;
    }

    // method to check for vertical blue wins
    public static int CheckBlueVertical() {
        for (int i = 0; i < 7; i++) {
            int counter = 0;
            for (int j = 0; j < 6; j++) {
                if (grid[j][i] == 1) {
                    counter++;
                    // four blue pieces connected vertically
                    if (counter == 4) {
                        return 1;
                    }
                    // reset the counter for yellow pieces
                } else {
                    counter = 0;
                }
            }
        }
        // no four pieces connected vertically
        return -1;
    }

    // method to check for vertical yellow wins
    public static int CheckYellowVertical() {
        for (int i = 0; i < 7; i++) {
            int counter = 0;
            for (int j = 0; j < 6; j++) {
                if (grid[j][i] == -1) {
                    counter--;
                    // four yellow pieces connected vertically
                    if (counter == -4) {
                        return 1;
                    }
                    // reset the counter for blue pieces
                } else {
                    counter = 0;
                }
            }
        }
        // no four pieces connected vertically
        return -1;
    }

    // i = columns, j = rows
    public static int CheckYellowDiagonal() {
        // check positive diagonal
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                // four yellow pieces connected diagonally (positive slope)
                if (grid[i][j] == -1 && grid[i + 1][j + 1] == -1 && grid[i + 2][j + 2] == -1
                        && grid[i + 3][j + 3] == -1) {
                    return 1;
                }
            }
        }

        // Check negative diagonal
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                // four yellow pieces connected diagonally (negative slope)
                if (grid[i][j + 3] == -1 && grid[i + 1][j + 2] == -1 && grid[i + 2][j + 1] == -1
                        && grid[i + 3][j] == -1) {
                    return 1;
                }
            }
        }
        // no four yellow pieces connected diagonally
        return -1;
    }

    //method to check for blue diagonal win
    public static int CheckBlueDiagonal() {
        // check positive diagonal
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] == 1 && grid[i + 1][j + 1] == 1 && grid[i + 2][j + 2] == 1
                        && grid[i + 3][j + 3] == 1) {
                    // four blue pieces connected diagonally (positive slope)
                    return 1;
                }
            }
        }

        // check negative diagonal
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j + 3] == 1 && grid[i + 1][j + 2] == 1 && grid[i + 2][j + 1] == 1
                        && grid[i + 3][j] == 1) {
                    // four blue pieces connected diagonally (negative slope)
                    return 1;
                }
            }
        }
        // no four blue pieces connected diagonally
        return -1;
    }

    // check method to check for wins
    public static void check(Text blue, Text yellow, Text nothing) {
        boolean blueWins = false;
        boolean yellowWins = false;

        if (CheckBlueHorizontal() == 1 || CheckBlueVertical() == 1 || CheckBlueDiagonal() == 1) {
            blueWins = true;
        } else if (CheckYellowHorizontal() == 1 || CheckYellowVertical() == 1 || CheckYellowDiagonal() == 1) {
            yellowWins = true;
        }

        if (blueWins) {
            // blue wins
            onec = twoc = threec = fourc = fivec = sixc = sevenc = 280;
            blue.setVisible(true);
            expandText(blue);
        } else if (yellowWins) {
            // yellow wins
            onec = twoc = threec = fourc = fivec = sixc = sevenc = 280;
            yellow.setVisible(true);
            expandText(yellow);
        } else if (count == 42) {
            // tie
            nothing.setVisible(true);
            expandText(nothing);
        }
    }

    // create animation for expanding text
    public static void expandText(Text text) {
        ScaleTransition expandAnimation = new ScaleTransition(Duration.seconds(0.5));
        expandAnimation.setFromX(0.5);
        expandAnimation.setFromY(0.5);
        expandAnimation.setToX(1.0);
        expandAnimation.setToY(1.0);
        expandAnimation.setNode(text);
        expandAnimation.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
