package br.com.caelum.carangobom.domain.entity;

public interface Vehicle {
    Long getId();
    void setId(Long id);
    String getModel();
    void setModel(String model);
    int getYear();
    void setYear(int year);
    double getPrice();
    void setPrice(double price);
    Marca getMarca();
    void setMarca(Marca marca);
}
