plugins {
    kotlin("jvm") version "1.9.20"
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("org.assertj:assertj-core:3.19.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}