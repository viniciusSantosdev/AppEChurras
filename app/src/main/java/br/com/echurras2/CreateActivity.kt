package br.com.echurras2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class CreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
    }

    fun CriarCadastro(v: View){
        var intent = Intent(this, MainAppActivity::class.java )
        startActivity(intent)
    }
}
