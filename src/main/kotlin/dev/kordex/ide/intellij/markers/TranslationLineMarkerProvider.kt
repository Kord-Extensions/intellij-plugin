package dev.kordex.ide.intellij.markers

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiVariable
import com.kotlindiscord.kord.extensions.tooling.Translatable
import com.kotlindiscord.kord.extensions.tooling.TranslatableType
import dev.kordex.ide.intellij.logger

abstract class TranslationLineMarkerProvider : LineMarkerProviderDescriptor() {
	abstract val type: TranslatableType

	override fun collectSlowLineMarkers(
		elements: MutableList<out PsiElement>,
		result: MutableCollection<in LineMarkerInfo<*>>
	) {
		collectMarkers(elements, result, false)
	}

	override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? =
		null

	fun collectMarkers(
		elements: List<PsiElement>,
		results: MutableCollection<in LineMarkerInfo<*>>,
		forNavigation: Boolean,
	) {
		elements.forEach { elem ->
			collectMarkersForElement(elem, results)

			if (forNavigation && elem is PsiNameIdentifierOwner) {
				val identifier = elem.nameIdentifier

				if (identifier != null && identifier !in elements) {
					collectMarkersForElement(identifier, results)
				}
			}
		}
	}

	fun collectMarkersForElement(
		element: PsiElement,
		results: MutableCollection<in LineMarkerInfo<*>>,
	) {
		element.references.forEach { reference ->
			if (reference is PsiVariable) {
				reference.annotations.forEach { annotation ->
					logger.info("Found annotation: $annotation")

					if (annotation.qualifiedName == Translatable::class.qualifiedName) {
						logger.info("Annotation matches.")

						val annotationType = annotation.findAttributeValue("type")

						logger.info("Found type parameter: $annotationType")

						if (annotationType is PsiReferenceExpression) {
							logger.info("Type reference name: ${annotationType.referenceName}")

							val enumType = TranslatableType.valueOf(annotationType.referenceName!!)

							logger.info("Type is translatable type: $enumType")

							if (enumType == this.type) {
								logger.info("Correct translatable type for this line marker provider.")

								val info = getMarkerInfo(reference)

								if (info != null) {
									logger.info("Adding line info: $info")

									results.add(info)
								}
							}
						}
					}
				}
			}
		}
	}

	abstract fun getMarkerInfo(reference: PsiVariable): LineMarkerInfo<*>?
}
