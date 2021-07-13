package br.com.caelum.carangobom.domain.entity;

public interface Vehicle {
    Long getId();
    void setId(Long id);
    String getModel();
    void setModel(String model);
    Integer getYear();
    void setYear(Integer year);
    Double getPrice();
    void setPrice(Double price);
    Marca getMarca();
    void setMarca(Marca marca);
}
