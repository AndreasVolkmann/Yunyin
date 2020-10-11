import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.4.10"
	id("org.springframework.boot") version "2.4.0-M2"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	id("org.openjfx.javafxplugin") version "0.0.9"
	id("com.github.ben-manes.versions") version "0.33.0"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion
}

group = "me.avo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_14
val javaVersion = java.sourceCompatibility.majorVersion

repositories {
	jcenter()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("no.tornado:tornadofx:1.7.20")
	implementation("com.googlecode.soundlibs:jlayer:1.0.1.4")

	/* OneDrive */
	implementation("com.microsoft.azure:msal4j:1.7.1")
	implementation("com.microsoft.graph:microsoft-graph:2.3.1")

	runtimeOnly("com.h2database:h2")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("io.strikt:strikt-spring:0.28.0")
	testImplementation("com.tngtech.archunit:archunit:0.14.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = javaVersion
	}
}

javafx {
	version = javaVersion
	modules = listOf("javafx.controls", "javafx.graphics", "javafx.web", "javafx.media")
}
