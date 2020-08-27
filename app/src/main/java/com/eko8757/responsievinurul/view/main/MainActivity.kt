package com.eko8757.responsievinurul.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eko8757.responsievinurul.R
import com.eko8757.responsievinurul.adapter.AdapterData
import com.eko8757.responsievinurul.model.data.ResultData
import com.eko8757.responsievinurul.presenter.data.PresenterData
import com.eko8757.responsievinurul.service.BaseApi
import com.eko8757.responsievinurul.utils.StaticString
import com.eko8757.responsievinurul.utils.gone
import com.eko8757.responsievinurul.utils.visible
import com.eko8757.responsievinurul.view.detail.DetailActivity
import com.eko8757.responsievinurul.view.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataView, MainView, AdapterData.OnItemClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: AdapterData
    private lateinit var presenter: PresenterData
    private lateinit var dataGlobal: ArrayList<ResultData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        val factory: BaseApi = BaseApi.create()
        presenter = PresenterData(this, this, factory)
        presenter.getList()
    }

    override fun showLoading() {
        progressBar_list.visible()
    }

    override fun hideLoading() {
        progressBar_list.gone()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout -> logout()
        }
        return true
    }

    private fun logout() {
        Prefs.remove(StaticString().UUID)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    override fun showDataList(data: ArrayList<ResultData>) {
        adapter = AdapterData(data)
        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list.adapter = adapter
        adapter.setOnItemClickListener(this)
        adapter.notifyDataSetChanged()
        this.dataGlobal = data
    }

    override fun onItemClick(position: Int) {
        val i = Intent(this, DetailActivity::class.java)
        i.putExtra("DataList", dataGlobal[position])
        startActivity(i)
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}