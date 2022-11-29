package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.VargPaheliStartGame

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetCrossWordModel(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("data")
	val data: DataItem? = null,

	@field:SerializedName("status")
	val status: String? = null
)


data class DataItem(
	@field:SerializedName("crossword_play")
	val crosswordPlay: ArrayList<CrosswordPlayItem> = ArrayList(),

	@field:SerializedName("corssword_borad")
	val corsswordBoard: ArrayList<CorsswordBoradItem> = ArrayList(),

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("game_date")
	val gameDate: String? = null,
)

data class CrosswordPlayItem(

	@field:SerializedName("answer")
	val answer: String? = null,

	@field:SerializedName("cell_column")
	val cellColumn: String? = null,

	@field:SerializedName("hint")
	val hint: String? = null,

	@field:SerializedName("number_of_cell")
	val numberOfCell: String? = null,

	@field:SerializedName("hint_type")
	val hintType: String? = null,

	@field:SerializedName("cell_row")
	val cellRow: String? = null,

	 @field:SerializedName("split_across")
     val splitAcross: String? = null,

	@field:SerializedName("split_down")
    val splitDown: String? = null
)

data class CorsswordBoradItem(

	@Expose
    var isWronAnswer :Boolean? = false,

	@Expose
    var value : String? = "",

	@Expose
	var isFreezed : Boolean? = false,

	@Expose
    @field:SerializedName("hint_across")
	val hintAcross: String? = null,

	@Expose
    @field:SerializedName("cell_column")
	val cellColumn: String? = null,

	@Expose
    @field:SerializedName("number_of_cell_down")
	val numberOfCellDown: String? = null,

	@Expose
    @field:SerializedName("number_of_cell_across")
	val numberOfCellAcross: String? = null,

	@Expose
    @field:SerializedName("across_cell_answers")
	val acrossCellAnswers: String? = null,

	@Expose
    @field:SerializedName("hintl_down")
	val hintlDown: String? = null,

	@Expose
    @field:SerializedName("down_cell_answer")
	val downCellAnswer: String? = null,

	@Expose
    @field:SerializedName("cell_info")
	val cellInfo: String? = null,

	@Expose
    @field:SerializedName("cell_row")
	val cellRow: String? = null,

	@Expose
    @field:SerializedName("split_across")
	val splitAcross: String? = null,

	@Expose
    @field:SerializedName("split_down")
	val splitDown: String? = null
)
