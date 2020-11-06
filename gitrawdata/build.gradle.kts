
plugins {
    `java-library`
}

dependencies {
    // https://mvnrepository.com/artifact/org.eclipse.jgit/org.eclipse.jgit
    implementation("org.eclipse.jgit:org.eclipse.jgit:5.8.1.202007141445-r")
    implementation("org.slf4j:slf4j-nop:1.7.30")
    testImplementation("junit:junit:4.+")
}


