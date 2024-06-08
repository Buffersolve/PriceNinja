package cards

import ShopMapper
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import priceninjakmp.composeapp.generated.resources.Res
import priceninjakmp.composeapp.generated.resources.delete_card
import priceninjakmp.composeapp.generated.resources.delete_card_screen
import priceninjakmp.composeapp.generated.resources.no
import priceninjakmp.composeapp.generated.resources.trash
import priceninjakmp.composeapp.generated.resources.yes
import pxToDp
import utils.Gray
import utils.GrayNavNar
import utils.Main
import kotlin.jvm.Transient

data class CardScreen(
    @Transient private val readLong: (String) -> Long?,
    @Transient private val readString: (String) -> String?,
    @Transient private val delete: (String) -> Unit,
    private val title: String
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var showDeleteDialog by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize().padding(top = pxToDp(WindowInsets.statusBars.getTop(LocalDensity.current)).dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp).clip(shape = RoundedCornerShape(50))
                        .clickable {
                            navigator.pop()
                        }
                )
                Text(ShopMapper(title), modifier = Modifier.align(Alignment.Center))
            }

            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(320.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp)
                    .background(color = GrayNavNar, shape = RoundedCornerShape(16.dp))
            ) {
                Text(
                    readString(title) ?: "",
                    modifier = Modifier.align(Alignment.Center)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                )
            }

            DeleteButton {
                showDeleteDialog = true
            }

            if (showDeleteDialog) {
                DeleteDialog(onDeleteClick = {
                    delete(title)
                    showDeleteDialog = false
                    navigator.pop()
                }, onDismissRequest = {
                    showDeleteDialog = false
                })
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun DeleteButton(
        onClick: () -> Unit
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(74.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
                .background(color = GrayNavNar, shape = RoundedCornerShape(8.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
        ) {
            Row(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)) {
                Icon(
                    painter = painterResource(Res.drawable.trash),
                    contentDescription = "Delete",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    tint = Main
                )
                Text(
                    stringResource(Res.string.delete_card),
                    modifier = Modifier.align(Alignment.CenterVertically).padding(start = 8.dp),
                    color = Main
                )
            }


        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun DeleteDialog(onDeleteClick: () -> Unit, onDismissRequest: () -> Unit) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(12.dp),
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                    Text(
                        text =  stringResource(Res.string.delete_card),
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 12.dp)
                            .align(Alignment.CenterHorizontally),
                        fontSize = 16.sp
                    )
                    Text(
                        text = stringResource(Res.string.delete_card_screen),
                        color = Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = 14.sp
                    )

                    Row(modifier = Modifier.padding(top = 24.dp)) {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = GrayNavNar),
                            modifier = Modifier.padding(end = 4.dp).clip(RoundedCornerShape(6.dp))
                                .weight(1f),
                            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
                            onClick = { onDismissRequest() }) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = stringResource(Res.string.no),
                                color = Gray,
                                fontSize = 14.sp,
                            )
                        }
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Main),
                            modifier = Modifier.padding(start = 4.dp).clip(RoundedCornerShape(6.dp))
                                .weight(1f),
                            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
                            onClick = { onDeleteClick() }) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = stringResource(Res.string.yes),
                                color = Color.White,
                                fontSize = 14.sp,
                            )
                        }

                    }

                }
            }
        }
    }


}