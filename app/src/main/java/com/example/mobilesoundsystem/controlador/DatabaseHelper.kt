package com.example.mobilesoundsystem.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "LoginSystem.db"
        private const val DATABASE_VERSION = 1

        // Tabela de Usuários
        private const val TABLE_USUARIOS = "usuarios"
        private const val COL_ID = "id"
        private const val COL_EMAIL = "email"
        private const val COL_SENHA = "senha"
        private const val COL_TIPO_USUARIO = "tipo_usuario"

        // Tipos de Usuários
        const val DIRETOR = "Diretor"
        const val GERENTE = "Gerente"
        const val VENDEDOR = "Vendedor"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Criação da tabela de usuários
        val createTableUsuarios = """
            CREATE TABLE $TABLE_USUARIOS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_EMAIL TEXT NOT NULL UNIQUE,
                $COL_SENHA TEXT NOT NULL,
                $COL_TIPO_USUARIO TEXT NOT NULL
            )
        """
        db.execSQL(createTableUsuarios)

        // Inserção de dados iniciais
        val insertData = """
            INSERT INTO $TABLE_USUARIOS ($COL_EMAIL, $COL_SENHA, $COL_TIPO_USUARIO) 
            VALUES 
            ('diretor@email.com', '12345', '$DIRETOR'),
            ('gerente@email.com', '12345', '$GERENTE'),
            ('vendedor@email.com', '12345', '$VENDEDOR')
        """
        db.execSQL(insertData)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }

    // Função para adicionar um usuário
    fun adicionarUsuario(email: String, senha: String, tipoUsuario: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_EMAIL, email)
            put(COL_SENHA, senha)
            put(COL_TIPO_USUARIO, tipoUsuario)
        }
        return db.insert(TABLE_USUARIOS, null, values)
    }

    // Função para verificar o login
    fun verificarLogin(email: String, senha: String, tipoUsuario: String): Boolean {
        val db = readableDatabase
        val query = """
            SELECT * FROM $TABLE_USUARIOS 
            WHERE $COL_EMAIL = ? AND $COL_SENHA = ? AND $COL_TIPO_USUARIO = ?
        """
        val cursor = db.rawQuery(query, arrayOf(email, senha, tipoUsuario))
        val existe = cursor.moveToFirst()
        cursor.close()
        return existe
    }

    fun salvarGerente(idGerente: String, nome: String, dataNasc: String, telefone: String, endereco: String, email: String, salario: String): Boolean {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id_gerente", idGerente)
        contentValues.put("nome", nome)
        contentValues.put("data_nasc", dataNasc)
        contentValues.put("telefone", telefone)
        contentValues.put("endereco", endereco)
        contentValues.put("email", email)
        contentValues.put("salario", salario)

        val result = db.insert("gerente", null, contentValues)
        return result != -1L  // Retorna verdadeiro se a inserção for bem-sucedida
    }

}


