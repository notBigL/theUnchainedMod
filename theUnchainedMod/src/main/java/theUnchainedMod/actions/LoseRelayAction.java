package theUnchainedMod.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.patches.RelayHelpers;
import theUnchainedMod.powers.GlyphBrandPower;

public class LoseRelayAction extends AbstractGameAction {

    public LoseRelayAction(AbstractCreature target) {
        this.target = target;
    }

    @Override
    public void update() {
        if (!target.isDying && !target.isDead) {

            RelayHelpers.loseRelay(false, target);
        }
        isDone = true;
    }
}
