package com.example.tbcacademy.data.datastore

import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object CredentialsSerializer : Serializer<CredentialsProto> {
    override val defaultValue: CredentialsProto = CredentialsProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CredentialsProto =
        try {
            CredentialsProto.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            defaultValue
        }

    override suspend fun writeTo(t: CredentialsProto, output: OutputStream) = t.writeTo(output)
}
