package id.ac.unhas.tugascrud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterCRUD : RecyclerView.Adapter<AdapterCRUD.CRUDViewHolder>() {

    private var dataList: ArrayList<ModelCRUD> = ArrayList()
    private var onClickItem: ((ModelCRUD) -> Unit)? = null
    private var onClickDeleteItem: ((ModelCRUD) -> Unit)? = null

    fun addItem(item: ArrayList<ModelCRUD>) {
        this.dataList = item
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (ModelCRUD) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (ModelCRUD) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CRUDViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
    )

    override fun onBindViewHolder(holder: AdapterCRUD.CRUDViewHolder, position: Int) {
        val data = dataList[position]
        holder.bindView(data)
        holder.itemView.setOnClickListener{
            onClickItem?.invoke(data)
            holder.hapusTombol.setOnClickListener{
                onClickDeleteItem?.invoke(data)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class CRUDViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var nama = view.findViewById<TextView>(R.id.tvUsername)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        var hapusTombol = view.findViewById<TextView>(R.id.hapus)

        fun bindView(ini: ModelCRUD) {
            id.text = ini.id.toString()
            nama.text = ini.username
            email.text = ini.email
        }
    }
}