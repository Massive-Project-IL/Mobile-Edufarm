package com.example.edufarm

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.edufarm.data.api.ApiClient
import com.example.edufarm.data.model.JadwalLive
import com.example.edufarm.data.model.Kategori
import com.example.edufarm.data.repository.BookmarkRepository
import com.example.edufarm.data.repository.PenggunaRepository
import com.example.edufarm.factory.BookmarkViewModelFactory
import com.example.edufarm.factory.PenggunaViewModelFactory
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.ConfirmationDialog
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.BookmarkViewModel
import com.example.edufarm.viewModel.JadwalLiveViewModel
import com.example.edufarm.viewModel.PelatihanViewModel
import com.example.edufarm.viewModel.PenggunaViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentScreen(
    navController: NavController,
    pelatihanViewModel: PelatihanViewModel = viewModel(),
    jadwalViewModel: JadwalLiveViewModel = viewModel(),
    bookmarkViewModel: BookmarkViewModel = viewModel(
        factory = BookmarkViewModelFactory(
            BookmarkRepository(ApiClient.apiService),
            LocalContext.current.applicationContext as Application
        )
    ),
    viewModel: PenggunaViewModel = viewModel(
        factory = PenggunaViewModelFactory(
            PenggunaRepository(ApiClient.apiService),
            LocalContext.current.applicationContext as Application
        )
    )
) {
    var namaPengguna by remember { mutableStateOf("...") }
    val pelatihanList by pelatihanViewModel.pelatihanList.collectAsState()
    val jadwalLive by jadwalViewModel.jadwalLive.collectAsState()
    val selectedItem = remember { mutableStateOf("Beranda") }
    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.green)

    val currentDate = getCurrentDates()
    val filteredJadwalLive = jadwalLive.filter { session ->
        val sessionDate = convertUtcToLocalDateTimes(session.tanggal).toLocalDate().toString()
        sessionDate == currentDate
    }

    var searchQuery by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    // Filter kategori berdasarkan input pencarian
    val filteredKategoriList = if (searchQuery.isNotBlank()) {
        pelatihanList.filter { kategori ->
            kategori.nama_kategori.contains(
                searchQuery,
                ignoreCase = true
            )
        }
    } else emptyList()

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
        pelatihanViewModel.fetchPelatihan()
        jadwalViewModel.fetchJadwalLive()
        bookmarkViewModel.getBookmarks()
        viewModel.getNamaPengguna { nama -> namaPengguna = nama }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background)),
        topBar = {
            InfoCard(
                navController = navController,
                namaPengguna = namaPengguna,
                searchQuery = searchQuery,
                onSearch = { query ->
                    searchQuery = query
                    isDropdownExpanded = searchQuery.isNotBlank()
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, selectedItem)
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .background(color = colorResource(R.color.background))) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 35.dp)
            ) {
                // Bagian Statis
                Spacer(modifier = Modifier.height(16.dp))
                CardLiveScrollable(filteredJadwalLive, navController)
                Spacer(modifier = Modifier.height(16.dp))
                KategoriBertani()
                Spacer(modifier = Modifier.height(6.dp))
                SelectKategori(navController, pelatihanList)
                Spacer(modifier = Modifier.height(16.dp))
                RekomendasiPelatihan(navController)
                Spacer(modifier = Modifier.height(16.dp))

                // Scrollable Daftar Pelatihan
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Bagian ini yang bisa di-scroll
                ) {
                    items(pelatihanList) { pelatihan ->
                        CardPelatihanBeranda(navController, pelatihan, bookmarkViewModel)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            // Dropdown hasil pencarian
            if (isDropdownExpanded && filteredKategoriList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .padding(8.dp)
                        .zIndex(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(filteredKategoriList) { kategori ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("halamanSubMateri/${kategori.kategori_id}")
                                    searchQuery = kategori.nama_kategori
                                    isDropdownExpanded = false
                                }
                                .padding(vertical = 4.dp, horizontal = 16.dp)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(colorResource(R.color.green), Color(0xFF51D195)),
                                        start = Offset.Zero,
                                        end = Offset.Infinite
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = kategori.gambar,
                                contentDescription = null,

                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.Gray),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = kategori.nama_kategori,
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontFamily = poppinsFontFamily,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun InfoCard(
    navController: NavController,
    namaPengguna: String,
    searchQuery: String,
    onSearch: (String) -> Unit
) {
    val namaDepan = namaPengguna.split(" ").firstOrNull() ?: namaPengguna

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(132.dp),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green))
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 37.dp)
                .padding(top = 5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hai, ",
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.background),
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(3.dp)
                )

                Text(
                    text = "$namaDepan👋",
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.background),
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(3.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { navController.navigate(Routes.HALAMAN_BOOKMARK) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.bookmark_putih),
                        contentDescription = "Bookmark",
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Text(
                text = "Ayo kita belajar bertani bersama!",
                fontSize = 13.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.background),
                modifier = Modifier.offset(y = (-6).dp)
            )
            // Search bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(
                        color = colorResource(R.color.white),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(R.color.green),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Search Icon",
                        tint = colorResource(id = R.color.gray_live),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    BasicTextField(
                        value = searchQuery,
                        onValueChange = onSearch,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.gray_text)
                        ),
                        decorationBox = { innerTextField ->
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "Cari Pelatihan",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = colorResource(id = R.color.gray_text),
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun convertUtcToLocalDateTimes(utcDate: String): LocalDateTime {
    val utcDateTime = ZonedDateTime.parse(utcDate)
    val localDateTime =
        utcDateTime.withZoneSameInstant(ZoneId.of("Asia/Jakarta")).toLocalDateTime()
    return localDateTime
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getCurrentDates(): String {
    return LocalDate.now().toString()
}

@Composable
fun CardLiveScrollable(jadwalLive: List<JadwalLive>, navController: NavController) {
    if (jadwalLive.isEmpty()) {
        NoJadwalCard()
    } else {
        LazyRow(
            contentPadding = PaddingValues(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(jadwalLive) { session ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CardLive(session, navController)
                }
            }
        }
    }
}

@Composable
private fun CardLive(session: JadwalLive, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    val dateOnly = session.tanggal.substring(0, 10)

    Card(
        modifier = Modifier
            .width(320.dp)
            .padding(2.dp)
            .clickable {
                navController.navigate(Routes.HALAMAN_LIVE_MENTOR)
            }
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.card_notif))
    ) {
        Column(modifier = Modifier.padding(16.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = session.judul_notifikasi,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.green_title),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Waktu",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppinsFontFamily,
                        color = colorResource(id = R.color.green_title),
                        modifier = Modifier.padding(bottom = 3.dp)
                    )
                    Text(
                        text = dateOnly,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppinsFontFamily,
                        color = colorResource(id = R.color.green_title)
                    )
                }
                Column {
                    Text(
                        text = "Nama Mentor",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppinsFontFamily,
                        color = colorResource(id = R.color.green_title),
                        modifier = Modifier.padding(bottom = 3.dp)
                    )
                    Text(
                        text = session.nama_mentor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppinsFontFamily,
                        color = colorResource(id = R.color.green_title)
                    )
                }
                Button(
                    onClick = { showDialog = true },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                    modifier = Modifier
                        .width(93.dp)
                        .height(30.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Gabung Live",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily
                    )
                }
            }
        }
    }

    if (showDialog) {
        ConfirmationDialog(
            message = "Apakah Kamu Mau Gabung Live?",
            onDismissRequest = { showDialog = false },
            onConfirm = { showDialog = false },
            onCancel = { showDialog = false }
        )
    }
}

