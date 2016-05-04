package controller;

import javafx.collections.ObservableList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
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
    
    public void setAreaChart(){
        controller.chartAltura.setCreateSymbols(false);
        XYChart.Series<Number,Number> series = new XYChart.Series();
        for(int i=0; i<chunks.size(); i++){
            series.getData().add(new XYChart.Data(chunks.get(i).getDistance(), chunks.get(i).getAvgHeight()));
        }
        controller.chartAltura.getData().addAll(series);
    }
}
