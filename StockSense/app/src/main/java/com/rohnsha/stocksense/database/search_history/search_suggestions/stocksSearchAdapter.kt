package com.rohnsha.stocksense.database.search_history.search_suggestions

import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.rohnsha.stocksense.R
import com.rohnsha.stocksense.database.search_history.bottomSheetSearchDelete
import com.rohnsha.stocksense.database.search_history.search_history
import com.rohnsha.stocksense.database.search_history.search_history_model
import com.rohnsha.stocksense.stocksInfo
import com.rohnsha.stocksense.database.watchlist_db.watchlistsVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class stocksSearchAdapter(private val application: Application): RecyclerView.Adapter<stocksSearchAdapter.stockSearchViewHolder>() {

    private var stockLists= emptyList<search>()
    private val mHistoryViewModel: search_history_model = ViewModelProvider.AndroidViewModelFactory
        .getInstance(application)
        .create(
        search_history_model::class.java
    )
    private val mWatchlistViewModel: watchlistsVM = ViewModelProvider.AndroidViewModelFactory
        .getInstance(application)
        .create(
            watchlistsVM::class.java
        )

    class stockSearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): stockSearchViewHolder {
        return stockSearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_search_history, parent, false))
    }

    override fun getItemCount(): Int {
        return stockLists.size
    }

    override fun onBindViewHolder(holder: stockSearchViewHolder, position: Int) {
        val currentStock= stockLists[position]

        holder.itemView.apply {
            val density = resources.displayMetrics.density
            val paddingImgView= (8 * density).toInt()

            findViewById<TextView>(R.id.tvHistory).text= currentStock.company
            findViewById<ImageView>(R.id.logoHistory).setImageResource(R.drawable.baseline_troubleshoot_24)
            findViewById<ImageView>(R.id.logoHistory).setPadding(paddingImgView)
            findViewById<TextView>(R.id.tvIndexSearch).visibility= View.GONE

            setOnLongClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val wlData= mWatchlistViewModel.searchWatchlistsDB(currentStock.yFinanceSymbol.uppercase())
                    Log.d("searchWL", wlData.isEmpty().toString())
                    withContext(Dispatchers.Main){
                        val deleteSheet= bottomSheetSearchDelete(
                            id_search = 0,
                            search_symbol = currentStock.yFinanceSymbol,
                            isSearch = false,
                            brandName= currentStock.company,
                            wlData= wlData,
                            isPresent = wlData.isNotEmpty()
                            )
                        deleteSheet.show((holder.itemView.context as AppCompatActivity).supportFragmentManager, deleteSheet.tag)
                    }
                }
                true
            }

            setOnClickListener {
                val intent= Intent(context, stocksInfo::class.java)
                intent.putExtra("symbol", currentStock.yFinanceSymbol.uppercase())
                context.startActivity(intent)
                val queryDB= search_history(0, currentStock.yFinanceSymbol)
                mHistoryViewModel.addHistory(queryDB)
            }

        }
    }

    fun setStocksList(stock: List<search>){
        this.stockLists= stock
        notifyDataSetChanged()
    }

}