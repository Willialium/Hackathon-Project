package MidiReader;
import java.util.ArrayList;

public class NoteDisplay
{
    public static void DisplayNotes (ArrayList<Note> notes)
    {
        String paramString = "";
        for (Note note : notes)
        {
            paramString += note.toMinString();
        }

        try
        {
            Runtime runtime = Runtime.getRuntime();
            Process noteDisplayProcess = runtime.exec("NoteDisplay.exe " + paramString);
            int processReturn = noteDisplayProcess.waitFor();
            noteDisplayProcess.destroy();
            System.out.println("Subprocess destroyed\nSubprocess returned a value of " + processReturn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
