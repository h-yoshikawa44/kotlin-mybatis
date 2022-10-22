import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.arenagod.gradle.MybatisGenerator") version "1.4"
    kotlin("jvm") version "1.7.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.mybatis:mybatis:3.5.11")
    implementation("org.mybatis.dynamic-sql:mybatis-dynamic-sql:1.4.1")
    implementation("mysql:mysql-connector-java:8.0.30")
    mybatisGenerator("org.mybatis.generator:mybatis-generator-core:1.4.1")
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

mybatisGenerator {
    verbose = true
    configFile = "${projectDir}/src/main/resources/generatorConfig.xml"
    dependencies {
        mybatisGenerator(group="mysql", name="mysql-connector-java", version="8.0.30")
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
