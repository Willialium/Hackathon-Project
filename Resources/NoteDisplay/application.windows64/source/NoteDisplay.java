import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Scanner; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class NoteDisplay extends PApplet {



ArrayList<Note> notes = new ArrayList();
int minPitch;
int maxPitch;
int minTime;
int maxTime;

public void setup ()
{
  
  frameRate(60);
  
  String inputString = "";
  try
  {
    if (args.length != 0)
    {
      inputString = args[0];
    }
  }
  catch (Exception e)
  {
    inputString = "84,C7,0,48,79,G6,48,96,76,E6,96,144,72,C6,144,192,74,D6,192,216,71,B5,216,240,67,G5,240,264,62,D5,264,288,67,G5,288,312,71,B5,312,336,74,D6,336,360,77,F6,360,384,76,E6,384,432,72,C6,432,480,67,G5,480,528,64,E5,528,576,60,C5,576,624,64,E5,624,672,67,G5,672,720,72,C6,720,768,74,D6,768,792,71,B5,792,816,67,G5,816,840,62,D5,840,864,59,B4,864,888,62,D5,888,912,67,G5,912,936,71,B5,936,960,74,D6,960,984,77,F6,984,1008,83,B6,1008,1032,86,D7,1032,1056,83,B6,1056,1080,79,G6,1080,1104,74,D6,1104,1128,71,B5,1128,1152,72,C6,1152,1200,76,E6,1200,1248,79,G6,1248,1296,84,C7,1296,1344,96,C8,1344,1535,";
  }
  
  Scanner reader = new Scanner(inputString);
  reader.useDelimiter(",");
  while (reader.hasNext())
  {
    notes.add(new Note(PApplet.parseInt(reader.next()), reader.next(), PApplet.parseInt(reader.next()), PApplet.parseInt(reader.next())));
  }
  reader.close();
  
  minPitch = notes.get(0).pitch;
  maxPitch = notes.get(0).pitch;
  minTime = notes.get(0).startTime;
  maxTime = notes.get(0).startTime;
  for (Note note : notes)
  {
    minPitch = Math.min(note.pitch, minPitch);
    maxPitch = Math.max(note.pitch, maxPitch);
    minTime = Math.min(note.startTime, minTime);
    maxTime = Math.max(note.endTime, maxTime);
  }
}

public void draw ()
{
  background(100);
  fill(0);
  rectMode(CORNER);
  int pitchRange = maxPitch + 1 - minPitch;
  int totalLength = maxTime + 1 - minTime;
  minPitch--;
  maxPitch++;
  
  for (Note note : notes)
  {
    rect( //<>//
    ((note.startTime - minTime) / (totalLength + 0.0f)) * width,
    height - (((note.pitch - minPitch) / (pitchRange + 0.0f)) * height),
    (((note.endTime - note.startTime)) / (totalLength + 0.0f)) * width,
    (1.0f / pitchRange) * height
    );
  }
  
  fill(255, 255, 255);
  for (Note note : notes)
  {
    text(note.name, ((note.startTime - minTime) / (totalLength + 0.0f)) * width, height - (((note.pitch - minPitch) / (pitchRange + 0.0f)) * height) + 12);
  }
  noLoop();
}

private class Note
{
  public int pitch;
  public String name;
  public int startTime;
  public int endTime;
  
  public Note (int pitch, String name, int startTime, int endTime)
  {
    this.pitch = pitch;
    this.name = name;
    this.startTime = startTime;
    this.endTime = endTime;
  }
}
  public void settings() {  size(1200, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "NoteDisplay" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
