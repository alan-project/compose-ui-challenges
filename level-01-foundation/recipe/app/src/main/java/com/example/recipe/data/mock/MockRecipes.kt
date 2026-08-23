package com.example.recipe.data.mock

import com.example.recipe.data.model.Recipe

object MockRecipes {
    // Curated Unsplash CDN images keep each mock recipe deterministic and relevant.
    private val RecipeImageIds = mapOf(
        "avocado-toast" to "https://images.unsplash.com/photo-1482049016688-2d3e1b311543",
        "tomato-pasta" to "https://images.unsplash.com/photo-1473093295043-cdd812d0e601",
        "berry-yogurt-bowl" to "https://images.unsplash.com/photo-1498837167922-ddd27525d352",
        "grilled-salmon" to "https://images.unsplash.com/photo-1504674900247-0877df9cc836",
        "mushroom-risotto" to "https://images.unsplash.com/photo-1505253716362-afaea1d3d1af",
        "blueberry-pancakes" to "https://images.unsplash.com/photo-1528712306091-ed0763094c98",
        "salmon-power-bowl" to "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe",
        "mushroom-rice" to "https://images.unsplash.com/photo-1551218808-94e220e084d2",
        "berry-oats" to "https://images.unsplash.com/photo-1512621776951-a57141f2eefd",
        "avocado-plate" to "https://images.unsplash.com/photo-1490645935967-10de6ba17061",
        "tomato-penne" to "https://images.unsplash.com/photo-1563379926898-05f4575a45d8",
        "blueberry-brunch" to "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0",
        "salmon-salad" to "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
        "mushroom-bowl" to "https://images.unsplash.com/photo-1505577058444-a3dab90d4253",
        "berry-parfait" to "https://images.unsplash.com/photo-1515003197210-e0cd71810b5f",
        "spicy-avocado-toast" to "https://images.unsplash.com/photo-1453614512568-c4024d13c247",
        "roasted-tomato-pasta" to "https://images.unsplash.com/photo-1551183053-bf91a1d81141",
        "protein-pancakes" to "https://images.unsplash.com/photo-1533777857889-4be7c70b33f7",
    )

    val items = listOf(
        Recipe(
            1,
            "Avocado Toast with Poached Egg",
            "Breakfast",
            15,
            4.8,
            410,
            recipeImageUrl("avocado-toast", 1),
            true
        ),
        Recipe(
            2, "Tomato Basil Pasta", "Italian", 30, 4.7, 560, recipeImageUrl("tomato-pasta", 2)
        ),
        Recipe(
            3,
            "Fresh Berry Yogurt Bowl",
            "Breakfast",
            10,
            4.9,
            320,
            recipeImageUrl("berry-yogurt-bowl", 3)
        ),
        Recipe(
            4,
            "Herb Grilled Salmon",
            "Healthy",
            35,
            4.8,
            490,
            recipeImageUrl("grilled-salmon", 4),
            true
        ),
        Recipe(
            5,
            "Creamy Mushroom Risotto",
            "Italian",
            40,
            4.6,
            610,
            recipeImageUrl("mushroom-risotto", 5)
        ),
        Recipe(
            6,
            "Blueberry Pancake Stack",
            "Breakfast",
            20,
            4.7,
            530,
            recipeImageUrl("blueberry-pancakes", 6)
        ),
        Recipe(
            7, "Salmon Power Bowl", "Healthy", 25, 4.5, 450, recipeImageUrl("salmon-power-bowl", 7)
        ),
        Recipe(
            8, "Mushroom Truffle Rice", "Quick", 25, 4.4, 580, recipeImageUrl("mushroom-rice", 8)
        ),
        Recipe(
            9,
            "Berry Overnight Oats",
            "Breakfast",
            8,
            4.8,
            350,
            recipeImageUrl("berry-oats", 9),
            true
        ),
        Recipe(
            10, "Avocado Garden Plate", "Healthy", 18, 4.6, 390, recipeImageUrl("avocado-plate", 10)
        ),
        Recipe(
            11, "Rustic Tomato Penne", "Italian", 28, 4.7, 545, recipeImageUrl("tomato-penne", 11)
        ),
        Recipe(
            12,
            "Blueberry Sunday Brunch",
            "Breakfast",
            22,
            4.9,
            570,
            recipeImageUrl("blueberry-brunch", 12)
        ),
        Recipe(
            13, "Lemon Salmon Salad", "Quick", 20, 4.5, 430, recipeImageUrl("salmon-salad", 13)
        ),
        Recipe(
            14,
            "Parmesan Mushroom Bowl",
            "Italian",
            35,
            4.6,
            600,
            recipeImageUrl("mushroom-bowl", 14)
        ),
        Recipe(
            15,
            "Ten-Minute Berry Parfait",
            "Quick",
            10,
            4.7,
            300,
            recipeImageUrl("berry-parfait", 15)
        ),
        Recipe(
            16,
            "Spicy Avocado Toast",
            "Quick",
            12,
            4.6,
            395,
            recipeImageUrl("spicy-avocado-toast", 16)
        ),
        Recipe(
            17,
            "Roasted Tomato Pasta",
            "Italian",
            32,
            4.8,
            575,
            recipeImageUrl("roasted-tomato-pasta", 17),
            true
        ),
        Recipe(
            18,
            "Protein Blueberry Pancakes",
            "Healthy",
            24,
            4.5,
            460,
            recipeImageUrl("protein-pancakes", 18)
        ),
    )

    private fun recipeImageUrl(keyword: String, lock: Int): String =
        "${RecipeImageIds.getValue(keyword)}?auto=format&fit=crop&w=640&q=80&sig=$lock"
}
