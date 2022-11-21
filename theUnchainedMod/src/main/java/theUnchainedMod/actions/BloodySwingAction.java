package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.patches.RelayHelpers;
import theUnchainedMod.powers.NextTurnRelayedDamagePower;
import theUnchainedMod.powers.RelayedDamagePower;

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
        if (RelayHelpers.thisTurnRelayedDamage.get(player) > amount) {
            RelayHelpers.loseThisTurnRelayedDamage(amount, false, player);
            damage = this.amount;
            restAmount = 0;
        } else {
            damage = RelayHelpers.thisTurnRelayedDamage.get(player);
            restAmount -= RelayHelpers.thisTurnRelayedDamage.get(player);
            RelayHelpers.loseThisTurnRelayedDamage(false, player);
        }

        if (restAmount > 0) {
            if (RelayHelpers.nextTurnRelayedDamage.get(player) > restAmount) {
                RelayHelpers.loseNextTurnRelayedDamage(restAmount, false, player);
                damage = this.amount;
            } else {
                damage += RelayHelpers.nextTurnRelayedDamage.get(player);
                RelayHelpers.loseNextTurnRelayedDamage(false, player);
            }
        }

        if (damage > 0) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL), AttackEffect.FIRE));
        }
        this.isDone = true;
    }
}
