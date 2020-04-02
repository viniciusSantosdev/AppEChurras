package br.com.echurras2


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import br.com.echurras2.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColorTo(R.color.colorPrimary)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun Window.setStatusBarColorTo(color: Int) {
        this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.statusBarColor = ContextCompat.getColor(baseContext, color)
    }

    fun Create(v: View) {
        val intent = Intent(this, CreateActivity::class.java) //change the screen for the CreateActivity
        startActivity(intent)
    }

    fun Login(v: View) {
        val intent = Intent(this, MainAppActivity::class.java) //change the screen for the MainApp
        startActivity(intent)
        finish()
    }

    fun EsqueciSenha(v: View) {
        val intent = Intent(this, ForgotPassActivity::class.java) //change the screen for the Forgot_my_Pass
        startActivity(intent)
    }
}