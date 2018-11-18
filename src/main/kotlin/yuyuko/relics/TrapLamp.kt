package yuyuko.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic
import yuyuko.cards.yuyuko.*
import yuyuko.getRandom


class TrapLamp : CustomRelic(
        ID,
        ImageMaster.loadImage(IMAGE_PATH),
        RelicTier.COMMON,
        LandingSound.MAGICAL
) {
    override fun makeCopy(): AbstractRelic = TrapLamp()

    companion object {
        @JvmStatic
        val ID = "Trap Lamp"
        val IMAGE_PATH = "images/relics/trapLamp.png"
    }

    init {
        this.counter = 1
    }

    override fun atBattleStart() {
        this.counter = 1
    }

    override fun atTurnStart() {
        if (counter == 3) {
            counter = 1
        } else {
            this.counter++
        }
        if (counter == 3) {
            this.flash()
            val toGet = listOf(
                    ::Butterfly,
                    ::ButterflyGhost,
                    ::ButterflyDeepRooted,
                    ::ButterflySwallowtail,
                    ::ButterflyDelusion
            )
            AbstractDungeon.actionManager.addToBottom(
                    MakeTempCardInDrawPileAction(toGet.getRandom()!!.invoke(), 1, true, true)
            )
        }
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

}