package com.example.musicplayer.presentation.composables

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@Composable
fun NoReadPermissions(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_read_permissions),
            contentDescription = "No Read permissions",
            colorFilter = ColorFilter
                .tint(MaterialTheme.colorScheme.onSecondaryContainer)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.no_read_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.no_read_desc),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextButton(
            onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                context.startActivity(intent)

            }
        ) {
            Text(
                text = "Allow Permissions",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun NoReadPermissionPreview() {
    MusicPlayerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            NoReadPermissions()
        }
    }
}