plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
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

	// Spring Security
	implementation("org.springframework.boot:spring-boot-starter-security")

	// JPA e Banco de Dados
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.h2database:h2")

	// JWT para autenticação stateless
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// Validação
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// A linha abaixo foi removida por ser redundante
	// implementation ("org.springframework:spring-web:6.1.9")

	// A linha 'spring-boot-starter' também é transitiva do 'starter-web',
	// então podemos removê-la para deixar ainda mais limpo.
	// implementation("org.springframework.boot:spring-boot-starter")

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
