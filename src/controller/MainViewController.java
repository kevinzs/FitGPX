package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jgpx.model.analysis.TrackData;
import utilities.FileLoader;
import utilities.TracksList;

public class MainViewController implements Initializable {
    
    @FXML private ScrollPane scrollPane;
    @FXML protected BorderPane borderPane;
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
        tracksList = new TracksList(this);
        summary = new Summary(this);
        charts = new Charts(this);

        listView.getSelectionModel().selectedIndexProperty().
                addListener((o, oldVal, newVal) -> {
                    selectedTrack = tracksList.getTrackData((int) newVal);
                    summary.setLabels(selectedTrack);
                    charts.setTrackData(selectedTrack);
                    charts.refreshCharts();
                });
        
        scrollPane.widthProperty().addListener(
                (observable, oldvalue, newvalue) -> borderPane.setPrefWidth((Double)newvalue - 20)
        );
    }

    @FXML
    private void loadAction(ActionEvent event) {
        fileLoader.loadFiles();
        if (!fileLoader.getFiles().equals(tracksList.getFiles())){
            tracksList.setFiles(fileLoader.getFiles());
            tracksList.refreshList();
        }
    }

    @FXML
    private void addFileAction(ActionEvent event) {
        fileLoader.addFiles();
        if (fileLoader.hasChanged()){
            tracksList.setFiles(fileLoader.getFiles());
            tracksList.refreshList();
        }
    }

    @FXML
    private void togglePressed(ActionEvent event) {
        if (selectedTrack != null){
            charts.refreshCharts();
        }
    }
    
    @FXML
    private void combineAction(ActionEvent event) {
        if (charts.getSeries().get(0) != null){
            try {
                Stage newStage = new Stage();
                newStage.setTitle("Combinar graficas");

                FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/view/CombineView.fxml"));
                AnchorPane root = (AnchorPane) miCargador.load();


                ((CombineViewController) miCargador.getController()).setController(this);
                ((CombineViewController) miCargador.getController()).setSeries(charts.getSeries());


                Scene scene = new Scene(root);
                newStage.setScene(scene);
                newStage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("Ninguna sesión seleccionada.");
            alert.setContentText("Cargue y/o seleccione alguna sesión por favor.");
            alert.showAndWait();
        }
    }
    
    public void setListItems(ObservableList<String> data){
        listView.setItems(data);
    }
}
