package models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("unused")
public final class Nomenclature {
    public Nomenclature(@NotNull String name, int code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass( ) != o.getClass( ) ) return false;
        Nomenclature that = (Nomenclature) o;
        return code == that.code &&
                name.equals( that.name );
    }

    @Override
    public int hashCode() {
        return Objects.hash( name, code );
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @NotNull
    private
    String name;
    private int code;

}
