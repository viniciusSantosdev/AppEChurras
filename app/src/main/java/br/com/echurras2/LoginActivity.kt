package br.com.echurras2


import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import br.com.echurras2.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_create.*
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    //Global varilables
    private var login: String? = null
    private var pass: String? = null

    //UI elements
    private var tvForgotPassActivity: TextView? = null
    private var etLogin: EditText? = null
    private var etPass: EditText? = null
    private var btnLogin: Button? = null
    private var btnCadastrar: Button? = null
    private var mProgressBar: ProgressDialog? = null

    //Firebase reference
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColorTo(R.color.colorPrimary)
        }

        initialise()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun Window.setStatusBarColorTo(color: Int) {
        this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.statusBarColor = ContextCompat.getColor(baseContext, color)
    }

    private fun initialise() {
        tvForgotPassActivity = findViewById(R.id.tv_forgot_pass) as TextView
        etLogin = findViewById(R.id.et_login) as EditText
        etPass = findViewById(R.id.et_pass) as EditText
        btnLogin = findViewById(R.id.btn_login) as Button
        btnCadastrar = findViewById(R.id.btn_register) as Button
        mProgressBar = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()

        tvForgotPassActivity!!.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity, ForgotPassActivity::class.java
                )
            )
        } //Change the screen for the ForgotACtivity

        

        btnLogin!!.setOnClickListener { loginUser() }
    }

    fun Cadastrar(v:View){
        val intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)
    }

    private fun loginUser() {
        login = etLogin?.text.toString()
        pass = etPass?.text.toString()

        if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(pass)) {
            mProgressBar!!.setMessage("Verificando usuário")
            mProgressBar!!.show()

            Log.d(TAG, "Usuário logado")

            mAuth!!.signInWithEmailAndPassword(login!!, pass!!)
                .addOnCompleteListener(this) { task ->

                    mProgressBar!!.hide()

                    //Authentication user, update infos the UI with infos the login
                    if (task.isSuccessful) {
                        Log.d(TAG, "Logado com sucesso")
                        updateUI()
                    } else {
                        Log.e(TAG, "Erro ao se logar", task.exception)
                        Toast.makeText(
                            this@LoginActivity,
                            "Login ou senha incorretos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateUI() {
        val intent = Intent(this@LoginActivity, MainAppActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}