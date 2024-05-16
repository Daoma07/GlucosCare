package alameda.daniel.glucoscare
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.UserProfileChangeRequest

class EditProfileActivity : AppCompatActivity() {
    // Your existing code...
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
        setContentView(R.layout.activity_edit_profile)
        auth = Firebase.auth

        nombreEditText = findViewById(R.id.txt_nombre)
        edadEditText = findViewById(R.id.txt_edad)
        telefonoEditText = findViewById(R.id.txt_telefono)
        correoEditText = findViewById(R.id.txt_correo)
        contraseniaEditText = findViewById(R.id.txt_contrasenia)
        contraseniaConfEditText = findViewById(R.id.txt_contrasenia_conf)
        generoSpinner = findViewById(R.id.genero)
        tipoDiabetesSpinner = findViewById(R.id.tipoDiab)

        val btnSaveChanges = findViewById<Button>(R.id.btn_save_changes)
        btnSaveChanges.setOnClickListener {
            saveChanges()
        }

        // Retrieve user data passed from RegistroActivity
        val nombre = intent.getStringExtra("nombre")
        val edad = intent.getStringExtra("edad")
        val telefono = intent.getStringExtra("telefono")
        val correo = intent.getStringExtra("correo")
        val genero = intent.getStringExtra("genero")
        val tipoDiabetes = intent.getStringExtra("tipoDiabetes")

        // Populate EditText and Spinner with user data
        nombreEditText.setText(nombre)
        edadEditText.setText(edad)
        telefonoEditText.setText(telefono)
        correoEditText.setText(correo)
        // Set selection for Spinners based on the received data
        // (You may need to map the received data to spinner positions)

    }
    private fun saveChanges() {
        // Retrieve updated user information
        val updatedNombre = nombreEditText.text.toString().trim()
        val updatedEdad = edadEditText.text.toString().trim()
        val updatedTelefono = telefonoEditText.text.toString().trim()
        val updatedCorreo = correoEditText.text.toString().trim()
        val updatedContrasenia = contraseniaEditText.text.toString().trim()
        val updatedContraseniaConf = contraseniaConfEditText.text.toString().trim()
        val updatedGenero = generoSpinner.selectedItem.toString()
        val updatedTipoDiabetes = tipoDiabetesSpinner.selectedItem.toString()

        // Validate inputs
        // (You can add your validation logic here)

        // Update user profile if needed
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(updatedNombre)
                // You can update other fields like photo URL if needed
                .build()

            user.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Profile updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Handle additional logic if needed
                    } else {
                        Toast.makeText(
                            this,
                            "Failed to update profile: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}