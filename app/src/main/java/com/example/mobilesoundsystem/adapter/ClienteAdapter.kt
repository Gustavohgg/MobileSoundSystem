package com.example.mobilesoundsystem.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilesoundsystem.R
import com.example.mobilesoundsystem.model.Cliente

class ClienteAdapter(
    private val clientes: List<Cliente>,
    private val onEditClick: (Cliente) -> Unit,
    private val onDeleteClick: (Cliente) -> Unit
) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.txtNome)
        val cpf: TextView = itemView.findViewById(R.id.txtCpf)
        val telefone: TextView = itemView.findViewById(R.id.txtTelefone)
        val email: TextView = itemView.findViewById(R.id.txtEmail)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        val btnExcluir: Button = itemView.findViewById(R.id.btnExcluir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = clientes[position]
        holder.nome.text = cliente.nome
        holder.cpf.text = cliente.cpf
        holder.telefone.text = cliente.telefone
        holder.email.text = cliente.email

        holder.btnEditar.setOnClickListener { onEditClick(cliente) }
        holder.btnExcluir.setOnClickListener { onDeleteClick(cliente) }
    }

    override fun getItemCount(): Int = clientes.size
}
