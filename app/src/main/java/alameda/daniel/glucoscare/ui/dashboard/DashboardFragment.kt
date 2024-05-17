package alameda.daniel.glucoscare.ui.dashboard

import alameda.daniel.glucoscare.Alimento
import alameda.daniel.glucoscare.Recordatorio
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
//import alameda.daniel.glucoscare.databinding.FragmentInteresadosBinding
import alameda.daniel.glucoscare.databinding.FragmentRecordatoriosBinding
import android.content.Context
import android.content.SharedPreferences
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashboardFragment : Fragment() {

    private var _binding: FragmentRecordatoriosBinding? = null
    lateinit var registar_recordatorio: Button
    lateinit var descripcion: EditText
    lateinit var fecha: EditText
    lateinit var sharedPreferences: SharedPreferences
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ajustesViewModel =
            ViewModelProvider(this).get(AjustesViewModel::class.java)

        _binding = FragmentRecordatoriosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registar_recordatorio = binding.registarRecordatorio
        descripcion = binding.descricpion
        fecha = binding.fecha

        sharedPreferences = requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)

        registar_recordatorio.setOnClickListener {
            registrarReccordatorio()
        }
    }

    private fun registrarReccordatorio() {
        val descricpion = descripcion.text.toString().trim()
        val fecha = fecha.text.toString().trim()

        if (descricpion.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, ingresa todos los datos", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = sharedPreferences.getString("id_usuario", "").toString()
        val database = FirebaseDatabase.getInstance()
        val recordatoriosRef = database.getReference("users").child(userId).child("recordatorios")
        val recordatorioBd = recordatoriosRef.push()
        var recordatorio = Recordatorio(descricpion, fecha)

        recordatorioBd.setValue(recordatorio)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "recordatorio registrado con éxito", Toast.LENGTH_SHORT).show()
                descripcion.setText("")
                

            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error al registrar el recordatorio", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}