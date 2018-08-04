package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.event.DegradeEvent
import yuyuko.event.DegradeEvent.DegradeReason.USE
import yuyuko.event.EventDispenser
import yuyuko.patches.CardColorEnum
import kotlin.math.ceil

class TheInkySakura : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.ALL_ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "The Inky Sakura"
        val IMAGE_PATH = "images/yuyuko/cards/attack2.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    override fun makeCopy(): AbstractCard = TheInkySakura()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        val rate = if (this.timesUpgraded >= 30) 1f else 0.05f

        val monsters = AbstractDungeon.getCurrRoom().monsters.monsters
        val damage = IntArray(monsters.size)
        monsters.forEachIndexed { i, m ->
            damage[i] = ceil(m.maxHealth * rate).toInt()
        }

        AbstractDungeon.actionManager.addToBottom(
                DamageAllEnemiesAction(
                        self, damage, HP_LOSS, FIRE
                )
        )
        EventDispenser.emit(DegradeEvent(this, USE, this::degradeToInitiation))
    }

    override fun canUpgrade(): Boolean = true

    override fun upgrade() {
        upgradeName()
    }

    override fun upgradeName() {
        ++this.timesUpgraded
        this.upgraded = true
        this.name = "${Sakura.NAME}+$timesUpgraded"
        this.initializeTitle()
    }

    private fun degradeToInitiation() {
        this.upgraded = false
        this.name = NAME
        this.timesUpgraded = 0
        this.initializeTitle()
    }


}