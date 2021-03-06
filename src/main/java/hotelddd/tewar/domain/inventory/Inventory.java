package hotelddd.tewar.domain.inventory;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import hotelddd.tewar.domain.inventory.entities.Food;
import hotelddd.tewar.domain.inventory.entities.Utensil;
import hotelddd.tewar.domain.inventory.events.BroughtBrand;
import hotelddd.tewar.domain.inventory.events.CheckedStock;
import hotelddd.tewar.domain.inventory.events.InventoryCreated;
import hotelddd.tewar.domain.inventory.events.UpdatedInventory;
import hotelddd.tewar.domain.inventory.values.InventoryId;

import java.util.List;

public class Inventory extends AggregateEvent<InventoryId> {

    protected Food food;
    protected Utensil utensil;

    public Inventory(InventoryId entityId, Food food, Utensil utensil) {
        super(entityId);
        appendChange(new InventoryCreated(food,utensil)).apply();
    }

    private Inventory(InventoryId entityId) {
        super(entityId);
        subscribe(new InventoryEventChange(this));
    }

    public static Inventory from(InventoryId entityId, List<DomainEvent> events){
        var recurso = new Inventory(entityId);
        events.forEach(recurso::applyEvent);
        return recurso;
    }


    public void UpdateInventory(InventoryId entityId, Food food, Utensil utensil){
        appendChange(new UpdatedInventory(entityId,food,utensil)).apply();
    }

    public void checkStock(InventoryId entityId, Food food, Utensil utensil){
        appendChange(new CheckedStock(entityId,food,utensil)).apply();
    }

    public void bringBrand(InventoryId entityId,Utensil utensil){
        appendChange(new BroughtBrand(entityId,utensil)).apply();
    }
}
