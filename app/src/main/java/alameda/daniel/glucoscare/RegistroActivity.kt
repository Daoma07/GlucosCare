package alameda.daniel.glucoscare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class RegistroActivity : AppCompatActivity() {
    lateinit var genero: Spinner
    lateinit var tipoDiabetes: Spinner
    lateinit var  adapterGenero: ArrayAdapter<CharSequence>
    lateinit var  adapterDiabetes: ArrayAdapter<CharSequence>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        genero = findViewById(R.id.genero);
        adapterGenero = ArrayAdapter.createFromResource(this, R.array.genero_items, android.R.layout.simple_spinner_item)
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genero.setAdapter(adapterGenero)
        genero.setSelection(0)

        tipoDiabetes = findViewById(R.id.tipoDiab)
        adapterDiabetes = ArrayAdapter.createFromResource(this, R.array.daibete_items, android.R.layout.simple_spinner_item)
        adapterDiabetes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipoDiabetes.setAdapter(adapterDiabetes)
        tipoDiabetes.setSelection(0)
    }
}