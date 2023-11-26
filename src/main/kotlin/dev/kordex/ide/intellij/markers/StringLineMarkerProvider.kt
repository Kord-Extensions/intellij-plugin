package dev.kordex.ide.intellij.markers

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.psi.PsiVariable
import com.kotlindiscord.kord.extensions.tooling.TranslatableType

class StringLineMarkerProvider : TranslationLineMarkerProvider() {
	override val type: TranslatableType = TranslatableType.STRING

	override fun getMarkerInfo(reference: PsiVariable): LineMarkerInfo<*> =
		NavigationGutterIconBuilder.create(AllIcons.Actions.InlayGlobe)
			.setTooltipText("String!")
			.createLineMarkerInfo(reference)

	override fun getName(): String =
		"Translations string"
}
