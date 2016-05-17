package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ToggleButton;
import javafx.util.StringConverter;

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
        
        NumberAxis xAxis = (NumberAxis) chartCombinado.getXAxis();
        
        title = "Velocidad FC Cadencia ";
        if (controller.toggleBase.isSelected()){
            title += "x Tiempo";
            xAxis.setTickLabelFormatter(new StringConverter<Number>() {
                @Override
                public String toString(Number object) {
                    return object.intValue()/3600 + ":" 
                            + (object.intValue()%3600)/60 +":"
                            + (object.intValue()%3600)%60;
                }

                @Override
                public Number fromString(String string) {
                    return 0;
                }
            });
        } else {
            title += "x Distancia";
            xAxis.setTickLabelFormatter(new StringConverter<Number>() {
                @Override
                public String toString(Number object) {
                    return "" + object.intValue()/1000;
                }

                @Override
                public Number fromString(String string) {
                    return 0;
                }
            });
        }
        
        chartCombinado.setTitle(title);
        chartCombinado.getData().addAll(seriesVelocidad, seriesCadencia, seriesFC);

    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    
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
        if (toggleVelocidad.isSelected() && toggleFC.isSelected() && toggleCadencia.isSelected())
            chartCombinado.setTitle("");
        else
            chartCombinado.setTitle(title);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(CombineViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        if (toggleVelocidad.isSelected() && toggleFC.isSelected() && toggleCadencia.isSelected())
            chartCombinado.setTitle("");
        else
            chartCombinado.setTitle(title);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(CombineViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        if (toggleVelocidad.isSelected() && toggleFC.isSelected() && toggleCadencia.isSelected())
            chartCombinado.setTitle("");
        else
            chartCombinado.setTitle(title);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(CombineViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
