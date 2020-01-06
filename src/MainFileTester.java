import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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


public class MainFileTester extends Application {

    private String musicFile = "Waiting.mp3";
    private Media sound = new Media(new File(musicFile).toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(sound);

    public void start(Stage ps) throws FileNotFoundException {
        Pane mainPane = new Pane();
        Text questionBox = new Text();
        mainPane.getChildren().add(questionBox);

        /*Image background = new Image("background.png");
        ImageView imageView = new ImageView(background);
        imageView.fitWidthProperty().bind(mainPane.widthProperty());
        imageView.fitHeightProperty().bind(mainPane.heightProperty());
        mainPane.getChildren().add(imageView);*/

        Image contestant1 = new Image("contestant1.jpg");
        ImageView contestant1imageView = new ImageView(contestant1);
        contestant1imageView.setFitWidth(contestant1.getWidth()/2);
        contestant1imageView.setFitHeight(contestant1.getHeight()/2);
        mainPane.getChildren().add(contestant1imageView);

        Image contestant2 = new Image("contestant2.jpg");
        ImageView contestant2imageView = new ImageView(contestant2);
        contestant2imageView.setFitWidth(contestant2.getWidth()/2);
        contestant2imageView.setFitHeight(contestant2.getHeight()/2);
        mainPane.getChildren().add(contestant2imageView);

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

        Scene scene = new Scene(mainPane, 1200,600);
        ps.setTitle("Quiz Bowl");
        ps.setScene(scene);
        ps.show();
    }
}
