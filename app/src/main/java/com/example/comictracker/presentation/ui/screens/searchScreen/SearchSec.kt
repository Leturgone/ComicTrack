package com.example.comictracker.presentation.ui.screens.searchScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


/**
 * Search sec
 *
 * @param navController
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSec(navController: NavHostController){
    var textFieldState = rememberTextFieldState()
    var expanded by rememberSaveable { mutableStateOf(false)}

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Search comics",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold)
            SearchBar(
                inputField = { SearchBarDefaults.InputField(
                    state = textFieldState,
                    shape = RoundedCornerShape(12.dp),
                    onSearch = {expanded = false },
                    expanded = expanded,
                    onExpandedChange = {expanded = it},
                    placeholder = { Text("Search") },
                    leadingIcon = { Icon(Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            navController.navigate("search_result/${textFieldState.text}")
                        }
                        ) },
                    trailingIcon = { if (textFieldState.text.isNotEmpty()){
                            Icon(Icons.Filled.Cancel,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.clickable {
                                    textFieldState.clearText()
                                    expanded = false
                                }
                            )
                        }
                    }
                )},
                expanded = expanded,
                onExpandedChange = {expanded = it}, modifier = Modifier.padding(bottom = 16.dp)
            ) {

            }
    }


}