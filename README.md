# Sample android app
Sample app demonstrating video playback using the vdocipher android sdk

### Add dependency

```
// use the latest available version
implementation 'com.vdocipher.aegis:vdocipher-android:1.8.1'
```

### Add cast plugin dependency

If you also need Google Cast integration for your app, add a dependency to the cast plugin as well.

Add the dependency in your cast app module's `build.gradle` file.

```
def vdocipher_sdk_version = '1.8.1'
```

```
implementation 'com.vdocipher.aegis:vdocipher-android:' + vdocipher_sdk_version
implementation 'com.vdocipher.aegis:vdocipher-cast:' + vdocipher_sdk_version
implementation 'com.google.android.gms:play-services-cast-framework:16.2.0'
```
## Issues

Please send all issues and feedback to support@vdocipher.com
