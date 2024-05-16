package alameda.daniel.glucoscare.ui.home

import alameda.daniel.glucoscare.Nivel
import alameda.daniel.glucoscare.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import alameda.daniel.glucoscare.databinding.FragmentHomeBinding
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    lateinit var btn_capturar_azucar: Button
    lateinit var nivel_azucar: EditText
    lateinit var sharedPreferences: SharedPreferences

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_capturar_azucar = binding.btnCaptuararAzucar
        nivel_azucar = binding.txtAzucar
        sharedPreferences = requireActivity().getSharedPreferences("userInfo", MODE_PRIVATE)
        generarGrafica()
        btn_capturar_azucar.setOnClickListener {
            registarAzucar()
        }


    }

    private fun registarAzucar() {
        val nivelAzucar = nivel_azucar.text.toString().toFloatOrNull()

        if (nivelAzucar == null || nivelAzucar == 0f || nivelAzucar < 30 || nivelAzucar > 700) {
            Toast.makeText(requireContext(), "Nivel de azúcar no válido", Toast.LENGTH_SHORT).show()
            return
        }

        val id_usuario = sharedPreferences.getString("id_usuario", "").toString()
        val database = FirebaseDatabase.getInstance()
        val nivelesAzucarRef = database.getReference("users").child(id_usuario).child("niveles_azucar")
        val nivelAzucarRef = nivelesAzucarRef.push()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val fecha_hora = sdf.format(Date())
        var nivel = Nivel(nivelAzucar, fecha_hora)
        nivelAzucarRef.setValue(nivel)
        generarGrafica()
        nivel_azucar.setText("")

    }

    private fun generarGrafica() {

        val anyChartView = binding.grafica
        anyChartView.setProgressBar(binding.progressBar)

        val cartesian = AnyChart.line()

        val id_usuario = sharedPreferences.getString("id_usuario", "").toString()
        val database = FirebaseDatabase.getInstance()
        val nivelesAzucarRef = database.getReference("users").child(id_usuario).child("niveles_azucar")

        val seriesData = mutableListOf<CustomDataEntry>()

        nivelesAzucarRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                seriesData.clear()
                var index = 1
                for (snapshot in dataSnapshot.children) {
                    val nivel = snapshot.getValue(Nivel::class.java)
                    nivel?.let {
                        val fechaHoraCaptura = it.fechaHoraCaptura
                        val nivelAzucar = it.nivel
                        seriesData.add(CustomDataEntry(fechaHoraCaptura, nivelAzucar))
                        index++
                    }
                }


                val series1 = cartesian.line(seriesData as List<DataEntry>?)
                series1.name("Niveles de Azucar")
                anyChartView.setChart(cartesian)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private inner class CustomDataEntry(x: String, value: Number) : ValueDataEntry(x, value)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}