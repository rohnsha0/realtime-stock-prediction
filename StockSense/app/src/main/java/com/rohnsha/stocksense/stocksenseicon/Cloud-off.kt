package com.rohnsha.stocksense.stocksenseicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.rohnsha.stocksense.StockSenseIcon

public val StockSenseIcon.`Cloud-off`: ImageVector
    get() {
        if (`_cloud-off` != null) {
            return `_cloud-off`!!
        }
        `_cloud-off` = Builder(name = "Cloud-off", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(22.61f, 16.95f)
                arcTo(5.0f, 5.0f, 0.0f, false, false, 18.0f, 10.0f)
                horizontalLineToRelative(-1.26f)
                arcToRelative(8.0f, 8.0f, 0.0f, false, false, -7.05f, -6.0f)
                moveTo(5.0f, 5.0f)
                arcToRelative(8.0f, 8.0f, 0.0f, false, false, 4.0f, 15.0f)
                horizontalLineToRelative(9.0f)
                arcToRelative(5.0f, 5.0f, 0.0f, false, false, 1.7f, -0.3f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(1.0f, 1.0f)
                lineTo(23.0f, 23.0f)
            }
        }
        .build()
        return `_cloud-off`!!
    }

private var `_cloud-off`: ImageVector? = null
