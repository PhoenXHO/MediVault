package com.ensas.medivault.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes

// Defines the standard shapes used throughout the app
val Shapes = Shapes(
    extraSmall = RoundedCornerShape(Dimensions.radiusExtraSmall),
    small = RoundedCornerShape(Dimensions.radiusSmall),
    medium = RoundedCornerShape(Dimensions.radiusMedium),
    large = RoundedCornerShape(Dimensions.radiusLarge),
    extraLarge = RoundedCornerShape(Dimensions.radiusExtraLarge),
)

object CustomShapes {
    // Shape with rounded corners on the left side
    val LeftRoundedCornerShape = RoundedCornerShape(
        topStart = Dimensions.radiusExtraLarge,
        bottomStart = Dimensions.radiusExtraLarge,
        topEnd = Dimensions.radiusMedium,
        bottomEnd = Dimensions.radiusMedium
    )
    
    // Shape with rounded corners on the right side
    val RightRoundedCornerShape = RoundedCornerShape(
        topStart = Dimensions.radiusMedium,
        bottomStart = Dimensions.radiusMedium,
        topEnd = Dimensions.radiusExtraLarge,
        bottomEnd = Dimensions.radiusExtraLarge
    )
    
    // Uniform rounded corner shape
    val RoundedCornerShape = RoundedCornerShape(Dimensions.radiusExtraLarge)
}