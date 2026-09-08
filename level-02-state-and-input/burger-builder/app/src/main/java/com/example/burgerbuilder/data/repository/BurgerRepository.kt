package com.example.burgerbuilder.data.repository

import com.example.burgerbuilder.data.model.Burger
import kotlinx.coroutines.flow.StateFlow

interface BurgerRepository {
    val burger: StateFlow<Burger>
}
