package alameda.daniel.glucoscare.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
//import alameda.daniel.glucoscare.databinding.FragmentInteresadosBinding
import alameda.daniel.glucoscare.databinding.ActivityEditProfileBinding
class AjustesFragment : Fragment() {

    private var _binding: ActivityEditProfileBinding? = null

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

        _binding = ActivityEditProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}