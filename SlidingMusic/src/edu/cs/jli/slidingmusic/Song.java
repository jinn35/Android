package edu.cs.jli.slidingmusic;

public class Song {
	
	private long id;
	private String title;
	private String artist;
	private String album;
	private String duration;
	private String size;
	private String year;

	public Song() {}
	
	public Song(long songID, String songTitle, String songArtist, String songAlbum, String songDuration,
			String songSize, String songYear) {
		  id=songID;
		  title=songTitle;
		  artist=songArtist;
		  album=songAlbum;
		  duration=songDuration;
		  size=songSize;
		  year=songYear;
		}
	
	public long getID(){return id;}
	public String getTitle(){return title;}
	public String getArtist(){return artist;}
	public String getAlbum(){return album;}
	public String getDuration(){return duration;}
	public String getSize(){return size;}
	public String getYear(){return year;}

}
