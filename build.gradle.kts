plugins {
    kotlin("jvm") version "1.9.22"
    application
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("io.mockk:mockk:1.13.8")
}

kotlin {
    jvmToolchain(17)
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "17"
}

tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("com.example.MainKt")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
} 