package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberRichTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class SliderCategory {
    UserScore, UserVotes, Runtime,
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleSliderX(
    initialValue: Float,
    minValue: Float,
    maxValue: Float,
    steps: Int = 0,
    scope: CoroutineScope,
    onValueChange: (Float) -> Unit,
    sliderCategory: SliderCategory,
) {

    val currentSliderValue = remember {
        mutableFloatStateOf(initialValue)
    }

    val tooltipState = rememberRichTooltipState(isPersistent = false)

    Slider(value = currentSliderValue.floatValue,
        valueRange = minValue..maxValue,
        steps = steps,
        onValueChange = {
            onValueChange(it)
            currentSliderValue.floatValue = it
            scope.launch {
                tooltipState.show()
            }
        },
        colors = SliderDefaults.colors(
            activeTrackColor = Darkness.rise
        ),
        thumb = {
            ThumbWithTooltip(
                text = getToolTipStringAsPerSlider(sliderCategory), tooltipState = tooltipState
            )
        })


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RangeSliderX(
    initialRange: ClosedFloatingPointRange<Float>,
    minValue: Float,
    maxValue: Float,
    steps: Int = 0,
    sliderCategory: SliderCategory,

    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    scope: CoroutineScope,

    ) {

    val currentSliderValue = remember {
        mutableStateOf(initialRange)
    }

    val tooltipState = rememberRichTooltipState(isPersistent = false)



    RangeSlider(
        value = currentSliderValue.value,
        valueRange = minValue..maxValue,
        steps = steps,
        onValueChange = {
            onValueChange(it)
            currentSliderValue.value = it
            scope.launch {
                tooltipState.show()

            }
        },
        colors = SliderDefaults.colors(
            activeTrackColor = Darkness.rise, thumbColor = Darkness.rise
        ),
        startThumb = {
            ThumbWithTooltip(
                text = getToolTipStringAsPerSlider(sliderCategory), tooltipState = tooltipState
            )

        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SliderValueToolTip(text: String, tooltipState: RichTooltipState) {
    RichTooltipBox(tooltipState = tooltipState, text = {
        Text(text = text, style = StylesX.labelMedium.copy(color = Darkness.light))
    }) {

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThumbWithTooltip(text: String, tooltipState: RichTooltipState) {
    SliderValueToolTip(text = text, tooltipState = tooltipState)
    Icon(
        imageVector = Icons.Filled.Circle,
        contentDescription = "Circle Icon Thumb",
        tint = Darkness.rise
    )
}

private fun getToolTipStringAsPerSlider(
    sliderCategory: SliderCategory, initialValue: Float = 0F, finalValue: Float = 0F
) = when (sliderCategory) {
    SliderCategory.UserScore -> "Rated ${initialValue.toInt()} - ${finalValue.toInt()}"
    SliderCategory.UserVotes -> initialValue.toInt().toString()
    SliderCategory.Runtime -> "${initialValue.toInt()} minutes - ${finalValue.toInt()} minutes"
}
