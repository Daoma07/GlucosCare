package alameda.daniel.glucoscare.ui.dashboard


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import alameda.daniel.glucoscare.databinding.ActivityEditProfileBinding
import alameda.daniel.glucoscare.LoginActivity
import alameda.daniel.glucoscare.R
import android.content.Context

class AjustesFragment : Fragment() {

    private var _binding: ActivityEditProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var nombreEditText: EditText
    private lateinit var edadEditText: EditText
    private lateinit var telefonoEditText: EditText
    private lateinit var correoEditText: EditText
    private lateinit var contraseniaEditText: EditText
    private lateinit var contraseniaConfEditText: EditText
    private lateinit var generoSpinner: Spinner
    private lateinit var tipoDiabetesSpinner: Spinner

    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nombreEditText = binding.txtNombre
        edadEditText = binding.txtEdad
        telefonoEditText = binding.txtTelefono
        correoEditText = binding.txtCorreo
        contraseniaEditText = binding.txtContrasenia
        contraseniaConfEditText = binding.txtContraseniaConf
        generoSpinner = binding.genero
        tipoDiabetesSpinner = binding.tipoDiab
        auth = FirebaseAuth.getInstance()
        sharedPreferences = requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)

        val btnSaveChanges = binding.btnSaveChanges
        btnSaveChanges.setOnClickListener {

        }

        val btnLogout = binding.btnLogout
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}