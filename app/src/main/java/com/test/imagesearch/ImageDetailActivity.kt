package com.test.imagesearch

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer

import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.bumptech.glide.Glide
import com.test.imagesearch.models.MasterData
import com.test.imagesearch.repositories.database.DatabaseClient
import com.test.imagesearch.viewmodel.MainActivityViewModel

class ImageDetailActivity : AppCompatActivity() {


    lateinit var mImageViewModel: MainActivityViewModel
    lateinit var iv_display: ImageView
    lateinit var et_comment: EditText
    lateinit var btn_save: Button
    lateinit var id: String

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        id = intent.getStringExtra("id")

        iv_display = findViewById(R.id.iv_display)
        et_comment = findViewById(R.id.et_comment)
        btn_save = findViewById(R.id.btn_save)


        toolbar = findViewById(R.id.toolbar)

        mImageViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)


        mImageViewModel.init(applicationContext)

        mImageViewModel.getSingdleData(id).observe(this@ImageDetailActivity, Observer { data ->

            if (data != null) {
                setData(data)
            }
        })

        btn_save.setOnClickListener {


            if (TextUtils.isEmpty(et_comment.text.toString())) {
                Toast.makeText(this@ImageDetailActivity, "Enter Comment", Toast.LENGTH_SHORT).show()
            } else {
                updateComment(et_comment.text.toString()).execute()
            }
        }


        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setData(data: List<MasterData>) {

        Glide.with(this@ImageDetailActivity)
                .load(data[0].images[0].link)
                .override(500, 600)
                .into(iv_display)

        et_comment.setText(data[0].user_comment)

        toolbar.title = data[0].title

    }


    internal inner class updateComment(var local_comment: String) : AsyncTask<Void, Void, Void>() {


        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg voids: Void): Void? {


            DatabaseClient
                    .getInstance(this@ImageDetailActivity)
                    .appDatabase
                    .dataDao()
                    .updateComment(local_comment, id)



            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            Toast.makeText(this@ImageDetailActivity,"Comment Saved",Toast.LENGTH_SHORT).show()
        }
    }
}
