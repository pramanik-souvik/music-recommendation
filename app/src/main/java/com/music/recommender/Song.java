package com.music.recommender;

public class Song {
    private String artist;
    private String title;
    private String emotion;
    private String genre;
    private int year;
    private int tempo;
    private double loudness;
    private boolean explicit;
    private int energy;
    private int danceability;
    private int positiveness; // formerly valence
    private int liveness;

    public Song(String artist, String title, String emotion, String genre, int year,
                int tempo, double loudness, boolean explicit, int energy, int danceability,
                int positiveness, int liveness) {
        this.artist = artist;
        this.title = title;
        this.emotion = emotion;
        this.genre = genre;
        this.year = year;
        this.tempo = tempo;
        this.loudness = loudness;
        this.explicit = explicit;
        this.energy = energy;
        this.danceability = danceability;
        this.positiveness = positiveness;
        this.liveness = liveness;
    }

    // Getters
    public String getArtist() { return artist; }
    public String getTitle() { return title; }
    public String getEmotion() { return emotion; }
    public String getGenre() { return genre; }
    public int getYear() { return year; }
    public int getTempo() { return tempo; }
    public double getLoudness() { return loudness; }
    public boolean isExplicit() { return explicit; }
    public int getEnergy() { return energy; }
    public int getDanceability() { return danceability; }
    public int getPositiveness() { return positiveness; }
    public int getLiveness() { return liveness; }

    @Override
    public String toString() {
        return title + " by " + artist + " (" + genre + ", " + year + ")";
    }
}