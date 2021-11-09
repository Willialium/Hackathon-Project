package GUI;

import MidiReader.MIDIFile;
import MidiReader.NoteDisplay;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import PracticeAlgorithm.*;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;


public class ResultsController {


    private Algorithm algorithm = new Algorithm(Main.URL);
    private ArrayList<Measure> measures = algorithm.getMeasures();
    private ArrayList<String> getTimes = (ArrayList<String>)algorithm.getTimes();
    private ArrayList<double[]> ratings = (ArrayList<double[]>) algorithm.getRatings();

    public Label label1, label2, label3, label4;

    public void initialize(){
        String firstBox = "First Measure\n\n" + toStrings(getTimes.get(0), ratings.get(0));
        label1.setText(firstBox);

        String secondBox = "Second Measure\n\n" + toStrings(getTimes.get(1), ratings.get(1));
        label2.setText(secondBox);

        String thirdBox = "Third Measure\n\n" + toStrings(getTimes.get(2), ratings.get(2));
        label3.setText(thirdBox);

        String fourthBox = "Fourth Measure\n\n" + toStrings(getTimes.get(3), ratings.get(3));
        label4.setText(fourthBox);

    }

    public String toStrings(String times, double[] rating){
        String string = "";

        string += "Practice for " + times + "\n";
        string += "Fingering Difficulty Rating of " + rating[0] + "\n";
        string += "Intonation Difficulty Rating of " + rating[1] + "\n";
        string += "Rhythm Difficulty Rating of " + rating[2] + "\n";
        string += "Overall Difficulty Rating of " + rating[3] + "\n";

        return string;
    }

    public void buttonOne(ActionEvent actionEvent) {
        MIDIFile mf = new MIDIFile(new File(Main.URL));
        MIDIFile.playMeasure(measures.get(0).getMeasure());
    }
    public void buttonOneMap(ActionEvent actionEvent) {
        MIDIFile mf = new MIDIFile(new File(Main.URL));
        NoteDisplay.DisplayNotes(measures.get(0).getMeasure());
    }
    public void buttonTwo(ActionEvent actionEvent) {
        MIDIFile mf = new MIDIFile(new File(Main.URL));
        MIDIFile.playMeasure(measures.get(1 ).getMeasure());
    }
    public void buttonTwoMap(ActionEvent actionEvent) {
        MIDIFile mf = new MIDIFile(new File(Main.URL));
        NoteDisplay.DisplayNotes(measures.get(1).getMeasure());
    }
    public void buttonThree(ActionEvent actionEvent) {
        MIDIFile mf = new MIDIFile(new File(Main.URL));
        MIDIFile.playMeasure(measures.get(2).getMeasure());
    }
    public void buttonThreeMap(ActionEvent actionEvent) {
        MIDIFile mf = new MIDIFile(new File(Main.URL));
        NoteDisplay.DisplayNotes(measures.get(2).getMeasure());
    }
    public void buttonFour(ActionEvent actionEvent) {
        MIDIFile mf = new MIDIFile(new File(Main.URL));
        MIDIFile.playMeasure(measures.get(3).getMeasure());
    }
    public void buttonFourMap(ActionEvent actionEvent) {
        MIDIFile mf = new MIDIFile(new File(Main.URL));
        NoteDisplay.DisplayNotes(measures.get(3).getMeasure());
    }
    public void buttonTotal(ActionEvent actionEvent) {
        MIDIFile mf = new MIDIFile(new File(Main.URL));
        MIDIFile.playMeasure(mf.getNotes());
    }
    public void buttonTotalMap(ActionEvent actionEvent) {
        MIDIFile mf = new MIDIFile(new File(Main.URL));
        NoteDisplay.DisplayNotes(mf.getNotes());
    }
}
