package com.yorick.curd.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yorick.curd.data.model.WaterIntake

@Composable
fun WaterIntakeScreen(
    waterIntakes: List<WaterIntake>,
    onAdd: (Int) -> Unit,
    onUpdate: (Int, Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    var intakeAmount by remember { mutableStateOf("") }
    var selectedIntakeId by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Water Intake Records", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = intakeAmount,
            onValueChange = { intakeAmount = it },
            label = { Text("Water Intake (ml)") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (intakeAmount.isNotBlank()) {
                onAdd(intakeAmount.toInt())
                intakeAmount = ""
            }
        }) {
            Text("Add Water Intake")
        }

        Spacer(modifier = Modifier.height(16.dp))

        waterIntakes.forEach { intake ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Intake: ${intake.intakeAmountMl} ml at ${intake.intakeTime}")
                Row {
                    Button(onClick = {
                        selectedIntakeId = intake.intakeId
                        intakeAmount = intake.intakeAmountMl.toString()
                    }) {
                        Text("Edit")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onDelete(intake.intakeId) }) {
                        Text("Delete")
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (selectedIntakeId != -1) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (intakeAmount.isNotBlank()) {
                    onUpdate(selectedIntakeId, intakeAmount.toInt())
                    selectedIntakeId = -1
                    intakeAmount = ""
                }
            }) {
                Text("Update Water Intake")
            }
        }
    }
}
