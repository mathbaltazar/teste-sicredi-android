package br.com.testedevandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.testedevandroid.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setupActionBarWithNavController(fragment.navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}