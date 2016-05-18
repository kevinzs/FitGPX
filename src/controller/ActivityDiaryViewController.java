
package controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import utilities.TracksList;

public class ActivityDiaryViewController implements Initializable {

    @FXML private ComboBox<String> choiceBox;
    @FXML private BarChart<String, Number> barChart;
    @FXML private BarChart<String, Number> barChart2;

    private XYChart.Series<String,Number> seriesTiempo;
    private XYChart.Series<String,Number> seriesDistancia;
    
    private TracksList tracksList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList list = FXCollections.observableArrayList(
            "Ultima semana","Ultimo mes", "Ultimos 3 meses"
        );
        choiceBox.setValue("Ultima semana");
        choiceBox.setItems(list);
        choiceBox.valueProperty().addListener(new ChangeListener(){
           @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal){
                 int position = choiceBox.getSelectionModel().getSelectedIndex();
                 refreshChart(position);
            }
        });
    }
    
    // Time es para saber si hay que mirar de la ultima semana, mes...
    public void refreshChart(int time){
        LocalDateTime date = LocalDateTime.now();
        switch(time){
            case 0:
                date = LocalDateTime.now().minusDays(7);
                break;
            case 1:
                date = LocalDateTime.now().minusDays(30);
                break;
            case 2:
                date = LocalDateTime.now().minusDays(90);
                break;
            default:
                break;
        }
        seriesTiempo = new XYChart.Series();
        seriesDistancia = new XYChart.Series();
        seriesTiempo.setName("Tiempo");
        seriesDistancia.setName("Distancia");
        for(int i=0; i<tracksList.getTracks().size(); i++){
            if(tracksList.getTracks().get(i).getStartTime().compareTo(date) >= 0){
                seriesTiempo.getData().add(new XYChart.Data<>(""+tracksList.getTracks().get(i).getStartTime().getDayOfYear(), 
                        tracksList.getTrackData(i).getTotalDuration().toMinutes()));
                seriesDistancia.getData().add(new XYChart.Data<>(""+tracksList.getTracks().get(i).getStartTime().getDayOfYear(), 
                        tracksList.getTrackData(i).getTotalDistance()));
            }
        }
        barChart.getData().clear();
        barChart.getData().addAll(seriesTiempo);
        barChart2.getData().clear();
        barChart2.getData().addAll(seriesDistancia);
    }
    
    public void setTracksList(TracksList tracksList){
        this.tracksList = tracksList;
    }
}
