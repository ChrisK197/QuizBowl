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
    private int playerWhoAnswered;

    private Text questionBox;

    public void typeText (String question) {
        final String content = question;

        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(20000));
            }

            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                questionBox.setText(content.substring(0, n));
            }

        };

        animation.play();
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

        Text scorep1 = new Text("0");
        scorep1.setScaleX(3);
        scorep1.setScaleY(3);
        scorep1.xProperty().bind(mainPane.widthProperty().multiply(0.055));
        scorep1.yProperty().bind(mainPane.heightProperty().multiply(.8));
        mainPane.getChildren().add(scorep1);

        Text scorep2 = new Text("0");
        scorep2.setScaleX(3);
        scorep2.setScaleY(3);
        scorep2.xProperty().bind(mainPane.widthProperty().multiply(0.5));
        scorep2.yProperty().bind(mainPane.heightProperty().multiply(.81));
        mainPane.getChildren().add(scorep2);

        Text scorep3 = new Text("0");
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

        Scanner s = new Scanner(new FileReader("questions.txt"));
        HashMap<String, String> qDict = new HashMap<>();
        while (s.hasNext()){
            String str = s.nextLine();
            String[] list = str.split("::");
            qDict.put(list[0], list[1]);
        }


        //I thought some classical music would be nice, we can change it later tho
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(100);
        mediaPlayer.play();

        questionBox = new Text();
        mainPane.getChildren().add(questionBox);
        questionBox.xProperty().bind(qBox.xProperty().multiply(1.06));
        questionBox.yProperty().bind(qBox.yProperty().multiply(1.4));
        questionBox.wrappingWidthProperty().bind(qBox.widthProperty().multiply(.95));

        ArrayList<String> keyList = new ArrayList<>(qDict.keySet());
        ArrayList<String> keyListRemove = new ArrayList<>(qDict.keySet());

        int num = (int)(Math.random()*keyListRemove.size());
        String question = keyListRemove.remove(num);
        typeText(question);
        //while(!(keyListRemove.isEmpty())){
          //  num = (int)(Math.random()*keyListRemove.size());
            //question = keyListRemove.remove(num);
            //questionBox.setText(question);
        //}

        mainPane.setOnKeyPressed(e ->{
            String str = "";

            if(e.getCode().toString().equals("Z")) {
                str = "Player 1 Answer:";
            }
            else if (e.getCode().toString().equals("B")){
                str = "Player 2 Answer:";
            }
            else if (e.getCode().toString().equals("SLASH")){
                str = "Player 3 Answer:";
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
                        if (e.getCode().toString().equals("Z")) {
                            if (qDict.get(question).toLowerCase().equals(answer.toLowerCase())) {
                                System.out.println("Right " + 1);
                            } else {
                                System.out.println("Wrong " + 1);
                            }
                            playerWhoAnswered = 1;
                        }
                        else if(e.getCode().toString().equals("B")) {
                            if(qDict.get(question).toLowerCase().equals(answer.toLowerCase())){
                                System.out.println("Right " +2);
                            }
                            else{
                                System.out.println("Wrong " + 2);
                            }
                            playerWhoAnswered = 2;
                        }
                        else if(e.getCode().toString().equals("SLASH")){
                            if(qDict.get(question).toLowerCase().equals(answer.toLowerCase())){
                                System.out.println("Right " +3);
                            }
                            else{
                                System.out.println("Wrong " + 3);
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