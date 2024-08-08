import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

//fun getApiKey(propertyKey:String):String{
//    return gradleLocalProperties(rootDir).getProperty(propertyKey)
//}

fun getKakaoAPiKey(propertyKey:String):String{
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}



val properties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}



plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

    //id("com.google.gms.google-services")

}

android {
    namespace = "com.example.capstoneapp"
    compileSdk = 34

    defaultConfig {
      manifestPlaceholders += mapOf()
        //  buildConfigField("String","google_map_key",getApiKey("google_map_key"))
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", getKakaoAPiKey("KAKAO_NATIVE_APP_KEY"))
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = getKakaoAPiKey("KAKAO_NATIVE_APP_KEY")
        applicationId = "com.example.capstoneapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = properties["KAKAO_NATIVE_APP_KEY"] as String
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/io.netty.versions.properties"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/LICENSE"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.3")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation("androidx.navigation:navigation-runtime-ktx:2.7.7")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("androidx.navigation:navigation-compose:2.7.7")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.3")


    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-gcm:17.0.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation("com.google.firebase:firebase-database-ktx:20.3.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    // Google Cloud Text-to-Speech API
    implementation("com.google.cloud:google-cloud-texttospeech:1.3.0")
    implementation("com.google.api-client:google-api-client-android:1.31.5")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.2.1")

    // gRPC dependencies
    implementation("io.grpc:grpc-okhttp:1.47.0")
    implementation("io.grpc:grpc-protobuf:1.47.0")
    implementation("io.grpc:grpc-stub:1.47.0")
    implementation("io.grpc:grpc-auth:1.47.0")
    implementation("io.grpc:grpc-netty-shaded:1.47.0")

    implementation("com.kakao.sdk:v2-all:2.20.1") // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation("com.kakao.sdk:v2-user:2.20.1") // 카카오 로그인 API 모듈
    implementation("com.kakao.sdk:v2-share:2.20.1") // 카카오톡 공유 API 모듈
    implementation("com.kakao.sdk:v2-talk:2.20.1") // 카카오톡 채널, 카카오톡 소셜, 카카오톡 메시지 API 모듈
    implementation("com.kakao.sdk:v2-friend:2.20.1") // 피커 API 모듈
    implementation("com.kakao.sdk:v2-navi:2.20.1") // 카카오내비 API 모듈
    implementation("com.kakao.sdk:v2-cert:2.20.1") // 카카오톡 인증 서비스 API 모듈

    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation( "com.google.accompanist:accompanist-pager:0.30.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.30.0")



    implementation("androidx.multidex:multidex:2.0.1")
}