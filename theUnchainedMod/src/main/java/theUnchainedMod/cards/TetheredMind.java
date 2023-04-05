package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.GainRelayAction;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.TiedToAnEnemyPower;
import theUnchainedMod.powers.TiedToThePlayerPower;
import theUnchainedMod.vfx.TetheredMindEffect;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.DefaultMod.makeCardPath;

public class TetheredMind extends AbstractDynamicRelayCard {

    public static final String ID = DefaultMod.makeID(TetheredMind.class.getSimpleName());
    public static final String IMG = makeCardPath("TetheredMind.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_ORANGE;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 10;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 4;

    public TetheredMind() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainRelayAction(p, magicNumber));
        if (m.hasPower(ArtifactPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 1, false)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new TetheredMindEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY)));
            TiedToThePlayerPower tiedToThePlayerPower = new TiedToThePlayerPower(m, p, p, 1);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, tiedToThePlayerPower));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TiedToAnEnemyPower(p, p, tiedToThePlayerPower, m)));
        }
    }
}
