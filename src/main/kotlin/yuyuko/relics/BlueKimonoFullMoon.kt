package yuyuko.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic
import yuyuko.powers.DiaphaneityPower


class BlueKimonoFullMoon : CustomRelic(
        ID,
        ImageMaster.loadImage(IMAGE_PATH),
        RelicTier.SPECIAL,
        LandingSound.MAGICAL
) {
    override fun makeCopy(): AbstractRelic = BlueKimonoFullMoon()

    companion object {
        @JvmStatic
        val ID = "Blue Kimono (FullMoon)"
        val IMAGE_PATH = "images/relics/blueKimono.png"
        val NAME: String
        val DESCRIPTION: String

        init {
            val strings = CardCrawlGame.languagePack.getRelicStrings(ID)
            NAME = strings.NAME
            DESCRIPTION = strings.DESCRIPTIONS[0]
        }
    }

    override fun obtain() {
        if (AbstractDungeon.player.hasRelic(BlueKimono.ID)) {
            instantObtain(
                    AbstractDungeon.player,
                    AbstractDungeon.player.relics.indexOfFirst { it.relicId == BlueKimono.ID },
                    false
            )
        } else {
            super.obtain()
        }

    }

    override fun atTurnStart() {
        this.flash()
        AbstractDungeon.getCurrRoom().monsters.monsters
                .filter { !it.isDeadOrEscaped }
                .forEach {
                    val amount = it.getPower(DiaphaneityPower.POWER_ID)?.amount ?: 0
                    val damage = amount / 5
                    if (damage < 1) {
                        return@forEach
                    }
                    AbstractDungeon.actionManager.addToBottom(
                            DamageAction(
                                    it, DamageInfo(AbstractDungeon.player, damage, HP_LOSS)
                            )
                    )
                }
    }

    override fun onPlayerEndTurn() {
        this.flash()
        val amount = AbstractDungeon.player.getPower(DiaphaneityPower.POWER_ID)?.amount ?: 0
        val heal = amount / 5;
        if (heal < 1) {
            return
        }
        AbstractDungeon.actionManager.addToBottom(
                GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, heal)
        )
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

}