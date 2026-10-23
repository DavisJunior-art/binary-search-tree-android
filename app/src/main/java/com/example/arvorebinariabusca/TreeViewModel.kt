package com.example.arvorebinariabusca

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TreeViewModel : ViewModel() {
    companion object {
        val challengeValues = listOf(25, 15, 50, 10, 22, 35, 70, 4, 12, 18, 24, 31, 44, 66, 90)
    }

    private val tree = BinarySearchTree()
    private val inserted = mutableListOf<Int>()
    private val _state = MutableStateFlow(TreeUiState())
    val state: StateFlow<TreeUiState> = _state.asStateFlow()

    init { resetChallenge() }

    fun insert(value: Int) {
        if (!tree.insert(value)) {
            publish("O valor " + value + " já está na árvore; valores repetidos não são inseridos.")
            return
        }
        inserted.add(value)
        publish("Valor " + value + " inserido na árvore.")
    }

    fun search(value: Int) {
        val path = tree.searchPath(value)
        val found = path.lastOrNull() == value
        val pathText = path.joinToString(" → ")
        val message = when {
            found -> "Valor " + value + " encontrado. Caminho: " + pathText
            path.isEmpty() -> "A árvore está vazia."
            else -> "Valor " + value + " não encontrado. Caminho percorrido: " + pathText
        }
        publish(message)
    }

    fun resetChallenge() {
        tree.clear()
        inserted.clear()
        challengeValues.forEach { value ->
            tree.insert(value)
            inserted.add(value)
        }
        publish("Sequência do desafio carregada.")
    }

    private fun publish(message: String) {
        _state.value = TreeUiState(
            treeText = tree.renderTree(),
            insertedValues = inserted.joinToString(", "),
            sortedValues = tree.inOrderValues().joinToString(", "),
            message = message
        )
    }
}
