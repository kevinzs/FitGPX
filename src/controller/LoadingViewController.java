
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

public class LoadingViewController implements Initializable {
    
    @FXML private ProgressIndicator progressIndicator;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progressIndicator.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue) {
                if(newvalue.doubleValue() > 0.99) closeWindow();
            }
        });
    }

    public void setCharts(Task task) {
        progressIndicator.progressProperty().bind(task.progressProperty());
    }
    
    public void closeWindow(){
        Stage stage = (Stage) progressIndicator.getScene().getWindow();
        stage.close();
    }
}
