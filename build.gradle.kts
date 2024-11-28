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
    implementation ("org.eclipse.parsson:parsson:1.1.1")

}

tasks.test {
    useJUnitPlatform()
}