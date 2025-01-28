package com.example.myapplication.Icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Power_rounded: ImageVector
    get() {
        if (_Power_rounded != null) {
            return _Power_rounded!!
        }
        _Power_rounded = ImageVector.Builder(
            name = "Power_rounded",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.White),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12f, 12.85f)
                quadToRelative(-0.425f, 0f, -0.712f, -0.288f)
                quadTo(11f, 12.275f, 11f, 11.85f)
                verticalLineToRelative(-8f)
                quadToRelative(0f, -0.425f, 0.288f, -0.713f)
                quadToRelative(0.287f, -0.287f, 0.712f, -0.287f)
                reflectiveQuadToRelative(0.713f, 0.287f)
                reflectiveQuadToRelative(0.287f, 0.713f)
                verticalLineToRelative(8f)
                quadToRelative(0f, 0.425f, -0.287f, 0.712f)
                quadToRelative(-0.288f, 0.288f, -0.713f, 0.288f)
                moveToRelative(0f, 8f)
                quadToRelative(-1.875f, 0f, -3.512f, -0.712f)
                quadToRelative(-1.638f, -0.713f, -2.85f, -1.926f)
                quadTo(4.425f, 17f, 3.712f, 15.363f)
                reflectiveQuadTo(3f, 11.85f)
                quadToRelative(0f, -1.725f, 0.625f, -3.3f)
                reflectiveQuadToRelative(1.8f, -2.85f)
                quadToRelative(0.275f, -0.3f, 0.7f, -0.3f)
                reflectiveQuadToRelative(0.725f, 0.3f)
                quadToRelative(0.275f, 0.275f, 0.25f, 0.687f)
                reflectiveQuadToRelative(-0.3f, 0.738f)
                quadToRelative(-0.875f, 0.975f, -1.337f, 2.187f)
                quadTo(5f, 10.525f, 5f, 11.85f)
                quadToRelative(0f, 2.925f, 2.038f, 4.962f)
                reflectiveQuadTo(12f, 18.85f)
                reflectiveQuadToRelative(4.962f, -2.038f)
                quadTo(19f, 14.775f, 19f, 11.85f)
                quadToRelative(0f, -1.325f, -0.475f, -2.563f)
                quadToRelative(-0.475f, -1.237f, -1.35f, -2.212f)
                quadToRelative(-0.275f, -0.3f, -0.287f, -0.7f)
                quadToRelative(-0.013f, -0.4f, 0.262f, -0.675f)
                quadToRelative(0.3f, -0.3f, 0.725f, -0.3f)
                reflectiveQuadToRelative(0.7f, 0.3f)
                quadToRelative(1.175f, 1.275f, 1.8f, 2.85f)
                reflectiveQuadToRelative(0.625f, 3.3f)
                quadToRelative(0f, 1.875f, -0.712f, 3.513f)
                quadToRelative(-0.713f, 1.637f, -1.925f, 2.849f)
                reflectiveQuadToRelative(-2.85f, 1.926f)
                quadToRelative(-1.638f, 0.712f, -3.513f, 0.712f)
            }
        }.build()
        return _Power_rounded!!
    }

private var _Power_rounded: ImageVector? = null
