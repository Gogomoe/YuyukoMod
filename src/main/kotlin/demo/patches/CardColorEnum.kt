package demo.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum
import com.megacrit.cardcrawl.cards.AbstractCard


class CardColorEnum {
    companion object {
        @SpireEnum
        @JvmField
        var REIMU_COLOR: AbstractCard.CardColor? = null
    }
}