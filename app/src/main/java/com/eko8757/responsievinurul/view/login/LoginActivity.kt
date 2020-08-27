package com.eko8757.responsievinurul.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.eko8757.responsievinurul.R
import com.eko8757.responsievinurul.utils.StaticString
import com.eko8757.responsievinurul.utils.gone
import com.eko8757.responsievinurul.utils.invisible
import com.eko8757.responsievinurul.utils.visible
import com.eko8757.responsievinurul.view.main.MainActivity
import com.eko8757.responsievinurul.view.main.MainView
import com.eko8757.responsievinurul.view.register.RegisterActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView, MainView, View.OnClickListener {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener(this)
        tv_to_register.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_login -> checkDataLogin()
            R.id.tv_to_register -> toRegister()
        }
    }

    override fun showLoading() {
        btn_login.invisible()
        progressBar_login.visible()
    }

    override fun hideLoading() {
        btn_login.visible()
        progressBar_login.gone()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun toRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun checkDataLogin() {
        showLoading()
        if (ed_email_login.text.toString().isNullOrEmpty()) {
            return
            showMessage("Email tidak boleh kosong")
        } else {
            if (ed_password_login.text.toString().isNullOrEmpty()) {
                return
                showMessage("Password tidak boleh kosong")
            } else {
                putDataLogin(
                    ed_email_login.text.toString(),
                    ed_password_login.text.toString()
                )
            }
        }
    }

    override fun putDataLogin(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
            if (task.isSuccessful) {
                hideLoading()

                //get uuid
                val user: FirebaseUser? = mAuth.currentUser
                Prefs.putString(StaticString().UUID, user!!.uid)

                showMessage("Login success")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                hideLoading()
                showMessage("Login failed")
            }
        })
    }
}