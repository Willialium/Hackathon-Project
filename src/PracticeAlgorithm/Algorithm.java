package PracticeAlgorithm;

import MidiReader.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Algorithm {

    MIDIFile audio;
    ArrayList<Note> notes;
    ArrayList<Measure> measures;

    public Algorithm(String path) {
        audio = new MIDIFile(new File(path));
        notes = audio.getNotes();
        measures = getMeasures();
    }

    public ArrayList<Measure> getMeasures() {
        ArrayList<Measure> measures = new ArrayList<>();
        int measureLength = notes.stream().mapToInt(Note::getLength).filter(x -> x != 0).min().orElse(-1) * 16;
        System.out.println("Measure length: " + measureLength);
        int current = measureLength;
        ArrayList<Note> measureNotes = new ArrayList<>();
        for (Note n : notes) {
            if (n.getStartTime() >= current) {
                current += measureLength;
                measures.add(new Measure(new ArrayList<>(measureNotes)));
                measureNotes.clear();
            }
            measureNotes.add(n);
        }
        if (!measureNotes.isEmpty()) {
            measures.add(new Measure(measureNotes));
        }
        return measures;
    }

    public List<String> getTimes() {
        return measures.stream().map(Measure::getTimes).collect(Collectors.toList());
    }

    public List<double[]> getRatings() {
        return measures.stream().map(x -> new double[] {x.getFrequency(), x.getChanges(), x.getSyncopation(), x.getRating()}).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Algorithm a = new Algorithm("Resources/Midis/hack.mid");
        System.out.println(a.notes.size());
        System.out.println(a.measures.size());
        for (Measure m : a.measures) {
            System.out.println(m);

//            NoteDisplay.DisplayNotes(m.getMeasure());
        }
//        MIDIFile.playMeasure(measures.get(3).getMeasure());
//        System.out.println(a.getRatings());
//        System.out.println(a.getTimes());
    }
}
