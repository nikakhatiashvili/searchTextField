package com.example.myapplication

import android.os.Bundle
import android.util.Log.d
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White,
                ) {
                    val focusManager = LocalFocusManager.current

                    var searchText by remember { mutableStateOf("") }
                    var camera by remember { mutableStateOf(false) }
                    var imageSearch by remember { mutableStateOf(false) }
                    val topBarState = rememberSaveable { (mutableStateOf(true)) }
                    Scaffold(
                        topBar = {
                            AnimatedVisibility(
                                visible = topBarState.value,
                                enter = slideInVertically(initialOffsetY = { -it }),
                                exit = slideOutVertically(targetOffsetY = { -it }),
                                content = {
                                    CenterAlignedTopAppBar(
                                        colors = TopAppBarDefaults.smallTopAppBarColors(
                                            containerColor = MaterialTheme.colorScheme.background
                                        ),
                                        title = {
                                            Text(
                                                text = "126 ბაგრატიონის ქუჩა, ბათუმი, საქართველო",
                                                color = colorResource(
                                                    id = R.color.location
                                                ),
                                                overflow = TextOverflow.Ellipsis,
                                                maxLines = 1,
                                                fontSize = 16.sp
                                            )
                                        },
                                        navigationIcon = {
                                            IconButton(onClick = {}) {
                                                Box(
                                                    modifier = Modifier
                                                        .background(
                                                            color = colorResource(id = R.color.lc_back),
                                                            shape = RoundedCornerShape(20.dp)
                                                        )
                                                        .height(35.dp)
                                                        .width(35.dp), contentAlignment = Alignment.Center
                                                ) {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.baseline_location_on_24),
                                                        contentDescription = null,
                                                        modifier = Modifier
                                                            .size(24.dp)
                                                            .clickable {
                                                                // location icon clicked
                                                            }
                                                    )
                                                }
                                            }
                                        },
                                        actions = {
                                            IconButton(onClick = { }) {
                                                Box(modifier = Modifier.padding(end = 10.dp)) {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                                                        contentDescription = null,
                                                        modifier = Modifier
                                                            .size(24.dp)
                                                            .clickable {
                                                                // arrow button clicked
                                                            }
                                                    )
                                                }
                                            }
                                        },
                                        modifier = Modifier
                                            .animateContentSize()
                                    )
                                }
                            )

                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(it),
                        ) {
                            CustomToolbar(searchText, camera, true, imageSearch, {
                                searchText = it
                            }, {
                                topBarState.value = true
                                focusManager.clearFocus()
                            }, {}, {}, {
                                camera = true
                            }, {
                                camera = false
                            }, {
                                imageSearch = false
                            }, {
                                topBarState.value = false
                            })

                        }
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbar(
    searchText: String,
    camera: Boolean,
    showBack: Boolean,
    imageSearch: Boolean,
    onSearchTextChange: (String) -> Unit,
    onLeftArrowClicked: () -> Unit,
    onSearchIconClick: () -> Unit,
    onClearTextClick: () -> Unit,
    onCameraIconClick: () -> Unit,
    onCloseMenu: () -> Unit,
    onBinClicked: () -> Unit,
    onTextFieldClicked: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (showBack) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onLeftArrowClicked()
                    }
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .background(
                    colorResource(id = R.color.textField),
                    shape = RoundedCornerShape(24.dp)
                )
                .clickable {
                    onTextFieldClicked()
                }
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                if (imageSearch) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_down_blue),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    onLeftArrowClicked()
                                }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_remove_bin_gray),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    onBinClicked()
                                }
                        )
                    }
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                onTextFieldClicked()
                            }
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                Box(modifier = Modifier.weight(1f)) {
                    BasicTextField(
                        value = searchText,
                        onValueChange = {
                            onSearchTextChange(it)
                        },

                        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
                        modifier = Modifier
                            .fillMaxWidth().onFocusEvent { it ->
                                d("adasdasdas", it.hasFocus.toString())
                                d("adasdasdas", it.isFocused.toString())
                                d("adasdasdas", it.isCaptured.toString())
                                if (it.hasFocus && it.isFocused){
                                    onTextFieldClicked()
                                }
                            }
                            ,
                        singleLine = true,
                    )
                    if (searchText.isEmpty()){
                        Text(
                            text =
                            "მოძებნე რაც გაგიხარდება",
                            color = Color.Gray,
                            fontSize = 18.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                        )
                    }
                }

                if (searchText.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.ic_close_search_header),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                onSearchTextChange("")
                                onClearTextClick()
                            }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Divider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier
                            .height(25.dp)
                            .width(1.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Box(modifier = Modifier.padding(end = 5.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                onCameraIconClick()
                            }
                    )
                    if (camera) {
                        PopupMenu(onDismissRequest = { onCloseMenu() }, camera)
                    }
                }


            }
        }
    }
}

@Composable
fun PopupMenu(
    onDismissRequest: () -> Unit,
    camera: Boolean
) {
    val context = LocalContext.current

    DisposableEffect(camera) {
        if (!camera) {
            onDismissRequest()
        }
        onDispose { }
    }

    Box(
        modifier = Modifier.padding(end = 30.dp)
    ) {
        DropdownMenu(
            expanded = true,
            onDismissRequest = {
                onDismissRequest()
            }
        ) {
            DropdownMenuItem(
                text = { Text("გალერეა", color = Color.Black) },
                onClick = {
                    // Handle option 1 click
                },
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_insert_photo_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {

                            }
                    )
                }
            )


            DropdownMenuItem(
                text = { Text("კამერა", color = Color.Black) },
                onClick = {
                    // Handle option 2 click
                },
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {

                            }
                    )
                }
            )

            DropdownMenuItem(
                text = { Text("ფაილები", color = Color.Black) },
                onClick = {
                    // Handle option 3 click
                },
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_folder_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {

                            }
                    )
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        CustomToolbar("", true, true, false, {}, {}, {}, {}, {}, {}, {}, {})
    }
}