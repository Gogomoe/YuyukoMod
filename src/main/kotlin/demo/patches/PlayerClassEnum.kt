package demo.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum
import com.megacrit.cardcrawl.characters.AbstractPlayer

class PlayerClassEnum {
    companion object {
//        @SpireEnum
//        @JvmField
//        var REIMU: AbstractPlayer.PlayerClass? = null
        @SpireEnum
        @JvmField
        var YUYOKO: AbstractPlayer.PlayerClass? = null
    }
}