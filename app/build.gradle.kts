plugins {
    application
    id("org.openjfx.javafxplugin") version "0.0.14"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("nz.ac.waikato.cms.weka:weka-stable:3.8.6")
}

application {
    mainClass.set("com.music.recommender.MainApp")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}