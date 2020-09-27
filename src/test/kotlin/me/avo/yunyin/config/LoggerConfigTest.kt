package me.avo.yunyin.config

import me.avo.yunyin.service.TestService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.get
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class LoggerConfigTest(
    @Autowired private val testService: TestService
) {
    private val outContent = ByteArrayOutputStream()
    private val originalOut = System.out

    @BeforeEach fun beforeEach() {
        System.setOut(PrintStream(outContent))
    }

    @AfterEach fun afterEach() {
        System.setOut(originalOut)
        println(outContent)
        outContent.reset()
    }

    @Test fun `logger output`() {
        // Given
        val x = 6
        val y = 7
        val expectedReturnValue = 42
        val expectedEntering = "Entering testMultiply($x,$y)"
        val expectedLeaving = "Leaving testMultiply(), returned $expectedReturnValue, running time = "

        // When
        testService.testMultiply(x, y)

        // Then
        expectThat(getLines()) {
            get(0).contains(expectedEntering)
            get(1).contains(expectedLeaving)
        }
    }

    @Test fun `logger output exception`() {
        // Given
        val expectedContent = "Error throwException(), exception java.lang.IllegalStateException: Expected, running time = "

        // When
        try {
            testService.throwException()
        } catch (ex: IllegalStateException) {

        }

        // Then
        expectThat(getLines()) {
            get(1).contains(expectedContent)
        }
    }

    private fun getLines() = outContent.toString()
        .split('\n')
        .filter { it.contains(':') }
        .map { it.substringAfter(':').trim() }
}