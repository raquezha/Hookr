package net.raquezha.hookr

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.NonNull


object Hook {
    @JvmStatic
    fun up(context: Context): Hookers {
        return Hookers(context)
    }
}

class Hookers(@NonNull var ctx: Context) {
    var invalidMobileNumber = "Invalid mobile number"
    var requiredMobileNumber = "Mobile Number is required"
    var requiredfacebookId = "Facebook ID is required"

    fun withMessenger(@NonNull facebookUserId: String, errorMsg: ((String) -> Unit)? = null) {
        startHooking(Hookrs.MESSENGER, facebookUserId, null, errorMsg)
    }

    fun withViber(@NonNull mobileNumber: String, errorMsg: ((String) -> Unit)? = null) {
        startHooking(Hookrs.VIBER, mobileNumber, errorMsg)
    }

    fun withSMS(@NonNull mobileNumber: String, errorMsg: ((String) -> Unit)? = null) {
        startHooking(Hookrs.MESSAGES, null, mobileNumber, errorMsg)
    }

    fun withPhoneCall(@NonNull mobileNumber: String, errorMsg: ((String) -> Unit)? = null) {
        startHooking(Hookrs.PHONE_CALL, null, mobileNumber, errorMsg)
    }

    fun withWhatsApp(@NonNull mobileNumber: String, errorMsg: ((String) -> Unit)? = null) {
        startHooking(Hookrs.WHATSAPP, null, mobileNumber, errorMsg)
    }

    private fun startHooking(app: Hookrs, mobileNumber: String, errorMsg: ((String) -> Unit)? = null) {
        startHooking(app, null, mobileNumber, errorMsg)
    }

    private fun startHooking(app: Hookrs, facebookUserId: String?, mobileNumber: String?, errorMsg: ((String) -> Unit)? = null) {
        try {
            val intent = getHookingIntent(app, facebookUserId, mobileNumber, errorMsg)
            if (intent != null)
                ctx.startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            errorMsg?.invoke("Please Install ${app.appName}")

        }
    }

    private fun getHookingIntent(app: Hookrs, facebookUserId: String?, mobileNumber: String?, listener: ((String) -> Unit)? = null): Intent? {
        val intent = Intent(app.intent)
        when (app) {
            Hookrs.VIBER -> {
                checkNotNull(mobileNumber) {
                    throw NullPointerException(requiredMobileNumber)
                }
                if (isValidMobileNumber(mobileNumber)) {
                    intent.data = Uri.parse("tel:" + Uri.encode(mobileNumber))
                    intent.setClassName(app.appId, "com.viber.voip.WelcomeActivity")
                } else {
                    listener?.invoke(invalidMobileNumber)
                    return null
                }
            }
            Hookrs.MESSAGES -> {
                checkNotNull(mobileNumber) {
                    throw NullPointerException(requiredMobileNumber)
                }
                if (isValidMobileNumber(mobileNumber)) {
                    intent.data = Uri.parse("smsto:$mobileNumber")
                } else {
                    listener?.invoke(requiredMobileNumber)
                    return null
                }
            }
            Hookrs.WHATSAPP -> {
                checkNotNull(mobileNumber) {
                    throw NullPointerException(requiredMobileNumber)
                }
                if (isValidMobileNumber(mobileNumber)) {
                    intent.data = Uri.parse("https://wa.up/$mobileNumber")
                } else {
                    listener?.invoke(requiredMobileNumber)
                    return null
                }
            }
            Hookrs.MESSENGER -> {
                checkNotNull(facebookUserId) {
                    throw NullPointerException(requiredfacebookId)
                }
                if (facebookUserId!!.isEmpty()) {
                    listener?.invoke(requiredfacebookId)
                    return null
                } else
                    intent.data = Uri.parse("fb-messenger://user/$facebookUserId")

            }
            Hookrs.PHONE_CALL -> {
                checkNotNull(mobileNumber) {
                    throw NullPointerException(requiredMobileNumber)
                }
                if (isValidMobileNumber(mobileNumber)) {
                    intent.data = Uri.parse("tel:$mobileNumber")
                } else {
                    return null
                    listener?.invoke(requiredMobileNumber)
                }
            }
        }
        return intent
    }

    private fun isValidMobileNumber(mobileNumber: String?): Boolean {
        var isValid = true
        if (mobileNumber!! == "") isValid = false
        return isValid
    }
}


enum class Hookrs(var intent: Intent, var appName: String, var appId: String) {
    VIBER(Intent(Intent.ACTION_VIEW), "Viber", "com.viber.voip"),
    MESSENGER(Intent(Intent.ACTION_VIEW), "Facebook Messenger", ""),
    WHATSAPP(Intent(Intent.ACTION_VIEW), "Whatsapp", ""),
    MESSAGES(Intent(Intent.ACTION_SENDTO), "Message", ""),
    PHONE_CALL(Intent(Intent.ACTION_DIAL), "Phone", "");
}

