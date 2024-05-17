package alameda.daniel.glucoscare.ui.notifications

import alameda.daniel.glucoscare.Alimento
import alameda.daniel.glucoscare.AlimentoAdapter
import alameda.daniel.glucoscare.Nivel
import alameda.daniel.glucoscare.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
//import alameda.daniel.glucoscare.databinding.FragmentInteresadosBinding
import alameda.daniel.glucoscare.databinding.ActivityRegistroAlimentosBinding
import android.content.Context
import android.content.SharedPreferences
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationsFragment : Fragment() {

    private var _binding: ActivityRegistroAlimentosBinding? = null
    private lateinit var nombreAlimentoEditText: EditText
    private lateinit var cantidadEditText: EditText
    private lateinit var btnRegistrarAlimento: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var alimentoAdapter: AlimentoAdapter
    private lateinit var sharedPreferences: SharedPreferences

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityRegistroAlimentosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nombreAlimentoEditText = binding.etNombreAlimento
        cantidadEditText = binding.etCantidad
        btnRegistrarAlimento = binding.btnRegistrarAlimento
        recyclerView = binding.recyclerViewAlimentos

        sharedPreferences = requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)

        btnRegistrarAlimento.setOnClickListener {
            registrarAlimento()
        }

        alimentoAdapter = AlimentoAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = alimentoAdapter

       // obtenerListaAlimentos()
    }

    private fun registrarAlimento() {
        val nombreAlimento = nombreAlimentoEditText.text.toString().trim()
        val cantidad = cantidadEditText.text.toString().trim()

        if (nombreAlimento.isEmpty() || cantidad.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, ingresa todos los datos", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = sharedPreferences.getString("id_usuario", "").toString()
        val database = FirebaseDatabase.getInstance()
        val alimentosRef = database.getReference("users").child(userId).child("alimentos")
        val alimentoBd = alimentosRef.push()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val fechaHora = sdf.format(Date())
        val alimento = Alimento(nombreAlimento, cantidad.toDouble(), fechaHora)

        alimentoBd.setValue(alimento)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Alimento registrado con éxito", Toast.LENGTH_SHORT).show()
                cantidadEditText.setText("")
                nombreAlimentoEditText.setText("")
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error al registrar el alimento", Toast.LENGTH_SHORT).show()
            }
    }

    private fun obtenerListaAlimentos() {
        val userId = sharedPreferences.getString("id_usuario", "").toString()
        val database = FirebaseDatabase.getInstance()
        val alimentosRef = database.getReference("users").child(userId).child("alimentos")

        alimentosRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val alimentos = mutableListOf<Alimento>()
                for (snapshot in dataSnapshot.children) {
                    val alimento = snapshot.getValue(Alimento::class.java)
                    alimento?.let { alimentos.add(it) }
                }
                // Actualizar el adaptador con la nueva lista de alimentos
             //  alimentoAdapter.actualizarLista(alimentos)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
