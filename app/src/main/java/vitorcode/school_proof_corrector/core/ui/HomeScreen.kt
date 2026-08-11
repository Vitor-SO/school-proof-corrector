package vitorcode.school_proof_corrector.core.ui

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import vitorcode.school_proof_corrector.core.ui.intents.CreateBulletinIntent
import vitorcode.school_proof_corrector.core.ui.viewModels.CreateBulletinViewModel
import java.io.File

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: CreateBulletinViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current


    var frontImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var backImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val frontLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->

            if (success) {
                frontImageUri?.let {
                    viewModel.onCreateBulletinIntent(
                        CreateBulletinIntent.PhotoFrontCaptured(it)
                    )
                }
            }
        }

    val backLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if(success){
            backImageUri?.let {
                viewModel.onCreateBulletinIntent(
                    CreateBulletinIntent.PhotoBackCaptured(it)
                )
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = {
                val uri = createImageUri(context, "front")

                frontImageUri = uri

                frontLauncher.launch(uri)
            }
        ) {
            Text("Capturar frontal da prova")
        }

        Spacer(modifier = Modifier.height(20. dp))

        Button(
            onClick = {
                val uri = createImageUri(context, "back")

                backImageUri = uri

                backLauncher.launch(uri)
            }
        ) {
            Text("Capturar verso da prova")
        }

        Spacer(modifier = Modifier.height(20. dp))

        Button(onClick = {viewModel.onClickCreateBulletin(context)}){
            Text("Criar boletim")
        }

        state.value.error?.let {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = it,
                color = Color.Red
            )
        }
    }

}

fun createImageUri(
    context: Context,
    title: String
): Uri {

    val imageFile = File(
        context.cacheDir,
        "${title}.jpg"
    )

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        imageFile
    )
}