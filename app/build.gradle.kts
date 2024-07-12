plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.vst.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vst.myapplication"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        dataBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        exclude ("META-INF/DEPENDENCIES")
        exclude ("META-INF/LICENSE")
        exclude ("META-INF/LICENSE.txt")
        exclude ("META-INF/license.txt")
        exclude ("META-INF/NOTICE")
        exclude ("META-INF/NOTICE.txt")
        exclude ("META-INF/notice.txt")
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.room.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.converter.gson)
    implementation ("com.squareup.retrofit2:retrofit:2.1.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:3.9.1")
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-auth")
    implementation ("eu.agno3.jcifs:jcifs-ng:2.1.6")
    implementation ("androidx.multidex:multidex:2.0.1")
//    implementation ("org.apache.httpcomponents:httpclient:4.5.13")
//    implementation ("org.simpleframework:simple-xml:2.7.1")
//    implementation ("com.squareup.retrofit2:converter-simplexml:2.9.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation ("commons-codec:commons-codec:1.15")
    implementation ("org.apache.httpcomponents:httpclient:4.5.13")
    implementation ("org.apache.httpcomponents:httpclient-win:4.5.13")
    implementation ("androidx.room:room-runtime:2.5.2")
    annotationProcessor ("androidx.room:room-compiler:2.5.2")
    implementation ("com.facebook.shimmer:shimmer:0.5.0")
    implementation ("com.razorpay:checkout:1.6.38")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:core:3.3.0")
//    implementation ("org.codelibs:jodd-http:3.9.0")

}