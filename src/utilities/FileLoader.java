package utilities;

import java.awt.Desktop;
import java.io.File;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileLoader {

    private final Stage stage;
    private final Desktop desktop;
    
    private final FileChooser fileChooser;

    public FileLoader(Stage stage, Desktop desktop) {
        this.stage = stage;
        this.desktop = desktop;
        fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione los archivos");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GPX", "*.gpx"));
    }
    
    public List<File> loadFiles(){
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        return list;
    }
}
