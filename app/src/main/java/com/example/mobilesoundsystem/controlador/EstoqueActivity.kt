package com.example.mobilesoundsystem.controlador

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilesoundsystem.R
import com.example.mobilesoundsystem.database.DatabaseHelper
import com.example.mobilesoundsystem.model.Produto

class EstoqueActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var produtoAdapter: ProdutoAdapter
    private lateinit var listaProdutos: MutableList<Produto>

    // Componentes do layout
    private lateinit var recyclerViewEstoque: RecyclerView
    private lateinit var edtPesquisarProduto: EditText
    private lateinit var spinnerOrdenacao: Spinner
    private lateinit var textQuantidadeEstoque: TextView
    private lateinit var buttonSalvarEstoque: Button
    private lateinit var buttonAtualizarEstoque: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estoque)

        dbHelper = DatabaseHelper(this)
        listaProdutos = mutableListOf()

        // Inicializando os componentes da interface
        recyclerViewEstoque = findViewById(R.id.recyclerViewEstoque)
        edtPesquisarProduto = findViewById(R.id.edtPesquisarProduto)
        spinnerOrdenacao = findViewById(R.id.spinnerOrdenacao)
        textQuantidadeEstoque = findViewById(R.id.textQuantidadeEstoque)
        buttonSalvarEstoque = findViewById(R.id.buttonSalvarEstoque)
        buttonAtualizarEstoque = findViewById(R.id.buttonAtualizarEstoque)

        // Configurar RecyclerView
        recyclerViewEstoque.layoutManager = LinearLayoutManager(this)
        produtoAdapter = ProdutoAdapter(listaProdutos) { produtoSelecionado ->
            // Aqui você pode abrir uma tela de edição, exibir um Toast, etc.
            Toast.makeText(this, "Produto selecionado: ${produtoSelecionado.nome}", Toast.LENGTH_SHORT).show()
        }
        recyclerViewEstoque.adapter = produtoAdapter

        // Carregar produtos do banco de dados
        carregarProdutos()

        // Filtragem ao digitar na barra de pesquisa
        edtPesquisarProduto.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filtrarProdutos(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Ordenação do estoque via Spinner
        spinnerOrdenacao.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                ordenarProdutos(spinnerOrdenacao.selectedItem.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Botão para salvar um novo produto no estoque
        buttonSalvarEstoque.setOnClickListener {
            salvarProduto()
        }

        // Botão para atualizar estoque
        buttonAtualizarEstoque.setOnClickListener {
            atualizarEstoque()
        }
    }

    // Carregar a lista de produtos do banco
    private fun carregarProdutos() {
        listaProdutos.clear()
        listaProdutos.addAll(dbHelper.buscarProdutos())
        produtoAdapter.notifyDataSetChanged()
    }

    // Filtrar produtos por nome ou código
    private fun filtrarProdutos(query: String) {
        val produtosFiltrados = listaProdutos.filter {
            it.nome.contains(query, ignoreCase = true) || it.codigo.contains(query, ignoreCase = true)
        }
        produtoAdapter.atualizarLista(produtosFiltrados)
    }

    // Ordenar produtos conforme a seleção no Spinner
    private fun ordenarProdutos(criterio: String) {
        when (criterio) {
            "Nome" -> listaProdutos.sortBy { it.nome }
            "Quantidade" -> listaProdutos.sortByDescending { it.quantidade }
            "Categoria" -> listaProdutos.sortBy { it.categoria }
        }
        produtoAdapter.notifyDataSetChanged()
    }

    // Função para salvar um novo produto no estoque
    private fun salvarProduto() {
        val nome = edtPesquisarProduto.text.toString()
        if (nome.isEmpty()) {
            Toast.makeText(this, "Preencha o nome do produto!", Toast.LENGTH_SHORT).show()
            return
        }

        val produto = Produto(
            codigo = "P${System.currentTimeMillis()}",
            nome = nome,
            categoria = "Geral",
            quantidade = 10,
            preco = 5.0,
            quantidadeMinima = 2,  // Adicione um valor padrão
            quantidadeMaxima = 50   // Adicione um valor padrão
        )


        dbHelper.registrarProduto(produto)
        Toast.makeText(this, "Produto salvo!", Toast.LENGTH_SHORT).show()
        carregarProdutos()
    }

    // Atualizar estoque de um produto específico
    private fun atualizarEstoque() {
        val nomeProduto = edtPesquisarProduto.text.toString()
        val novaQuantidade = textQuantidadeEstoque.text.toString().toIntOrNull()

        if (nomeProduto.isEmpty() || novaQuantidade == null) {
            Toast.makeText(this, "Digite o nome e a nova quantidade!", Toast.LENGTH_SHORT).show()
            return
        }

        val atualizado = dbHelper.atualizarProdutoEstoque(nomeProduto, novaQuantidade)
        if (atualizado) {
            Toast.makeText(this, "Estoque atualizado!", Toast.LENGTH_SHORT).show()
            carregarProdutos()
        } else {
            Toast.makeText(this, "Produto não encontrado!", Toast.LENGTH_SHORT).show()
        }
    }

}
