package demo.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect
import demo.relics.Yuyukosfan

@SpirePatch(
        cls = "com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect",
        method = "update"
)
class CampfireSleepEffectUpdatePatch {

    companion object {
        @JvmStatic
        @SpireInsertPatch(
                rloc = 12
        )
        fun Insert(instance: CampfireSleepEffect) {
            val fan = AbstractDungeon.player.getRelic(Yuyukosfan.ID) as Yuyukosfan?
            if (fan != null) {
                fan.fanAmount += 1
                fan.updateDescription()
                fan.flash()
            }
        }
    }
}