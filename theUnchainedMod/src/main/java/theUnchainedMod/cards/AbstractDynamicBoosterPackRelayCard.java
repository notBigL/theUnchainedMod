package theUnchainedMod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.ArcaneMasteryPower;

public abstract class AbstractDynamicBoosterPackRelayCard extends AbstractDynamicRelayCard {

    public AbstractDynamicBoosterPackRelayCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);

        tags.add(CustomTags.RELAY);
    }
    @Override
    public void applyPowers() {
        super.applyPowers();
        this.isMagicNumberModified = false;
        AbstractPlayer player = AbstractDungeon.player;
        float tmp = (float)this.baseMagicNumber;

        if(player != null && player.hasPower(ArcaneMasteryPower.POWER_ID))
        {
            tmp += AbstractDungeon.player.getPower(ArcaneMasteryPower.POWER_ID).amount;
        }

        if (this.baseMagicNumber != MathUtils.floor(tmp)) {
            this.isMagicNumberModified = true;}

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        this.magicNumber = MathUtils.floor(tmp);

    }
}
