package alameda.daniel.glucoscare

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Usuario(
    var nombre: String,
    var edad: Int,
    var telefono: String,
    var correo: String,
    var contrasenia: String,
    var genero: String,
    var tipoDiabetes: String
)
