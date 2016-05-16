
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ComboBox;

public class ActivityDiaryViewController implements Initializable {

    @FXML private ComboBox<String> choiceBox;
    @FXML private BarChart<Number, Number> barChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList list = FXCollections.observableArrayList(
            "Ultima semana","Ultimo mes", "Ultimos 3 meses"
        );
        choiceBox.setValue("Ultima semana");
        choiceBox.setItems(list);
    }    
    
}
