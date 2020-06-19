package yuyuko.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import yuyuko.event.EventDispenser
import yuyuko.event.OnDrawEvent

@SpirePatch(
        clz = com.megacrit.cardcrawl.characters.AbstractPlayer::class,
        method = "draw",
        paramtypez = [Int::class]
)
class OnDrawEventPatch {

    companion object {

        @JvmStatic
        @SpireInsertPatch(
                rloc = 14,
                localvars = ["c"]
        )
        fun Insert(instance: AbstractPlayer, numCards: Int, c: AbstractCard) {
            EventDispenser.emit(OnDrawEvent(c))
        }
    }
}