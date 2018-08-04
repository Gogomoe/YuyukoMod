package yuyuko.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.helpers.input.InputHelper
import com.megacrit.cardcrawl.powers.ArtifactPower
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower
import com.megacrit.cardcrawl.relics.AbstractRelic


class Coronal : CustomRelic(
        ID,
        ImageMaster.loadImage(IMAGE_PATH),
        RelicTier.STARTER,
        LandingSound.MAGICAL
) {
    override fun makeCopy(): AbstractRelic = Coronal()

    companion object {
        @JvmStatic
        val ID = "Coronal"
        val IMAGE_PATH = "images/relics/coronal.png"
    }

    init {
        this.counter = 5
    }

    override fun onVictory() {
        if (counter < 5) {
            this.counter++
            if (counter == 5) {
                this.flash()
            }
        }
    }

    fun onRightClick() {
        if (this.counter < 5) {
            return
        }
        this.counter = 0
        val player = AbstractDungeon.player
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        player, player,
                        IntangiblePlayerPower(player, 1),
                        1
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        player, player,
                        ArtifactPower(player, 1),
                        1
                )
        )
        this.flash()
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

    private var RclickStart = false
    private var Rclick = false

    override fun update() {
        super.update()
        if (!this.isObtained) {
            return
        }

        if (this.RclickStart && InputHelper.justReleasedClickRight) {
            if (this.hb.hovered) {
                this.Rclick = true
            }

            this.RclickStart = false
        }

        if (this.hb != null && this.hb.hovered && InputHelper.justClickedRight) {
            this.RclickStart = true
        }

        if (this.Rclick) {
            this.Rclick = false
            this.onRightClick()
        }

    }


}