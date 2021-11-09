package GUI;

import MidiReader.*;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;

public class Main extends Application {

    private VBox root = new VBox();
    static AnchorPane urlPane;
    static AnchorPane resultsPane;
    static StackPane stackPane;
    Stage primaryStage;
    static String URL;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;
        Scene scene = createScene();

        urlPane = FXMLLoader.load(getClass().getResource("urlSelection.fxml"));

        stackPane = new StackPane();
        stackPane.setMinHeight(380);
        stackPane.setMaxWidth(800);
        stackPane.getChildren().add(urlPane);


        root.getChildren().add(stackPane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setHeight(444);
        primaryStage.show();
        stackPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public static void transition(String s) throws IOException {
        URL = s;
        resultsPane = FXMLLoader.load(Main.class.getResource("results.fxml"));

        stackPane.getChildren().add(resultsPane);

        resultsPane.setTranslateX(0);
        resultsPane.setTranslateY(381);

        TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), urlPane);
        TranslateTransition t2 = new TranslateTransition(Duration.seconds(1), resultsPane);

        if(urlPane.getTranslateX()==0){
            stackPane.setBackground(new Background(new BackgroundFill(Color.web("#38C759"), CornerRadii.EMPTY, Insets.EMPTY)));

            t1.setFromX(0);
            t1.setToX(801);
            t2.setFromY(481);
            t2.setToY(0);
        }
        t1.play();
        t2.play();



        //urlPane.setTranslateX(801);
        //resultsPane.setTranslateY(0);
    }

    private Scene createScene(){

        root.getChildren().add(getMenuBar());
        Scene scene = new Scene(root, 800, 380);

        return scene;
    }

    private MenuBar getMenuBar(){

        MenuBar menuBar = new MenuBar();

        Menu menu = new Menu("Credits");
        MenuItem first = new MenuItem("Tommy");
        MenuItem second = new MenuItem("Jeffery");
        MenuItem third = new MenuItem("William");
        MenuItem fourth = new MenuItem("Naveen");

        menu.getItems().addAll(first, second, third, fourth);

        menuBar.getMenus().add(menu);
        return menuBar;

    }

    public String getURL(){
        return URL;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
