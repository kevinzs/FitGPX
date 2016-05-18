
package controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
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
            "Ultimo mes", "Ultimos 3 meses", "Ultimos 6 meses"
        );
        choiceBox.setValue("Ultima mes");
        choiceBox.setItems(list);
        choiceBox.valueProperty().addListener(new ChangeListener(){
           @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal){
                 int position = choiceBox.getSelectionModel().getSelectedIndex();
                 refreshChart(position);
            }
        });
        
        barChart.getXAxis().setTickLabelRotation(-40);
        barChart2.getXAxis().setTickLabelRotation(-40);
    }
    
    // Time es para saber si hay que mirar de la ultima semana, mes...
    public void refreshChart(int time){
        seriesTiempo = new XYChart.Series();
        seriesDistancia = new XYChart.Series();
        seriesTiempo.setName("Tiempo");
        seriesDistancia.setName("Distancia");
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime todai = LocalDateTime.now();
        int today = date.getDayOfYear();
        switch(time){
            case 0:
                date = LocalDateTime.now().minusMonths(1);
                break;
            case 1:
                date = LocalDateTime.now().minusMonths(3);
                break;
            case 2:
                date = LocalDateTime.now().minusMonths(6);
                break;
            default:
                break;
        }
        for(int i=0; i<tracksList.getTracks().size(); i++){
            if(tracksList.getTracks().get(i).getStartTime().compareTo(date) >= 0){
                int days = today - tracksList.getTracks().get(i).getStartTime().getDayOfYear();
                if (days < 0)
                    days = 365 + days;
                seriesTiempo.getData().add(new XYChart.Data<>(todai.minusDays(days).toString().substring(0, 10), 
                        tracksList.getTrackData(i).getTotalDuration().toMinutes()));
                seriesDistancia.getData().add(new XYChart.Data<>(todai.minusDays(days).toString().substring(0, 10), 
                        tracksList.getTrackData(i).getTotalDistance()));
            }
        } 
        sort(seriesTiempo);
        sort(seriesDistancia);
        barChart.getData().clear();
        barChart.getData().addAll(seriesTiempo);
        barChart2.getData().clear();
        barChart2.getData().addAll(seriesDistancia);
    }
    
    public void setTracksList(TracksList tracksList){
        this.tracksList = tracksList;
        refreshChart(0);
    }
    
    public void sort(XYChart.Series<String,Number> serie){
        Collections.sort(serie.getData(), new Comparator<XYChart.Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                String xValue1 = (String) o1.getXValue();
                int xValue1Year = Integer.valueOf(xValue1.substring(0, 4));
                int xValue1Month = Integer.valueOf(xValue1.substring(5, 7));
                int xValue1Day = Integer.valueOf(xValue1.substring(8, 10));
                
                String xValue2 = (String) o2.getXValue();
                int xValue2Year = Integer.valueOf(xValue2.substring(0, 4));
                int xValue2Month = Integer.valueOf(xValue2.substring(5, 7));
                int xValue2Day = Integer.valueOf(xValue2.substring(8, 10));
                
                if (xValue1Year > xValue2Year){
                    return 1;
                } else if (xValue1Year < xValue2Year) {
                    return -1;
                } else {
                    if (xValue1Month > xValue2Month) {
                        return 1;
                    } else if (xValue1Month < xValue2Month){
                        return -1;
                    } else {
                        if (xValue1Day > xValue2Day){
                            return 1;
                        } else if (xValue1Day < xValue2Day){
                            return -1;
                        } else {
                            return 0;
                        }     
                    }
                }
            }
        });    
    }
}
