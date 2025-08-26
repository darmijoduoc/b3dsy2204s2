package cl.duoc.darmijo.data

import at.favre.lib.crypto.bcrypt.BCrypt
import io.viascom.nanoid.NanoId

object Usuarios {
    private val usuarios = mutableListOf<Usuario>(
        Usuario(
            nombre = "Juan Perez",
            rut = "12.345.678-9",
            nroDocumento = "12345678",
            email = "jp@duocuc.cl",
            hashedPassword = BCrypt.withDefaults().hashToString(12, "Password1@".toCharArray())
        ),
        Usuario(
            nombre = "Maria Gomez",
            rut = "98.765.432-1",
            nroDocumento = "87654321",
            email = "mg@duocuc.cl",
            hashedPassword = BCrypt.withDefaults().hashToString(12, "Password1@".toCharArray())
        ),
        Usuario(
            nombre = "Pedro Rodriguez",
            rut = "11.223.334-5",
            nroDocumento = "11223344",
            email = "pr@duocuc.cl",
            hashedPassword = BCrypt.withDefaults().hashToString(12, "Password1@".toCharArray())
        ),
        Usuario(
            nombre = "Ana Martinez",
            rut = "22.334.445-6",
            nroDocumento = "22334455",
            email = "am@duocuc.cl",
            hashedPassword = BCrypt.withDefaults().hashToString(12, "Password1@".toCharArray())
        ),
        Usuario(
            nombre = "Luis Fernandez",
            rut = "33.445.556-7",
            nroDocumento = "33445566",
            email = "lf@duocuc.cl",
            hashedPassword = BCrypt.withDefaults().hashToString(12, "Password1@".toCharArray())
        )
    )

    fun getRandomUsuario(): Usuario {
        return usuarios.random()
    }

    fun getUsuarioByUid(uid: String): Usuario {
        return usuarios.find { it.uid == uid } ?: throw Exception("Usuario no encontrado")
    }

    fun tryRegistro(nombre: String, rut: String, nroDocumento: String, email: String, plainPassword: String) {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.+)(\\.)(.+)"
        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{6,}\$"

        if (nombre.isBlank()) throw Exception("Nombre no puede estar vacío")
        if (rut.isBlank()) throw Exception("RUT no puede estar vacío")
        if (nroDocumento.isBlank()) throw Exception("Número de documento no puede estar vacío")
        if (email.isBlank()) throw Exception("Email no puede estar vacío")
        if (!email.matches(emailRegex.toRegex())) throw Exception("Email no es válido")
        if (getUsuarioByEmail(email) != null) throw Exception("Usuario email ya existe")
        if (plainPassword.length < 6) throw Exception("Password debe tener al menos 6 caracteres")
        if (!plainPassword.matches(passwordRegex.toRegex())) throw Exception("Password no es válido")


        val hashedPassword = BCrypt.withDefaults().hashToString(
            12,
            plainPassword.toCharArray()
        )


        usuarios.add(
            Usuario(
                nombre = nombre,
                rut = rut,
                nroDocumento = nroDocumento,
                email = email,
                hashedPassword = hashedPassword
            )
        )
    }

    fun tryRecuperaPassword(rut: String, nroDocumento: String, plainPassword: String, plainConfirmPassword: String) {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.+)(\\.)(.+)"
        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{6,}\$"

        if (rut.isBlank()) throw Exception("RUT no puede estar vacío")
        if (nroDocumento.isBlank()) throw Exception("Número de documento no puede estar vacío")
        if(plainPassword != plainConfirmPassword) throw Exception("Las contraseñas no coinciden")
        if (plainPassword.length < 6) throw Exception("Password debe tener al menos 6 caracteres")
        if (!plainPassword.matches(passwordRegex.toRegex())) throw Exception("Password no es válido")

        val usuario = getUsuarioByRut(rut) ?: throw Exception("Usuario no encontrado")

        if (usuario.nroDocumento != nroDocumento) throw Exception("Número de documento no coincide")

        usuarios.removeIf {
            it.rut == rut
        }

        val hashedPassword = BCrypt.withDefaults().hashToString(
            12,
            plainPassword.toCharArray()
        )


        usuarios.add(
            Usuario(
                nombre = usuario.nombre,
                rut = rut,
                nroDocumento = nroDocumento,
                email = usuario.email,
                hashedPassword = hashedPassword
            )
        )
    }

    private fun getUsuarioByEmail(email: String): Usuario? {
        return usuarios.find { it.email == email }
    }

    private fun getUsuarioByRut(rut: String): Usuario? {
        return usuarios.find { it.rut == rut }
    }

    fun tryLoginWithEmailAndPlainPassword(email: String, plainPassword: String): Usuario {
        val usuario = getUsuarioByEmail(email)
        if (usuario == null) {
            throw Exception("Usuario no encontrado")
        }
        val result = BCrypt.verifyer().verify(
            plainPassword.toCharArray(),
            usuario.hashedPassword
        )

        if (!result.verified) {
            throw Exception("La contraseña no es válida");
        }

        return usuario;
    }
}
