package alameda.daniel.glucoscare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
<<<<<<< HEAD
=======

>>>>>>> 9dafe3aa1d73b7f8be99da0c55f20c31c3acfa68

class RegistroActivity : AppCompatActivity() {
    lateinit var genero: Spinner
    lateinit var tipoDiabetes: Spinner
    lateinit var adapterGenero: ArrayAdapter<CharSequence>
    lateinit var adapterDiabetes: ArrayAdapter<CharSequence>

    private lateinit var nombreEditText: EditText
    private lateinit var edadEditText: EditText
    private lateinit var telefonoEditText: EditText
    private lateinit var correoEditText: EditText
    private lateinit var contraseniaEditText: EditText
    private lateinit var contraseniaConfEditText: EditText
    private lateinit var generoSpinner: Spinner
    private lateinit var tipoDiabetesSpinner: Spinner

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        llenarComboBox();
        auth = Firebase.auth;

        nombreEditText = findViewById(R.id.txt_nombre)
        edadEditText = findViewById(R.id.txt_edad)
        telefonoEditText = findViewById(R.id.txt_telefono)
        correoEditText = findViewById(R.id.txt_correo)
        contraseniaEditText = findViewById(R.id.txt_contrasenia)
        contraseniaConfEditText = findViewById(R.id.txt_contrasenia_conf)
        generoSpinner = findViewById(R.id.genero)
        tipoDiabetesSpinner = findViewById(R.id.tipoDiab)

        val btnRegistrar = findViewById<Button>(R.id.btn_registar)
        btnRegistrar.setOnClickListener {
            validarUsuario()
        }


    }


    private fun llenarComboBox() {
        genero = findViewById(R.id.genero);
        adapterGenero = ArrayAdapter.createFromResource(
            this,
            R.array.genero_items,
            android.R.layout.simple_spinner_item
        )
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genero.setAdapter(adapterGenero)
        genero.setSelection(0)

        tipoDiabetes = findViewById(R.id.tipoDiab)
        adapterDiabetes = ArrayAdapter.createFromResource(
            this,
            R.array.daibete_items,
            android.R.layout.simple_spinner_item
        )
        adapterDiabetes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipoDiabetes.setAdapter(adapterDiabetes)
        tipoDiabetes.setSelection(0)

    }


    private fun validarUsuario() {
        val nombre = nombreEditText.text.toString().trim()
        val edadText = edadEditText.text.toString().trim()
        val telefono = telefonoEditText.text.toString().trim()
        val correo = correoEditText.text.toString().trim()
        val contrasenia = contraseniaEditText.text.toString().trim()
        val contraseniaConf = contraseniaConfEditText.text.toString().trim()
        val genero = generoSpinner.selectedItem.toString()
        val tipoDiabetes = tipoDiabetesSpinner.selectedItem.toString()

        if (nombre.isEmpty() || edadText.isEmpty() || telefono.isEmpty() || correo.isEmpty() ||
            contrasenia.isEmpty() || contraseniaConf.isEmpty() || genero.isEmpty() ||
            tipoDiabetes.isEmpty()
        ) {
            Toast.makeText(
                this,
                "Algún campo esta vacio, verifique otra vez",
                Toast.LENGTH_SHORT,
            ).show()
            return
        }

        // Validar que las contraseñas coincidan
        if (contrasenia != contraseniaConf) {
            Toast.makeText(
                this,
                "Las contraseñas no coinciden",
                Toast.LENGTH_SHORT,
            ).show()
            return
        }

        if (contrasenia.length < 6) {
            Toast.makeText(
                this,
                "Las contraseñas debe de ser mayor a 6 caracteres",
                Toast.LENGTH_SHORT,
            ).show()
            return
        }

        var usuario =
            Usuario(nombre, edadText.toInt(), telefono, correo, contrasenia, genero, tipoDiabetes)
        registarUsuario(usuario)


    }


    public fun registarUsuario(usuario: Usuario) {

        var correo = usuario.correo;
        var contrasenia = usuario.contrasenia
        // Crear usuario en Firebase Authentication
        auth.createUserWithEmailAndPassword(correo, contrasenia)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser!!.uid

                    val database = FirebaseDatabase.getInstance()
                    val usersRef = database.getReference("users")

                    usersRef.child(userId).setValue(usuario)
                        .addOnSuccessListener {
                            Log.d("exito", "createUserWithEmail:success")
                            Toast.makeText(
                                this,
                                "Se ha registrado con exito usuario",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener {
                            Log.w("error", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                this,
                                "No se pudo registrar el usuario",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                } else {
                    Log.w("error", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this,
                        "No se pudo Autenticar",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
<<<<<<< HEAD
=======
    }

    private fun navigateToEditProfile() {
        val intent = Intent(this, EditProfileActivity::class.java).apply {
            // Pass user data as extras
            putExtra("nombre", nombreEditText.text.toString())
            putExtra("edad", edadEditText.text.toString())
            putExtra("telefono", telefonoEditText.text.toString())
            putExtra("correo", correoEditText.text.toString())
            putExtra("genero", generoSpinner.selectedItem.toString())
            putExtra("tipoDiabetes", tipoDiabetesSpinner.selectedItem.toString())
        }
        startActivity(intent)
>>>>>>> 9dafe3aa1d73b7f8be99da0c55f20c31c3acfa68
    }
}