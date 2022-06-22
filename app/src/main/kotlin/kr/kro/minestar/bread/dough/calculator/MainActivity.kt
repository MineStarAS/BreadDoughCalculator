package kr.kro.minestar.bread.dough.calculator

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kr.kro.minestar.utility.number.round
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent


class MainActivity : AppCompatActivity() {

    private enum class TextViewID(val ingredientID: Int, val percentID: Int, val weightID: Int, val multiplyID: Int) {
        CATEGORY(R.id.ingredientTextView0, R.id.percentTextView0, R.id.weightTextView0, R.id.multiplyTextView0),
        FLOUR(R.id.ingredientTextView1, R.id.percentTextView1, R.id.weightTextView1, R.id.multiplyTextView1),
        WATER(R.id.ingredientTextView2, R.id.percentTextView2, R.id.weightTextView2, R.id.multiplyTextView2),
        SALT(R.id.ingredientTextView3, R.id.percentTextView3, R.id.weightTextView3, R.id.multiplyTextView3),
        YEAST(R.id.ingredientTextView4, R.id.percentTextView4, R.id.weightTextView4, R.id.multiplyTextView4),
        OIL(R.id.ingredientTextView5, R.id.percentTextView5, R.id.weightTextView5, R.id.multiplyTextView5),
        EGG(R.id.ingredientTextView6, R.id.percentTextView6, R.id.weightTextView6, R.id.multiplyTextView6),
        SUGAR(R.id.ingredientTextView7, R.id.percentTextView7, R.id.weightTextView7, R.id.multiplyTextView7),
        POWDER_MILK(R.id.ingredientTextView8, R.id.percentTextView8, R.id.weightTextView8, R.id.multiplyTextView8),
        IMPROVER(R.id.ingredientTextView9, R.id.percentTextView9, R.id.weightTextView9, R.id.multiplyTextView9),
        TOTAL(R.id.ingredientTextView10, R.id.percentTextView10, R.id.weightTextView10, R.id.multiplyTextView10),
        ;
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        calculate()

        fun listenerEnable(id: Int) {
            val textView = getTextView(id)
            textView.setOnKeyListener { _, keyCode, keyEvent ->
                if (keyEvent.action != KeyEvent.ACTION_DOWN) return@setOnKeyListener false

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    val text = textView.text.toString().toDoubleOrNull() ?: 0.0
                    textView.text = text.removeLastZero()
                    calculate()
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(textView.windowToken, 0)
                }

                false
            }
        }

        fun totalListenerEnable(id: Int) {
            val textView = getTextView(id)
            textView.setOnKeyListener { _, keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    val text = textView.text.toString().toDoubleOrNull() ?: 0.0
                    textView.text = text.toString()

                    val totalPercent = getTextView(TextViewID.TOTAL.percentID).text.toString().toDoubleOrNull() ?: 0.0
                    val flourView = getTextView(TextViewID.FLOUR.weightID)
                    val calcFlour = ((text / totalPercent) * 100).round(1)
                    flourView.text = calcFlour.toString()
                    calculate()
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(textView.windowToken, 0)
                }
                false
            }
        }

        for (ids in TextViewID.values()) when (ids) {
            TextViewID.CATEGORY -> listenerEnable(ids.multiplyID)
            TextViewID.FLOUR -> listenerEnable(ids.weightID)
            TextViewID.TOTAL -> totalListenerEnable(ids.weightID)
            else -> listenerEnable(ids.percentID)
        }

        KeyboardVisibilityEvent.setEventListener(this) {
            calculate()
        }
    }

    private fun calculate() {
        val flourWeight = getValue(TextViewID.FLOUR.weightID)
        val multiplyValue = getValue(TextViewID.CATEGORY.multiplyID)
        for (ids in TextViewID.values()) {

            fun calcWeight() {
                val view = getTextView(ids.weightID)
                val weight = flourWeight * getValue(ids.percentID) / 100
                view.text = weight.round(1).removeLastZero()
            }

            fun multiplyCalc() {
                val view = getTextView(ids.multiplyID)
                val weightValue = getValue(ids.weightID)
                view.text = (multiplyValue * weightValue).round(1).removeLastZero()
            }

            fun calcTotalPercent() {
                var total = 0.0
                for (i in TextViewID.values())
                    if (i != TextViewID.TOTAL)
                        if (i != TextViewID.CATEGORY)
                            total += getValue(i.percentID)
                val view = getTextView(ids.percentID)
                view.text = total.round(1).removeLastZero()
            }

            fun calcTotalWeight() {
                var total = 0.0
                for (i in TextViewID.values())
                    if (i != TextViewID.TOTAL)
                        if (i != TextViewID.CATEGORY)
                            total += getValue(i.weightID)
                val view = getTextView(ids.weightID)
                view.text = total.round(1).removeLastZero()
            }

            fun calcTotalMultiply() {
                var total = 0.0
                for (i in TextViewID.values())
                    if (i != TextViewID.TOTAL)
                        if (i != TextViewID.CATEGORY)
                            total += getValue(i.multiplyID)
                val view = getTextView(ids.multiplyID)
                view.text = total.round(1).removeLastZero()
            }

            when (ids) {
                TextViewID.CATEGORY -> continue
                TextViewID.FLOUR -> multiplyCalc()
                TextViewID.TOTAL -> {
                    calcTotalPercent()
                    calcTotalWeight()
                    calcTotalMultiply()
                }
                else -> {
                    calcWeight()
                    multiplyCalc()
                }
            }
        }
    }

    private fun getTextView(id: Int) = findViewById<TextView>(id)
    private fun getValue(textView: TextView) = textView.text.toString().toDoubleOrNull() ?: 0.0
    private fun getValue(id: Int) = getTextView(id).text.toString().toDoubleOrNull() ?: 0.0
    private fun Double.removeLastZero(): String {
        val text = toString()
        val split = text.split('.')
        if (split.last() != "0") return text
        return split.first()
    }


    /**
     * 뒤로가기 기능
     */
    private val finishTime = 1000L
    private var pressTime = 0L

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - pressTime

        if (intervalTime in 0..finishTime) return finish()
        pressTime = tempTime
        Toast.makeText(applicationContext, "한번 더 누르시면 앱이 종료됩니다", Toast.LENGTH_SHORT).show()
    }
}
