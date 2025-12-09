package com.music.recommender;

import java.io.*;
import java.util.*;

public class MusicDataset {

    private List<Song> songs = new ArrayList<>();

    public MusicDataset(String resourcePath) {
        loadCSV(resourcePath);
    }

    private void loadCSV(String resourcePath) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.err.println("Resource not found: " + resourcePath);
                return;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line = br.readLine(); // skip header
                while ((line = br.readLine()) != null) {
                    // Split respecting quotes around commas
                    String[] parts = parseCSVLine(line);
                    if (parts.length < 18) {
                        System.out.println("Skipping line due to parse error: " + Arrays.toString(parts));
                        continue;
                    }

                    try {
                        String artist = parts[0];
                        String title = parts[1];
                        String emotion = parts[2];
                        String genre = parts[4];
                        int year = Integer.parseInt(parts[5]);
                        int tempo = Integer.parseInt(parts[7]);
                        double loudness = Double.parseDouble(parts[8]);
                        boolean explicit = parts[9].equalsIgnoreCase("Yes");
                        int energy = Integer.parseInt(parts[11]);
                        int danceability = Integer.parseInt(parts[12]);
                        int positiveness = Integer.parseInt(parts[13]);
                        int liveness = Integer.parseInt(parts[15]);

                        Song song = new Song(
                                artist, title, emotion, genre, year,
                                tempo, loudness, explicit, energy, danceability,
                                positiveness, liveness
                        );
                        songs.add(song);

                    } catch (NumberFormatException ex) {
                        System.out.println("Skipping line due to number format error: " + Arrays.toString(parts));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle CSV fields with commas inside quotes
    private String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        result.add(sb.toString().trim());
        return result.toArray(new String[0]);
    }

    public List<Song> getSongs() {
        return songs;
    }
}