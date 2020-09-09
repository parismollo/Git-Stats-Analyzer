
plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":config"))
    testImplementation("junit:junit:4.+")
}


