package alameda.daniel.glucoscare

import com.google.firebase.database.IgnoreExtraProperties
import java.util.Date

@IgnoreExtraProperties
data class Nivel(
    var nivel: Float = 0f,
    var fechaHoraCaptura: String = ""
)
