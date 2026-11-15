package io.github.mariuszprzeczek.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            fontSize = 48.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth(),
            text = state.display
        )
        Row {
            CalcButton("7", { onDigit(7) }, Modifier.weight(1f))
            CalcButton("8", { onDigit(8) }, Modifier.weight(1f))
            CalcButton("9", { onDigit(9) }, Modifier.weight(1f))
            CalcButton("÷", { onOperator('/') }, Modifier.weight(1f))
        }
        Row {
            CalcButton("4", { onDigit(4) }, Modifier.weight(1f))
            CalcButton("5", { onDigit(5) }, Modifier.weight(1f))
            CalcButton("6", { onDigit(6) }, Modifier.weight(1f))
            CalcButton("-", { onOperator('-') }, Modifier.weight(1f))
        }
        Row {
            CalcButton("1", { onDigit(1) }, Modifier.weight(1f))
            CalcButton("2", { onDigit(2) }, Modifier.weight(1f))
            CalcButton("3", { onDigit(3) }, Modifier.weight(1f))
            CalcButton("×", { onOperator('*') }, Modifier.weight(1f))
        }
        Row {
            CalcButton("C",  onClear, Modifier.weight(1f))
            CalcButton("0", { onDigit(0) }, Modifier.weight(1f))
            CalcButton("=", onEquals, Modifier.weight(1f))
            CalcButton("+", { onOperator('+') }, Modifier.weight(1f))
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
fun CalcButton(label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onClick, modifier = modifier) { Text(label) }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorTheme {
        CalculatorDisplay(
            state = CalculatorUiState(display = "123"),
            onDigit = {},
            onOperator = {},
            onEquals = {},
            onClear = {}
        )
    }
}