package io.github.mariuszprzeczek.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.mariuszprzeczek.calculator.ui.theme.CalculatorTheme

@Composable
fun CalculatorDisplay(
    state: CalculatorUiState,
    onDigit: (Int) -> Unit,
    onOperator: (Char) -> Unit,
    onEquals: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)
    ) {
        Text(
            fontSize = 64.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth(),
            text = state.display,
            maxLines = 1
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalcButton("7", { onDigit(7) }, Modifier
                .weight(1f)
                .aspectRatio(1f))
            CalcButton("8", { onDigit(8) }, Modifier
                .weight(1f)
                .aspectRatio(1f))
            CalcButton("9", { onDigit(9) }, Modifier
                .weight(1f)
                .aspectRatio(1f))
            CalcButton(
                "÷",
                { onOperator('/') },
                Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                MaterialTheme.colorScheme.tertiary
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalcButton("4", { onDigit(4) }, Modifier
                .weight(1f)
                .aspectRatio(1f))
            CalcButton("5", { onDigit(5) }, Modifier
                .weight(1f)
                .aspectRatio(1f))
            CalcButton("6", { onDigit(6) }, Modifier
                .weight(1f)
                .aspectRatio(1f))
            CalcButton(
                "-",
                { onOperator('-') },
                Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                MaterialTheme.colorScheme.tertiary
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalcButton("1", { onDigit(1) }, Modifier
                .weight(1f)
                .aspectRatio(1f))
            CalcButton("2", { onDigit(2) }, Modifier
                .weight(1f)
                .aspectRatio(1f))
            CalcButton("3", { onDigit(3) }, Modifier
                .weight(1f)
                .aspectRatio(1f))
            CalcButton(
                "×",
                { onOperator('*') },
                Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                MaterialTheme.colorScheme.tertiary
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalcButton(
                "C",
                onClear,
                Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                MaterialTheme.colorScheme.secondary
            )
            CalcButton("0", { onDigit(0) }, Modifier
                .weight(1f)
                .aspectRatio(1f))
            CalcButton(
                "=",
                onEquals,
                Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                MaterialTheme.colorScheme.tertiary
            )
            CalcButton(
                "+",
                { onOperator('+') },
                Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier, viewModel: CalculatorViewModel = viewModel()) {
    CalculatorDisplay(
        state = viewModel.uiState,
        onDigit = viewModel::onDigit,
        onOperator = viewModel::onOperator,
        onEquals = viewModel::onEquals,
        onClear = viewModel::onClear,
        modifier = modifier
    )
}

@Composable
fun CalcButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) { Text(label, fontSize = 28.sp) }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorTheme {
        CalculatorDisplay(
            state = CalculatorUiState(display = "123456789"),
            onDigit = {},
            onOperator = {},
            onEquals = {},
            onClear = {}
        )
    }
}