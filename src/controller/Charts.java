package controller;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import jgpx.model.analysis.Chunk;
import jgpx.model.analysis.TrackData;


public class Charts{
    
    private final MainViewController controller;
    private TrackData track;
    private ObservableList<Chunk> chunks;
    
    private XYChart.Series<Number,Number> seriesVelocidad;
    private XYChart.Series<Number,Number> seriesFC;
    private XYChart.Series<Number,Number> seriesCadencia;
    
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
    
    public List<XYChart.Series<Number,Number>> getSeries(){
        List<XYChart.Series<Number,Number>> list = new ArrayList<>();
        list.add(0, seriesVelocidad); list.add(1, seriesFC); list.add(2, seriesCadencia);
        return list;
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
        seriesVelocidad = new XYChart.Series();
        int value = 0;
        if (controller.toggleBase.isSelected()){
            controller.chartVelocidad.setTitle("Velocidad x Tiempo");
            for (Chunk chunk : chunks){
                value += chunk.getDuration().getSeconds();
                seriesVelocidad.getData().add(new XYChart.Data<>(value, chunk.getSpeed()));
            }
        } else {
            controller.chartVelocidad.setTitle("Velocidad x Distancia");
            for (Chunk chunk : chunks){
                value += chunk.getDistance();
                seriesVelocidad.getData().add(new XYChart.Data<>(value, chunk.getSpeed()));
            }
        }
        controller.chartVelocidad.getData().clear();
        controller.chartVelocidad.getData().addAll(seriesVelocidad);
    }
    
    public void setChartFC(){
        controller.chartFC.setCreateSymbols(false);
        seriesFC = new XYChart.Series();
        int value = 0;
        if (controller.toggleBase.isSelected()){
            controller.chartFC.setTitle("FC x Tiempo");
            for (Chunk chunk : chunks){
                value += chunk.getDuration().getSeconds();
                seriesFC.getData().add(new XYChart.Data<>(value, chunk.getAvgHeartRate()));
            }
        } else {
            controller.chartFC.setTitle("FC x Distancia");
            for (Chunk chunk : chunks){
                value += chunk.getDistance();
                seriesFC.getData().add(new XYChart.Data<>(value, chunk.getAvgHeartRate()));
            }
        }
        controller.chartFC.getData().clear();
        controller.chartFC.getData().addAll(seriesFC);
    }
        
    public void setChartCadencia(){
        controller.chartCadencia.setCreateSymbols(false);
        seriesCadencia = new XYChart.Series();
        int value = 0;
        if (controller.toggleBase.isSelected()){
            controller.chartCadencia.setTitle("Cadencia x Tiempo");
            for (Chunk chunk : chunks){
                value += chunk.getDuration().getSeconds();
                seriesCadencia.getData().add(new XYChart.Data<>(value, chunk.getAvgCadence()));
            }
        } else {
            controller.chartCadencia.setTitle("Cadencia x Distancia");
            for (Chunk chunk : chunks){
                value += chunk.getDistance();
                seriesCadencia.getData().add(new XYChart.Data<>(value, chunk.getAvgCadence()));
            }
        }
        controller.chartCadencia.getData().clear();
        controller.chartCadencia.getData().addAll(seriesCadencia);
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
                                              + "         FC Maxima: " + maxFC + " PPM" );
    }
}
