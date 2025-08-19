plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.25" // ADICIONE ESTA LINHA
}

group = "com.rrmvies.bff"
version = "0.0.1-SNAPSHOT"
description = "Demo project rrmovies bff"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Esta linha já traz tudo que você precisa para web, incluindo spring-web
	implementation ("org.springframework.boot:spring-boot-starter-web")

	// Esta é importante para o Spring "entender" Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.9.0-RC")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.9.0-RC")

	// Segurança
	implementation("org.springframework.boot:spring-boot-starter-security")

	// Persistência de Dados
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// Biblioteca JWT (vamos usar a implementação da Auth0/Okta, que é um fork popular da original)
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.111.Final:osx-aarch_64")

	// Dependências de teste
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
