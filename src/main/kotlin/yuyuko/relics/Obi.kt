package yuyuko.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic
import yuyuko.powers.DiaphaneityPower

class Obi : CustomRelic(
        ID,
        ImageMaster.loadImage(IMAGE_PATH),
        RelicTier.UNCOMMON,
        LandingSound.MAGICAL
) {
    override fun makeCopy(): AbstractRelic = Obi()

    companion object {
        @JvmStatic
        val ID = "Obi"
        val IMAGE_PATH = "images/relics/obi.png"
    }

    override fun atBattleStartPreDraw() {
        val player = AbstractDungeon.player
        val amount = (player.getRelic(Yuyukosfan.ID) as Yuyukosfan?)
                ?.turnsOfLastBattle ?: 0
        if (amount == 0) {
            return
        }
        this.flash()
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        player, player,
                        DiaphaneityPower(player, amount),
                        amount
                )
        )
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

}