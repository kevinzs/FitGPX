package utilities;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileLoader {

    private final Stage stage;
    private final Desktop desktop;
    
    private List<File> files;
    private boolean changed;
    private final FileChooser fileChooser;

    public FileLoader(Stage stage, Desktop desktop) {
        this.stage = stage;
        this.desktop = desktop;
        this.files = new ArrayList<>();
        changed = false;
        fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione los archivos");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GPX", "*.gpx"));
        File defaultDirectory = new File("tracks/");
        fileChooser.setInitialDirectory(defaultDirectory);
    }
    
    public List<File> getFiles() { return this.files; }
    
    public boolean hasChanged() { return changed; }
    
    public void loadFiles(){
        changed = true;
        this.files = fileChooser.showOpenMultipleDialog(stage);
        if(this.files == null) changed = false;
    }
    
    public void addFiles(){
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        if (list != null){
            this.files = new ArrayList<>(this.files);
            for (int i = 0; i < list.size(); i++)
                this.files.add(list.get(i));
            changed = true;
        } else
            changed = false;
    }
}
