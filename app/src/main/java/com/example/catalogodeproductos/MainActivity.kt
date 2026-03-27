package com.example.catalogodeproductos

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity(), MenuFragment.OnOptionClickListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configuración de la Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configuración del Drawer (Menú lateral oculto)
        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Migración de onBackPressed a OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    // Si el menú no está abierto, permitimos el comportamiento normal (salir de la app)
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            }
        })

        // Cargar el perfil por defecto al iniciar
        if (savedInstanceState == null) {
            onOptionClicked("profile")
        }
    }

    // Método que se activa al elegir una opción del menú o del footer
    override fun onOptionClicked(option: String) {
        val fragment: Fragment = when (option) {
            "photos" -> PhotosFragment()
            "video" -> VideoFragment()
            "web" -> WebFragment()
            "buttons" -> ButtonsFragment()
            else -> ProfileFragment()
        }

        // Reemplazar el fragmento actual
        supportFragmentManager.commit {
            replace(R.id.content_fragment_container, fragment)
            setReorderingAllowed(true)
        }

        // Cerrar el menú lateral después de seleccionar
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }
}
