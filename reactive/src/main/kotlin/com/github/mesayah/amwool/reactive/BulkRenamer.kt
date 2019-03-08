package com.github.mesayah.amwool.reactive

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import reactor.core.publisher.Flux
import java.lang.IllegalArgumentException

fun main(args: Array<String>) = RenameCommand.main(args)

object RenameCommand : CliktCommand(
    help = "Renames provided files adding specified prefix and suffix",
    name = "rename"
) {
    val files by argument("files", "Files to rename").file(exists = true).multiple()
    val prefix by option("--prefix", "-p", help = "Prefix for renamed files").default("")
    val suffix by option("--suffix", "-s", help = "Suffix for renamed files").default("")
    val destination by option("--destination", "-d", help = "Destination directory for renamed files").file()

    override fun run() {
        Flux.fromIterable(files)
            .map { file -> file to prefix + file.nameWithoutExtension + suffix + "." + file.extension }
            .map { (originalFile, renamedFileName) ->
                if (destination != null) {
                    if (!destination!!.exists()) throw IllegalArgumentException("Provided destination does not exists")
                    else if (destination!!.isDirectory) {
                        originalFile to destination!!.resolve(renamedFileName)
                    } else throw IllegalArgumentException("Provided destination is not a directory")
                } else originalFile to originalFile.parentFile.resolve(renamedFileName)
            }
            .map { (originalFile, renamedFile) -> renamedFile.apply { writeBytes(originalFile.readBytes()) } }
            .onErrorContinue { throwable, _ -> println("Error while renaming: ${throwable.localizedMessage}") }
            .subscribe { file ->
                println("Renamed to file ${file.path}")
            }
    }
}