package com.example.mymultifragapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mymultifragapplication.databinding.ActivityMainBinding

val String.numOfkoreanCharacters: Int
    get() {
        var count1 = 0
        for (i in 0 until length) {
            if (this[i] >= 0xAC00.toChar() && this[i] <= 0xD7A3.toChar()) {
                count1 += 1
            }
        }

        return count1
    }

class MainActivity : AppCompatActivity() {
    var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        fun replaceFragment(frag: Fragment) {
            supportFragmentManager.beginTransaction().run {
                replace(binding.frmFrag.id, frag)
                commit()
            }
        }



        binding.run {
            btnInput.setOnClickListener {
                replaceFragment(InputFragment.newInstance())
            }
            btnResult.setOnClickListener {
                replaceFragment(ResultFragment.newInstance(text?.numOfkoreanCharacters ?: 0))
            }
        }


        supportFragmentManager.setFragmentResultListener("input_text", this) { _, bundle ->
            text = bundle.getString("input")
        }
        replaceFragment(InputFragment.newInstance())



        setContentView(binding.root)
    }
}