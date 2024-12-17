package com.example.edufarm

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.edufarm.data.api.ApiClient
import com.example.edufarm.data.model.Bookmark
import com.example.edufarm.data.repository.BookmarkRepository
import com.example.edufarm.factory.BookmarkViewModelFactory
import com.example.edufarm.ui.components.SearchBar
import com.example.edufarm.ui.components.TopBar
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.BookmarkViewModel
import com.example.edufarm.viewModel.PelatihanViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BookmarkScreen(
    navController: NavController,
    pelatihanViewModel: PelatihanViewModel = viewModel(),
    bookmarkViewModel: BookmarkViewModel = viewModel(
        factory = BookmarkViewModelFactory(
            BookmarkRepository(ApiClient.apiService),
            LocalContext.current.applicationContext as Application
        )
    )
) {
    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.background)
    val bookmarks by bookmarkViewModel.bookmarks.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredBookmarks = bookmarks.filter { bookmark ->
        bookmark.nama_kategori.contains(searchQuery, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
        pelatihanViewModel.fetchPelatihan()
        bookmarkViewModel.getBookmarks()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
            .padding(start = 35.dp, end = 35.dp, top = 5.dp)
    ) {
        TopBar(
            title = "Simpan Pelatihan",
            navController = navController
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(
            placeholder = "Cari Pelatihan",
            onSearchQueryChanged = { query ->
                searchQuery = query
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        if (filteredBookmarks.isEmpty()) {
            NoCardBookmark()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredBookmarks) { bookmark ->
                    CardPelatihanBookmark(navController, bookmark, bookmarkViewModel)
                }
            }
        }
    }
}


@Composable
private fun CardPelatihanBookmark(
    navController: NavController,
    bookmark: Bookmark,
    bookmarkViewModel: BookmarkViewModel
) {
    val isBookmarkedMap by bookmarkViewModel.isBookmarkedMap.collectAsState()
    val isBookmarked = isBookmarkedMap[bookmark.kategori_id] ?: true

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white))
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = bookmark.gambar),
                    contentDescription = "Deskripsi Gambar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 19.dp, end = 15.dp)
                        .size(24.dp)
                        .background(
                            color = if (isBookmarked) Color.White else Color.Gray,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .clickable {
                            // Toggle bookmark
                            bookmarkViewModel.toggleBookmark(bookmark.kategori_id)

                            bookmarkViewModel.getBookmarks()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(
                            id = if (isBookmarked) R.drawable.bookmark_green else R.drawable.bookmark_putih
                        ),
                        contentDescription = "Bookmark",
                        modifier = Modifier.size(width = 16.dp, height = 18.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = bookmark.nama_kategori,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = bookmark.penjelasan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = poppinsFontFamily,
                    lineHeight = 13.sp,
                    color = colorResource(id = R.color.gray_bookmark),
                    modifier = Modifier.padding(bottom = 8.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { navController.navigate("halamanSubMateri/${bookmark.kategori_id}") },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                        modifier = Modifier
                            .width(115.dp)
                            .height(30.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Lihat Selengkapnya",
                            fontSize = 10.sp,
                            color = Color.White,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight(500),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NoCardBookmark() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(320.dp)
            .border(
                BorderStroke(1.dp, colorResource(id = R.color.green_jadwal)),
                RoundedCornerShape(16.dp)
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.card_notif))
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.empty_bookmark),
                    contentDescription = "Empty Calendar",
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Yahh, Kamu belum menyimpan pelatihan",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.green_jadwal),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Kamu Bisa Mencari Pelatihan Yang Menarik Di Halaman Pelatihan",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFontFamily,
                    color = colorResource(id = R.color.green_jadwal),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBookmarkScreen() {
    EdufarmTheme {
        BookmarkScreen(
            navController = rememberNavController()
        )
    }
}
