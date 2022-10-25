package david.ferrer.myselectorscomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import david.ferrer.myselectorscomponents.ui.theme.MySelectorsComponentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var myName by remember{ mutableStateOf("") }
            var mySurname by remember{ mutableStateOf("") }
            var myEmail by remember{ mutableStateOf("") }

            var myContacts by rememberSaveable{ mutableStateOf(false) }
            var myVisible by rememberSaveable{ mutableStateOf(false) }
            var myTelefono by rememberSaveable{ mutableStateOf(false) }

            var myPhone by remember{ mutableStateOf("") }

            var myDeporte by rememberSaveable { mutableStateOf(false) }
            var myMuseo by rememberSaveable { mutableStateOf(false) }
            var myCine by rememberSaveable { mutableStateOf(false) }
            var myVideojuego by rememberSaveable { mutableStateOf(false) }
            var myViaje by rememberSaveable { mutableStateOf(false) }
            var myAnime by rememberSaveable { mutableStateOf(false) }

            var mostrarContenido by remember{ mutableStateOf(false) }

            var contador: Int

            MySelectorsComponentsTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MyTitle()
                    MySpace()
                    MyTextField(myName, "Nombre"){myName = it}
                    MySpace()
                    MyTextField(mySurname, "Apellidos"){mySurname = it}
                    MySpace()
                    MyTextField(myEmail, "Email"){myEmail = it}
                    MySpace()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text = "Añadir texto")
                        MyHorizontalSpace()
                        Text(text = "Visible")
                        MyHorizontalSpace()
                        Text(text = "Teléfono")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        MySwitch(myContacts){myContacts = it}
                        MyHorizontalSpace()
                        MySwitch(myVisible){myVisible = it}
                        MyHorizontalSpace()
                        MySwitch(myTelefono){myTelefono = it}
                    }
                    MySpace()
                    TextField(
                        value = myPhone,
                        onValueChange = {myPhone = it},
                        label = { Text(text = "Teléfono")},
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Red
                        )
                    )
                    MySpace()
                    Row {
                        Text("Intereses")
                        MyLastSpace()
                    }
                    Row{
                        Column {
                            MyCheckBox("Deportes", state = myDeporte){myDeporte = it}
                            MyCheckBox("Cine", state = myCine){myCine = it}
                            MyCheckBox("Viajes", state = myViaje){myViaje = it}
                        }
                        Column {
                            MyCheckBox("Museos", state = myMuseo){myMuseo = it}
                            MyCheckBox("Videojuegos", state = myVideojuego){myVideojuego = it}
                            MyCheckBox("Animes", state = myAnime){myAnime = it}
                        }
                    }
                    Row {
                        Text("Estudios")
                        MyLastSpace()
                    }
                    MultipleRadioButtons()
                    
                    Button(
                        onClick = { mostrarContenido = !mostrarContenido },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF8000)),
                        modifier = Modifier.width(250.dp)
                    ) {
                        Text(text = "Enviar", color = Color.White)
                    }

                    if(mostrarContenido){
                        LinearProgressIndicator(
                            modifier = Modifier.padding(top = 2.dp),
                            color = Color.Red,
                            backgroundColor = Color.Green
                        )
                        contador = 0
                        if(myDeporte)
                            contador++
                        if(myMuseo)
                            contador++
                        if(myCine)
                            contador++
                        if(myVideojuego)
                            contador++
                        if(myViaje)
                            contador++
                        if(myAnime)
                            contador++

                        val texto = "Ha seleccionado $contador interes"
                        if(contador == 1)
                            Text(texto)
                        else
                            Text(texto + "es")
                    }
                    
                }

            }
        }
    }
}

@Composable
fun MyTitle(){
    Text(
        text = "Registro",
        modifier = Modifier.padding(top = 10.dp),
        fontSize = 40.sp,
        color = Color(0xFF900C3F)
    )
}

@Composable
fun MyTextField(name: String,texto : String, onValueChanged: (String) -> Unit){
    TextField(
        value = name,
        onValueChange = {onValueChanged(it)},
        label = { Text(text = texto)}
    )
}

@Composable
fun MySpace(){
    Spacer(modifier = Modifier.padding(5.dp))
}

@Composable
fun MySwitch(state: Boolean, onCheckedChange: (Boolean) -> Unit){
    Switch(
        checked = state,
        onCheckedChange = { onCheckedChange(!state) }
    )
}

@Composable
fun MyHorizontalSpace(){
    Spacer(modifier = Modifier.padding(horizontal = 20.dp))
}

@Composable
fun MyLastSpace(){
    Spacer(modifier = Modifier.padding(horizontal = 100.dp))
}

@Composable
fun MyCheckBox(texto: String,state: Boolean, onCheckedChange: (Boolean) -> Unit){
    Row(modifier = Modifier.padding(2.dp)) {
        Checkbox(checked = state, onCheckedChange = { onCheckedChange(!state) })
        Spacer(modifier = Modifier.width(2.dp))
        Text(texto, modifier = Modifier.padding(vertical = 12.dp))
    }
}

@Composable
fun MultipleRadioButtons() {
    val selectedValue = remember { mutableStateOf("") }

    val isSelectedItem: (String) -> Boolean = { selectedValue.value == it }
    val onChangeState: (String) -> Unit = { selectedValue.value = it }

    val items = listOf("No", "Secundaria", "Superior")
    Row(Modifier.padding(8.dp)) {
        items.forEach { item ->
            Row(
                modifier = Modifier
                    .selectable(
                        selected = isSelectedItem(item),
                        onClick = { onChangeState(item) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = isSelectedItem(item),
                    onClick = null
                )
                Text(
                    text = item,
                )
            }
        }
    }
}