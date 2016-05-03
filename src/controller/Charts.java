package controller;

import jgpx.model.analysis.TrackData;


public class Charts{
    
    private MainViewController controller;
    
    public Charts (MainViewController controller) {
        this.controller = controller;
    }
    
    public void setLabels(TrackData track){
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
    
    
}
