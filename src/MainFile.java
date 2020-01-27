import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class MainFile extends Application {

    private String musicFile = "Waiting.mp3";
    private Media sound = new Media(new File(musicFile).toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(sound);
    private String answer ="";
    private boolean buzzed1 = false;
    private boolean buzzed2 = false;
    private boolean buzzed3 = false;
    private int points1 = 0;
    private int points2 = 0;
    private int points3 = 0;
    private int playerWhoAnswered;
    private String question;
    private int num;
    private boolean finished =false;
    private boolean answered=false;
    private boolean doneRunning = false;

    private Text questionBox;

    public void typeText (String question) {
        final String content = question;

        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(20000));
            }

            protected void interpolate(double frac) {
                if(answered==false) {
                    final int length = content.length();
                    final int n = Math.round(length * (float) frac);
                    questionBox.setText(content.substring(0, n));
                }
            }

        };
        if(answered==true){
            animation.pause();
        }
        else{
            animation.play();
        }
        animation.setOnFinished(e -> doneRunning = true);
    }

    public void start(Stage ps) throws FileNotFoundException {
        BorderPane mainPane = new BorderPane();
        HBox bottomPane = new HBox();
        mainPane.setBottom(bottomPane);

        /*Image image = new Image("background.png");
        BackgroundSize bs = new BackgroundSize(image.getWidth(), image.getHeight(), true, true, false, true);
        BackgroundImage myBI= new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                bs);
        mainPane.setBackground(new Background(myBI));
        */
        BackgroundImage myBI= new BackgroundImage(new Image("background.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        mainPane.setBackground(new Background(myBI));

        Text scorep1 = new Text(String.format("%d",points1));
        scorep1.setScaleX(3);
        scorep1.setScaleY(3);
        scorep1.xProperty().bind(mainPane.widthProperty().multiply(0.055));
        scorep1.yProperty().bind(mainPane.heightProperty().multiply(.8));
        mainPane.getChildren().add(scorep1);

        Text scorep2 = new Text(String.format("%d",points2));
        scorep2.setScaleX(3);
        scorep2.setScaleY(3);
        scorep2.xProperty().bind(mainPane.widthProperty().multiply(0.5));
        scorep2.yProperty().bind(mainPane.heightProperty().multiply(.81));
        mainPane.getChildren().add(scorep2);

        Text scorep3 = new Text(String.format("%d",points3));
        scorep3.setScaleX(3);
        scorep3.setScaleY(3);
        scorep3.xProperty().bind(mainPane.widthProperty().multiply(1-0.055));
        scorep3.yProperty().bind(mainPane.heightProperty().multiply(.795));
        mainPane.getChildren().add(scorep3);


        Rectangle qBox = new Rectangle(150, 75, 900, 100);
        qBox.setFill(Color.WHITE);
        mainPane.getChildren().add(qBox);
        qBox.widthProperty().bind(mainPane.widthProperty().multiply(3).divide(4));
        qBox.heightProperty().bind(mainPane.heightProperty().divide(6));
        qBox.xProperty().bind(mainPane.widthProperty().divide(8));
        qBox.yProperty().bind(mainPane.heightProperty().divide(16));

        Pane centerPane = new Pane();
        Button moveOn = new Button ("Click this to go to the next question.");
        mainPane.setCenter(centerPane);
        centerPane.getChildren().add(moveOn);
        moveOn.setLayoutX(380);
        moveOn.setLayoutY(249);
        //may need to request focus

        Scanner s = new Scanner(new FileReader("questions.txt"));
        HashMap<String, String> qDict = new HashMap<>();
        while (s.hasNext()){
            String str = s.nextLine();
            String[] list = str.split("::");
            qDict.put(list[0], list[1]);
        }

        ArrayList<String> keyList = new ArrayList<>(qDict.keySet());
        ArrayList<String> keyListRemove = new ArrayList<>(qDict.keySet());

        num = (int)(Math.random()*keyListRemove.size());
        question = keyListRemove.remove(num);
        typeText(question);
        moveOn.setOnAction(e ->{
            answered=false;
            if(keyListRemove.size()>0) {
                num = (int) (Math.random() * keyListRemove.size());
                question = keyListRemove.remove(num);
                typeText(question);
                buzzed1 = false;
                buzzed2 = false;
                buzzed3 = false;
            }
            if(keyListRemove.size()<=0 && finished==false){
                typeText(getWinner() + "                                                       ");
                //questionBox.setText("ou answered all the questions.");
                buzzed1 = true;
                buzzed2 = true;
                buzzed3 = true;
                finished=true;
            }
        });



        //I thought some classical music would be nice, we can change it later tho
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(100);
        mediaPlayer.play();

        questionBox = new Text();
        mainPane.getChildren().add(questionBox);
        questionBox.xProperty().bind(qBox.xProperty().multiply(1.06));
        questionBox.yProperty().bind(qBox.yProperty().multiply(1.4));
        questionBox.wrappingWidthProperty().bind(qBox.widthProperty().multiply(.95));


        //while(!(keyListRemove.isEmpty())){
          //  num = (int)(Math.random()*keyListRemove.size());
            //question = keyListRemove.remove(num);
            //questionBox.setText(question);
        //}

        mainPane.setOnKeyPressed(e ->{
            String str = "";

            if(e.getCode().toString().equals("Z")) {
                if(!buzzed1) {
                    str = "Player 1 Answer:";
                    buzzed1 = true;
                    answered=true;
                }
            }
            else if (e.getCode().toString().equals("B")){
                if(!buzzed2) {
                    str = "Player 2 Answer:";
                    answered=true;
                    buzzed2 = true;
                }
            }
            else if (e.getCode().toString().equals("SLASH")){
                if(!buzzed3) {
                    str = "Player 3 Answer:";
                    buzzed3 = true;
                    answered=true;
                }
            }
            if(!str.equals("")) {
                Stage stage = new Stage();
                stage.setTitle(str);
                Pane p = new Pane();
                TextField answerBox = new TextField();
                answerBox.setLayoutX(0);
                answerBox.setLayoutY(0);
                p.getChildren().add(answerBox);

                p.requestFocus();
                Scene ss = new Scene(p, 300, 50);
                stage.setScene(ss);
                stage.show();
                p.setOnKeyPressed(j ->{
                    if (j.getCode().toString().equals("ENTER")) {
                        answer = answerBox.getText();
                        answered=false;
                        if (e.getCode().toString().equals("Z")) {
                            if (qDict.get(question).toLowerCase().equals(answer.toLowerCase())) {
                                System.out.println("Right " + 1);
                                points1 += 10; scorep1.setText(String.format("%d",points1));
                                if(keyListRemove.size()>0) {
                                    num = (int) (Math.random() * keyListRemove.size());
                                    question = keyListRemove.remove(num);
                                    typeText(question);
                                    buzzed1 = false;
                                    buzzed2 = false;
                                    buzzed3 = false;
                                }
                                if(keyListRemove.size()<=0 && finished==false){
                                    typeText(getWinner() + "                                                       ");
                                    //questionBox.setText("ou answered all the questions.");
                                    buzzed1 = true;
                                    buzzed2 = true;
                                    buzzed3 = true;
                                    finished=true;
                                }
                            } else {
                                System.out.println("Wrong " + 1);
                                points1 -= 10; scorep1.setText(String.format("%d",points1));
                                if (doneRunning && !questionBox.getText().equals(question)){
                                    questionBox.setText(question);
                                }
                                if(buzzed1==true && buzzed2==true && buzzed3 ==true){
                                    if(keyListRemove.size()>0) {
                                        num = (int) (Math.random() * keyListRemove.size());
                                        question = keyListRemove.remove(num);
                                        typeText(question);
                                        buzzed1 = false;
                                        buzzed2 = false;
                                        buzzed3 = false;
                                    }
                                    if(keyListRemove.size()<=0 && finished==false){
                                        typeText(getWinner() + "                                                       ");
                                        //questionBox.setText("ou answered all the questions.");
                                        buzzed1 = true;
                                        buzzed2 = true;
                                        buzzed3 = true;
                                        finished=true;
                                    }
                                }
                            }
                            playerWhoAnswered = 1;
                        }
                        else if(e.getCode().toString().equals("B")) {
                            if(qDict.get(question).toLowerCase().equals(answer.toLowerCase())){
                                System.out.println("Right " +2);
                                points2 += 10; scorep2.setText(String.format("%d",points2));
                                if(keyListRemove.size()>0) {
                                    num = (int) (Math.random() * keyListRemove.size());
                                    question = keyListRemove.remove(num);
                                    typeText(question);
                                    buzzed1 = false;
                                    buzzed2 = false;
                                    buzzed3 = false;
                                }
                                if(keyListRemove.size()<=0 && finished==false){
                                    typeText(getWinner() + "                                                       ");
                                    //questionBox.setText("ou answered all the questions.");
                                    buzzed1 = true;
                                    buzzed2 = true;
                                    buzzed3 = true;
                                    finished=true;
                                }
                            }
                            else{
                                System.out.println("Wrong " + 2);
                                points2 -= 10; scorep2.setText(String.format("%d",points2));
                                if (doneRunning && !questionBox.getText().equals(question)){
                                    questionBox.setText(question);
                                }
                                if(buzzed1==true && buzzed2==true && buzzed3 ==true){
                                    if(keyListRemove.size()>0) {
                                        num = (int) (Math.random() * keyListRemove.size());
                                        question = keyListRemove.remove(num);
                                        typeText(question);
                                        buzzed1 = false;
                                        buzzed2 = false;
                                        buzzed3 = false;
                                    }
                                    if(keyListRemove.size()<=0 && finished==false){
                                        typeText(getWinner() + "                                                       ");
                                        //questionBox.setText("ou answered all the questions.");
                                        buzzed1 = true;
                                        buzzed2 = true;
                                        buzzed3 = true;
                                        finished=true;
                                    }
                                }
                            }
                            playerWhoAnswered = 2;
                        }
                        else if(e.getCode().toString().equals("SLASH")){
                            if(qDict.get(question).toLowerCase().equals(answer.toLowerCase())){
                                System.out.println("Right " +3);
                                points3 += 10; scorep3.setText(String.format("%d",points3));
                                if(keyListRemove.size()>0) {
                                    num = (int) (Math.random() * keyListRemove.size());
                                    question = keyListRemove.remove(num);
                                    typeText(question);
                                    buzzed1 = false;
                                    buzzed2 = false;
                                    buzzed3 = false;
                                }
                                if(keyListRemove.size()<=0 && finished==false){
                                    typeText(getWinner() + "                                                       ");
                                    //questionBox.setText("ou answered all the questions.");
                                    buzzed1 = true;
                                    buzzed2 = true;
                                    buzzed3 = true;
                                    finished=true;
                                }
                            }
                            else{
                                System.out.println("Wrong " + 3);
                                points3 -= 10; scorep3.setText(String.format("%d",points3));
                                if (doneRunning && !questionBox.getText().equals(question)){
                                    questionBox.setText(question);
                                }
                                if(buzzed1==true && buzzed2==true && buzzed3 ==true){
                                    if(keyListRemove.size()>0) {
                                        num = (int) (Math.random() * keyListRemove.size());
                                        question = keyListRemove.remove(num);
                                        typeText(question);
                                        buzzed1 = false;
                                        buzzed2 = false;
                                        buzzed3 = false;
                                    }
                                    if(keyListRemove.size()<=0 && finished==false){
                                        typeText(getWinner() + "                                                       ");
                                        //questionBox.setText("ou answered all the questions.");
                                        buzzed1 = true;
                                        buzzed2 = true;
                                        buzzed3 = true;
                                        finished=true;
                                    }
                                }
                            }
                            playerWhoAnswered=3;
                        }
                        stage.hide();
                    }
                });
            }
            mainPane.requestFocus();
        });

        Scene scene = new Scene(mainPane, 955,598);
        ps.setTitle("Quiz Bowl");
        mainPane.requestFocus();
        ps.setScene(scene);
        //ps.setResizable(false);
        ps.show();

    }

    public String getWinner(){
        if (points1 > points2 && points1 > points3){
            return "Player 1 Wins!";
        }
        else if (points2 > points1 && points2 > points3){
            return "Player 2 Wins!";
        }
        else if (points3 > points1 && points3 > points2){
            return "Player 3 Wins!";
        }
        else if (points1 == points2 && points1 > points3){
            return "Player 1 and Player 2 Win!";
        }
        else if (points3 == points2 && points3 > points1){
            return "Player 1 and Player 2 Win!";
        }
        else if (points1 == points3 && points1 > points2){
            return "Player 1 and Player 2 Win!";
        }
        else{
            return "Tie";
        }
    }

}

/*
For evan's file

Duration.millis(10);

or

Task<Void> sleeper = new Task<Void>() {
    @Override
    protected Void call() throws Exception {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        return null;
    }
};
sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
    @Override
    public void handle(WorkerStateEvent event) {
    }
});
new Thread(sleeper).start();
*/