package kr.kro.minestar.utility.bool

class BooleanScript(val boolean: Boolean, val script: String) {
    companion object {
        fun Boolean.addScript(script: String): BooleanScript {
            return BooleanScript(this, script)
        }

        fun Boolean.addScript(): BooleanScript {
            return BooleanScript(this, "")
        }
    }
}