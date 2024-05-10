package com.clo.accloss.core.common

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
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

fun Navigator.levelPush(screen: Screen) {
    println(this.level)
    when (this.level) {
        1 -> this.push(screen)
        2 -> this.parent?.push(screen)
        3 -> this.parent?.parent?.push(screen)
    }
}
