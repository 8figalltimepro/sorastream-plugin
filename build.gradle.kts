import com.lagradost.cloudstream3.gradle.CloudstreamExtension
import com.android.build.gradle.BaseExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        // Shitpack repo which contains our tools and dependencies
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        // Cloudstream gradle plugin which makes everything work and builds plugins
        classpath("com.github.recloudstream:gradle:master-SNAPSHOT")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

fun Project.cloudstream(configuration: CloudstreamExtension.() -> Unit) = extensions.getByName<CloudstreamExtension>("cloudstream").configuration()

fun Project.android(configuration: BaseExtension.() -> Unit) = extensions.getByName<BaseExtension>("android").configuration()

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "com.lagradost.cloudstream3.gradle")

    cloudstream {
        // when running through github workflow, GITHUB_REPOSITORY should contain current repository name
        setRepo(System.getenv("GITHUB_REPOSITORY") ?: "https://github.com/8figalltimepro/sorastream-plugin")

        authors = listOf("8Figalltimeprhjjo")
    }

    android {
        compileSdkVersion(33)

        defaultConfig {
            minSdk = 21
            targetSdk = 33

//            val properties = Properties()
//            properties.load(project.rootProject.file("local.properties").inputStream())
//
//            buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")

        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8" // Required
                // Disables some unnecessary features
                freeCompilerArgs = freeCompilerArgs +
                        "-Xno-call-assertions" +
                        "-Xno-param-assertions" +
                        "-Xno-receiver-assertions"
            }
        }
    }

    dependencies {
        val apk by configurations
        val implementation by configurations

        // Stubs for all Cloudstream classes
        apk("com.lagradost:cloudstream3:pre-release")

        // these dependencies can include any of those which are added by the app,
        // but you dont need to include any of them if you dont need them
        // https://github.com/recloudstream/cloudstream/blob/master/app/build.gradle
        implementation(kotlin("stdlib")) // adds standard kotlin features, like listOf, mapOf etc
        implementation("com.github.Blatzar:NiceHttp:0.4.1") // http library
        implementation("org.jsoup:jsoup:1.15.3") // html parser
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
        implementation("io.karn:khttp-android:0.1.2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4") // html parser
        //run JS
        implementation("org.mozilla:rhino:1.7.14")
        implementation("com.google.code.gson:gson:2.8.9")

    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
