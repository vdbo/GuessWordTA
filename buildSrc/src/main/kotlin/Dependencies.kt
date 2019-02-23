object Versions {
    const val gradleBuildTools = "3.3.1"
    const val kotlin = "1.3.21"
    const val androidMinSdk = 21
    const val androidTargetSdk = 28
    const val appCompat = "1.0.2"
    const val materialDesign = "1.0.0"
    const val dagger = "2.19"
    const val androidKtx = "1.0.0"
    const val retrofit = "2.5.0"
    const val rxJava2 = "2.2.0"
    const val rxKotlin = "2.3.0"
    const val rxAndroid = "2.1.0"
    const val archComponents = "2.0.0"
    const val junit = "4.12"
    const val mockk = "1.9"
}

object Dependencies {
    val kotlinLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val materialDesignLibrary = "com.google.android.material:material:${Versions.materialDesign}"
    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val androidKtx = "androidx.core:core-ktx:${Versions.androidKtx}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val retrofitRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val rxJava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxJava2}"
    val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    val archComponents = "androidx.lifecycle:lifecycle-extensions:${Versions.archComponents}"
    val archComponentsCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.archComponents}"
    val junit = "junit:junit:${Versions.junit}"
    val mockk = "io.mockk:mockk:${Versions.mockk}"
}