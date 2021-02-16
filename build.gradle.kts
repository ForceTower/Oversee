plugins {
    java
    kotlin("jvm") version "1.4.30"
    maven
    `maven-publish`
    signing
}

group = "dev.forcetower.unes"
version = "1.1.0"

repositories {
    mavenCentral()
}

val sourcesJar = task<Jar> ("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar = task<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    val task = project.tasks["javadoc"] as Javadoc
    from(task.destinationDir)
    dependsOn(task)
}

artifacts {
    archives(sourcesJar)
    archives(javadocJar)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "dev.forcetower.unes"
            artifactId = "oversee"
            version = "1.1.0"

            pom {
                name.set("Oversee")
                description.set("Searches for news at the university")
                url.set("http://github.com/ForceTower/Oversee")
                from(components["java"])

                licenses {
                    license {
                        name.set("General Public License v3.0")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.html")
                    }
                }

                developers {
                    developer {
                        id.set("forcetower")
                        name.set("João Paulo Sena")
                        email.set("joaopaulo761@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/ForceTower/Oversee.git")
                    developerConnection.set("scm:git:ssh://github.com/ForceTower/Oversee.git")
                    url.set("http://unes.forcetower.dev/oversee")
                }
            }

            artifact(sourcesJar) {
                classifier = "sources"
            }

            artifact(javadocJar) {
                classifier = "javadoc"
            }
        }
    }

    repositories {
        maven {
            val sonatypeUsername: String by project
            val sonatypePassword: String by project
            setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            name = "maven"
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}

dependencies {
    implementation("org.jsoup:jsoup:1.11.2")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.google.code.gson:gson:2.8.6")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.9")

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