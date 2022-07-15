package com.kimdo.myrunningapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.kimdo.myrunningapp.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class StatisticsViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {
}