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
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("org.glassfish:javax.json:1.1.4")
}

tasks.test {
    useJUnitPlatform()
}