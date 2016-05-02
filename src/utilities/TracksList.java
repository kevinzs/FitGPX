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
import jgpx.model.jaxb.GpxType;
import jgpx.model.jaxb.TrackPointExtensionT;

public class TracksList {

    private List<File> files;
    private List<GpxType> gpxFiles;

    public TracksList() {
        gpxFiles = new ArrayList<>();
    }
    
    public void setFiles(List<File> files){ this.files = files; }

    public void readFiles() {
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

    public ObservableList<String> refreshList() {
        readFiles();
        List<String> list = new ArrayList();
        for(GpxType gpxFile : gpxFiles){
                for(int i=0; i<gpxFile.getTrk().size(); i++)
                        list.add(gpxFile.getTrk().get(i).getName());
        }
        ObservableList<String> data = FXCollections.observableArrayList(list);
        return data;
    }
}
