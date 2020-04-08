package br.com.echurras2

import android.app.ProgressDialog
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateActivity : AppCompatActivity() {

    //Elementos da UI (User Interface - Interface do Usuario)
    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etUsername: EditText? = null
    private var etLogin: EditText? = null
    private var etPass: EditText? = null
    private var etEmail: EditText? = null
    private var btnCreateAccount: Button? = null
    private var mProgressBar: ProgressDialog? = null

    //Referencias ao Banco de Dados

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "CreateActivity"

    //Variaveis globais
    private var firstName: String? = null
    private var lastName: String? = null
    private var username: String? = null
    private var login: String? = null
    private var pass: String? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        initialise()
    }

    private fun initialise() {
        etFirstName = findViewById(R.id.et_first_name) as EditText
        etLastName = findViewById(R.id.et_last_name) as EditText
        etUsername = findViewById(R.id.et_username) as EditText
        etLogin = findViewById(R.id.et_login) as EditText
        etPass = findViewById(R.id.et_pass) as EditText
        etEmail = findViewById(R.id.et_email) as EditText
        btnCreateAccount = findViewById(R.id.btn_create_account) as Button
        mProgressBar = ProgressDialog(this)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference =
            mDatabase!!.reference!!.child("Users") //When we access, add a new child to firebase
        mAuth = FirebaseAuth.getInstance()

        btnCreateAccount!!.setOnClickListener { createNewAccount() }

    }

    private fun createNewAccount() {

        firstName = etFirstName?.text.toString()
        lastName = etLastName?.text.toString()
        username = etUsername?.text.toString()
        login = etLogin?.text.toString()
        pass = etPass?.text.toString()
        email = etEmail?.text.toString() //Get this infos and convert to String

        if (!TextUtils.isEmpty(firstName) &&
            !TextUtils.isEmpty(lastName) &&
            !TextUtils.isEmpty(username) &&
            !TextUtils.isEmpty(login) &&
            !TextUtils.isEmpty(pass) &&
            !TextUtils.isEmpty(email)
        ) {
            Toast.makeText(this, "Informações preenchidas corretamente!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Entre com mais detalhes!", Toast.LENGTH_SHORT).show()
        }

        mProgressBar!!.setMessage("Registrando usuário")
        mProgressBar!!.show() //Show this mensage for the user

        mAuth!!.createUserWithEmailAndPassword(email!!, pass!!)
            .addOnCompleteListener(this) { task ->
                mProgressBar!!.hide()

                if (task.isSuccessful) {
                    Log.d(TAG, "CreateUserWithEmail:Sucess")

                    val userId = mAuth!!.currentUser!!.uid

                    //Verify if the user verified the email
                    verifyEmail()

                    val currentUserDb = mDatabaseReference!!.child(userId)
                    currentUserDb.child("fisrtName").setValue(firstName)
                    currentUserDb.child("fisrtName").setValue(lastName)

                    //Update the info in database
                    updateUserInfoandUi()
                } else {
                    Log.w(TAG, "CreateUserWithEmail:Failure", task.exception)
                    Toast.makeText(this@CreateActivity, "A autenticação falhou", Toast.LENGTH_SHORT)
                        .show()
                }
            }


    }

    private fun updateUserInfoandUi() {
        //Start new activity
        val intent = Intent(this@CreateActivity, MainAppActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification().addOnCompleteListener(this) { task ->

            if (task.isSuccessful) {
                Toast.makeText(
                    this@CreateActivity, "Verificação de E-Mail enviado para" + mUser.getEmail(),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Log.e(TAG, "SendEmailVerification", task.exception)
                Toast.makeText(
                    this@CreateActivity,
                    "Falha ao enviar verificação de E-Mail",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}


