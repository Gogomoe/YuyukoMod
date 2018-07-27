package yuyuko.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum
import com.megacrit.cardcrawl.cards.AbstractCard


class CardColorEnum {
    companion object {
        @SpireEnum
        @JvmField
        var YUYUKO_COLOR: AbstractCard.CardColor? = null
    }
}