plugins {
    `java-library`
}

dependencies {
    testImplementation("junit:junit:4.+")
    implementation(project(":gitrawdata"))
    implementation(project(":analyzer"))
    implementation(project(":config"))
    implementation(project(":graphs"))
    implementation(files("../libs/jfreechart-1.5.0.jar"))
}


   
