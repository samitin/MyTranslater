package ru.samitin.mytranslater.view.main

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.scope.currentScope
import ru.samitin.core.BaseActivity
import ru.samitin.history.view.history.HistoryActivity
import ru.samitin.model.data.DataModel
import ru.samitin.model.dataDto.SearchResultDto
import ru.samitin.mytranslater.R
import ru.samitin.mytranslater.model.data.AppState
import ru.samitin.mytranslater.view.main.adapter.MainAdapter
import java.lang.IllegalStateException
import ru.samitin.mytranslater.view.descriptionScreen.DescriptionActivity
import ru.samitin.utils.ui.viewById


private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    private val mainActivityRecyclerView by viewById<RecyclerView>(R.id.main_activity_recyclerview)
    private val searchFAB by viewById<FloatingActionButton>(R.id.search_fab)
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
                        data.meanings[0].translatedMeaning.translatedMeaning,
                        data.meanings!![0].imageUrl
                    )
                )
            }
        }
    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                if (isNetworkAvailable) {
                    model.getData(searchWord, isNetworkAvailable)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDefaultSplashScreen()
        iniViewModel()
        initViews()
    }
    private fun setDefaultSplashScreen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            setSplashScreenHideAnimation()
        setSplashScreenDuration()
    }
    @RequiresApi(31)
    private fun setSplashScreenHideAnimation(){
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideLeft = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.height.toFloat()
            )
            slideLeft.interpolator = AnticipateInterpolator()
            slideLeft.duration = 1000L

            slideLeft.doOnEnd { splashScreenView.remove() }
            slideLeft.start()
        }
    }

    private fun setSplashScreenDuration(){
        var isHideSplashScreen = false

        object :CountDownTimer(2000,1000){
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                isHideSplashScreen =true
            }
        }.start()

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener (
            object : ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {
                    return if (isHideSplashScreen){
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    }else{
                        false
                    }
                }
            }
        )
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
        if (mainActivityRecyclerView.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
    }

    private fun initViews() {
        searchFAB.setOnClickListener(fabClickListener)
        mainActivityRecyclerView.adapter = adapter
    }
}
