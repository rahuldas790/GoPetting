package rahulkumardas.gopetting.models;

/**
 * Created by Rahul Kumar Das on 08-02-2017.
 */

public class Item {

    public Item(String name, String image, String date, String startDate, String type) {
        this.name = name;
        this.image = image;
        this.date = date;
        this.startDate = startDate;
        this.type = type;
    }

    public String name, image, date, startDate, type;
}
