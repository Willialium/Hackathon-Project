package MidiReader;

public class Note implements Comparable
{
	public static final String NOTE_NAMES = "C,C#,D,D#,E,F,F#,G,G#,A,A#,B";

	private int pitch;
	private int startTime;
	private int endTime;
	
	public Note(int pitch, int startTime)
	{
		this.pitch = pitch;
		this.startTime = startTime;
	}

	public void setEndTime (int endTime)
	{
		this.endTime = endTime;
	}
	
	public int getStartTime ()
	{
		return startTime;
	}
	
	public int getEndTime ()
	{
		return endTime;
	}

	public int getLength ()
	{
		return endTime - startTime;
	}
	
	public int getPitch ()
	{
		return pitch;
	}
	
	public String getPitchName ()
	{
		String pitchName = NOTE_NAMES.split(",")[pitch % 12];
		pitchName += pitch / 12;
		return pitchName;
	}

	@Override
	public String toString ()
	{
		return "<Pitch: " + pitch + "(" + getPitchName() + ")" + ", Start: " + startTime + ", End: " + endTime + ">";
	}

	public String toMinString ()
	{
		return pitch + "," + getPitchName() + "," + startTime + "," + endTime + ",";
	}

	@Override
	public int compareTo (Object o)
	{
		return startTime - ((Note) o).startTime;
	}
}
