import org.asciidoctor.gradle.jvm.AsciidoctorTask

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("groovy")

    id("org.asciidoctor.jvm.convert") version "4.0.4"
}

group = "com.jeffrey"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

val asciidoctorExt: Configuration by configurations.creating

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Spring REST Docs
    testImplementation("org.springframework.restdocs:spring-restdocs-restassured:3.0.3")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:3.0.3")

    // RestAssured + Spring MockMvc
    testImplementation("io.rest-assured:spring-mock-mvc:5.5.1") {
        exclude(group = "org.springframework", module = "spring-webmvc")
    }
    testImplementation("org.springframework:spring-webmvc:6.2.6")
    testImplementation("io.rest-assured:rest-assured:5.5.1") {
        exclude(group = "org.apache.johnzon", module = "johnzon-mapper")
        exclude(group = "org.codehaus.jackson", module = "jackson-mapper-asl")
        exclude(group = "commons-io", module = "commons-io")
    }
    testImplementation("commons-io:commons-io:2.19.0")

    // Spock
    testImplementation("org.spockframework:spock-core:2.4-M6-groovy-4.0")
    testImplementation("org.spockframework:spock-spring:2.4-M6-groovy-4.0")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val snippetsDir = file("build/generated-snippets")
val asciidocOutputDir = file("build/generated-docs")

tasks.test {
    outputs.dir(snippetsDir)
    useJUnitPlatform()
    jvmArgs("-Xshare:off")
}

tasks.named<AsciidoctorTask>("asciidoctor") {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
    baseDirFollowsSourceFile()
    configurations("asciidoctorExt")
    setOutputDir(asciidocOutputDir)
}

// restdocs default snippet exclude
tasks.named<ProcessResources>("processTestResources") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named<Jar>("bootJar") {
    dependsOn("asciidoctor")

    from(asciidocOutputDir) {
        into("BOOT-INF/classes/static/docs")
    }
}