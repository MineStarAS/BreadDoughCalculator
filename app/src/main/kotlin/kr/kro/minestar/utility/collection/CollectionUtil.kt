package kr.kro.minestar.utility.collection

fun Collection<Any>.toStringList(): List<String> {
    val list = mutableListOf<String>()
    for (a in this) list.add(a.toString())
    return list.toList()
}
