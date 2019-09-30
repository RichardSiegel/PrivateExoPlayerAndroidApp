# PrivateExoPlayerAndroidApp #
This is a very simple Android-App streaming client. It utilizes ExoPlayer to display MPEG-DASH video streams of a referanced Media Presentation Description (MPD-file).

The Android-Studio project uses kotlin and the following files have already been modified for the base setup:

added in `app/src/main/AndroidManifest.xml`
```gradle
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.WAKE_LOCK"/>
```
Internet is needed to connect to the streaming source and the wake look is used to prevent the phone from turning off while a video is playing..

added in `app/build.gradle`
```gradle
dependencies {
    ...
    implementation 'com.google.android.exoplayer:exoplayer:2.10.5'
}
android {
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```
This includes [ExoPlayer by Google]: https://github.com/google/ExoPlayer
Also version problems are avoided by setting the JavaVersion.
In addition 'minSdkVersion 15' had to be changed to 'minSdkVersion 16'
