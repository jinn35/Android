package edu.cs.jli.slidingmusic;

public class Album {
	
	private long id;
	private String title;
	private String artist;

	public Album() {}
	
	public Album(long albumID, String albumTitle, String albumArtist) {
		  id=albumID;
		  title=albumTitle;
		  artist=albumArtist;
		}
	
	public long getID(){return id;}
	public String getTitle(){return title;}
	public String getArtist(){return artist;}

}
