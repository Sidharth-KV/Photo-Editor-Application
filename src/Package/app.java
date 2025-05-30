package Package;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class app extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primarystage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            Scene scene = new Scene(root);
            primarystage.setScene(scene);
            primarystage.setMaximized(true);
            primarystage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
