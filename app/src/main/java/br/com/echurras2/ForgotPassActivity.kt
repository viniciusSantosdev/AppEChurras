package br.com.echurras2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPassActivity : AppCompatActivity() {

    private val TAG = "ForgotPassActivity"

    //Elementos do UI
    private var etUsername: EditText? = null
    private var etEmail: EditText? = null
    private var btnSubmit: Button? = null

    //Referencia do banco de dados
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)

        initialise()
    }

    private fun initialise() {
        etUsername = findViewById(R.id.et_username) as EditText
        etEmail = findViewById(R.id.et_email) as EditText
        btnSubmit = findViewById(R.id.btn_submit)

        mAuth = FirebaseAuth.getInstance()

        btnSubmit!!.setOnClickListener { sendPassEmail() }
    }

    private fun sendPassEmail() {
        val username = etUsername?.text.toString()
        val email = etEmail?.text.toString()

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email)) {
            mAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val mensage = "Email enviado"
                        Log.d(TAG, mensage)
                        Toast.makeText(this, mensage, Toast.LENGTH_SHORT).show()
                        updateUI()
                    } else{
                        Log.w(TAG, task.exception!!.message)
                        Toast.makeText(this, "Nenhum usuário encontrado com esse email", Toast.LENGTH_SHORT).show()
                    }
                }
        } else{
            Toast.makeText(this, "Entre com um E-Mail válido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(){
        val intent = Intent(this@ForgotPassActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}

    /*fun esqueciSenha(v: View){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
            finish()
    }*/

