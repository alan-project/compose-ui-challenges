package com.example.burgerbuilder.ui.burgerbuilder

import androidx.lifecycle.ViewModel
import com.example.burgerbuilder.data.repository.BurgerRepository

class BurgerBuilderViewModel(
  burgerRepository: BurgerRepository,
) : ViewModel() {
  val burger = burgerRepository.burger
}
