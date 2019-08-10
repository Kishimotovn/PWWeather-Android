package kishimotovn.pocketworksWeather.base

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kishimotovn.pocketworksWeather.Application
import kishimotovn.pocketworksWeather.R
import kishimotovn.pocketworksWeather.factory.AppViewModelFactory
import kishimotovn.pocketworksWeather.injection.AppComponent
import javax.inject.Inject

open class BaseActivity: AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    fun getComponent(): AppComponent {
        return (applicationContext as Application).getMyComponent()
    }

    fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton(getString(R.string.alert_dismiss_button_title)) { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }
}