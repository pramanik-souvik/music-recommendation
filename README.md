# Music Recommender Java Application

A Java-based music recommender system that uses **JavaFX** for the user interface and **Weka's KMeans clustering** for music recommendations. The application takes user input for genre, emotion, and explicit content and suggests songs that best match the user’s preferences.

---

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Dataset](#dataset)
- [Dependencies](#dependencies)
- [Setup and Build](#setup-and-build)
- [Running the Application](#running-the-application)
- [Usage](#usage)
- [Future Improvements](#future-improvements)

---

## Features

1. **Music Recommendation**
   - Uses numeric features like tempo, energy, danceability, loudness, liveness, and positiveness to cluster similar songs.
   - Clustering is performed using **KMeans (from Weka)**.
   - Recommends the top 3 songs from the most relevant cluster.

2. **User Input Filters**
   - Users can filter songs by:
     - **Genre** (pop, rock, hip-hop, jazz, etc.)
     - **Emotion** (happy, sad, angry, chill, romantic)
     - **Explicit content** (Yes/No)

3. **Modern UI**
   - Built using **JavaFX**.
   - Dropdowns for filters and a text area for recommendations.
   - Clean and intuitive interface.

4. **CSV Dataset**
   - The app reads a CSV file `songs.csv` located in `src/main/resources/`.
   - CSV contains:
     ```
     artist,song,emotion,variance,Genre,Release Date,Key,Tempo,Loudness,Explicit,Popularity,Energy,Danceability,Positiveness,Speechiness,Liveness,Acousticness,Instrumentalness
     ```

---

## Project Structure

