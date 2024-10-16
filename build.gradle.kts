plugins {
    id("java")
    id("io.qameta.allure") version "2.12.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured:5.5.0")
    testCompileOnly("io.rest-assured:rest-assured:5.5.0")
    testCompileOnly("io.rest-assured:rest-assured:5.5.0")
    compileOnly("io.rest-assured:rest-assured:5.5.0")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.34")
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    testImplementation("org.testng:testng:7.8.0")
    testImplementation("io.qameta.allure:allure-testng:2.20.1")
    testImplementation("io.qameta.allure:allure-junit-platform:2.20.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.2.1")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:3.0.1")
    implementation("org.glassfish.jaxb:jaxb-runtime:3.0.1")



}

tasks.test {
    useJUnitPlatform()
    useTestNG()
}
