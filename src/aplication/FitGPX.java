
package aplication;

import controller.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FitGPX extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
        BorderPane root = (BorderPane) miCargador.load();
            
        ((MainViewController) miCargador.getController()).setStage(stage);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("FitGPX");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
