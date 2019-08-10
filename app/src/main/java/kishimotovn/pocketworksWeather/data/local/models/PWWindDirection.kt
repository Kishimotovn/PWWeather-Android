package kishimotovn.pocketworksWeather.data.local.models

enum class PWWindDirection {
    n,
    nne,
    ne,
    ene,
    e,
    ese,
    se,
    sse,
    s,
    ssw,
    sw,
    wsw,
    w,
    wnw,
    nw,
    nnw;

    companion object {
        fun from(direction: Double): PWWindDirection {
            val index = ((direction + 11.25).rem(360) / 22.5).toInt()
            return values()[index]
        }
    }
}
