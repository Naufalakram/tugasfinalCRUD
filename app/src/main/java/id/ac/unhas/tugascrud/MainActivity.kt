package id.ac.unhas.tugascrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var tambah : Button
    private lateinit var lihat : Button
    private lateinit var update : Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: AdapterCRUD? = null
    private var data: ModelCRUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        tambah.setOnClickListener { tambahOrang() }
        lihat.setOnClickListener { lihatOrang() }
        update.setOnClickListener { updateOrang() }

        adapter?.setOnClickItem {
            Toast.makeText(this, it.username, Toast.LENGTH_SHORT).show()

            username.setText(it.username)
            email.setText(it.email)
            data = it
        }
    }

    private fun tambahOrang() {
        val nama = username.text.toString()
        val email = email.text.toString()

        if (nama.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Nama Wajib diisi", Toast.LENGTH_SHORT).show()
        } else {
            val data = ModelCRUD(username = nama, email = email)
            val status = sqLiteHelper.insertData(data)

            if(status > -1) {
                Toast.makeText(this, "Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                clearEditText()
                tambahOrang()
            } else {
                Toast.makeText(this, "Gagal menaambahkan ", Toast.LENGTH_SHORT).show()
            }
        }

        adapter?.setOnClickDeleteItem {
            deleteData(it.id)
        }

    }

    private fun updateOrang() {
        val nama = username.text.toString()
        val email = email.text.toString()

        if(nama == data?.username && email == data?.email) {
            Toast.makeText(this, "Data Berhasil Diupdate!!", Toast.LENGTH_SHORT).show()
            return
        }

        if(data == null) return

        val data = ModelCRUD(id = data!!.id, username = nama, email = email)
        val status = sqLiteHelper.updateData(data)
        if(status > -1) {
            clearEditText()
            lihatOrang()
        }
    }

    private fun lihatOrang() {
        val dataList = sqLiteHelper.getAllData()
        Log.e("USER", "${dataList.size}")

        adapter?.addItem(dataList)
    }

    private fun deleteData(id: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Anda Yakin Ingin Menghapus Ini ?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yakin") { dialog, _ ->
            sqLiteHelper.deleteDataById(id)
            lihatOrang()
            dialog.dismiss()
        }
        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()

    }

    private fun clearEditText() {
        username.setText("")
        email.setText("")
        username.requestFocus()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdapterCRUD()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        username = findViewById(R.id.username)
        email = findViewById(R.id.email)
        tambah = findViewById(R.id.tambah)
        lihat = findViewById(R.id.lihat)
        update = findViewById(R.id.update)
        recyclerView = findViewById(R.id.recyclerView)
    }
}