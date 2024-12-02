plugins {
    id("java")
}

group = "ca.axoplasm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("jakarta.json:jakarta.json-api:2.1.3")
    implementation("com.itextpdf:kernel:7.2.6")
    implementation("com.itextpdf:layout:7.2.6")
    implementation("org.eclipse.parsson:parsson:1.1.3")
    implementation("com.jgoodies:jgoodies-forms:1.9.0")
    implementation("com.jgoodies:jgoodies-common:1.8.1")
    implementation("org.apache.tika:tika-core:3.0.0")
    implementation("org.apache.tika:tika-parsers-standard-package:3.0.0")
    implementation("net.harawata:appdirs:1.2.2")

}

tasks.test {
    useJUnitPlatform()
}