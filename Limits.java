public enum Limits{
    VOLUME_INFERIOR_LIMIT(0),
    VOLUME_SUPERIOR_LIMIT(127),
    OCTAVE_INFERIOR_LIMIT(-24),
    OCTAVE_SUPERIOR_LIMIT(24),
    OCTAVE_OFFSET(12),
    INSTRUMENT_SUPERIOR_LIMIT(126);

    private final int value;

    Limits(int value){
        this.value = value;
    }

    public int Value(){
        return this.value;
    }
}