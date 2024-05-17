package alameda.daniel.glucoscare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    lateinit var btn_ingresar: Button
    lateinit var btn_registro: Button
    lateinit var tv_recuperacion: TextView
    private lateinit var correoEditText: EditText
    private lateinit var contraseniaEditText: EditText
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_ingresar = findViewById(R.id.btn_ingresar)
        btn_registro = findViewById(R.id.btn_registro)
        tv_recuperacion = findViewById(R.id.tv_recuperacion)
        correoEditText = findViewById(R.id.txt_email_login)
        contraseniaEditText = findViewById(R.id.txt_contrasenia_login)
        auth = Firebase.auth

        btn_registro.setOnClickListener {
            var intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btn_ingresar.setOnClickListener {
            login()
        }

        tv_recuperacion.setOnClickListener {
            var intent = Intent(this, RecuperacionActivity::class.java)
            startActivity(intent)
        }

    }


    private fun login() {
        var correo: String = correoEditText.text.toString()
        var contra: String = contraseniaEditText.text.toString()

        if (!correo.isNullOrEmpty() && !contra.isNullOrEmpty()) {
            auth.signInWithEmailAndPassword(correo, contra)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        val userId = user?.uid
                        val correoUsuario = user?.email

                        val sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("id_usuario", userId)
                        editor.putString("correo", correoUsuario)
                        editor.apply()

                        Toast.makeText(
                            this,
                            "Bienvenido",
                            Toast.LENGTH_SHORT,
                        ).show()

                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        // Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this,
                            "Datos incorrectos.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        //updateUI(null)
                    }
                }

        } else {
            Toast.makeText(this, "Ingresar datos", Toast.LENGTH_SHORT).show()
        }

    }


}
