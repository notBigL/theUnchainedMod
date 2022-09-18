package theUnchainedMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.cards.Swirl;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.DefaultMod.makeRelicOutlinePath;
import static theUnchainedMod.DefaultMod.makeRelicPath;

public class BalletShoes extends CustomRelic {

    public static final String ID = DefaultMod.makeID("BalletShoes");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BalletShoes_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BalletShoes_relic.png"));

    public BalletShoes() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStartPreDraw() {
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new MakeTempCardInHandAction(new Swirl(), 1, false));
        this.flash();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
