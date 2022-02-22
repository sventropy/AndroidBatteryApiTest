# AndroidBatteryApiTest

From Android 8 and above, certain actions like `Intent.ACTION_POWER_DISCONNECTED` & 
`Intent.ACTION_POWER_CONNECTED`, can now longer be listened to through regular broadcast 
receivers (see [the docs](https://developer.android.com/guide/components/broadcast-exceptions). 

This PoC app implements the same behavior through a foreground service.

Tested on:
- Android 10.0 device
- Android 9.0 emulator