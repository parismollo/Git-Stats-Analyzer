plugins {
    java
}

version = "0.0.1"
group = "up"

allprojects {
    repositories {
        mavenCentral()
    }

    plugins.apply("java")

    java.sourceCompatibility = JavaVersion.VERSION_1_10

}