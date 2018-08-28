package yuyuko.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import yuyuko.powers.TicketToHeavenPower;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.powers.ConstrictedPower",
        method = "atEndOfTurn"
)
public class ConstrictedPowerPatch {

    public static void Replace(ConstrictedPower instance, final boolean isPlayer) {
        instance.flash();
        AbstractPower power = instance.owner.getPower(TicketToHeavenPower.Companion.getPOWER_ID());

        int amount = power == null ? 0 : power.amount;
        int damage = instance.amount * (1 + amount);
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(instance.owner,
                        new DamageInfo(instance.source, damage, DamageInfo.DamageType.THORNS)
                )
        );
    }

}
