package com.bav.myweatherapp.presentation.ui

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bav.myweatherapp.R
import com.bav.myweatherapp.app.App
import com.bav.myweatherapp.presentation.ui.theme.MyWeatherAppTheme
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
                    MainCompose(
                        viewModel = viewModel,
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}

@Composable
fun MainCompose(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val backgroundImage by viewModel.backgroundImage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .paint(
                painter = BitmapPainter(image = backgroundImage),
                contentScale = ContentScale.FillBounds,
            ),
    ) {
        WeatherNow(viewModel = viewModel, modifier = modifier)
    }
}

@Composable
fun WeatherNow(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val temp by viewModel.temp.collectAsState()
    val city by viewModel.city.collectAsState()
    val time by viewModel.time.collectAsState()
    val condition by viewModel.condition.collectAsState()
    val wind by viewModel.wind.collectAsState()

    val imageSize = 40.dp

    ConstraintLayout(
        modifier = Modifier.fillMaxWidth(),
    ) {
        val (
            timeRef,
            cityRef,
            tempRef,
            conditionRef,
            updateRef,
            windRef,
            windIconRef,
        ) = createRefs()

        // Time text
        Text(
            text = time,
            fontSize = 30.sp,
            color = Color.White,
            modifier = modifier
                .constrainAs(ref = timeRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
        )

        // Update weather imageButton
        Image(
            painter = painterResource(id = R.drawable.update),
            contentScale = ContentScale.Crop,
            contentDescription = "reloadWeather",
            modifier = Modifier
                .width(imageSize)
                .height(imageSize)
                .clickable {
                    viewModel.updateWeather()
                }
                .constrainAs(updateRef) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
        )

        // City
        Text(
            text = city,
            fontSize = 30.sp,
            color = Color.White,
            modifier = modifier
                .padding(top = 80.dp)
                .constrainAs(cityRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )

        // Temp
        Text(
            text = temp,
            fontSize = 40.sp,
            color = Color.White,
            modifier = modifier
                .constrainAs(tempRef) {
                    top.linkTo(cityRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )

        // Condition
        Text(
            text = condition,
            fontSize = 30.sp,
            color = Color.White,
            modifier = modifier
                .constrainAs(conditionRef) {
                    top.linkTo(tempRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )

        // Wind image
        Image(
            painter = painterResource(id = R.drawable.wind),
            contentScale = ContentScale.Crop,
            contentDescription = "reloadWeather",
            modifier = Modifier
                .clickable {
                    viewModel.updateWeather()
                }
                .padding(start = 20.dp, top = 20.dp)
                .size(imageSize)
                .constrainAs(windIconRef) {
                    top.linkTo(conditionRef.bottom)
                    start.linkTo(parent.start)
                },
        )

        // Wind
        Text(
            text = wind,
            fontSize = 30.sp,
            color = Color.White,
            modifier = modifier
                .padding(start = 10.dp, top = 20.dp)
                .constrainAs(windRef) {
                    start.linkTo(windIconRef.end)
                    top.linkTo(windIconRef.top)
                    bottom.linkTo(windIconRef.bottom)
                },
        )
    }
}

@Composable
fun Greeting(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val temp by viewModel.temp.collectAsState()
    val city by viewModel.city.collectAsState()
    val backgroundImage by viewModel.backgroundImage.collectAsState()
    val time by viewModel.time.collectAsState()

    val imageSize = 50.dp

    Box(
        modifier = Modifier.paint(
            painter = BitmapPainter(image = backgroundImage),
            contentScale = ContentScale.FillBounds,
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f),
        ) {
            Text(
                text = city,
                fontSize = 30.sp,
                color = Color.White,
                modifier = modifier,
            )
            Text(
                text = temp,
                fontSize = 40.sp,
                color = Color.White,
                modifier = modifier,
            )
        }
        Text(
            text = time,
            fontSize = 30.sp,
            color = Color.White,
            modifier = modifier,
        )
        Image(
            painter = painterResource(id = R.drawable.update),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(imageSize)
                .height(imageSize)
                .align(alignment = Alignment.TopEnd)
                .clickable {
                    viewModel.updateWeather()
                },
            contentDescription = "reloadWeather",
        )
    }
}
