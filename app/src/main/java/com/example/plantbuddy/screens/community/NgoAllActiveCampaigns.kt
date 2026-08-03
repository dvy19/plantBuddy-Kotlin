package com.example.plantbuddy.screens.community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plantbuddy.NGO.campaign.CampaignRepo
import com.example.plantbuddy.NGO.campaign.CampaignVM
import com.example.plantbuddy.NGO.campaign.GetActiveCampaignState
import com.example.plantbuddy.NGO.details.NgoDetailsRepo
import com.example.plantbuddy.NGO.details.NgoDetailsVM
import com.example.plantbuddy.Screens
import com.example.plantbuddy.auth.SessionManager
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NgoAllActiveCampaigns(
    mainNavController: NavController,
    ngo_id:Int?
){

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val context= LocalContext.current

    val scope = rememberCoroutineScope()

    val sessionManager= SessionManager(context)



    val campaignRepo= CampaignRepo(sessionManager)

    val campaignViewmodel: CampaignVM=viewModel{
        CampaignVM(campaignRepo)
    }

    val getActiveCampaignState by campaignViewmodel.getActiveCampaignState.collectAsState()

    LaunchedEffect(Unit) {

        campaignViewmodel.getActiveCampaign(true , ngo_id)

    }




    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(240.dp)
            ) {
                // Header spacing (or add a DrawerHeader here)
                Spacer(modifier = Modifier.height(16.dp))

                NavigationDrawerItem(
                    label = { Text("Saved Facts") },
                    selected = false,
                    onClick = {
                        mainNavController.navigate(Screens.SavedFactsScreen.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )


                NavigationDrawerItem(
                    label = { Text("Your Plants") },
                    selected = false,
                    onClick = {
                        mainNavController.navigate(Screens.YourPlantsScreen.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("Plant Catalog") },
                    selected = false,
                    onClick = {
                        mainNavController.navigate(Screens.PlantCatalogScreen.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("logout") },
                    selected = false,

                    onClick = {
                        sessionManager.logout()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                // Push the close button to the bottom of the drawer
                Spacer(modifier = Modifier.weight(1f))

                NavigationDrawerItem(
                    label = { Text("Close") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("View Your Community") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open navigation drawer"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Message,
                                contentDescription = "Messages"
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    mainNavController.navigate(Screens.CameraScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }
        ) { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item {

                    Text(
                        text = "Active Campaigns",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                }



                when(val state=getActiveCampaignState){
                    is GetActiveCampaignState.Idle -> {
                        item {
                            Text("Idle")
                        }
                    }
                    is GetActiveCampaignState.Loading -> {
                        item {
                            Text("Loading")
                        }
                    }
                    is GetActiveCampaignState.Success -> {

                        items(
                            items = state.data,
                            key = { it.id }
                        ){
                            ActiveCampaignCard(
                                logo = it.logo.toString(),
                                ngoTitle = it.title,
                                city = it.location,
                                startDate = it.start_date,
                                endDate = it.end_date,
                                modifier = Modifier.padding(horizontal = 16.dp),
                                onClick = {
                                    mainNavController.navigate("campaign_detail/${it.id}")
                                }
                            )
                        }

                    }
                    is GetActiveCampaignState.Error -> {
                        item {
                            Text("Failed to load plants")
                        }
                    }

                }


            }



            }
            }



}