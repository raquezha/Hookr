

# Hookr (Kotlin)
I use this in my applications and I thought I just make it into a library.<br>
Simple Utility that hook up your app to this external applications:

1. Inbox (SMS)
1. Phone
1. Facebook Messenger
1. Viber
1. Whatsapp

This is usually used in Help/Support section where you link your app to external apps.

# How to

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Step 2. Add the dependency
```gradle
dependencies {
  implementation "com.github.raquezha:hookr:1.2.0.2"
}
```

That's it! In case of a build failed, try cleaning or rebuilding your project. If all else fails try to Invalidate Cache and Restart option

## Usage

```kotlin
var sampleNumber = "+639123456780"
var facebookId = "1231232131"
```
### Inbox (SMS)

```kotlin
Hook.up(context).withSMS(sampleNumber)
```

### Phone

```kotlin
Hook.up(context).withPhoneCall(sampleNumber)
```

### Facebook Messenger

```kotlin
Hook.up(context).withMessenger(facebookId)
```
Can't find your facebook ID? use this: https://findmyfbid.com/

### Viber

```kotlin
Hook.up(context).withViber(facebookId)
```

### Whatsapp

```kotlin
Hook.up(context).withWhatsApp(facebookId)
```

### Error Callback

In case something went wrong you can set a callback,
<br>just call the lambda and `it` will be the error message.
```kotlin
Hook.up(context).withSMS(sampleNumber) {
  ...
  Log.e("Hookr", "error: $it")
}
```
