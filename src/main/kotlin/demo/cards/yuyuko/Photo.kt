package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.patches.CardColorEnum

class Photo(val hp: Int = 0) : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.STATUS, CardColorEnum.YUYUKO_COLOR,
        CardRarity.SPECIAL, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Photo"
        val IMAGE_PATH = "images/yuyuko/cards/photo.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.exhaust = true
        this.magicNumber = hp
        this.baseMagicNumber = hp
    }

    override fun makeCopy(): AbstractCard = Photo(hp)

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean =
            this.cardPlayable(m) && this.hasEnoughEnergy()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.player.currentHealth = hp
        AbstractDungeon.player.healthBarUpdatedEvent()
    }

    override fun canUpgrade(): Boolean = false

    override fun upgrade() {}

}