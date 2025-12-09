package com.music.recommender;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import weka.clusterers.SimpleKMeans;
import weka.core.*;

import java.util.*;
import java.util.stream.Collectors;

public class MainApp extends Application {

    private MusicDataset dataset;

    @Override
    public void start(Stage stage) {
        dataset = new MusicDataset("songs.csv");
        if (dataset.getSongs().isEmpty()) {
            System.out.println("ERROR: No songs loaded.");
            return;
        }

        // --- UI CONTROLS ---
        Label titleLabel = new Label("🎵 Music Recommender");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        ComboBox<String> genreBox = new ComboBox<>();
        genreBox.getItems().addAll("pop", "rock", "hip-hop", "r&b", "jazz", "k-pop", "edm", "metal", "folk");
        genreBox.setPromptText("Select Genre");

        ComboBox<String> emotionBox = new ComboBox<>();
        emotionBox.getItems().addAll("joy", "sadness", "love", "anger", "chill", "romantic");
        emotionBox.setPromptText("Select Emotion");

        ComboBox<String> explicitBox = new ComboBox<>();
        explicitBox.getItems().addAll("Yes", "No");
        explicitBox.setPromptText("Explicit Content");

        Button recommendButton = new Button("Recommend Songs");
        recommendButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setStyle("-fx-font-size: 14px;");

        // --- BUTTON ACTION ---
        recommendButton.setOnAction(e -> {
            String genre = genreBox.getValue();
            String emotion = emotionBox.getValue();
            String explicit = explicitBox.getValue();

            if (genre == null || emotion == null || explicit == null) {
                outputArea.setText("⚠️ Please select all filters.");
                return;
            }

            List<Song> filtered = dataset.getSongs().stream()
                    .filter(s -> s.getGenre().equalsIgnoreCase(genre))
                    .filter(s -> s.getEmotion().equalsIgnoreCase(emotion))
                    .filter(s -> explicit.equals("Yes") ? s.isExplicit() : !s.isExplicit())
                    .collect(Collectors.toList());

            if (filtered.size() < 3) {
                outputArea.setText("⚠️ Not enough songs for this selection.");
                return;
            }

            try {
                List<Song> recommended = runKMeansRecommender(filtered);
                outputArea.setText("🎧 Recommendations:\n");
                for (Song s : recommended) {
                    outputArea.appendText("- " + s + "\n");
                }
            } catch (Exception ex) {
                outputArea.setText("❌ Error running KMeans: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // --- LAYOUT ---
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(
                titleLabel,
                genreBox,
                emotionBox,
                explicitBox,
                recommendButton,
                outputArea
        );

        // Styling the root
        root.setStyle("-fx-background-color: #f0f0f0;");

        Scene scene = new Scene(root, 450, 500);
        stage.setScene(scene);
        stage.setTitle("Music Recommender");
        stage.show();
    }

    // --------------------------------------------
    // KMEANS RECOMMENDER
    // --------------------------------------------
    private List<Song> runKMeansRecommender(List<Song> list) throws Exception {
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("tempo"));
        attributes.add(new Attribute("energy"));
        attributes.add(new Attribute("danceability"));
        attributes.add(new Attribute("loudness"));
        attributes.add(new Attribute("liveness"));
        attributes.add(new Attribute("positiveness"));

        Instances data = new Instances("songs", attributes, list.size());

        for (Song s : list) {
            double[] vals = new double[]{
                    s.getTempo(),
                    s.getEnergy(),
                    s.getDanceability(),
                    s.getLoudness(),
                    s.getLiveness(),
                    s.getPositiveness()
            };
            data.add(new DenseInstance(1.0, vals));
        }

        SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setNumClusters(3);
        kmeans.buildClusterer(data);

        Map<Integer, List<Song>> clusterMap = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            int clusterId = kmeans.clusterInstance(data.get(i));
            clusterMap.putIfAbsent(clusterId, new ArrayList<>());
            clusterMap.get(clusterId).add(list.get(i));
        }

        // Pick the largest cluster
        int largestCluster = clusterMap.entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getValue().size()))
                .get().getKey();

        List<Song> clusterSongs = clusterMap.get(largestCluster);

        return clusterSongs.stream().limit(3).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        launch();
    }
}