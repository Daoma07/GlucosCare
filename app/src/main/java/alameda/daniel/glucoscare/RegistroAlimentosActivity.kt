package alameda.daniel.glucoscare
import alameda.daniel.glucoscare.AlimentoAdapter
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class RegistroAlimentosActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private lateinit var nombreAlimentoEditText: EditText
    private lateinit var cantidadEditText: EditText
    private lateinit var btnRegistrarAlimento: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var alimentoAdapter: AlimentoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_alimentos)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("alimentos")

        nombreAlimentoEditText = findViewById(R.id.et_nombre_alimento)
        cantidadEditText = findViewById(R.id.et_cantidad)
        btnRegistrarAlimento = findViewById(R.id.btn_registrar_alimento)

        btnRegistrarAlimento.setOnClickListener {
            registrarAlimento()
        }



        // Inicializa el adaptador con una lista vacía al principio
        alimentoAdapter = AlimentoAdapter(emptyList())
        recyclerView.adapter = alimentoAdapter

    }

    private fun registrarAlimento() {
        /*
        val nombreAlimento = nombreAlimentoEditText.text.toString().trim()
        val cantidad = cantidadEditText.text.toString().trim()

        if (nombreAlimento.isEmpty() || cantidad.isEmpty()) {
            // Validar que se haya ingresado el nombre del alimento y la cantidad
            // Mostrar un mensaje de error si falta algún dato
            return
        }

        // Obtener el ID del usuario actualmente autenticado
        val userId = auth.currentUser?.uid ?: return

        // Crear un nuevo objeto Alimento
        val alimentoId = databaseReference.push().key ?: return
       val alimento = Alimento(alimentoId, nombreAlimento, cantidad.toDouble(), "e")

        // Guardar el alimento en la base de datos
        databaseReference.child(userId).child(alimentoId).setValue(alimento)
            .addOnSuccessListener {
                // Mostrar un mensaje de éxito
            }
            .addOnFailureListener {
                // Mostrar un mensaje de error
            }
    }

    // Método para actualizar la lista de alimentos mostrada en el RecyclerView
    private fun actualizarListaAlimentos(alimentos: List<Alimento>) {
        alimentoAdapter = AlimentoAdapter(alimentos)
        recyclerView.adapter = alimentoAdapter

         */
    }

}