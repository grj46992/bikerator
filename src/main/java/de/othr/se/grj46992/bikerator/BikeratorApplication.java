package de.othr.se.grj46992.bikerator;

import de.othr.se.grj46992.bikerator.entity.*;
import de.othr.se.grj46992.bikerator.service.ArticleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class BikeratorApplication implements CommandLineRunner {

	@Autowired
	private ArticleManagementService articleManagementService;


	public static void main(String[] args) {
		SpringApplication.run(BikeratorApplication.class, args);
	}

	// Create Data for DB
	@Override
	public void run(String... args) throws Exception {
		// Create Categories
		Category rennradrahmen = new Category();
		rennradrahmen.setName("Rennradrahmen");
		rennradrahmen.setDescription("Große Auswahl an Rennradrahmen.");
		Category stadtrahmen = new Category();
		stadtrahmen.setName("Stadtrahmen");
		stadtrahmen.setDescription("Große Auswahl an Stadtrahmen.");
		Category mountainbikerahmen = new Category();
		mountainbikerahmen.setName("Mountainbikerahmen");
		mountainbikerahmen.setDescription("Große Auswahl an Mountainbikerahmen.");
		Category rahmen = new Category();
		rahmen.setName("Fahrradrahmen");
		rahmen.setDescription("Große Auswahl an Rahmen der verschiedenen Fahrradtypen.");
		rahmen.setChildCategories(new ArrayList<Category>());
		rahmen.addChildCategory(rennradrahmen);
		rahmen.addChildCategory(stadtrahmen);
		rahmen.addChildCategory(mountainbikerahmen);
		rennradrahmen.setFatherCategory(rahmen);
		stadtrahmen.setFatherCategory(rahmen);
		mountainbikerahmen.setFatherCategory(rahmen);

		Category rennradfelgen = new Category();
		rennradfelgen.setName("Rennradfelgen");
		rennradfelgen.setDescription("Große Auswahl an Rennradfelgen.");
		Category stadtfelgen = new Category();
		stadtfelgen.setName("Stadtfelgen");
		stadtfelgen.setDescription("Große Auswahl an Stadtfelgen.");
		Category mountainbikefelgen = new Category();
		mountainbikefelgen.setName("Mountainbikefelgen");
		mountainbikefelgen.setDescription("Große Auswahl an Mountainbikefelgen.");
		Category felgen = new Category();
		felgen.setName("Fahrradfelgen");
		felgen.setDescription("Große Auswahl an Fahrradfelgen der verschiedenen Fahrradtypen.");
		felgen.setChildCategories(new ArrayList<Category>());
		felgen.addChildCategory(rennradfelgen);
		felgen.addChildCategory(stadtfelgen);
		felgen.addChildCategory(mountainbikefelgen);
		rennradfelgen.setFatherCategory(felgen);
		stadtfelgen.setFatherCategory(felgen);
		mountainbikefelgen.setFatherCategory(felgen);

		articleManagementService.createCategory(rahmen);
		articleManagementService.createCategory(rennradrahmen);
		articleManagementService.createCategory(stadtrahmen);
		articleManagementService.createCategory(mountainbikerahmen);
		articleManagementService.createCategory(felgen);
		articleManagementService.createCategory(rennradfelgen);
		articleManagementService.createCategory(stadtfelgen);
		articleManagementService.createCategory(mountainbikefelgen);

		//Create ItemPools
		ItemPool pool1 = new ItemPool();
		ItemPool pool2 = new ItemPool();
		ItemPool pool3 = new ItemPool();

		articleManagementService.createItemPool(pool1);
		articleManagementService.createItemPool(pool2);
		articleManagementService.createItemPool(pool3);

		// Create Items
		Item item1 = new Item("Rennradrahmen 1", "beschreibung", 149.99, 15.50, rennradrahmen, pool1);
		Item item2 = new Item("Rennradrahmen 2", "beschreibung", 199.99, 15.50, rennradrahmen, pool1);
		Item item3 = new Item("Stadtrahmen 1", "beschreibung", 149.99, 15.50, stadtrahmen, pool2);
		Item item4 = new Item("Stadtrahmen 2", "beschreibung", 199.99, 15.50, stadtrahmen, pool2);
		Item item5 = new Item("Mountainbikerahmen 1", "beschreibung", 149.99, 15.50, mountainbikerahmen, pool3);
		Item item6 = new Item("Mountainbikerahmen 2", "beschreibung", 199.99, 15.50, mountainbikerahmen, pool3);
		Item item7 = new Item("Rennradfelge 1", "beschreibung", 89.99, 15.50, rennradfelgen, pool1);
		Item item8 = new Item("Rennradfelge 2", "beschreibung", 119.99, 15.50, rennradfelgen, pool1);
		Item item9 = new Item("Stadtfelge 1", "beschreibung", 89.99, 15.50, stadtfelgen, pool2);
		Item item10 = new Item("Stadtfelge 2", "beschreibung", 119.99, 15.50, stadtfelgen, pool2);
		Item item11 = new Item("Mountainbikefelge 1", "beschreibung", 89.99, 15.50, mountainbikefelgen, pool3);
		Item item12 = new Item("Mountainbikefelge 2", "beschreibung", 119.99, 15.50, mountainbikefelgen, pool3);

		articleManagementService.createItem(item1);
		articleManagementService.createItem(item2);
		articleManagementService.createItem(item3);
		articleManagementService.createItem(item4);
		articleManagementService.createItem(item5);
		articleManagementService.createItem(item6);
		articleManagementService.createItem(item7);
		articleManagementService.createItem(item8);
		articleManagementService.createItem(item9);
		articleManagementService.createItem(item10);
		articleManagementService.createItem(item11);
		articleManagementService.createItem(item12);

		pool1.addItem(item1);
		pool1.addItem(item2);
		pool1.addItem(item7);
		pool1.addItem(item8);
		pool2.addItem(item3);
		pool2.addItem(item4);
		pool2.addItem(item9);
		pool2.addItem(item10);
		pool3.addItem(item5);
		pool3.addItem(item6);
		pool3.addItem(item11);
		pool3.addItem(item12);

		// Create DepotItems
		for (int i=0 ; i < 10 ; i++) {
			articleManagementService.createDepotItem(new DepotItem(item1));
			articleManagementService.createDepotItem(new DepotItem(item2));
			articleManagementService.createDepotItem(new DepotItem(item3));
			articleManagementService.createDepotItem(new DepotItem(item4));
			articleManagementService.createDepotItem(new DepotItem(item5));
			articleManagementService.createDepotItem(new DepotItem(item6));
			articleManagementService.createDepotItem(new DepotItem(item7));
			articleManagementService.createDepotItem(new DepotItem(item8));
			articleManagementService.createDepotItem(new DepotItem(item9));
			articleManagementService.createDepotItem(new DepotItem(item10));
			articleManagementService.createDepotItem(new DepotItem(item11));
			articleManagementService.createDepotItem(new DepotItem(item12));
		}
	}
}

