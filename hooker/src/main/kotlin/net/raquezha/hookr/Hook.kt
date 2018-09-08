package net.raquezha.hookr

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.annotation.NonNull

object Hook {
    @JvmStatic
    fun up(context: Context): Hookers {
        return Hookers(context)
    }
}

class Hookers(var ctx: Context) {

    fun withMessenger(@NonNull facebookUserId: String) {
        startHooking(Hookrs.MESSENGER, facebookUserId, null)
    }

    fun withViber(@NonNull mobileNumber: String) {
        startHooking(Hookrs.VIBER, mobileNumber)
    }

    fun withSMS(@NonNull mobileNumber: String) {
        startHooking(Hookrs.MESSAGES, null, mobileNumber)
    }

    fun withPhoneCall(@NonNull mobileNumber: String) {
        startHooking(Hookrs.PHONE_CALL, null, mobileNumber)
    }

    fun withWhatsApp(@NonNull mobileNumber: String) {
        startHooking(Hookrs.PHONE_CALL, null, mobileNumber)
    }

    private fun startHooking(app: Hookrs, mobileNumber: String) {
        startHooking(app, null, mobileNumber)
    }

    private fun startHooking(app: Hookrs, facebookUserId: String?, mobileNumber: String?) {
        try {
            ctx.startActivity(getHookingIntent(app, facebookUserId, mobileNumber))

        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(ctx, "Please Install ${app.appName}", Toast.LENGTH_LONG).show()
        }
    }

    private fun getHookingIntent(app: Hookrs, facebookUserId: String?, mobileNumber: String?): Intent {
        val intent = Intent(app.intent)
        when (app) {
            Hookrs.VIBER -> {
                checkNotNull(mobileNumber) {
                    throw NullPointerException("Mobile Number is required.")
                }
                intent.data = Uri.parse("tel:" + Uri.encode(mobileNumber))
                intent.setClassName(app.appId, "com.viber.voip.WelcomeActivity")
            }
            Hookrs.MESSAGES -> {
                checkNotNull(mobileNumber) {
                    throw NullPointerException("Mobile Number is required.")
                }
                intent.data = Uri.parse("smsto:$mobileNumber")
            }
            Hookrs.WHATSAPP -> {
                checkNotNull(mobileNumber) {
                    throw NullPointerException("Mobile Number is required.")
                }
                intent.data = Uri.parse("https://wa.up/$mobileNumber")

            }
            Hookrs.MESSENGER -> {
                checkNotNull(facebookUserId) {
                    throw NullPointerException("Facebook User ID is required.")
                }
                intent.data = Uri.parse("fb-messenger://user/$facebookUserId")
            }
            Hookrs.PHONE_CALL -> {
                checkNotNull(mobileNumber) {
                    throw NullPointerException("Mobile Number is required.")
                }
                intent.data = Uri.parse("tel:$mobileNumber")
            }
        }
        return intent
    }
}


enum class Hookrs(var intent: Intent, var appName: String, var appId: String) {
    VIBER(Intent(Intent.ACTION_VIEW), "Viber", "com.viber.voip"),
    MESSENGER(Intent(Intent.ACTION_VIEW), "Facebook Messenger", ""),
    WHATSAPP(Intent(Intent.ACTION_VIEW), "Whatsapp", ""),
    MESSAGES(Intent(Intent.ACTION_SENDTO), "Message", ""),
    PHONE_CALL(Intent(Intent.ACTION_DIAL), "Phone", "");
}

