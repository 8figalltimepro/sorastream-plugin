// use an integer for version numbers
version = 71


cloudstream {
    language = "en"
    // All of these properties are optional, you can safely remove them

     description = "#1 best extention based on MultiAPIjinkipunchi"
     authors = listOf("8", "tora")

    /**
     * Status int as the following:
     * 0: Down
     * 1: Ok
     * 2: Slow
     * 3: Beta only
     * */
    status = 1 // will be 3 if unspecified
    tvTypes = listOf(
        "AsianDrama",
        "TvSeries",
        "Anime",
        "Movie",
    )

    iconUrl = "https://eightfiglive.wpenginepowered.com/wp-content/uploads/2022/03/8fig_logo_darkgreen.svg"
}
