package com.test.pokemonapp.navDrawer

import android.view.Menu
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.pokemonapp.R
import com.test.pokemonapp.data.remote.models.MenuItem
import com.test.pokemonapp.ui.theme.PokemonAppTheme
import kotlinx.coroutines.launch

@Composable
fun DrawerHeader() {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
            contentDescription = "Pokemon Logo",
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))


    }
}

@Composable
fun DrawerBody(
    menuItems: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 20.sp),
    onClickItem: (MenuItem) -> Unit
) {
    var activeMenu by remember {
        mutableStateOf("home")
    }
    LazyColumn(modifier = modifier)
    {
        items(menuItems) { menuItem ->

            MenuItem(
                menuItem = menuItem,
                modifier = modifier.padding(horizontal = 10.dp),
                itemTextStyle = itemTextStyle,
                backgroundColor = if (activeMenu == menuItem.menuID) {
                    MaterialTheme.colors.background
                } else {
                    Color.LightGray
                }
            )
            {
                onClickItem.invoke(menuItem)
                activeMenu= menuItem.menuID
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

    }

}

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    menuItem: MenuItem,
    itemTextStyle: TextStyle = TextStyle(fontSize = 20.sp),
    backgroundColor: Color = Color.LightGray,
    onClickItem: () -> Unit
) {


    Row(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(
                backgroundColor
            )
            .clickable {
                onClickItem.invoke()
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = menuItem.icon,
            contentDescription = menuItem.contentDescription,
            tint = MaterialTheme.colors.onSurface
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = menuItem.title, style = itemTextStyle,

            modifier = Modifier.weight(1f)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokemonAppTheme {
        Column {
            DrawerHeader()

            DrawerBody(menuItems = listOf(
                MenuItem(
                    "home",
                    "Home",
                    Icons.Default.Home,
                    "home"
                ), MenuItem(
                    "setting",
                    "Setting",
                    Icons.Default.Settings,
                    "setting"
                ), MenuItem(
                    "help",
                    "Help",
                    Icons.Default.Star,
                    "help"
                ), MenuItem(
                    "share",
                    "Share",
                    Icons.Default.Share,
                    "Share"
                ), MenuItem(
                    "close",
                    "Close",
                    Icons.Default.Close,
                    "close"
                )
            ), onClickItem = {

            })
        }
    }

}