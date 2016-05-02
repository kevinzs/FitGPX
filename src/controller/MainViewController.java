
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class MainViewController implements Initializable {

    @FXML private ListView<?> listView;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
