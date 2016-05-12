package utilities;

import controller.LoadingViewController;
import controller.MainViewController;
import java.io.File;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import jgpx.model.analysis.TrackData;
import jgpx.model.gpx.Track;
import jgpx.model.jaxb.GpxType;
import jgpx.model.jaxb.TrackPointExtensionT;

public class TracksList {

    private List<File> files;
    private List<GpxType> gpxFiles;
    private List<TrackData> tracksList;
    
    private MainViewController controller;

    public TracksList(MainViewController controller) {
        gpxFiles = new ArrayList<>();
        tracksList = new ArrayList<>();
        this.controller = controller;
    }
    
    public TrackData getTrackData(int i) { return tracksList.get(i); }
    
    public void setFiles(List<File> files) { this.files = files; }
    
    public List<File> getFiles() { return this.files; }

    public void loadFiles() {
        if (files != null){
            gpxFiles.clear();
            Task<Void> task = new Task<Void>() {
                @Override 
                protected Void call() throws Exception {
                    for (int i=0; i<files.size(); i++) {
                        updateProgress(i,files.size()-1);
                        try{
                            JAXBContext jaxbContext = JAXBContext.newInstance(GpxType.class,
                                    TrackPointExtensionT.class);
                            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                            JAXBElement<Object> root = (JAXBElement<Object>) unmarshaller.unmarshal(files.get(i));
                            gpxFiles.add((GpxType) root.getValue());
                        } catch (JAXBException e){
                            e.printStackTrace();
                        }
                    }
                    return null;
                }
            };
            
            task.setOnRunning((WorkerStateEvent event) -> {
                try {
                    Stage newStage = new Stage(StageStyle.UNDECORATED);
                    newStage.setTitle("Cargando...");

                    FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/view/LoadingView.fxml"));
                    VBox root = (VBox) miCargador.load();

                    ((LoadingViewController) miCargador.getController()).setCharts(task);

                    Scene scene = new Scene(root);
                    newStage.setScene(scene);
                    newStage.initModality(Modality.APPLICATION_MODAL);
                    newStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                } 
            });
            
            task.setOnSucceeded((WorkerStateEvent event) -> {
                refreshList();
            });
            
            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
        }
    }
    
    public void refreshList(){
        Task<Void> task = new Task<Void>() {
            @Override 
            protected Void call() throws Exception {
                tracksList.clear();
                List<String> list = new ArrayList();
                for (int j=0; j<gpxFiles.size(); j++){
                    updateProgress(j,gpxFiles.size()-1);
                    for (int i = 0; i < gpxFiles.get(j).getTrk().size(); i++){
                        list.add(gpxFiles.get(j).getTrk().get(i).getName());
                        TrackData trackData = new TrackData(new Track(gpxFiles.get(j).getTrk().get(i)));
                        tracksList.add(trackData);            
                    }
                }
                ObservableList<String> data = FXCollections.observableArrayList(list);
                controller.setListItems(data);
                return null;
            }
        };
            
        task.setOnRunning((WorkerStateEvent event) -> {
            try {
                Stage newStage = new Stage(StageStyle.UNDECORATED);
                newStage.setTitle("Cargando...");

                FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/view/LoadingView.fxml"));
                VBox root = (VBox) miCargador.load();

                ((LoadingViewController) miCargador.getController()).setCharts(task);

                Scene scene = new Scene(root);
                newStage.setScene(scene);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }
}
