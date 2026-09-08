package com.example.burgerbuilder.data.repository

import com.example.burgerbuilder.data.mock.MockBurger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BurgerRepositoryImpl : BurgerRepository {
    private val _burger = MutableStateFlow(MockBurger.item)

    override val burger = _burger.asStateFlow()
}
