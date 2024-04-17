package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.patches.RelayHelpers;

public class BloodySwingAction extends AbstractGameAction {

    private final AbstractPlayer player;

    public BloodySwingAction(AbstractPlayer player, AbstractMonster m) {
        this.player = player;
        this.target = m;
    }


    @Override
    public void update() {
        int damage = 0;

        damage += RelayHelpers.thisTurnRelayedDamage.get(player);
        RelayHelpers.loseThisTurnRelayedDamage(false, player);
        damage += RelayHelpers.nextTurnRelayedDamage.get(player);
        RelayHelpers.loseNextTurnRelayedDamage(false, player);

        if (damage > 0) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL), AttackEffect.FIRE));
        }
        this.isDone = true;
    }
}
