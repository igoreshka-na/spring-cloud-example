plugins {
    java
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven { url = uri("https://artifactory-oss.prod.netflix.net/artifactory/maven-oss-candidates") }
    maven { url = uri("https://repo.spring.io/milestone") }
}

extra["springCloudVersion"] = "2022.0.0-RC2"

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}