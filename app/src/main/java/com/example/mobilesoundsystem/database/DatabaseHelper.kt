package com.example.mobilesoundsystem.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mobilesoundsystem.model.Cliente
import com.example.mobilesoundsystem.model.Gerente
import com.example.mobilesoundsystem.model.Produto
import com.example.mobilesoundsystem.model.Venda

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "db_mobile_sound_system", null, 3) {

    companion object {
        // Tabela de Usuários
        private const val TABLE_USUARIOS = "usuarios"
        private const val COL_ID = "id"
        private const val COL_EMAIL_USUARIO = "email"
        private const val COL_SENHA = "senha"
        private const val COL_TIPO_USUARIO = "tipo_usuario"

        // Tipos de Usuários
        const val DIRETOR = "Diretor"
        const val GERENTE = "Gerente"
        const val VENDEDOR = "Vendedor"

        // Tabela Clientes
        private const val TABLE_CLIENTES = "clientes"
        private const val COL_ID_CLIENTE = "id_cliente"
        private const val COL_NOME_CLIENTE = "nome"
        private const val COL_CPF_CLIENTE = "cpf"
        private const val COL_TELEFONE_CLIENTE = "telefone"
        private const val COL_EMAIL_CLIENTE = "email"

        // Tabela de Produtos
        private const val TABLE_PRODUTOS = "produtos"
        private const val COL_CODIGO = "codigo"
        private const val COL_NOME_PRODUTO = "nome"
        private const val COL_CATEGORIA = "categoria"
        private const val COL_QUANTIDADE = "quantidade"
        private const val COL_PRECO = "preco"
        private const val COL_QUANTIDADE_MINIMA = "quantidade_minima"
        private const val COL_QUANTIDADE_MAXIMA = "quantidade_maxima"

        // Tabela de Vendas
        private const val TABLE_VENDAS = "vendas"
        private const val COL_ID_VENDA = "id_venda"
        private const val COL_DATA = "data"
        private const val COL_CLIENTE = "cliente"
        private const val COL_CPF = "cpf"
        private const val COL_TOTAL = "total"

        // Tabela de Itens de Venda
        private const val TABLE_ITENS_VENDA = "itens_venda"
        private const val COL_ID_ITEM_VENDA = "id_item_venda"
        private const val COL_ID_VENDA_ITEM = "id_venda"
        private const val COL_CODIGO_PRODUTO = "codigo_produto"
        private const val COL_QUANTIDADE_ITEM = "quantidade"
        private const val COL_DESCONTO = "desconto"
        private const val COL_PRECO_ITEM = "preco"

        // Tabela de Gerentes
        private const val TABLE_GERENTES = "gerentes"
        private const val COL_ID_GERENTE = "id_gerente"
        private const val COL_NOME_GERENTE = "nome"
        private const val COL_DATA_NASC = "data_nasc"
        private const val COL_TELEFONE = "telefone"
        private const val COL_ENDERECO = "endereco"
        private const val COL_EMAIL_GERENTE = "email"
        private const val COL_SALARIO = "salario"
    }


    override fun onCreate(db: SQLiteDatabase) {
        // Criação da tabela de usuários
        val createTableUsuarios = """
            CREATE TABLE $TABLE_USUARIOS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_EMAIL_USUARIO TEXT NOT NULL UNIQUE,
                $COL_SENHA TEXT NOT NULL,
                $COL_TIPO_USUARIO TEXT NOT NULL
            )
        """

        // Criação da tabela de clientes
        val createTableClientes = """
            CREATE TABLE $TABLE_CLIENTES (
                $COL_ID_CLIENTE INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOME_CLIENTE TEXT NOT NULL,
                $COL_CPF_CLIENTE TEXT NOT NULL UNIQUE,
                $COL_TELEFONE_CLIENTE TEXT,
                $COL_EMAIL_CLIENTE TEXT
            )
        """

        // Criação da tabela de produtos
        val createTableProdutos = """
            CREATE TABLE $TABLE_PRODUTOS (
                $COL_CODIGO TEXT PRIMARY KEY,
                $COL_NOME_PRODUTO TEXT,
                $COL_CATEGORIA TEXT,
                $COL_QUANTIDADE INTEGER,
                $COL_PRECO REAL,
                $COL_QUANTIDADE_MINIMA INTEGER,
                $COL_QUANTIDADE_MAXIMA INTEGER
            )
        """

        // Criação da tabela de vendas
        val createTableVendas = """
            CREATE TABLE $TABLE_VENDAS (
                $COL_ID_VENDA INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_DATA TEXT NOT NULL,
                $COL_CLIENTE TEXT NOT NULL,
                $COL_CPF TEXT NOT NULL,
                $COL_TOTAL REAL NOT NULL
            )
        """

        // Criação da tabela de itens de venda
        val createTableItensVenda = """
            CREATE TABLE $TABLE_ITENS_VENDA (
                $COL_ID_ITEM_VENDA INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_ID_VENDA_ITEM INTEGER NOT NULL,
                $COL_CODIGO_PRODUTO TEXT NOT NULL,
                $COL_QUANTIDADE_ITEM INTEGER NOT NULL,
                $COL_DESCONTO REAL,
                $COL_PRECO_ITEM REAL NOT NULL,
                FOREIGN KEY ($COL_ID_VENDA_ITEM) REFERENCES $TABLE_VENDAS($COL_ID_VENDA),
                FOREIGN KEY ($COL_CODIGO_PRODUTO) REFERENCES $TABLE_PRODUTOS($COL_CODIGO)
            )
        """

        // Criação da tabela de gerentes
        val createTableGerentes = """
            CREATE TABLE $TABLE_GERENTES (
                $COL_ID_GERENTE TEXT PRIMARY KEY,
                $COL_NOME_GERENTE TEXT NOT NULL,
                $COL_DATA_NASC TEXT NOT NULL,
                $COL_TELEFONE TEXT NOT NULL,
                $COL_ENDERECO TEXT NOT NULL,
                $COL_EMAIL_GERENTE TEXT NOT NULL, 
                $COL_SALARIO REAL NOT NULL
            )
        """

        // Executando a criação das tabelas
        db.execSQL(createTableUsuarios)
        db.execSQL(createTableClientes)
        db.execSQL(createTableProdutos)
        db.execSQL(createTableVendas)
        db.execSQL(createTableItensVenda)
        db.execSQL(createTableGerentes)

        // Inserção de dados iniciais para a tabela de usuários
        val insertData = """
            INSERT INTO $TABLE_USUARIOS ($COL_EMAIL_USUARIO, $COL_SENHA, $COL_TIPO_USUARIO) 
            VALUES 
            ('diretor@email.com', '12345', '$DIRETOR'),
            ('gerente@email.com', '12345', '$GERENTE'),
            ('vendedor@email.com', '12345', '$VENDEDOR')
        """
        db.execSQL(insertData)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 3) {
            // Migração para a versão 2: adicionar a coluna email à tabela de gerentes
            db.execSQL("ALTER TABLE $TABLE_GERENTES ADD COLUMN $COL_EMAIL_GERENTE TEXT")
        }
        // Outras migrações podem ser adicionadas aqui
    }

    // Funções para Usuários
    fun verificarLogin(email: String, senha: String, tipoUsuario: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USUARIOS WHERE $COL_EMAIL_USUARIO = ? AND $COL_SENHA = ? AND $COL_TIPO_USUARIO = ?",
            arrayOf(email, senha, tipoUsuario)
        )
        val isValid = cursor.moveToFirst()
        cursor.close()
        return isValid
    }

    // Funções para Clientes
    fun inserirCliente(cliente: Cliente): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOME_CLIENTE, cliente.nome)
            put(COL_CPF_CLIENTE, cliente.cpf)
            put(COL_TELEFONE_CLIENTE, cliente.telefone)
            put(COL_EMAIL_CLIENTE, cliente.email)
        }
        return db.insert(TABLE_CLIENTES, null, values) > 0
    }

    fun atualizarCliente(cliente: Cliente): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOME_CLIENTE, cliente.nome)
            put(COL_CPF_CLIENTE, cliente.cpf)
            put(COL_TELEFONE_CLIENTE, cliente.telefone)
            put(COL_EMAIL_CLIENTE, cliente.email)
        }
        val result = db.update(TABLE_CLIENTES, values, "$COL_ID_CLIENTE = ?", arrayOf(cliente.id.toString()))
        return result > 0
    }

    fun removerCliente(id: Int): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_CLIENTES, "$COL_ID_CLIENTE = ?", arrayOf(id.toString()))
        return result > 0
    }

    fun buscarClientes(): List<Cliente> {
        val clientes = mutableListOf<Cliente>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_CLIENTES", null)

        if (cursor.moveToFirst()) {
            do {
                val cliente = Cliente(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_CLIENTE)),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOME_CLIENTE)),
                    cpf = cursor.getString(cursor.getColumnIndexOrThrow(COL_CPF_CLIENTE)),
                    telefone = cursor.getString(cursor.getColumnIndexOrThrow(COL_TELEFONE_CLIENTE)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL_CLIENTE))
                )
                clientes.add(cliente)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return clientes
    }

    fun buscarClientePorCpf(cpf: String): Cliente? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_CLIENTES WHERE $COL_CPF_CLIENTE = ?",
            arrayOf(cpf)
        )
        return if (cursor.moveToFirst()) {
            val cliente = Cliente(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_CLIENTE)),
                nome = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOME_CLIENTE)),
                cpf = cursor.getString(cursor.getColumnIndexOrThrow(COL_CPF_CLIENTE)),
                telefone = cursor.getString(cursor.getColumnIndexOrThrow(COL_TELEFONE_CLIENTE)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL_CLIENTE))
            )
            cursor.close()
            cliente
        } else {
            cursor.close()
            null
        }
    }

    // Funções para Produtos
    fun registrarProduto(produto: Produto): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_CODIGO, produto.codigo)
            put(COL_NOME_PRODUTO, produto.nome)
            put(COL_CATEGORIA, produto.categoria)
            put(COL_QUANTIDADE, produto.quantidade)
            put(COL_PRECO, produto.preco)
            put(COL_QUANTIDADE_MINIMA, produto.quantidadeMinima)
            put(COL_QUANTIDADE_MAXIMA, produto.quantidadeMaxima)
        }
        return db.insert(TABLE_PRODUTOS, null, values)
    }

    fun atualizarProdutoEstoque(codigo: String, novaQuantidade: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_QUANTIDADE, novaQuantidade)
        }
        val result = db.update(TABLE_PRODUTOS, values, "$COL_CODIGO = ?", arrayOf(codigo))
        return result > 0
    }

    fun buscarProdutos(): List<Produto> {
        val produtos = mutableListOf<Produto>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_PRODUTOS", null)
        while (cursor.moveToNext()) {
            val produto = Produto(
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(COL_CODIGO)),
                nome = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOME_PRODUTO)),
                categoria = cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORIA)),
                quantidade = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QUANTIDADE)),
                preco = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRECO)),
                quantidadeMinima = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QUANTIDADE_MINIMA)),
                quantidadeMaxima = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QUANTIDADE_MAXIMA))
            )
            produtos.add(produto)
        }
        cursor.close()
        return produtos
    }

    // Funções para Vendas
    fun registrarVenda(venda: Venda): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_DATA, venda.data)
            put(COL_CLIENTE, venda.cliente)
            put(COL_CPF, venda.cpf)
            put(COL_TOTAL, venda.total)
        }
        val id = db.insert(TABLE_VENDAS, null, values)
        return id.toInt()
    }

    fun registrarItensVenda(idVenda: Long, codigoProduto: String, quantidade: Int, desconto: Double, preco: Double): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_ID_VENDA_ITEM, idVenda)
            put(COL_CODIGO_PRODUTO, codigoProduto)
            put(COL_QUANTIDADE_ITEM, quantidade)
            put(COL_DESCONTO, desconto)
            put(COL_PRECO_ITEM, preco)
        }
        return db.insert(TABLE_ITENS_VENDA, null, values)
    }

    fun buscarVendas(): List<Venda> {
        val vendas = mutableListOf<Venda>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_VENDAS", null)

        if (cursor.moveToFirst()) {
            do {
                val venda = Venda(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_VENDA)),
                    data = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATA)),
                    cliente = cursor.getString(cursor.getColumnIndexOrThrow(COL_CLIENTE)),
                    cpf = cursor.getString(cursor.getColumnIndexOrThrow(COL_CPF)),
                    total = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TOTAL))
                )
                vendas.add(venda)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return vendas
    }

    // Funções para Gerentes
    fun salvarGerente(gerente: Gerente): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_ID_GERENTE, gerente.idGerente)
            put(COL_NOME_GERENTE, gerente.nome)
            put(COL_DATA_NASC, gerente.dataNasc)
            put(COL_TELEFONE, gerente.telefone)
            put(COL_ENDERECO, gerente.endereco)
            put(COL_EMAIL_GERENTE, gerente.email) // Adicionando o campo email
            put(COL_SALARIO, gerente.salario)
        }
        return db.insert(TABLE_GERENTES, null, values) > 0
    }

    fun alterarGerente(gerente: Gerente): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOME_GERENTE, gerente.nome)
            put(COL_DATA_NASC, gerente.dataNasc)
            put(COL_TELEFONE, gerente.telefone)
            put(COL_ENDERECO, gerente.endereco)
            put(COL_EMAIL_GERENTE, gerente.email) // Adicionando o campo email
            put(COL_SALARIO, gerente.salario)
        }
        val result = db.update(TABLE_GERENTES, values, "$COL_ID_GERENTE = ?", arrayOf(gerente.idGerente))
        return result > 0
    }

    fun excluirGerente(idGerente: String): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_GERENTES, "$COL_ID_GERENTE = ?", arrayOf(idGerente))
        return result > 0
    }
}