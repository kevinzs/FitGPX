package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ToggleButton;

public class CombineViewController implements Initializable {

    @FXML private LineChart<Number, Number> chartCombinado;
    @FXML private ToggleButton toggleVelocidad;
    @FXML private ToggleButton toggleFC;
    @FXML private ToggleButton toggleCadencia;
    
    private MainViewController controller;
    
    private XYChart.Series<Number,Number> seriesVelocidad;
    private XYChart.Series<Number,Number> seriesFC;
    private XYChart.Series<Number,Number> seriesCadencia;
    
    private String title;
    
    public void setController (MainViewController controller) {
        this.controller = controller;
    }

    public void setSeries(List<XYChart.Series<Number,Number>> list){
        seriesVelocidad = list.get(0);
        seriesFC = list.get(1);
        seriesCadencia = list.get(2);
        
        
        seriesVelocidad.setName("Velocidad");
        seriesFC.setName("Frecuencia Cardiaca");
        seriesCadencia.setName("Cadencia");
        
        chartCombinado.setCreateSymbols(false);
        
        title = "Velocidad FC Cadencia ";
        if (controller.toggleBase.isSelected())
            title += "x Tiempo";
        else
            title += "x Distancia";
        
        chartCombinado.getData().addAll(seriesVelocidad, seriesCadencia, seriesFC);
        chartCombinado.setTitle(title);
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
    @FXML
    private void toggleVelocidadPressed(ActionEvent event) {
        if (toggleVelocidad.isSelected()){
            chartCombinado.getData().remove(seriesVelocidad);
            title = title.replace("Velocidad ", "");
        } else {
            chartCombinado.getData().add(seriesVelocidad);
            if(!title.contains("Velocidad "))
                title = "Velocidad " + title;
        }
        chartCombinado.setTitle(title);
    }
    
    @FXML
    private void toggleFCPressed(ActionEvent event) {
        if (toggleFC.isSelected()){
            chartCombinado.getData().remove(seriesFC);
            title = title.replace("FC ", "");
        } else {
            chartCombinado.getData().add(seriesFC);
            if(!title.contains("FC "))
                title = "FC " + title;
        }
        chartCombinado.setTitle(title);;
    }
    
    @FXML
    private void toggleCadenciaPressed(ActionEvent event) {
        if (toggleCadencia.isSelected()){
            chartCombinado.getData().remove(seriesCadencia);
            title = title.replace("Cadencia ", "");
        } else {
            chartCombinado.getData().add(seriesCadencia);
            if(!title.contains("Cadencia "))
                title = "Cadencia " + title;
        }
        chartCombinado.setTitle(title);
    }
}
