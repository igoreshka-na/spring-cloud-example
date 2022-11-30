import io.github.fomin.oasgen.gradle.DirectorySource
import io.github.fomin.oasgen.gradle.java.SingleNamespace
import io.github.fomin.oasgen.gradle.java.SingleOutput
import io.github.fomin.oasgen.gradle.java.javaReactorNettyClient

plugins {
    java
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("io.github.fomin.oas-gen") version "0.2.2"
    id("org.sonarqube") version "3.5.0.2730"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://artifactory-oss.prod.netflix.net/artifactory/maven-oss-candidates") }
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://plugins.gradle.org/m2/") }
}

extra["springCloudVersion"] = "2022.0.0-RC2"

dependencies {
    implementation("io.github.fomin.oas-gen:gradle-plugin:0.2.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

oasGen {
    generate("user-controller") {
        source = DirectorySource(file("src/main/resources/openapi"))
        schemaPath = "UserController.yaml"
        generator = javaReactorNettyClient(
                namespaceConfiguration = SingleNamespace("com.example"),
                outputConfiguration = SingleOutput(java.sourceSets.main),
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

sonarqube {
    properties {
        property("sonar.projectKey") to "igoreshka-na_spring-cloud-example"
        property("sonar.organization") to "igoreshka-na"
        property( "sonar.host.url") to "https://sonarcloud.io"
    }
}