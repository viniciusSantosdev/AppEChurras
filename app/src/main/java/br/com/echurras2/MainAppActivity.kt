package br.com.echurras2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)
    }

    fun listarItens(v: View){
        var intent = Intent(this, ListaItensActivity::class.java)
        startActivity(intent)
    }
}
