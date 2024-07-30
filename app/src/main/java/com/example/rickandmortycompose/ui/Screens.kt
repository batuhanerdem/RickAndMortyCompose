import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
sealed class Screens {
    @Serializable
    @SerialName("Location")
    data object Location : Screens()

    @Serializable
    @SerialName("Character")
    data object Character : Screens()

    @Serializable
    @SerialName("Episode")
    data object Episode : Screens()

    @Serializable
    @SerialName("CharacterDetails")
    data class CharacterDetails(val characterId: String) : Screens()
}


val screensModule = SerializersModule {
    polymorphic(Screens::class) {
        subclass(Screens.Location::class)
        subclass(Screens.Character::class)
        subclass(Screens.Episode::class)
        subclass(Screens.CharacterDetails::class)
    }
}

val json = Json {
    serializersModule = screensModule
    classDiscriminator = "type" // You can specify a custom discriminator key if needed
}