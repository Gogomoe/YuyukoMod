package yuyuko.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import yuyuko.applyShufflePowers

@SpirePatch(
        cls = "com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction",
        method = SpirePatch.CONSTRUCTOR
)
class TriggerOnShufflePatch {

    companion object {
        @JvmStatic
        fun Postfix(instance: EmptyDeckShuffleAction) {
            AbstractDungeon.getCurrRoom().monsters.applyShufflePowers()
            AbstractDungeon.player.applyShufflePowers()
        }
    }
}