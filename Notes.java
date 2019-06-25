public enum Notes {
    DO(60),
    FA(65),
    INVALID_NOTE(0),
    LA(69),
    MI(64),
    RE(62),
    SI(71),
    SOL(67);

    private final int note;
    Notes(int note){ this.note = note; }

    public int Note(){
        return this.note;
    }
}