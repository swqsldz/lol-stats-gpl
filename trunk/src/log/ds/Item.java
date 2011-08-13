package log.ds;

import java.util.ArrayList;

public class Item {
	/** Identificador del objeto */
	private int itemId;
	
	/** Imagen del objeto TODO No es un String, o si, como sea */
	private String image;
	
	/** Nombre del objeto */
	private String name;
	
	/** Descripción del objeto */
	private String description;
	
	/** Precio del objeto, si el objeto es básico es su precio de compra,
	 * si el objeto está formado por más, es el precio de crearlo */
	private int price;
	
	/** Precio total contando los materiales y el precio de crear el objeto */
	private int totalPrice;
	
	/** Lista de objetos necesarios para constuirlo */
	private ArrayList<Integer> builtFrom;
	
	/** Lista de objetos que se pueden construir con él */
	private ArrayList<Integer> buildsInto;
	
	public Item() {
		itemId = -1;
		image = null;
		name = null;
		description = null;
		price = -1;
		totalPrice = -1;
		builtFrom = null;
		buildsInto = null;
	}

	public int getItemId() {return itemId;}
	public void setItemId(int itemId) {this.itemId = itemId;}

	public String getImage() {return image;}
	public void setImage(String image) {this.image = image;}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}

	public int getPrice() {return price;}
	public void setPrice(int price) {this.price = price;}

	public int getTotalPrice() {return totalPrice;}
	public void setTotalPrice(int totalPrice) {this.totalPrice = totalPrice;}
	
	public ArrayList<Integer> getBuiltFrom() {return builtFrom;}
	public void setBuiltFrom(ArrayList<Integer> builtFrom) {this.builtFrom = builtFrom;}

	public ArrayList<Integer> getBuildsInto() {return buildsInto;}
	public void setBuildsInto(ArrayList<Integer> buildsInto) {this.buildsInto = buildsInto;
	}
}
