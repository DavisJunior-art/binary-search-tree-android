package com.example.arvorebinariabusca

class BinarySearchTree {
    private data class Node(
        val value: Int,
        var left: Node? = null,
        var right: Node? = null
    )

    private var root: Node? = null

    fun clear() {
        root = null
    }

    fun insert(value: Int): Boolean {
        val currentRoot = root
        if (currentRoot == null) {
            root = Node(value)
            return true
        }

        var current = currentRoot
        while (true) {
            when {
                value == current.value -> return false
                value < current.value -> {
                    val next = current.left
                    if (next == null) {
                        current.left = Node(value)
                        return true
                    }
                    current = next
                }
                else -> {
                    val next = current.right
                    if (next == null) {
                        current.right = Node(value)
                        return true
                    }
                    current = next
                }
            }
        }
    }

    fun searchPath(value: Int): List<Int> {
        val visited = mutableListOf<Int>()
        var current = root
        while (current != null) {
            visited.add(current.value)
            when {
                value == current.value -> return visited
                value < current.value -> current = current.left
                else -> current = current.right
            }
        }
        return visited
    }

    fun inOrderValues(): List<Int> {
        val result = mutableListOf<Int>()
        fun visit(node: Node?) {
            if (node == null) return
            visit(node.left)
            result.add(node.value)
            visit(node.right)
        }
        visit(root)
        return result
    }

    fun renderTree(): String {
        val currentRoot = root ?: return "(árvore vazia)"
        val lines = mutableListOf(currentRoot.value.toString())

        fun appendChildren(node: Node, prefix: String) {
            val children = buildList {
                node.left?.let { add("E" to it) }
                node.right?.let { add("D" to it) }
            }
            children.forEachIndexed { index, entry ->
                val side = entry.first
                val child = entry.second
                val isLast = index == children.lastIndex
                val branch = if (isLast) "└── " else "├── "
                lines.add(prefix + branch + side + ": " + child.value)
                val nextPrefix = prefix + (if (isLast) "    " else "│   ")
                appendChildren(child, nextPrefix)
            }
        }

        appendChildren(currentRoot, "")
        return lines.joinToString(separator = "\n")
    }
}
