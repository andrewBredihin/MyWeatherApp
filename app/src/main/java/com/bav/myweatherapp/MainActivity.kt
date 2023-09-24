package com.bav.myweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bav.myweatherapp.app.App
import com.bav.myweatherapp.ui.theme.MyWeatherAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel> {
        mainVMFactory
    }

    @Inject
    lateinit var mainVMFactory: MainVMFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as App).appComponent.inject(this)

        setContent {
            MyWeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Greeting(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val temp by viewModel.temp.collectAsState()
    val city by viewModel.city.collectAsState()
    val backgroundImage by viewModel.image.collectAsState()
    val time by viewModel.time.collectAsState()

    val imageSize = 50.dp

    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.3f).paint(
                painter = BitmapPainter(image = backgroundImage),
                contentScale = ContentScale.FillBounds,
            ),
        ) {
            Text(
                text = city,
                modifier = modifier,
                fontSize = 30.sp,
            )
            Text(
                text = temp,
                modifier = modifier,
                fontSize = 40.sp,
            )
        }
        Text(
            text = time,
            fontSize = 30.sp,
        )
        Image(
            painter = painterResource(id = R.drawable.update),
            contentScale = ContentScale.Crop,
            modifier = Modifier.width(imageSize).height(imageSize)
                .align(alignment = Alignment.TopEnd).clickable {
                    viewModel.updateWeather()
                },
            contentDescription = "reloadWeather",
        )
    }
}
