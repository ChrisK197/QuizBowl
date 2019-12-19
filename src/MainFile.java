import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class MainFile extends Application {
    public void start(Stage ps){
        BorderPane mainPane = new BorderPane();
        HBox bottomPane = new HBox();
        Text questionBox = new Text();
        mainPane.setBottom(bottomPane);
        mainPane.setCenter(questionBox);




        Scene scene = new Scene(mainPane, 1200,600);
        ps.setTitle("Quiz Bowl!!");
        ps.setScene(scene);
        ps.show();
    }
}
