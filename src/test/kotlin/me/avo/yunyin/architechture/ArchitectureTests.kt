package me.avo.yunyin.architechture

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.jupiter.api.Test
import javax.persistence.Entity

class ArchitectureTests {

    private val root = "me.avo.yunyin"
    private val importedClasses = ClassFileImporter().importPackages(root)
    private val repository = pkg("repository")
    private val controller = pkg("controller")
    private val view = pkg("view")

    @Test fun `no entity in presentation`() =
        classes()
            .that().areAnnotatedWith(Entity::class.java)
            .should()
            .onlyBeAccessed().byClassesThat().resideOutsideOfPackages(controller, view)
            .check(importedClasses)

    @Test fun `no repository in presentation`() =
        noClasses()
            .that().resideInAPackage(repository)
            .should()
            .dependOnClassesThat().resideInAnyPackage(controller, view)
            .check(importedClasses)

    private fun pkg(name: String) = "$root.$name"
}