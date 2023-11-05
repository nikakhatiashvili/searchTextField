package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var searchText by remember { mutableStateOf("") }
            var camera by remember { mutableStateOf(false) }
            var imageSearch by remember { mutableStateOf(true) }

            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Column {
                        CustomToolbar(searchText,camera,true,imageSearch, {
                            searchText = it
                        }, {}, {}, {}, {
                            camera = true
                        },{
                            camera = false
                        },{
                            imageSearch = false
                        })
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
    showBack:Boolean,
    imageSearch:Boolean,
    onSearchTextChange: (String) -> Unit,
    onLeftArrowClicked: () -> Unit,
    onSearchIconClick: () -> Unit,
    onClearTextClick: () -> Unit,
    onCameraIconClick: () -> Unit,
    onCloseMenu:() -> Unit,
    onBinClicked: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (showBack){
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
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                if (imageSearch){
                    //replace with image user selected from gallery or smth
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
                                    onLeftArrowClicked()
                                }
                        )
                    }
                }else{
                    Image(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                onSearchIconClick()
                            }
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                Box(modifier = Modifier.weight(1f)) {
                    BasicTextField(
                        value = searchText,
                        onValueChange = { onSearchTextChange(it) },
                        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )
                    if (searchText.isEmpty()) {
                        Text(text = "მოძებნე რაც გაგიხარდება", color = Color.Gray)
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
                    if (camera){
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
                text = { Text("გალერეა",color = Color.Black) },
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
                text = { Text("კამერა",color = Color.Black) },
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
        CustomToolbar("", true, true,false,{}, {}, {}, {}, {},{},{})
    }
}