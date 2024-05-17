package alameda.daniel.glucoscare
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
        //Agregado para cerrar sesión:
        val btnLogout: Button = findViewById(R.id.btn_logout)
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

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

        nombreEditText.setText(nombre)
        edadEditText.setText(edad)
        telefonoEditText.setText(telefono)
        correoEditText.setText(correo)
        val generoOptions = resources.getStringArray(R.array.genero_items)
        val generoPosition = generoOptions.indexOf(genero)
        if (generoPosition != -1) { generoSpinner.setSelection(generoPosition) }
        val tipoDiabetesOptions = resources.getStringArray(R.array.daibete_items)
        val tipoDiabetesPosition = tipoDiabetesOptions.indexOf(tipoDiabetes)
        if (tipoDiabetesPosition != -1) { tipoDiabetesSpinner.setSelection(tipoDiabetesPosition) }

    }
    private fun saveChanges() {
        // Retrieve updated user information
        /*val updatedNombre = nombreEditText.text.toString().trim()
        val updatedEdad = edadEditText.text.toString().trim()
        val updatedTelefono = telefonoEditText.text.toString().trim()
        val updatedCorreo = correoEditText.text.toString().trim()
        val updatedContrasenia = contraseniaEditText.text.toString().trim()
        val updatedContraseniaConf = contraseniaConfEditText.text.toString().trim()
        val updatedGenero = generoSpinner.selectedItem.toString()
        val updatedTipoDiabetes = tipoDiabetesSpinner.selectedItem.toString()
        */

            val currentUser = auth.currentUser

            // Update nombre if needed
            updateNombre(currentUser)

            // Update edad if needed
            updateEdad(currentUser)

            // Update telefono if needed
            updateTelefono(currentUser)

            // Update correo if needed
            updateCorreo(currentUser)

            // Update contrasenia if needed
            updateContrasenia(currentUser)

            // Update genero if needed
            updateGenero(currentUser)

            // Update tipo diabetes if needed
            updateTipoDiabetes(currentUser)
        }

        private fun updateNombre(user: FirebaseUser?) {
            val updatedNombre = nombreEditText.text.toString().trim()
            if (updatedNombre.isNotEmpty() && user != null) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(updatedNombre)
                    // You can update other fields like photo URL if needed
                    .build()

                user.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Nombre updated successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this,
                                "Failed to update nombre: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    private fun updateEdad(user: FirebaseUser?) {
        val updatedEdad = edadEditText.text.toString().trim()
        if (updatedEdad.isNotEmpty() && user != null) {
            val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                .setDisplayName(user.displayName) // Mantener el nombre sin cambios
                .build()

            user.updateProfile(userProfileChangeRequest)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Actualización exitosa
                        Toast.makeText(
                            this,
                            "Edad actualizada exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Error al actualizar
                        Toast.makeText(
                            this,
                            "Error al actualizar la edad: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
    private fun updateTelefono(user: FirebaseUser?) {
        val updatedTelefono = telefonoEditText.text.toString().trim()
        if (updatedTelefono.isNotEmpty() && user != null) {
            val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                .setDisplayName(user.displayName) // Mantener el nombre sin cambios
                //.setPhoneNumbe(updatedTelefono) // Establecer el nuevo número de teléfono
                .build()

            user.updateProfile(userProfileChangeRequest)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Actualización exitosa
                        Toast.makeText(
                            this,
                            "Teléfono actualizado exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Error al actualizar
                        Toast.makeText(
                            this,
                            "Error al actualizar el teléfono: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
    private fun updateCorreo(user: FirebaseUser?) {
        val updatedCorreo = correoEditText.text.toString().trim()
        if (updatedCorreo.isNotEmpty() && user != null) {
            user.updateEmail(updatedCorreo)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Actualización exitosa
                        Toast.makeText(
                            this,
                            "Correo electrónico actualizado exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Error al actualizar
                        Toast.makeText(
                            this,
                            "Error al actualizar el correo electrónico: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
    private fun updateContrasenia(user: FirebaseUser?) {
        val updatedContrasenia = contraseniaEditText.text.toString().trim()
        val updatedContraseniaConf = contraseniaConfEditText.text.toString().trim()
        if (updatedContrasenia.isNotEmpty() && updatedContrasenia == updatedContraseniaConf && user != null) {
            user.updatePassword(updatedContrasenia)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Actualización exitosa
                        Toast.makeText(
                            this,
                            "Contraseña actualizada exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Error al actualizar
                        Toast.makeText(
                            this,
                            "Error al actualizar la contraseña: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            // Contraseñas no coinciden o están vacías
            Toast.makeText(
                this,
                "Las contraseñas no coinciden o están vacías",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateGenero(user: FirebaseUser?) {
        val updatedGenero = generoSpinner.selectedItem.toString()
        if (updatedGenero.isNotEmpty() && user != null) {
            val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                .setDisplayName(user.displayName) // Mantener el nombre sin cambios
                .build()

            user.updateProfile(userProfileChangeRequest)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Actualización exitosa
                        Toast.makeText(
                            this,
                            "Género actualizado exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Error al actualizar
                        Toast.makeText(
                            this,
                            "Error al actualizar el género: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun updateTipoDiabetes(user: FirebaseUser?) {
        val updatedTipoDiabetes = tipoDiabetesSpinner.selectedItem.toString()
        if (updatedTipoDiabetes.isNotEmpty() && user != null) {
            val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                .setDisplayName(user.displayName) // Mantener el nombre sin cambios
                .build()

            user.updateProfile(userProfileChangeRequest)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Actualización exitosa
                        Toast.makeText(
                            this,
                            "Tipo de diabetes actualizado exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Error al actualizar
                        Toast.makeText(
                            this,
                            "Error al actualizar el tipo de diabetes: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }


        // Update user profile if needed
        /*val currentUser = auth.currentUser
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
        }*/
    //}
}