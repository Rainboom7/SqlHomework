package models;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public final class WaybillPosition {
    @NotNull
    private
    Waybill waybill;
    private int price;
    @NotNull
    private
    String nomenclature;
    private int quantity;

    @Override
    public boolean equals(@NotNull Object o) {
        if ( this == o ) return true;
        if ( getClass( ) != o.getClass( ) ) return false;
        WaybillPosition that = (WaybillPosition) o;
        return price == that.price &&
                quantity == that.quantity &&
                waybill.equals( that.waybill ) &&
                nomenclature.equals( that.nomenclature );
    }

    @Override
    public int hashCode() {
        return Objects.hash( waybill, price, nomenclature, quantity );
    }

    public WaybillPosition(@NotNull Waybill waybill, int price, @NotNull String nomenclature, int quantity) {
        this.waybill = waybill;
        this.price = price;
        this.nomenclature = nomenclature;
        this.quantity = quantity;
    }

    @NotNull
    public Waybill getWaybill() {
        return waybill;
    }

    public void setWaybill(@NotNull Waybill waybill) {
        this.waybill = waybill;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @NotNull
    public String  getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(@NotNull String nomenclature) {
        this.nomenclature = nomenclature;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
