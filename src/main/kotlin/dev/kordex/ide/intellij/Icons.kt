package dev.kordex.ide.intellij

import com.intellij.openapi.util.IconLoader

object Icons {
	private fun load(path: String) = IconLoader.getIcon(path, Icons::class.java)
}
