
package controller;

import java.text.DecimalFormat;
import java.time.Duration;
import jgpx.model.analysis.TrackData;


public class Summary{
    
    private MainViewController controller;
    
    public Summary (MainViewController controller) {
        this.controller = controller;
    }
    
    public void setLabels(TrackData track){
        controller.labelFecha.setText(track.getStartTime().toString().substring(0,10));
        controller.labelDuracion.setText(formatDuration(track.getTotalDuration()));
        controller.labelVelocidadMaxima.setText(formatDouble(track.getMaxSpeed())+ " Km/h");
        controller.labelVelocidadMedia.setText(formatDouble(track.getAverageSpeed()) + " Km/h");
        controller.labelFCMaxima.setText(track.getMaxHeartrate() + " PPM");
        controller.labelFCMinima.setText(track.getMinHeartRate() + " PPM");
        controller.labelFCMedia.setText(track.getAverageHeartrate() + " PPM");
        controller.labelCPMaxima.setText(track.getMaxCadence() + " Pedaleadas/m");
        controller.labelCPMedia.setText(track.getAverageCadence() + " Pedaleadas/m");
        controller.labelDesnivel.setText(track.getMaxHeight() - track.getMinHeight() + " m");   
        controller.labelDistanciaRecorrida.setText(formatDouble(track.getTotalDistance() / 1e3) + " Km");
        controller.labelTiempoMovimiento.setText(formatDuration(track.getMovingTime()));
    }
    
    public String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
            "%d:%02d:%02d",
            absSeconds / 3600,
            (absSeconds % 3600) / 60,
            absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }
    
    public String formatDouble (Double doub) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(doub);
    }
}
