package MidiReader;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class MIDIFile
{
	private final ArrayList<Note> notes;
	private int tempo;

	public static File csvFile = new File("Resources/temp/csv");
	public static File tempMidiFile = new File("Resources/temp/measure.mid");

	
	public MIDIFile (File midiFile)
	{
		try
		{
			int generateCSVReturn = generateCSV(midiFile, csvFile);
			if (generateCSVReturn != 0)
			{
				System.out.println("Error generating CSV file!");
				System.exit(1);
			}
		}
		catch (Exception e)
		{
			System.out.println("Error running MIDICSV!");
			e.printStackTrace();
			System.exit(1);
		}

		Scanner tempoFinder = new Scanner(System.in);
		try {
			tempoFinder = new Scanner(csvFile);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("CSV file not found!");
			e.printStackTrace();
			System.exit(1);
		}
		tempoFinder.nextLine();
		while (tempoFinder.hasNextLine())
		{
			String line = tempoFinder.nextLine();
			if (line.split(", ")[2].contains("Tempo"))
			{
				tempo = Integer.parseInt(line.split(", ")[3]);
			}
		}
		// If no tempo information is found, tempo will equal 500,000
		tempoFinder.close();
		System.out.println("Tempo: " + tempo);


		Scanner trackFinder = new Scanner(System.in);
		try {
			trackFinder = new Scanner(csvFile);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("CSV file not found!");
			e.printStackTrace();
			System.exit(1);
		}

		ArrayList<String> allTracks = new ArrayList<>();
		while (trackFinder.hasNextLine())
		{
			String line = trackFinder.nextLine();
			if (line.split(", ")[2].contains("Start_track"))
			{
				StringBuilder trackContents = new StringBuilder(line);
				while (trackFinder.hasNextLine())
				{
					String nextLine = trackFinder.nextLine();
					trackContents.append("\n").append(nextLine);
					if (nextLine.contains("End_track"))
					{
						break;
					}
				}
				trackContents.append("\n-1, -1, Note_off_c, -1, -1, -1");

				allTracks.add(trackContents.toString());
			}
		}
		trackFinder.close();
		System.out.println("Track information stored");

		String bestTrack = allTracks.get(0);
		for (String track : allTracks)
		{
			if (countMatches(track, "Note_on_c") > countMatches(bestTrack, "Note_on_c"))
			{
				bestTrack = track;
			}
		}

		System.out.println("The best track is: " + allTracks.indexOf(bestTrack));

		notes = new ArrayList<>();
		HashMap<Integer, Note> unfinishedNotes = new HashMap<>();
		Scanner noteFinder = new Scanner(bestTrack);
		int finalTimestamp = -1;
		while (noteFinder.hasNextLine())
		{
			String line = noteFinder.nextLine();
			if (line.split(", ")[2].contains("Note_on_c"))
			{
				if (unfinishedNotes.containsKey(Integer.parseInt(line.split(", ")[4])))
				{
					unfinishedNotes.get(Integer.parseInt(line.split(", ")[4])).setEndTime(Integer.parseInt(line.split(", ")[1]));
					notes.add(unfinishedNotes.get(Integer.parseInt(line.split(", ")[4])));
					unfinishedNotes.remove(Integer.parseInt(line.split(", ")[4]));
				}

				Note newNote = new Note(Integer.parseInt(line.split(", ")[4]), Integer.parseInt(line.split(", ")[1]));
				unfinishedNotes.put(Integer.parseInt(line.split(", ")[4]), newNote);
				finalTimestamp = Math.max(finalTimestamp, Integer.parseInt(line.split(", ")[1]));
			}
			else if (line.split(", ")[2].contains("Note_off_c"))
			{
				if (unfinishedNotes.containsKey(Integer.parseInt(line.split(", ")[4])))
				{
					unfinishedNotes.get(Integer.parseInt(line.split(", ")[4])).setEndTime(Integer.parseInt(line.split(", ")[1]));
					notes.add(unfinishedNotes.get(Integer.parseInt(line.split(", ")[4])));
					unfinishedNotes.remove(Integer.parseInt(line.split(", ")[4]));
				}
				finalTimestamp = Math.max(finalTimestamp, Integer.parseInt(line.split(", ")[1]));
			}
		}

		for (Note dumbNotes : unfinishedNotes.values())
		{
			dumbNotes.setEndTime(finalTimestamp);
			notes.add(dumbNotes);
		}
		noteFinder.close();
		Collections.sort(notes);
		System.out.println("Note data parsed!");
	}

	public int getTempoBPM ()
	{
		return 60000000 / tempo;
	}

	private static int generateCSV (File midiFile, File csvFile) throws IOException, InterruptedException
	{
		if (csvFile.exists())
		{
			csvFile.delete();
			System.out.println("Preexisting CSV file destroyed");
		}
		System.out.println("Midicsv.exe will now be run");
		Runtime runtime = Runtime.getRuntime();
		Process midicsvProcess = runtime.exec("Resources/midicsv/Midicsv.exe " + midiFile.getAbsolutePath() + " " + csvFile.getAbsolutePath());
		System.out.println("Resources/midicsv/Midicsv.exe " + midiFile.getAbsolutePath() + " " + csvFile.getAbsolutePath());

		int processReturn = midicsvProcess.waitFor();
		midicsvProcess.destroy();
		System.out.println("Subprocess destroyed\nSubprocess returned a value of " + processReturn);
		if (processReturn == 0)
		{
			System.out.println(".csv file sucessfully generated");
		}
		else
		{
			System.out.println("This can't be good...\nMost likely, the MIDI file you provided is broken");
			return -1;
		}
		return 0;
	}

	public ArrayList<Note> getNotes ()
	{
		return notes;
	}

	private int countMatches (String str, String findStr)
	{
		return str.split(findStr, -1).length;
	}

	public static void playMeasure (ArrayList<Note> notes)
	{
		int finalTimestamp = notes.get(0).getStartTime();
		int firstTimestamp = notes.get(0).getStartTime();
		for (Note note : notes)
		{
			finalTimestamp = Math.max(finalTimestamp, note.getEndTime());
			firstTimestamp = Math.min(firstTimestamp, note.getStartTime());
		}
		try
		{
			FileWriter writer = new FileWriter(csvFile);
			writer.write("0, 0, Header, 1, 3, 96\n" +
					"1, 0, Start_track\n" +
					"1, 0, Time_signature, 4, 2, 24, 8\n" +
					"1, 0, End_track\n" +
					"2, 0, Start_track\n" +
					"2, 0, Tempo, " + 500000 + "\n2, 0, End_track\n" +
					"3, 0, Start_track\n" +
					"3, 0, Title_t, \"measure\"\n");

			ArrayList<Event> events = new ArrayList<>();
			for (Note note : notes)
			{
				events.add(new Event(note.getStartTime() - firstTimestamp, "3, " + (note.getStartTime() - firstTimestamp) + ", Note_on_c, 0, " + note.getPitch() + ", 127\n"));
				events.add(new Event(note.getEndTime() - firstTimestamp, "3, " + (note.getEndTime() - firstTimestamp) + ", Note_off_c, 0, " + note.getPitch() + ", 0\n"));
			}
			Collections.sort(events);
			for (Event event : events)
			{
				writer.write(event.event);
			}
			writer.write("3, " + finalTimestamp + ", End_track\n" +
					"0, 0, End_of_file\n");

			writer.close();

			generateMIDI(tempMidiFile, csvFile);
		}
		catch (Exception e)
		{
			System.out.println("Unable to create measure midi file");
			return;
		}
		System.out.println("Created midi file!!!");

		try
		{
			Desktop.getDesktop().open(new File("Resources/temp/measure.mid"));
		}
		catch (Exception e)
		{
			System.out.println("Cannot open midi file :(");
		}
	}

	private static int generateMIDI (File midiFile, File csvFile) throws IOException, InterruptedException
	{
		if (midiFile.exists())
		{
			midiFile.delete();
			System.out.println("Preexisting MIDI file destroyed");
		}
		System.out.println("Csvmidi.exe will now be run");
		Runtime runtime = Runtime.getRuntime();
		Process midicsvProcess = runtime.exec("Resources/midicsv/Csvmidi.exe " + csvFile.getAbsolutePath() + " " + midiFile.getAbsolutePath());
		System.out.println("Resources/midicsv/Csvmidi.exe " + csvFile.getAbsolutePath() + " " + midiFile.getAbsolutePath());

		int processReturn = midicsvProcess.waitFor();
		midicsvProcess.destroy();
		System.out.println("Subprocess destroyed\nSubprocess returned a value of " + processReturn);
		if (processReturn == 0)
		{
			System.out.println(".mid file sucessfully generated");
		}
		else
		{
			System.out.println("Something went horribly wrong...");
			return -1;
		}
		return 0;
	}

	private static class Event implements Comparable
	{
		public int timestamp;
		public String event;
		public Event (int timestamp, String event)
		{
			this.timestamp = timestamp;
			this.event = event;
		}

		@Override
		public int compareTo(Object o) {
			return timestamp - ((Event) o).timestamp;
		}
	}
}
