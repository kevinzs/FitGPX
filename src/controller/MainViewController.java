package controller;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jgpx.model.analysis.TrackData;
import utilities.FileLoader;
import utilities.TracksList;

public class MainViewController implements Initializable {

    @FXML private BorderPane borderPane;
    @FXML private ListView<String> listView;
    @FXML private Label labelFecha;
    @FXML private Label labelDuracion;
    @FXML private Label labelVelocidadMaxima;
    @FXML private Label labelVelocidadMedia;
    @FXML private Label labelFCMaxima;
    @FXML private Label labelFCMinima;
    @FXML private Label labelFCMedia;
    @FXML private Label labelCPMaxima;
    @FXML private Label labelCPMinima;
    @FXML private Label labelDesnivel;
    @FXML private Label labelDistanciaRecorrida;
    @FXML private Label labelTiempoMovimiento;
    @FXML private Label labelGraficaAltura;
    @FXML private Label labelGraficaVelocidad;
    @FXML private Label labelGraficaFC;
    @FXML private Label labelGraficaCadencia;
    @FXML private Insets x1;

    private Stage stage;

    private FileLoader fileLoader;
    private TracksList tracksList;
    private List<File> files;
    private TrackData selectedTrack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileLoader = new FileLoader(stage, Desktop.getDesktop());
        tracksList = new TracksList();

        listView.getSelectionModel().selectedIndexProperty().
                addListener((o, oldVal, newVal) -> {
                    selectedTrack = tracksList.getTrackData((int) newVal);
                });
    }

    @FXML
    private void loadAction(ActionEvent event) {
        files = fileLoader.loadFiles();
        tracksList.setFiles(files);
        listView.setItems(tracksList.refreshList());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
