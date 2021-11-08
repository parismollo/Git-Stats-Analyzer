
plugins {
    `java-library`
}

dependencies {
    implementation(project(":config"))
    implementation(project(":gitrawdata"))
    testImplementation("junit:junit:4.+")
    testImplementation("junit:junit:5.8.1")
}


