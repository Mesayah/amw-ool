package com.github.mesayah.amwool.reactive

import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class BulkRenamerTest {
    @Test
    fun shouldRenamedFileExistsAfterRenaming() {
        val file1 = File(javaClass.classLoader.getResource("file1.txt").toURI())
        val file2 = File(javaClass.classLoader.getResource("file2.txt").toURI())
        val file3 = File(javaClass.classLoader.getResource("file3.txt").toURI())
        val prefix = "test_prefix"
        val suffix = "test_suffix"

        RenameCommand.main(
            listOf(
                "--prefix", prefix,
                "--suffix", suffix,
                file1.path, file2.path, file3.path
            )
        )

        listOf(file1, file2, file3).forEach { file ->
            val renamedFile =
                file.parentFile.resolve(prefix + file.nameWithoutExtension + suffix + "." + file.extension)
            assert(Files.exists(renamedFile.toPath()))
            Files.delete(renamedFile.toPath())
        }
    }

    @Test
    fun shouldSaveRenamedFilesToGivenDestination() {
        val file1 = File(javaClass.classLoader.getResource("file1.txt").toURI())
        val file2 = File(javaClass.classLoader.getResource("file2.txt").toURI())
        val file3 = File(javaClass.classLoader.getResource("file3.txt").toURI())
        val prefix = "test_prefix"
        val suffix = "test_suffix"
        val destination = "destination"
        Files.createDirectory(Paths.get(destination))

        RenameCommand.main(
            listOf(
                "--prefix", prefix,
                "--suffix", suffix,
                "--destination", destination,
                file1.path, file2.path, file3.path
            )
        )

        listOf(file1, file2, file3).forEach { file ->
            val renamedFile =
                File(destination).resolve(prefix + file.nameWithoutExtension + suffix + "." + file.extension)
            assert(Files.exists(renamedFile.toPath()))
            Files.delete(renamedFile.toPath())
        }

        Files.delete(Paths.get(destination))
    }
}