package demo.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum
import com.megacrit.cardcrawl.helpers.CardLibrary

class LibraryTypeEnum {
    companion object {
        @SpireEnum
        @JvmField
        var REIMU_COLOR: CardLibrary.LibraryType? = null
        @SpireEnum
        @JvmField
        var YUYUKO_COLOR: CardLibrary.LibraryType? = null
    }
}