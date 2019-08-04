package models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class Organization {
    @NotNull
    private
    String name;
    private int inn;

    @NotNull
    public String getName() {
        return name;
    }

    public int getInn() {
        return inn;
    }

    public int getCheking() {
        return cheking;
    }

    private int cheking;

    @Override
    public boolean equals(@Nullable Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass( ) != o.getClass( ) ) return false;
        Organization that = (Organization) o;
        return inn == that.inn &&
                cheking == that.cheking &&
                name.equals( that.name );
    }

    @Override
    public int hashCode() {
        return Objects.hash( name, inn, cheking );
    }

    public Organization(@NotNull String name, int inn, int checking) {
        this.name = name;
        this.inn = inn;
        this.cheking = checking;
    }
}
