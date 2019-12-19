import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public class MainFile extends Application {
    public void start(Stage ps) throws FileNotFoundException {
        BorderPane mainPane = new BorderPane();
        HBox bottomPane = new HBox();
        Text questionBox = new Text();
        mainPane.setBottom(bottomPane);
        mainPane.setCenter(questionBox);
        Scanner s = new Scanner(new File("questions.txt"));
        HashMap<String, String> qDict = new HashMap<>();
        while (s.hasNext()){
            String str = s.nextLine();
            String[] list = str.split("::");
            qDict.put(list[0], list[1]);
        }






        Scene scene = new Scene(mainPane, 1200,600);
        ps.setTitle("Quiz Bowl!!");
        ps.setScene(scene);
        ps.show();
    }
}
