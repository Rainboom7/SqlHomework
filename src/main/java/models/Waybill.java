package models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("unused")
public final class Waybill {
    private int id;
    @NotNull
    private
    String date;
    @NotNull
    private
    String organization;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getDate() {
        return date;
    }

    public void setDate(@NotNull String date) {
        this.date = date;
    }

    @NotNull
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(@NotNull String organization) {
        this.organization = organization;
    }

    public Waybill(int id, @NotNull String date, @NotNull String  organization) {
        this.id = id;
        this.date = date;
        this.organization = organization;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass( ) != o.getClass( ) ) return false;
        Waybill waybill = (Waybill) o;
        return id == waybill.id &&
                date.equals( waybill.date ) &&
                organization.equals( waybill.organization );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, date, organization );
    }
}
