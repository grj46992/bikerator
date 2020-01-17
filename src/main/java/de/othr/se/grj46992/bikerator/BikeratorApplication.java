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

		Category rennradreifen = new Category();
		rennradreifen.setName("Rennradreifen");
		rennradreifen.setDescription("Große Auswahl an Rennradreifen.");
		Category stadtreifen = new Category();
		stadtreifen.setName("Stadtreifen");
		stadtreifen.setDescription("Große Auswahl an Stadtreifen.");
		Category mountainbikereifen = new Category();
		mountainbikereifen.setName("Mountainbikereifen");
		mountainbikereifen.setDescription("Große Auswahl an Mountainbikereifen.");
		Category reifen = new Category();
		reifen.setName("Fahrradreifen");
		reifen.setDescription("Große Auswahl an Fahrradreifen der verschiedenen Fahrradtypen.");
		reifen.setChildCategories(new ArrayList<Category>());
		reifen.addChildCategory(rennradreifen);
		reifen.addChildCategory(stadtreifen);
		reifen.addChildCategory(mountainbikereifen);
		rennradreifen.setFatherCategory(reifen);
		stadtreifen.setFatherCategory(reifen);
		mountainbikereifen.setFatherCategory(reifen);

		Category rennradschaltwerke = new Category();
		rennradschaltwerke.setName("Rennradschaltwerke");
		rennradschaltwerke.setDescription("Große Auswahl an Rennradschaltwerken.");
		Category stadtschaltwerke = new Category();
		stadtschaltwerke.setName("Stadtschaltwerke");
		stadtschaltwerke.setDescription("Große Auswahl an Stadtschaltwerken.");
		Category mountainbikeschaltwerke = new Category();
		mountainbikeschaltwerke.setName("Mountainbikeschaltwerke");
		mountainbikeschaltwerke.setDescription("Große Auswahl an Mountainbikeschaltwerken.");
		Category schaltwerke = new Category();
		schaltwerke.setName("Fahrradschaltwerke");
		schaltwerke.setDescription("Große Auswahl an Fahrradschaltwerken der verschiedenen Fahrradtypen.");
		schaltwerke.setChildCategories(new ArrayList<Category>());
		schaltwerke.addChildCategory(rennradschaltwerke);
		schaltwerke.addChildCategory(stadtschaltwerke);
		schaltwerke.addChildCategory(mountainbikeschaltwerke);
		rennradschaltwerke.setFatherCategory(schaltwerke);
		stadtschaltwerke.setFatherCategory(schaltwerke);
		mountainbikeschaltwerke.setFatherCategory(schaltwerke);

		articleManagementService.createCategory(rahmen);
		articleManagementService.createCategory(rennradrahmen);
		articleManagementService.createCategory(stadtrahmen);
		articleManagementService.createCategory(mountainbikerahmen);
		articleManagementService.createCategory(felgen);
		articleManagementService.createCategory(rennradfelgen);
		articleManagementService.createCategory(stadtfelgen);
		articleManagementService.createCategory(mountainbikefelgen);
		articleManagementService.createCategory(reifen);
		articleManagementService.createCategory(rennradreifen);
		articleManagementService.createCategory(stadtreifen);
		articleManagementService.createCategory(mountainbikereifen);
		articleManagementService.createCategory(schaltwerke);
		articleManagementService.createCategory(rennradschaltwerke);
		articleManagementService.createCategory(stadtschaltwerke);
		articleManagementService.createCategory(mountainbikeschaltwerke);

		//Create ItemPools
		ItemPool pool1 = new ItemPool();
		ItemPool pool2 = new ItemPool();
		ItemPool pool3 = new ItemPool();
		ItemPool pool4 = new ItemPool();
		ItemPool pool5 = new ItemPool();
		ItemPool pool6 = new ItemPool();
		ItemPool pool7 = new ItemPool();
		ItemPool pool8 = new ItemPool();
		ItemPool pool9 = new ItemPool();
		ItemPool pool10 = new ItemPool();
		ItemPool pool11 = new ItemPool();
		ItemPool pool12 = new ItemPool();

		articleManagementService.createItemPool(pool1);
		articleManagementService.createItemPool(pool2);
		articleManagementService.createItemPool(pool3);
		articleManagementService.createItemPool(pool4);
		articleManagementService.createItemPool(pool5);
		articleManagementService.createItemPool(pool6);
		articleManagementService.createItemPool(pool7);
		articleManagementService.createItemPool(pool8);
		articleManagementService.createItemPool(pool9);
		articleManagementService.createItemPool(pool10);
		articleManagementService.createItemPool(pool11);
		articleManagementService.createItemPool(pool12);

		// Create Items
		Item item1 = new Item("Rennradrahmen 1", "beschreibung", 149.99, 15.50, rennradrahmen);
		item1.setImagePath("C:\\Users\\Johannes\\IdeaProjects\\bikerator\\src\\main\\resources\\static\\images\\bike.jpg");
		item1.addItemPool(pool1);
		Item item2 = new Item("Rennradrahmen 2", "beschreibung", 199.99, 15.50, rennradrahmen);
		item2.addItemPool(pool2);
		Item item3 = new Item("Stadtrahmen 1", "beschreibung", 149.99, 15.50, stadtrahmen);
		item3.addItemPool(pool3);
		Item item4 = new Item("Stadtrahmen 2", "beschreibung", 199.99, 15.50, stadtrahmen);
		item4.addItemPool(pool4);
		Item item5 = new Item("Mountainbikerahmen 1", "beschreibung", 149.99, 15.50, mountainbikerahmen);
		item5.addItemPool(pool5);
		Item item6 = new Item("Mountainbikerahmen 2", "beschreibung", 199.99, 15.50, mountainbikerahmen);
		item6.addItemPool(pool6);
		Item item7 = new Item("Rennradfelge 1", "beschreibung", 89.99, 15.50, rennradfelgen);
		item7.addItemPool(pool1);
		item7.addItemPool(pool2);
		item7.addItemPool(pool7);
		Item item8 = new Item("Rennradfelge 2", "beschreibung", 119.99, 15.50, rennradfelgen);
		item8.addItemPool(pool1);
		item8.addItemPool(pool2);
		item8.addItemPool(pool8);
		Item item9 = new Item("Stadtfelge 1", "beschreibung", 89.99, 15.50, stadtfelgen);
		item9.addItemPool(pool2);
		item9.addItemPool(pool3);
		item9.addItemPool(pool9);
		Item item10 = new Item("Stadtfelge 2", "beschreibung", 119.99, 15.50, stadtfelgen);
		item10.addItemPool(pool3);
		item10.addItemPool(pool4);
		item10.addItemPool(pool10);
		Item item11 = new Item("Mountainbikefelge 1", "beschreibung", 89.99, 15.50, mountainbikefelgen);
		item11.addItemPool(pool5);
		item11.addItemPool(pool11);
		Item item12 = new Item("Mountainbikefelge 2", "beschreibung", 119.99, 15.50, mountainbikefelgen);
		item12.addItemPool(pool6);
		item12.addItemPool(pool12);
		Item item13 = new Item("Rennradreifensatz 1", "beschreibung", 19.99, 15.50, rennradreifen);
		item13.addItemPool(pool7);
		item13.addItemPool(pool8);
		Item item14 = new Item("Rennradreifensatz 2", "beschreibung", 39.99, 15.50, rennradreifen);
		item14.addItemPool(pool7);
		Item item15 = new Item("Rennradreifensatz 3", "beschreibung", 59.99, 15.50, rennradreifen);
		item15.addItemPool(pool7);
		item15.addItemPool(pool8);
		Item item16 = new Item("Stadtreifensatz 1", "beschreibung", 19.99, 15.50, stadtreifen);
		item16.addItemPool(pool9);
		item16.addItemPool(pool10);
		item16.addItemPool(pool11);
		Item item17 = new Item("Stadtreifensatz 2", "beschreibung", 39.99, 15.50, stadtreifen);
		item17.addItemPool(pool9);
		item17.addItemPool(pool10);
		Item item18 = new Item("Stadtreifensatz 3", "beschreibung", 59.99, 15.50, stadtreifen);
		item18.addItemPool(pool9);
		item18.addItemPool(pool11);
		Item item19 = new Item("Mountainbikereifensatz 1", "beschreibung", 19.99, 15.50, mountainbikereifen);
		item19.addItemPool(pool11);
		Item item20 = new Item("Mountainbikereifensatz 2", "beschreibung", 39.99, 15.50, mountainbikereifen);
		item20.addItemPool(pool11);
		item20.addItemPool(pool12);
		Item item21 = new Item("Mountainbikereifensatz 3", "beschreibung", 59.99, 15.50, mountainbikereifen);
		item21.addItemPool(pool9);
		item21.addItemPool(pool12);
		Item item22 = new Item("Rennradschaltwerk 1", "beschreibung", 29.99, 15.50, rennradschaltwerke);
		item22.addItemPool(pool1);
		item22.addItemPool(pool2);
		Item item23 = new Item("Rennradschaltwerk 2", "beschreibung", 49.99, 15.50, rennradschaltwerke);
		item23.addItemPool(pool2);
		Item item24 = new Item("Stadtschaltwerk 1", "beschreibung", 29.99, 15.50, stadtschaltwerke);
		item24.addItemPool(pool3);
		Item item25 = new Item("Stadtschaltwerk 2", "beschreibung", 49.99, 15.50, stadtschaltwerke);
		item25.addItemPool(pool3);
		Item item26 = new Item("Mountainbikeschaltwerk 1", "beschreibung", 29.99, 15.50, mountainbikeschaltwerke);
		item26.addItemPool(pool3);
		item26.addItemPool(pool4);
		item26.addItemPool(pool5);
		Item item27 = new Item("Mountainbikeschaltwerk 2", "beschreibung", 49.99, 15.50, mountainbikeschaltwerke);
		item27.addItemPool(pool5);
		item27.addItemPool(pool6);

		// Add Items to ItemPools
		pool1.addItem(item1);
		pool1.addItem(item7);
		pool1.addItem(item8);
		pool1.addItem(item22);
		pool2.addItem(item2);
		pool2.addItem(item7);
		pool2.addItem(item8);
		pool2.addItem(item9);
		pool2.addItem(item22);
		pool2.addItem(item23);
		pool3.addItem(item3);
		pool3.addItem(item9);
		pool3.addItem(item10);
		pool3.addItem(item24);
		pool3.addItem(item25);
		pool3.addItem(item26);
		pool4.addItem(item4);
		pool4.addItem(item10);
		pool4.addItem(item26);
		pool5.addItem(item5);
		pool5.addItem(item11);
		pool5.addItem(item26);
		pool5.addItem(item27);
		pool6.addItem(item6);
		pool6.addItem(item12);
		pool6.addItem(item27);
		pool7.addItem(item7);
		pool7.addItem(item13);
		pool7.addItem(item14);
		pool7.addItem(item15);
		pool8.addItem(item8);
		pool8.addItem(item13);
		pool8.addItem(item15);
		pool9.addItem(item9);
		pool9.addItem(item16);
		pool9.addItem(item17);
		pool9.addItem(item18);
		pool9.addItem(item21);
		pool10.addItem(item10);
		pool10.addItem(item16);
		pool10.addItem(item17);
		pool11.addItem(item11);
		pool11.addItem(item16);
		pool11.addItem(item18);
		pool11.addItem(item19);
		pool11.addItem(item20);
		pool12.addItem(item12);
		pool12.addItem(item20);
		pool12.addItem(item21);

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
		articleManagementService.createItem(item13);
		articleManagementService.createItem(item14);
		articleManagementService.createItem(item15);
		articleManagementService.createItem(item16);
		articleManagementService.createItem(item17);
		articleManagementService.createItem(item18);
		articleManagementService.createItem(item19);
		articleManagementService.createItem(item20);
		articleManagementService.createItem(item21);
		articleManagementService.createItem(item22);
		articleManagementService.createItem(item23);
		articleManagementService.createItem(item24);
		articleManagementService.createItem(item25);
		articleManagementService.createItem(item26);
		articleManagementService.createItem(item27);

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
			articleManagementService.createDepotItem(new DepotItem(item13));
			articleManagementService.createDepotItem(new DepotItem(item14));
			articleManagementService.createDepotItem(new DepotItem(item15));
			articleManagementService.createDepotItem(new DepotItem(item16));
			articleManagementService.createDepotItem(new DepotItem(item17));
			articleManagementService.createDepotItem(new DepotItem(item18));
			articleManagementService.createDepotItem(new DepotItem(item19));
			articleManagementService.createDepotItem(new DepotItem(item20));
			articleManagementService.createDepotItem(new DepotItem(item21));
			articleManagementService.createDepotItem(new DepotItem(item22));
			articleManagementService.createDepotItem(new DepotItem(item23));
			articleManagementService.createDepotItem(new DepotItem(item24));
			articleManagementService.createDepotItem(new DepotItem(item25));
			articleManagementService.createDepotItem(new DepotItem(item26));
			articleManagementService.createDepotItem(new DepotItem(item27));
		}
	}
}

