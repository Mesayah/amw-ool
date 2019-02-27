package com.github.mesayah.amwool.reactive

import reactor.core.publisher.Flux
import reactor.util.Loggers
import java.io.File
import javax.crypto.Cipher

const val ENCRYPT_COMMAND = "encrypt"

object EncryptCommand : CryptCommand(
    help = "Encrypt a given file with $DEFAULT_ALGORITHM algorithm",
    name = ENCRYPT_COMMAND
) {

    private val logger = Loggers.getLogger(EncryptCommand.javaClass)

    private fun encrypt(file: File, secret: String, algorithm: String): ByteArray =
        with(Cipher.getInstance(algorithm)) {
            // TODO()
            return ByteArray(0)
        }

    override fun run() {
        Flux.just(Triple(file, secret, algorithm))
            .doOnNext { logger.info("Loaded file ${file.path}.") }
            .map { (file, secret, algorithm) -> file to encrypt(file, secret, algorithm) }
            .doOnNext { logger.info("Encrypted file ${file.name} with secret key \"${secret}\".") }
            .map { (originalFile, encryptedBytes) ->
                File(originalFile.path.plus(".encrypted")).apply { writeBytes(encryptedBytes) }
            }
            .doOnNext { logger.info("Saved file to ${it.path}.") }
            .subscribe(
                { println("Encrypted and saved to file ${it.path}.") },
                { logger.error(it.localizedMessage, it) }
            )
    }
}
