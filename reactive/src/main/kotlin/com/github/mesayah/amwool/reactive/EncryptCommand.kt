package com.github.mesayah.amwool.reactive

import reactor.core.publisher.Flux
import reactor.util.Loggers
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

const val ENCRYPT_COMMAND = "encrypt"

object EncryptCommand : CryptCommand(
    help = "Encrypt a given file with $DEFAULT_ALGORITHM algorithm",
    name = ENCRYPT_COMMAND
) {

    private val logger = Loggers.getLogger(EncryptCommand.javaClass)

    private fun encrypt(file: File, secret: String, algorithm: String) = with(Cipher.getInstance(algorithm)) {
        // TODO()
    }

    override fun run() {
        Flux.just(Triple(file, secret, algorithm))
            .doOnNext { logger.info("Loaded file ${file.path}.") }
            .map { (file, secret, algorithm) -> file to encrypt(file, secret, algorithm) }
            .doOnNext { logger.info("Encrypted file ${file.name} with secret key \"${secret}\".") }
            .map { (originalFile, encryptedBytes) ->
                val encryptedFile = Files.createFile(Paths.get("${originalFile.path}.Encrypted"))
                Files.write(Paths.get(encryptedFile.toUri()), encryptedBytes)
            }
            .doOnNext { logger.info("Saved file to $it.") }
            .subscribe(
                { println("Encrypted and saved to file ${it.toAbsolutePath()}.") },
                { logger.error(it.localizedMessage, it) }
            )
    }
}
