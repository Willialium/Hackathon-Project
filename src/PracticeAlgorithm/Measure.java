package PracticeAlgorithm;
import MidiReader.Note;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Measure
{
    private final ArrayList<Note> notes;
    private double rating = 0.0;
    private double note_freq = 0.0;
    private double note_changes = 0.0;
    private double syncopation = 0.0;
    private String practice_time = "";

    public Measure(ArrayList<Note> measure)
    {
        notes = measure;
        rate();
        practice();
    }

    public ArrayList<Note> getMeasure() { return notes; }

    public double getRating() { return (int) (rating * 100) / 100.0; }

    public String getTimes() { return practice_time; }

    public double getFrequency() { return (int) (note_freq * 100) / 100.0; }

    public double getChanges() { return (int) (note_changes * 100) / 100.0; }

    public double getSyncopation() { return (int) (syncopation * 100) / 100.0; }

    private void rate()
    {
        note_freq = notes.size();


        List<Integer> pitches = notes.stream().map(Note::getPitch).collect(Collectors.toList());
        double pitch_num = 0.0;

        for(int i = 0; i < pitches.size() - 1; i++)
        {
            pitch_num += Math.abs(pitches.get(i) - pitches.get(i + 1));
        }

        pitch_num /= pitches.size();
        note_changes = pitch_num;

        for (Integer pitch : pitches)
        {
            if ((pitch + 30) % 60 == 0)
            {
                syncopation += 1;
            }
        }

        rating = note_freq + note_changes + syncopation;
    }

    private void practice()
    {
        if(rating <= 18)
        {
            practice_time = "< 5 minutes";
        }
        else if(rating <= 25)
        {
            practice_time = "5-10 minutes";
        }
        else if(rating <= 40)
        {
            practice_time = "10-30 minutes";
        }
        else
        {
            practice_time = "30+ minutes";
        }
    }

    @Override
    public String toString()
    {
        return "Measure Notes: " + notes + "\n Measure rating: " + getRating();
    }

    public static void main(String[] args)
    {
    }
}

