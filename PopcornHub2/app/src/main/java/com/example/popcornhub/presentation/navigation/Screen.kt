package com.example.popcornhub.presentation.navigation

sealed class Screen(val route: String) {
    data object Home:Screen("home")
    data object Details:Screen("details/{contentId}"){
        fun createRoute(contentId:String)="details/$contentId"
    }

}