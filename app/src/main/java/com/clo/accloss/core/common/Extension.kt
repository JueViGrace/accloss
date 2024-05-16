package com.clo.accloss.core.common

import android.util.Log
import com.clo.accloss.core.common.Constants.HUNDRED_DOUBLE
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

fun Date.toStringFormat(): String =
    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(this)

fun String.toDate(): Date =
    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(this) as Date

fun Double.roundFormat(format: Int? = null): String {
    val num = (this * HUNDRED_DOUBLE).roundToInt() / HUNDRED_DOUBLE

    return when (format) {
        0 -> DecimalFormat("#,##0").format(num)
        1 -> DecimalFormat("#,##0.00").format(num)
        else -> DecimalFormat("#,##0.00").format(num)
    }
}

fun Throwable.log(tag: String) =
    Log.e(
        tag,
        """
            Message: ${this.message}\n
            Localized Message: ${this.localizedMessage}
        """.trimIndent()
    )
