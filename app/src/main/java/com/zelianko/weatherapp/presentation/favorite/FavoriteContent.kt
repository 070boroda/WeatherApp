package com.zelianko.weatherapp.presentation.favorite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.zelianko.weatherapp.R
import com.zelianko.weatherapp.presentation.extations.tempToFormatString
import com.zelianko.weatherapp.presentation.ui.theme.CardGradients
import com.zelianko.weatherapp.presentation.ui.theme.Gradient

@Composable
fun FavoriteContent(component: FavoriteComponent) {

    val state by component.model.collectAsState()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            SearchCard(
                onClick = { component.onClickSearch() }
            )
        }
        itemsIndexed(
            items = state.cityItems,
            key = { _, item -> item.city.id }) { index, item ->
            CityCard(
                cityItem = item,
                index = index,
                onClick = { component.onCityItemClick(item.city) }
            )
        }
        item {
            AddFavouriteCityCard(
                onClick = { component.onClickFavourite() }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CityCard(
    cityItem: FavoriteStore.State.WeatherState.CityItem,
    index: Int,
    onClick: () -> Unit
) {
    val gradientByIndex = getGradientByIndex(index)
    Card(
        modifier = Modifier
            .fillMaxSize()
            .shadow(
                elevation = 16.dp,
                spotColor = gradientByIndex.shadowColor,
                shape = MaterialTheme.shapes.extraLarge
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Blue),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Box(
            modifier = Modifier
                .background(gradientByIndex.primaryGradient)
                .fillMaxSize()
                .clickable { onClick() }
                .sizeIn(minHeight = 196.dp)
                .drawBehind {
                    drawCircle(
                        brush = gradientByIndex.secondaryGradient,
                        center = Offset(
                            x = center.x - size.width / 10,
                            y = center.y + size.height / 2
                        ),
                        radius = size.maxDimension / 2
                    )
                }
                .padding(24.dp)
        ) {
            when (val weatherState = cityItem.weatherState) {
                FavoriteStore.State.WeatherState.Error -> {

                }

                FavoriteStore.State.WeatherState.Initial -> {

                }

                is FavoriteStore.State.WeatherState.Loaded -> {
                    GlideImage(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(56.dp),
                        model = weatherState.iconUrl.replace("http", "https"),
                        contentDescription = "icon weather"
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(bottom = 28.dp),
                        text = weatherState.tempC.tempToFormatString(),
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 48.sp)
                    )

                }

                FavoriteStore.State.WeatherState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
            Text(
                //Нижняя левая часть
                modifier = Modifier.align(Alignment.BottomStart),
                text = cityItem.city.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Composable
fun AddFavouriteCityCard(
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.extraLarge,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground)
    ) {
        Column(
            modifier = Modifier
                .sizeIn(minHeight = 196.dp)
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(24.dp)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .size(48.dp),
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.add_favourite_button),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun SearchCard(
    onClick: () -> Unit
) {
    val gradient = CardGradients.gradients[3]

    Card(
        shape = CircleShape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .background(gradient.primaryGradient)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.background,
                contentDescription = null,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
            Text(
                text = stringResource(R.string.search),
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(end = 16.dp)
            )
        }

    }
}


private fun getGradientByIndex(index: Int): Gradient {
    val cardGradient = CardGradients.gradients
    return cardGradient[index % cardGradient.size]
}