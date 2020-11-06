package woods.datamodel;

public enum WoodTypes {
    ДІЛОВА, ДЕРЕВИНА_ДРОВ_ХВ_П, ДРОВА_ХВ_П, ДРОВА_ТВ_П, СУЧКИ, ХВОРОСТ, НЕЛІКВІД;

    private double volume;

    WoodTypes(double volume) {
        this.volume = volume;
    }

    WoodTypes() {
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
