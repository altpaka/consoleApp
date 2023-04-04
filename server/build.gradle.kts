plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
}

repositories {
    mavenCentral()
//    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
//    maven(url = "https://jitpack.io/")
}

kotlin {
    target {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
            compileJavaTaskProvider.get().options.encoding = "UTF-8"
        }
    }

    sourceSets {
        val main by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.postgresql:postgresql:42.2.10")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.3")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
                implementation(project(":common"))
                implementation(project(":client"))
            }
        }
        val test by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

application {
    mainClass.set("com.github.altpaka.consoleApp.manage.MainServerKt")
}

val fatJar = tasks.create<Jar>("fatJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks["build"].dependsOn(fatJar)