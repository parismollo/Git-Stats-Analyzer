plugins {
    `java-library`
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.9" // Install javaFX
}

javafx {
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.web") // get javaFX modules
}

dependencies {
    testImplementation("junit:junit:4.+")
}
