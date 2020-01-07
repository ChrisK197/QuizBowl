import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class MainFileAddingQuestionsTest extends Application {

    private String musicFile = "Waiting.mp3";
    private Media sound = new Media(new File(musicFile).toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(sound);

    public void start(Stage ps) throws FileNotFoundException {
        BorderPane mainPane = new BorderPane();
        HBox bottomPane = new HBox();
        mainPane.setBottom(bottomPane);

        BackgroundImage myBI= new BackgroundImage(new Image("background.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        mainPane.setBackground(new Background(myBI));


        /*Image contestant1 = new Image("contestant1.jpg");
        ImageView contestant1imageView = new ImageView(contestant1);
        contestant1imageView.setFitWidth(contestant1.getWidth()/2);
        contestant1imageView.setFitHeight(contestant1.getHeight()/2);
        bottomPane.getChildren().add(contestant1imageView);*/

        Text scorep1 = new Text("0");
        scorep1.setScaleX(3);
        scorep1.setScaleY(3);
        scorep1.xProperty().bind(mainPane.widthProperty().multiply(0.045));
        scorep1.yProperty().bind(mainPane.heightProperty().multiply(.8));
        mainPane.getChildren().add(scorep1);
    /*
        Image contestant2 = new Image("contestant2.jpg");
        ImageView contestant2imageView = new ImageView(contestant2);
        contestant2imageView.setFitWidth(contestant2.getWidth()/2);
        contestant2imageView.setFitHeight(contestant2.getHeight()/2);
        bottomPane.getChildren().add(contestant2imageView);*/

        Rectangle qBox = new Rectangle(150, 75, 900, 100);
        qBox.setFill(Color.WHITE);
        mainPane.getChildren().add(qBox);
        qBox.widthProperty().bind(mainPane.widthProperty().multiply(3).divide(4));
        qBox.heightProperty().bind(mainPane.heightProperty().divide(6));
        qBox.xProperty().bind(mainPane.widthProperty().divide(8));
        qBox.yProperty().bind(mainPane.heightProperty().divide(16));

        Text questionBox = new Text();
        mainPane.getChildren().add(questionBox);
        questionBox.xProperty().bind(qBox.xProperty().multiply(1.06));
        questionBox.yProperty().bind(qBox.yProperty().multiply(1.4));
        questionBox.wrappingWidthProperty().bind(qBox.widthProperty().multiply(.95));

        Rectangle ansBox = new Rectangle(425, 250, 300, 200);
        ansBox.setFill(Color.WHITE);
        mainPane.getChildren().add(ansBox);
        ansBox.widthProperty().bind(qBox.widthProperty().divide(2));
        ansBox.heightProperty().bind(qBox.heightProperty().divide(2));
        ansBox.xProperty().bind(qBox.xProperty().multiply(2));
        ansBox.yProperty().bind(qBox.yProperty().multiply(8));


        TextField answerBox = new TextField();
        mainPane.getChildren().add(answerBox);



        Scanner s = new Scanner(new FileReader("questions.txt"));
        HashMap<String, Integer> pointDict = new HashMap<>();
        HashMap<String, String> qDict = new HashMap<>();
        while (s.hasNext()){
            String str = s.nextLine();
            String[] list = str.split("::");
            qDict.put(list[0], list[1]);
            pointDict.put(list[0], Integer.parseInt(list[2]));
        }
        ArrayList<String> keyList = new ArrayList<>(qDict.keySet());
        ArrayList<String> keyListRemove = new ArrayList<>(qDict.keySet());
        while(!(keyListRemove.isEmpty())){
            int num = (int)(Math.random()*keyListRemove.size());
            String question = keyListRemove.get(num);
            questionBox.setText(question);
            break;

        }

        //I thought some classical music would be nice, we can change it later tho
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(100);
        mediaPlayer.play();

        Scene scene = new Scene(mainPane, 955,598);
        ps.setTitle("Quiz Bowl");
        ps.setScene(scene);
        ps.show();
    }
}
