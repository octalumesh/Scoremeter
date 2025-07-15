package com.cricbuzzplus.liveline.livedata.ui.activity

import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.BuildConfig
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityNewsDetailBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.PlayersViewModel
import java.text.SimpleDateFormat
import java.util.*

class NewsDetailActivity : BaseActivity() {

    lateinit var binding: ActivityNewsDetailBinding
    private lateinit var viewModel: PlayersViewModel

    var newsId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail)
        viewModel = ViewModelProvider(this).get(PlayersViewModel::class.java)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@NewsDetailActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObserver()



        newsId = intent.getIntExtra("newsId", 0)

        binding.back.setOnClickListener {
            finish()
        }


        binding.share.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                shareNews()
            }else{
                premissionAllowCls()
            }

        }

        callPlayerInfo()

    }

    private fun premissionAllowCls() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (applicationContext.checkSelfPermission(permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                shareNews()
            } else {
                requestPermissions(arrayOf(permission.WRITE_EXTERNAL_STORAGE), 1536)
            }
        } else {
            shareNews()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1536) {
            shareNews()
        }
    }


    fun shareNews() {
        try {
            val bitmapDrawable = binding.newsImage.getDrawable() as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            val intent = Intent(Intent.ACTION_SEND)
            val bitmapPath: String =
                MediaStore.Images.Media.insertImage(contentResolver, bitmap, binding.title.text.toString(), binding.title.text.toString())
            val bitmapUri = Uri.parse(bitmapPath)
            intent.type = "image/*"
            //intent.putExtra("description",niyamList.get(0).description.toString())
            var shareMessage = "\n${binding.title.text.toString()}\n\n"
            shareMessage =
                """
                    ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                    
                    
                    """.trimIndent()
            intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
            intent.putExtra(
                Intent.EXTRA_TEXT, shareMessage
            )
            startActivity(Intent.createChooser(intent, "Share News"))
        } catch (e: java.lang.Exception) {
            Log.e("shareNews", "shareNews: " + e.message)
        }
    }



    fun callPlayerInfo() {

        if (newsId != 0) {
            if (isInternetConnection()) {
                viewModel.getNewsDetails(newsId)
            }
        }
    }

    private fun setObserver() {

        observeExtras()
        observePlayerInfo()

    }

    private fun observePlayerInfo() {

        viewModel.newsDetailsLiveData.observe(this, Observer {

            if (it != null) {

                binding.parent.visibility = View.VISIBLE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.GONE

                try {

                    val requestOptions = RequestOptions()
                    requestOptions.placeholder(R.mipmap.ic_launcher)
                    requestOptions.error(R.mipmap.ic_launcher)


                    val image =
                        "https://api2.cricbuzz.com/a/img/v1/i1/c${it.coverImage?.id}/i.jpg?p=det&d=high"


                    Glide.with(this).load(image).placeholder(R.drawable.custom_progress)
                        .apply(requestOptions).into(binding.newsImage)


                    binding.title.setText("" + it.headline)

                    try {

                        if (!it.publishTime.isNullOrEmpty()) {

                            var time = convertTimestampToRelativeDate(it.publishTime.toString().toLong())


                            binding.dateTime.setText("" + time)

                        }

                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }

                    if (!it.authors.isNullOrEmpty()){
                       binding.authors.setText("${it.authors.get(0)?.name.toString()}")
                    }


                    var content = ""
                    for (con in it.content!!) {
                        if (!con?.content?.contentValue.isNullOrEmpty()) {
                            content = content + "<br>"+ "<br>" + con?.content?.contentValue.toString()
                        }

                        if (con?.content?.hasFormat != null && con?.content?.hasFormat == true) {

                            if (!it.format.isNullOrEmpty()) {

                                for (format in it.format) {
                                    if (format != null) {
                                        if (format?.type.equals("links")) {
                                            if (!format?.value.isNullOrEmpty()) {
                                                for (values in format.value!!) {
                                                    if (content.contains(values?.id.toString())!!) {

                                                        content = content?.replace(
                                                            values?.id.toString(),
                                                            "<font color='blue'><a href='https://example.com'>${values?.value.toString()}</a></font>"
                                                        ).orEmpty()
                                                    }
                                                }
                                            }
                                        } else if (format?.type.equals("italic")) {
                                            if (!format?.value.isNullOrEmpty()) {
                                                for (values in format.value!!) {
                                                    if (content.contains(values?.id.toString())!!) {

                                                        content = content?.replace(
                                                            values?.id.toString(),
                                                            "<i>" + values?.value.toString() + "</i>"
                                                        ).orEmpty()
                                                    }
                                                }
                                            }
                                        } else if (format?.type.equals("bold")) {
                                            if (!format?.value.isNullOrEmpty()) {
                                                for (values in format.value!!) {
                                                    if (content.contains(values?.id.toString())!!) {

                                                        content = content?.replace(
                                                            values?.id.toString(),
                                                            "<b>" + values?.value.toString() + "</b>"
                                                        ).orEmpty()
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }

                        }


                        if (content.contains("\"")){
                            content.replace("\"","")
                        }

                    }


                    binding.content.setText(
                        HtmlCompat.fromHtml(
                            content,
                            HtmlCompat.FROM_HTML_MODE_COMPACT
                        )
                    )


                } catch (e: Exception) {


                }


            } else {
                binding.parent.visibility = View.GONE
                binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
            }

        })

    }

    fun convertTimestampToRelativeDate(timestamp: Long): String {
        val currentTimeMillis = System.currentTimeMillis()
        val timestampMillis = timestamp.toLong()

        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        val differenceMillis = currentTimeMillis - timestampMillis
        val daysDifference = differenceMillis / (1000 * 60 * 60 * 24)

        return if (daysDifference <= 7) {
            if (daysDifference <= 1) {
                "Today"
            } else {
                "${daysDifference.toInt()} days ago"
            }
        } else {
            dateFormat.format(Date(timestampMillis))
        }
    }


    private fun observeExtras() {
        viewModel.loaderLiveData.observe(this, Observer
        { isLoading -> handleProgressLoader(isLoading!!) })
        viewModel!!.dataLoadError.observe(this, Observer { s ->
            Log.e("TAG", "onChanged: $s")
            handleError(s.toString())
        })
    }

}