package yuyuko.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic
import yuyuko.powers.GhostPower


class GhostLamp : CustomRelic(
        ID,
        ImageMaster.loadImage(IMAGE_PATH),
        RelicTier.COMMON,
        LandingSound.MAGICAL
) {
    override fun makeCopy(): AbstractRelic = GhostLamp()

    companion object {
        @JvmStatic
        val ID = "Ghost Lamp"
        val IMAGE_PATH = "images/relics/ghostLamp.png"
    }

    override fun atTurnStart() {
        val player = AbstractDungeon.player
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        player, player,
                        GhostPower(player, 1),
                        1
                )
        )
        this.flash()
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

}