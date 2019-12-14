plugins {
    java
    kotlin("jvm") version "1.3.61"
}

group = "dev.forcetower.unes"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation("org.jsoup:jsoup:1.11.2")
    implementation("com.squareup.okhttp3:okhttp:4.2.1")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("io.reactivex.rxjava2:rxkotlin:2.4.0-RC3")

    testImplementation("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}