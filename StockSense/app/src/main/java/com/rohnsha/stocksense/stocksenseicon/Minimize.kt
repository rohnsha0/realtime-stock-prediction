package com.rohnsha.stocksense.stocksenseicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.rohnsha.stocksense.StockSenseIcon

public val StockSenseIcon.Minimize: ImageVector
    get() {
        if (_minimize != null) {
            return _minimize!!
        }
        _minimize = Builder(name = "Minimize", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(8.0f, 3.0f)
                verticalLineToRelative(3.0f)
                arcToRelative(2.0f, 2.0f, 0.0f, false, true, -2.0f, 2.0f)
                horizontalLineTo(3.0f)
                moveToRelative(18.0f, 0.0f)
                horizontalLineToRelative(-3.0f)
                arcToRelative(2.0f, 2.0f, 0.0f, false, true, -2.0f, -2.0f)
                verticalLineTo(3.0f)
                moveToRelative(0.0f, 18.0f)
                verticalLineToRelative(-3.0f)
                arcToRelative(2.0f, 2.0f, 0.0f, false, true, 2.0f, -2.0f)
                horizontalLineToRelative(3.0f)
                moveTo(3.0f, 16.0f)
                horizontalLineToRelative(3.0f)
                arcToRelative(2.0f, 2.0f, 0.0f, false, true, 2.0f, 2.0f)
                verticalLineToRelative(3.0f)
            }
        }
        .build()
        return _minimize!!
    }

private var _minimize: ImageVector? = null
