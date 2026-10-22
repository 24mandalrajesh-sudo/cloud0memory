package com.example.cloudmemory

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val bg = Color.rgb(13, 17, 28)
    private val panel = Color.rgb(25, 32, 48)
    private val accent = Color.rgb(104, 128, 255)
    private val muted = Color.rgb(165, 176, 199)
    private val white = Color.rgb(245, 247, 255)
    private val PICK_LOCAL_FOLDER = 2001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        window.decorView.systemUiVisibility = 0
        render()
    }

    private fun render() {
        val scroll = ScrollView(this).apply { setBackgroundColor(bg) }
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(24), dp(22), dp(28))
        }
        scroll.addView(root)
        setContentView(scroll)

        root.addView(TextView(this).apply {
            text = "CLOUD MEMORY"
            textSize = 12f
            letterSpacing = 0.18f
            setTextColor(accent)
            typeface = Typeface.DEFAULT_BOLD
        })
        root.addView(TextView(this).apply {
            text = "Your files,\\none place."
            textSize = 32f
            setTextColor(white)
            typeface = Typeface.create("sans-serif", Typeface.BOLD)
            setPadding(0, dp(10), 0, dp(8))
        })
        root.addView(TextView(this).apply {
            text = "A unified dashboard for phone storage and cloud drives."
            textSize = 15f
            setTextColor(muted)
        })

        val summary = card()
        summary.addView(TextView(this).apply {
            text = "STORAGE OVERVIEW"
            textSize = 12f
            letterSpacing = 0.08f
            setTextColor(muted)
        })
        summary.addView(TextView(this).apply {
            text = "Phone storage"
            textSize = 17f
            setTextColor(white)
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, dp(12), 0, dp(3))
        })
        summary.addView(TextView(this).apply {
            text = "Browse local folders using Android's secure folder picker."
            textSize = 13f
            setTextColor(muted)
        })
        summary.addView(actionButton("Browse phone files") { openLocalFolderPicker() })
        root.addView(summary, marginTop(24))

        root.addView(sectionTitle("CONNECTED CLOUDS"), marginTop(24))
        root.addView(cloudCard("Google Drive", "Sign-in integration needs Google OAuth setup.", "Connect Google Drive") {
            showInfo("Google Drive connection requires an OAuth client ID and Drive API setup.")
        }, marginTop(10))
        root.addView(cloudCard("TeraBox", "Integration depends on an authorized, supported API.", "Set up TeraBox") {
            showInfo("TeraBox requires an official supported API or approved integration. This starter does not collect your TeraBox password.")
        }, marginTop(10))

        root.addView(sectionTitle("WHAT THIS APP CAN DO"), marginTop(24))
        root.addView(TextView(this).apply {
            text = "• One dashboard for supported cloud services\\n• Local folder access through Android\\n• Designed for future uploads, downloads and offline files\\n\\nCloud drives are not merged into Android's physical internal memory. Supported files can instead be opened or managed through app and Android file-provider features."
            textSize = 14f
            setTextColor(muted)
            setLineSpacing(dp(5).toFloat(), 1f)
            setPadding(dp(4), dp(12), dp(4), 0)
        })
        root.addView(TextView(this).apply {
            text = "EARLY PREVIEW  •  v0.1.0"
            textSize = 11f
            letterSpacing = 0.08f
            setTextColor(Color.rgb(110, 121, 146))
            gravity = Gravity.CENTER
            setPadding(0, dp(30), 0, dp(8))
        })
    }

    private fun card(): LinearLayout = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(17), dp(17), dp(17), dp(17))
        setBackgroundColor(panel)
    }

    private fun cloudCard(title: String, description: String, button: String, click: () -> Unit): LinearLayout {
        val box = card()
        box.addView(TextView(this).apply {
            text = title
            textSize = 18f
            setTextColor(white)
            typeface = Typeface.DEFAULT_BOLD
        })
        box.addView(TextView(this).apply {
            text = description
            textSize = 13f
            setTextColor(muted)
            setPadding(0, dp(7), 0, dp(4))
        })
        box.addView(actionButton(button, click))
        return box
    }

    private fun sectionTitle(text: String) = TextView(this).apply {
        this.text = text
        textSize = 12f
        letterSpacing = 0.08f
        setTextColor(muted)
        typeface = Typeface.DEFAULT_BOLD
    }

    private fun actionButton(label: String, onClick: () -> Unit) = Button(this).apply {
        text = label
        isAllCaps = false
        textSize = 14f
        setTextColor(Color.WHITE)
        setBackgroundColor(accent)
        setOnClickListener { onClick() }
        val p = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(48))
        p.topMargin = dp(14)
        layoutParams = p
    }

    private fun openLocalFolderPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(intent, PICK_LOCAL_FOLDER)
    }

    @Deprecated("Deprecated in Android API; retained for broad device compatibility")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_LOCAL_FOLDER && resultCode == RESULT_OK) {
            val uri: Uri? = data?.data
            if (uri != null) {
                contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                showInfo("Folder access granted. A file browser view is the next integration step.")
            }
        }
    }

    private fun showInfo(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun marginTop(value: Int) = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply { topMargin = dp(value) }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
