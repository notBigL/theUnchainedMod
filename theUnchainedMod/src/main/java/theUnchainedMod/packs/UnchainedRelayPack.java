package theUnchainedMod.packs;

import theUnchainedMod.cards.*;

import java.util.ArrayList;

import static theUnchainedMod.TheUnchainedMod.makeID;

public class UnchainedRelayPack extends AbstractUnchainedPack {
    public static final String ID = makeID("RelayPack");

    public UnchainedRelayPack() {
        super(ID, Cocoon.ID, new PackSummary(3, 4, 1, 3, 3));
    }

    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add(Cocoon.ID);
        cards.add(DualBarrier.ID);
        cards.add(ArcaneStrike.ID);
        cards.add(RecklessSwing.ID);

        cards.add(SharePain.ID);
        cards.add(MasterworkGlyph.ID);
        cards.add(EyeForAnEye.ID);
        cards.add(Refresh.ID);

        cards.add(GlyphBeam.ID);
        cards.add(ArcaneCharm.ID);
        return cards;
    }
}