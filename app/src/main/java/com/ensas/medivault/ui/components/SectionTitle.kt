package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.MedicalServices
import androidx.compose.material.icons.sharp.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.Typography

@Composable
fun SectionTitle(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = Dimensions.paddingMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(end = Dimensions.paddingSmall)
                .size(Typography.titleSmall.fontSize.value.dp)
        )
        Text(
            text = title,
            style = Typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SectionTitlePreview() {
    SectionTitle(
        title = "Section Title",
        icon = Icons.Default.MedicalServices
    )
}