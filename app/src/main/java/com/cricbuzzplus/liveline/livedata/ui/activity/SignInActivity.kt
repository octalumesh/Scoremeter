package com.cricbuzzplus.liveline.livedata.ui.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Timestamp
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ActivitySignInBinding
import com.cricbuzzplus.liveline.livedata.base.BaseActivity
import com.cricbuzzplus.liveline.livedata.model.UserModel
import com.cricbuzzplus.liveline.livedata.model.UserModelResponse
import com.cricbuzzplus.liveline.livedata.response.LoginResponse
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class SignInActivity : BaseActivity() {

    lateinit var viewModel: UsersViewModel
    lateinit var binding: ActivitySignInBinding

    var googleSignInClient: GoogleSignInClient? = null
    private lateinit var firebaseAuth: FirebaseAuth
    val RC_SIGN_IN = 1231
    lateinit var db: FirebaseFirestore

    val loginResultLauncer =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val dataIntent: Intent? = result.data
                if (dataIntent != null) {
                    val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        dataIntent?.extras?.getParcelable("data", LoginResponse::class.java)
                    } else {
                        dataIntent?.extras?.getParcelable<LoginResponse>("data")!!
                    }

                    if (data != null) {

                        val i = Intent()
                        val bundel = Bundle()
                        bundel.putParcelable("data", data)
                        i.putExtras(bundel)
                        setResult(Activity.RESULT_OK, i)
                        finish()
                        /*mPrefs.prefUserDetails=data
                        binding.islogin=true
                        getChat()*/
                    }
                }
            }
        }
    lateinit var callbackManager: CallbackManager

    var loginManager: LoginManager? = null

    var token = ""

    var loginType = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(this@SignInActivity, R.color.colorPrimaryDark)
        }

        hideKeyBoard()
        setObserver()

        db = FirebaseFirestore.getInstance()
        callbackManager = CallbackManager.Factory.create()



        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result.toString()

            Log.e("TAG11116", "onViewCreated:fcmtokern ----   ${token}")

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

        binding.signUp.setOnClickListener {

            val intent = Intent(this, SignUpActivity::class.java)
            //startActivity(intent)
            loginResultLauncer.launch(intent)

        }

        binding.forgotPassword.setOnClickListener {

            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }


        val privacyPolicy = SpannableString("Privacy Policy")
        privacyPolicy.setSpan(UnderlineSpan(), 0, privacyPolicy.length, 0)

        binding.privacyPolicy.setText(privacyPolicy)

        val termsCondition = SpannableString("T&C")
        termsCondition.setSpan(UnderlineSpan(), 0, termsCondition.length, 0)

        binding.termsCondition.setText(termsCondition)


        val signUp = SpannableString("Sign Up")
        signUp.setSpan(UnderlineSpan(), 0, signUp.length, 0)

        binding.signUp.setText(signUp)


        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.signIn.setOnClickListener {
            if (isValid()) {
                if (loginType.equals("email")) {
                    callEmailLogin(
                        binding.etNumberEmail.text.trim().toString(),
                        binding.etPassword.text?.trim().toString(),
                        token
                    )
                } else if (loginType.equals("number")) {
                    callMobileLogin(
                        binding.etNumberEmail.text.trim().toString(),
                        binding.etPassword.text?.trim().toString(),
                        token
                    )
                }
            }
        }


        binding.fbLogin.setOnClickListener {
            FacebookSdk.sdkInitialize(this);

            loginManager = LoginManager.getInstance()

            loginManager?.logInWithReadPermissions(
                this,
                Arrays.asList(
                    "email",
                    "public_profile"
                )
            );

            facebookLogin()
        }

        binding.googleLogin.setOnClickListener {
            handleProgressLoader(true)
            processLogin()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    private fun isValid(): Boolean {


        if (binding.etNumberEmail.text.trim()
                .isNullOrEmpty() || binding.etNumberEmail.text.trim().length < 10
        ) {

            binding.etNumberEmail.error = "please enter correct email or number"
            binding.etNumberEmail.requestFocus()
            return false

        } else {

            if (binding.etNumberEmail.text?.matches(("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+").toRegex())!!) {

                loginType = "email"
            } else if (binding.etNumberEmail.text?.matches("[0-9]{10}".toRegex())!!) {
                loginType = "number"
            } else {
                binding.etNumberEmail.error = "please enter valid email or number"
                binding.etNumberEmail.requestFocus()
                return false
            }

        }


        /* if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etNumberEmail.text.toString().trim()).matches())
         {
             binding.etNumberEmail.error = "Please check the email address."
             binding.etNumberEmail.requestFocus()
             return false
         }*/

        if (binding.etPassword.text.toString().trim()
                .isNullOrEmpty() || binding.etPassword.text.toString().trim().length < 6
        ) {
            binding.etPassword.error = "please enter valid password"
            binding.etPassword.requestFocus()
            return false
        }

        return true
    }

    fun facebookLogin() {
        /*loginManager!!.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.e("TAGSUCCESS", "Success Login => ${loginResult.accessToken}")

                handleFacebookAccessToken(loginResult.accessToken, loginResult.accessToken.userId)

            }

            override fun onCancel() {

                Log.e("TAGSUCCESS", "onCancel: " )
                Toast.makeText(this@SignInActivity, "Login Cancelled", Toast.LENGTH_LONG).show()
            }

            override fun onError(exception: FacebookException) {

                Log.e("TAGSUCCESS", "onError: "+exception.message )

               // Toast.makeText(this@SignInActivity, "On Errior"+exception.message, Toast.LENGTH_LONG).show()
            }
        })*/

        loginManager!!
            .registerCallback(
                callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        val request = GraphRequest.newMeRequest(
                            loginResult.accessToken,
                            object : GraphRequest.GraphJSONObjectCallback {
                                override fun onCompleted(
                                    `object`: JSONObject?,
                                    response: GraphResponse?
                                ) {
                                    if (`object` != null) {
                                        try {
                                            val name = `object`.getString("name")
                                            val email = `object`.getString("email")
                                            val fbUserID = `object`.getString("id")

                                            Log.e(
                                                "TAGSUCCESS",
                                                "onCompleted: ${name + " " + email + " " + " " + fbUserID}"
                                            )
                                            // viewModel.loginWithSocial(name,fbUserID,"facebook",email,token);
                                            // do action after Facebook login success
                                            // or call your API
                                        } catch (e: JSONException) {
                                            Log.e("TAGSUCCESS", "onCompleted:ex " + e.message)
                                            e.printStackTrace()
                                        } catch (e: NullPointerException) {
                                            Log.e("TAGSUCCESS", "onCompleted:edx " + e.message)
                                            e.printStackTrace()
                                        }
                                    }
                                }
                            })
                        val parameters = Bundle()
                        parameters.putString(
                            "fields",
                            "id, name, email, gender, birthday"
                        )
                        request.parameters = parameters
                        request.executeAsync()
                    }

                    override fun onCancel() {
                        Log.e("TAGSUCCESS", "---onCancel")
                    }

                    override fun onError(error: FacebookException) {
                        // here write code when get error
                        Log.e(
                            "TAGSUCCESS", "----onError: "
                                    + error.message
                        )
                    }
                })
    }

    private fun handleFacebookAccessToken(accessToken: AccessToken, userId: String?) {
        Log.d(ContentValues.TAG, "handleFacebookAccessToken:$accessToken")

        val credential = FacebookAuthProvider.getCredential(accessToken.token)

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.e("TAGSUCCESS", "handleFacebookAccessToken: fb success ")
                    val name = task.result.user?.displayName.toString()
                    val socialId = task.result.user?.uid.toString()
                    callSocialLogin(name, socialId, "facebook", token)
                } else {

                    Log.e("TAGSUCCESS", "handleFacebookAccessToken: " + task.exception)
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

    fun callEmailLogin(email: String, password: String, fcmToken: String) {

        if (checkForInternet(this)) {
            viewModel.emailLogin(email, password, fcmToken)
        }
    }

    fun callMobileLogin(mobile: String, password: String, fcmToken: String) {

        if (checkForInternet(this)) {
            viewModel.mobileLogin(mobile, password, fcmToken)
        }
    }

    fun callSocialLogin(name: String, socialId: String, socialType: String, fcmToken: String) {

        if (checkForInternet(this)) {
            viewModel.socialLogin(name, socialId, socialType, fcmToken)
        }
    }

    private fun setObserver() {
        observeLoginEmail()
        observeLoginSocial()
        observeLoginMobile()
        observeExtras()
    }


    private fun observeLoginEmail() {

        viewModel.userLoginLiveData.observe(this, Observer {


            val i = Intent()
            val bundel = Bundle()
            bundel.putParcelable("data", it)
            i.putExtras(bundel)
            setResult(Activity.RESULT_OK, i)
            finish()

        })

    }

    private fun observeLoginMobile() {

        viewModel.otpLiveData.observe(this, Observer {

            val intent = Intent(this@SignInActivity, OtpActivity::class.java)
            intent.putExtra("otp", it)
            intent.putExtra("mobile", binding.etNumberEmail.text.trim().toString())
            loginResultLauncer.launch(intent)
        })

    }

    private fun observeLoginSocial() {

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


    fun processLogin() {
        googleSignInClient?.signOut()
        val signInIntent: Intent = googleSignInClient?.signInIntent!!

        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
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

                    val name = task.result.user?.displayName.toString()
                    val socialId = task.result.user?.uid.toString()
                    callSocialLogin(name, socialId, "google", token)
                    // checkUser()
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