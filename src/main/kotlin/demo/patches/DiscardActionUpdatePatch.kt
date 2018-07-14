package demo.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.DiscardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import demo.cards.triggerOnDiscard

@SpirePatch(
        cls = "com.megacrit.cardcrawl.actions.common.DiscardAction",
        method = "update"
)
class DiscardActionUpdatePatch {

    companion object {
        @JvmStatic
        @SpireInsertPatch(
                rloc = 15,
                localvars = ["c", "endTurn"]
        )
        fun Insert(instance: DiscardAction, c: AbstractCard, endTurn: Boolean) {
            /**
             * @see triggerOnDiscard in cards.CardProperty
             */
            c.triggerOnDiscard(endTurn)
        }
    }
}