package yuyuko.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.MonsterGroup
import yuyuko.powers.DizzinessPower

@SpirePatch(
        cls = "com.megacrit.cardcrawl.monsters.MonsterGroup",
        method = "queueMonsters"
)
class MonsterGroupQueuePatch {

    companion object {
        @JvmStatic
        fun Postfix(instance: MonsterGroup) {
            if (AbstractDungeon.player.hasPower(DizzinessPower.POWER_ID)) {
                AbstractDungeon.actionManager.monsterQueue.clear()
            }
        }
    }
}