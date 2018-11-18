package yuyuko.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_VERTICAL
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.relics.AbstractRelic
import yuyuko.actions.ChangeCardsInHandAction
import yuyuko.cards.isSakura
import yuyuko.cards.yuyuko.*
import yuyuko.event.EventDispenser
import yuyuko.event.OnDrawEvent
import kotlin.math.ceil


class BlueKimonoSakura : CustomRelic(
        ID,
        ImageMaster.loadImage(IMAGE_PATH),
        RelicTier.SPECIAL,
        LandingSound.MAGICAL
) {
    override fun makeCopy(): AbstractRelic = BlueKimonoSakura()

    companion object {
        @JvmStatic
        val ID = "Blue Kimono (Sakura)"
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
        applyTransformSakura()

    }

    override fun atBattleStart() {
        applyTransformSakura()
    }

    private fun applyTransformSakura() {
        EventDispenser.subscribe<OnDrawEvent>(OnDrawEvent.ID) {
            if (card.cardID == Sakura.ID) {
                val toChange = listOf(
                        ::SakuraSeal,
                        ::SakuraBloom,
                        ::SakuraDormancy,
                        ::SakuraSuicide
                )
                AbstractDungeon.actionManager.addToBottom(
                        ChangeCardsInHandAction({ card == it }, toChange)
                )
            }
        }
    }

    override fun onPlayCard(c: AbstractCard?, m: AbstractMonster?) {
        if (!c!!.isSakura()) {
            return
        }

        val monsters = AbstractDungeon.getCurrRoom().monsters.monsters
        val damage = IntArray(monsters.size)
        monsters.forEachIndexed { i, it ->
            damage[i] = ceil(it.maxHealth * 0.03f).toInt()
        }
        AbstractDungeon.actionManager.addToBottom(
                DamageAllEnemiesAction(
                        AbstractDungeon.player, damage, HP_LOSS, SLASH_VERTICAL
                )
        )
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

}