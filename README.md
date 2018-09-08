
# Hookr (Kotlin)
I use this in my applications and I thought I just make it into a library.
Simple Utility that hook up your app to this various external applications:

1. Inbox (SMS)
1. Phone
1. Facebook Messenger
1. Viber
1. Whatsapp

This is usually used in Help/Support section where you link your app to external apps.

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

In case something went wrong you can set a callback, just call the lambda and `it` will be the error message.
```kotlin
Hook.up(context).withSMS(sampleNumber) {
  toast(it)
}
```
