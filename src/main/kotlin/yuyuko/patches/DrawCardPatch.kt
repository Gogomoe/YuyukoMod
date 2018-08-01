package yuyuko.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import yuyuko.powers.FloatOnMoonPower
import kotlin.math.max

@SpirePatch(
        cls = "com.megacrit.cardcrawl.actions.common.DrawCardAction",
        method = SpirePatch.CONSTRUCTOR
)
class DrawCardPatch {

    companion object {
        @JvmStatic
        fun Postfix(instance: DrawCardAction, source: AbstractCreature?, amount: Int, endTurnDraw: Boolean) {
            val count = AbstractDungeon.player.getPower(FloatOnMoonPower.POWER_ID)?.amount ?: 0
            if (endTurnDraw && count != 0) {
                instance.amount = max(instance.amount - count, 0)
            }
        }
    }
}