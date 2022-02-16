package ru.samitin.mytranslater.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import org.koin.android.viewmodel.ext.android.viewModel

import ru.samitin.core.BaseActivity
import ru.samitin.history.view.history.HistoryActivity
import ru.samitin.model.data.DataModel
import ru.samitin.mytranslater.R
import ru.samitin.mytranslater.databinding.ActivityMainBinding
import ru.samitin.mytranslater.model.data.AppState

import ru.samitin.mytranslater.view.main.adapter.MainAdapter
import java.lang.IllegalStateException
import ru.samitin.mytranslater.utils.convertMeaningsToString
import ru.samitin.mytranslater.utils.network.isOnline
import ru.samitin.mytranslater.view.descriptionScreen.DescriptionActivity


private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    private lateinit var binding: ActivityMainBinding
    override val model: MainViewModel by currentScope.inject<MainViewModel>()
    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }
    private val fabClickListener: View.OnClickListener =
        View.OnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        convertMeaningsToString(data.meanings!!),
                        data.meanings!![0].imageUrl
                    )
                )
            }
        }
    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                isNetworkAvailable = isOnline(applicationContext)
                if (isNetworkAvailable) {
                    model.getData(searchWord, isNetworkAvailable)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iniViewModel()
        initViews()
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun iniViewModel() {
        if (binding.mainActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
    }

    private fun initViews() {
        binding.searchFab.setOnClickListener(fabClickListener)
        binding.mainActivityRecyclerview.adapter = adapter
    }
}
