package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class SolarPlexusSoundAction extends AbstractGameAction {

    @Override
    public void update() {
        CardCrawlGame.sound.play("normalChainAttack");
        isDone = true;
    }
}
