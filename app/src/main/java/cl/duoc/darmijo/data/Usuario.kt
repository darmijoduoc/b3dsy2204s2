package cl.duoc.darmijo.data

import io.viascom.nanoid.NanoId

data class Usuario(
    val uid : String = NanoId.generate(),
    val nombre: String,
    val email: String,
    val rut: String,
    val nroDocumento: String,
    val hashedPassword: String,
)