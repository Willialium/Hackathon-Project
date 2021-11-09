package GUI;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class URLController {

    public Button button;
    public Label label;
    public AnchorPane anchor;
    public TextField field;

    public String midiUrl;
    public ImageView loading;

    public void browse(ActionEvent actionEvent) {

        final FileChooser fChooser = new FileChooser();
        fChooser.setTitle("Choose MIDI file");
        fChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MIDI Files", "*.mid"));

        Stage stage = (Stage) anchor.getScene().getWindow();
        fChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/Resources/Midis"));
        File file = fChooser.showOpenDialog(stage);

        if(file != null){
            midiUrl = file.getAbsolutePath();
            field.setText(midiUrl);
            loading.setTranslateX(0);
            loadingAnimation();
        }

    }

    public void loadingAnimation(){
        RotateTransition rt = new RotateTransition(Duration.seconds(3), loading);
        rt.setByAngle(30);
        rt.setToAngle(1080);
        rt.setOnFinished(e->{
            try {
                System.out.println(midiUrl);
                Main.transition(midiUrl);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        rt.play();


    }
}
