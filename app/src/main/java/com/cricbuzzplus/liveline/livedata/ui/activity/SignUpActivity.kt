package com.cricbuzzplus.liveline.livedata.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivitySignUpBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.model.UserModel
import com.cricbuzzplus.liveline.livedata.model.UserModelResponse
import com.cricbuzzplus.liveline.livedata.response.LoginResponse
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.messaging.FirebaseMessaging


class SignUpActivity : BaseActivity() {

    lateinit var viewModel: UsersViewModel
    lateinit var binding: ActivitySignUpBinding

    var googleSignInClient: GoogleSignInClient? = null
    private lateinit var firebaseAuth: FirebaseAuth
    val RC_SIGN_IN = 1231
    lateinit var db: FirebaseFirestore

    val loginResultLauncer = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
        if (result.resultCode== Activity.RESULT_OK){
            val dataIntent:Intent? = result.data
            if (dataIntent!=null){
                val data=if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    dataIntent?.extras?.getParcelable("data", LoginResponse::class.java)
                }else{
                    dataIntent?.extras?.getParcelable<LoginResponse>("data")!!
                }

                if (data!=null){

                    val i=Intent()
                    val bundel=Bundle()
                    bundel.putParcelable("data",data)
                    i.putExtras(bundel)
                    setResult(Activity.RESULT_OK,i)
                    finish()
                    /*mPrefs.prefUserDetails=data
                    binding.islogin=true
                    getChat()*/
                }
            }
        }
    }

    var token =""
    var referBy =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@SignUpActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObserver()


        db = FirebaseFirestore.getInstance()


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result.toString()

            Log.e("TAG1111", "onViewCreated:fcmtokern ----   ${token}")

        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.signin_key))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()


        binding.ivPasswordToggle.setOnClickListener {
            val selectionStart = binding.etPassword.selectionStart
            val selectionEnd = binding.etPassword.selectionEnd

            if (binding.etPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                // Show password
                binding.etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.ivPasswordToggle.setImageResource(R.drawable.ic_baseline_eye_24)
            } else {
                // Hide password
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.ivPasswordToggle.setImageResource(R.drawable.ic_baseline_eye_off_24)
            }

            // Restore cursor position after transformation
            binding.etPassword.setSelection(selectionStart, selectionEnd)
        }


        val privacyPolicy = SpannableString("Privacy Policy")
        privacyPolicy.setSpan(UnderlineSpan(), 0, privacyPolicy.length, 0)

        binding.privacyPolicy.setText(privacyPolicy)

        val termsCondition = SpannableString("T&C")
        termsCondition.setSpan(UnderlineSpan(), 0, termsCondition.length, 0)

        binding.termsCondition.setText(termsCondition)


        val signIn = SpannableString("Log In")
        signIn.setSpan(UnderlineSpan(), 0, signIn.length, 0)

        binding.signIn.setText(signIn)


        binding.signUp.setOnClickListener {

            if (isValid()){
                callRegister()
            }

        }

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.signIn.setOnClickListener {
            onBackPressed()
        }

        binding.googleLogin.setOnClickListener {
            handleProgressLoader(true)
            processLogin()
        }

    }

    private fun isValid(): Boolean {


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailEt.text.toString().trim()).matches())
        {
            binding.emailEt.error = "Please check the email address."
            binding.emailEt.requestFocus()
            return false
        }

        if (binding.userNameEt.text.toString().trim().isNullOrEmpty() || binding.userNameEt.text.toString().trim().length < 3)
        {
            binding.userNameEt.error = "please enter valid name"
            binding.userNameEt.requestFocus()
            return false
        }

        if (!binding.mobileEt.text.toString().matches("[0-9]{10}".toRegex())) {
            binding.mobileEt.error = "please check the Mobile no."
            binding.mobileEt.requestFocus()
            return false
        }

        if (binding.etPassword.text.toString().trim().isNullOrEmpty() || binding.etPassword.text.toString().trim().length < 6)
        {
            binding.etPassword.error = "please enter valid password"
            binding.etPassword.requestFocus()
            return false
        }

        if (binding.referBy.text.toString().trim().isNullOrEmpty())
        {
            referBy = ""
        }else{
            referBy = binding.referBy.text.toString().trim()
        }



        return true
    }

    fun callRegister(){

        if (checkForInternet(this)) {
            viewModel.registerUser(binding.userNameEt.text.toString().trim(),binding.mobileEt.text.toString().trim(),binding.emailEt.text.toString().trim(),binding.etPassword.text.toString().trim(),referBy)
        }

    }

    fun callSocialLogin(name:String,socialId:String,socialType:String,fcmToken:String){

        if (checkForInternet(this)) {
            viewModel.socialLogin(name,socialId,socialType, fcmToken)
        }
    }



    private fun setObserver(){
        observeRegister()
        observeLoginSocial()
        observeExtras()
    }


    private fun observeRegister(){

        viewModel.userLoginLiveData.observe(this, Observer {

           // mPrefs.prefUserDetails = it

          /*  val intent = Intent(this, HomeActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()*/


            val intent = Intent(this@SignUpActivity, OtpActivity::class.java)
            intent.putExtra("otp",it.otp)
            intent.putExtra("mobile",it.mobile)
            loginResultLauncer.launch(intent)
        })

    }

    private fun observeLoginSocial(){

        viewModel.socialLoginLiveData.observe(this, Observer {


            val i = Intent()
            val bundel = Bundle()
            bundel.putParcelable("data", it)
            i.putExtras(bundel)
            setResult(Activity.RESULT_OK, i)
            finish()

        })

    }


    private fun observeExtras() {
        viewModel!!.loaderLiveData.observe(this,
            { isLoading -> handleProgressLoader(isLoading!!) })
        viewModel!!.dataLoadError.observe(this, { s ->
            Log.e("TAG", "onChanged: $s")
            handleError(s.toString())
        })
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    fun processLogin() {
        googleSignInClient?.signOut()
        val signInIntent: Intent = googleSignInClient?.signInIntent!!

        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {

            try {

                handleProgressLoader(false)

                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {

                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {

                    Log.e("TAG", "Google sign in failed", e)
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }

    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        handleProgressLoader(true)
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                handleProgressLoader(false)
                if (task.isSuccessful) {
                   // checkUser()

                    val name = task.result.user?.displayName.toString()
                    val socialId = task.result.user?.displayName.toString()

                    callSocialLogin(name,socialId,"google",token)

                    //showToast("Login Success")

                } else {
                    showToast("Login Failed")
                }
            }
    }

    fun checkUser() {

        val uid = firebaseAuth.currentUser?.uid.toString()
        db.collection("users").document(uid).get().addOnSuccessListener {
            if (it.exists()) {
                // senderid = firebaseAuth.currentUser?.uid.toString()
                // sendername = firebaseAuth.currentUser?.displayName.toString()
                /*db.collection("users").document(uid).update(
                    mapOf(
                        "name" to firebaseAuth.currentUser?.displayName,
                        "email" to firebaseAuth.currentUser?.email,
                        "loginat" to FieldValue.serverTimestamp()
                    )
                )*/

                val userRef = db.collection("users").document(uid)
                val data = mapOf(
                    "name" to firebaseAuth.currentUser?.displayName,
                    "email" to firebaseAuth.currentUser?.email,
                    "loginat" to FieldValue.serverTimestamp()
                )
                userRef.set(data, SetOptions.merge()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        db.collection("users").document(uid).get()
                            .addOnSuccessListener { document ->
                                if (document != null && document.exists()) {
                                    // Convert the document data to a UserModelResponse object
                                    val userModelResponse =
                                        document.toObject(UserModelResponse::class.java)
                                    //mPrefs.prefUserDetails = userModelResponse
                                    // onBackPressed()
                                    val i = Intent()
                                    val bundel = Bundle()
                                    bundel.putParcelable("data", userModelResponse)
                                    i.putExtras(bundel)
                                    setResult(Activity.RESULT_OK, i)
                                    finish()
                                } else {
                                    // Document does not exist or there was an error retrieving it
                                }
                            }
                            .addOnFailureListener { e ->
                                adddatatodb()
                            }
                    } else {
                        adddatatodb()
                    }
                }

            } else {
                adddatatodb()
            }
        }.addOnFailureListener {
            adddatatodb()
        }


    }

    fun adddatatodb() {

        val sendername = firebaseAuth.currentUser?.displayName.toString()
        val uid = firebaseAuth.currentUser?.uid.toString()
        val email = firebaseAuth.currentUser?.email.toString()
        val number = firebaseAuth.currentUser?.phoneNumber.toString()
        val profilepicture = firebaseAuth.currentUser?.photoUrl.toString()


        val model = UserModel(
            email, uid, profilepicture, sendername, number, "token", true,
            FieldValue.serverTimestamp(),
            FieldValue.serverTimestamp()
        )


        db.collection("users").document(uid)
            .set(model)
            .addOnSuccessListener { documentReference ->


                db.collection("users").document(uid).get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            // Convert the document data to a UserModelResponse object
                            val userModelResponse = document.toObject(UserModelResponse::class.java)
                            // mPrefs.prefUserDetails = userModelResponse
                            val i = Intent()
                            val bundel = Bundle()
                            bundel.putParcelable("data", userModelResponse)
                            i.putExtras(bundel)
                            setResult(Activity.RESULT_OK, i)
                            finish()
                        } else {
                            // Document does not exist or there was an error retrieving it
                        }
                    }
                    .addOnFailureListener { e ->
                        val modelR = UserModelResponse(
                            email, uid, profilepicture, sendername, number, "token", true,
                            Timestamp.now(), Timestamp.now()
                        )

                        // mPrefs.prefUserDetails = modelR
                        val i = Intent()
                        val bundel = Bundle()
                        bundel.putParcelable("data", modelR)
                        i.putExtras(bundel)
                        setResult(Activity.RESULT_OK, i)
                        finish()
                    }

            }
            .addOnFailureListener { e ->
                showToast(e.message.toString())
            }


    }
}