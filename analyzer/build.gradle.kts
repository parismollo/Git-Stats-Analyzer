
plugins {
    `java-library`
}

dependencies {
    implementation(project(":config"))
    implementation(project(":datamodel"))
    testImplementation("junit:junit:4.+")
}


