import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.adds.safewomen.ui.theme.MyAppTheme
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.adds.safewomen.R




 

@Composable
fun SOSApp() {
    var isSOSActivated by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally // Ensure you import Alignment
    ) {
        Button(
            onClick = {
                // Add your SOS functionality here.
                // For example, you can send an SOS message.
                sendSOSMessage()
                isSOSActivated = true
            },
            enabled = !isSOSActivated // Disable the button if SOS is already activated.
        ) {
            Text(text = if (isSOSActivated) "SOS Activated" else "SOS")
        }
    }
}

fun sendSOSMessage() {
    // Add your SOS message sending logic here.
    // This can include sending an SMS, making a phone call, or other emergency actions.
    // For demonstration, we'll show a toast message.
    // Note: In a real app, you should implement the SOS functionality here.
    // Example:
    // val phoneNumber = "1234567890"
    // val smsIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phoneNumber"))
    // smsIntent.putExtra("sms_body", "Emergency SOS! Help needed!")
    // startActivity(smsIntent)
    // Handle the SOS action accordingly.
}



class MainActivity : AppCompatActivity() {

    private val smsPermissionCode = 101
    private val locationManager by lazy { getSystemService(LOCATION_SERVICE) as LocationManager }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sendButton = findViewById<Button>(R.id.sendButton)

        sendButton.setOnClickListener {
            if (checkPermission(Manifest.permission.SEND_SMS, smsPermissionCode)) {
                // Get the current location
                getLocation()
            }
        }
    }

    private fun checkPermission(permission: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            return false
        }
        return true
    }

    private fun getLocation() {
        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // Get the latitude and longitude
                val latitude = location.latitude
                val longitude = location.longitude

                // Send location via SMS
                sendLocationViaSMS(latitude, longitude)

                // Send location via WhatsApp
                sendLocationViaWhatsApp(latitude, longitude)

                // Remove location updates to conserve battery
                locationManager.removeUpdates(this)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1)) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListener
            )
        }
    }

    private fun sendLocationViaSMS(latitude: Double, longitude: Double) {
        // Replace 'phoneNumber' with the recipient's phone number
        val phoneNumber = "1234567890"
        val message = "My current location: https://maps.google.com/?q=$latitude,$longitude"
        val smsIntent = Intent(Intent.ACTION_SENDTO)
        smsIntent.data = Uri.parse("smsto:$phoneNumber")
        smsIntent.putExtra("sms_body", message)
        startActivity(smsIntent)
    }

    private fun sendLocationViaWhatsApp(latitude: Double, longitude: Double) {
        // Replace 'phoneNumber' with the recipient's phone number
        val phoneNumber = "1234567890"
        val message = "My current location: https://maps.google.com/?q=$latitude,$longitude"
        val whatsappIntent = Intent(Intent.ACTION_SEND)
        whatsappIntent.type = "text/plain"
        whatsappIntent.`package` = "com.whatsapp"
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, message)
        whatsappIntent.putExtra("jid", phoneNumber + "@s.whatsapp.net") // for WhatsApp Business use "@c.us"
        try {
            startActivity(whatsappIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show()
        }
    }
}

