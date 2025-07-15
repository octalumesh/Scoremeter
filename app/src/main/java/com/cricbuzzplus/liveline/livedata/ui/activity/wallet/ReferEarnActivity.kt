package com.cricbuzzplus.liveline.livedata.ui.activity.wallet

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.BuildConfig
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivityReferEarnBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import com.cricbuzzplus.liveline.mPrefs

class ReferEarnActivity : BaseActivity() {

    lateinit var binding: ActivityReferEarnBinding
    private lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refer_earn)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@ReferEarnActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()

        binding.referralCode.setText(""+mPrefs.prefUserDetails?.referId)

        binding.copyCode.setOnClickListener {
            copyToClipboard(binding.referralCode.text.toString())
        }

        binding.referAmount.setText("Refer to your friend and Get ₹${mPrefs.prefUserDetails?.prize?.referAmount}\n Extra Cash money")


        binding.refer.setOnClickListener(View.OnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                var shareMessage = "\nLet me recommend you this application\n\n"
                shareMessage =
                    """
                    ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                    
                    
                    """.trimIndent()

                if (mPrefs.prefUserDetails != null && !mPrefs?.prefUserDetails?.referId.isNullOrEmpty()){
                    shareMessage = "${shareMessage}\n Use Refer Code:  ${mPrefs?.prefUserDetails?.referId}"
                }

                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                //e.toString();
            }
        })

        binding.back.setOnClickListener {
            onBackPressed()
        }

    }


    private fun copyToClipboard(text: String) {
        val clipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("ReferralCode", text)
        clipboardManager.setPrimaryClip(clipData)

        showToast("Copied to clipboard")
    }


    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }

}