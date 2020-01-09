import javafx.application.Application;
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

    public void start(Stage ps) throws FileNotFoundException {
        BorderPane mainPane = new BorderPane();
        HBox bottomPane = new HBox();
        Text questionBox = new Text();
        mainPane.setBottom(bottomPane);
        mainPane.setCenter(questionBox);

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
        scorep3.yProperty().bind(mainPane.heightProperty().multiply(.795 ));
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
        ArrayList<String> keyList = new ArrayList<>(qDict.keySet());

        //I thought some classical music would be nice, we can change it later tho
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(100);
        mediaPlayer.play();


        mainPane.setOnKeyPressed(e ->{
            String str = "";

            if(e.getCode().toString().equals("Q")) {
                str = "Player 1 Answer:";
            }
            else if (e.getCode().toString().equals("P")){
                str = "Player 2 Answer:";
            }
            else if (e.getCode().toString().equals("DIGIT5")){
                str = "Player 3 Answer:";
            }
            if(!str.equals("")) {
                Stage stage = new Stage();
                stage.setTitle(str);
                Pane p = new Pane();
                Scene ss = new Scene(p, 900, 600);
                stage.setScene(ss);
                stage.show();
            }
        });

        Scene scene = new Scene(mainPane, 955,598);
        ps.setTitle("Quiz Bowl");
        mainPane.requestFocus();
        ps.setScene(scene);
        ps.show();
    }

}
