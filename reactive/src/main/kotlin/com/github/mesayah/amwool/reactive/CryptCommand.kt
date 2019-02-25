package com.github.mesayah.amwool.reactive

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import javax.crypto.Cipher

abstract class CryptCommand(name: String, help: String) : CliktCommand(help, name) {
    internal val algorithm by option(
        help = "Encryption algorithm to use, possible values:\n" +
                "AES/CBC/NoPadding\n" +
                "AES/CBC/PKCS5Padding\n" +
                "AES/ECB/NoPadding\n" +
                "AES/ECB/PKCS5Padding\n" +
                "DES/CBC/NoPadding\n" +
                "DES/CBC/PKCS5Padding\n" +
                "DES/ECB/NoPadding\n" +
                "DES/ECB/PKCS5Padding\n" +
                "DESede/CBC/NoPadding\n" +
                "DESede/CBC/PKCS5Padding\n" +
                "DESede/ECB/NoPadding\n" +
                "DESede/ECB/PKCS5Padding\n" +
                "RSA/ECB/PKCS1Padding\n" +
                "RSA/ECB/OAEPWithSHA-1AndMGF1Padding\n" +
                "RSA/ECB/OAEPWithSHA-256AndMGF1Padding",
        names = *arrayOf("--algorithm", "-a")
    ).default(DEFAULT_ALGORITHM)

    protected val secret by option(
        help = "Secret key for encryption/decryption",
        names = *arrayOf("--secret", "-s")
    ).required()

    protected val file by argument(help = "File to encrypt/decrypt").file()
}
