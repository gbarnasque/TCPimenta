public enum Initial {
    INITIAL_INSTRUMENT_INDEX(0),
    INITIAL_INSTRUMENT(0),
    INITIAL_BPM(120),
    INITIAL_VOLUME(60),
    INITIAL_OCTAVE(0),
    DEFAULT_MIDI_CHANNEL(0),
    INITIAL_TICK(1);

    private final int value;
    Initial(int value){ this.value = value; }

    public int Value(){
        return this.value;
    }
}