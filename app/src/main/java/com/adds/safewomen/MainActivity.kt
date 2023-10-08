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



