package com.music.recommender;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Path relative to src/main/resources
        String resourcePath = "songs.csv";

        MusicDataset dataset = new MusicDataset(resourcePath);
        List<Song> songs = dataset.getSongs();

        if (songs.isEmpty()) {
            System.out.println("No songs loaded!");
            return;
        }

        // Pick the first song as a seed
        Song seed = songs.get(0);
        System.out.println("Recommendations for: " + seed);

        // Create Weka-based recommender
        Recommender recommender = new Recommender(songs);

        // Get top 3 recommendations
        List<Song> recommended = recommender.recommend(seed, 3);

        if (recommended.isEmpty()) {
            System.out.println("No recommendations found.");
        } else {
            recommended.forEach(s -> System.out.println("- " + s));
        }
    }
}