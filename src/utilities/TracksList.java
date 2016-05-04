package utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public TracksList() {
        gpxFiles = new ArrayList<>();
        tracksList = new ArrayList<>();
    }
    
    public TrackData getTrackData(int i) { return tracksList.get(i); }
    
    public void setFiles(List<File> files){ this.files = files; }

    public void readFiles() {
        if(files != null){
            gpxFiles.clear();
            for(File file : files){
                try{
                    JAXBContext jaxbContext = JAXBContext.newInstance(GpxType.class,
                            TrackPointExtensionT.class);
                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                    JAXBElement<Object> root = (JAXBElement<Object>) unmarshaller.unmarshal(file);
                    gpxFiles.add((GpxType) root.getValue());
                } catch (JAXBException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public ObservableList<String> refreshList() {
        readFiles();
        tracksList.clear();
        List<String> list = new ArrayList();
        for(GpxType gpxFile : gpxFiles){
            for(int i=0; i<gpxFile.getTrk().size(); i++){
                list.add(gpxFile.getTrk().get(i).getName());
                TrackData trackData = new TrackData(new Track(gpxFile.getTrk().get(i)));
                tracksList.add(trackData);            
            }
        }
        ObservableList<String> data = FXCollections.observableArrayList(list);
        return data;
    }
}
