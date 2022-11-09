package theUnchainedMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.DefaultMod.makeRelicOutlinePath;
import static theUnchainedMod.DefaultMod.makeRelicPath;

public class Churros extends CustomRelic {

    public static final String ID = DefaultMod.makeID("Churros");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Churros_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Churros_relic.png"));

    private boolean eaten = false;

    public Churros() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }


    @Override
    public void atBattleStart() {
        eaten = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.tags.contains(CustomTags.CHAIN) && !this.eaten) {
            this.eaten = true;
            this.flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }

            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractCard tmp = card.makeSameInstanceOf();
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            tmp.applyPowers();
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            this.pulse = false;
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
