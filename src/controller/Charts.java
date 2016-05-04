package controller;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import jgpx.model.analysis.Chunk;
import jgpx.model.analysis.TrackData;


public class Charts{
    
    private final MainViewController controller;
    private TrackData track;
    ObservableList<Chunk> chunks;
    
    public Charts (MainViewController controller) {
        this.controller = controller;
    }
    
    public void setTrackData(TrackData track){ 
        this.track = track;
        chunks = track.getChunks();
    }
    
    public void setLabels(){
        if (controller.toggleBase.isSelected()) {
            controller.labelGraficaAltura.setText("Altura x Tiempo");
            controller.labelGraficaVelocidad.setText("Velocidad x Tiempo");
            controller.labelGraficaFC.setText("FC x Tiempo");
            controller.labelGraficaCadencia.setText("Cadencia x Tiempo");
        } else {
            controller.labelGraficaAltura.setText("Altura x Distancia");
            controller.labelGraficaVelocidad.setText("Velocidad x Distancia");
            controller.labelGraficaFC.setText("FC x Distancia");
            controller.labelGraficaCadencia.setText("Cadencia x Distancia");
        }
    }
    
    public void refreshCharts(){
        setAreaChart();
    }
    
    public void setAreaChart(){
        controller.chartAltura.setCreateSymbols(false);
        XYChart.Series<Number,Number> series = new XYChart.Series();
        int distance = 0, duration = 0;
        for(Chunk chunk : chunks){
            if(controller.toggleBase.isSelected()){ // Altura x Tiempo
                duration += chunk.getDuration().getSeconds();
                series.getData().add(new XYChart.Data<>(duration, chunk.getAvgHeight()));
            } else {    // Altura x Distancia
                distance += chunk.getDistance();
                series.getData().add(new XYChart.Data<>(distance, chunk.getAvgHeight()));
            }
        }
        controller.chartAltura.getData().clear();
        controller.chartAltura.getData().addAll(series);
    }
}
