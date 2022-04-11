package com.example.downloadpicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mEditText: EditText
    private lateinit var mImageView: ImageView
    private lateinit var mButton: Button
    private lateinit var mFailToast: Toast

    private val sPicasso = Picasso.get()
    private val sRandom = Random()
    private val sURLSample: String = "https://picsum.photos/id/%d/%d/%d"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mEditText = findViewById(R.id.main_edittext)
        mImageView = findViewById(R.id.main_imageview)
        mButton = findViewById(R.id.btn_get_random)
        mFailToast =
            Toast.makeText(this, getText(R.string.toast_fail_text), Toast.LENGTH_SHORT)

        mButton.setOnClickListener {
            val randomURl = String.format(
                sURLSample, getRandomPicId(),
                getRandomPicWidth(),
                getRandomPicHeight()
            )
            mEditText.text = Editable.Factory.getInstance().newEditable(randomURl)
        }

        mEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d("12345", "afterTextChanged - $p0")
                p0?.let {
                    if (p0.isNotBlank()) {
                        sPicasso.load(p0.toString().trim().toUri())
                            .into(mImageView, object : com.squareup.picasso.Callback {
                                override fun onSuccess() {
                                }

                                override fun onError(e: Exception?) {
                                    mFailToast.show()
                                }
                            })
                    }
                }
            }
        })
    }

    private fun getRandomPicWidth(): Int = (sRandom.nextInt(5) + 1) * 100

    private fun getRandomPicHeight(): Int = (sRandom.nextInt(4) + 6) * 100

    private fun getRandomPicId(): Int = sRandom.nextInt(1050) + 1
}