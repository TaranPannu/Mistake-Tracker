package com.example.mistaketracker

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mistaketracker.DataClass.Mistake
import com.example.mistaketracker.MVVM.MistakeViewModel
import com.example.mistaketracker.ui.theme.MistakeTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class ProfileActivity : ComponentActivity() {
    @Inject
    lateinit var mistakeViewModel: MistakeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MistakeTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    Log.d("s11", "it.toString()")
                    lifecycleScope.launch(Dispatchers.IO) {
                        mistakeViewModel.insert(
                            Mistake(
                                0,
                                0,
                                "Title",
                                "Finance",
                                "01",
                                "Detail",
                                "Lesson",
                                "img",
                                0L,
                                false
                            )
                        )
                    }
                    MistakeListScreen(mistakeViewModel)
                } } } } }
@Composable
fun MistakeListScreen(viewModel: MistakeViewModel) {
    // Observe the LiveData from the ViewModel
    val mistakesState = viewModel.getAllMistakes().observeAsState(emptyList())
    val colors = listOf(
        Color(0xFFAE82FF), Color(0xFF8957E2), Color(0xFF6F30EB)
    )
    LazyColumn(  modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors // Define your gradient colors here
            )
        ) ) {
        items(mistakesState.value) { it ->
            MistakeCard(mistake = it)
            Log.d("s11", it.toString())
        }
    }
}


@Composable
fun MistakeCard(mistake: Mistake) {
    val colors = listOf(
        Color(0xFF8852E7), Color(0xFF643CAA), Color(0xFF4D2B8D)
    )
    Card(
        elevation = 8.dp, // Adjust the elevation to your preference
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth().background(
                brush = Brush.verticalGradient(
                    colors // Define your gradient colors here
                ),
                shape = RoundedCornerShape(50.dp)
                ).clip(RoundedCornerShape(16.dp))
            // Distribute space between children

    ) {
        Column(
            modifier = Modifier.padding(0.dp).background(brush = Brush.verticalGradient(
                colors // Define your gradient colors here
            )) // Add padding inside the card
        ) {

                Text(
                    text = mistake.title,
                    style = MaterialTheme.typography.titleMedium // Style for the title
                    , color = androidx.compose.ui.graphics.Color.Black
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = mistake.category,
                    style = MaterialTheme.typography.bodySmall, // Style for the category
                //    modifier = Modifier.padding(4.dp) // Add spacing between texts
                     color = androidx.compose.ui.graphics.Color.Black
                )
            TitleCard(mistake.detail)
            Text(
                text = mistake.detail,
                style = MaterialTheme.typography.bodyLarge, // Style for the detail
                modifier = Modifier.padding(top = 4.dp) // Add spacing between texts
                , color = androidx.compose.ui.graphics.Color.Black
            )
            Text(
                text = mistake.lesson,
                style = MaterialTheme.typography.bodyMedium, // Style for the lesson
                modifier = Modifier.padding(top = 4.dp) // Add spacing between texts
                , color = androidx.compose.ui.graphics.Color.Black
            )
        }
    }
}

@Composable
@Preview()
fun xx() {
    MistakeCard(Mistake(0, 0, "Title", "Finance", "01", "Detail", "Lesson", "img", 0L, false))
}
@Composable
fun TitleCard(short_title:String)
{
    Card(
        elevation = 2.dp, // Adjust the elevation to your preference
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth().height(200.dp)
            .background( androidx.compose.ui.graphics.Color.White,shape = RoundedCornerShape(50.dp)
            ).clip(RoundedCornerShape(16.dp))
        // Distribute space between children
    )
    {
        Text(text = "On creation, mistakes are stored locally and immediately pushed to the server if there is an internet connection",
            style = MaterialTheme.typography.titleLarge
     ,   modifier = Modifier.padding(8.dp)
//                .background(Color(
//                red = Random.nextFloat(),
//                green = Random.nextFloat(),
//                blue = Random.nextFloat()
//            ))
            )

    }
}