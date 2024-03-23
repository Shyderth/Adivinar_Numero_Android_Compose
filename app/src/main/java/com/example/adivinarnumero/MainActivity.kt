package com.example.adivinarnumero

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivinarnumero.ui.theme.AdivinarNumeroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdivinarNumeroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JuegoAdivinar()
                }
            }
        }
    }
}

@Composable
fun JuegoAdivinar(modifier: Modifier = Modifier) {
    /*TODO: buscar una mejor manera de preservar el valor de las variables*/
    var numeroRandom by remember{mutableIntStateOf((1..50).random())}
    var num_adv by remember{mutableStateOf(true)}
    if (num_adv) {
        numeroRandom = (1..50).random()
        num_adv = false
    }
    var pista by remember{ mutableStateOf("menor")}
    var numberInput by remember {mutableStateOf("")}
    val numero = numberInput.toIntOrNull()?: 0

    //Columna que almacena los elementos
    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.titulo),
            fontSize = 30.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )
        //Campo de texto para el numero adivinado
        NumberField(
            label = R.string.numero_adivinado,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = numberInput,
            onValueChange ={numberInput = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 24.dp)
        )
        //Boton que envia el numero
        FloatingActionButton(
            onClick = {
                pista = validarNumero(numero,numeroRandom)
                if (pista=="Correcto. Otra vez") {
                    num_adv = true
                }
                      },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.enviar))
        }
        Spacer(modifier = Modifier.height(32.dp))
        //Texto que indica si el numero adivinado es correcto, menor o mayor
        Text(
            text = stringResource(R.string.Texto_pista, "$pista!"),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
        //Text(text = numeroRandom.toString())
    }
}

@Composable
fun NumberField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        label = { Text(stringResource(label))},
        leadingIcon = null,
        singleLine = true,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}


fun validarNumero(guesInt: Int, realInt: Int): String {
    val pista = when {
        guesInt<realInt -> "menor"
        guesInt>realInt -> "mayor"
        else -> "Correcto. Otra vez"
    }

    return pista
}


