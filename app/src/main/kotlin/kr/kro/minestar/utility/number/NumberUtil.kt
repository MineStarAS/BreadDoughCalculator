package kr.kro.minestar.utility.number

import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.round
import kotlin.random.Random

/**
 * Int 에 3자리 수 마다 반점을 추가한 후 String 으로 출력합니다.
 */
fun Int.addComma(): String {
    return DecimalFormat("###,###").format(this)
}

/**
 * Long 에 3자리 수 마다 반점을 추가한 후 String 으로 출력합니다.
 */
fun Long.addComma(): String {
    return DecimalFormat("###,###").format(this)
}

/**
 * Double 을 백분율로 계산하여 확률에 맞게 Boolean 값을 출력합니다.
 */
fun Double.percent(): Boolean {
    if (this <= 0) return false
    if (this >= 1) return true
    val split = toString().split('.').last()
    val length = split.length.toDouble()
    val tureInt = split.toInt()
    val maxInt = 10.0.pow(length).toInt()
    val randomInt = Random.nextInt(maxInt)
    if (randomInt < tureInt) return true
    return false
}

/**
 * Double 을 length 값 길이 만큼 소수점을 남기고 반올림 합니다.
 */
fun Double.round(length: Int): Double {
    if (length <= 0) return toInt().toDouble()
    var up = 1
    for (int in 1..length) up *= 10
    return round((this * up)) / up
}