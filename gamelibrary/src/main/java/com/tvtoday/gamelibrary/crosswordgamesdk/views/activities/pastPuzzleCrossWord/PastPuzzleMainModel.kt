package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord

import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.PastPuzzleInnerModel

data class PastPuzzleMainModel(
    var months: String,

    var list: List<PastPuzzleInnerModel>
)