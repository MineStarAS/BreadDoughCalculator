package kr.kro.minestar.utility.string

/**
 * String 을 콘솔에 출력합니다.
 */
fun String.toConsole() = println(this)

/**
 * String 에 있는 공백을 밑줄로 치환합니다.
 */
fun String.setUnderBar(): String {
    return this.replace(' ', '_')
}

/**
 * String 에 있는 밑줄을 공백으로 치환합니다.
 */
fun String.removeUnderBar(): String {
    return this.replace('_', ' ')
}

/**
 * String 에 포함된 문자 중 입력된 문자와 같은 문자를 모두 삭제합니다.
 */
fun String.remove(char: Char): String {
    return this.replace(char.toString(), "")
}

/**
 * String 에 포함된 문자 중 입력된 문자열에 해당 되는 부분들을 모두 삭제합니다.
 */
fun String.remove(string: String): String {
    return this.replace(string, "")
}

/**
 * String 에 포함된 문자 중 파일명으로 사용 할 수 없는 문자를 삭제 합니다.
 */
fun String.convertFileName(): String {
    return this.remove('/').remove("\\").remove(':').remove('?').remove('*').remove('<').remove('>').remove('|').remove("\"")
}