package kr.kro.minestar.utility.unit

/**
 * Unit 대신 T 를 반환하게 설정합니다.
 */
fun <T> Unit.set(any: T) = any

/**
 * Unit 대신 true 값을 반환합니다.
 */
fun Unit.setTrue() = true

/**
 * Unit 대신 false 값을 반환합니다.
 */
fun Unit.setFalse() = false
