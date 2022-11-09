package theUnchainedMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.DefaultMod.makeRelicOutlinePath;
import static theUnchainedMod.DefaultMod.makeRelicPath;

public class Memento extends CustomRelic {
    public static final String ID = DefaultMod.makeID("Memento");


    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Memento_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Memento_relic.png"));

    public Memento() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void onPlayerEndTurn() {
        int playerArmor = AbstractDungeon.player.currentBlock;
        int enemyArmor = 0;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            enemyArmor += mo.currentBlock;
        }
        if (playerArmor <= enemyArmor) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1)));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
