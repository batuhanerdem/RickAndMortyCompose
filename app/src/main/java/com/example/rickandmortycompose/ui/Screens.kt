import kotlinx.serialization.Serializable

@Serializable
sealed class Screens(val isOnBottomBar: Boolean) { //buraya bak

    @Serializable
    data object Location : Screens(true)

    @Serializable
    data object AllCharacters : Screens(true)

    @Serializable
    data object Season : Screens(true)

    @Serializable
    data object Episode : Screens(false)

    @Serializable
    data class CharacterDetails(val characterId: String) : Screens(false)

    @Serializable
    data class CharactersInLocation(val characterIdList: List<String>) : Screens(false)


}