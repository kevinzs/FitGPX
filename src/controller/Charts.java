package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
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
    
    public void refreshCharts(){
        setChartAltura();
        setChartVelocidad();
        setChartFC();
        setChartCadencia();
        setChartDistribucion();
    }
    
    public void setChartAltura(){
        controller.chartAltura.setCreateSymbols(false);
        XYChart.Series<Number,Number> series = new XYChart.Series();
        int value = 0;
        if (controller.toggleBase.isSelected()){
            controller.chartAltura.setTitle("Altura x Tiempo");
            for (Chunk chunk : chunks){
                value += chunk.getDuration().getSeconds();
                series.getData().add(new XYChart.Data<>(value, chunk.getAvgHeight()));
            }
        } else {
            controller.chartAltura.setTitle("Altura x Distancia");
            for (Chunk chunk : chunks){
                value += chunk.getDistance();
                series.getData().add(new XYChart.Data<>(value, chunk.getAvgHeight()));
            }
        }
        controller.chartAltura.getData().clear();
        controller.chartAltura.getData().addAll(series);
    }
    
    public void setChartVelocidad(){
        controller.chartVelocidad.setCreateSymbols(false);
        XYChart.Series<Number,Number> series = new XYChart.Series();
        int value = 0;
        if (controller.toggleBase.isSelected()){
            controller.chartVelocidad.setTitle("Velocidad x Tiempo");
            for (Chunk chunk : chunks){
                value += chunk.getDuration().getSeconds();
                series.getData().add(new XYChart.Data<>(value, chunk.getSpeed()));
            }
        } else {
            controller.chartVelocidad.setTitle("Velocidad x Distancia");
            for (Chunk chunk : chunks){
                value += chunk.getDistance();
                series.getData().add(new XYChart.Data<>(value, chunk.getSpeed()));
            }
        }
        controller.chartVelocidad.getData().clear();
        controller.chartVelocidad.getData().addAll(series);
    }
    
    public void setChartFC(){
        controller.chartFC.setCreateSymbols(false);
        XYChart.Series<Number,Number> series = new XYChart.Series();
        int value = 0;
        if (controller.toggleBase.isSelected()){
            controller.chartFC.setTitle("FC x Tiempo");
            for (Chunk chunk : chunks){
                value += chunk.getDuration().getSeconds();
                series.getData().add(new XYChart.Data<>(value, chunk.getAvgHeartRate()));
            }
        } else {
            controller.chartFC.setTitle("FC x Distancia");
            for (Chunk chunk : chunks){
                value += chunk.getDistance();
                series.getData().add(new XYChart.Data<>(value, chunk.getAvgHeartRate()));
            }
        }
        controller.chartFC.getData().clear();
        controller.chartFC.getData().addAll(series);
    }
        
    public void setChartCadencia(){
        controller.chartCadencia.setCreateSymbols(false);
        XYChart.Series series = new XYChart.Series();
        int value = 0;
        if (controller.toggleBase.isSelected()){
            controller.chartCadencia.setTitle("Cadencia x Tiempo");
            for (Chunk chunk : chunks){
                value += chunk.getDuration().getSeconds();
                series.getData().add(new XYChart.Data<>(value, chunk.getAvgCadence()));
            }
        } else {
            controller.chartCadencia.setTitle("Cadencia x Distancia");
            for (Chunk chunk : chunks){
                value += chunk.getDistance();
                series.getData().add(new XYChart.Data<>(value, chunk.getAvgCadence()));
            }
        }
        controller.chartCadencia.getData().clear();
        controller.chartCadencia.getData().addAll(series);
    }
    
    public void setChartDistribucion(){
        int maxFC = track.getMaxHeartrate();
        int z1, z2, z3, z4 , z5;
        z1 = z2 = z3 = z4 = z5 = 0;
        for (Chunk chunk : chunks){
            double FC = chunk.getAvgHeartRate();
            double percent = (FC/maxFC);
            if (percent < 0.6)
                z1++;
            else if (percent < 0.7)
                z2++;
            else if (percent < 0.8)
                z3++;
            else if (percent <= 0.9)
                z4++;
            else
                z5++;
        }
        ObservableList<PieChart.Data> pieChartData = 
                FXCollections.observableArrayList(
                new PieChart.Data("Recuperación", z1),
                new PieChart.Data("Fondo", z2),
                new PieChart.Data("Tempo", z3),
                new PieChart.Data("Umbral", z4),
                new PieChart.Data("Anaeróbico", z5));
        controller.chartDistribucion.setData(pieChartData);
        controller.chartDistribucion.setTitle("Tiempo en cada zona cardiaca\n"
                                              + "           FC Maxima: " + maxFC );
    }
}