@Composable
private fun NoJadwalCard() {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.card_notif))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_calendar_empty),
                contentDescription = "Empty Calendar",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Yahh, Tidak ada live mentor untuk Hari ini",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily,
                color = colorResource(id = R.color.green_jadwal),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Tetap semangat belajar, ya!",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFontFamily,
                color = colorResource(id = R.color.green_jadwal),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun KategoriBertani() {
    Text(
        text = "Kategori Bertani",
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = poppinsFontFamily,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun SelectKategori(navController: NavController, pelatihanList: List<Kategori>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pelatihanList) { pelatihan ->
            KategoriItem(
                navController = navController,
                iconUrl = pelatihan.icon,
                kategori = pelatihan,
                title = pelatihan.nama_kategori
            )
        }
    }
}

@Composable
fun KategoriItem(
    navController: NavController,
    kategori: Kategori,
    iconUrl: String?,
    title: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {   navController.navigate("halamanSubMateri/${kategori.kategori_id}") }
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(3.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.size(45.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                iconUrl?.let { url ->
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current).data(data = url)
                                .apply(block = fun ImageRequest.Builder.() {
                                    listener(onError = { _, result ->
                                        Log.e(
                                            "Image Load Error",
                                            "Failed to load image",
                                            result.throwable
                                        )
                                    })
                                }).build()
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                } ?: run {
                    Icon(Icons.Filled.Error, contentDescription = null)
                }
            }
        }

        Text(
            text = title,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = poppinsFontFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 4.dp)
                .wrapContentWidth()
        )
    }
}


@Composable
fun RekomendasiPelatihan(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Rekomendasi Pelatihan",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily
        )
        Text(
            text = "Lihat Semua",
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = poppinsFontFamily,
            modifier = Modifier
                .clickable { navController.navigate(Routes.HALAMAN_PELATIHAN) }
        )
    }
}

@Composable
fun CardPelatihanBeranda(
    navController: NavController,
    pelatihan: Kategori,
    bookmarkViewModel: BookmarkViewModel
) {
    // Ambil status bookmark dari ViewModel
    val isBookmarkedMap by bookmarkViewModel.isBookmarkedMap.collectAsState()
    val isBookmarked = isBookmarkedMap[pelatihan.kategori_id] ?: false

    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .size(260.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white))
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .align(Alignment.CenterHorizontally),
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = pelatihan.gambar)
                                .apply(block = fun ImageRequest.Builder.() {
                                    listener(onError = { _, result ->
                                        Log.e(
                                            "Image Load Error",
                                            "Failed to load image",
                                            result.throwable
                                        )
                                    })
                                }).build()
                        ),
                        contentDescription = "Deskripsi Gambar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    // Tombol bookmark
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
                                // Panggil toggleBookmark di ViewModel
                                bookmarkViewModel.toggleBookmark(pelatihan.kategori_id)
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
                        text = pelatihan.nama_kategori,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFontFamily,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "Materi ini akan membahas cara menanam ${pelatihan.nama_kategori} dari awal sampai akhir",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppinsFontFamily,
                        lineHeight = 13.sp,
                        color = colorResource(id = R.color.gray_bookmark),
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { navController.navigate("halamanSubMateri/${pelatihan.kategori_id}") },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(
                                    id = R.color.green
                                )
                            ),
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
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HalamanBerandaPreview() {
    EdufarmTheme {
        ContentScreen(
            navController = rememberNavController()
        )
    }
}