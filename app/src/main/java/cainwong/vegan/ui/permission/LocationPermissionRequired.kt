package cainwong.vegan.ui.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cainwong.vegan.R
import cainwong.vegan.ui.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
@Composable
fun LocationPermissionRequired(
    content: @Composable () -> Unit
) {
    val locationPermissionState =
        rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)
    PermissionRequired(
        permissionState = locationPermissionState,
        permissionNotGrantedContent = {
            if (locationPermissionState.permissionRequested) {
                PermissionNotAvailableContent()
            } else {
                SideEffect {
                    locationPermissionState.launchPermissionRequest()
                }
            }
        },
        permissionNotAvailableContent = {
            PermissionNotAvailableContent()
        },
        content = content
    )
}

@Composable
fun PermissionNotAvailableContent() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            stringResource(R.string.permission_denied)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        }) {
            Text(
                stringResource(R.string.open_settings)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PermissionNotAvailableContentPreview() {
    AppTheme {
        PermissionNotAvailableContent()
    }
}
