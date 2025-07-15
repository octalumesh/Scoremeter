package com.cricbuzzplus.liveline.livedata.ui.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivitySettingsBinding
import com.cricbuzzplus.liveline.databinding.ActivitySignInBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import com.cricbuzzplus.liveline.mPrefs

class SettingsActivity : BaseActivity() {

    lateinit var viewModel: UsersViewModel
    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@SettingsActivity, R.color.colorPrimaryDark)
        }

        /* val theme = PreferenceManager.getDefaultSharedPreferences(this)
             .getString("theme", "light")

         when (theme) {
             "light" -> setTheme(R.style.AppTheme_Light)
             "dark" -> setTheme(R.style.AppTheme_Dark)
         }

         setContentView(R.layout.activity_main)*/

        /* val changeThemeButton = findViewById<Button>(R.id.changeThemeButton)
         changeThemeButton.setOnClickListener {
             // Change the theme and recreate the activity
             val newTheme = if (theme == "light") "dark" else "light"
             PreferenceManager.getDefaultSharedPreferences(this)
                 .edit()
                 .putString("theme", newTheme)
                 .apply()

             AppCompatDelegate.setDefaultNightMode(
                 if (newTheme == "light") AppCompatDelegate.MODE_NIGHT_NO
                 else AppCompatDelegate.MODE_NIGHT_YES
             )

             recreate()
         }*/

        binding.back.setOnClickListener {
            onBackPressed()
        }

        if (mPrefs.prefDarkCheck) {
            binding.switchDark.isChecked = true
        } else {
            binding.switchDark.isChecked = false
        }


        binding.switchDark.setOnClickListener {

            if (binding.switchDark.isChecked) {


                mPrefs.prefDarkCheck = true

                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )

                recreate()

            } else {

                mPrefs.prefDarkCheck = false

                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )

                recreate()

            }

        }

    }
}