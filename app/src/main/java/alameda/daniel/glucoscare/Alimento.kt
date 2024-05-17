package alameda.daniel.glucoscare

data class Alimento(
    val nombre: String,
    val cantidad: Double,
    var fechaHoraCaptura: String = ""
)