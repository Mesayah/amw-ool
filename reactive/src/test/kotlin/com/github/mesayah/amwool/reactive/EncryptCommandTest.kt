package com.github.mesayah.amwool.reactive

import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths

class EncryptCommandTest {
    @Test
    fun shouldEncryptedFileExistAfterEncrypting() {
        val file = createTempFile().apply { writeText("TEST") }

        EncryptCommand.main(listOf(ENCRYPT_COMMAND, file.path, "password"))

        assert(Files.exists(Paths.get("${file.path}.encrypted")))
    }

    @Test
    fun shouldEncryptedFileContainProperlyEncryptedContent() {
        val data = "TEST string To EnCoDe"
        val encryptedData = ""
        val file = createTempFile().apply { writeText(data) }

        EncryptCommand.main(listOf("-s", "secret", "-a", "DES/CBC/NoPadding", file.path))

        println(String(Files.readAllBytes(Paths.get("${file.path}.encrypted"))))
        assert(String(Files.readAllBytes(Paths.get("${file.path}.encrypted"))) == encryptedData)
    }
}