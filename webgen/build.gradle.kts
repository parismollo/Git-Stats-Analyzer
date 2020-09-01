
plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":config"))
    implementation(project(":datamodel"))
    testImplementation("junit:junit:4.+")
}


