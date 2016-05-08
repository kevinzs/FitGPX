package controller;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jgpx.model.analysis.TrackData;
import utilities.FileLoader;
import utilities.TracksList;

public class MainViewController implements Initializable {

    @FXML private BorderPane borderPane;
    @FXML private ListView<String> listView;
    
    @FXML protected Label labelFecha;
    @FXML protected Label labelDuracion;
    @FXML protected Label labelVelocidadMaxima;
    @FXML protected Label labelVelocidadMedia;
    @FXML protected Label labelFCMaxima;
    @FXML protected Label labelFCMinima;
    @FXML protected Label labelFCMedia;
    @FXML protected Label labelCPMaxima;
    @FXML protected Label labelCPMedia;
    @FXML protected Label labelDesnivel;
    @FXML protected Label labelDistanciaRecorrida;
    @FXML protected Label labelTiempoMovimiento;

    @FXML protected ToggleButton toggleBase;
    
    @FXML protected AreaChart<Number, Number> chartAltura;
    @FXML protected LineChart<Number, Number> chartVelocidad;
    @FXML protected LineChart<Number, Number> chartFC;
    @FXML protected LineChart<Number, Number> chartCadencia;
    @FXML protected PieChart chartDistribucion;

    private Stage stage;

    private FileLoader fileLoader;
    private TracksList tracksList;
    private List<File> files;
    private TrackData selectedTrack;
    private Summary summary;
    private Charts charts;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileLoader = new FileLoader(stage, Desktop.getDesktop());
        tracksList = new TracksList();
        summary = new Summary(this);
        charts = new Charts(this);

        listView.getSelectionModel().selectedIndexProperty().
                addListener((o, oldVal, newVal) -> {
                    selectedTrack = tracksList.getTrackData((int) newVal);
                    summary.setLabels(selectedTrack);
                    charts.setTrackData(selectedTrack);
                    charts.refreshCharts();
                });
    }

    @FXML
    private void loadAction(ActionEvent event) {
        fileLoader.loadFiles();
        if (!fileLoader.getFiles().equals(tracksList.getFiles())){
            tracksList.setFiles(fileLoader.getFiles());
            listView.setItems(tracksList.refreshList());
        }
    }

    @FXML
    private void addFileAction(ActionEvent event) {
        fileLoader.addFiles();
        if (fileLoader.hasChanged()){
            tracksList.setFiles(fileLoader.getFiles());
            listView.setItems(tracksList.refreshList());
        }
    }

    @FXML
    private void togglePressed(ActionEvent event) {
        if (selectedTrack != null){
            charts.refreshCharts();
        }
    }
}
