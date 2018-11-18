package yuyuko.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.relics.AbstractRelic
import yuyuko.actions.ChangeCardsInHandAction
import yuyuko.cards.isButterfly
import yuyuko.cards.yuyuko.Butterfly
import yuyuko.cards.yuyuko.ButterflyDeepRooted
import yuyuko.cards.yuyuko.ButterflyDelusion
import yuyuko.cards.yuyuko.ButterflyGhost
import yuyuko.cards.yuyuko.ButterflySwallowtail
import yuyuko.event.EventDispenser
import yuyuko.event.OnDrawEvent
import kotlin.math.ceil


class BlueKimonoButterfly : CustomRelic(
        ID,
        ImageMaster.loadImage(IMAGE_PATH),
        RelicTier.SPECIAL,
        LandingSound.MAGICAL
) {
    override fun makeCopy(): AbstractRelic = BlueKimonoButterfly()

    companion object {
        @JvmStatic
        val ID = "Blue Kimono (Butterfly)"
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
        applyTransformButterfly()
    }

    override fun atBattleStart() {
        applyTransformButterfly()
    }

    private fun applyTransformButterfly() {
        EventDispenser.subscribe<OnDrawEvent>(OnDrawEvent.ID) {
            if (card.cardID == Butterfly.ID) {
                val toChange = listOf(
                        ::ButterflyGhost,
                        ::ButterflyDeepRooted,
                        ::ButterflySwallowtail,
                        ::ButterflyDelusion
                )

                AbstractDungeon.actionManager.addToBottom(
                        ChangeCardsInHandAction({ card == it }, toChange)
                )
            }
        }
    }

    override fun onPlayCard(c: AbstractCard?, m: AbstractMonster?) {
        if (!c!!.isButterfly()) {
            return
        }

        val heal = ceil(AbstractDungeon.player.maxHealth * 0.03f).toInt()
        AbstractDungeon.actionManager.addToBottom(
                HealAction(AbstractDungeon.player, AbstractDungeon.player, heal)
        )
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

}