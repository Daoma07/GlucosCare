package alameda.daniel.glucoscare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlimentoAdapter(private val alimentos: List<Alimento>) : RecyclerView.Adapter<AlimentoAdapter.AlimentoViewHolder>() {

    class AlimentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.text_nombre_alimento)
        val cantidadTextView: TextView = itemView.findViewById(R.id.text_cantidad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlimentoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alimento, parent, false)
        return AlimentoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlimentoViewHolder, position: Int) {
        val currentAlimento = alimentos[position]
        holder.nombreTextView.text = currentAlimento.nombre
        holder.cantidadTextView.text = currentAlimento.cantidad.toString()
    }

    override fun getItemCount(): Int {
        return alimentos.size
    }
}