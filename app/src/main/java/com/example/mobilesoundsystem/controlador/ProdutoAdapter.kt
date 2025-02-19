package com.example.mobilesoundsystem.controlador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilesoundsystem.R
import com.example.mobilesoundsystem.model.Produto

class ProdutoAdapter(
    private var listaProdutos: MutableList<Produto>, // Usando MutableList para permitir atualizações
    private val onItemClick: (Produto) -> Unit // Callback para clique no item
) : RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    // Método para criar o ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_produto, parent, false)
        return ProdutoViewHolder(view)
    }

    // Método para vincular os dados ao ViewHolder
    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val produto = listaProdutos[position]
        holder.bind(produto) // Vincula os dados do produto ao ViewHolder
        holder.itemView.setOnClickListener { onItemClick(produto) } // Configura o clique no item
    }

    // Retorna o número de itens na lista
    override fun getItemCount(): Int {
        return listaProdutos.size
    }

    // Método para atualizar a lista de produtos
    fun atualizarLista(novaLista: List<Produto>) {
        listaProdutos.clear()
        listaProdutos.addAll(novaLista)
        notifyDataSetChanged() // Notifica o adapter sobre as mudanças
    }

    // ViewHolder para o ProdutoAdapter
    class ProdutoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textNomeProduto: TextView = itemView.findViewById(R.id.textNomeProduto)
        private val textEstoque: TextView = itemView.findViewById(R.id.textEstoque)
        private val textMinimaMaxima: TextView = itemView.findViewById(R.id.textMinimaMaxima)

        // Método para vincular os dados do produto ao ViewHolder
        fun bind(produto: Produto) {
            textNomeProduto.text = produto.nome
            textEstoque.text = "Estoque: ${produto.quantidade}"
            textMinimaMaxima.text = "Min: ${produto.quantidadeMinima} - Max: ${produto.quantidadeMaxima}"
        }
    }
}