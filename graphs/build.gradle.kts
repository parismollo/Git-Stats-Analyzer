plugins {
    `java-library`
}

dependencies {
    testImplementation("junit:junit:4.+")
    implementation(project(":analyzer"))
    implementation(project(":config"))
    implementation(files("../libs/jfreechart-1.5.0.jar"))
}
