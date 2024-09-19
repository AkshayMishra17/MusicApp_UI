package com.example.musicapp

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter","UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainView() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    //allows us to find out on which view we are currently on
    val viewModel:MainViewModel = viewModel()
    val controller: NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    var currentRoute = navBackStackEntry?.destination?.route
    val dialogOpen = remember {
        mutableStateOf(false)
    }
    val currentScreen = remember{
    viewModel.currentScreen.value
    }
    val title = remember{
        mutableStateOf(currentScreen.title)
    }
    val isSheetFullScreen by remember {
        mutableStateOf(false)
    }
    val modifier = if(isSheetFullScreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()
    val modalSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {it != ModalBottomSheetValue.HalfExpanded}
        )
    val roundedCornerRadius = if(isSheetFullScreen) 0.dp else 12.dp
    val bottomBar: @Composable () -> Unit =  {
     if(currentScreen is Screen.DrawerScreen || currentScreen == Screen.BottomScreen.Home){
         BottomNavigation(
             Modifier
                 .wrapContentSize()
                 .fillMaxWidth()
                 .padding(10.dp),
                 backgroundColor = Color.White) {
             screenInBottom.forEach{
                 item ->
                 BottomNavigationItem(selected = currentRoute == item.broute,
                     onClick = { controller.navigate(item.broute)
                               title.value = item.bTitle
                               },
                     icon = { Icon(painter = painterResource(id = item.icon),contentDescription = item.broute) },
                     label = {Text(text = item.bTitle)}
                     )
             }
         }
     }
    }
    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius)
        ,sheetContent = {
        MoreBottomSheet(modifier = modifier)
    }){
    Scaffold(
        bottomBar = bottomBar,
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title.value,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .heightIn(25.dp)
                    )
                },
                actions = {
                    IconButton(onClick = { scope.launch {
                        if(modalSheetState.isVisible){
                            modalSheetState.hide()
                        }else{
                            modalSheetState.show()
                        }
                    } }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // Open the drawer
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Menu")
                    }
                },

            )
        },
        drawerContent = {
            // Add drawer content here if needed
            LazyColumn(modifier = Modifier.padding(16.dp)) {
             items(screensInDrawer){
              item ->
              DrawerItem(selected = currentRoute == item.droute,item =item ) {
                  scope.launch{
                      scaffoldState.drawerState.close()
                  }
                  if(item.droute == "add_account"){
                   dialogOpen.value = true
                  }else{
                      controller.navigate(item.droute)
                      title.value = item.dTitle
                  }
              }
          }
            }
        },
        content = {
            // Add the main content of your screen here
            Navigation(navController = controller, viewModel = viewModel, pd = it)
            AccountDialog(dialogOpen = dialogOpen)
        }
    )
}
}
    @Composable
fun DrawerItem(selected:Boolean,
               item:Screen.DrawerScreen,
               onDrawerItemClicked:() -> Unit){
    val background = if (selected) Color.DarkGray else Color.White
     Row(modifier = Modifier
         .fillMaxSize()
         .padding(horizontal = 10.dp, vertical = 15.dp)
         .background(background)
         .clickable {
             onDrawerItemClicked()
         }) {
         Icon(painter = painterResource(id = item.icon), contentDescription = item.dTitle,
        Modifier.padding(end = 8.dp, top = 4.dp)
        )
        Text(text = item.dTitle,
            style = MaterialTheme.typography.headlineMedium,
            )
    }

}
@Composable
fun MoreBottomSheet(modifier: Modifier){
  Box(modifier = Modifier
      .fillMaxWidth()
      .height(300.dp)
      .background(MaterialTheme.colorScheme.primary))
  {
      Column(modifier=Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
     Row(modifier = Modifier.padding(16.dp)) {
          Icon(modifier = Modifier.padding(end = 8.dp).clickable {  }, painter = painterResource(id = R.drawable.ic_settings)
          ,contentDescription = "Settings")
         Text(text = "Settings", fontSize = 20.sp, color = Color.White)

     }
          Row(modifier = Modifier.padding(16.dp)) {
              Icon(modifier = Modifier.padding(end = 8.dp).clickable {  }, painter = painterResource(id = R.drawable.ic_share)
                  , contentDescription = "Settings")
              Text(text = "Share", fontSize = 20.sp, color = Color.White)

          }
          Row(modifier = Modifier.padding(16.dp)) {
              Icon(modifier = Modifier.padding(end = 8.dp).clickable {  }, painter = painterResource(id = R.drawable.ic_help)
                  , contentDescription = "Settings")
              Text(text = "Help", fontSize = 20.sp, color = Color.White)

          }
      }
  }
}

@Composable
fun Navigation(navController: NavController,viewModel: MainViewModel,pd: PaddingValues){
    NavHost(navController = navController as NavHostController,
        startDestination = Screen.DrawerScreen.Account.route,
        modifier = Modifier.padding(pd)
    ){
        composable(Screen.BottomScreen.Home.broute){
       //add home screen
            HomeView()
        }
        composable(Screen.BottomScreen.Browse.broute){
            //add browse screen
            BrowseView()
        }
        composable(Screen.BottomScreen.Library.broute){
            //add library screen
            LibraryView()
        }
        composable(Screen.DrawerScreen.Account.route){
        AccountView()
        }
        composable(Screen.DrawerScreen.Subscription.route){
      SubscriptionView()
        }
    }
}
