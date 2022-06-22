package kr.kro.minestar.utility.array

import java.io.File
import java.util.*

fun Array<File>.sortFileList(): Array<File> {
    Arrays.sort(this) { object1, object2 ->
        val s1 : String = (object1 as File).lastModified().toString()
        val s2 : String = (object2 as File).lastModified().toString()
        s1.compareTo(s2)
    }
    return this
}