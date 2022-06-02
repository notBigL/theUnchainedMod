package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BloodySwingAction extends AbstractGameAction {

    private final AbstractPlayer player;

    public BloodySwingAction(int amountToLoseAndDamage, AbstractPlayer player, AbstractMonster m) {
        this.amount = amountToLoseAndDamage;
        this.player = player;
        this.target = m;
    }


    @Override
    public void update() {
        int damage = 0;
        int restAmount = this.amount;
        if (player.hasPower("theUnchainedMod:RelayedDamagePower")) {
            AbstractPower relayedDamage = player.getPower("theUnchainedMod:RelayedDamagePower");
            if (this.amount < relayedDamage.amount) {
                relayedDamage.reducePower(this.amount);
                damage = this.amount;
                restAmount = 0;
            } else {
                damage = relayedDamage.amount;
                restAmount -= relayedDamage.amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, relayedDamage));
            }
        }
        if (restAmount > 0 && player.hasPower("theUnchainedMod:NextTurnRelayedDamagePower")) {
            AbstractPower nextTurnRelayedDamage = player.getPower("theUnchainedMod:NextTurnRelayedDamagePower");
            if (restAmount < nextTurnRelayedDamage.amount) {
                nextTurnRelayedDamage.reducePower(restAmount);
                damage = this.amount;
            } else {
                damage += nextTurnRelayedDamage.amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, nextTurnRelayedDamage));
            }
        }
        if (damage > 0) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL), AttackEffect.FIRE));
        }
        this.isDone = true;
    }
}
