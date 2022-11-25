package com.noveogroup.moviecatalog.presentation.screen.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.noveogroup.moviecatalog.R
import com.noveogroup.moviecatalog.domain.model.Movie
import com.noveogroup.moviecatalog.presentation.components.TopAppBarTitle
import com.noveogroup.moviecatalog.presentation.navigation.Screen

@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel
) {

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect {
            when (it) {
                is Screen.MovieDetails -> navController.navigate(it.route)
                else -> {} //do nothing
            }
        }
    }

    MovieListView(
        state = viewModel.state.collectAsState().value,
        onItemClicked = viewModel::handleMovieClicked,
        onScrolledToBottom = viewModel::handleListScrolledToEnd
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListView(
    state: MovieListScreenState,
    onItemClicked: (MovieListItem.MovieItem) -> Unit,
    onScrolledToBottom: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                TopAppBarTitle(R.string.movie_list_title)
            })
        },
        content = { paddingValues ->

            val contentPaddings = remember {
                PaddingValues(
                    start = 16.dp,
                    top = paddingValues.calculateTopPadding(),
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding()
                )
            }

            MovieListContent(
                paddingValues = contentPaddings,
                items = state.items,
                onItemClicked = onItemClicked,
                onScrolledToBottom = onScrolledToBottom
            )
        }
    )
}

@Composable
fun MovieListContent(
    paddingValues: PaddingValues,
    items: List<MovieListItem>,
    onItemClicked: (MovieListItem.MovieItem) -> Unit,
    onScrolledToBottom: () -> Unit
) {

    val lazyListState = rememberLazyListState()

    Box {
        LazyColumn(
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = paddingValues
        ) {
            items(items.size) { index ->

                when (val item = items.toList()[index]) {
                    MovieListItem.Loading -> {
                        LoadingListItemView()
                    }
                    is MovieListItem.MovieItem -> {
                        MovieListItemView(
                            movie = item,
                            onClicked = onItemClicked
                        )
                    }
                }


            }
        }
    }

    val isScrolledToBottom = remember {
        derivedStateOf { lazyListState.isScrolledNearBottom() }
    }

    LaunchedEffect(isScrolledToBottom.value) {
        onScrolledToBottom()
    }
}


@Composable
fun LoadingListItemView() {

    Box(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator()
    }
}

@Composable
fun MovieListItemView(
    movie: MovieListItem.MovieItem,
    onClicked: (MovieListItem.MovieItem) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClicked(movie)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            modifier = Modifier
                .weight(1f)
                .height(100.dp),
            model = movie.movie.posterUrl,
            contentDescription = "Movie poster"
/*            placeholder = rememberVectorPainter(image = Icons.Default),
            error = rememberVectorPainter(image = Icons.Default.AccountCircle)*/
        )

        Column(
            modifier = Modifier
                .weight(3f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = movie.movie.title)

            Text(text = movie.movie.originalTitle)

            Text(text = movie.movie.genres.joinToString())

            Row {

                Text(text = "${movie.movie.rating}")

                Text(text = "(${movie.movie.voteCount})")
            }

        }
    }
}

@Preview
@Composable
fun MovieListPreview() {

    MovieListView(
        state = MovieListScreenState(
            items = listOf(
                MovieListItem.MovieItem(
                    Movie(
                        id = 1000L,
                        posterUrl = "",
                        rating = 5.5f,
                        voteCount = 1000,
                        title = "Title",
                        originalTitle = "Original title",
                        genres = listOf("Comedy", "Horror")
                    )
                ),
                MovieListItem.Loading
            )
        ),
        onItemClicked = {},
        onScrolledToBottom = {})
}

fun LazyListState.isScrolledNearBottom(): Boolean {
    return (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) >= layoutInfo.totalItemsCount - 5
}
