package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import jgpx.model.analysis.Chunk;
import jgpx.model.analysis.TrackData;


public class Charts{
    
    private final MainViewController controller;
    private TrackData track;
    private ObservableList<Chunk> chunks;
    
    private XYChart.Series<Number,Number> seriesAltura;
    private XYChart.Series<Number,Number> seriesVelocidad;
    private XYChart.Series<Number,Number> seriesFC;
    private XYChart.Series<Number,Number> seriesCadencia;
    
    private int z1, z2, z3, z4 , z5, maxFC;
    
    public Charts (MainViewController controller) {
        this.controller = controller;
    }
    
    public void setTrackData(TrackData track){ 
        this.track = track;
        chunks = track.getChunks();
    }
    
    public List<XYChart.Series<Number,Number>> getSeries(){
        List<XYChart.Series<Number,Number>> list = new ArrayList<>();
        list.add(0, seriesVelocidad); list.add(1, seriesFC); list.add(2, seriesCadencia);
        return list;
    }
    
    public void setChartAltura(){
        controller.chartAltura.setCreateSymbols(false);
        controller.chartAltura.getData().clear();
        controller.chartAltura.getData().addAll(seriesAltura);
        controller.chartAltura.getYAxis().setLabel("m");
        NumberAxis xAxis = (NumberAxis) controller.chartAltura.getXAxis();
        if (controller.toggleBase.isSelected()){
            controller.chartAltura.setTitle("Altura x Tiempo");
            xAxis.setLabel("");
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
        }
        else{
            controller.chartAltura.setTitle("Altura x Distancia");
            xAxis.setLabel("Km");
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
    }
    
    public void setChartVelocidad(){
        controller.chartVelocidad.setCreateSymbols(false);
        controller.chartVelocidad.getData().clear();
        controller.chartVelocidad.getData().addAll(seriesVelocidad);
        NumberAxis xAxis = (NumberAxis) controller.chartVelocidad.getXAxis();
        controller.chartVelocidad.getYAxis().setLabel("Km/h");
        if (controller.toggleBase.isSelected()){
            controller.chartVelocidad.setTitle("Velocidad x Tiempo");
            xAxis.setLabel("");
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
            controller.chartVelocidad.setTitle("Velocidad x Distancia");
            controller.chartVelocidad.getXAxis().setLabel("Km");
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
    }
    
    public void setChartFC(){
        controller.chartFC.setCreateSymbols(false);
        controller.chartFC.getData().clear();
        controller.chartFC.getData().addAll(seriesFC);
        NumberAxis xAxis = (NumberAxis) controller.chartFC.getXAxis();
        controller.chartFC.getYAxis().setLabel("PPM");
        if (controller.toggleBase.isSelected()){
            controller.chartFC.setTitle("FC x Tiempo");
            xAxis.setLabel("");
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
            controller.chartFC.setTitle("FC x Distancia");
            controller.chartFC.getXAxis().setLabel("Km");
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
    }
        
    public void setChartCadencia(){
        controller.chartCadencia.setCreateSymbols(false);
        controller.chartCadencia.getData().clear();
        controller.chartCadencia.getData().addAll(seriesCadencia);
        NumberAxis xAxis = (NumberAxis) controller.chartCadencia.getXAxis();
        controller.chartCadencia.getYAxis().setLabel("Pedaleadas");
        if (controller.toggleBase.isSelected()){
            controller.chartCadencia.setTitle("Cadencia x Tiempo");
            xAxis.setLabel("");
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
            controller.chartCadencia.setTitle("Cadencia x Distancia");
            controller.chartCadencia.getXAxis().setLabel("Km");
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
    }
    
    public void setChartDistribucion(){
        ObservableList<PieChart.Data> pieChartData = 
                FXCollections.observableArrayList(
                new PieChart.Data("Recuperación", z1),
                new PieChart.Data("Fondo", z2),
                new PieChart.Data("Tempo", z3),
                new PieChart.Data("Umbral", z4),
                new PieChart.Data("Anaeróbico", z5));
        controller.chartDistribucion.setData(pieChartData);
        controller.chartDistribucion.setTitle("Tiempo en cada zona cardiaca\n"
                                              + "         FC Máxima: " + maxFC + " PPM" );
    }
    
    public void refreshCharts(){
        Task<Void> task = new Task<Void>() {
            @Override 
            protected Void call() throws Exception {
                seriesAltura = new XYChart.Series();
                int altura = 0;
                if (controller.toggleBase.isSelected()){
                    for (int i=0; i<chunks.size(); i++){
                        updateProgress(i+1, chunks.size());
                        altura += chunks.get(i).getDuration().getSeconds();
                        seriesAltura.getData().add(new XYChart.Data<>(altura, chunks.get(i).getAvgHeight()));
                    }
                } else {
                    for (int i=0; i<chunks.size(); i++){
                        updateProgress(i+1, chunks.size());
                        altura += chunks.get(i).getDistance();
                        seriesAltura.getData().add(new XYChart.Data<>(altura, chunks.get(i).getAvgHeight()));
                    }
                }
                return null;
            }
        };
        
        task.setOnSucceeded((WorkerStateEvent event) -> {
            setChartAltura();
        });
        
        Task<Void> task2 = new Task<Void>() {
            @Override 
            protected Void call() throws Exception {
                seriesVelocidad = new XYChart.Series();
                int velocidad = 0;
                if (controller.toggleBase.isSelected()){
                    for (int i=0; i<chunks.size(); i++){
                        updateProgress(i+1, chunks.size());
                        velocidad += chunks.get(i).getDuration().getSeconds();
                        seriesVelocidad.getData().add(new XYChart.Data<>(velocidad, chunks.get(i).getSpeed()));
                    }
                } else {
                    for (int i=0; i<chunks.size(); i++){
                        updateProgress(i+1, chunks.size());
                        velocidad += chunks.get(i).getDistance();
                        seriesVelocidad.getData().add(new XYChart.Data<>(velocidad, chunks.get(i).getSpeed()));
                    }
                }
                return null;
            }
        };
        
        task2.setOnSucceeded((WorkerStateEvent event) -> {
            setChartVelocidad();
        });
        
        Task<Void> task3 = new Task<Void>() {
            @Override 
            protected Void call() throws Exception {
                seriesFC = new XYChart.Series();
                int fc = 0;
                if (controller.toggleBase.isSelected()){
                    for (int i=0; i<chunks.size(); i++){
                        updateProgress(i+1, chunks.size());
                        fc += chunks.get(i).getDuration().getSeconds();
                        seriesFC.getData().add(new XYChart.Data<>(fc, chunks.get(i).getAvgHeartRate()));
                    }
                } else {
                    for (int i=0; i<chunks.size(); i++){
                        updateProgress(i+1, chunks.size());
                        fc += chunks.get(i).getDistance();
                        seriesFC.getData().add(new XYChart.Data<>(fc, chunks.get(i).getAvgHeartRate()));
                    }
                }
                return null;
            }
        };
        
        task3.setOnSucceeded((WorkerStateEvent event) -> {
            setChartFC();
        });
        
        Task<Void> task4 = new Task<Void>() {
            @Override 
            protected Void call() throws Exception {
                seriesCadencia = new XYChart.Series();
                int cadencia = 0;
                if (controller.toggleBase.isSelected()){
                    for (int i=0; i<chunks.size(); i++){
                        updateProgress(i+1, chunks.size());
                        cadencia += chunks.get(i).getDuration().getSeconds();
                        seriesCadencia.getData().add(new XYChart.Data<>(cadencia, chunks.get(i).getAvgCadence()));
                    }
                } else {
                    for (int i=0; i<chunks.size(); i++){
                        updateProgress(i+1, chunks.size());
                        cadencia += chunks.get(i).getDistance();
                        seriesCadencia.getData().add(new XYChart.Data<>(cadencia, chunks.get(i).getAvgCadence()));
                    }
                }
                return null;
            }
        };
        
        task4.setOnRunning((WorkerStateEvent event) -> {
           try {
               Stage newStage = new Stage(StageStyle.UNDECORATED);
               newStage.setTitle("Cargando...");

               FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/view/LoadingView.fxml"));
               VBox root = (VBox) miCargador.load();
               
               ((LoadingViewController) miCargador.getController()).setCharts(task4);

               Scene scene = new Scene(root);
               newStage.setScene(scene);
               newStage.initModality(Modality.APPLICATION_MODAL);
               newStage.show();
            } catch (IOException ex) {
               Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
        
        task4.setOnSucceeded((WorkerStateEvent event) -> {
            setChartCadencia();
        });
        
        Task<Void> task5 = new Task<Void>() {
            @Override 
            protected Void call() throws Exception {
                maxFC = track.getMaxHeartrate();
                z1 = z2 = z3 = z4 = z5 = 0;
                for (int i=0; i<chunks.size();i++){
                    updateProgress(i+1, chunks.size());
                    double FC = chunks.get(i).getAvgHeartRate();
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
                return null;
            }
        };
        
        task5.setOnSucceeded((WorkerStateEvent event) -> {
            setChartDistribucion();
        });
        
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        
        Thread th2 = new Thread(task2);
        th2.setDaemon(true);
        th2.start();
        
        Thread th3 = new Thread(task3);
        th3.setDaemon(true);
        th3.start();
        
        Thread th4 = new Thread(task4);
        th4.setDaemon(true);
        th4.start();
        
        Thread th5 = new Thread(task5);
        th5.setDaemon(true);
        th5.start();
    }
}
