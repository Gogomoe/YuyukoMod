package demo.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.HAND
import demo.cards.triggerOnDiscard

@SpirePatch(
        cls = "com.megacrit.cardcrawl.cards.CardGroup",
        method = "moveToDiscardPile"
)
class TriggerOnDiscardPatch {

    companion object {
        @JvmStatic
        fun Prefix(instance: CardGroup, c: AbstractCard) {
            if (instance.type == HAND) {
                c.triggerOnDiscard()
            }
        }
    }
}