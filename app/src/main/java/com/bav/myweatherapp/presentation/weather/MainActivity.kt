package com.bav.myweatherapp.presentation.weather

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bav.myweatherapp.R
import com.bav.myweatherapp.app.App
import com.bav.myweatherapp.presentation.theme.BluePrimary
import com.bav.myweatherapp.presentation.theme.MyWeatherAppTheme
import com.bav.myweatherapp.presentation.theme.blur
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel> {
        mainVMFactory
    }

    @Inject
    lateinit var mainVMFactory: MainVMFactory

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                    Scaffold(
                        topBar = {
                            var city by remember { mutableStateOf(value = "Самара") }
                            TopAppBar(
                                colors = TopAppBarDefaults.smallTopAppBarColors(
                                    containerColor = BluePrimary,
                                    titleContentColor = Color.White,
                                ),
                                title = {
                                    TextField(
                                        value = city,
                                        onValueChange = { newText ->
                                            city = newText
                                            viewModel.setCity(newText)
                                        },
                                        textStyle = androidx.compose.ui.text.TextStyle(
                                            fontSize = 30.sp,
                                        ),
                                        colors = TextFieldDefaults.textFieldColors(
                                            textColor = Color.White,
                                            containerColor = Color.Transparent,
                                        ),
                                        singleLine = true,
                                    )
                                },
                            )
                        },
                    ) {
                        MainCompose(
                            viewModel = viewModel,
                            modifier = Modifier.padding(top = 64.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainCompose(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val backgroundImage by viewModel.backgroundImage.collectAsState()
    val cloud by viewModel.clouds.collectAsState()
    var showDayWeather by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = BitmapPainter(image = backgroundImage),
                contentScale = ContentScale.FillBounds,
            )
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier.clickable {
                showDayWeather = !showDayWeather
            },
        ) {
            CloudAnimation(cloud)
            WeatherNow(viewModel = viewModel)
        }
        AnimatedVisibility(visible = showDayWeather) {
            WeatherDay(viewModel = viewModel)
        }
        WeatherWeek(viewModel = viewModel)
    }
}

@Composable
fun CloudAnimation(clouds: MainViewModel.CloudsEnum) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.toFloat()

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val cloudOneXOffset by infiniteTransition.animateFloat(
        initialValue = screenWidth,
        targetValue = -screenWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "",
    )
    val cloudTwoXOffset by infiniteTransition.animateFloat(
        initialValue = screenWidth,
        targetValue = -screenWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "",
    )
    val cloudThreeXOffset by infiniteTransition.animateFloat(
        initialValue = screenWidth,
        targetValue = -screenWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "",
    )

    Box(modifier = Modifier.fillMaxWidth()) {
        if (clouds.ordinal >= 1) {
            Image(
                modifier = Modifier
                    .offset(x = cloudOneXOffset.dp),
                painter = painterResource(id = R.drawable.cloud),
                contentDescription = "",
            )
        }
        if (clouds.ordinal >= 2) {
            Image(
                modifier = Modifier
                    .offset(x = cloudTwoXOffset.dp),
                painter = painterResource(id = R.drawable.cloud_2),
                contentDescription = "",
            )
        }
        if (clouds.ordinal >= 3) {
            Image(
                modifier = Modifier
                    .offset(x = cloudThreeXOffset.dp, y = 100.dp),
                painter = painterResource(id = R.drawable.cloud_3),
                contentDescription = "",
            )
        }
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

    val playing by viewModel.state.collectAsState()
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotationValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "",
    )

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
            fontSize = 40.sp,
            color = Color.White,
            modifier = modifier
                .constrainAs(ref = timeRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
        )

        // Update weather imageButton
        if (playing) {
            Image(
                painter = painterResource(id = R.drawable.update),
                contentScale = ContentScale.Crop,
                contentDescription = "reloadWeather",
                modifier = Modifier
                    .width(imageSize)
                    .height(imageSize)
                    .rotate(rotationValue)
                    .constrainAs(updateRef) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
            )
        }

        // City
        Text(
            text = city,
            fontSize = 40.sp,
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
            fontSize = 80.sp,
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
            textAlign = TextAlign.Center,
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WeatherDay(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val day by viewModel.day.collectAsState()
    val imageSize = 64.dp

    LazyRow(
        modifier = modifier
            .padding(top = 40.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        items(day) { hour ->
            val path = "https:${hour.condition.icon}"
            Box(
                modifier = modifier.clip(shape = RoundedCornerShape(30.dp)),
            ) {
                Column(
                    modifier = modifier
                        .padding(horizontal = 10.dp)
                        .background(color = blur),
                ) {
                    // Condition
                    GlideImage(
                        model = path,
                        contentDescription = "condition",
                        modifier = Modifier
                            .size(imageSize)
                            .align(alignment = Alignment.CenterHorizontally),
                    )

                    // Date
                    val time = hour.time.split(" ")[1]
                    Text(
                        text = time,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(horizontal = 10.dp),
                    )

                    // Average Temp
                    Text(
                        text = "${hour.temp}℃",
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = modifier
                            .padding(top = 10.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                    )

                    // Wind
                    Row(
                        modifier = Modifier
                            .padding(10.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.wind),
                            contentScale = ContentScale.Crop,
                            contentDescription = "reloadWeather",
                            modifier = Modifier
                                .size(imageSize / 2),
                        )

                        // Wind
                        Text(
                            text = "${String.format("%.1f", hour.wind / 3.6)} м/с",
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = modifier
                                .padding(start = 10.dp),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WeatherWeek(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val week by viewModel.week.collectAsState()
    val imageSize = 64.dp

    LazyRow(
        modifier = modifier
            .padding(vertical = 40.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        items(week) { forecastDay ->
            val path = "https:${forecastDay.day.condition.icon}"
            Box(
                modifier = modifier.clip(shape = RoundedCornerShape(30.dp)),
            ) {
                Column(
                    modifier = modifier
                        .padding(horizontal = 10.dp)
                        .background(color = blur),
                ) {
                    // Condition
                    GlideImage(
                        model = path,
                        contentDescription = "condition",
                        modifier = Modifier
                            .size(imageSize)
                            .align(alignment = Alignment.CenterHorizontally),
                    )

                    // Date
                    val day = LocalDate.parse(forecastDay.date).dayOfMonth
                    val dayOfWeek = LocalDate.parse(forecastDay.date).dayOfWeek.getDisplayName(
                        TextStyle.FULL,
                        Locale("ru"),
                    )
                    Text(
                        text = "$day $dayOfWeek",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(horizontal = 10.dp),
                    )

                    // Average Temp
                    Text(
                        text = "${forecastDay.day.avgTemp}℃",
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = modifier
                            .padding(top = 10.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                    )
                    // Min-Max Temp
                    Text(
                        text = "${forecastDay.day.minTemp}℃ - ${forecastDay.day.maxTemp}℃",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(horizontal = 10.dp),
                    )

                    // Wind
                    Row(
                        modifier = Modifier
                            .padding(10.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.wind),
                            contentScale = ContentScale.Crop,
                            contentDescription = "reloadWeather",
                            modifier = Modifier
                                .size(imageSize / 2),
                        )

                        // Wind
                        Text(
                            text = "${String.format("%.1f", forecastDay.day.maxWind / 3.6)} м/с",
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = modifier
                                .padding(start = 10.dp),
                        )
                    }
                }
            }
        }
    }
}
