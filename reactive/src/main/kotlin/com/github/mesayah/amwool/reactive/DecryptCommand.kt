package com.github.mesayah.amwool.reactive

import reactor.core.publisher.Flux
import reactor.util.Loggers
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

const val DECRYPT_COMMAND = "decrypt"

object DecryptCommand : CryptCommand(
    help = "Decrypt a given file with $DEFAULT_ALGORITHM algorithm",
    name = DECRYPT_COMMAND
) {

    private val logger = Loggers.getLogger(DecryptCommand.javaClass)

    private fun decrypt(file: File, secret: String, algorithm: String) = with(Cipher.getInstance(algorithm)) {
        init(Cipher.DECRYPT_MODE, SecretKeySpec(secret.toByteArray(), algorithm.split("/").firstOrNull()))
        doFinal(file.readBytes())
    }

    override fun run() {
        Flux.just(Triple(file, secret, algorithm))
            .doOnNext { logger.info("Loaded file ${file.path}.") }
            .map { (file, secret, algorithm) -> file to decrypt(file, secret, algorithm) }
            .doOnNext { logger.info("Decrypted file ${file.name} with secret key \"$secret\".") }
            .map { (originalFile, encryptedBytes) ->
                val encryptedFile = Files.createFile(Paths.get("${originalFile.path}.decrypted"))
                Files.write(Paths.get(encryptedFile.toUri()), encryptedBytes)
            }
            .doOnNext { logger.info("Saved file to $it.") }
            .subscribe(
                { println("Decrypted and saved to file ${it.toAbsolutePath()}.") },
                { logger.error(it.localizedMessage, it) }
            )
    }
}