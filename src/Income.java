import java.util.Calendar;

public class Income {
    private Double count;
    private Calendar expirationDate;
    private Double price;
    private Double producerPrice;
    private String series;
    private Double total;
    private String tradeName;
    private Double medicament;
    private String manufacturer;
    private String information;
    private String barCode;
    private String unit;


    public void setCount(Double count) {
        this.count = count;
    }

    public void setExpirationDate(Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setProducerPrice(Double producerPrice) {
        this.producerPrice = producerPrice;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public void setMedicament(Double medicament) {
        this.medicament = medicament;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }


    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Количество по накладной: " + count +"\n" +
                "Срок годности: " + expirationDate +"\n" +
                "Цена отпускная: " + price +"\n" +
                "Цена производителя: " + producerPrice +"\n" +
                "Серия: " + series +"\n" +
                "Общая сумма: " + total +"\n" +
                "Торговое наименование: " + tradeName +"\n" +
                "Ссылка: " + medicament +"\n" +
                "Производитель: " + manufacturer +"\n" +
                "Доп. информация: " + information +"\n" +
                "Баркод: " + barCode +"\n" +
                "Единица измерения: " + unit +"\n";
    }
}
