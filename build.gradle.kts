plugins {
    java
    kotlin("jvm") version "2.1.21"
    `maven-publish`
    signing
}

group = "dev.forcetower.unes"
version = "1.2.0"

repositories {
    mavenCentral()
}

val sourcesJar = tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    val task = project.tasks["javadoc"] as Javadoc
    from(task.destinationDir)
    dependsOn(task)
}

artifacts {
    archives(sourcesJar.get())
    archives(javadocJar.get())
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
            val sonatypeUsername = System.getenv("MAVEN_CENTRAL_USERNAME") ?: System.getenv("sonatypeUsername")
            val sonatypePassword = System.getenv("MAVEN_CENTRAL_PASSWORD") ?: System.getenv("sonatypePassword")
            setUrl("https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/")
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
    implementation("org.jsoup:jsoup:1.15.3")
    implementation("com.squareup.okhttp3:okhttp:5.1.0")
    implementation("com.google.code.gson:gson:2.13.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")

    testImplementation(kotlin("test-junit"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of("17"))
    }
}