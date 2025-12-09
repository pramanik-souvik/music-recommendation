package com.music.recommender;

import java.util.*;
import java.util.stream.Collectors;

public class Recommender {
    private List<Song> songs;

    public Recommender(List<Song> songs) {
        this.songs = songs;
    }

    // Find top N similar songs to the given song
    public List<Song> recommend(Song target, int topN) {
        return songs.stream()
                .filter(s -> !s.getTitle().equals(target.getTitle())) // skip same song
                .sorted(Comparator.comparingDouble(s -> similarity(target, s)))
                .limit(topN)
                .collect(Collectors.toList());
    }

    // Simple similarity: Euclidean distance over numeric features
    private double similarity(Song a, Song b) {
        double diffTempo = a.getTempo() - b.getTempo();
        double diffEnergy = a.getEnergy() - b.getEnergy();
        double diffDance = a.getDanceability() - b.getDanceability();
        double diffLoud = a.getLoudness() - b.getLoudness();
        double diffLiveness = a.getLiveness() - b.getLiveness();
        double diffPositiveness = a.getPositiveness() - b.getPositiveness();

        return Math.sqrt(diffTempo * diffTempo + diffEnergy * diffEnergy + diffDance * diffDance +
                         diffLoud * diffLoud + diffLiveness * diffLiveness + diffPositiveness * diffPositiveness);
    }
}