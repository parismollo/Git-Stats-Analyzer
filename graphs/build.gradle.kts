plugins {
    `java-library`
}

dependencies {
    testImplementation("junit:junit:4.+")
    implementation(files("lib/jfreechart-1.5.0.jar"))
}
