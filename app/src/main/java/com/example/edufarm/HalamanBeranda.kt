package com.example.edufarm

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.edufarm.data.model.JadwalLive
import com.example.edufarm.data.model.Kategori
import com.example.edufarm.navigation.Routes
import com.example.edufarm.ui.components.BottomNavigationBar
import com.example.edufarm.ui.components.ConfirmationDialog
import com.example.edufarm.ui.theme.EdufarmTheme
import com.example.edufarm.ui.theme.poppinsFontFamily
import com.example.edufarm.viewModel.JadwalLiveViewModel
import com.example.edufarm.viewModel.PelatihanViewModel
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
    jadwalViewModel: JadwalLiveViewModel = viewModel()
) {
    val pelatihanList by pelatihanViewModel.pelatihanList.collectAsState()
    val selectedItem = remember { mutableStateOf("Beranda") }
    val systemUiController = rememberSystemUiController()
    val topBarColor = colorResource(id = R.color.green)
    val jadwalLive by jadwalViewModel.jadwalLive.collectAsState()

    val currentDate = getCurrentDates()
    val filteredJadwalLive = jadwalLive.filter { session ->
        val sessionDate = convertUtcToLocalDateTimes(session.tanggal).toLocalDate().toString()
        sessionDate == currentDate
    }

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
        pelatihanViewModel.fetchPelatihan()
        jadwalViewModel.fetchJadwalLive()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            InfoCard(
                navController = navController,
                hai = "Hai,",
                title = "Petani👋",
                deskripsi = "Ayo kita belajar bertani bersama!"
            )
        },
        bottomBar = { BottomNavigationBar(navController, selectedItem) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(color = colorResource(R.color.background))
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            CardLiveScrollable(filteredJadwalLive, navController) // Kirim jadwal yang sudah difilter
            Spacer(modifier = Modifier.height(16.dp))
            KategoriBertani()
            Spacer(modifier = Modifier.height(6.dp))
            SelectKategori(navController, pelatihanList)
            Spacer(modifier = Modifier.height(16.dp))
            RekomendasiPelatihan(navController)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(pelatihanList) { pelatihan ->
                    CardPelatihanBeranda(navController, pelatihan)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun convertUtcToLocalDateTimes(utcDate: String): LocalDateTime {
    val utcDateTime = ZonedDateTime.parse(utcDate)
    val localDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Asia/Jakarta")).toLocalDateTime()
    return localDateTime
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getCurrentDates(): String {
    return LocalDate.now().toString()
}

@Composable
fun SearchBarBeranda(
    placeholder: String,
    onSearch: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

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
                tint = colorResource(R.color.gray_live),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            BasicTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    onSearch(it.text)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = colorResource(R.color.gray_text)
                ),
                decorationBox = { innerTextField ->
                    if (searchQuery.text.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = colorResource(R.color.gray_text),
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

@Composable
fun CardLiveScrollable(jadwalLive: List<JadwalLive>, navController: NavController) {
    if (jadwalLive.isEmpty()) {
        NoJadwalCard()
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
        ) {
            items(jadwalLive) { session ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    CardLive(session, navController)
                }
            }
        }
    }
}

@Composable
private fun NoJadwalCard() {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 35.dp)
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
                painter = painterResource(id = R.drawable.ic_calendar_empty),
                contentDescription = "Empty Calendar",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Yahh, Tidak ada live mentor untuk Hari ini",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.green_title),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tetap semangat belajar, ya!",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFontFamily,
                color = colorResource(id = R.color.green_title),
                textAlign = TextAlign.Center
            )
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
            .clickable {
                navController.navigate(Routes.HALAMAN_LIVE_MENTOR)
            }
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.card_notif))
    )
 {
        Column(modifier = Modifier.padding(16.dp)) {
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
                    overflow = TextOverflow.Ellipsis
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
fun KategoriBertani() {
    Text(
        text = "Kategori Bertani",
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = poppinsFontFamily,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp)
    )
}

@Composable
fun SelectKategori(navController: NavController, pelatihanList: List<Kategori>) {
    LazyRow(
        modifier = Modifier
            .padding(horizontal = 36.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pelatihanList) { pelatihan ->
            KategoriItem(
                navController = navController,
                iconUrl = pelatihan.icon,
                title = pelatihan.nama_kategori
            )
        }
    }
}

@Composable
fun KategoriItem(
    navController: NavController,
    iconUrl: String?,
    title: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { navController.navigate(Routes.HALAMAN_PELATIHAN) }
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
                // Menggunakan rememberAsyncImagePainter untuk memuat gambar dari URL
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
                            .size(29.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                } ?: run {
                    // Menangani kasus ketika URL tidak valid atau null
                    Icon(Icons.Filled.Error, contentDescription = null)
                }
            }
        }

        Text(
            text = title,
            fontSize = 8.sp,
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
            .fillMaxWidth()
            .padding(horizontal = 37.dp),
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
fun InfoCard(hai: String, title: String, deskripsi: String, navController: NavController) {
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
                    text = hai,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.background),
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(3.dp)
                )

                Text(
                    text = title,
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
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
            Text(
                text = deskripsi,
                fontSize = 13.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.background),
                modifier = Modifier.offset(y = (-6).dp)
            )
            SearchBarBeranda(
                placeholder = "Cari Pelatihan"
            ) { query -> //untuk logika pencarian nya ni

                println("query dari info card: $query")
            }
            Spacer(modifier = Modifier.padding(bottom = 23.dp))
        }
    }
}

@Composable
private fun CardPelatihanBeranda(navController: NavController, pelatihan: Kategori) {
    var isBookmarked by remember { mutableStateOf(false) }
    val progressCurrent = 1
    val progressTotal = 6
    val progressFraction = progressCurrent.toFloat() / progressTotal.toFloat()

    Box(
        modifier = Modifier
            .padding(horizontal = 35.dp),
    ) {
        Card(
            modifier = Modifier
                .size(width = 330.dp, height = 260.dp),
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
                            ImageRequest.Builder(LocalContext.current).data(data = pelatihan.gambar)
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
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 19.dp, end = 15.dp)
                            .size(24.dp)
                            .background(
                                color = if (isBookmarked) Color.White else Color.Gray,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickable { isBookmarked = !isBookmarked },
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
                            onClick = { navController.navigate(Routes.HALAMAN_SUB_MATERI) },
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
                        Text(
                            text = "Progres Materi",
                            fontSize = 11.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.W400,
                            color = Color.Black,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .offset(x = 15.dp)
                        )

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier

                        ) {
                            CircularProgressIndicator(
                                progress = { progressFraction },
                                modifier = Modifier
                                    .width(44.dp)
                                    .height(44.dp),
                                color = colorResource(id = R.color.green),
                                strokeWidth = 4.dp,
                                trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                            )
                            Text(
                                text = "$progressCurrent/$progressTotal",
                                fontSize = 10.sp,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
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