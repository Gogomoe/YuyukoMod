package yuyuko.potions

import basemod.abstracts.CustomPotion
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.powers.WeakPower
import com.megacrit.cardcrawl.rooms.AbstractRoom


class TurbidWinePotion : CustomPotion(NAME, ID,
        PotionRarity.UNCOMMON, PotionSize.BOTTLE, PotionColor.GREEN) {
    companion object {
        @JvmStatic
        val ID = "Turbid Wine"
        val NAME: String
        val DESCRIPTIONS: Array<String>

        init {
            val strings = CardCrawlGame.languagePack.getPotionString(ID)
            NAME = strings.NAME
            DESCRIPTIONS = strings.DESCRIPTIONS
        }
    }

    init {
        this.potency = this.getPotency()
        this.description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1] + potency + DESCRIPTIONS[2]
        this.isThrown = false
        this.tips.add(PowerTip(this.name, this.description))
    }

    override fun use(target: AbstractCreature?) {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return
        }
        val player = AbstractDungeon.player

        val damage = IntArray(AbstractDungeon.getCurrRoom().monsters.monsters.size)
        AbstractDungeon.getCurrRoom().monsters.monsters.forEachIndexed { i, m ->
            damage[i] = 1
        }
        AbstractDungeon.actionManager.addToBottom(
                DamageAllEnemiesAction(
                        player, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE
                )
        )
        AbstractDungeon.getCurrRoom().monsters.monsters
                .filter { !it.isDeadOrEscaped }
                .forEach {
                    AbstractDungeon.actionManager.addToBottom(
                            ApplyPowerAction(
                                    it, player,
                                    WeakPower(it, this.potency, false),
                                    this.potency
                            )
                    )
                }
        AbstractDungeon.getCurrRoom().addPotionToRewards(this.makeCopy())

    }

    override fun getPotency(p0: Int): Int {
        return 2
    }

    override fun makeCopy(): AbstractPotion = TurbidWinePotion()

}