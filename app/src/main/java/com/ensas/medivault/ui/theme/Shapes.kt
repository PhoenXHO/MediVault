package com.ensas.medivault.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(Dimensions.radiusExtraSmall),
    small = RoundedCornerShape(Dimensions.radiusSmall),
    medium = RoundedCornerShape(Dimensions.radiusMedium),
    large = RoundedCornerShape(Dimensions.radiusLarge),
    extraLarge = RoundedCornerShape(Dimensions.radiusExtraLarge),
)

object CustomShapes {
    val LeftRoundedCornerShape = RoundedCornerShape(
        topStart = Dimensions.radiusExtraLarge,
        bottomStart = Dimensions.radiusExtraLarge,
        topEnd = Dimensions.radiusMedium,
        bottomEnd = Dimensions.radiusMedium
    )
    val RightRoundedCornerShape = RoundedCornerShape(
        topStart = Dimensions.radiusMedium,
        bottomStart = Dimensions.radiusMedium,
        topEnd = Dimensions.radiusExtraLarge,
        bottomEnd = Dimensions.radiusExtraLarge
    )
    val RoundedCornerShape = RoundedCornerShape(Dimensions.radiusExtraLarge)
}